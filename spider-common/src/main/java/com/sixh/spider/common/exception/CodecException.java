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
package com.sixh.spider.common.exception;

import java.io.IOException;

/**
 * CodecException.
 * <p>
 * 对于通讯编解码相关的异常信息.
 * <p>
 * 18-12-5下午2:51
 *
 * @author chenbin sixh
 */
public class CodecException extends IOException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Codec exception.
     */
    public CodecException() {
    }

    /**
     * Instantiates a new Codec exception.
     *
     * @param message the message
     */
    public CodecException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Codec exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public CodecException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Codec exception.
     *
     * @param cause the cause
     */
    public CodecException(Throwable cause) {
        super(cause);
    }
}
