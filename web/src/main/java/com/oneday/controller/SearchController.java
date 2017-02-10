package com.oneday.controller;

import com.oneday.common.domain.Result;
import com.oneday.common.util.DateUtil;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.Location;
import com.oneday.domain.vo.Page;
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
     * @param user
     * @return
     */
    @RequestMapping(value = "/recommend", method = {RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public Result recommend(@ModelAttribute User user) {
        Result result = new Result();
        try {
            if (user == null) {
                throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "request user not found");
            }
            Page<User> page = searchService.nearBy(0, user);
            result.setData(page);
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
}
