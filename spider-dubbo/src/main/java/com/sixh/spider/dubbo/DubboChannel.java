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

import com.sixh.spider.common.URL;
import com.sixh.spider.core.network.Channel;

/**
 * DubboChannel.
 * <p>
 *     dubbo相关的通道;
 * <p>
 * 18-12-4下午5:02
 *
 * @author chenbin sixh
 */
public class DubboChannel {
    /**
     * 网络通道;
     */
    private Channel channel;

    /**
     * 网络注册地址;
     */
    private URL url;

    public DubboChannel(Channel channel, URL url) {
        this.channel = channel;
        this.url = url;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
