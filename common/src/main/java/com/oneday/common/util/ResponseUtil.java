package com.oneday.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * http响应工具类
 * Created by chender on 2017/12/04.
 */
public final class ResponseUtil {

    private ResponseUtil() {

    }

    public static void responseJson(HttpServletResponse response, String json) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(json);
            writer.flush();
        } catch (Exception e) {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
