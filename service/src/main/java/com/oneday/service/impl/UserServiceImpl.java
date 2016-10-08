package com.oneday.service.impl;

import com.oneday.common.util.MD5Util;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.constant.StateEnum;
import com.oneday.dao.HunterReceiverDao;
import com.oneday.dao.UserDao;
import com.oneday.domain.User;
import com.oneday.domain.HunterReceiver;
import com.oneday.exceptions.OndayException;
import com.oneday.service.UserService;
import com.oneday.service.state.Machine;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/8 10:59
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
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
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "password is required");
        }
        _checkCode(user);
        user.setPassword(MD5Util.md5Encode32(user.getPassword()));
        return userDao.add(user);
    }

    protected void _checkCode(User user) {
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
