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
}
