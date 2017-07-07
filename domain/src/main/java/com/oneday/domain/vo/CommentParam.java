package com.oneday.domain.vo;

import com.oneday.domain.po.Comment;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/22 14:32
 */
public class CommentParam extends Comment {
    protected Integer index;

    protected Integer pageNum;

    protected String orderBy;

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
}
