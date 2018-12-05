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

import com.sixh.spider.core.network.MChannel;
import com.sixh.spider.core.network.MFuture;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * AioChannel.
 * <p>
 * aio 调用.
 * <p>
 * 18-12-5下午3:10
 *
 * @author chenbin sixh
 */
public class AioChannel implements MChannel {

    private AsynchronousSocketChannel channel;

    public AioChannel(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public SocketAddress remoteAddress() {
        try {
            return channel.getRemoteAddress();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public SocketAddress localAddress() {
        try {
            return channel.getLocalAddress();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean isConnected() {
        return channel.isOpen();
    }

    @Override
    public boolean isOpened() {
        return channel.isOpen();
    }

    @Override
    public boolean isClose() {
        return !channel.isOpen();
    }

    @Override
    public MFuture close() {
        try {
            channel.close();
        } catch (IOException e) {
        }
        return null;
    }

    @Override
    public MFuture send(Object message) {
        return channel.write(message);
    }
}
