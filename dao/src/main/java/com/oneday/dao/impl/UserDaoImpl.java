package com.oneday.dao.impl;

import com.oneday.dao.UserDao;
import com.oneday.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/9/7 13:48
 */
@Repository("userDao")
public class UserDaoImpl extends DaoImpl<User, Long> implements UserDao {
    private final static String NAMESPACE = "com.oneday.dao.UserDao.";
    @Override
    public Map<Long, User> getMapByIds(List<Long> uids) {
        List<User> res = sqlSessionTemplate.selectList(getNameSpace("getByIds"), uids);
        Map<Long, User> map = new HashMap<Long, User>();
        if (res != null) {
            for (User user: res) {
                map.put(user.getId(), user);
            }
        }
        return map;
    }

    @Override
    public Map<Long, User> getMapByIds(Set<Long> uids) {
        List<Long> uidlist = new ArrayList<>();
        uidlist.addAll(uids);
        return getMapByIds(uidlist);
    }

    @Override
    public Map<Long, User> getMapByIds(Long... uids) {
        List<Long> uidlist = new ArrayList<>();
        for (Long id: uids) {
            uidlist.add(id);
        }
        return getMapByIds(uidlist);
    }

    @Override
    public int batchUpdate(List<User> users) {
        return batchSqlSessionTemplate.update(getNameSpace("batchUpdate"), users);
    }

    @Override
    public String getNameSpace(String name) {
        return NAMESPACE + name;
    }

    @Override
    public int updateByPhone(User user) {
        return sqlSessionTemplate.update(getNameSpace("updateByPhone"), user);
    }

    @Override
    public User getByPhone(String phone) {
        return sqlSessionTemplate.selectOne(getNameSpace("getByPhone"), phone);
    }
}
