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

import java.util.HashMap;
import java.util.Map;

/**
 * DubboResult.
 * <p>
 * dubbo返回结果集.
 * <p>
 * 18-12-4下午6:50
 *
 * @author chenbin sixh
 */
public class DubboResult {

    private Map<String, String> attachments = new HashMap<>();

    private Object result;

    private Throwable exception;

    /**
     * Instantiates a new Dubbo result.
     */
    public DubboResult() {

    }

    /**
     * Instantiates a new Dubbo result.
     *
     * @param result the result
     */
    DubboResult(Object result) {
        this.result = result;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(Object value) {
        this.result = value;
    }

    /**
     * Get value object.
     *
     * @return the object
     */
    public Object getValue() {
        return result;
    }

    /**
     * Gets attachments.
     *
     * @return the attachments
     */
    public Map<String, String> getAttachments() {
        return attachments;
    }

    /**
     * Sets attachments.
     *
     * @param attachments the attachments
     * @return the attachments
     */
    public DubboResult setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
        return this;
    }

    /**
     * Gets exception.
     *
     * @return the exception
     */
    public Throwable getException() {
        return exception;
    }

    /**
     * Sets exception.
     *
     * @param exception the exception
     * @return the exception
     */
    public DubboResult setException(Throwable exception) {
        this.exception = exception;
        return this;
    }
    /**
     * 生成一个空的对象.
     *
     * @return dubboResult. dubbo result
     */
    public static DubboResult empty() {
        return new DubboResult();
    }

    @Override
    public String toString() {
        return "DubboResult{" +
                "attachments=" + attachments +
                ", result=" + result +
                ", exception=" + exception +
                '}';
    }
}
