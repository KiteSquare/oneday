package com.oneday.service;

import com.oneday.domain.po.HunterReceiver;
import com.oneday.domain.vo.*;

import java.util.List;

/**
 *
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/9/13 17:42
 */
public interface AssociateService {

    /**
     * 发送
     * @param user
     * @param targetUserId
     */
    void send(BaseUser user, Long targetUserId);

    /**
     * 接受
     * @param user
     * @param targetUserId
     */
    void accept(BaseUser user, Long targetUserId);

    /**
     * 拒绝
     * @param user
     * @param targetUserId
     */
    void reject(BaseUser user, Long targetUserId);

    /**
     * 承认
     * @param user
     */
    void admit(BaseUser user);

    /**
     * 候选人列表
     * @param userId 用户id
     * @param sex  性别
     * @param index  开始行数
     * @param count  每页数量
     * @return
     */
    Page<Candidate> candidates(Long userId,Integer sex , Integer index, Integer count);

    /**
     * 获取用户信息和用户历史关系用户的信息
     * @param user
     * @param currentPage
     * @param count
     * @return
     */
    UserInfo getUserInfo(BaseUser user, Integer currentPage, Integer count);

    /**
     * 查询用户之间关系
     * @param user
     * @param targetUserId
     * @return
     */
    Relation relation(BaseUser user , Long targetUserId);
}
