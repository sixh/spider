/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sixh.spider.dubbo.codec;

import com.sixh.spider.common.Const;
import com.sixh.spider.common.buffer.ChannelBuffer;
import com.sixh.spider.common.buffer.ChannelBufferInputStream;
import com.sixh.spider.common.buffer.ChannelBufferOutputStream;
import com.sixh.spider.common.serialization.ObjectInput;
import com.sixh.spider.common.serialization.ObjectOutput;
import com.sixh.spider.common.serialization.Serialization;
import com.sixh.spider.common.serialization.hessian.Hessian2Serialization;
import com.sixh.spider.common.utils.Bytes;
import com.sixh.spider.common.utils.ReflectUtils;
import com.sixh.spider.dubbo.DubboChannel;
import com.sixh.spider.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * DubboCodec.
 * <p>
 * //引用dubbo:org.apache.dubbo.remoting.exchange.codec.ExchangeCodec
 * <p>
 * <p>
 * 18-12-3上午11:15
 *
 * @author chenbin sixh
 */
public class DubboCodec implements Codec2 {
    private final Logger logger = LoggerFactory.getLogger(DubboCodec.class);

    /**
     * header length.
     */
    private static final int HEADER_LENGTH = 16;

    /**
     * magic header.
     */
    private static final short MAGIC = (short) 0xdabb;

    /**
     * The constant MAGIC_HIGH.
     */
    private static final byte MAGIC_HIGH = Bytes.short2bytes(MAGIC)[0];

    /**
     * The constant MAGIC_LOW.
     */
    private static final byte MAGIC_LOW = Bytes.short2bytes(MAGIC)[1];

    /**
     * The constant FLAG_REQUEST.
     */
    private static final byte FLAG_REQUEST = (byte) 0x80;

    /**
     * The constant FLAG_TWOWAY.
     */
    private static final byte FLAG_TWOWAY = (byte) 0x40;

    /**
     * The constant FLAG_EVENT.
     */
    private static final byte FLAG_EVENT = (byte) 0x20;

    /**
     * The constant SERIALIZATION_MASK.
     */
    private static final int SERIALIZATION_MASK = 0x1f;
    private RequestCodec requestCodec = new RequestCodec();
    private ResponseCodec responseCodec = new ResponseCodec();

    @Override
    public void encode(DubboChannel channel, ChannelBuffer byteBuf, Object message) throws IOException {
        requestCodec.encode(channel, byteBuf, message);
    }

    @Override
    public Object decode(DubboChannel channel, ChannelBuffer buffer) throws IOException {
        return responseCodec.decode(channel, buffer);
    }

    /**
     * 对于请求体的处理.
     */
    static class RequestCodec implements Codec2 {

        @Override
        public void encode(DubboChannel channel, ChannelBuffer buffer, Object message) throws IOException {
            if (!(message instanceof DubboRequest)) {
                return;
            }
            DubboRequest req = (DubboRequest) message;
            Serialization serialization = new Hessian2Serialization();

            //header;
            byte[] header = new byte[HEADER_LENGTH];
            //0:MAGIC_HIGH 1: MAGIC_LOW

            //:0---7:magic high
            //:8---15: magic low
            Bytes.short2bytes(MAGIC, header, 0);


            //16: ([req&res]&twoawy&event)
            //2:FLAG_REQUEST
            int flagReq = FLAG_REQUEST | serialization.getContentTypeId();
            if (req.isTwoWay()) {
                flagReq |= FLAG_TWOWAY;
            }
            if (req.isEvent()) {
                flagReq |= FLAG_EVENT;
            }
            header[2] = (byte) flagReq;

            //3:null空闲
            // set request id.
            Bytes.long2bytes(req.getId(), header, 4);

            // encode request data.
            int savedWriteIndex = buffer.writerIndex();
            buffer.writerIndex(savedWriteIndex + HEADER_LENGTH);
            ChannelBufferOutputStream bos = new ChannelBufferOutputStream(buffer);
            ObjectOutput out = serialization.serialize(null, bos);
            if (req.isEvent()) {
                out.writeObject(req.getData());
            } else {
                encodeRequestData(channel, out, req.getData(), req.getVersion());
            }
            out.flushBuffer();
            bos.flush();
            bos.close();
            int len = bos.writtenBytes();
            checkPayload(channel, len);
            Bytes.int2bytes(len, header, 12);
            // write
            buffer.writerIndex(savedWriteIndex);
            // write header.
            buffer.writeBytes(header);
            buffer.writerIndex(savedWriteIndex + HEADER_LENGTH + len);
        }

        /**
         * Encode request data.
         *
         * @param channel the channel
         * @param out     the out
         * @param data    the data
         * @param version the version
         * @throws IOException the io exception
         */
        protected void encodeRequestData(DubboChannel channel, ObjectOutput out, Object data, String version) throws IOException {
            DubboInvocation inv = (DubboInvocation) data;
            out.writeUTF(version);
            out.writeUTF(inv.getAttachment(Const.PATH_KEY));
            out.writeUTF(inv.getAttachment(Const.VERSION_KEY));
            out.writeUTF(inv.getMethodName());
            out.writeUTF(ReflectUtils.getDesc(inv.getParameterTypes()));
            Object[] args = inv.getArguments();
            if (args != null) {
                for (Object arg : args) {
                    out.writeObject(arg);
                }
            }
            out.writeObject(inv.getAttachments());
        }

        @Override
        public Object decode(DubboChannel channel, ChannelBuffer byteBuf) throws IOException {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * The type DubboResponse codec.
     */
    class ResponseCodec implements Codec2 {

        @Override
        public void encode(DubboChannel channel, ChannelBuffer byteBuf, Object message) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object decode(DubboChannel channel, ChannelBuffer buffer) throws IOException {
            int readable = buffer.readableBytes();
            byte[] header = new byte[Math.min(readable, HEADER_LENGTH)];
            buffer.readBytes(header);
            return decode(channel, buffer, readable, header);
        }

        private Object decode(DubboChannel channel, ChannelBuffer buffer, int readable, byte[] header) throws IOException {
            // check magic number.
            if (readable > 0 && header[0] == MAGIC_HIGH
                    || readable > 1 && header[1] == MAGIC_LOW) {
                // check length.
                if (readable < HEADER_LENGTH) {
                    return DecodeResult.NEED_MORE_INPUT;
                }
                // get data length.
                int len = Bytes.bytes2int(header, 12);
                checkPayload(channel, len);
                int tt = len + HEADER_LENGTH;
                if (readable < tt) {
                    return DecodeResult.NEED_MORE_INPUT;
                }
                // limit input stream.
                ChannelBufferInputStream is = new ChannelBufferInputStream(buffer, len);
                try {
                    return decodeBody(channel, is, header);
                } finally {
                    if (is.available() > 0) {
                        try {
                            if (logger.isWarnEnabled()) {
                                logger.warn("Skip input stream " + is.available());
                            }
                            if (is.available() > 0) {
                                is.skip(is.available());
                            }
                        } catch (IOException e) {
                            logger.warn(e.getMessage(), e);
                        }
                    }
                }
            }
            return DubboResult.empty();
        }
    }

    /**
     * Decode body object.
     *
     * @param channel the channel
     * @param is      the is
     * @param header  the header
     * @return the object
     * @throws IOException the io exception
     */
    private Object decodeBody(DubboChannel channel, InputStream is, byte[] header) throws IOException {
        byte flag = header[2], proto = (byte) (flag & SERIALIZATION_MASK);
        // get request id.
        long id = Bytes.bytes2long(header, 4);
        if ((flag & FLAG_REQUEST) == 0) {
            // decode response.
            DubboResponse res = new DubboResponse(id);
            if ((flag & FLAG_EVENT) != 0) {
                res.setEvent(DubboResponse.HEARTBEAT_EVENT);
            }
            // get status.
            byte status = header[3];
            res.setStatus(status);
            try {
                Serialization serialization = new Hessian2Serialization();
                ObjectInput in = serialization.deserialize(null, is);
                if (status == DubboResponse.OK) {
                    Object data;
                    if (res.isHeartbeat()) {
                        data = in.readObject();
                    } else if (res.isEvent()) {
                        data = in.readObject();
                    } else {
                        data = ResponseBody.decode(is);
                    }
                    res.setResult(data);
                } else {
                    res.setErrorMessage(in.readUTF());
                }
            } catch (Throwable t) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Decode response failed: " + t.getMessage(), t);
                }
                res.setStatus(DubboResponse.CLIENT_ERROR);
                res.setErrorMessage(t.getMessage());
            }
            return res;
        }
        return DubboResult.empty();
    }

}
