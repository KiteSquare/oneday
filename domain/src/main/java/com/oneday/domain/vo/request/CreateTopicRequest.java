package com.oneday.domain.vo.request;

import com.oneday.domain.vo.BaseRequest;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/21 15:08
 */
public class CreateTopicRequest extends BaseRequest {
    /**
     * 分类id
     */
    protected Long category;
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
     * 城市
     */
    protected String city;
    /**
     * 城市code
     */
    protected String cityCode;
    /**
     * 纬度
     */
    protected Double lat;
    /**
     * 经度
     */
    protected Double lon;


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
