package com.oneday.domain.vo;

import com.oneday.domain.po.Topic;

import java.util.Date;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 * 2017/6/21 16:35
 */
public class TopicParam extends Topic {
    protected Integer index;

    protected Integer pageNum;

    protected String orderBy;

    protected Date startUpdated;

    protected Date endUpdated;

    protected Integer startWeight;

    protected Integer endWeight;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Date getStartUpdated() {
        return startUpdated;
    }

    public void setStartUpdated(Date startUpdated) {
        this.startUpdated = startUpdated;
    }

    public Date getEndUpdated() {
        return endUpdated;
    }

    public void setEndUpdated(Date endUpdated) {
        this.endUpdated = endUpdated;
    }

    public Integer getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(Integer startWeight) {
        this.startWeight = startWeight;
    }

    public Integer getEndWeight() {
        return endWeight;
    }

    public void setEndWeight(Integer endWeight) {
        this.endWeight = endWeight;
    }
}
