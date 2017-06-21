package com.oneday.service;

import com.oneday.domain.po.Topic;
import com.oneday.domain.vo.Page;
import com.oneday.domain.vo.request.CreateTopicRequest;
import com.oneday.domain.vo.request.GetTopicRequest;
import com.oneday.domain.vo.request.RecommendTopicRequest;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/21 14:57
 */
public interface TopicService {
    Integer create(CreateTopicRequest request);
    Topic get(GetTopicRequest request);
    Page<Topic> recommend(RecommendTopicRequest request);
    Topic getByWhere(Topic param);
    Integer update(Topic topic);
}
