package com.oneday.service.impl;

import com.oneday.common.util.MD5Util;
import com.oneday.common.util.Validator;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.constant.SexEnum;
import com.oneday.dao.UserDao;
import com.oneday.domain.po.User;
import com.oneday.exceptions.OndayException;
import com.oneday.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/8 10:59
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    UserDao userDao;
    public Integer add(User user) {
        return null;
    }

    /**
     * @param user
     * @return
     */
    public Integer regist(User user) {
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "user is null");
        }
        if (StringUtils.isEmpty(user.getPhone())) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "phone is required");
        }
        if (!Validator.isMobile(user.getPhone())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "phone is invalid");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "password is required");
        }

        user.setPassword(MD5Util.md5Encode32(user.getPassword()));
        return userDao.updateByPhone(user);
    }

    /**
     * 验证码注册
     *
     * @param user
     * @param code
     * @return
     */
    @Override
    public Integer registWithCode(User user, String code) {
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "user is null");
        }
        if (StringUtils.isEmpty(user.getPhone())) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "phone is required");
        }
        if (!Validator.isMobile(user.getPhone())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "phone is invalid");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "password is required");
        }
        if (user.getSex() == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "sex is required");
        }
        if (!SexEnum.isAvailable(user.getSex())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "sex is invalid");
        }

        _checkCode(user, code);
        user.setPassword(MD5Util.md5Encode32(user.getPassword()));
        Date now = new Date();
        user.setUpdate(now);
        user.setCreate(now);
        return userDao.add(user);
    }

    /**
     * 发送验证码
     *
     * @param user
     * @return
     */
    @Override
    public Integer sendCode(User user) {
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "user is null");
        }
        if (StringUtils.isEmpty(user.getPhone())) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "phone is required");
        }
        if (!Validator.isMobile(user.getPhone())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "phone is invalid");
        }
        int res =userDao.add(user);
        // TODO  发送验证码
        return res;
    }

    protected void _checkCode(User user, String code) {
        // TODO 手机短信验证码

    }

    public User getById(Long userId) {
        return userDao.get(userId);
    }

    public List<User> get(User user) {


        return null;
    }

    /**
     * @param user
     * @return
     */
    public Boolean login(User user) {
        return null;
    }

    /**
     * @param phone
     * @param password
     * @return
     */
    public Boolean login(String phone, String password) {
        return null;
    }

    /**
     * @param phone
     * @param code
     * @return
     */
    public Boolean loginWithCode(String phone, String code) {
        return null;
    }
}
