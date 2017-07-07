package com.oneday.service.impl;

import com.oneday.common.util.GeocodeUtil;
import com.oneday.common.util.Validator;
import com.oneday.constant.ConfigConstant;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.po.Topic;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.BaseUser;
import com.oneday.domain.vo.Page;
import com.oneday.domain.vo.TopicDetail;
import com.oneday.domain.vo.TopicParam;
import com.oneday.domain.vo.request.CreateTopicRequest;
import com.oneday.domain.vo.request.GetTopicRequest;
import com.oneday.domain.vo.request.RecommendTopicRequest;
import com.oneday.exceptions.OndayException;
import com.oneday.mapper.TopicDao;
import com.oneday.service.TopicService;
import com.oneday.service.UserService;
import com.oneday.utils.VoConvertor;
import com.spatial4j.core.io.GeohashUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/21 14:58
 */
@Service("topicService")
public class TopicServiceImpl implements TopicService {
    @Resource
    protected UserService userService;
    @Resource
    protected TopicDao topicDao;
    @Override
    public Integer create(CreateTopicRequest request) {
        BaseUser user = userService.getUser(request.getAccessToken());
        if (user == null || user.getId() == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "请登录");
        }
        Topic topic = VoConvertor.convert(request, user);
        Date now = new Date();
        topic.setCreate(now);
        topic.setUpdate(now);
        topic.setYn(0);
        topic.setWeight(0);
        topic.setCount(0);
        topic.setLat(request.getLat());
        topic.setLon(request.getLon());
        topic.setCityCode(request.getCityCode());
        topic.setCity(request.getCity());
        if (request.getLat() != null && request.getLon() != null) {
            topic.setGeocode(GeohashUtils.encodeLatLon( request.getLat(),request.getLon(),ConfigConstant.GEOCODE_LENGTH_MAX));
        }
        //html转码处理
        topic.setContent(Validator.escapContentString(topic.getContent()));
        Integer res = topicDao.add(topic);
        return res;
    }

    @Override
    public TopicDetail get(GetTopicRequest request) {
        return VoConvertor.convert(topicDao.getById(request.getId()), request.getLat(), request.getLon());
    }

    @Override
    public Topic getByWhere(Topic param) {
        return null;
    }

    @Override
    public Integer update(Topic topic) {
        return null;
    }

    @Override
    public Page<TopicDetail> recommend(RecommendTopicRequest request) {
        BaseUser user = null;
        if (!StringUtils.isEmpty(request.getAccessToken())) {
            user = userService.getUser(request.getAccessToken());
            if (user == null || user.getId() == null) {
                throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "请登录");
            }
        }
        Page<TopicDetail> result = new Page<>(request.getCurrentPage(), request.getPageNum());
        TopicParam param = new TopicParam();
        param.setIndex(result.getIndex());
        param.setPageNum(request.getPageNum());
        param.setOrderBy("weight desc,`update` desc");
        param.setStartWeight(0);
        param.setEndUpdated(new Date());
        param.setAdjacentGeocodes(GeocodeUtil.adjacentGeocodes(request.getLat(), request.getLon(), ConfigConstant.GEOCODE_LENGTH_MAX));
        if (!StringUtils.isEmpty(request.getCityCode())) {
            param.setCityCode(request.getCityCode());
        }
        List<Topic> data = topicDao.getByWhere(param);

        result.setData(VoConvertor.convert(data, request.getLat(), request.getLon()));
        result.setTotal(topicDao.getCount(param));
        return result;
    }
}
