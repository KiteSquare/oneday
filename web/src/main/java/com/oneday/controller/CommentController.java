package com.oneday.controller;

import com.alibaba.fastjson.JSONObject;
import com.oneday.common.domain.Result;
import com.oneday.constant.ConfigConstant;
import com.oneday.constant.ErrorCodeEnum;
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

/**
 * 搜索相关
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2017/1/19 15:33
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController extends BaseController {
    private static Logger logger = LogHelper.TOPIC_LOG;
    @Resource
    private CommentService commentService;
    /**
     * 添加-评论
     * @param request
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public Result add(@RequestBody AddCommentRequest request) {
        Result result = new Result();
        String log = String.format("/comment/add request: %s ", JSONObject.toJSONString(request));
        try {
            _checkParam(request);
            result.setData(commentService.add(request));
        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("add comment to topic failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage(ErrorCodeEnum.SYSTEM_EXCEPTION.getValue());
            logger.info(String.format("add comment to topic failed, %s", e.getMessage()), e);
        }
        log += String.format(" result: %s", JSONObject.toJSONString(result));
        logger.info(log);
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
    public Result get(@RequestBody GetCommentsRequest request) {
        Result result = new Result();
        String log = String.format("/comment/get request: %s ", JSONObject.toJSONString(request));
        try {
            _checkParam(request);
            result.setData(commentService.get(request));
        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("get comment of topic failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage(ErrorCodeEnum.SYSTEM_EXCEPTION.getValue());
            logger.info(String.format("get comment of topic failed, %s", e.getMessage()), e);
        }
        log += String.format(" result: %s", JSONObject.toJSONString(result));
        logger.info(log);
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
