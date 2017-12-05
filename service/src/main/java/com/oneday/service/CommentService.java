package com.oneday.service;

import com.oneday.domain.po.Comment;
import com.oneday.domain.po.Topic;
import com.oneday.domain.vo.BaseUser;
import com.oneday.domain.vo.CommentDetail;
import com.oneday.domain.vo.Page;
import com.oneday.domain.vo.request.*;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/21 14:57
 */
public interface CommentService {
    /**
     * 增加评论
     * @param request
     * @return
     */
    Comment add(AddCommentRequest request,BaseUser user);

    /**
     *
     * @param request
     * @param user
     * @return
     */
    Comment checkTopicAndAdd(AddCommentRequest request, BaseUser user );

    /**
     * 查询评论
     * @param request
     * @return
     */
    Page<CommentDetail> get(GetCommentsRequest request, BaseUser user);

    /**
     * 查询单条评论
     * @param param
     * @return
     */
    CommentDetail getByWhere(Comment param);

    /**
     * 删除评论
     * @param request
     * @return
     */
    Integer delete(DeleteCommentRequest request );
}
