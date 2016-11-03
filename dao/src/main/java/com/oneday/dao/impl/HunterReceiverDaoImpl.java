package com.oneday.dao.impl;

import com.oneday.dao.HunterReceiverDao;
import com.oneday.domain.po.HunterReceiver;
import com.oneday.domain.vo.HunterReceiverParam;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 15:38
 */
@Repository("hunterReceiverDao")
public class HunterReceiverDaoImpl extends  DaoImpl<HunterReceiver, Long> implements HunterReceiverDao {
    private final static String NAMESPACE = "com.oneday.dao.HunterReceiverDao.";
    @Override
    public Map<Long, HunterReceiver> getMap(HunterReceiverParam param) {
        List<HunterReceiver> list = this.list(param);
        Map<Long, HunterReceiver> map = new HashMap<Long, HunterReceiver>();
        if (list != null) {
            for (HunterReceiver hunterReceiver: list) {
                map.put(hunterReceiver.getId(), hunterReceiver);
            }
        }
        return map;
    }

    public List<HunterReceiver> list(HunterReceiverParam po) {
        return sqlSessionTemplate.selectList(getNameSpace(STATEMENT_GET), po);
    }

    @Override
    public Map<Long, HunterReceiver> getHunterMap(HunterReceiverParam param) {
        List<HunterReceiver> list = this.list(param);
        Map<Long, HunterReceiver> map = new HashMap<Long, HunterReceiver>();
        if (list != null) {
            for (HunterReceiver hunterReceiver: list) {
                map.put(hunterReceiver.getHunter(), hunterReceiver);
            }
        }
        return map;
    }

    @Override
    public Map<Long, HunterReceiver> getReceiverMap(HunterReceiverParam param) {
        List<HunterReceiver> list = this.list(param);
        Map<Long, HunterReceiver> map = new HashMap<Long, HunterReceiver>();
        if (list != null) {
            for (HunterReceiver hunterReceiver: list) {
                map.put(hunterReceiver.getReceiver(), hunterReceiver);
            }
        }
        return map;
    }

    @Override
    public List<HunterReceiver> getByWhere(HunterReceiverParam param) {
        return sqlSessionTemplate.selectList(getNameSpace("getByWhere"), param);
    }

    @Override
    public int updateStatusByReceivers(Integer status, Set<Long> uids) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("status", status);
        params.put("uids", uids);
        params.put("update", System.currentTimeMillis()/1000);
        return sqlSessionTemplate.update(getNameSpace("updateStatusByReceivers"), params);
    }

    @Override
    public int updateStatusByReceiver(Integer status, Long uid) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("status", status);
        params.put("receiver", uid);
        params.put("update", System.currentTimeMillis()/1000);
        return sqlSessionTemplate.update(getNameSpace("updateStatusByReceiver"), params);
    }

    @Override
    public int updateStatusByHunterAndReceiver(Integer status, Long hunter, Long receiver) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", status);
        params.put("hunter", hunter);
        params.put("receiver", receiver);
        params.put("update", System.currentTimeMillis()/1000);
        return sqlSessionTemplate.update(getNameSpace("updateStatusByHunterAndReceiver"), params);
    }

    public String getNameSpace(String name) {
        return NAMESPACE + name;
    }

    public List<HunterReceiver> getReceiverList(Long senderId, Integer index, Integer count) {
        Map<String, Object> params = new HashMap<>();
        params.put("hunter", senderId);
        params.put("index", index);
        params.put("count", count);
        return sqlSessionTemplate.selectList(getNameSpace("getReceiverList"), params);
    }

    public int candidateCount(HunterReceiverParam param) {
        return sqlSessionTemplate.selectOne(getNameSpace("selectCount"), param);
    }

    public List<HunterReceiver> getSenderList(Long receiver, Integer index, Integer count) {
        Map<String, Object> params = new HashMap<>();
        params.put("receiver", receiver);
        params.put("index", index);
        params.put("count", count);
        return sqlSessionTemplate.selectList(getNameSpace("getHunterList"), params);
    }
}
