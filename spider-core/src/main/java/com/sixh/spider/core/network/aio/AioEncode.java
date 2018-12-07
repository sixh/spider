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
package com.sixh.spider.core.network.aio;

import com.sixh.spider.common.buffer.ChannelBuffer;
import com.sixh.spider.common.buffer.ChannelBuffers;
import com.sixh.spider.core.network.MChannel;

/**
 * AioEncode.
 * <p>
 * <p>
 * 18-12-5下午4:46
 *
 * @author chenbin sixh
 */
public abstract class AioEncode implements AioOutHandler {

    /**
     * Encode.
     *
     * @param ctx     the ctx
     * @param channel the channel
     * @param object  the object
     * @param buffer  the buffer
     */
    public abstract void encode(AioHandlerContext ctx, MChannel channel, Object object, ChannelBuffer buffer);

    @Override
    public void write(AioHandlerContext ctx, Object msg) {
        ChannelBuffer byteBuffer = ChannelBuffers.buffer(1024*1024*8);
        encode(ctx, ctx.channel(), msg, byteBuffer);
        ctx.write(byteBuffer);
    }
}
