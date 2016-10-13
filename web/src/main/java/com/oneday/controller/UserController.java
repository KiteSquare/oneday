package com.oneday.controller;

import com.oneday.common.domain.Result;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.po.User;
import com.oneday.exceptions.OndayException;
import com.oneday.service.UserService;
import com.oneday.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/9/9 17:12
 */
@Controller
@RequestMapping(value = "/oneday/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    UserService userService;
    @RequestMapping(value = "/regist", method = {RequestMethod.POST })
    @ResponseBody
    public  Result regist(@RequestBody UserVo user) {
        Result result = new Result();
        try {
            int num = userService.registWithCode((User) user, user.getCode());
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

    @RequestMapping(value = "/sendCode", method = {RequestMethod.POST })
    @ResponseBody
    public  Result sendCode(@RequestBody User user) {
        Result result = new Result();
        try {
            int num = userService.sendCode(user);
        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        } catch (Exception e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        }
        return result;
    }



    @RequestMapping(value = "/{id}", method = {RequestMethod.GET, RequestMethod.POST })
    public Result info(@PathVariable int id) {
        Result result = new Result();



        return result;
    }

}
