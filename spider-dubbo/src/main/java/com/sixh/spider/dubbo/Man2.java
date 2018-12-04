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

import com.sixh.spider.common.utils.Bytes;

import java.nio.ByteBuffer;

/**
 * Man2.
 * <p>
 * <p>
 * 18-12-4下午5:56
 *
 * @author chenbin sixh
 */
public class Man2 {
    public static void main(String[] args) {
        int off = 0;
//        1101(13)  00001110(14)  00000010(2)

        System.out.println((byte)855554);
        byte[] b = Bytes.long2bytes(855554);
        long l =  ((b[off + 7] & 0xFFL) << 0) +
                ((b[off + 6] & 0xFFL) << 8) +
                ((b[off + 5] & 0xFFL) << 16) +
                ((b[off + 4] & 0xFFL) << 24) +
                ((b[off + 3] & 0xFFL) << 32) +
                ((b[off + 2] & 0xFFL) << 40) +
                ((b[off + 1] & 0xFFL) << 48) +
                (((long) b[off + 0]) << 56);
        System.out.println(l);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8).putInt(855554);
        byte[] array = byteBuffer.array();
        System.out.println(array);
    }
}
