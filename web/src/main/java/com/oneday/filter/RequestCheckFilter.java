
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.SessionRepositoryFilter;

/**
 * 请求合法性检查入口
 * //TODO 目前只校验是否登录，后续如有更多的校验逻辑，可做扩展，
 * Created by chender on 2017/12/04.
 */
public final class RequestCheckFilter extends SessionRepositoryFilter {


    private static final Logger errorLogger = LogHelper.ERROR_LOG;

    private static final Logger warnLogger = LogHelper.WARN_LOG;

    private CookieSerializer cookieSerializer;

    private List<String> ignore;

    private Map<String, Integer> ignoreMap;

    public RequestCheckFilter(SessionRepository sessionRepository) {
        super(sessionRepository);
    }

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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,final FilterChain filterChain) throws ServletException, IOException {
       //父类会做会话代理，所以需要通过这种方式拿到代理后的request
        super.doFilterInternal(request, response, new FilterChain() {
           @Override
           public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
               exceptionResove((HttpServletRequest)servletRequest, (HttpServletResponse)servletResponse, filterChain);
           }
       });
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
                filterChain.doFilter(request,response);
                return;
            }
            Result result = checkRequest(request);
            if (result == null) {
                filterChain.doFilter(request,response);
            } else {
                ResponseUtil.responseJson(response, JSON.toJSONString(result));
            }
        } catch (Throwable e) {
            if (e.getCause() instanceof OnedaySystmException) {
                OnedaySystmException onedaySystmException = (OnedaySystmException)e.getCause();
                Result result = Result.systemFailure(onedaySystmException.getCode(), onedaySystmException.getMessage());
                ResponseUtil.responseJson(response, JSON.toJSONString(result));
                LogHelper.ERROR_LOG.error(e.getMessage(), e);
            } else if (e.getCause() instanceof OndayException) {
                OndayException ondayException = (OndayException) e.getCause();
                Result result = Result.bizFailure(ondayException.getCode(), ondayException.getMessage());
                ResponseUtil.responseJson(response, JSON.toJSONString(result));
                LogHelper.WARN_LOG.warn(e.getMessage(), e);
            } else {
                Result result = Result.systemFailure(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode(), ErrorCodeEnum.SYSTEM_EXCEPTION.getValue());
                ResponseUtil.responseJson(response, JSON.toJSONString(result));
                LogHelper.ERROR_LOG.error("未知异常", e);
            }
        }
    }

    /**
     * 请求校验
     *
     * @param request
     * @return 校验通过时返回null，不通过时返回对应的Result
     */
    private Result checkRequest(HttpServletRequest request) {
        BaseUser user=(BaseUser)request.getSession().getAttribute(HttpKeyEnum.SESSIONTATTIBUTERUSER.getKey());
        if(user==null){
            return Result.systemFailure(StatusEnum.NEEDLOGIN.getCode(),StatusEnum.NEEDLOGIN.getName());
        }
        return null;
    }

    public List<String> getIgnore() {
        return ignore;
    }

    public void setIgnore(List<String> ignore) {
        this.ignore = ignore;
    }

    public void setCookieSerializer(CookieSerializer cookieSerializer) {
        this.cookieSerializer = cookieSerializer;
        CookieHttpSessionStrategy strategy=new CookieHttpSessionStrategy();
        strategy.setCookieSerializer(cookieSerializer);
        super.setHttpSessionStrategy(strategy);
    }
}
