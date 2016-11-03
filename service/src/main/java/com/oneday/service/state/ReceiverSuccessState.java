package com.oneday.service.state;

import com.oneday.constant.ErrorCodeEnum;
import com.oneday.constant.ReceiverEnum;
import com.oneday.exceptions.OndayException;

/**
 * 接受者成功状态
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 16:06
 */
public class ReceiverSuccessState implements ReceiverState {

    /**
     * 接收追求
     */
    @Override
    public Integer receive() {
        throw new OndayException(ErrorCodeEnum.STATE_ALREADY_SUCCESS.getCode(), "Receiver already success");
    }

    /**
     * 拒绝
     *
     * @return
     */
    @Override
    public Integer reject() {
        return ReceiverEnum.SUCCESS.getStatus();
    }

    /**
     * 接受
     */
    @Override
    public Integer accept() {
        throw new OndayException(ErrorCodeEnum.STATE_ERROR.getCode(), "Receiver can not accept on success status");
    }

    /**
     * 承认
     */
    @Override
    public Integer admit() {
        throw new OndayException(ErrorCodeEnum.STATE_ERROR.getCode(), "Receiver can not admit on success status");
    }
}
