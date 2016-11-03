package com.oneday.service;

import com.oneday.domain.po.User;

import java.util.List;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 18:29
 */
public interface UserService {
    /**
     *
     * @param user
     * @return
     */
    Integer add(User user);

    /**
     *
     * @param user
     * @return
     */
    User regist(User user);

    /**
     * 验证码注册
     * @param user
     * @return
     */
    User verifyRegistCode(User user, String code);

    /**
     * 发送验证码
     * @param user
     * @return
     */
    Integer sendCode(User user);

    /**
     *
     * @param userId
     * @return
     */
    User getById(Long userId);

    /**
     * 获取接受的用户
     * @param receiverId
     * @return
     */
    User getAcceptedUser(Long receiverId);

    /**
     *
     * @param user
     * @return
     */
    List<User> get(User user);


    /**
     *
     * @param user
     * @return
     */
    Boolean login(User user);

    /**
     *
     * @param phone
     * @param password
     * @return
     */
    Boolean login(String phone, String password);

    /**
     *
     * @param phone
     * @param code
     * @return
     */
    Boolean loginWithCode(String phone, String code);


}
