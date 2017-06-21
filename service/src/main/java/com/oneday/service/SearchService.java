package com.oneday.service;

import com.oneday.domain.po.User;
import com.oneday.domain.vo.BaseUser;
import com.oneday.domain.vo.Location;
import com.oneday.domain.vo.Page;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2017/1/19 15:36
 */
public interface SearchService {
    Page<User> nearBy(Integer distance, BaseUser user);
    Page<User> nearBy(Integer distance, String accessToken) ;
}
