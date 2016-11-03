package com.oneday.service.impl;

import com.oneday.constant.ErrorCodeEnum;
import com.oneday.constant.SexEnum;
import com.oneday.constant.StateEnum;
import com.oneday.dao.HunterReceiverDao;
import com.oneday.dao.UserDao;
import com.oneday.domain.po.HunterReceiver;
import com.oneday.domain.po.User;
import com.oneday.domain.vo.Candidate;
import com.oneday.domain.vo.HunterReceiverParam;
import com.oneday.domain.vo.Page;
import com.oneday.exceptions.OndayException;
import com.oneday.service.AssociateService;
import com.oneday.service.UserService;
import com.oneday.service.state.Machine;
import com.oneday.service.utils.VoConvertor;
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
     * @param userId
     * @param targetUserId
     */
    @Transactional
    public void send(Long userId, Long targetUserId) {
        Map<Long, User> relateUserMap = userDao.getMapByIds(userId, targetUserId);
        User user = relateUserMap.get(userId);
        User targetUser = relateUserMap.get(targetUserId);
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), String.format("User id [%s] not found.", userId));
        }
        if (targetUser == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), String.format("User id [%s] not found.", targetUserId));
        }
        this._checkIsMale(user);
        this._checkIsFemale(targetUser);
        user.setStatus(machine.hunterSend(user.getStatus()));
        targetUser.setStatus(machine.receiverReceive(targetUser.getStatus()));
        // 状态
        HunterReceiver hunterReceiver = new HunterReceiver();
        hunterReceiver.setStatus(StateEnum.SEND.getStatus());
        hunterReceiver.setHunter(userId);
        hunterReceiver.setReceiver(targetUserId);
        Long time = (System.currentTimeMillis()/1000);
        hunterReceiver.setCreate(time);
        hunterReceiver.setUpdate(time);
        try {
            int res = hunterReceiverDao.add(hunterReceiver);
            if (res <= 0) {
                throw new RuntimeException(String.format("Insert db fail sender %s, receiver %s, status %s", userId, targetUserId, hunterReceiver.getStatus()));
            }
        } catch (DuplicateKeyException  e) {
            logger.info(String.format("send fail, user[%s] duplicated sending to user[%s]", userId, targetUserId), e);
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
     * @param userId
     * @param targetUserId
     */
    @Transactional
    public void accept(Long userId, Long targetUserId) {
        Map<Long, User> relateUserMap = userDao.getMapByIds(userId, targetUserId);
        User user = relateUserMap.get(userId);
        User targetUser = relateUserMap.get(targetUserId);
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), String.format("User id [%s] not found.",
                    userId));
        }
        if (targetUser == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), String.format("User id [%s] not found.",
                    targetUserId));
        }
        this._checkIsFemale(user);
        this._checkIsMale(targetUser);
        HunterReceiverParam param = new HunterReceiverParam();
        param.setReceiver(userId);
        param.setLessStatus(StateEnum.ADMIT.getStatus());
        param.setMaxStatus(StateEnum.REJECT.getStatus());


        Map<Long, HunterReceiver> hunters = hunterReceiverDao.getHunterMap(param);
        if (! hunters.containsKey(targetUserId)) {
            throw new OndayException(ErrorCodeEnum.USER_HUNTER_INVALID.getCode(),
                    String.format("User id [%s] is not the hunter of user id[%s] yet.", targetUserId, userId));
        }
        // 查出所有追求者的用户信息
        Set<Long> hunterSet = hunters.keySet();
        hunterSet.remove(targetUserId);
        if (!hunters.isEmpty()) {
            relateUserMap.putAll(userDao.getMapByIds(hunterSet));
        }
        // 更新所有状态
        _acceptOneAndRejectOthers(hunters, relateUserMap, user, targetUser);

        // TODO 推送接受和拒绝消息通知

    }

    /**
     *
     * @param hunters
     * @param userMap
     * @param user
     * @param targetUser
     */
    protected void _acceptOneAndRejectOthers(Map<Long, HunterReceiver> hunters, Map<Long, User> userMap,User user, User targetUser) {
        Set<Long> rejectUids = new HashSet<Long>();
        List<User> updateUsers = new ArrayList<User>();
        // 设置用户状态
        Date update = new Date();
        user.setStatus(machine.receiverAccept(user.getStatus()));
        user.setUpdate(update);
        targetUser.setStatus(machine.hunterAccept(targetUser.getStatus()));
        targetUser.setUpdate(update);
        updateUsers.add(_buildUpdateUserPo(user));
        updateUsers.add(_buildUpdateUserPo(targetUser));



        for (Long uid: hunters.keySet()) {
            if (uid.equals(targetUser.getId())) {
                // 被接受的用户
                continue;
            }
            User user1 = userMap.get(uid);
            user1.setUpdate(update);

            // 其他用户都需要拒绝, ^_^
            rejectUids.add(uid);
            user1.setStatus(machine.hunterReject(user1.getStatus()));
            updateUsers.add(_buildUpdateUserPo(user1));
        }


        // 更新数据库关系状态

        // 更新与接受者的状态
        int num1 = hunterReceiverDao.updateStatusByHunterAndReceiver(StateEnum.ACCEPT.getStatus(), targetUser.getId(), user.getId());

        // 更新其他人的拒绝信息
        if (! rejectUids.isEmpty()) {
            int num2 = hunterReceiverDao.updateStatusByReceivers(StateEnum.REJECT.getStatus(),rejectUids);
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
     * @param userId
     * @param targetUserId
     */
    @Transactional
    public void reject(Long userId, Long targetUserId) {
        Map<Long, User> relateUserMap = userDao.getMapByIds(userId, targetUserId);
        User user = relateUserMap.get(userId);
        User targetUser = relateUserMap.get(targetUserId);
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), String.format("User id [%s] not found.",
                    userId));
        }
        if (targetUser == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), String.format("User id [%s] not found.",
                    targetUserId));
        }
        this._checkIsFemale(user);
        this._checkIsMale(targetUser);

        user.setStatus(machine.receiverReject(user.getStatus()));
        targetUser.setStatus(machine.hunterReject(targetUser.getStatus()));
        HunterReceiverParam param = new HunterReceiverParam();
        param.setReceiver(userId);
        param.setHunter(targetUserId);
        param.setStatus(StateEnum.SEND.getStatus());

        int sendNum = hunterReceiverDao.candidateCount(param);
        if (sendNum == 0) {
            throw new OndayException(ErrorCodeEnum.USER_HUNTER_INVALID.getCode(),
                    String.format("User id [%s] is not the hunter of user id[%s] yet.", targetUserId, userId));
        } else if (sendNum == 1) {
            throw new OndayException(ErrorCodeEnum.STATE_RECEIVER_CANT_REJECT_ERROR.getCode(),
                    String.format("User id [%s] is the only hunter of user id[%s]. It can not be rejected now", targetUserId, userId));
        }

        // 更新拒绝状态
        int num = hunterReceiverDao.updateStatusByHunterAndReceiver(StateEnum.REJECT.getStatus(), userId, targetUserId);
        if (num <= 0) {
            throw new OndayException(ErrorCodeEnum.STATE_ERROR.getCode(),
                    String.format("User id [%s] has been rejected by user id[%s].", targetUserId, userId));
        }
        // TODO 推送拒绝消息通知
    }

    /**
     * 承认
     *
     * @param userId
     */
    @Transactional
    public void admit(Long userId) {
        if (userId == null) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "user id can not be null");
        }
        User user = userDao.get(userId);
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), String.format("User id [%s] not found.",
                    userId));
        }
        User hunter = userService.getAcceptedUser(userId);
        if (hunter == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), String.format("The accepted hunter of user[%s] not found.",
                    userId));
        }
        user.setStatus(machine.receiverAdmit(user.getStatus()));
        hunter.setStatus(machine.hunterAdmit(hunter.getStatus()));
        // 更新承认状态
        // 更新拒绝状态
        int num = hunterReceiverDao.updateStatusByHunterAndReceiver(StateEnum.ADMIT.getStatus(), hunter.getId(), userId);
        // 更新用户状态
        Date now = new Date();
        user.setUpdate(now);
        hunter.setUpdate(now);
        int num1 = userDao.update(user);
        int num2 = userDao.update(hunter);
        // TODO 推送承认消息
    }

    /**
     * 用户关系信息
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
