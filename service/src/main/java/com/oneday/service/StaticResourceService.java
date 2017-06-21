package com.oneday.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/16 15:23
 */
public interface StaticResourceService {
    /**
     * 上传文件到静态服务，返回文件链接
     * @param request
     * @return
     */
    String upload(HttpServletRequest request);

}
