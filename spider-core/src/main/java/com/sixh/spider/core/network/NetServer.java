package com.sixh.spider.core.network;

/**
 * NetServer.
 * <p>
 * A generic network server invocation interface definition.
 * * http and rpc dubbo．
 * <p>
 * 18-11-30下午2:51
 *
 * @author chenbin sixh
 */
public interface NetServer extends Transport{

    /**
     * Open.
     */
    void open();

    /**
     * Close.
     */
    void close();

    /**
     * Is open boolean.
     *
     * @return the boolean
     */
    boolean isOpen();
}
