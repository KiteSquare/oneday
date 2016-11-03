package com.oneday.service.state;

/**
 * 接受者行为接口
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 15:56
 */
public interface ReceiverState {
    /**
     * 接收追求
     * @return 目标状态值
     */
    Integer receive();

    /**
     * 拒绝
     * @return
     */
    Integer reject();

    /**
     * 接受
     * @return 目标状态值
     */
    Integer accept();
    /**
     * 承认
     * @return 目标状态值
     */
    Integer admit();
}
