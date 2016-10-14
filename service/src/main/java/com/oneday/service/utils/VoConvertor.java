package com.oneday.service.utils;

import com.oneday.domain.po.HunterReceiver;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.Candidate;

import java.util.Date;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/10/13 18:28
 */
public class VoConvertor {
    public static Candidate convert(User user, HunterReceiver hunterReceiver) {
        Candidate candidate = convert(user);
        candidate.setCandStatus(hunterReceiver.getStatus());
        candidate.setChangeUpdateTime(new Date(hunterReceiver.getUpdate()));
        return candidate;
    }
    public static Candidate convert(User user) {
        Candidate candidate = new Candidate();
        candidate.setId(user.getId());
        candidate.setStatus(user.getStatus());
        candidate.setBirth(user.getBirth());
        candidate.setCity(user.getCity());
        candidate.setCreate(user.getCreate());
        candidate.setDetail(user.getDetail());
        candidate.setHead(user.getHead());
        candidate.setIdcard(user.getIdcard());
        candidate.setLat(user.getLat());
        candidate.setLon(user.getLon());
        candidate.setName(user.getName());
        candidate.setPhone(user.getPhone());
        candidate.setStatus(user.getStatus());
        return candidate;
    }

}
