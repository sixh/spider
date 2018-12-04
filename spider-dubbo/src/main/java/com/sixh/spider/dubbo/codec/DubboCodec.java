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
import com.sixh.spider.dubbo.rpc.DecodeableRpcResult;
import com.sixh.spider.dubbo.rpc.Request;
import com.sixh.spider.dubbo.rpc.Response;
import com.sixh.spider.dubbo.rpc.RpcInvocation;
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
    protected static final int HEADER_LENGTH = 16;
    /**
     * magic header.
     */
    protected static final short MAGIC = (short) 0xdabb;
    /**
     * The constant MAGIC_HIGH.
     */
    protected static final byte MAGIC_HIGH = Bytes.short2bytes(MAGIC)[0];
    /**
     * The constant MAGIC_LOW.
     */
    protected static final byte MAGIC_LOW = Bytes.short2bytes(MAGIC)[1];
    /**
     * The constant FLAG_REQUEST.
     */
    protected static final byte FLAG_REQUEST = (byte) 0x80;
    /**
     * The constant FLAG_TWOWAY.
     */
    protected static final byte FLAG_TWOWAY = (byte) 0x40;
    /**
     * The constant FLAG_EVENT.
     */
    protected static final byte FLAG_EVENT = (byte) 0x20;
    /**
     * The constant SERIALIZATION_MASK.
     */
    protected static final int SERIALIZATION_MASK = 0x1f;

    /**
     * The constant EMPTY_OBJECT_ARRAY.
     */
    public static final byte RESPONSE_WITH_EXCEPTION = 0;
    public static final byte RESPONSE_VALUE = 1;
    public static final byte RESPONSE_NULL_VALUE = 2;
    public static final byte RESPONSE_WITH_EXCEPTION_WITH_ATTACHMENTS = 3;
    public static final byte RESPONSE_VALUE_WITH_ATTACHMENTS = 4;
    public static final byte RESPONSE_NULL_VALUE_WITH_ATTACHMENTS = 5;

    @Override
    public void encode(DubboChannel channel, ChannelBuffer byteBuf, Object message) throws IOException {
        if (message instanceof Request) {
            encodeRequest(channel, byteBuf, (Request) message);
        }
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
        return null;
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
    protected Object decodeBody(DubboChannel channel, InputStream is, byte[] header) throws IOException {
        byte flag = header[2], proto = (byte) (flag & SERIALIZATION_MASK);
        // get request id.
        long id = Bytes.bytes2long(header, 4);
        if ((flag & FLAG_REQUEST) == 0) {
            // decode response.
            Response res = new Response(id);
            if ((flag & FLAG_EVENT) != 0) {
                res.setEvent(Response.HEARTBEAT_EVENT);
            }
            // get status.
            byte status = header[3];
            res.setStatus(status);
            try {
                Serialization serialization = new Hessian2Serialization();
                ObjectInput in = serialization.deserialize(null, is);
                if (status == Response.OK) {
                    Object data;
                    if (res.isHeartbeat()) {
                        data = in.readObject();
                    } else if (res.isEvent()) {
                        data = in.readObject();
                    } else {
                        DecodeableRpcResult result;
                        if (channel.getUrl().getParameter(
                                Const.DECODE_IN_IO_THREAD_KEY,
                                Const.DEFAULT_DECODE_IN_IO_THREAD)) {
                            result = new DecodeableRpcResult(channel, res, is, null, proto);
                            result.decode();
                        } else {
                            result = new DecodeableRpcResult(channel, res,
                                    is,
                                    null, proto);
                        }
                        data = result;
                    }
                    res.setResult(data);
                } else {
                    res.setErrorMessage(in.readUTF());
                }
            } catch (Throwable t) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Decode response failed: " + t.getMessage(), t);
                }
                res.setStatus(Response.CLIENT_ERROR);
                res.setErrorMessage(t.getMessage());
            }
            return res;
        }
        return null;
    }

    private byte[] readMessageData(InputStream is) throws IOException {
        if (is.available() > 0) {
            byte[] result = new byte[is.available()];
            is.read(result);
            return result;
        }
        return new byte[]{};
    }


    private void encodeRequest(DubboChannel channel, ChannelBuffer buffer, Request req) throws IOException {
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
        RpcInvocation inv = (RpcInvocation) data;
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

}
