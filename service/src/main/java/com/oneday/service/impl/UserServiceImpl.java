package com.oneday.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.oneday.common.util.MD5Util;
import com.oneday.common.util.Uploader;
import com.oneday.common.util.Validator;
import com.oneday.constant.*;
import com.oneday.dao.HunterReceiverDao;
import com.oneday.dao.UserDao;
import com.oneday.domain.po.HunterReceiver;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.*;
import com.oneday.domain.vo.response.LoginResponse;
import com.oneday.exceptions.OndayException;
import com.oneday.service.IMService;
import com.oneday.service.StaticResourceService;
import com.oneday.service.UserService;
import com.oneday.utils.*;
import com.spatial4j.core.io.GeohashUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/8 10:59
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogHelper.USER_LOG;
    @Resource
    UserDao userDao;
    @Resource
    HunterReceiverDao hunterReceiverDao;
    @Resource
    IMService imService;
    @Resource
    private StaticResourceService localStaticResourceService;
    public Integer add(User user) {
        return null;
    }


    public LoginResponse regist(UserRegistVo user) {
        switch (user.getStep()) {
            case 1:
                UserParam userParam = new UserParam();
                userParam.setIdcard(user.getIdcard());
                List<User> res = userDao.getByWhere(userParam);
                if (res != null && res.size() > 0) {
                    User user1 = res.get(0);
                    if (user1.getSex() != null) {
                        throw new OndayException(ErrorCodeEnum.USER_REGIST_IDCARD_EXISTED.getCode(), "该身份证已经注册");
                    }
                }
                break;
            case 2:
                User u = userDao.getByPhone(user.getPhone());
                if (u != null) {
                    if (u.getSex() != null) {
                        throw new OndayException(ErrorCodeEnum.USER_REGIST_DUPLICATE_ERROR.getCode(), "该手机号已经注册");
                    } else {
                        //完成第二步但未完成第三步
                        break;
                    }
                }
                // 检查验证码
                _checkCode(user, user.getCode());
                u = VoConvertor.convert(user, user.getStep());
                Date now = new Date();
                u.setCreate(now);
                u.setUpdate(now);
                u.setCount(0);
                if (SexEnum.isMale(u.getSex())) {
                    u.setStatus(HunterEnum.NOTHING.getStatus());
                } else {
                    u.setStatus(ReceiverEnum.NOTHING.getStatus());
                }

                int addRes = userDao.add(u);
                if (addRes <= 0) {
                    throw new OndayException(ErrorCodeEnum.USER_REGIST_FAIL.getCode(), "注册失败");
                }
                break;
            case 3:
                User u1 = userDao.getByPhone(user.getPhone());
                if (u1 == null ) {
                    throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "该手机号的用户不存在");
                }
                if (!u1.getIdcard().equals(user.getIdcard())) {
                    throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "该手机号的用户与身份证绑定异常");
                }
                Date now1 = new Date();
                u1.setUpdate(now1);
                u1.setSex(user.getSex());
                if (SexEnum.isMale(user.getSex())) {
                    u1.setStatus(HunterEnum.NOTHING.getStatus());
                } else {
                    u1.setStatus(ReceiverEnum.NOTHING.getStatus());
                }
                u1.setName(user.getName());
                u1.setBirth(user.getBirth());
                u1.setProvinceCode(user.getProvinceCode());
                u1.setProvince(user.getProvince());
                u1.setCityCode(user.getCityCode());
                u1.setCity(user.getCity());
                u1.setLat(user.getLat());
                u1.setLon(user.getLon());
                if (user.getLat() != null && user.getLon() != null) {
                    u1.setGeocode(GeohashUtils.encodeLatLon( user.getLat(),user.getLon(),ConfigConstant.USER_GEOCODE_LENGTH_MAX));
                }
                u1.setHead(user.getHead());
                u1.setDeviceId(user.getDeviceId());


                boolean needIM = false;
                if (needIM) {
                    try {
                        JSONObject imresult = imService.create(user);
                        JSONObject info = imresult.getJSONObject("info");
                        if (info != null ) {
                            u1.setImtoken(info.getString("token"));
                        }
                    } catch (Throwable e) {
                        logger.warn(e.getMessage(), e);
                    }
                }
                int updateRes = userDao.updateByPhone(u1);
                if (updateRes <= 0) {
                    throw new OndayException(ErrorCodeEnum.USER_REGIST_FAIL.getCode(), "更新失败");
                }
                AccessToken accessToken = AccessTokenUtil.accessToken(u1);
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setAccessToken(accessToken.getAccessToken());
                loginResponse.setSdktoken(ConfigConstant.SDKTOKEN);
                return loginResponse;
            default:
        }
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
     * @param accessToken
     * @return
     */
    public BaseUser getUser(String accessToken) {
        if (accessToken == null) return null;
        BaseUser user = AccessTokenUtil.decryptAccessToken(accessToken);
        return user;
    }

    /**
     * 获取用户详情
     * @param user
     * @param uid
     * @return
     */
    @Override
    public UserDisplay getUserDetail(BaseUser user, Long uid) {
        if (uid == 0) {
            uid = user.getId();
        }
        User user1 = userDao.get(uid);
        if (user1 == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), ErrorCodeEnum.USER_NOT_FOUND.getValue());
        }
        return  VoConvertor.convertUserToUserDisplay(user1);
    }

    /**
     * 获取用户Map
     *
     * @param accessToken
     * @param uid
     * @return
     */
    @Override
    public Map<Long, User> getUserMap(String accessToken, Long... uid) {
        if (accessToken == null || uid == null) return null;
        BaseUser user = AccessTokenUtil.decryptAccessToken(accessToken);
        if (user == null || user.getId() == null) {
            throw new OndayException(ErrorCodeEnum.USER_DECRYPT_FAIL.getCode(), "验证失败，请重新登录");
        }

        List<Long> uidlist = new ArrayList<>();
        for (Long id: uid) {
            if (id == 0) {
                uidlist.add(user.getId());
            } else {
                uidlist.add(id);
            }

        }
        return userDao.getMapByIds(uidlist);
    }

    /**
     * @param phone
     * @param password
     * @return
     */
    @Override
    public LoginResponse loginForAccessToken(String phone, String password) {
        User user = userDao.getByPhone(phone);
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_LOGIN_FAIL.getCode(), ErrorCodeEnum.USER_LOGIN_FAIL.getValue());
        }
        try {
            if (!MD5Util.verify(password, user.getPassword())) {
                throw new OndayException(ErrorCodeEnum.USER_LOGIN_FAIL.getCode(), ErrorCodeEnum.USER_LOGIN_FAIL.getValue());
            }

        } catch (Throwable e) {
            throw  new OndayException(ErrorCodeEnum.USER_LOGIN_FAIL.getCode(), "密码错误",e);
        }

        AccessToken accessToken = AccessTokenUtil.accessToken(user);
        if (accessToken == null || accessToken.getAccessToken() == null) {
            logger.error(String.format("Encrypt user info for accessToken fail, uid[%s]", user.getId()));
            throw new OndayException(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode(), "系统异常");
        }
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSdktoken(user.getImtoken());
        loginResponse.setAppkey(PropertyPlaceholder.getProperty("netease.app.key"));
        loginResponse.setAccessToken(accessToken.getAccessToken());
        return loginResponse;
    }

    /**
     * @param phone
     * @param code
     * @return
     */
    @Override
    public LoginResponse loginForAccessTokenWithCode(String phone, String code) {
        return null;
    }

    /**
     * @param phone
     * @param code
     * @return
     */
    public User loginWithCode(String phone, String code) {
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

    /**
     * 更新
     *
     * @param user
     * @return
     */
    @Override
    public Integer update(User user, String accessToken,BaseUser currentUser) {
        user.setId(currentUser.getId());
        _safeUpdateUser(user, false);
        int res = userDao.update(user);
        return res;
    }

    /**
     * 更新用户图片
     *
     * @param images
     * @param baseUser
     * @return
     */
    @Override
    public Integer updateImage(String[] images, BaseUser baseUser) {
        if (baseUser == null || baseUser.getId() == null || images == null || images.length ==0) {
            return 0;
        }
        User user = userDao.getImagesById(baseUser.getId());
        if (user == null) {
            return 0;
        }
        List<ImageVo> imageVos = null;
        if (!StringUtils.isEmpty(user.getImages())) {
            imageVos = JSONObject.parseArray(user.getImages(), ImageVo.class);
        } else {
            imageVos = new ArrayList<>();
        }
        for (String image : images) {
            ImageVo imageVo = new ImageVo();
            imageVo.setUrl(image);
            imageVos.add(imageVo);
        }

        user.setImages(JSONObject.toJSONString(imageVos));
        user.setUpdate(new Date());

        return userDao.update(user);
    }

    protected void _safeUpdateUser(User user, boolean needPhone) {
        if (user == null) return;
        if (user.getIdcard() != null) {
            user.setIdcard(null);
        }
        if (user.getPassword() != null) {
            user.setPassword(null);
        }
        if (user.getSex() != null) {
            user.setSex(null);
        }
        if (!needPhone && user.getPhone() != null) {
            user.setPhone(null);
        }
        if (user.getLevel() != null) {
            user.setLevel(null);
        }
    }

    /**
     * 从http请求头里获取用户信息
     *
     * @param request
     * @return
     */
    @Override
    public BaseUser getUserFromHttpRequst(HttpServletRequest request) {
        if (request == null) return null;
        String accessToken = request.getHeader("accessToken");

        if (StringUtils.isEmpty(accessToken)) {
            return null;
        }

        return getUser(accessToken);
    }

    /**
     * 上传文件到静态服务，返回文件链接
     * @param request
     * @param baseUser
     * @return
     */
    @Override
    public String uploadUserImage(HttpServletRequest request,BaseUser baseUser) {
        String res = localStaticResourceService.upload(request, baseUser);
        int updateRes = updateImage(new String[]{res}, baseUser);
        return res;
    }


}
