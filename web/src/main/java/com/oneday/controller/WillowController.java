package com.oneday.controller;

import com.alibaba.fastjson.JSONObject;
import com.oneday.common.domain.Result;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.vo.Relation;
import com.oneday.domain.vo.UserInfo;
import com.oneday.domain.vo.request.*;
import com.oneday.exceptions.OndayException;
import com.oneday.service.AssociateService;
import org.apache.commons.lang.StringUtils;
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
    public  Result send(@RequestBody SendRequest sendRequest) {
        Result result = new Result();
        try {
            _validateRequest(sendRequest);
             associateService.send(sendRequest.getAccessToken(),sendRequest.getReceiverId());

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
     * @param acceptRequest
     * @return
     */
    @RequestMapping(value = "/accept", method = {RequestMethod.POST })
    @ResponseBody
    public  Result accept(@RequestBody AcceptRequest acceptRequest) {
        Result result = new Result();
        try {
            _validateRequest(acceptRequest);
            associateService.accept(acceptRequest.getAccessToken(),acceptRequest.getTargetUserId());

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
     * @param rejectRequest
     * @return
     */
    @RequestMapping(value = "/reject", method = {RequestMethod.POST })
    @ResponseBody
    public  Result reject(@RequestBody RejectRequest rejectRequest) {
        Result result = new Result();
        try {
            _validateRequest(rejectRequest);
            associateService.reject(rejectRequest.getAccessToken(),rejectRequest.getTargetUserId());

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
     * @param admitRequest
     * @return
     */
    @RequestMapping(value = "/admit", method = {RequestMethod.POST })
    @ResponseBody
    public  Result admit(@RequestBody AdmitRequest admitRequest) {
        Result result = new Result();
        try {
            _validateRequest(admitRequest);
            associateService.admit(admitRequest.getAccessToken());
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

    /**
     * 获取用户候选人数据
     * @param request
     * @param currentPage
     * @param count
     * @return
     */
    @RequestMapping(value = "/candidates", method = {RequestMethod.POST })
    @ResponseBody
    public Result candidates(@RequestBody CandidatesRequest request, @RequestParam(value = "currentPage", required = false)Integer currentPage,
                            @RequestParam(value = "count", required = false)Integer count) {
        Result result = new Result();

        try {
            _checkCandidatesRequest(request);
            if (currentPage == null || currentPage < 0) {
                currentPage = 0;
            }
            if (count == null || count <0 ) {
                count = 10;
            } else if (count > 1000) {
                count = 1000;
            }
            UserInfo userInfo = associateService.getUserInfo(request.getAccessToken(), currentPage, count);
            result.setData(userInfo);
            System.out.println(String.format("/info/{id}, id %s, result : %s", request.getAccessToken(), JSONObject.toJSONString(userInfo)));
        } catch (OndayException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        } catch (Throwable e) {
            result.setCode(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            result.setMessage("操作失败");
            logger.info(String.format("regist failed, %s", e.getMessage()), e);
        }
        logger.info(String.format("willow/candidates, id %s, result : %s", request.getAccessToken(), JSONObject.toJSONString(result)));
        return result;
    }

    protected boolean _checkCandidatesRequest(CandidatesRequest request) {
        if (request == null) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "请求异常");
        }
        if (StringUtils.isEmpty(request.getAccessToken())) {
            throw new OndayException(ErrorCodeEnum.NULL_PARAM.getCode(), "用户未登录");
        }
        return true;
    }

    @RequestMapping(value = "/relation", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Result relation(@RequestBody RelationRequest request) {
        Result result = new Result();
        try {

            Relation relation = associateService.relation(request.getAccessToken(), request.getTargetUserId());
            result.setData(relation);
            System.out.println(String.format("/relation, param %s, result : %s", JSONObject.toJSONString(request),
                    JSONObject.toJSONString(result)));
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


    protected void _validateRequest(SendRequest sendRequest) {
        if (sendRequest == null) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "request data not found");
        }
        if (StringUtils.isEmpty(sendRequest.getAccessToken())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请登录");
        }
        if (sendRequest.getReceiverId() == null || sendRequest.getReceiverId() < 0) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "receiver is invalid");
        }
    }

    protected void _validateRequest(AcceptRequest requestVo) {
        if (requestVo == null) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "request data not found");
        }
        if (StringUtils.isEmpty(requestVo.getAccessToken())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请登录");
        }
        if (requestVo.getTargetUserId() == null || requestVo.getTargetUserId() < 0) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "target user id is not found or invalid");
        }
    }

    protected void _validateRequest(RejectRequest requestVo) {
        if (requestVo == null) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "request data not found");
        }
        if (StringUtils.isEmpty(requestVo.getAccessToken())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请登录");
        }
        if (requestVo.getTargetUserId() == null || requestVo.getTargetUserId() < 0) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "target user id is not found or invalid");
        }
    }

    protected void _validateRequest(AdmitRequest requestVo) {
        if (requestVo == null) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "request data not found");
        }
        if (StringUtils.isEmpty(requestVo.getAccessToken())) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "请登录");
        }
    }
    protected void _validateRequest(RelationRequest requestVo) {
        if (requestVo == null) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "request data not found");
        }
        if (requestVo.getTargetUserId() == null || requestVo.getTargetUserId() < 0) {
            throw new OndayException(ErrorCodeEnum.INVALID_PARAM.getCode(), "target user id is not found or invalid");
        }
    }



}
