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
     * 当前页码
     */
    protected int currentPage;
    /**
     * 数据
     */
    protected List<T> data;


    protected String unit = "条";

    protected Object extInfo;

    public Page(){}

    public Page(Integer currentPage, Integer count){
        this.currentPage = currentPage;
        this.count = count;
        this.index = (getCurrentPage() - 1) * this.count;
    }

    public int getEndIndex()
    {
        return getCurrentPage() * getCount();
    }

    public boolean isFirstPage()
    {
        return getCurrentPage() <= 1;
    }

    public boolean isLastPage()
    {
        return getCurrentPage() >= getPageCount();
    }

    public int getNextPage()
    {
        if (isLastPage()) {
            return getCurrentPage();
        }
        return getCurrentPage() + 1;
    }

    public int getPreviousPage()
    {
        if (isFirstPage()) {
            return 1;
        }
        return getCurrentPage() - 1;
    }

    public int getCurrentPage()
    {
        if (this.currentPage == 0) {
            this.currentPage = 1;
        }
        return this.currentPage;
    }

    public int getPageCount()
    {
        if (this.total % this.count == 0) {
            return this.total / this.count;
        }
        return this.total / this.count + 1;
    }

    public boolean hasNextPage()
    {
        return getCurrentPage() < getPageCount();
    }

    public boolean hasPreviousPage()
    {
        return getCurrentPage() > 1;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Object getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Object extInfo) {
        this.extInfo = extInfo;
    }

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
