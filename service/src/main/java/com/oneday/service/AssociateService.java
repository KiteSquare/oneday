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
     * @param accessToken
     * @param targetUserId
     */
    void send(String accessToken, Long targetUserId);

    /**
     * 接受
     * @param accessToken
     * @param targetUserId
     */
    void accept(String accessToken, Long targetUserId);

    /**
     * 拒绝
     * @param accessToken
     * @param targetUserId
     */
    void reject(String accessToken, Long targetUserId);

    /**
     * 承认
     * @param accessToken
     */
    void admit(String accessToken);

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
     * @param accessToken 用户accessToken
     * @param currentPage 开始行数
     * @param count 每页数量
     * @return
     */
    UserInfo getUserInfo(String accessToken , Integer currentPage, Integer count);

    /**
     * 查询用户之间关系
     * @param accessToken
     * @param targetUserId
     * @return
     */
    Relation relation(String accessToken , Long targetUserId);
}
