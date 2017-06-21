package com.oneday.service.impl;

import com.oneday.constant.ErrorCodeEnum;
import com.oneday.constant.SexEnum;
import com.oneday.constant.StateEnum;
import com.oneday.dao.HunterReceiverDao;
import com.oneday.dao.UserDao;
import com.oneday.domain.po.HunterReceiver;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.*;
import com.oneday.exceptions.OndayException;
import com.oneday.service.AssociateService;
import com.oneday.service.UserService;
import com.oneday.service.state.Machine;
import com.oneday.utils.VoConvertor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/9/13 17:42
 */
@Service("associateService")
public class AssociateServiceImpl implements AssociateService{
    private static final Logger logger = LoggerFactory.getLogger(AssociateServiceImpl.class);
    @Resource
    UserDao userDao;
    @Resource
    HunterReceiverDao hunterReceiverDao;
    @Resource
    UserService userService;
    @Resource
    Machine machine;
    /**
     * 发送
     *
     * @param accessToken
     * @param targetUserId
     */
    @Transactional
    public void send(String accessToken, Long targetUserId) {
        BaseUser senderBase = userService.getUser(accessToken);
        if (senderBase == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(),"请登录后再操作");
        }

        Map<Long, User> relateUserMap = userDao.getMapByIds(senderBase.getId(), targetUserId);
        User user = relateUserMap.get(senderBase.getId());
        User targetUser = relateUserMap.get(targetUserId);
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "发送者用户信息为空");
        }
        if (targetUser == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "接收者用户信息为空");
        }
        this._checkIsMale(user);
        this._checkIsFemale(targetUser);
        user.setStatus(machine.hunterSend(user.getStatus()));
        targetUser.setStatus(machine.receiverReceive(targetUser.getStatus()));
        // 状态
        HunterReceiver hunterReceiver = new HunterReceiver();
        hunterReceiver.setStatus(StateEnum.SEND.getStatus());
        hunterReceiver.setHunter(senderBase.getId());
        hunterReceiver.setReceiver(targetUserId);
        Long time = (System.currentTimeMillis()/1000);
        hunterReceiver.setCreate(time);
        hunterReceiver.setUpdate(time);
        try {
            int res = hunterReceiverDao.add(hunterReceiver);
            if (res <= 0) {
                throw new RuntimeException(String.format("Insert db fail sender %s, receiver %s, status %s", senderBase.getId(), targetUserId, hunterReceiver.getStatus()));
            }
        } catch (DuplicateKeyException  e) {
            logger.info(String.format("send fail, user[%s] duplicated sending to user[%s]", senderBase.getId(), targetUserId), e);
            throw new OndayException(ErrorCodeEnum.STATE_SEND_DUPLICATE_ERROR.getCode(), ErrorCodeEnum.STATE_SEND_DUPLICATE_ERROR.getValue());
        }

        // 更新用户状态
        Date now = new Date();
        user.setUpdate(now);
        targetUser.setUpdate(now);
        userDao.update(user);
        userDao.update(targetUser);
        // TODO 推送接收消息通知

    }

    /**
     * 接受
     *
     * @param accessToken
     * @param targetUserId
     */
    @Transactional
    public void accept(String accessToken, Long targetUserId) {
        BaseUser senderBase = userService.getUser(accessToken);
        if (senderBase == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(),"请登录后再操作");
        }
        Map<Long, User> relateUserMap = userDao.getMapByIds(senderBase.getId(), targetUserId);
        User user = relateUserMap.get(senderBase.getId());
        User targetUser = relateUserMap.get(targetUserId);
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "当前用户信息为空");
        }
        if (targetUser == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "接受对象用户信息为空");
        }
        this._checkIsFemale(user);
        this._checkIsMale(targetUser);
        HunterReceiverParam param = new HunterReceiverParam();
        param.setReceiver(senderBase.getId());
        param.setLessStatus(StateEnum.ADMIT.getStatus());
        param.setMaxStatus(StateEnum.ACCEPT.getStatus());

        //需要拒绝的用户
        User rejectUser = null;

        //查询已经接受或承认的用户
        List<HunterReceiver> hunters = hunterReceiverDao.list(param);
        if (hunters.size() > 1) {
            throw new OndayException(ErrorCodeEnum.STATE_ERROR.getCode(), "操作失败，查不到对方信息，请稍后重试");
        } else if (!hunters.isEmpty()) {
            HunterReceiver acceptedHunter = hunters.get(0);
            if (acceptedHunter.getStatus().equals(StateEnum.ADMIT.getStatus())) {
                throw new OndayException(ErrorCodeEnum.STATE_ERROR.getCode(), "您已经锁定了对象，无法再接受了");
            }
            if (acceptedHunter.getHunter().equals( targetUserId)) {
                throw new OndayException(ErrorCodeEnum.STATE_SEND_DUPLICATE_ERROR.getCode(),"已经接受过他啦");
            }
            rejectUser = userDao.get(acceptedHunter.getHunter());
        }
        // 更新所有状态
        _acceptOneAndRejectOthers(rejectUser, user, targetUser);

        // TODO 推送接受和拒绝消息通知

    }

    /**
     *
     * @param rejectUser
     * @param user
     * @param acceptUser
     */
    protected void _acceptOneAndRejectOthers(User rejectUser, User user, User acceptUser) {
        Set<Long> rejectUids = new HashSet<Long>();
        List<User> updateUsers = new ArrayList<User>();
        // 设置用户状态
        Date update = new Date();
        user.setStatus(machine.receiverAccept(user.getStatus()));
        user.setUpdate(update);
        acceptUser.setStatus(machine.hunterAccept(acceptUser.getStatus()));
        acceptUser.setUpdate(update);
        updateUsers.add(_buildUpdateUserPo(user));
        updateUsers.add(_buildUpdateUserPo(acceptUser));
        if (rejectUser != null) {
            rejectUser.setStatus(machine.hunterReject(rejectUser.getStatus()));
            rejectUids.add(rejectUser.getId());
            rejectUser.setUpdate(update);
            updateUsers.add(_buildUpdateUserPo(rejectUser));
        }


        // 更新数据库关系状态

        // 更新与接受者的状态
        int num1 = hunterReceiverDao.updateStatusByHunterAndReceiver(StateEnum.ACCEPT.getStatus(), acceptUser.getId(), user.getId());

        // 更新其他人的拒绝信息
        if (! rejectUids.isEmpty()) {
            int num2 = hunterReceiverDao.updateStatusByReceivers(StateEnum.REJECT.getStatus(),user.getId(), rejectUids);
        }

        for (User u: updateUsers) {
            int num3 = userDao.update(u);
        }

    }

    private User _buildUpdateUserPo(User user) {
        User po = new User();
        po.setId(user.getId());
        po.setStatus(user.getStatus());
        po.setUpdate(user.getUpdate());
        return po;
    }

    /**
     * 拒绝
     *
     * @param accessToken 用户token
     * @param targetUserId 拒绝的对象id
     */
    @Transactional
    public void reject(String accessToken, Long targetUserId) {
        BaseUser senderBase = userService.getUser(accessToken);
        if (senderBase == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(),"请登录后再操作");
        }

        Map<Long, User> relateUserMap = userDao.getMapByIds(senderBase.getId(), targetUserId);
        User user = relateUserMap.get(senderBase.getId());
        User targetUser = relateUserMap.get(targetUserId);
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "当前用户信息为空");
        }
        if (targetUser == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "拒绝对象用户不存在");
        }
        this._checkIsFemale(user);
        this._checkIsMale(targetUser);

        user.setStatus(machine.receiverReject(user.getStatus()));
        targetUser.setStatus(machine.hunterReject(targetUser.getStatus()));
        HunterReceiverParam param = new HunterReceiverParam();
        param.setReceiver(senderBase.getId());
//        param.setHunter(targetUserId);
        List<Integer> instatus = new ArrayList<>(3);
        instatus.add(StateEnum.SEND.getStatus());
        instatus.add(StateEnum.ACCEPT.getStatus());
        instatus.add(StateEnum.ADMIT.getStatus());
        param.setInstatus(instatus);

        int sendNum = hunterReceiverDao.candidateCount(param);
        if (sendNum == 0) {
            throw new OndayException(ErrorCodeEnum.USER_HUNTER_INVALID.getCode(),
                   "状态异常，该用户不是你的追求者");
        } else if (sendNum == 1) {
            throw new OndayException(ErrorCodeEnum.STATE_RECEIVER_CANT_REJECT_ERROR.getCode(),
                    String.format("%s是您现在唯一的追求者哦，不要就这么拒绝他", targetUser.getName()));
        }

        // 更新拒绝状态
        int num = hunterReceiverDao.updateStatusByHunterAndReceiver(StateEnum.REJECT.getStatus(), targetUserId, senderBase.getId());
        if (num <= 0) {
            throw new OndayException(ErrorCodeEnum.STATE_ERROR.getCode(),
                    "已经拒绝过啦");
        }
        // TODO 推送拒绝消息通知
    }

    /**
     * 承认
     *
     * @param accessToken
     */
    @Transactional
    public void admit(String accessToken) {
        BaseUser baseUser = userService.getUser(accessToken);
        if (baseUser == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(),"请登录后再操作");
        }
        User user = userDao.get(baseUser.getId());
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "用户不存在");
        }
        User hunter = userService.getAcceptedUser(baseUser.getId());
        if (hunter == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "你还没有接受他哦");
        }
        user.setStatus(machine.receiverAdmit(user.getStatus()));
        hunter.setStatus(machine.hunterAdmit(hunter.getStatus()));
        // 更新承认状态
        // 更新拒绝状态
        int num = hunterReceiverDao.updateStatusByHunterAndReceiver(StateEnum.ADMIT.getStatus(), hunter.getId(), baseUser.getId());
        // 更新用户状态
        Date now = new Date();
        user.setUpdate(now);
        hunter.setUpdate(now);
        int num1 = userDao.update(user);
        int num2 = userDao.update(hunter);
        // TODO 推送承认消息
    }

    /**
     *
     * @param accessToken
     * @param currentPage
     * @param count
     * @return
     */
    public UserInfo getUserInfo(String accessToken , Integer currentPage, Integer count) {
        if (StringUtils.isEmpty(accessToken)) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "user id is invalid");
        }
        UserInfo result = new UserInfo();
        UserDisplay user = userService.getUserDetail(accessToken, 0L);
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(),"用户不存在");
        }
        result.setUser(user);
        Page<Candidate> candidatePage = candidates(user.getId(), user.getSex(), currentPage, count);
        if (candidatePage.getCount()>0) {
            for (Candidate candidate: candidatePage.getData()) {
                if (candidate.getCandStatus() != StateEnum.NOTHING.getStatus() && candidate.getCandStatus() <= StateEnum.ACCEPT.getStatus()) {
                    result.setAcceptedUser(candidate);
                    candidatePage.getData().remove(candidate);
                    break;
                }
            }
        }
        result.setHistory(candidatePage);
        return result;
    }



    /**
     * 用户历史关系信息
     *
     * @param userId
     * @return
     */
    public Page<Candidate> candidates(Long userId, Integer sex , Integer currentPage, Integer count) {
        if (userId == null || userId <= 0) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "user id is invalid");
        }
        if (sex == null || sex < 0) {
            User user = userDao.get(userId);
            if (user == null) {
                throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), String.format("User id [%s] not found.",
                        userId));
            }
            sex = user.getSex();
        }
        Page<Candidate> page = new Page<>(currentPage, count);
        List<Candidate> candidatesList = new ArrayList<>();
        page.setData(candidatesList);

        HunterReceiverParam param = new HunterReceiverParam();
        List<HunterReceiver> data = null;
        Map<Long, User> candidateUserMap = null;

        if (sex.equals(SexEnum.MAN.getSex())) {
            param.setHunter(userId);
            page.setTotal(hunterReceiverDao.candidateCount(param));
            data = hunterReceiverDao.getReceiverList(userId, page.getIndex(), page.getCount());

            if (data != null) {
                List<Long> ids = new ArrayList<>();
                for (HunterReceiver hr: data) {
                    ids.add(hr.getReceiver());
                }
                if (ids.size() > 0) {
                    candidateUserMap = userDao.getMapByIds(ids);
                    for (HunterReceiver hr: data) {
                        User u = candidateUserMap.get(hr.getReceiver());
                        if (u == null ) {
                            logger.warn(String.format("candidate receiver %s is not found in user db", hr.getReceiver()));
                            continue;
                        }
                        candidatesList.add(VoConvertor.convert(u, hr));
                    }
                }
            }
        } else if (sex.equals(SexEnum.FEMALE.getSex())) {
            param.setReceiver(userId);
            page.setTotal(hunterReceiverDao.candidateCount(param));
            data = hunterReceiverDao.getSenderList(userId, page.getIndex(), page.getCount());
            if (data != null) {
                List<Long> ids = new ArrayList<>();
                for (HunterReceiver hr: data) {
                    ids.add(hr.getHunter());
                }
                if (ids.size() > 0) {
                    candidateUserMap = userDao.getMapByIds(ids);
                    for (HunterReceiver hr: data) {
                        User u = candidateUserMap.get(hr.getHunter());
                        if (u == null ) {
                            logger.warn(String.format("candidate hunter %s is not found in user db", hr.getHunter()));
                            continue;
                        }
                        candidatesList.add(VoConvertor.convert(u, hr));
                    }
                }

            }
        }

        return page;
    }

    /**
     * 查询用户之间关系
     *
     * @param accessToken
     * @param targetUserId
     * @return
     */
    @Override
    public Relation relation(String accessToken, Long targetUserId) {
        Relation relation = new Relation();
        if (StringUtils.isEmpty(accessToken)) {
            User targetUser = userDao.get(targetUserId);
            relation.setTargetUser(VoConvertor.convertUserToUserDisplay(targetUser));
        } else {
            BaseUser baseUser = userService.getUser(accessToken);
            if (baseUser == null) {
                throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(),"请登录后再操作");
            }
            Map<Long, User> userMap = userDao.getMapByIds(baseUser.getId(),targetUserId);
            User user = userMap.get(baseUser.getId());
            User targetUser = userMap.get(targetUserId);
            relation.setCurrentUser(VoConvertor.convertUserToUserDisplay(user));
            relation.setTargetUser(VoConvertor.convertUserToUserDisplay(targetUser));
            HunterReceiverParam param = new HunterReceiverParam();
            if (user.isMale()) {
                param.setHunter(baseUser.getId());
                param.setReceiver(targetUserId);
            } else {
                param.setHunter(targetUserId);
                param.setReceiver(baseUser.getId());
            }
            List<HunterReceiver> res = hunterReceiverDao.getByWhere(param);
            if (res != null && !res.isEmpty()) {
                HunterReceiver hunterReceiver = res.get(0);
                relation.setRelation(hunterReceiver);
            }
        }

        return relation;
    }

    /**
     *
     * @param user
     */
    protected void _checkIsMale(User user) {
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "User is null.");
        }
        if (!user.isMale()) {
            throw new OndayException(ErrorCodeEnum.USER_SEX_INVALID.getCode(), "User is not male.");
        }
    }

    /**
     *
     * @param user
     */
    protected void _checkIsFemale(User user) {
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "User is null.");
        }
        if (!user.isFemale()) {
            throw new OndayException(ErrorCodeEnum.USER_SEX_INVALID.getCode(), "User is not female.");
        }
    }

}
