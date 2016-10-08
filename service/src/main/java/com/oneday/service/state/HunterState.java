package com.oneday.service.state;

/**
 * 状态接口
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 15:56
 */
public interface HunterState {
    /**
     * 发送追求
     * @return
     */
    Integer send();

    /**
     * 拒绝
     * @return
     */
    Integer reject();

    /**
     * 接受
     * @return
     */
    Integer accept();

    /**
     * 承认
     * @return
     */
    Integer admit();
}
