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

import com.sixh.spider.common.serialization.ObjectInput;
import com.sixh.spider.common.serialization.hessian.Hessian2Serialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * ResponseType.
 * <p>
 * 返回数据的类型;
 * <p>
 * 18-12-4下午7:02
 *
 * @author chenbin sixh
 */
public enum ResponseType {

    /**
     * Response with exception response type.
     */
    RESPONSE_WITH_EXCEPTION(0) {
        @Override
        public DubboResult decode(ObjectInput in) throws IOException {
            try {
                Object obj = in.readObject();
                if (!(obj instanceof Throwable)) {
                    throw new IOException("Response data error, expect Throwable, but get " + obj);
                }
                DubboResult result = new DubboResult();
                result.setException((Throwable) obj);
                return result;
            } catch (ClassNotFoundException e) {
                LOGGER.error("Read response data failed.", e);
                throw new IOException("Read response data failed.");
            }
        }
    },
    /**
     * Response value response type.
     */
    RESPONSE_VALUE(1) {
        @Override
        public DubboResult decode(ObjectInput in) throws IOException {
            try {
                return new DubboResult(in.readObject());
            } catch (ClassNotFoundException e) {
                LOGGER.error("Read response data failed.", e);
                throw new IOException("Read response data failed.");
            }
        }
    },
    /**
     * Response null value response type.
     */
    RESPONSE_NULL_VALUE(2) {
        @Override
        public DubboResult decode(ObjectInput in) {
            return new DubboResult();
        }
    },
    /**
     * Response with exception with attachments response type.
     */
    RESPONSE_WITH_EXCEPTION_WITH_ATTACHMENTS(3) {
        @Override
        public DubboResult decode(ObjectInput in) throws IOException {
            try {
                Object obj = in.readObject();
                if (!(obj instanceof Throwable)) {
                    throw new IOException("Response data error, expect Throwable, but get " + obj);
                }
                DubboResult result = new DubboResult();
                result.setException((Throwable) obj);
                result.setAttachments((Map<String, String>) in.readObject(Map.class));
                return result;
            } catch (ClassNotFoundException e) {
                LOGGER.error("Read response data failed.", e);
                throw new IOException("Read response data failed.");
            }
        }
    },
    /**
     * Response value with attachments response type.
     */
    RESPONSE_VALUE_WITH_ATTACHMENTS(4) {
        @Override
        public DubboResult decode(ObjectInput in) throws IOException {
            try {
                DubboResult result = new DubboResult();
                result.setAttachments((Map<String, String>) in.readObject(Map.class));
                return result;
            } catch (ClassNotFoundException e) {
                LOGGER.error("Read response data failed.", e);
                throw new IOException("Read response data failed.");
            }
        }
    },
    /**
     * Response null value with attachments response type.
     */
    RESPONSE_NULL_VALUE_WITH_ATTACHMENTS(5) {
        @Override
        public DubboResult decode(ObjectInput in) throws IOException {
            try {
                DubboResult result = new DubboResult();
                result.setAttachments((Map<String, String>) in.readObject(Map.class));
                return result;
            } catch (ClassNotFoundException e) {
                LOGGER.error("Read response data failed.", e);
                throw new IOException("Read response data failed.");
            }
        }
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseType.class);

    private byte type;

    private static final Map<Byte, ResponseType> valueMap = new HashMap<>();

    static {
        valueMap.put(RESPONSE_WITH_EXCEPTION.getType(), RESPONSE_VALUE);
        valueMap.put(RESPONSE_VALUE.getType(), RESPONSE_VALUE);
        valueMap.put(RESPONSE_NULL_VALUE.getType(), RESPONSE_VALUE);
        valueMap.put(RESPONSE_WITH_EXCEPTION_WITH_ATTACHMENTS.getType(), RESPONSE_VALUE);
        valueMap.put(RESPONSE_VALUE_WITH_ATTACHMENTS.getType(), RESPONSE_VALUE);
        valueMap.put(RESPONSE_NULL_VALUE_WITH_ATTACHMENTS.getType(), RESPONSE_VALUE);
    }

    /**
     * 对response解码.
     *
     * @param in 数据流.
     * @return 对象.
     */
    abstract DubboResult decode(ObjectInput in) throws IOException;

    /**
     * 对response解码.
     *
     * @param is   is;
     * @return 数据.
     */
    public static DubboResult decode(InputStream is) throws IOException {
        if (is == null) {
            new DubboResult();
        }
        ObjectInput in = new Hessian2Serialization()
                .deserialize(null, is);
        byte flag = in.readByte();
        return valueMap.get(flag).decode(in);
    }


    ResponseType(int type) {
        this.type = (byte) type;
    }

    public byte getType() {
        return type;
    }}


