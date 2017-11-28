package com.oneday.service.impl;

import com.oneday.constant.ErrorCodeEnum;
import com.oneday.constant.SexEnum;
import com.oneday.dao.HunterReceiverDao;
import com.oneday.dao.UserDao;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.BaseUser;
import com.oneday.domain.vo.Location;
import com.oneday.domain.vo.Page;
import com.oneday.domain.vo.UserParam;
import com.oneday.domain.vo.request.SearchRequest;
import com.oneday.exceptions.OndayException;
import com.oneday.service.SearchService;
import com.oneday.service.UserService;
import com.oneday.utils.VoConvertor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/1/19 15:52
 */
@Service("searchService")
public class SearchServiceImp implements SearchService {
    @Resource
    UserDao userDao;
    @Resource
    HunterReceiverDao hunterReceiverDao;

    @Resource
    UserService userService;

    /**
     * TODO 重写方法
     * @param distance
     * @param user
     * @return
     */
    @Override
    public Page<User> nearBy(Integer distance, BaseUser user) {
        Location location = new Location(0d,0d);
        List<Long> relatedUids = null;
        UserParam userParam = new UserParam();
        if (user != null) {
            relatedUids = hunterReceiverDao.getAllRelatedUids(user.getId(), SexEnum.isMale(user.getSex()));
            if (SexEnum.isMale(user.getSex())) {
                userParam.setSex(SexEnum.FEMALE.getSex());
            } else {
                userParam.setSex(SexEnum.MAN.getSex());
            }
        }
        if (relatedUids != null && !relatedUids.isEmpty()) {
            userParam.setBlackIds(relatedUids);
        }
        userParam.setIndex(0);
        int pageNum = 10;
        userParam.setPageNum(pageNum);
        List<User> users = userDao.getByWhere(userParam);
        Page<User> res = new Page<>(1, pageNum);
        res.setData(users);
        res.setTotal(users.size());
        return res;
    }

    /**
     * TODO 重写方法
     * @param distance
     * @param accessToken
     * @return
     */
    @Override
    public Page<User> nearBy(Integer distance, String accessToken) {
        Location location = new Location(0d,0d);
        if (accessToken == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "用户未登录");
        }
        BaseUser baseUser = userService.getUser(accessToken);
        if (baseUser == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "用户未登录");
        }
        return nearBy(distance, baseUser);
    }

    @Override
    public Page<User> search(SearchRequest request) {
        BaseUser baseUser = userService.getUser(request.getAccessToken());
        List<Long> relatedUids = null;
        if (baseUser != null) {
            relatedUids = hunterReceiverDao.getAllRelatedUids(baseUser.getId(), SexEnum.isMale(baseUser.getSex()));
        }
        UserParam userParam = new UserParam();
        if (relatedUids != null && !relatedUids.isEmpty()) {
            userParam.setBlackIds(relatedUids);
        }
        VoConvertor.convertSearchToParam(request, userParam);
        Page<User> res = new Page<>(request.getCurrentPage(), request.getPageNum(), request.getIndex());
        userParam.setIndex(res.getIndex());
        userParam.setPageNum(res.getCount());

        return null;
    }
}
