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
package com.sixh.spider.core.network.netty;

import com.sixh.spider.core.network.AbstractNetClient;
import com.sixh.spider.core.network.MChannel;
import com.sixh.spider.core.network.codec.CodecHandler;
import com.sixh.spider.core.network.codec.CodecFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * NettyClient.
 * <p>
 * netty client 实现.
 * <p>
 * 18-11-30下午3:32
 *
 * @author chenbin sixh
 */
public class NettyClient extends AbstractNetClient {

    private Bootstrap bootstrap;

    private EpollEventLoopGroup group = new EpollEventLoopGroup(8);

    private io.netty.channel.Channel channel;

    /**
     * Instantiates a new Abstract net client.
     *
     * @param codec the codec
     */
    public NettyClient(CodecFactory codec) {
        super(codec);
    }

    @Override
    protected MChannel getChannel() {
        return new NettyChannel(channel);
    }

    @Override
    protected void doOpen() {
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.ALLOCATOR, ByteBufAllocator.DEFAULT)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .channel(EpollSocketChannel.class);
        bootstrap.handler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) {

                for (CodecHandler<ChannelHandler> codec : codec().getCodecs()) {
                    System.out.println("初始化到这里了.............."+codec.name());
                    ch.pipeline().addLast(codec.name(), codec.codec());
                }
            }
        });
    }

    @Override
    protected void doConnection() {
        ChannelFuture future = bootstrap.connect(getAddress());
        //        if (connect.isDone()) {
        boolean ret = future.awaitUninterruptibly(3000, TimeUnit.MILLISECONDS);
        if(ret && future.isSuccess()) {
            channel = future.channel();
        }
//        }
    }
}
