package com.oneday.domain.vo;

import java.util.List;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/10/13 16:57
 */
public class Page<T> {
    /**
     * 每页数量
     */
    protected Integer count;
    /**
     * 总数
     */
    protected Integer total;
    /**
     * 开始行
     */
    protected Integer index;
    /**
     * 数据
     */
    protected List<T> data;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
