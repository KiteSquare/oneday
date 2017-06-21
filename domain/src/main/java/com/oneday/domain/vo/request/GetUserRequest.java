package com.oneday.domain.vo.request;

import com.oneday.domain.vo.BaseRequest;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/14 17:18
 */
public class GetUserRequest extends BaseRequest {
    private Long uid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
