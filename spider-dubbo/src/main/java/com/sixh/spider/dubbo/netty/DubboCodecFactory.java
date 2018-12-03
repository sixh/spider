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

import com.sixh.spider.core.network.codec.Codec;
import com.sixh.spider.core.network.codec.CodecFactory;
import com.sixh.spider.dubbo.codec.DubboCodec;
import io.netty.channel.ChannelHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * DubboCodecFactory.
 * <p>
 * <p>
 * 18-11-30下午4:47
 *
 * @author chenbin sixh
 */
public class DubboCodecFactory implements CodecFactory{

    @Override
    public List<Codec> getCodecs() {
        List<Codec> list = new ArrayList<>();
        NettyCodecAdapter adapter = new NettyCodecAdapter(new DubboCodec(), null, null);
        list.add(new Codec<ChannelHandler>() {
            @Override
            public String name() {
                return "encode";
            }

            @Override
            public ChannelHandler codec() {
                return adapter.getEncoder();
            }
        });
        list.add(new Codec<ChannelHandler>() {
            @Override
            public String name() {
                return "decode";
            }

            @Override
            public ChannelHandler codec() {
                return adapter.getDecoder();
            }
        });
        list.add(new Codec<ChannelHandler>() {
            @Override
            public String name() {
                return "handler";
            }

            @Override
            public ChannelHandler codec() {
                return new NettyServerHandler();
            }
        });
        return list;
    }
}
