package com.oneday.controller;

import com.oneday.common.domain.Result;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.User;
import com.oneday.exceptions.OndayException;
import com.oneday.service.AssociateService;
import com.oneday.service.UserService;
import com.oneday.vo.SendRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/9/9 17:12
 */
@Controller
@RequestMapping(value = "/oneday/willow")
public class WillowController {
    private static Logger logger = LoggerFactory.getLogger(WillowController.class);
    @Resource
    AssociateService associateService;
    @RequestMapping(value = "/send", method = {RequestMethod.POST })
    @ResponseBody
    public  Result send(@RequestBody SendRequest sendRequest) {
        Result result = new Result();
        try {
            _validateRequest(sendRequest);
            associateService.send(sendRequest.getSenderId(),sendRequest.getReceiverId());

        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        } catch (Exception e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage("操作失败");
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        }
        return result;
    }


    protected void _validateRequest(SendRequest sendRequest) {
        if (sendRequest == null) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "request data not found");
        }
        if (sendRequest.getSenderId() == null || sendRequest.getSenderId() < 0) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "sender is invalid");
        }
        if (sendRequest.getReceiverId() == null || sendRequest.getReceiverId() < 0) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "receiver is invalid");
        }
    }



}
