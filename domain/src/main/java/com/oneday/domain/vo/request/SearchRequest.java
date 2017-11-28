package com.oneday.domain.vo.request;

import com.oneday.domain.vo.BaseRequest;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/15 10:53
 */
public class SearchRequest extends BaseRequest {
    /**
     * 省份代码
     */
    protected String provinceCode;
    /**
     * 城市代码
     */
    protected String cityCode;
    /**
     * 性别
     */
    protected Integer sex;
    /**
     * 收入范围  参见IncomeEnum
     */
    protected Integer income;
    /**
     * 学历 0 小学以下，1 小学， 2 初中， 3 高中，4 中专，5 大专，6 本科，7 硕士，8 博士
     * 参见EducationEnum
     */
    protected Integer education;
    /**
     * 婚姻状况
     * 参考 MarriageEnum
     */
    protected Integer marriage;

    protected Integer index;
    protected Integer pageNum;
    protected Integer currentPage;

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getMarriage() {
        return marriage;
    }

    public void setMarriage(Integer marriage) {
        this.marriage = marriage;
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

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
