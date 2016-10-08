package com.oneday.controller;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/13 17:08
 */
public abstract class BaseController {
    Logger LOGGER = Logger.getLogger(BaseController.class);
    protected HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    protected String getRemoteIp() {
        HttpServletRequest request = this.getRequest();

        try {
            String e = request.getHeader("x-forwarded-for");
            if(e == null || e.length() == 0 || "unknown".equalsIgnoreCase(e)) {
                e = request.getHeader("Proxy-Client-IP");
            }

            if(e == null || e.length() == 0 || "unknown".equalsIgnoreCase(e)) {
                e = request.getHeader("WL-Proxy-Client-IP");
            }

            if(e == null || e.length() == 0 || "unknown".equalsIgnoreCase(e)) {
                e = request.getHeader("X-Real-IP");
            }

            if(e == null || e.length() == 0 || "unknown".equalsIgnoreCase(e)) {
                e = request.getRemoteAddr();
            }

            LOGGER.info("proxy-ip=" + e);
            if(e != null && e.length() != 0 && !"unknown".equalsIgnoreCase(e)) {
                int dotIdx = e.indexOf(",");
                String[] ipToken;
                if(dotIdx == -1) {
                    ipToken = e.split(" ");
                    if(ipToken.length > 1) {
                        return ipToken[1];
                    }
                } else {
                    ipToken = e.split(",");
                    if(ipToken.length > 1) {
                        return ipToken[0];
                    }
                }

                LOGGER.info("user ip=" + e);
                return e;
            }
        } catch (Exception var5) {
            LOGGER.error(var5.getMessage(), var5);
        }

        return "";
    }
}
