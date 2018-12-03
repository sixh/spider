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
package com.sixh.spider.dubbo.rpc;

import com.sixh.spider.common.buffer.ChannelBuffer;
import com.sixh.spider.common.buffer.ChannelBufferInputStream;
import com.sixh.spider.common.serialization.ObjectInput;
import com.sixh.spider.common.serialization.hessian.Hessian2Serialization;
import com.sixh.spider.core.network.Channel;
import com.sixh.spider.dubbo.codec.Codec2;
import com.sixh.spider.dubbo.codec.DubboCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class DecodeableRpcResult extends RpcResult implements Codec2 {

    private static final Logger log = LoggerFactory.getLogger(DecodeableRpcResult.class);

    private Channel channel;

    private byte serializationType;

    private InputStream inputStream;

    private Response response;

    private RpcInvocation invocation;

    private volatile boolean hasDecoded;

    public DecodeableRpcResult(Channel channel, Response response, InputStream is, RpcInvocation invocation, byte id) {
        this.channel = channel;
        this.response = response;
        this.inputStream = is;
        this.invocation = invocation;
        this.serializationType = id;
    }

    @Override
    public void encode(Channel channel, ChannelBuffer byteBuf, Object message) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object decode(Channel channel, ChannelBuffer byteBuf) throws IOException {
        ObjectInput in = new Hessian2Serialization()
                .deserialize(null, new ChannelBufferInputStream(byteBuf));

        byte flag = in.readByte();
        switch (flag) {
            case DubboCodec.RESPONSE_NULL_VALUE:
                break;
            case DubboCodec.RESPONSE_VALUE:
              /*  try {
                    Type[] returnType = RpcUtils.getReturnTypes(invocation);
                    setValue(returnType == null || returnType.length == 0 ? in.readObject() :
                            (returnType.length == 1 ? in.readObject((Class<?>) returnType[0])
                                    : in.readObject((Class<?>) returnType[0], returnType[1])));
                } catch (ClassNotFoundException e) {
                    log.error("Read response data failed.", e);
                    throw new IOException("Read response data failed.");
                }*/
                break;
            case DubboCodec.RESPONSE_WITH_EXCEPTION:
                try {
                    Object obj = in.readObject();
                    if (obj instanceof Throwable == false) {
                        throw new IOException("Response data error, expect Throwable, but get " + obj);
                    }
                    setException((Throwable) obj);
                } catch (ClassNotFoundException e) {
                    log.error("Read response data failed.", e);
                    throw new IOException("Read response data failed.");
                }
                break;
            case DubboCodec.RESPONSE_NULL_VALUE_WITH_ATTACHMENTS:
                try {
                    setAttachments((Map<String, String>) in.readObject(Map.class));
                } catch (ClassNotFoundException e) {
                    log.error("Read response data failed.", e);
                    throw new IOException("Read response data failed.");
                }
                break;
            case DubboCodec.RESPONSE_VALUE_WITH_ATTACHMENTS:
            /*    try {
                    Type[] returnType = RpcUtils.getReturnTypes(invocation);
                    setValue(returnType == null || returnType.length == 0 ? in.readObject() :
                            (returnType.length == 1 ? in.readObject((Class<?>) returnType[0])
                                    : in.readObject((Class<?>) returnType[0], returnType[1])));
                    setAttachments((Map<String, String>) in.readObject(Map.class));
                } catch (ClassNotFoundException e) {
                    log.error("Read response data failed.", e);
                    throw new IOException("Read response data failed.");
                }*/
                break;
            case DubboCodec.RESPONSE_WITH_EXCEPTION_WITH_ATTACHMENTS:
                try {
                    Object obj = in.readObject();
                    if (obj instanceof Throwable == false) {
                        throw new IOException("Response data error, expect Throwable, but get " + obj);
                    }
                    setException((Throwable) obj);
                    setAttachments((Map<String, String>) in.readObject(Map.class));
                } catch (ClassNotFoundException e) {
                    log.error("Read response data failed.", e);
                    throw new IOException("Read response data failed.");
                }
                break;
            default:
                throw new IOException("Unknown result flag, expect '0' '1' '2', get " + flag);
        }
        return this;
    }
}
