package com.oneday.controller;

import com.alibaba.fastjson.JSONObject;
import com.oneday.common.domain.Result;
import com.oneday.constant.ConfigConstant;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.Page;
import com.oneday.domain.vo.request.CreateTopicRequest;
import com.oneday.domain.vo.request.GetTopicRequest;
import com.oneday.domain.vo.request.RecommendRequest;
import com.oneday.domain.vo.request.RecommendTopicRequest;
import com.oneday.exceptions.OndayException;
import com.oneday.service.TopicService;
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
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/21 15:00
 */
@Controller
@RequestMapping(value = "/topic")
public class TopicController extends BaseController {
    private static Logger logger = LogHelper.TOPIC_LOG;
    @Resource
    private TopicService topicService;

    /**
     * 创建帖子
     * @param request
     * @return
     */
    @RequestMapping(value = "/create", method = {RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public Result create(@RequestBody CreateTopicRequest request) {
        Result result = new Result();
        String log = String.format("/topic/create request: %s ", JSONObject.toJSONString(request));
        try {
            _checkParam(request);
            topicService.create(request);
        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("create topic failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage(ErrorCodeEnum.SYSTEM_EXCEPTION.getValue());
            logger.info(String.format("create topic failed, %s", e.getMessage()), e);
        }
        log += String.format(" result: %s", JSONObject.toJSONString(result));
        logger.info(log);
        return result;
    }

    protected boolean _checkParam(CreateTopicRequest request) {
        if (request == null) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "request is empty");
        }
        if (StringUtils.isEmpty(request.getAccessToken())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "accessToken is required");
        }
        if (StringUtils.isEmpty(request.getTitle())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "title is required");
        }
        if (StringUtils.isEmpty(request.getContent())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "content is required");
        }
        if (request.getCategory() == null || request.getCategory() <= 0 ) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "category is empty or invalid");
        }

        return true;
    }

    /**
     * 查询帖子
     * @param request
     * @return
     */
    @RequestMapping(value = "/get", method = {RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public Result get(@RequestBody GetTopicRequest request) {
        Result result = new Result();
        String log = String.format("/topic/get request: %s ", JSONObject.toJSONString(request));
        try {
            _checkParam(request);
            result.setData(topicService.get(request));
        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("get topic failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage(ErrorCodeEnum.SYSTEM_EXCEPTION.getValue());
            logger.info(String.format("get topic failed, %s", e.getMessage()), e);
        }
        log += String.format(" result: %s", JSONObject.toJSONString(result));
        logger.info(log);
        return result;
    }

    protected boolean _checkParam(GetTopicRequest request) {
        if (request == null) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "request is empty");
        }
        if (request.getId() == null || request.getId() <= 0 ) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "category is empty or invalid");
        }

        return true;
    }

    /**
     * 查询帖子
     * @param request
     * @return
     */
    @RequestMapping(value = "/recommend", method = {RequestMethod.GET,RequestMethod.POST }, consumes = "application/json")
    @ResponseBody
    public Result recommend(@RequestBody RecommendTopicRequest request) {
        Result result = new Result();
        String log = String.format("/topic/recommend request: %s ", JSONObject.toJSONString(request));
        try {
            _checkParam(request);
            result.setData(topicService.recommend(request));
        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("recommend topic failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage(ErrorCodeEnum.SYSTEM_EXCEPTION.getValue());
            logger.info(String.format("recommend topic failed, %s", e.getMessage()), e);
        }
        log += String.format(" result: %s", JSONObject.toJSONString(result));
        logger.info(log);
        return result;
    }

    protected boolean _checkParam(RecommendTopicRequest request) {
        if (request == null) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "request is empty");
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
