package com.oneday.vo;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/10/12 18:11
 */
public class AcceptRequestVo {
    protected Long userId;
    protected Long targetUserId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }
}
