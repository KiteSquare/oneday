package com.oneday.dao;

import com.oneday.domain.po.HunterReceiver;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.HunterReceiverParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 13:47
 */
public interface HunterReceiverDao extends Dao<HunterReceiver, Long> {
    Map<Long, HunterReceiver> getMap(HunterReceiverParam param);
    List<HunterReceiver> list(HunterReceiverParam param) ;

    Map<Long, HunterReceiver> getHunterMap(HunterReceiverParam param);
    Map<Long, HunterReceiver> getReceiverMap(HunterReceiverParam param);
    int updateStatusByReceivers(Integer status, Long receiver,  Set<Long> uids);
    int updateStatusByReceiver(Integer status, Long uid);
    int updateStatusByHunterAndReceiver(Integer status, Long hunter, Long receiver);

    List<HunterReceiver> getReceiverList(Long senderId, Integer index, Integer count);
    List<HunterReceiver> getByWhere(HunterReceiverParam param);

    int candidateCount(HunterReceiverParam param);

    List<HunterReceiver> getSenderList(Long receiverId, Integer index, Integer count);

    /**
     *
     * @param userId
     * @param isHunter
     * @return
     */
    List<Long> getAllRelatedUids(Long userId, boolean isHunter);
}
