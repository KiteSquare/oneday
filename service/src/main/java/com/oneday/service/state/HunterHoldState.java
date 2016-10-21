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
        throw new OndayException(ErrorCodeEnum.STATE_ERROR.getCode(), "Hunter can not send on hold status");
    }

    /**
     * 拒绝
     */
    @Override
    public Integer reject() {
        throw new OndayException(ErrorCodeEnum.STATE_ERROR.getCode(), "Hunter can not reject on hold status");
    }

    /**
     * 接受
     */
    @Override
    public Integer accept() {
        throw new OndayException(ErrorCodeEnum.STATE_ERROR.getCode(), "Hunter can not accept on hold status");
    }

    /**
     * 承认
     */
    @Override
    public Integer admit() {
        return HunterEnum.SUCCESS.getStatus();
    }
}
