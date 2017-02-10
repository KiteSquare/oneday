package com.oneday.controller;

import com.oneday.common.domain.Result;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.exceptions.OndayException;
import com.oneday.service.AssociateService;
import com.oneday.vo.AcceptRequestVo;
import com.oneday.vo.AdmitRequestVo;
import com.oneday.vo.RejectRequestVo;
import com.oneday.vo.SendRequestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/9/9 17:12
 */
@Controller
@RequestMapping(value = "/oneday/willow")
public class WillowController {
    private static Logger logger = LoggerFactory.getLogger(WillowController.class);
    @Resource
    AssociateService associateService;

    /**
     *
     * @param sendRequest
     * @return
     */
    @RequestMapping(value = "/send", method = {RequestMethod.POST })
    @ResponseBody
    public  Result send(@RequestBody SendRequestVo sendRequest) {
        Result result = new Result();
        try {
            _validateRequest(sendRequest);
             associateService.send(sendRequest.getSenderId(),sendRequest.getReceiverId());

        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        } catch (DuplicateKeyException e) {
            result.setCode(ErrorCodeEnum.STATE_ERROR.getCode());
            result.setMessage("已经发送过啦~~");
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage("操作失败");
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        }
        return result;
    }

    /**
     *
     * @param acceptRequestVo
     * @return
     */
    @RequestMapping(value = "/accept", method = {RequestMethod.POST })
    @ResponseBody
    public  Result accept(@RequestBody AcceptRequestVo acceptRequestVo) {
        Result result = new Result();
        try {
            _validateRequest(acceptRequestVo);
            associateService.accept(acceptRequestVo.getUserId(),acceptRequestVo.getTargetUserId());

        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        }  catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage("操作失败");
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        }
        return result;
    }

    /**
     * 拒绝
     * @param rejectRequestVo
     * @return
     */
    @RequestMapping(value = "/reject", method = {RequestMethod.POST })
    @ResponseBody
    public  Result reject(@RequestBody RejectRequestVo rejectRequestVo) {
        Result result = new Result();
        try {
            _validateRequest(rejectRequestVo);
            associateService.reject(rejectRequestVo.getUserId(),rejectRequestVo.getTargetUserId());

        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("reject failed, %s", e.getMessage()), e);
        }  catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage("操作失败");
            logger.info(String.format("reject failed, %s", e.getMessage()), e);
        }
        return result;
    }

    /**
     * 拒绝
     * @param admitRequestVo
     * @return
     */
    @RequestMapping(value = "/admit", method = {RequestMethod.POST })
    @ResponseBody
    public  Result admit(@RequestBody AdmitRequestVo admitRequestVo) {
        Result result = new Result();
        try {
            _validateRequest(admitRequestVo);
            associateService.admit(admitRequestVo.getUserId());
        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("admit failed, %s", e.getMessage()), e);
        }  catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage("操作失败");
            logger.info(String.format("admit failed, %s", e.getMessage()), e);
        }
        return result;
    }

    @RequestMapping(value = "/history/{id}", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Result history(@PathVariable long id) {
        Result result = new Result();
        try {
            result.setData(associateService.candidates(id, null, 0, 100));

        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage("操作失败");
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        }

        return result;
    }

    @RequestMapping(value = "/info/{id}", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Result all(@PathVariable long id) {
        Result result = new Result();
        try {

            result.setData(associateService.getUserInfo(id, 0, 100));

        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage("操作失败");
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        }

        return result;
    }


    protected void _validateRequest(SendRequestVo sendRequest) {
        if (sendRequest == null) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "request data not found");
        }
        if (sendRequest.getSenderId() == null || sendRequest.getSenderId() < 0) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "sender is invalid");
        }
        if (sendRequest.getReceiverId() == null || sendRequest.getReceiverId() < 0) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "receiver is invalid");
        }
    }

    protected void _validateRequest(AcceptRequestVo requestVo) {
        if (requestVo == null) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "request data not found");
        }
        if (requestVo.getUserId() == null || requestVo.getUserId() < 0) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "user id is not found or invalid");
        }
        if (requestVo.getTargetUserId() == null || requestVo.getTargetUserId() < 0) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "target user id is not found or invalid");
        }
    }

    protected void _validateRequest(RejectRequestVo requestVo) {
        if (requestVo == null) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "request data not found");
        }
        if (requestVo.getUserId() == null || requestVo.getUserId() < 0) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "user id is not found or invalid");
        }
        if (requestVo.getTargetUserId() == null || requestVo.getTargetUserId() < 0) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "target user id is not found or invalid");
        }
    }

    protected void _validateRequest(AdmitRequestVo requestVo) {
        if (requestVo == null) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "request data not found");
        }
        if (requestVo.getUserId() == null || requestVo.getUserId() < 0) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "user id is not found or invalid");
        }
    }



}
