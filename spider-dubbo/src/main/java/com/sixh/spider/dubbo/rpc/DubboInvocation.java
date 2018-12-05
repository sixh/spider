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

import lombok.Getter;

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
@Getter
public class DubboInvocation {

    private String methodName;

    private Class<?>[] parameterTypes;
    /**
     * 属
     */
    private Object[] arguments;

    /**
     * 属性值.
     */
    private Map<String, String> attachments;


    /**
     * Sets arguments.
     *
     * @param arguments the arguments
     * @return the arguments
     */
    public DubboInvocation setArguments(Object[] arguments) {
        this.arguments = arguments;
        return this;
    }

    /**
     * Sets method name.
     *
     * @param methodName the method name
     * @return the method name
     */
    public DubboInvocation setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    /**
     * Sets parameter types.
     *
     * @param parameterTypes the parameter types
     * @return the parameter types
     */
    public DubboInvocation setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
        return this;
    }

    /**
     * Sets attachments.
     *
     * @param attachments the attachments
     * @return the attachments
     */
    public DubboInvocation setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
        return this;
    }

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
     * @return the dubbo invocation
     */
    public DubboInvocation putAttachment(String key, String value) {
        attachments.put(key, value);
        return this;
    }
}
