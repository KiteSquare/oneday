package com.oneday.service.utils;

import com.oneday.constant.EducationEnum;
import com.oneday.constant.IncomeEnum;
import com.oneday.constant.MarriageEnum;
import com.oneday.domain.po.HunterReceiver;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.Candidate;
import com.oneday.domain.vo.UserDisplay;

import java.util.Date;

/**
 * 对象转换
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

    public static UserDisplay convertUserToUserDisplay(User user) {
        UserDisplay userDisplay = new UserDisplay();
        if (user == null) {
            return userDisplay;
        }
        userDisplay.setName(user.getName());
        userDisplay.setSignature(user.getSignature());
        userDisplay.setPhone(user.getPhone());
        userDisplay.setSex(user.getSex());
        userDisplay.setHeight(user.getHeight());
        if (user.getBirth() == null) {
            userDisplay.setAge(0);
        } else {
            userDisplay.setAge(DateUtil.getAge(user.getBirth()));
        }
        userDisplay.setEducation(EducationEnum.getStrByIndex(user.getEducation()));
        userDisplay.setIndustry(user.getIndustry());
        userDisplay.setIncome(IncomeEnum.getStrByIndex(user.getIncome()));
        userDisplay.setHead(user.getHead());
        userDisplay.setDetail(user.getDetail());
        userDisplay.setDistance(0d);
        userDisplay.setMarriage(MarriageEnum.getStrByIndex(user.getMarriage()));
        return userDisplay;
    }

}
