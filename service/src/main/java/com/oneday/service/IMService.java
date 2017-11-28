package com.oneday.service;

import com.alibaba.fastjson.JSONObject;
import com.oneday.domain.po.User;

/**
 * 第三方IM聊天服务
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/7/11 16:11
 */
public interface IMService {
    JSONObject create(User user);
    JSONObject update(User user);
    JSONObject refresh(User user);
    JSONObject block(User user);
    JSONObject unblock(User user);
}
