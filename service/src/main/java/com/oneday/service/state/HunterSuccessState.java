package com.oneday.service.state;

import com.oneday.constant.ErrorCodeEnum;
import com.oneday.exceptions.OndayException;

/**
 * 追求者成功状态
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 16:06
 */
public class HunterSuccessState implements HunterState {
    /**
     * 发送追求
     * @return
     */
    @Override
    public Integer send() {
        throw new OndayException(ErrorCodeEnum.STATE_ERROR.getCode(), "Hunter can not send on success candStatus");
    }

    /**
     * 拒绝
     * @return
     */
    @Override
    public Integer reject() {
        throw new OndayException(ErrorCodeEnum.STATE_ERROR.getCode(), "Hunter can not send on success candStatus");
    }

    /**
     * 接受
     * @return
     */
    @Override
    public Integer accept() {
        throw new OndayException(ErrorCodeEnum.STATE_ERROR.getCode(), "Hunter can not send on success candStatus");
    }

    /**
     * 承认
     * @return
     */
    @Override
    public Integer admit() {
        throw new OndayException(ErrorCodeEnum.STATE_ERROR.getCode(), "Hunter can not send on success candStatus");
    }
}
