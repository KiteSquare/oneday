package com.oneday.controller;

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
    private static Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Resource
    private SearchService searchService;
    /**
     * 推荐
     * @param request
     * @return
     */
    @RequestMapping(value = "/recommend", method = {RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public Result recommend(@RequestBody RecommendRequest request) {
        Result result = new Result();
        try {
            if (request == null || request.getAccessToken() == null) {
//                throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "");
                // 推荐附近？
            } else {
                Page<User> page = searchService.nearBy(0, request.getAccessToken());
                result.setData(page);
            }


        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("recommend failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage(ErrorCodeEnum.SYSTEM_EXCEPTION.getValue());
            logger.info(String.format("recommend failed, %s", e.getMessage()), e);
        }
        return result;
    }
    @RequestMapping(value = "/search", method = {RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public Result search(@RequestBody SearchRequest request) {
        Result result = new Result();
        try {
            Page<User> page = searchService.search(request);
            result.setData(page);

        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("search failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage(ErrorCodeEnum.SYSTEM_EXCEPTION.getValue());
            logger.info(String.format("search failed, %s", e.getMessage()), e);
        }
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
