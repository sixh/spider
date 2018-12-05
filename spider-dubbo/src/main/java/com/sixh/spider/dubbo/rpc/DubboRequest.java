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


import java.util.concurrent.atomic.AtomicLong;

/**
 * 引用dubbo．
 * DubboRequest.
 *
 * @author p
 */
public class DubboRequest {

    /**
     * The constant HEARTBEAT_EVENT.
     */
    public static final String HEARTBEAT_EVENT = null;

    private static final AtomicLong INVOKE_ID = new AtomicLong(0);

    private final long mId;

    private String mVersion;

    private boolean mTwoWay = true;

    private boolean mEvent = false;

    private boolean mBroken = false;

    private Object mData;

    /**
     * Instantiates a new DubboRequest.
     */
    public DubboRequest() {
        mId = newId();
    }

    private static long newId() {
        return INVOKE_ID.incrementAndGet();
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return mId;
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    public String getVersion() {
        return mVersion;
    }

    /**
     * Sets version.
     *
     * @param version the version
     */
    public void setVersion(String version) {
        mVersion = version;
    }

    /**
     * Is two way boolean.
     *
     * @return the boolean
     */
    public boolean isTwoWay() {
        return mTwoWay;
    }

    /**
     * Sets two way.
     *
     * @param twoWay the two way
     */
    public void setTwoWay(boolean twoWay) {
        mTwoWay = twoWay;
    }

    /**
     * Is event boolean.
     *
     * @return the boolean
     */
    public boolean isEvent() {
        return mEvent;
    }

    /**
     * Sets event.
     *
     * @param event the event
     */
    public void setEvent(String event) {
        mEvent = true;
        mData = event;
    }

    /**
     * Is broken boolean.
     *
     * @return the boolean
     */
    public boolean isBroken() {
        return mBroken;
    }

    /**
     * Sets broken.
     *
     * @param mBroken the m broken
     */
    public void setBroken(boolean mBroken) {
        this.mBroken = mBroken;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public Object getData() {
        return mData;
    }

    /**
     * Sets data.
     *
     * @param msg the msg
     */
    public void setData(Object msg) {
        mData = msg;
    }

    /**
     * Is heartbeat boolean.
     *
     * @return the boolean
     */
    public boolean isHeartbeat() {
        return mEvent && HEARTBEAT_EVENT == mData;
    }

    /**
     * Sets heartbeat.
     *
     * @param isHeartbeat the is heartbeat
     */
    public void setHeartbeat(boolean isHeartbeat) {
        if (isHeartbeat) {
            setEvent(HEARTBEAT_EVENT);
        }
    }

    @Override
    public String toString() {
        return "DubboRequest{" +
                "mId=" + mId +
                ", mVersion='" + mVersion + '\'' +
                ", mTwoWay=" + mTwoWay +
                ", mEvent=" + mEvent +
                ", mBroken=" + mBroken +
                ", mData=" + mData +
                '}';
    }
}
