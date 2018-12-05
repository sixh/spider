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
import com.sixh.spider.core.network.MChannelHandler;
import com.sixh.spider.dubbo.codec.Codec2;

/**
 * AioCodecAdapter.
 * <p>
 * <p>
 * 18-12-5下午3:57
 *
 * @author chenbin sixh
 */
public class AioCodecAdapter {

    private AioDecode decode = new AioDecode();
    private AioEncode encode = new AioEncode();
    private Codec2 codec;

    private URL url;

    private MChannelHandler handler;

    /**
     * Instantiates a new Aio codec adapter.
     *
     * @param codec   the codec
     * @param url     the url
     * @param handler the handler
     */
    public AioCodecAdapter(Codec2 codec, URL url, MChannelHandler handler) {
        this.codec = codec;
        this.url = url;
        this.handler = handler;
    }

    public class AioEncode {

    }

    public class AioDecode {

    }
}
