package com.oneday.service.impl;

import com.oneday.common.util.Validator;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.po.Comment;
import com.oneday.domain.po.Topic;
import com.oneday.domain.vo.BaseUser;
import com.oneday.domain.vo.CommentDetail;
import com.oneday.domain.vo.CommentParam;
import com.oneday.domain.vo.Page;
import com.oneday.domain.vo.request.AddCommentRequest;
import com.oneday.domain.vo.request.DeleteCommentRequest;
import com.oneday.domain.vo.request.GetCommentsRequest;
import com.oneday.exceptions.OndayException;
import com.oneday.mapper.CommentDao;
import com.oneday.mapper.TopicDao;
import com.oneday.service.CommentService;
import com.oneday.service.UserService;
import com.oneday.utils.VoConvertor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/22 11:20
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentDao commentDao;
    @Resource
    private TopicDao topicDao;
    @Resource
    private UserService userService;
    /**
     * 增加评论
     *
     * @param request
     * @return
     */
    @Override
    public Comment add(AddCommentRequest request, BaseUser user) {
        return checkTopicAndAdd(request, user);
    }
    @Transactional
    public Comment checkTopicAndAdd(AddCommentRequest request, BaseUser user ) {
        Topic topic = topicDao.getCountById(request.getTopicId());
        if (topic == null) {
            throw new OndayException(ErrorCodeEnum.TOPIC_NOT_FOUND.getCode(), "主题不存在");
        }
        topic.setCount(topic.getCount()+1);
        Comment comment = VoConvertor.convert(request, user);
        comment.setFloor(topic.getCount() );
        Date time = new Date();
        comment.setCreate(time);
        comment.setUpdate(time);
        //html转码处理
        comment.setContent(Validator.escapContentString(comment.getContent()));
        comment.setLat(request.getLat());
        comment.setLon(request.getLon());
        topic.setUpdate(time);
        Integer res = commentDao.add(comment);
        Integer resTopic = topicDao.update(topic);
        return comment;
    }

    /**
     * 查询评论
     *
     * @param request
     * @return
     */
    @Override
    public Page<CommentDetail> get(GetCommentsRequest request,BaseUser user ) {

        Page<CommentDetail> result = new Page<>(request.getCurrentPage(), request.getPageNum(), request.getIndex());
        CommentParam param = new CommentParam();
        param.setTopicId(request.getTopicId());
        param.setIndex(result.getIndex());
        param.setPageNum(request.getPageNum());
        param.setOrderBy("id");
        List<Comment> data = commentDao.getByWhere(param);
        result.setData(VoConvertor.convertCommentList(data, request.getLat(), request.getLon()));
        result.setTotal(commentDao.getCount(param));
        return result;
    }

    /**
     * 查询单条评论
     *
     * @param param
     * @return
     */
    @Override
    public CommentDetail getByWhere(Comment param) {
        return null;
    }

    /**
     * 删除评论
     *
     * @param request
     * @return
     */
    @Override
    public Integer delete(DeleteCommentRequest request) {
        return null;
    }
}
