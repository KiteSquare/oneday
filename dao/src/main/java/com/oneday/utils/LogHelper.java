package com.oneday.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/13 17:01
 */
public class LogHelper {
    // 用户统相关调用日志
    public static final Logger USER_LOG = LoggerFactory.getLogger("USER");

    public static final Logger SR_LOG = LoggerFactory.getLogger("STATIC_RESOURCE");
    //帖子
    public static final Logger TOPIC_LOG = LoggerFactory.getLogger("TOPIC");

    public static final Logger WARN_LOG = LoggerFactory.getLogger("WARN");

    public static final Logger ERROR_LOG = LoggerFactory.getLogger("ERROR");
}
