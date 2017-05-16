package com.oneday.utils;

import com.oneday.constant.ConfigConstant;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.LoginResult;
import com.oneday.domain.vo.LoginUserVo;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/4/26 15:50
 */
public class ConvertUtil {
    public static LoginResult convert(LoginUserVo requst, User user) {
        LoginResult loginResult = new LoginResult();
        loginResult.setType(requst.getType());
        loginResult.setUrl(requst.getUrl());
        loginResult.setId(user.getId());
        loginResult.setHead(user.getHead());
        loginResult.setPhone(user.getPhone());
        loginResult.setSex(user.getSex());
        loginResult.setStatus(user.getStatus());
        loginResult.setMarriage(user.getMarriage());
        loginResult.setSdktoken(ConfigConstant.SDKTOKEN);
        return loginResult;
    }
}
