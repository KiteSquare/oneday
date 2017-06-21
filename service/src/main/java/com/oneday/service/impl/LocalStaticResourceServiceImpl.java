package com.oneday.service.impl;

import com.oneday.common.util.Uploader;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.BaseUser;
import com.oneday.exceptions.OndayException;
import com.oneday.service.StaticResourceService;
import com.oneday.service.UserService;
import com.oneday.utils.PropertyPlaceholder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/16 15:25
 */
@Service("localStaticResourceService")
public class LocalStaticResourceServiceImpl implements StaticResourceService {
    @Resource
    UserService userService;
    /**
     * 上传文件到静态服务，返回文件链接
     *
     * @param request
     * @return
     */
    @Override
    public String upload(HttpServletRequest request) {
        Uploader uploader = new Uploader(request);
        uploader.setBasePath(PropertyPlaceholder.getProperty("path.file.upload.prefix"));
        String res = null;
        try {
            uploader.upload();
            if (!"SUCCESS".equals(uploader.getState())) {
                throw new OndayException(ErrorCodeEnum.FILE_UPLOAD_FAIL.getCode(), uploader.getState());
            }
            res = uploader.getUrl();
        } catch (OndayException e) {
            throw e;
        } catch (Exception e) {
            throw new OndayException(ErrorCodeEnum.FILE_UPLOAD_FAIL.getCode(), "上传文件失败", e);
        }
        return res;
    }

}
