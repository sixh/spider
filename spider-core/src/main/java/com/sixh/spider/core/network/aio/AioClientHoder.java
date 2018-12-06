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

import com.sixh.spider.core.network.MFuture;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * AioClientHoder.
 * <p>
 * aio处理器.
 * <p>
 * 18-12-6上午10:54
 *
 * @author chenbin sixh
 */
public class AioClientHoder {

    private AsynchronousSocketChannel channel;

    private Executor boss;

    private Map<SocketOption, Object> options = new HashMap<>();

    private AioInitChannelHandler handler;

    /**
     * Instantiates a new Aio client hoder.
     */
    public AioClientHoder() {
        init();
    }

    private void init() {
        boss = Executors.newFixedThreadPool(4);
    }

    /**
     * Group aio client hoder.
     *
     * @param g the g
     * @return the aio client hoder
     */
    public AioClientHoder group(Executor g) {
        this.boss = g;
        return this;
    }

    /**
     * Option aio client hoder.
     *
     * @param o     the o
     * @param value the value
     * @return the aio client hoder
     */
    public AioClientHoder option(SocketOption o, Object value) {
        options.put(o, value);
        return this;
    }

    public AioClientHoder handler(AioInitChannelHandler handler) {
        this.handler = handler;
        return this;
    }


    public MFuture connection(SocketAddress address) throws IOException {
        AioChannel aioChannel = initAndRegister();
        options.forEach((k, v) -> {
            try {
                channel.setOption(k, v);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        channel.connect(address);
        return new AioFuture(aioChannel);
    }

    private AioChannel initAndRegister() throws IOException {
        channel = AsynchronousSocketChannel.open();
        AioChannel aioChannel = new AioChannel(channel);
        handler.initChannel(aioChannel);
        return aioChannel;
    }
}
