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
package com.sixh.spider.dubbo.rpc;

import lombok.Data;

import java.util.Map;

/**
 * RpcInvocation.
 * <p>
 * 引用dubbo#RpcInvocation
 * <p>
 * 18-12-3下午3:13
 *
 * @author chenbin sixh
 */
@Data
public class RpcInvocation {

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] arguments;

    private Map<String, String> attachments;

    /**
     * Gets attachment.
     *
     * @param key the key
     * @return the attachment
     */
    public String getAttachment(String key) {
        return attachments.get(key);
    }

    /**
     * Sets attachment.
     *
     * @param key   the key
     * @param value the value
     */
    public void setAttachment(String key, String value) {
        attachments.put(key, value);
    }
}
