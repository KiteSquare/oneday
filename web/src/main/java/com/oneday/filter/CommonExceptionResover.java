package com.oneday.filter;

import com.alibaba.fastjson.JSON;
import com.oneday.common.domain.Result;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CommonExceptionResover implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        PrintWriter writer = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            writer = response.getWriter();
            Result result = Result.systemFailure(Result.STATUS_EXCEPTION, "系统异常");//TODO 根据不同的异常做不同的处理?
            writer.write(JSON.toJSONString(result));
        } catch (IOException e1) {
            e1.printStackTrace();
        }finally {
            if (writer != null) {
                writer.close();
            }
        }
        return null;
    }
}
