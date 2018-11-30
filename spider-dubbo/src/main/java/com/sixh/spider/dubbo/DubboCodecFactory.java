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

import com.sixh.spider.core.network.codec.Codec;
import com.sixh.spider.core.network.codec.CodecFactory;

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
public class DubboCodecFactory implements CodecFactory {

    @Override
    public List<Codec> getCodecs() {
        List<Codec> list = new ArrayList<>();
        list.add(new Codec() {
            @Override
            public String name() {
                return "encode";
            }

            @Override
            public <T> T codec() {
                return null;
            }
        });

        return null;
    }
}
