package com.oneday.service.state;

import com.oneday.constant.ErrorCodeEnum;
import com.oneday.constant.HunterEnum;
import com.oneday.constant.ReceiverEnum;
import com.oneday.exceptions.OndayStateException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 状态机
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0  2016/9/7 16:01
 */
@Service("machine")
public class Machine {
    /**
     * 追求者状态集合map
     */
    private Map<Integer, HunterState> hunterStateMap = new HashMap<Integer, HunterState>();
    /**
     * 接受者状态集合map
     */
    private Map<Integer, ReceiverState> receiverStateMap = new HashMap<Integer, ReceiverState>();
    public Machine() {
        register();
    }

    /**
     * 注册所有状态
     */
    private void register() {
        hunterStateMap.put(HunterEnum.SINGLE.getStatus(), new HunterSingleState());
        hunterStateMap.put(HunterEnum.WAITING.getStatus(), new HunterWaitingState());
        hunterStateMap.put(HunterEnum.HOLD.getStatus(), new HunterHoldState());
        hunterStateMap.put(HunterEnum.SUCCESS.getStatus(), new HunterSuccessState());

        receiverStateMap.put(ReceiverEnum.SINGLE.getStatus(), new ReceiverSingleState());
        receiverStateMap.put(ReceiverEnum.CHOOSE.getStatus(), new ReceiverChooseState());
        receiverStateMap.put(ReceiverEnum.HOLD.getStatus(), new ReceiverHoldState());
        receiverStateMap.put(ReceiverEnum.SUCCESS.getStatus(), new ReceiverSuccessState());
    }


    /**
     *
     * @param status 当前状态
     * @return 目标状态
     */
    public Integer hunterSend(Integer status) {
        HunterState s = hunterStateMap.get(status);
        if (s != null) {
            return s.send();
        }
        throw new OndayStateException(ErrorCodeEnum.STATE_ERROR.getCode(),"Hunter candStatus " + status + " not found in machine");
    }
    /**
     *
     * @param status 当前状态
     * @return 目标状态
     */
    public Integer hunterReject(Integer status) {
        HunterState s = hunterStateMap.get(status);
        if (s != null) {
            return s.reject();
        }
        throw new OndayStateException(ErrorCodeEnum.STATE_ERROR.getCode(),"Hunter candStatus " + status + " not found in machine");
    }
    /**
     *
     * @param status 当前状态
     * @return 目标状态
     */
    public Integer hunterAccept(Integer status) {
        HunterState s = hunterStateMap.get(status);
        if (s != null) {
            return s.accept();
        }
        throw new OndayStateException(ErrorCodeEnum.STATE_ERROR.getCode(),"Hunter candStatus " + status + " not found in machine");
    }
    /**
     *
     * @param status 当前状态
     * @return 目标状态
     */
    public Integer hunterAdmit(Integer status) {
        HunterState s = hunterStateMap.get(status);
        if (s != null) {
            return s.admit();
        }
        throw new OndayStateException(ErrorCodeEnum.STATE_ERROR.getCode(),"Hunter candStatus " + status + " not found in machine");
    }

    /**
     *
     * @param status 当前状态
     * @return 目标状态
     */
    public Integer receiverReceive(Integer status) {
        ReceiverState s = receiverStateMap.get(status);
        if (s != null) {
            return s.receive();
        }
        throw new OndayStateException(ErrorCodeEnum.STATE_ERROR.getCode(),"Receiver candStatus " + status + " not found in machine");
    }

    /**
     *
     * @param status 当前状态
     * @return 目标状态
     */
    public Integer receiverReject(Integer status) {
        ReceiverState s = receiverStateMap.get(status);
        if (s != null) {
            return s.reject();
        }
        throw new OndayStateException(ErrorCodeEnum.STATE_ERROR.getCode(),"Receiver candStatus " + status + " not found in machine");
    }

    /**
     *
     * @param status 当前状态
     * @return 目标状态
     */
    public Integer receiverAccept(Integer status) {
        ReceiverState s = receiverStateMap.get(status);
        if (s != null) {
            return s.accept();
        }
        throw new OndayStateException(ErrorCodeEnum.STATE_ERROR.getCode(),"Receiver candStatus " + status + " not found in machine");
    }

    /**
     *
     * @param status 当前状态
     * @return 目标状态
     */
    public Integer receiverAdmit(Integer status) {
        ReceiverState s = receiverStateMap.get(status);
        if (s != null) {
            return s.admit();
        }
        throw new OndayStateException(ErrorCodeEnum.STATE_ERROR.getCode(),"Receiver candStatus " + status + " not found in machine");
    }
}
