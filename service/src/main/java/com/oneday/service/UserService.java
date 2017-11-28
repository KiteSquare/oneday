package com.oneday.service;

import com.oneday.domain.po.User;
import com.oneday.domain.vo.*;
import com.oneday.domain.vo.response.LoginResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
     * 更新
     * @param user
     * @param accessToken
     * @return
     */
    Integer update(User user, String accessToken);

    /**
     * 更新用户图片
     * @param images
     * @param baseUser
     * @return
     */
    Integer updateImage(String[] images, BaseUser baseUser);

    /**
     *
     * @param user
     * @return
     */
    User regist(User user);

    /**
     * 注册
     * @param user
     * @return
     */
    LoginResponse regist(UserRegistVo user);

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
     * @param accessToken
     * @return
     */
    BaseUser getUser(String accessToken);

    /**
     * 获取用户详情
     * @param accessToken
     * @return
     */
    UserDisplay getUserDetail(String accessToken, Long uid);

    /**
     * 获取用户Map
     * @param accessToken
     * @return
     */
    Map<Long, User> getUserMap(String accessToken, Long... uid);

    /**
     * 获取接受的用户
     * @param receiverId
     * @return
     */
    User getAcceptedUser(Long receiverId);


    /**
     *
     * @param phone
     * @param password
     * @return
     */
    LoginResponse loginForAccessToken(String phone, String password);
    /**
     *
     * @param phone
     * @param code
     * @return
     */
    LoginResponse loginForAccessTokenWithCode(String phone, String code);

    /**
     * 从http请求头里获取用户信息
     * @param request
     * @return
     */
    BaseUser getUserFromHttpRequst(HttpServletRequest request) ;

    /**
     * 上传文件到静态服务，返回文件链接
     * @param request
     * @return
     */
    String uploadUserImage(HttpServletRequest request);
}
