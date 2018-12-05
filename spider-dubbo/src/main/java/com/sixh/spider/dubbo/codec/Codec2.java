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
import com.sixh.spider.common.exception.CodecException;
import com.sixh.spider.dubbo.DubboChannel;

import java.io.IOException;

/**
 * Codec2.
 * <p>
 * dubbo 协议的一部分实现.参考dubbo协议实现.
 * <p>
 * 18-11-30下午5:02
 *
 * @author chenbin sixh
 */
public interface Codec2 {

    /**
     * Encode.
     *
     * @param channel the channel
     * @param byteBuf the byte buf
     * @param message the message
     * @throws IOException the io exception
     */
    void encode(DubboChannel channel, ChannelBuffer byteBuf, Object message) throws IOException;

    /**
     * Decode.
     *
     * @param channel the channel
     * @param byteBuf the byte buf
     * @return the object
     * @throws IOException the io exception
     */
    Object decode(DubboChannel channel, ChannelBuffer byteBuf) throws IOException;

    /**
     * Check payload.
     *
     * @param channel the channel
     * @param size    the size
     * @throws IOException the io exception
     */
    default void checkPayload(DubboChannel channel, long size) throws IOException {
        int payload = Const.DEFAULT_PAYLOAD;
        if (channel != null && channel.getUrl() != null) {
            payload = channel.getUrl().getParameter(Const.PAYLOAD_KEY, Const.DEFAULT_PAYLOAD);
        }
        if (payload > 0 && size > payload) {
            throw new CodecException("Data length too large: " + size + ", max payload: " + payload + ", channel: " + channel);
        }
    }

    /**
     * The enum Decode result.
     */
    enum DecodeResult {
        /**
         * Need more input decode result.
         */
        NEED_MORE_INPUT,
        /**
         * Skip some input decode result.
         */
        SKIP_SOME_INPUT
    }
}
