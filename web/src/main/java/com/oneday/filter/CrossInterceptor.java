package com.oneday.filter;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/12/13 16:00
 */
public class CrossInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        response.addHeader("Access-Control-Allow-Origin", "*");
//        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
//
//            //Add CORS "pre-flight" request
//            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
////            response.addHeader("Access-Control-Allow-Headers", "Authentication-token");
//            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
//            response.addHeader("Access-Control-Max-Age", "3600");
//        }
        return super.preHandle(request, response, handler);
    }
}
