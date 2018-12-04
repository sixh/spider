package com.sixh.spider.common;

import java.util.regex.Pattern;

/**
 * Const.
 * <p>
 * 一些常量的定义.
 * <p>
 * 18-11-30上午11:34
 *
 * @author chenbin sixh
 */
public final class Const {


    /**
     * 以下为Dubbo使用的一些常量，
     * 全部引用dubbo的定义.
     */
    public static final int DEFAULT_PAYLOAD = 8 * 1024 * 1024;
    public static final String PAYLOAD_KEY = "payload";
    public static final String BACKUP_KEY = "backup";
    public static final String DEFAULT_KEY_PREFIX = "default.";
    public static final Pattern COMMA_SPLIT_PATTERN = Pattern
            .compile("\\s*[,]+\\s*");
    public static final String VERSION_KEY = "version";
    public static final String GROUP_KEY = "group";
    public static final String INTERFACE_KEY = "interface";
    public static final String WEIGHT_KEY = "weight";
    public static final int DEFAULT_WEIGHT = 100;
    public static final String TIMESTAMP_KEY = "timestamp";
    public static final String WARMUP_KEY = "warmup";
    public static final int DEFAULT_WARMUP = 10 * 60 * 1000;
    public static final String ANY_VALUE = "*";
    public static final String EMPTY_PROTOCOL = "empty";
    public static final String ADMIN_PROTOCOL = "admin";
    public static final String CLASSIFIER_KEY = "classifier";
    public static final String CATEGORY_KEY = "category";
    public static final String PROVIDERS_CATEGORY = "providers";
    public static final String ENABLED_KEY = "enabled";
    public static final String DISABLED_KEY = "disabled";
    public static final String CHECK_KEY = "check";
    public static final String DEFAULT_PROTOCOL = "dubbo";
    public static final String DUBBO_VERSION_KEY = "dubbo";
    public static final String METHODS_KEY = "methods";
    public final static String PATH_SEPARATOR = "/";
    public static final String CONSUMERS_CATEGORY = "consumers";
    public static final String DEFAULT_CATEGORY = PROVIDERS_CATEGORY;
    public static final String CONFIGURATORS_CATEGORY = "configurators";
    public static final String ROUTERS_CATEGORY = "routers";
    public static final String REMOVE_VALUE_PREFIX = "-";
    public static final String PATH_KEY = "path";
    public static final String DECODE_IN_IO_THREAD_KEY = "decode.in.io";
    public static final boolean DEFAULT_DECODE_IN_IO_THREAD = true;

}
