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

import com.sixh.spider.core.network.MChannel;
import com.sixh.spider.core.network.MFuture;
import io.netty.channel.ChannelFuture;

/**
 * NettyFuture.
 * <p>
 * <p>
 * 18-12-5下午4:01
 *
 * @author chenbin sixh
 */
public class NettyFuture implements MFuture {

    private ChannelFuture future;

    public NettyFuture(ChannelFuture future) {
        this.future = future;
    }

    @Override
    public boolean isSuccessfully() {
        return future.isSuccess();
    }

    @Override
    public Throwable cause() {
        return future.cause();
    }

    @Override
    public MChannel getChannel() {
        return new NettyChannel(future.channel());
    }
}
