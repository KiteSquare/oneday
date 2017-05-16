package com.oneday.service.state;

import com.oneday.constant.ErrorCodeEnum;
import com.oneday.constant.HunterEnum;
import com.oneday.exceptions.OndayException;

/**
 * 追求者暂定状态
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 16:06
 */
public class HunterHoldState implements HunterState {
    /**
     * 发送追求
     */
    @Override
    public Integer send() {
        throw new OndayException(ErrorCodeEnum.STATE_HUNTER_SEND_ON_HOLD_ERROR.getCode(), "发送失败，已经有人接受你了哦");
    }

    /**
     * 拒绝
     */
    @Override
    public Integer reject() {
//        throw new OndayException(ErrorCodeEnum.STATE_REJECT_ILLEGAL_ERROR.getCode(), ErrorCodeEnum.STATE_REJECT_ILLEGAL_ERROR.getValue());
        return HunterEnum.SINGLE.getStatus();
    }

    /**
     * 接受
     */
    @Override
    public Integer accept() {
        throw new OndayException(ErrorCodeEnum.STATE_ACCEPT_DUPLICATED_ERROR.getCode(), ErrorCodeEnum.STATE_ACCEPT_DUPLICATED_ERROR.getValue());
    }

    /**
     * 承认
     */
    @Override
    public Integer admit() {
        return HunterEnum.SUCCESS.getStatus();
    }
}
