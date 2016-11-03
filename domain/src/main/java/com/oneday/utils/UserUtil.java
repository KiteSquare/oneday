package com.oneday.utils;

import com.oneday.domain.po.User;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/11/3 16:15
 */
public class UserUtil {

    public static User safeUserInfo(User user) {
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public static User cope(User user1, User user2) {
        if (user2 == null) {
            return user1;
        }
        if (user1 == null) {
            return null;
        }

        if (user2.getId() != null) {
            user1.setId(user2.getId());
        }

        if (user2.getCount() != null) {
            user1.setCount(user2.getCount());
        }

        if (user2.getName() != null) {
            user1.setName(user2.getName());
        }
        if (user2.getSex() != null) {
            user1.setSex(user2.getSex());
        }
        if (user2.getPassword() != null) {
            user1.setPassword(user2.getPassword());
        }
        if (user2.getBirth() != null) {
            user1.setBirth(user2.getBirth());
        }
        if (user2.getCity() != null) {
            user1.setCity(user2.getCity());
        }
        if (user2.getCreate() != null) {
            user1.setCreate(user2.getCreate());
        }
        if (user2.getDetail() != null) {
            user1.setDetail(user2.getDetail());
        }
        if (user2.getHead() != null) {
            user1.setHead(user2.getHead());
        }
        if (user2.getIdcard() != null) {
            user1.setIdcard(user2.getIdcard());
        }
        if (user2.getPhone() != null) {
            user1.setPhone(user2.getPhone());
        }
        if (user2.getLat() != null) {
            user1.setLat(user2.getLat());
        }
        if (user2.getLon() != null) {
            user1.setLon(user2.getLon());
        }
        if (user2.getSignature() != null) {
            user1.setSignature(user2.getSignature());
        }
        if (user2.getStatus() != null) {
            user1.setStatus(user2.getStatus());
        }
        if (user2.getYn() != null) {
            user1.setYn(user2.getYn());
        }
        return user1;
    }
}
