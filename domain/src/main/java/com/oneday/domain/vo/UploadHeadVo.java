package com.oneday.domain.vo;

import java.io.Serializable;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/9 15:42
 */
public class UploadHeadVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String data;
    private String fileName;
    private String fileType;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
