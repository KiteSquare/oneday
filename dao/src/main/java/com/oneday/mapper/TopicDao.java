package com.oneday.mapper;

import com.oneday.domain.po.Topic;
import com.oneday.domain.vo.TopicParam;

import java.util.List;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *  2017/6/21 14:38
 */
public interface TopicDao {
    Integer add(Topic topic);
    Topic getById(Long id);
    Topic getCountById(Long id);
    Topic getOneByWhere(TopicParam param);
    List<Topic> getByWhere(TopicParam param);
    Integer getCount(TopicParam topic);
    Integer update(Topic topic);
}
