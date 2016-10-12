package com.oneday.service.impl;

import com.oneday.constant.ErrorCodeEnum;
import com.oneday.constant.StateEnum;
import com.oneday.dao.HunterReceiverDao;
import com.oneday.dao.UserDao;
import com.oneday.domain.HunterReceiver;
import com.oneday.domain.User;
import com.oneday.exceptions.OndayException;
import com.oneday.service.AssociateService;
import com.oneday.service.state.Machine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        this._checkStatus(user);
        this._checkIsFemale(targetUser);
        user.setStatus(machine.hunterSend(user.getStatus()));
        targetUser.setStatus(machine.receiverReceive(targetUser.getStatus()));
        // 状态
        HunterReceiver hunterReceiver = new HunterReceiver();
        hunterReceiver.setStatus(StateEnum.SEND.getStatus());
        hunterReceiver.setHunter(userId);
        hunterReceiver.setReceiver(targetUserId);
        Long time = System.currentTimeMillis();
        hunterReceiver.setCreate(time);
        hunterReceiver.setUpdate(time);
        int res = hunterReceiverDao.add(hunterReceiver);
        // 保存
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
        HunterReceiver param = new HunterReceiver();
        param.setReceiver(userId);
        Map<Long, HunterReceiver> hunters = hunterReceiverDao.getMap(param);
        if (! hunters.containsKey(targetUserId)) {
            throw new OndayException(ErrorCodeEnum.USER_HUNTER_INVALID.getCode(),
                    String.format("User id [%s] is not the hunter of user id[%s] yet.", targetUserId, userId));
        }
        // 查出所有追求者的用户信息
        Set<Long> hunterSet = hunters.keySet();
        hunterSet.remove(targetUserId);
        relateUserMap.putAll(userDao.getMapByIds(hunterSet));
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
        user.setStatus(machine.receiverAccept(user.getStatus()));
        targetUser.setStatus(machine.hunterAccept(targetUser.getStatus()));
        updateUsers.add(user);
        updateUsers.add(targetUser);

        Date update = new Date();

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
            updateUsers.add(user1);
        }


        // 更新数据库关系状态
        int num1 = hunterReceiverDao.updateStatusByReceiver(StateEnum.ACCEPT.getStatus(), targetUser.getId());
        int num2 = hunterReceiverDao.updateStatusByReceivers(StateEnum.REJECT.getStatus(),rejectUids);
        int num3 = userDao.batchUpdate(updateUsers);
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
        user.setStatus(machine.hunterReject(user.getStatus()));
        targetUser.setStatus(machine.hunterReject(targetUser.getStatus()));
        HunterReceiver param = new HunterReceiver();
        param.setReceiver(userId);
        param.setHunter(targetUserId);
        List<HunterReceiver> hunterReceivers = hunterReceiverDao.get(param);
        if (hunterReceivers == null || hunterReceivers.isEmpty()) {
            throw new OndayException(ErrorCodeEnum.USER_HUNTER_INVALID.getCode(),
                    String.format("User id [%s] is not the hunter of user id[%s] yet.", targetUserId, userId));
        }
        // 更新状态
        int num = hunterReceiverDao.updateStatusByHunterAndReceiver(StateEnum.REJECT.getStatus(), userId, targetUserId);
        // TODO 推送拒绝消息通知
    }

    /**
     * 承认
     *
     * @param userId
     */
    @Transactional
    public void admit(Long userId) {

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

    /**
     *
     * @param user
     */
    protected void _checkStatus(User user) {
        if (user == null) {
            throw new OndayException(ErrorCodeEnum.USER_NOT_FOUND.getCode(), "User is null.");
        }
        if (user.getStatus() <= 0) {
            throw new OndayException(ErrorCodeEnum.USER_HUNTER_INVALID.getCode(), "Status invalid.");
        }
    }
}
