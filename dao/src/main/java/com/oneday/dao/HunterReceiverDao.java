package com.oneday.dao;

import com.oneday.domain.po.HunterReceiver;
import com.oneday.domain.po.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 13:47
 */
public interface HunterReceiverDao extends Dao<HunterReceiver, Long> {
    Map<Long, HunterReceiver> getMap(HunterReceiver param);
    int updateStatusByReceivers(Integer status, Set<Long> uids);
    int updateStatusByReceiver(Integer status, Long uid);
    int updateStatusByHunterAndReceiver(Integer status, Long hunter, Long receiver);

    List<HunterReceiver> getReceiverList(Long senderId, Integer index, Integer count);

    int candidateCount(HunterReceiver param);

    List<HunterReceiver> getSenderList(Long receiverId, Integer index, Integer count);

}
