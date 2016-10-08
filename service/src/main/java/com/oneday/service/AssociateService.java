package com.oneday.service;

/**
 *
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/9/13 17:42
 */
public interface AssociateService {

    /**
     * 发送
     * @param userId
     * @param targetUserId
     */
    void send(Long userId, Long targetUserId);

    /**
     * 接受
     * @param userId
     * @param targetUserId
     */
    void accept(Long userId, Long targetUserId);

    /**
     * 拒绝
     * @param userId
     * @param targetUserId
     */
    void reject(Long userId, Long targetUserId);

    /**
     * 承认
     * @param userId
     */
    void admit(Long userId);
}
