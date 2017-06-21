package com.oneday.utils;

import com.alibaba.fastjson.JSONObject;
import com.oneday.common.util.DateUtil;
import com.oneday.common.util.MD5Util;
import com.oneday.constant.EducationEnum;
import com.oneday.constant.IncomeEnum;
import com.oneday.constant.MarriageEnum;
import com.oneday.constant.SexEnum;
import com.oneday.domain.po.HunterReceiver;
import com.oneday.domain.po.Topic;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.*;
import com.oneday.domain.vo.request.CreateTopicRequest;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        candidate.setSex(user.getSex());
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

    public static User convert(UserRegistVo userVo, int step) {
        User user = new User();
        if (step == 2) {
            user.setIdcard(userVo.getIdcard());
            user.setLat(userVo.getLat());
            user.setLon(userVo.getLon());
            user.setPhone(userVo.getPhone());
            user.setPassword(MD5Util.generate(userVo.getPassword().trim()));
        }

        return user;
    }

    public static BaseUser convertBaseUser(User user) {
        BaseUser res = new BaseUser();
        if (user != null) {
            res.setId(user.getId());
            res.setName(user.getName());
            res.setPhone(user.getPhone());
            res.setHead(user.getHead());
            res.setSex(user.getSex());
        }

        return res;
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
        userDisplay.setSexDisplay(SexEnum.getSexDesc(user.getSex()));
        userDisplay.setHeight(user.getHeight());
        userDisplay.setWeight(user.getWeight());
        if (user.getWeight() != null) {
            userDisplay.setWeightDisplay(user.getWeight()+"kg");
        }
        if (user.getHeight() != null) {
            userDisplay.setHeightDisplay(user.getHeight()+"cm");
        }

        userDisplay.setId(user.getId());
        if (user.getBirth() == null) {
            userDisplay.setAge(0);
        } else {
            userDisplay.setAge(DateUtil.getAge(user.getBirth()));
            userDisplay.setAgeDisplay(userDisplay.getAge()+"岁");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            userDisplay.setBirth(formatter.format(user.getBirth()));
        }

        userDisplay.setEducation(user.getEducation());
        userDisplay.setEducationDisplay(EducationEnum.getStrByIndex(user.getEducation()));
        userDisplay.setIndustry(user.getIndustry());
        userDisplay.setIncome(user.getIncome());
        userDisplay.setIncomeDisplay(IncomeEnum.getStrByIndex(user.getIncome()));
        userDisplay.setHead(user.getHead());
        userDisplay.setDetail(user.getDetail());
        userDisplay.setDistance(0d);
        userDisplay.setMarriage(user.getMarriage());
        userDisplay.setMarriageDisplay(MarriageEnum.getStrByIndex(user.getMarriage()));
        userDisplay.setCity(user.getCity());
        userDisplay.setProvinceCode(user.getProvinceCode());
        userDisplay.setProvince(user.getProvince());
        userDisplay.setCityCode(user.getCityCode());
        if (!StringUtils.isEmpty(user.getImages())) {
            List<ImageVo> imageVos = JSONObject.parseArray(user.getImages(), ImageVo.class);
            List<String> images = new ArrayList<>();
            String host = PropertyPlaceholder.getProperty("url.static.source", "http://static.xwutong.com/");
            for (ImageVo vo: imageVos) {
                if (!vo.getUrl().startsWith("http")) {
                    images.add(host+vo.getUrl());
                } else {
                    images.add(vo.getUrl());
                }

            }
            userDisplay.setImages(images);
        }
        userDisplay.setRequirement(user.getRequirement());
        return userDisplay;
    }

    public static Topic convert(CreateTopicRequest request, BaseUser user) {
        Topic topic = new Topic();
        topic.setCategory(request.getCategory());
        topic.setTitle(request.getTitle());
        topic.setContent(request.getContent());
        if (!StringUtils.isEmpty(request.getImages())) {
            topic.setImages(request.getImages());
        }
        topic.setUid(user.getId());
        topic.setUname(user.getName());
        topic.setSex(user.getSex());
        topic.setHead(user.getHead());
        return topic;
    }

}
