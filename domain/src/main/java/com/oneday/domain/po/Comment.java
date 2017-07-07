package com.oneday.domain.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 * 2017/6/22 10:24
 */
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 评论id
     */
    protected Long id;
    /**
     * 帖子id
     */
    protected Long topicId;
    /**
     * 评论用户id
     */
    protected Long uid;
    /**
     * 楼层
     */
    protected Integer floor;
    /**
     * 楼层
     */
    protected Integer replyfloor;
    /**
     * 被回复用户id
     */
    protected Long replyUid;
    /**
     * 评论用户昵称
     */
    protected String uname;
    /**
     * 评论用户头像
     */
    protected String head;
    /**
     * 评论用户性别
     */
    protected Integer sex;
    /**
     * 图片
     */
    protected String images;
    /**
     * 评论内容
     */
    protected String content;

    /**
     * 纬度
     */
    protected Double lat;
    /**
     * 经度
     */
    protected Double lon;
    /**
     * 创建时间
     */
    protected Date create;
    /**
     * 更新时间
     */
    protected Date update;

    protected Integer yn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getReplyUid() {
        return replyUid;
    }

    public void setReplyUid(Long replyUid) {
        this.replyUid = replyUid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getReplyfloor() {
        return replyfloor;
    }

    public void setReplyfloor(Integer replyfloor) {
        this.replyfloor = replyfloor;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
