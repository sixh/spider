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
package com.sixh.spider.dubbo.aio;

import com.sixh.spider.common.URL;
import com.sixh.spider.common.buffer.ByteBufferBackedChannelBuffer;
import com.sixh.spider.common.buffer.ChannelBuffer;
import com.sixh.spider.core.network.MChannel;
import com.sixh.spider.core.network.MChannelHandler;
import com.sixh.spider.core.network.aio.AioChannel;
import com.sixh.spider.core.network.aio.AioDecode;
import com.sixh.spider.core.network.aio.AioEncode;
import com.sixh.spider.core.network.aio.AioHandlerContext;
import com.sixh.spider.dubbo.DubboChannel;
import com.sixh.spider.dubbo.codec.Codec2;
import com.sixh.spider.dubbo.netty.NettyBackedChannelBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * AioCodecAdapter.
 * <p>
 * <p>
 * 18-12-5下午3:57
 *
 * @author chenbin sixh
 */
public class AioCodecAdapter {

    private AioDecode decode = new InAioDecode();
    private AioEncode encode = new InAioEncode();
    private Codec2 codec;

    private URL url;

    private MChannelHandler handler;

    public AioDecode getDecode() {
        return decode;
    }

    public AioEncode getEncode() {
        return encode;
    }

    /**
     * Instantiates a new Aio codec adapter.
     *
     * @param codec   the codec
     * @param url     the url
     * @param handler the handler
     */
    public AioCodecAdapter(Codec2 codec, URL url, MChannelHandler handler) {
        this.codec = codec;
        this.url = url;
        this.handler = handler;
    }

    class InAioEncode extends AioEncode {

        @Override
        public void encode(AioHandlerContext ctx, MChannel channel, Object msg, ChannelBuffer buffer) {
            AioChannel ch = ctx.channel();
            try {
                AioBackedChannelBuffer heapChannelBuffer = new AioBackedChannelBuffer(buffer);
                codec.encode(new DubboChannel(ch, url), heapChannelBuffer, msg);
//                buffer.flip();
//                buffer.
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
//                NettyChannel.removeChannelIfDisconnected(ch);
            }
        }
    }

    class InAioDecode extends AioDecode {

        @Override
        protected Object decode(AioHandlerContext ctx, MChannel channel, ByteBuffer buffer) {
            return null;
        }
    }
}
