package com.oneday.controller;

import com.oneday.common.domain.Result;
import com.oneday.common.util.DateUtil;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.UserDisplay;
import com.oneday.exceptions.OndayException;
import com.oneday.service.UserService;
import com.oneday.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

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

    /**
     * 注册信息
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/regist", method = {RequestMethod.POST })
    @ResponseBody
    public  Result regist(@RequestBody UserVo userVo) {
        Result result = new Result();
        try {
            if (!StringUtils.isEmpty(userVo.getBirthStr())) {
                userVo.setBirth(DateUtil.strToDate(userVo.getBirthStr(), "yyyy-MM-dd"));
            }
            User user = userService.regist((User) userVo);
            result.setData(user);
        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage(ErrorCodeEnum.SYSTEM_EXCEPTION.getValue());
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        }
        return result;
    }

    /**
     * 校验验证码
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/verifyRegistCode", method = {RequestMethod.POST })
    @ResponseBody
    public  Result verifyRegistCode(@RequestBody UserVo userVo) {
        Result result = new Result();
        try {
            User user = userService.verifyRegistCode((User) userVo, userVo.getCode());
            result.setData(user);
        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("verifyRegistCode failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage(ErrorCodeEnum.SYSTEM_EXCEPTION.getValue());
            logger.info(String.format("verifyRegistCode failed, %s", e.getMessage()), e);
        }
        return result;
    }

    /**
     * 发送验证码
     * @param user
     * @return
     */
    @RequestMapping(value = "/sendCode", method = {RequestMethod.POST })
    @ResponseBody
    public  Result sendCode(@RequestBody User user) {
        Result result = new Result();
        try {
            if (user == null) {
                throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "用户信息为空");
            }

            int num = userService.sendCode(user);
        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("sendCode failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("sendCode failed, %s", e.getMessage()), e);
        }
        return result;
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Result info(@PathVariable Long id) {
        Result result = new Result();

        try {
            if (id == null || id <= 0) {
                throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "用户id错误");
            }
            UserDisplay user = userService.getUserDisplayById(id);
            if (user == null) {
                throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "用户id错误");
            }
            result.setData(user);
        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("get user info failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("get user info failed, %s", e.getMessage()), e);
        }
        return result;
    }

}
