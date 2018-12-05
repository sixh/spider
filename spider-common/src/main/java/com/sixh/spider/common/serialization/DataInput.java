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
package com.sixh.spider.common.serialization;

import java.io.IOException;

/**
 * Data input.
 */
public interface DataInput {

    /**
     * Read boolean.
     *
     * @return boolean. boolean
     * @throws IOException the io exception
     */
    boolean readBool() throws IOException;

    /**
     * Read byte.
     *
     * @return byte value.
     * @throws IOException the io exception
     */
    byte readByte() throws IOException;

    /**
     * Read short integer.
     *
     * @return short. short
     * @throws IOException the io exception
     */
    short readShort() throws IOException;

    /**
     * Read integer.
     *
     * @return integer. int
     * @throws IOException the io exception
     */
    int readInt() throws IOException;

    /**
     * Read long.
     *
     * @return long. long
     * @throws IOException the io exception
     */
    long readLong() throws IOException;

    /**
     * Read float.
     *
     * @return float. float
     * @throws IOException the io exception
     */
    float readFloat() throws IOException;

    /**
     * Read double.
     *
     * @return double. double
     * @throws IOException the io exception
     */
    double readDouble() throws IOException;

    /**
     * Read UTF-8 string.
     *
     * @return string. string
     * @throws IOException the io exception
     */
    String readUTF() throws IOException;

    /**
     * Read byte array.
     *
     * @return byte array.
     * @throws IOException the io exception
     */
    byte[] readBytes() throws IOException;
}