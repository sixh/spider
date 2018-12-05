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
import java.lang.reflect.Type;

/**
 * Object input.
 * @author p
 */
public interface ObjectInput extends DataInput {

    /**
     * read object.
     *
     * @return object. object
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    Object readObject() throws IOException, ClassNotFoundException;

    /**
     * read object.
     *
     * @param <T> the type parameter
     * @param cls object type.
     * @return object. t
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException;

    /**
     * read object.
     *
     * @param <T>  the type parameter
     * @param cls  object type.
     * @param type the type
     * @return object. t
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException;

}