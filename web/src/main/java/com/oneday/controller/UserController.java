package com.oneday.controller;

import com.alibaba.fastjson.JSONObject;
import com.oneday.common.domain.Result;
import com.oneday.common.util.DateUtil;
import com.oneday.common.util.EncryptUtil;
import com.oneday.constant.ConfigConstant;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.LoginResult;
import com.oneday.domain.vo.LoginUserVo;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @RequestMapping(value = "/sendCode", method = {RequestMethod.GET, RequestMethod.POST })
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

    /**
     * 获取登录用户信息
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public  Result getUserInfo(HttpServletRequest request) {
        Result result = new Result();
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                throw new OndayException(ErrorCodeEnum.USER_NOT_LOGIN_ERROR.getCode(),
                        ErrorCodeEnum.USER_NOT_LOGIN_ERROR.getValue());
            }
            for (Cookie cookie: cookies) {
                if (ConfigConstant.COOKIE_USERINFO_KEY.equals(cookie.getName())) {
                    String str = cookie.getValue();
                    String jsonStr = EncryptUtil.decryptUser(str);
                    try {
                        User user = JSONObject.parseObject(jsonStr, User.class);
                        result.setData(user);
                    } catch (Exception e) {
                        throw new OndayException(ErrorCodeEnum.USER_NOT_LOGIN_ERROR.getCode(),
                                ErrorCodeEnum.USER_NOT_LOGIN_ERROR.getValue());
                    }
                    break;
                }
            }
//            int num = userService.sendCode(user);
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

    /**
     * 发送验证码
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public  Result login(@RequestBody LoginUserVo user, HttpServletResponse response) {
        Result result = new Result();
        try {
            _checkLoginParam(user);
            User userInfo = null;
            switch (user.getType()) {
                case 1:
                    userInfo = userService.login(user.getPhone(), user.getPassword());
                    if (userInfo == null) {
                        throw new OndayException(ErrorCodeEnum.USER_LOGIN_PASSWORD_ERROR.getCode(),
                                ErrorCodeEnum.USER_LOGIN_PASSWORD_ERROR.getValue());
                    }
                    break;

                case 2:
                    userInfo = userService.loginWithCode(user.getPhone(), user.getCode());
                    if (userInfo == null) {
                        throw new OndayException(ErrorCodeEnum.USER_LOGIN_CODE_ERROR.getCode(),
                                ErrorCodeEnum.USER_LOGIN_CODE_ERROR.getValue());
                    }
                    break;
                default:

            }
            if (userInfo == null) {
                throw new OndayException(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode(), "用户信息为空");
            }
            LoginResult loginResult = new LoginResult();
            loginResult.setType(user.getType());
            loginResult.setUrl(user.getUrl());
            loginResult.setId(userInfo.getId());
            loginResult.setSdktoken(ConfigConstant.SDKTOKEN);
            result.setData(loginResult);
            JSONObject userJo = new JSONObject();
            userJo.put("id", userInfo.getId());
            if (!StringUtils.isEmpty(userInfo.getName())) {
                userJo.put("name", userInfo.getName());
            }
            if (!StringUtils.isEmpty(userInfo.getPhone())) {
                userJo.put("phone", userInfo.getPhone());
            }
            if (!StringUtils.isEmpty(userInfo.getHead())) {
                userJo.put("head", userInfo.getHead());
            }
            String str = EncryptUtil.encryptUser(userJo.toJSONString());
            if (str == null) {
                throw new OndayException(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode(), "加密用户信息出错了");
            }
            Cookie cookie = new Cookie(ConfigConstant.COOKIE_USERINFO_KEY, str);
            cookie.setPath("/");
            cookie.setMaxAge(ConfigConstant.COOKIE_EXPIER_TIME);
            response.addCookie(cookie);
        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("login failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("login failed, %s", e.getMessage()), e);
        }
        return result;
    }
    private void _checkLoginParam(LoginUserVo user) {
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "request is empty");
        }
        if (StringUtils.isEmpty(user.getPhone())) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "phone is empty");
        }
        if (user.getType() == null) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "type is required");
        }
        if (user.getType() == 1) {
            if (StringUtils.isEmpty(user.getPassword())) {
                throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "password is required");
            }
        } else if (user.getType() == 2) {
            if (StringUtils.isEmpty(user.getCode())) {
                throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "code is required");
            }
        } else {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "type is invalid");
        }

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
