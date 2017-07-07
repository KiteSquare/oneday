package com.oneday.mapper;

import com.oneday.domain.po.Comment;
import com.oneday.domain.vo.CommentParam;

import java.util.List;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/22 11:09
 */
public interface CommentDao {
    Integer add(Comment comment);
    Comment getById(Long id);
    Comment getOneByWhere(CommentParam param);
    List<Comment> getByWhere(CommentParam param);
    Integer getCount(CommentParam topic);
    Integer update(Comment comment);
}
