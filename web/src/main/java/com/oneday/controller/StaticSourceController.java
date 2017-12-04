package com.oneday.controller;

import com.oneday.common.domain.Result;
import com.oneday.common.util.Uploader;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.exceptions.OndayException;
import com.oneday.service.StaticResourceService;
import com.oneday.utils.LogHelper;
import com.oneday.utils.PropertyPlaceholder;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/16 14:59
 */
@Controller
@RequestMapping(value = "/sr")
public class StaticSourceController extends BaseController {
    @Resource
    private StaticResourceService localStaticResourceService;

    @RequestMapping(value = "/upload", method = {RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public  Object upload(HttpServletRequest request) {
        Object result = Result.success(localStaticResourceService.upload(request));
        return result;
    }
}
