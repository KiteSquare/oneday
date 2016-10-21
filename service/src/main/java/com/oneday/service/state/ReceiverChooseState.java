package com.oneday.service.state;

import com.oneday.constant.ErrorCodeEnum;
import com.oneday.constant.ReceiverEnum;
import com.oneday.exceptions.OndayException;

/**
 * 接受者选择状态
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 16:06
 */
public class ReceiverChooseState implements ReceiverState {

    /**
     * 接收追求
     * @return
     */
    @Override
    public Integer receive() {
        return ReceiverEnum.CHOOSE.getStatus();
    }

    /**
     * 接受
     * @return
     */
    @Override
    public Integer accept() {
        return ReceiverEnum.HOLD.getStatus();
    }

    /**
     * 承认
     * @return
     */
    @Override
    public Integer admit() {
        throw new OndayException(ErrorCodeEnum.STATE_ERROR.getCode(), "Receiver can not admit on single status");
    }
}
