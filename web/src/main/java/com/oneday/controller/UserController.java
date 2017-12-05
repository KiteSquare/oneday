package com.oneday.controller;

import com.alibaba.fastjson.JSONObject;
import com.oneday.common.domain.Result;
import com.oneday.common.util.DateUtil;
import com.oneday.common.util.Uploader;
import com.oneday.common.util.Validator;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.constant.HttpKeyEnum;
import com.oneday.constant.SexEnum;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.*;
import com.oneday.domain.vo.request.GetUserRequest;
import com.oneday.domain.vo.request.UserUpdateRequest;
import com.oneday.domain.vo.response.LoginResponse;
import com.oneday.exceptions.OndayException;
import com.oneday.service.UserService;
import com.oneday.utils.ConvertUtil;
import com.oneday.utils.LogHelper;
import com.oneday.utils.PropertyPlaceholder;
import com.oneday.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/9/9 17:12
 */
@Controller
@RequestMapping(value = "/oneday/user")
public class UserController  extends BaseController  {
    @Resource
    UserService userService;

    /**
     * 注册信息
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/regist", method = {RequestMethod.POST,RequestMethod.OPTIONS })
    @ResponseBody
    public  Object regist(@RequestBody UserRegistVo userVo) {
        LogHelper.USER_LOG.info(String.format("oneday/user/regist, request %s", JSONObject.toJSONString(userVo)));//TODO 干掉敏感信息
        _checkRegistParam(userVo);

        Object result = Result.success(userService.regist(userVo));
        LogHelper.USER_LOG.info(String.format("oneday/user/regist, result : %s",  JSONObject.toJSONString(result)));

        return result;
    }
    protected boolean _checkRegistParam(UserRegistVo request) {
        if (request == null) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "request is empty");
        }
        if (request.getStep() == null) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "step is required");
        }
        if (request.getStep() < 1 || request.getStep() > 3) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "step is not in range [1,2,3]");
        }
        switch (request.getStep()) {
            case 1:
                if (request.getIdcard() == null) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "需要身份证号码哦");
                }
                if (!Validator.isIDCard(request.getIdcard())) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请输入正确的身份证号码");
                }
                break;
            case 2:
                if (request.getIdcard() == null) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "需要身份证号码哦");
                }
                if (!Validator.isIDCard(request.getIdcard())) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请输入正确的身份证号码");
                }
                if (request.getPhone() == null) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请输入手机号码");
                }
                if (!Validator.isMobile(request.getPhone())) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请输入正确的手机号码");
                }
                if (request.getCode() == null) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请填写收到的短信码");
                }
                if (request.getPassword() == null) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请填写密码");
                }
                if (request.getPassword().trim().length() < 6 ) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "密码的长度不能低于6位哦");
                }
                break;
            case 3:
                if (request.getIdcard() == null) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "需要身份证号码哦");
                }
                if (!Validator.isIDCard(request.getIdcard())) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请输入正确的身份证号码");
                }
                if (request.getPhone() == null) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请输入手机号码");
                }
                if (!Validator.isMobile(request.getPhone())) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请输入正确的手机号码");
                }
                if (request.getName() == null) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请输入昵称");
                }
                if (request.getName().length() > 25) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "昵称不能超过25字");
                }
                if (request.getSex() == null) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请选择性别");
                }
                if (!SexEnum.isAvailable(request.getSex())) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "嘿，性别哪里不对");
                }
                if (request.getBirthStr() == null) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请选择生日");
                }
                try {
                    request.setBirth(DateUtil.strToDate(request.getBirthStr(), "yyyy-MM-dd"));
                    int age = DateUtil.getAge(request.getBirth());
                    if (age < 12) {
                        throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "年龄太小了哦");
                    }
                    if (age > 150) {
                        throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "别吓我，你都超过150岁了");
                    }
                } catch (OndayException e) {
                    throw e;
                } catch (Throwable e) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "生日格式不对");
                }
                if (request.getCity() == null) {
                    throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请选择城市");
                }
                break;
            default:
        }
        return true;
    }

    /**
     * 注册信息
     * @param user
     * @return
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public  Object update(@RequestBody UserUpdateRequest user, HttpServletRequest httpServletRequest) {
        LogHelper.USER_LOG.info(String.format("oneday/user/update, request %s", JSONObject.toJSONString(user)));//TODO 干掉敏感信息
        _checkUpdateParam(user);
        int res = userService.update(user, user.getAccessToken(), getUser(httpServletRequest));
        LogHelper.USER_LOG.info("oneday/user/update, result : success");

        return Result.success("");
    }

    protected boolean _checkUpdateParam(UserUpdateRequest request) {
        if (request == null) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "request is empty");
        }
        if (request.getAccessToken() == null) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "用户未登录");
        }
        return true;
    }

    /**
     * 校验验证码
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/verifyRegistCode", method = {RequestMethod.POST })
    @ResponseBody
    public  Object verifyRegistCode(@RequestBody UserVo userVo) {
        LogHelper.USER_LOG.info(String.format("oneday/user/verifyRegistCode, request %s", JSONObject.toJSONString(userVo)));
        Object result = Result.success(userService.verifyRegistCode((User) userVo, userVo.getCode()));
        LogHelper.USER_LOG.info(String.format("oneday/user/verifyRegistCode,  result : %s", JSONObject.toJSONString(result)));
        return result;
    }

    /**
     * 发送验证码
     * @param user
     * @return
     */
    @RequestMapping(value = "/sendCode", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public  Object sendCode(@RequestBody User user) {
        LogHelper.USER_LOG.info(String.format("oneday/user/sendCode, request %s", JSONObject.toJSONString(user)));
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "用户信息为空");
        }
        int num = userService.sendCode(user);
        Object result = Result.success("ok");
        LogHelper.USER_LOG.info(String.format("oneday/user/sendCode, result : %s", JSONObject.toJSONString(result)));
        return result;
    }

    /**
     * 获取登录用户信息
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public  Object getUserDetail(HttpServletRequest request) {
        LogHelper.USER_LOG.info(String.format("oneday/user/getUserInfo, request %s", JSONObject.toJSONString(request)));
//        int num = userService.sendCode(user);
        Object result = Result.success("ok");
        LogHelper.USER_LOG.info(String.format("oneday/user/getUserInfo,  result : %s",  JSONObject.toJSONString(result)));
        return result;
    }

    /**
     * 发送验证码
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public  Object login(@RequestBody LoginUserVo user, HttpServletResponse response) {
        LogHelper.USER_LOG.info(String.format("oneday/user/login, request phone %s", user.getPhone()));
        _checkLoginParam(user);
        LoginResponse loginResponse = null;
        switch (user.getType()) {
            case 1:
                loginResponse = userService.loginForAccessToken(user.getPhone(), user.getPassword());
                if (loginResponse == null) {
                    throw new OndayException(ErrorCodeEnum.USER_LOGIN_PASSWORD_ERROR.getCode(),
                            ErrorCodeEnum.USER_LOGIN_PASSWORD_ERROR.getValue());
                }
                break;

            case 2:
                loginResponse = userService.loginForAccessTokenWithCode(user.getPhone(), user.getCode());
                if (loginResponse == null) {
                    throw new OndayException(ErrorCodeEnum.USER_LOGIN_CODE_ERROR.getCode(),
                            ErrorCodeEnum.USER_LOGIN_CODE_ERROR.getValue());
                }
                break;
            default:
        }
        if (loginResponse == null) {
            throw new OndayException(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode(), "登录失败");
        }
        loginResponse.setType(user.getType());
        loginResponse.setUrl(user.getUrl());
        user.setPassword(null);
        Object result = Result.success(loginResponse);
        LogHelper.USER_LOG.info(String.format("oneday/user/login,  result : %s",  JSONObject.toJSONString(result)));
        return result;
    }



    /**
     * 创建会话
     * @param request
     * @return
     */
    @RequestMapping(value = "/session", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public  Object session(HttpServletRequest request) {
        String accessToken=request.getHeader(HttpKeyEnum.HTTPHEADTOKEN.getKey());
        LogHelper.USER_LOG.info(String.format("oneday/user/session, accessToken %s", accessToken));
        BaseUser baseUser = userService.getUser(accessToken);
        request.getSession().setAttribute(HttpKeyEnum.SESSIONTATTIBUTERUSER.getKey(),baseUser);
        return Result.success("欢迎回来");//TODO 根据不同因素，设置不同的提示语
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

    @RequestMapping(value = "/get", method = { RequestMethod.POST })
    @ResponseBody
    public Object info(@RequestBody GetUserRequest request, HttpServletRequest httpServletRequest) {
        LogHelper.USER_LOG.info(String.format("oneday/user/get, request %s", JSONObject.toJSONString(request)));
        if (request == null || StringUtils.isEmpty(request.getAccessToken())) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "参数错误");
        }
        Object tuser = null;
        if (request.getUid() != null && request.getUid() >= 0) {
            tuser = userService.getUserDetail(getUser(httpServletRequest), request.getUid());
        } else {
            tuser = userService.getUser(request.getAccessToken());
        }
        if (tuser == null) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "获取用户失败");
        }
        Object result = Result.success(tuser);
        LogHelper.USER_LOG.info(String.format("oneday/user/get,  result : %s",  JSONObject.toJSONString(result)));

        return result;
    }

    @RequestMapping(value = "/uploadHead", method = {RequestMethod.POST })
    @ResponseBody
    public  Object uploadHead(HttpServletRequest request) {
        Uploader uploader = new Uploader(request);
        uploader.setBasePath(PropertyPlaceholder.getProperty("path.file.upload.prefix"));
        uploader.uploadBase64("data");
        Object result = Result.success(uploader.getUrl());
        LogHelper.USER_LOG.info(String.format("oneday/user/uploadHead,  result : %s", JSONObject.toJSONString(result)));

        return result;
    }
    @RequestMapping(value = "/uploadUserImg", method = {RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public  Object uploadUserImg(HttpServletRequest request) {
        Object result = Result.success(userService.uploadUserImage(request, getUser(request)));
        LogHelper.USER_LOG.info(String.format("oneday/user/uploadUserImg, result : %s", JSONObject.toJSONString(result)));

        return result;
    }
}
