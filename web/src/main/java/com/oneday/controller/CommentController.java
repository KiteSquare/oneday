package com.oneday.controller;

import com.alibaba.fastjson.JSONObject;
import com.oneday.common.domain.Result;
import com.oneday.constant.ConfigConstant;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.po.Comment;
import com.oneday.domain.vo.CommentDetail;
import com.oneday.domain.vo.Page;
import com.oneday.domain.vo.request.AddCommentRequest;
import com.oneday.domain.vo.request.GetCommentsRequest;
import com.oneday.exceptions.OndayException;
import com.oneday.service.CommentService;
import com.oneday.utils.LogHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 搜索相关
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2017/1/19 15:33
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController extends BaseController {
    @Resource
    private CommentService commentService;
    /**
     * 添加-评论
     * @param request
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public Object add(@RequestBody AddCommentRequest request, HttpServletRequest httpServletRequest) {
        LogHelper.TOPIC_LOG.info(String.format("/comment/add request: %s ",JSONObject.toJSONString(request)));
        _checkParam(request);
        Object result = Result.success(commentService.add(request, getUser(httpServletRequest)));
        LogHelper.TOPIC_LOG.info(String.format("/comment/add response: %s ",JSONObject.toJSONString(result)));
        return result;
    }
    protected boolean _checkParam(AddCommentRequest request) {
        if (request == null) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "request is empty");
        }
        if (StringUtils.isEmpty(request.getAccessToken())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "accessToken is required");
        }
        if (request.getTopicId() == null) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "topic is required");
        }
        if (StringUtils.isEmpty(request.getContent())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "content is required");
        }
        return true;
    }

    @RequestMapping(value = "/get", method = {RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public Object get(@RequestBody GetCommentsRequest request, HttpServletRequest httpServletRequest) {
        LogHelper.TOPIC_LOG.info(String.format("/comment/get request: %s ",JSONObject.toJSONString(request)));
        _checkParam(request);
        Object result = Result.success(commentService.get(request, getUser(httpServletRequest)));
        LogHelper.TOPIC_LOG.info(String.format("/comment/get response: %s ",JSONObject.toJSONString(result)));
        return result;
    }

    protected boolean _checkParam(GetCommentsRequest request) {
        if (request == null) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "request is empty");
        }
        if (request.getTopicId() == null) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "topic is required");
        }
        if (request.getCurrentPage() == null || request.getCurrentPage() <= 0){
            request.setCurrentPage(1);
        }
        if (request.getCurrentPage() > ConfigConstant.PAGE_MAX) {
            request.setCurrentPage(ConfigConstant.PAGE_MAX);
        }
        if (request.getPageNum() == null || request.getPageNum() <= 0 ) {
            request.setPageNum(ConfigConstant.PAGE_NUM_DEFAULT);
        }
        if (request.getPageNum() > ConfigConstant.PAGE_NUM_MAX) {
            request.setPageNum(ConfigConstant.PAGE_NUM_MAX);
        }
        return true;
    }
}
