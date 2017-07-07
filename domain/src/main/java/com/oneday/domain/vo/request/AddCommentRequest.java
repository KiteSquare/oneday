package com.oneday.domain.vo.request;

import com.oneday.domain.vo.BaseRequest;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/21 15:08
 */
public class AddCommentRequest extends BaseRequest {
    /**
     * 帖子id
     */
    protected Long topicId;
    /**
     * 被回复用户id
     */
    protected Integer replyfloor;
    /**
     * 被回复用户id
     */
    protected Long replyUid;
    /**
     * 评论内容
     */
    protected String content;

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getReplyUid() {
        return replyUid;
    }

    public void setReplyUid(Long replyUid) {
        this.replyUid = replyUid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReplyfloor() {
        return replyfloor;
    }

    public void setReplyfloor(Integer replyfloor) {
        this.replyfloor = replyfloor;
    }
}
