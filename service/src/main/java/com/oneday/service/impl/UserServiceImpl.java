package com.oneday.service.impl;

import com.oneday.common.util.MD5Util;
import com.oneday.common.util.Validator;
import com.oneday.constant.*;
import com.oneday.dao.HunterReceiverDao;
import com.oneday.dao.UserDao;
import com.oneday.domain.po.HunterReceiver;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.HunterReceiverParam;
import com.oneday.domain.vo.UserDisplay;
import com.oneday.exceptions.OndayException;
import com.oneday.service.UserService;
import com.oneday.service.utils.VoConvertor;
import com.oneday.utils.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
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
    @Resource
    HunterReceiverDao hunterReceiverDao;
    public Integer add(User user) {
        return null;
    }

    /**
     * @param user
     * @return
     */
    public User regist(User user) {
        _verifyUserInfo(user);
        User userDb = userDao.getByPhone(user.getPhone());
        Date now = new Date();
        if (userDb == null ) {
            // 数据库不存在则插入用户数据
            user.setPassword(MD5Util.md5Encode32(user.getPassword()));
            user.setUpdate(now);
            user.setCreate(now);
            if (SexEnum.FEMALE.getSex().equals(user.getSex())) {
                user.setStatus(ReceiverEnum.SINGLE.getStatus());
            } else {
                user.setStatus(HunterEnum.SINGLE.getStatus());
            }
            int res =userDao.add(user);
            userDb = userDao.getByPhone(user.getPhone());
        } else if (userDb.getSex() != null && SexEnum.isAvailable(userDb.getSex())) {
            UserUtil.cope(userDb, user);
            // 用户存在但是未初始化则更新用户信息
            userDb.setPassword(MD5Util.md5Encode32(user.getPassword()));
            userDb.setUpdate(now);
            userDb.setCreate(now);
            if (SexEnum.FEMALE.getSex().equals(user.getSex())) {
                userDb.setStatus(ReceiverEnum.SINGLE.getStatus());
            } else {
                userDb.setStatus(HunterEnum.SINGLE.getStatus());
            }
            int res = userDao.updateByPhone(userDb);

        } else {
            // 用户已经注册过了
            throw new OndayException(ErrorCodeEnum.USER_REGIST_DUPLICATE_ERROR.getCode(), ErrorCodeEnum.USER_REGIST_DUPLICATE_ERROR.getValue());
        }
        return UserUtil.safeUserInfo(userDb);
    }

    protected void _verifyUserInfo(User user) {
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
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "password is required");
        }
        if (user.getSex() == null) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "sex is required");
        }
        if (!SexEnum.isAvailable(user.getSex())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "sex is invalid");
        }
        if (StringUtils.isEmpty(user.getName())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "name is required");
        }

        if (StringUtils.isEmpty(user.getIdcard())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "idcard is required");
        }

        if (StringUtils.isEmpty(user.getHead())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "head is required");
        }

        if (!Validator.isIDCard(user.getIdcard())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "idcard is invalid");
        }

        if (user.getBirth() == null) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "birth is required");
        }
        Date now = new Date();
        if (user.getBirth().after(now)) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "birth is invalid");
        }



    }

    /**
     * 验证码注册
     *
     * @param user
     * @param code
     * @return
     */
    @Override
    public User verifyRegistCode(User user, String code) {
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "user is null");
        }
        if (StringUtils.isEmpty(user.getPhone())) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "phone is required");
        }
        if (!Validator.isMobile(user.getPhone())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "phone is invalid");
        }

        // 检查验证码
        _checkCode(user, code);

        User userDb = userDao.getByPhone(user.getPhone());
        if (userDb == null) {
            // 数据库不存在则插入用户数据
            user.setStatus(HunterEnum.NOTHING.getStatus());
            user.setPassword(null);
            Date now = new Date();
            user.setUpdate(now);
            user.setCreate(now);
            user.setSex(null);
            int res =userDao.add(user);
            userDb = userDao.getByPhone(user.getPhone());
        }
        return UserUtil.safeUserInfo(userDb);
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
        User userDb = userDao.getByPhone(user.getPhone());
        if (userDb == null) {
            // 数据库不存在则插入用户数据
            Date now = new Date();
            user.setUpdate(now);
            user.setCreate(now);
            user.setStatus(HunterEnum.NOTHING.getStatus());
            user.setPassword(null);
            int res =userDao.add(user);
        }

        // TODO  发送验证码
        return 1;
    }

    protected void _checkCode(User user, String code) {
        // TODO 手机短信验证码
        if (code == null || !"123".equals(code)) {
            throw new OndayException(ErrorCodeEnum.USER_REGIST_CODE_ERROR.getCode(), ErrorCodeEnum.USER_REGIST_CODE_ERROR.getValue());
        }
    }

    /**
     * 获取用户展示信息
     * @param userId
     * @return
     */
    public UserDisplay getUserDisplayById(Long userId) {
        if (userId == null) return null;
        User user = userDao.get(userId);
        return VoConvertor.convertUserToUserDisplay(user);
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

    /**
     * 获取接受的用户
     *
     * @param receiverId
     * @return
     */
    @Override
    public User getAcceptedUser(Long receiverId) {
        HunterReceiverParam param = new HunterReceiverParam();
        param.setStatus(StateEnum.ACCEPT.getStatus());
        param.setReceiver(receiverId);
        param.setYn(0);
        List<HunterReceiver> data = hunterReceiverDao.getByWhere(param);
        if (data !=null && data.size() > 0) {
            Long hunterId = data.get(0).getHunter();
            return userDao.get(hunterId);
        }
        return null;
    }
}
