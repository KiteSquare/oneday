package com.oneday.service.impl;

import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.po.Topic;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.BaseUser;
import com.oneday.domain.vo.Page;
import com.oneday.domain.vo.TopicParam;
import com.oneday.domain.vo.request.CreateTopicRequest;
import com.oneday.domain.vo.request.GetTopicRequest;
import com.oneday.domain.vo.request.RecommendTopicRequest;
import com.oneday.exceptions.OndayException;
import com.oneday.mapper.TopicDao;
import com.oneday.service.TopicService;
import com.oneday.service.UserService;
import com.oneday.utils.VoConvertor;
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
        Integer res = topicDao.add(topic);
        return res;
    }

    @Override
    public Topic get(GetTopicRequest request) {
        return topicDao.getById(request.getId());
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
    public Page<Topic> recommend(RecommendTopicRequest request) {
        BaseUser user = null;
        if (!StringUtils.isEmpty(request.getAccessToken())) {
            user = userService.getUser(request.getAccessToken());
            if (user == null || user.getId() == null) {
                throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "请登录");
            }
        }
        Page<Topic> result = new Page<>(request.getCurrentPage(), request.getPageNum());
        TopicParam param = new TopicParam();
        param.setIndex(result.getIndex());
        param.setPageNum(request.getPageNum());
        param.setOrderBy("weight desc,`update` desc");
        param.setStartWeight(0);
        param.setEndUpdated(new Date());
        List<Topic> data = topicDao.getByWhere(param);
        result.setData(data);
        result.setTotal(topicDao.getCount(param));
        return result;
    }
}
