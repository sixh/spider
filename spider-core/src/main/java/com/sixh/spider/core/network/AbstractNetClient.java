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
package com.sixh.spider.core.network;

import com.sixh.spider.core.network.codec.CodecFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * AbstractNetClient.
 * <p>
 * <p>
 * 18-11-30下午3:16
 *
 * @author chenbin sixh
 */
public abstract class AbstractNetClient implements NetClient {

    /**
     * 一个可定义的codec转换器.
     */
    private CodecFactory codec;

    private String address;

    private Integer port;

    /**
     * Instantiates a new Abstract net client.
     *
     * @param codec the codec
     */
    public AbstractNetClient(CodecFactory codec) {
        this.codec = codec;
    }

    @Override
    public void open() {
        doOpen();
    }

    @Override
    public void close() {

    }

    @Override
    public void connection() {
        doConnection();
    }

    @Override
    public void disConnection() {

    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets port.
     *
     * @param port the port
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * CodecHandler codec factory.
     *
     * @return the codec factory
     */
    protected CodecFactory codec() {
        return codec;
    }

    @Override
    public MFuture send(Object message) {
        if (message != null) {
            if (usableness()) {
                return getChannel().send(message);
            }
        }
        return null;
    }

    /**
     * 通道是可用的判断.
     *
     * @return boolean boolean
     */
    public boolean usableness() {
        return getChannel().isOpened();
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public SocketAddress getAddress() {
        return new InetSocketAddress(address, port);
    }

    /**
     * Gets MChannel.
     *
     * @return MChannel MChannel
     */
    protected abstract MChannel getChannel();

    /**
     * Do open.
     */
    protected abstract void doOpen();

    /**
     * Do connection.
     */
    protected abstract void doConnection();
}
