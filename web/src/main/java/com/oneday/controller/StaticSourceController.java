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
    private static final Logger logger = LogHelper.SR_LOG;
    @Resource
    private StaticResourceService localStaticResourceService;

    @RequestMapping(value = "/upload", method = {RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public  Result upload(HttpServletRequest request) {
        Result result = new Result();
        try {
            result.setData(localStaticResourceService.upload(request));
        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("upload failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage(ErrorCodeEnum.SYSTEM_EXCEPTION.getValue());
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        }
        return result;
    }
}
