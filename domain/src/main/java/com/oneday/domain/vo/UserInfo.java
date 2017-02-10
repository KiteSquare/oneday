package com.oneday.domain.vo;

import com.oneday.domain.po.User;

import java.util.List;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/10/13 15:40
 */
public class UserInfo {
    /**
     * 当前用户
     */
    protected User user;

    /**
     * 接受的对象
     */
    protected Candidate acceptedUser;
    /**
     * 历史对象
     */
    protected Page<Candidate> history;

    protected Integer count;

    protected Integer total;

    protected Integer index;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Page<Candidate>getHistory() {
        return history;
    }

    public void setHistory(Page<Candidate> history) {
        this.history = history;
    }

    public Candidate getAcceptedUser() {
        return acceptedUser;
    }

    public void setAcceptedUser(Candidate acceptedUser) {
        this.acceptedUser = acceptedUser;
    }
}
