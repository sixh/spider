package com.sixh.spider.core.network;

/**
 * NetClient.
 * <p>
 * A generic network client invocation interface definition.
 * http and rpc dubbo．
 * <p>
 * 18-11-30上午11:05
 *
 * @author chenbin sixh
 */
public interface NetClient extends Transport{
    /**
     * open.
     */
    void open();

    /**
     * close.
     */
    void close();

    /**
     * connection.
     */
    void connection();

    /**
     * disconnect.
     */
    void disConeection();
}
