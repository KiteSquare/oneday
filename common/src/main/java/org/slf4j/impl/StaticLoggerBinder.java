package org.slf4j.impl;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.classic.util.ContextSelectorStaticBinder;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.status.StatusUtil;
import ch.qos.logback.core.util.StatusPrinter;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.ILoggerFactory;
import org.slf4j.helpers.Util;
import org.slf4j.spi.LoggerFactoryBinder;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/5/25 16:52
 */

public class StaticLoggerBinder implements LoggerFactoryBinder {
    public static String REQUESTED_API_VERSION = "1.6";
    private static final String DEFAULT_LOG_PATH = "/web/logs";
    private static final String DYNAMIC_LOG_PATH = "dynamic.log.path";
    private static final String CATALINA_BASE = "catalina.base";
    private static final String SLASH = "/";
    static final String NULL_CS_URL = "http://logback.qos.ch/codes.html#null_CS";
    private static StaticLoggerBinder SINGLETON = new StaticLoggerBinder();
    private static Object KEY = new Object();
    private boolean initialized = false;
    private LoggerContext defaultLoggerContext = new LoggerContext();
    private final ContextSelectorStaticBinder contextSelectorBinder = ContextSelectorStaticBinder.getSingleton();

    static {
        SINGLETON.initLogSystemProperty();
        SINGLETON.init();
    }

    private StaticLoggerBinder() {
        this.defaultLoggerContext.setName("default");
    }

    public static StaticLoggerBinder getSingleton() {
        return SINGLETON;
    }

    static void reset() {
        SINGLETON = new StaticLoggerBinder();
        SINGLETON.init();
    }

    void init() {
        try {
            try {
                (new ContextInitializer(this.defaultLoggerContext)).autoConfig();
            } catch (JoranException var2) {
                Util.report("Failed to auto configure default logger context", var2);
            }

            if(!StatusUtil.contextHasStatusListener(this.defaultLoggerContext)) {
                StatusPrinter.printInCaseOfErrorsOrWarnings(this.defaultLoggerContext);
            }

            this.contextSelectorBinder.init(this.defaultLoggerContext, KEY);
            this.initialized = true;
        } catch (Throwable var3) {
            Util.report("Failed to instantiate [" + LoggerContext.class.getName() + "]", var3);
        }

    }

    public ILoggerFactory getLoggerFactory() {
        if(!this.initialized) {
            return this.defaultLoggerContext;
        } else if(this.contextSelectorBinder.getContextSelector() == null) {
            throw new IllegalStateException("contextSelector cannot be null. See also http://logback.qos.ch/codes.html#null_CS");
        } else {
            return this.contextSelectorBinder.getContextSelector().getLoggerContext();
        }
    }

    public String getLoggerFactoryClassStr() {
        return this.contextSelectorBinder.getClass().getName();
    }

    private void initLogSystemProperty() {
        Util.report("============================================");
        Util.report("");
        Util.report("                 初始化日志...");
        Util.report("");
        Util.report("============================================");
        String catalinaBase = System.getProperty("catalina.base");
        Util.report("catalina.base===>" + catalinaBase);
        String[] strArray = StringUtils.split(catalinaBase, "/");
        StringBuilder builder = new StringBuilder("/web/logs");
        if(ArrayUtils.isNotEmpty(strArray) && strArray.length > 1) {
            builder.append("/");
            builder.append(strArray[strArray.length - 2]);
            builder.append("/");
            builder.append(strArray[strArray.length - 1]);
        }

        Util.report("dynamic.log.path===>" + builder.toString());
        System.setProperty("dynamic.log.path", builder.toString());
    }
}