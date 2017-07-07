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

    protected Integer pageNum;

    protected List<String> adjacentGeocodes;

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

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public List<String> getAdjacentGeocodes() {
        return adjacentGeocodes;
    }

    public void setAdjacentGeocodes(List<String> adjacentGeocodes) {
        this.adjacentGeocodes = adjacentGeocodes;
    }
}
