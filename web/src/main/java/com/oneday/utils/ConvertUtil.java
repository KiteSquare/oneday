package com.oneday.utils;

import com.oneday.constant.ConfigConstant;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.response.LoginResponse;
import com.oneday.domain.vo.LoginUserVo;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/4/26 15:50
 */
public class ConvertUtil {
    public static LoginResponse convert(LoginUserVo requst) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setType(requst.getType());
        loginResponse.setUrl(requst.getUrl());
        loginResponse.setSdktoken(ConfigConstant.SDKTOKEN);
        loginResponse.setAppkey(PropertyPlaceholder.getProperty("netease.app.key"));
        return loginResponse;
    }
    public static LoginResponse convert(User requst) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSdktoken(ConfigConstant.SDKTOKEN);
        return loginResponse;
    }
}
