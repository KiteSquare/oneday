package com.oneday.domain.vo;

import com.oneday.domain.po.HunterReceiver;

import java.io.Serializable;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/5/12 16:14
 */
public class Relation implements Serializable {
    private static final long serialVersionUID = 1L;
    private UserDisplay currentUser;
    private UserDisplay targetUser;
    private HunterReceiver relation;

    public UserDisplay getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserDisplay currentUser) {
        this.currentUser = currentUser;
    }

    public UserDisplay getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(UserDisplay targetUser) {
        this.targetUser = targetUser;
    }

    public HunterReceiver getRelation() {
        return relation;
    }

    public void setRelation(HunterReceiver relation) {
        this.relation = relation;
    }
}
