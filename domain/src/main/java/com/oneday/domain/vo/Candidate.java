package com.oneday.domain.vo;

import com.oneday.domain.po.User;

import java.util.Date;

/**
 * 候选人对象
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/10/13 16:34
 */
public class Candidate extends User {
    /**
     * 1 承认(admit), 2 接受(accept), 4 发送(send)，8 拒绝(reject)
     */
    protected Integer candStatus;
    /**
     * 状态变化时间
     */
    protected Date changeUpdateTime;

    public Integer getCandStatus() {
        return candStatus;
    }

    public void setCandStatus(Integer candStatus) {
        this.candStatus = candStatus;
    }

    public Date getChangeUpdateTime() {
        return changeUpdateTime;
    }

    public void setChangeUpdateTime(Date changeUpdateTime) {
        this.changeUpdateTime = changeUpdateTime;
    }
}
