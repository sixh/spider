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
import com.sixh.spider.core.network.codec.CodecFactory;

import java.io.IOException;
import java.net.StandardSocketOptions;
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

    private AsynchronousSocketChannel client;

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
        return new AioChannel(client);
    }

    @Override
    protected void doOpen() {
        try {
            client = AsynchronousSocketChannel.open();
            client.setOption(StandardSocketOptions.TCP_NODELAY, true);
            client.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
            client.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doConnection() {
        try {
            client.connect(getAddress(), null, new CompletionHandler<Void, Object>() {
                @Override
                public void completed(Void result, Object attachment) {
                    System.out.println("连接成功!");
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("连接失败!");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
