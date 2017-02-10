package com.oneday.domain.vo;

import com.oneday.domain.po.User;

import java.util.List;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/2/7 11:29
 */
public class UserParam extends User {
    protected List<Long> blackIds;

    protected Integer index;

    protected Integer count;

    public List<Long> getBlackIds() {
        return blackIds;
    }

    public void setBlackIds(List<Long> blackIds) {
        this.blackIds = blackIds;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public Integer getCount() {
        return count;
    }

    @Override
    public void setCount(Integer count) {
        this.count = count;
    }
}
