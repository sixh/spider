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
package com.sixh.spider.dubbo;

import com.sixh.spider.core.network.aio.AioClient;
import com.sixh.spider.core.network.netty.NettyClient;
import com.sixh.spider.dubbo.aio.AioDubboCodecFactory;
import com.sixh.spider.dubbo.netty.NettyDubboCodecFactory;
import com.sixh.spider.dubbo.rpc.DubboInvocation;
import com.sixh.spider.dubbo.rpc.DubboRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Main2.
 * <p>
 * <p>
 * 18-12-6下午3:46
 *
 * @author chenbin sixh
 */
public class Main2 {
    public static void main(String[] args) {
        AioClient client = new AioClient(new AioDubboCodecFactory());
        client.setAddress("192.168.1.139");
        client.setPort(20881);
        DubboRequest request = new DubboRequest();
        request.setBroken(false);
        request.setTwoWay(true);
        request.setVersion("2.0.2");
        DubboInvocation invocation = new DubboInvocation();
        invocation.setMethodName("$invoke");
        Class<?>[] parameterTypes = {String.class, String[].class, Object[].class};
        invocation.setParameterTypes(parameterTypes);
        Map<String,Object> values = new HashMap<>();
        values.put("volume",1);
        values.put("orderNo","123333");
        values.put("source","WEB");
        values.put("price",1.25);
        values.put("mainCoinSymbol","USDT");
        values.put("otherCoinSymbol","LOOM");
//        values.put("orderNo","123333");
        String [] arg1 = {"com.proxy.default"};
        Object [] arg2 = {values};
        Object[] arguments = {"sellOut",arg1,arg2};
        invocation.setArguments(arguments);
        Map<String,String> attrachments = new HashMap<>();
        attrachments.put("path","com.calvin.order.api.service.OnlineOrderService");
        attrachments.put("id","１");
        attrachments.put("interface","com.calvin.order.api.service.OnlineOrderService");
        attrachments.put("version","1.0.0");
        attrachments.put("generic","true");
        attrachments.put("timeout","3000");
        invocation.setAttachments(attrachments);
        request.setData(invocation);
        request.setHeartbeat(false);
        client.open();
        client.connection();
        client.send(request);
    }
}
