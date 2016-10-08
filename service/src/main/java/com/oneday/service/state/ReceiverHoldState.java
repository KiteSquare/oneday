package com.oneday.service.state;

import com.oneday.constant.ReceiverEnum;

/**
 * 接受者暂定状态
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 16:06
 */
public class ReceiverHoldState implements ReceiverState {

    /**
     * 接收追求
     * @return
     */
    @Override
    public Integer receive() {
        return ReceiverEnum.HOLD.getStatus();
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
        return ReceiverEnum.SUCCESS.getStatus();
    }
}
