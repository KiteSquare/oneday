
package com.oneday.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.oneday.common.domain.Result;
import com.oneday.common.util.ResponseUtil;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.constant.HttpKeyEnum;
import com.oneday.constant.StatusEnum;
import com.oneday.domain.vo.BaseUser;
import com.oneday.exceptions.OndayException;
import com.oneday.exceptions.OnedaySystmException;
import com.oneday.utils.AccessTokenUtil;
import com.oneday.utils.LogHelper;
import org.slf4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 请求合法性检查入口
 * //TODO 目前只校验是否登录，后续如有更多的校验逻辑，可做扩展，
 * Created by chender on 2017/12/04.
 */
public final class RequestCheckFilter extends OncePerRequestFilter {


    private static final Logger errorLogger = LogHelper.ERROR_LOG;

    private static final Logger warnLogger = LogHelper.WARN_LOG;

    private List<String> ignore;

    private Map<String, Integer> ignoreMap;

    @PostConstruct
    public void init() {
        if (ignore != null && ignore.size() != 0) {
            ignoreMap = new HashMap<>();
            for (String path : ignore) {
                ignoreMap.put(path, 1);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        exceptionResove(request, response, filterChain);
    }

    /**
     * 统一异常处理
     *
     * @param request
     * @param response
     * @param filterChain
     */
    private void exceptionResove(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String url = request.getRequestURI();
            if (ignoreMap.containsKey(url)) {
                filterChain.doFilter(request, response);
            }
            Result result = checkRequest(request);
            if (result == null) {
                filterChain.doFilter(request, response);
            } else {
                ResponseUtil.responseJson(response, JSON.toJSONString(result));
            }
        } catch (OnedaySystmException e) {
            Result result = Result.systemFailure(e.getCode(), e.getMessage());
            ResponseUtil.responseJson(response, JSON.toJSONString(result));
            errorLogger.error(e.getMessage(), e);
        } catch (OndayException e) {
            Result result = Result.bizFailure(e.getCode(), e.getMessage());
            ResponseUtil.responseJson(response, JSON.toJSONString(result));
            warnLogger.warn(e.getMessage(), e);
        } catch (Exception e) {
            Result result = Result.bizFailure(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode(), ErrorCodeEnum.SYSTEM_EXCEPTION.getValue());
            ResponseUtil.responseJson(response, JSON.toJSONString(result));
            errorLogger.error("未知异常", e);
        }
    }

    /**
     * 请求校验
     *
     * @param request
     * @return 校验通过时返回null，不通过时返回对应的Result
     */
    private Result checkRequest(HttpServletRequest request) {
        //检查是否登录
        String token = request.getHeader(HttpKeyEnum.HTTPHEADTOKEN.getKey());
        if (token == null) {
            return Result.systemFailure(StatusEnum.NEEDLOGIN.getCode(), ErrorCodeEnum.USER_NOT_LOGIN_ERROR.getValue());
        }
        try {
            BaseUser user = AccessTokenUtil.decryptAccessToken(token);
            request.setAttribute(HttpKeyEnum.REQUESTATTIBUTERUSER.getKey(), user);
        } catch (OndayException e) {
            return Result.systemFailure(StatusEnum.NEEDLOGIN.getCode(), e.getMessage());
        }
        return null;
    }

    public List<String> getIgnore() {
        return ignore;
    }

    public void setIgnore(List<String> ignore) {
        this.ignore = ignore;
    }
}
