package com.oneday.service;

import com.oneday.domain.po.HunterReceiver;
import com.oneday.domain.vo.Candidate;
import com.oneday.domain.vo.Page;
import com.oneday.domain.vo.Relation;
import com.oneday.domain.vo.UserInfo;

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
     * @param userId 用户id
     * @param currentPage 开始行数
     * @param count 每页数量
     * @return
     */
    UserInfo getUserInfo(Long userId , Integer currentPage, Integer count);

    /**
     * 查询用户之间关系
     * @param userId
     * @param targetUserId
     * @return
     */
    Relation relation(Long userId , Long targetUserId);
}
