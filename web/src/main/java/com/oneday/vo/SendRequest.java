package com.oneday.vo;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/10/12 18:11
 */
public class SendRequest {
    protected Long senderId;
    protected Long receiverId;

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
}
