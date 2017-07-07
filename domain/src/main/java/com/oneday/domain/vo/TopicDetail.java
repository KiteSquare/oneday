package com.oneday.domain.vo;

import com.oneday.domain.po.Topic;

import java.util.List;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/22 10:02
 */
public class TopicDetail extends Topic{

    protected String time;

    protected String distance;

    protected List<String> imagesList;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<String> imagesList) {
        this.imagesList = imagesList;
    }
}
