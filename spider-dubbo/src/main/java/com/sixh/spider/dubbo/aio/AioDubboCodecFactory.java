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
import com.sixh.spider.core.network.aio.AioDecode;
import com.sixh.spider.core.network.aio.AioEncode;
import com.sixh.spider.core.network.codec.CodecFactory;
import com.sixh.spider.core.network.codec.CodecHandler;
import com.sixh.spider.dubbo.codec.DubboCodec;
import com.sixh.spider.dubbo.netty.NettyClientHandler;
import com.sixh.spider.dubbo.netty.NettyCodecAdapter;
import io.netty.channel.ChannelHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * AioDubboCodecFactory.
 * <p>
 * <p>
 * 18-12-5下午3:57
 *
 * @author chenbin sixh
 */
public class AioDubboCodecFactory implements CodecFactory {
    @Override
    public List<CodecHandler> getCodecs() {
        List<CodecHandler> list = new ArrayList<>();
        URL url = URL.valueOf("dubbo://192.168.1.139:20881/com.calvin.order.api.service.OnlineOrderService?anyhost=true&application=order-provider&bind.ip=192.168.1.139&bind.port=20881&channel.readonly.sent=true&codec=dubbo&dubbo=2.0.2&generic=false&heartbeat=60000&interface=com.calvin.order.api.service.OnlineOrderService&methods=$getOnlineOrderProposeList,$cancelOrder,$sellOut,$buyIn,$generateOrderNo&pid=23151&qos.enable=false&revision=1.0.0&server=netty4&side=provider&timestamp=1543911427507&version=1.0.0");
        AioCodecAdapter adapter = new AioCodecAdapter(new DubboCodec(), url, null);
        list.add(new CodecHandler<AioEncode>() {
            @Override
            public String name() {
                return "encode";
            }

            @Override
            public AioEncode codec() {
                return adapter.getEncode();
            }
        });
        list.add(new CodecHandler<AioDecode>() {
            @Override
            public String name() {
                return "decode";
            }

            @Override
            public AioDecode codec() {
                return adapter.getDecode();
            }
        });
        return list;
    }
}
