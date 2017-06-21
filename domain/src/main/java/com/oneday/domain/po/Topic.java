package com.oneday.domain.po;

import java.util.Date;

/**
 * 帖子信息
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/21 11:44
 */
public class Topic {
    /**
     * 帖子id
     */
    protected Long id;
    /**
     * 作者id
     */
    protected Long uid;
    /**
     * 分类id
     */
    protected Long category;
    /**
     * 创建者头像
     */
    protected String head;
    /**
     * 标题
     */
    protected String title;
    /**
     * 帖子内容
     */
    protected String content;
    /**
     * 帖子图片
     */
    protected String images;
    /**
     * 创建者昵称
     */
    protected String uname;
    /**
     * 权重
     */
    protected Integer weight;
    /**
     * 性别
     */
    protected Integer sex;
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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
