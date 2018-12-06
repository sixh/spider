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

import com.sixh.spider.core.network.AbstractNetClient;
import com.sixh.spider.core.network.MChannel;
import com.sixh.spider.core.network.MFuture;
import com.sixh.spider.core.network.codec.CodecFactory;
import com.sixh.spider.core.network.codec.CodecHandler;
import io.netty.channel.ChannelHandler;

import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * AioClient.
 * <p>
 * <p>
 * 18-11-30下午3:33
 *
 * @author chenbin sixh
 */
public class AioClient extends AbstractNetClient {

    private AioClientHoder hoder;

    private MChannel channel;

    /**
     * Instantiates a new Abstract net client.
     *
     * @param codec the codec
     */
    public AioClient(CodecFactory codec) {
        super(codec);
    }

    @Override
    protected MChannel getChannel() {
        return channel;
    }

    @Override
    protected void doOpen() {
        hoder = new AioClientHoder();
        hoder.option(StandardSocketOptions.TCP_NODELAY, true);
        hoder.option(StandardSocketOptions.SO_KEEPALIVE, true);
        hoder.option(StandardSocketOptions.SO_REUSEADDR, true);
        hoder.handler(new AioInitChannelHandler() {
            @Override
            void initChannel(AioChannel channel) {
                for (CodecHandler<AioHandler> codec : codec().getCodecs()) {
                    System.out.println("初始化到这里了.............."+codec.name());
                    channel.pipeline().addLast(codec.name(), codec.codec());
                }
            }
        });
    }

    @Override
    protected void doConnection() {
        try {
            MFuture connection = hoder.connection(getAddress());
            if (connection.isSuccessfully()) {
                channel = connection.getChannel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
