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
package com.sixh.spider.dubbo.netty;

import com.sixh.spider.common.URL;
import com.sixh.spider.core.network.netty.NettyChannel;
import com.sixh.spider.dubbo.codec.Codec2;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;
import java.util.List;

/**
 * NettyCodecAdapter.
 *
 * @author chenbin six
 */
public final class NettyCodecAdapter {

    private final ChannelHandler encoder = new InternalEncoder();

    private final ChannelHandler decoder = new InternalDecoder();

    private final Codec2 codec;

    private final URL url;

    private final ChannelHandler handler;

    public NettyCodecAdapter(Codec2 codec, URL url, ChannelHandler handler) {
        this.codec = codec;
        this.url = url;
        this.handler = handler;
    }

    public ChannelHandler getEncoder() {
        return encoder;
    }

    public ChannelHandler getDecoder() {
        return decoder;
    }

    private class InternalEncoder extends MessageToByteEncoder {

        @Override
        protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
            Channel ch = ctx.channel();
            try {
                codec.encode(new NettyChannel(ch), new NettyBackedChannelBuffer(out), msg);
            } finally {
//                NettyChannel.removeChannelIfDisconnected(ch);
            }
        }
    }

    private class InternalDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> out) throws Exception {

            NettyChannel channel = new NettyChannel(ctx.channel());
            Object msg;
            int saveReaderIndex;
            try {
                // decode object.
                do {
                    saveReaderIndex = input.readerIndex();
                    try {
                        msg = codec.decode(channel, new NettyBackedChannelBuffer(input));
                    } catch (IOException e) {
                        throw e;
                    }
                    if (msg == Codec2.DecodeResult.NEED_MORE_INPUT) {
                        input.readerIndex(saveReaderIndex);
                        break;
                    } else {
                        //is it possible to go here ?
                        if (saveReaderIndex == input.readerIndex()) {
                            throw new IOException("Decode without read data.");
                        }
                        if (msg != null) {
                            out.add(msg);
                        }
                    }
                } while (input.isReadable());
            } finally {
//                NettyChannel.removeChannelIfDisconnected(ctx.channel());
            }
        }
    }
}
