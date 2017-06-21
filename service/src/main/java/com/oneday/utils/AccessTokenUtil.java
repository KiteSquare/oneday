package com.oneday.utils;

import com.alibaba.fastjson.JSONObject;
import com.oneday.common.util.AESEncrypter;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.AccessToken;
import com.oneday.domain.vo.BaseUser;
import com.oneday.exceptions.OndayException;

import java.util.Calendar;
import java.util.Date;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/13 17:26
 */
public class AccessTokenUtil {
    public static AccessToken accessToken(User user){
        if (user == null || user.getId() == null) {
            return null;
        }
        BaseUser baseUser = VoConvertor.convertBaseUser(user);
        Calendar calendar = Calendar.getInstance();
//        baseUser.setCreate(calendar.getTime());
        String expireTime = PropertyPlaceholder.getProperty("access.token.expire.time", "3600");
        calendar.add(Calendar.SECOND, Integer.valueOf(expireTime));
        baseUser.setExpired(calendar.getTime());

        AccessToken accessToken = new AccessToken();
        String content = JSONObject.toJSONString(baseUser);
        String key = PropertyPlaceholder.getProperty("key.encrypt");
        if (key == null) {
            throw new OndayException(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode(), "Property key.encriper not found");
        }
        try {
            String token = AESEncrypter.aesEncrypt(content, key);
            accessToken.setAccessToken(token);
        } catch (Throwable e) {
            //
            throw new OndayException(ErrorCodeEnum.USER_ENCRYPT_FAIL.getCode(), "加密失败");
        }
        return accessToken;
    }

    public static BaseUser decryptAccessToken(String accessToken){
        if (accessToken == null) {
            return null;
        }
        String key = PropertyPlaceholder.getProperty("key.encrypt");
        if (key == null) {
            throw new OndayException(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode(), "Property key.encriper not found");
        }
        BaseUser user = null;
        try {
            String content = AESEncrypter.aesDecrypt(accessToken, key);
            user = JSONObject.parseObject(content, BaseUser.class);
            if (user == null || user.getId()==null) {
                return null;
            }
        } catch (Throwable e) {
            //
            throw new OndayException(ErrorCodeEnum.USER_DECRYPT_FAIL.getCode(), "验证失败，请重新登录",e);
        }
        if (new Date().after(user.getExpired())) {
            throw new OndayException(ErrorCodeEnum.USER_ACCESS_TOKEN_EXPIRED.getCode(), "accessToken失效，请重新登录");
        }
        return user;
    }

    public static void main(String[] s) {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        calendar.add(Calendar.SECOND, 5184000);
        System.out.println(calendar.getTime());
    }
}
