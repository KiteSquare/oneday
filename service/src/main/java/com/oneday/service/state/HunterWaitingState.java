package com.oneday.service.state;

import com.oneday.constant.ErrorCodeEnum;
import com.oneday.constant.HunterEnum;
import com.oneday.exceptions.OndayException;

/**
 * 追求者等待状态
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 16:06
 */
public class HunterWaitingState implements HunterState {
    /**
     * 发送追求
     * @return
     */
    @Override
    public Integer send() {
        throw new OndayException(ErrorCodeEnum.STATE_ERROR.getCode(), "Hunter can not send on waiting candStatus");

    }

    /**
     * 拒绝
     * @return
     */
    @Override
    public Integer reject() {
        return HunterEnum.SINGLE.getStatus();
    }

    /**
     * 接受
     * @return
     */
    @Override
    public Integer accept() {
        return HunterEnum.HOLD.getStatus();
    }

    /**
     * 承认
     * @return
     */
    @Override
    public Integer admit() {
        throw new OndayException(ErrorCodeEnum.STATE_ERROR.getCode(), "Hunter can not admit on waiting candStatus");
    }
}
