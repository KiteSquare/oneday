package com.oneday.dao;

import com.oneday.domain.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/9/7 13:47
 */
public interface UserDao extends Dao<User, Long> {
    Map<Long, User> getMapByIds(Set<Long> uids);
    Map<Long, User> getMapByIds(Long... uids);

    int batchUpdate(List<User> users);
}
