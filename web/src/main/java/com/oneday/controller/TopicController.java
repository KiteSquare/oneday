package com.oneday.controller;

import com.alibaba.fastjson.JSONObject;
import com.oneday.common.domain.Result;
import com.oneday.constant.ConfigConstant;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.vo.ImageVo;
import com.oneday.domain.vo.request.CreateTopicRequest;
import com.oneday.domain.vo.request.GetTopicRequest;
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
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/21 15:00
 */
@Controller
@RequestMapping(value = "/topic")
public class TopicController extends BaseController {
    @Resource
    private TopicService topicService;

    /**
     * 创建帖子
     * @param request
     * @return
     */
    @RequestMapping(value = "/create", method = {RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public Object create(@RequestBody CreateTopicRequest request, HttpServletRequest httpServletRequest) {
        LogHelper.TOPIC_LOG.info(String.format("/topic/create request: %s ", JSONObject.toJSONString(request)));
        _checkParam(request);
        Object result = Result.success(topicService.create(request, getUser(httpServletRequest)));
        LogHelper.TOPIC_LOG.info(String.format("/topic/create result: %s ", JSONObject.toJSONString(result)));
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
        if (!StringUtils.isEmpty(request.getImages())) {
            String[] imgArr = request.getImages().split(",");
            List<ImageVo> imageVos =  new ArrayList<>();
            for (String url: imgArr) {
                if (StringUtils.isEmpty(url)) {
                    continue;
                }
                ImageVo imageVo = new ImageVo();
                imageVo.setUrl(url);
                imageVos.add(imageVo);
            }
            request.setImages(JSONObject.toJSONString(imageVos));
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
    public Object get(@RequestBody GetTopicRequest request) {
        LogHelper.TOPIC_LOG.info(String.format("/topic/get request: %s ", JSONObject.toJSONString(request)));
        _checkParam(request);
        Object result = Result.success(topicService.get(request));
        LogHelper.TOPIC_LOG.info(String.format("/topic/get result: %s ", JSONObject.toJSONString(result)));
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
    public Object recommend(@RequestBody RecommendTopicRequest request, HttpServletRequest httpServletRequest) {
        LogHelper.TOPIC_LOG.info(String.format("/topic/recommend request: %s ", JSONObject.toJSONString(request)));
        _checkParam(request);

        Object result = Result.success(topicService.recommend(request, getUser(httpServletRequest)));
        LogHelper.TOPIC_LOG.info(String.format("/topic/recommend result: %s ", JSONObject.toJSONString(result)));
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
