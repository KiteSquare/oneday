package com.oneday.domain.vo.request;

import com.oneday.domain.vo.BaseRequest;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/10/12 18:11
 */
public class AcceptRequest extends BaseRequest {
    protected Long targetUserId;

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }
}
