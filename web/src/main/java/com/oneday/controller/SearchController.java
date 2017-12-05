package com.oneday.controller;

import com.alibaba.fastjson.JSONObject;
import com.oneday.common.domain.Result;
import com.oneday.common.util.DateUtil;
import com.oneday.constant.ConfigConstant;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.BaseRequest;
import com.oneday.domain.vo.Location;
import com.oneday.domain.vo.Page;
import com.oneday.domain.vo.request.RecommendRequest;
import com.oneday.domain.vo.request.SearchRequest;
import com.oneday.exceptions.OndayException;
import com.oneday.service.SearchService;
import com.oneday.utils.LogHelper;
import com.oneday.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 搜索相关
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2017/1/19 15:33
 */
@Controller
@RequestMapping(value = "/oneday/search")
public class SearchController extends BaseController {
    @Resource
    private SearchService searchService;
    /**
     * 推荐
     * @param request
     * @return
     */
    @RequestMapping(value = "/recommend", method = {RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public Object recommend(@RequestBody RecommendRequest request) {
        LogHelper.USER_LOG.info(String.format("oneday/search/recommend, request %s", JSONObject.toJSONString(request)));

        Object result = null;
        if (request == null || request.getAccessToken() == null) {
//                throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "");
            // 推荐附近？
        } else {
            result = Result.success(searchService.nearBy(0, request.getAccessToken()));
        }
        LogHelper.USER_LOG.info(String.format("oneday/search/recommend,  result : %s",  JSONObject.toJSONString(result)));

        return result;
    }
    @RequestMapping(value = "/search", method = {RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public Object search(@RequestBody SearchRequest request) {
        LogHelper.USER_LOG.info(String.format("oneday/search/search, request %s", JSONObject.toJSONString(request)));
        Object result = Result.success(searchService.search(request));
        LogHelper.USER_LOG.info(String.format("oneday/search/search,  result : %s", JSONObject.toJSONString(result)));

        return result;
    }

    private void _checkRequest(SearchRequest request) {
        if (request == null) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "request is empty");
        }
        if (request.getCurrentPage() == null || request.getCurrentPage() <= 0){
            request.setCurrentPage(1);
        }
        if (request.getCurrentPage() > ConfigConstant.PAGE_MAX) {
            throw new OndayException(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode(), "没有记录了");
        }
        if (request.getPageNum() == null || request.getPageNum() <= 0 ) {
            request.setPageNum(ConfigConstant.PAGE_NUM_DEFAULT);
        }
        if (request.getPageNum() > ConfigConstant.PAGE_NUM_MAX) {
            request.setPageNum(ConfigConstant.PAGE_NUM_MAX);
        }
        if (request.getIndex() != null && request.getIndex() > ConfigConstant.PAGE_INDEX_MAX) {
            throw new OndayException(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode(), "没有记录了");
        }
    }
}
