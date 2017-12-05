package com.oneday.controller;

import com.alibaba.fastjson.JSONObject;
import com.oneday.common.domain.Result;
import com.oneday.constant.ErrorCodeEnum;
import com.oneday.domain.vo.Relation;
import com.oneday.domain.vo.UserInfo;
import com.oneday.domain.vo.request.*;
import com.oneday.exceptions.OndayException;
import com.oneday.service.AssociateService;
import com.oneday.utils.LogHelper;
import org.apache.commons.lang.StringUtils;
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
    @Resource
    AssociateService associateService;

    /**
     *
     * @param sendRequest
     * @return
     */
    @RequestMapping(value = "/send", method = {RequestMethod.POST })
    @ResponseBody
    public  Object send(@RequestBody SendRequest sendRequest) {
        LogHelper.USER_LOG.info(String.format("willow/send, request %s", JSONObject.toJSONString(sendRequest)));

        _validateRequest(sendRequest);
        associateService.send(sendRequest.getAccessToken(),sendRequest.getReceiverId());
        Object result = Result.success("ok");
        LogHelper.USER_LOG.info(String.format("willow/send,  result : %s", JSONObject.toJSONString(result)));
        return result;
    }

    /**
     *
     * @param acceptRequest
     * @return
     */
    @RequestMapping(value = "/accept", method = {RequestMethod.POST })
    @ResponseBody
    public  Object accept(@RequestBody AcceptRequest acceptRequest) {
        LogHelper.USER_LOG.info(String.format("willow/accept, request %s ", JSONObject.toJSONString(acceptRequest)));
        _validateRequest(acceptRequest);
        associateService.accept(acceptRequest.getAccessToken(),acceptRequest.getTargetUserId());
        Object result = Result.success("ok");
        LogHelper.USER_LOG.info(String.format("willow/accept,  result : %s",  JSONObject.toJSONString(result)));
        return result;
    }

    /**
     * 拒绝
     * @param rejectRequest
     * @return
     */
    @RequestMapping(value = "/reject", method = {RequestMethod.POST })
    @ResponseBody
    public  Object reject(@RequestBody RejectRequest rejectRequest) {
        LogHelper.USER_LOG.info(String.format("willow/reject, request %s", JSONObject.toJSONString(rejectRequest)));

        _validateRequest(rejectRequest);
        associateService.reject(rejectRequest.getAccessToken(),rejectRequest.getTargetUserId());
        Object result = Result.success("ok");
        LogHelper.USER_LOG.info(String.format("willow/reject, result : %s", JSONObject.toJSONString(result)));
        return result;
    }

    /**
     * 拒绝
     * @param admitRequest
     * @return
     */
    @RequestMapping(value = "/admit", method = {RequestMethod.POST })
    @ResponseBody
    public  Object admit(@RequestBody AdmitRequest admitRequest) {
        LogHelper.USER_LOG.info(String.format("willow/admit, request %s", JSONObject.toJSONString(admitRequest)));
        _validateRequest(admitRequest);
        associateService.admit(admitRequest.getAccessToken());
        Object result = Result.success("ok");
        LogHelper.USER_LOG.info(String.format("willow/admit, result : %s", JSONObject.toJSONString(result)));

        return result;
    }

    @RequestMapping(value = "/history/{id}", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object history(@PathVariable long id) {
        LogHelper.USER_LOG.info(String.format("willow/history, id %s", id));
        Object result = Result.success(associateService.candidates(id, null, 0, 100));

        LogHelper.USER_LOG.info(String.format("willow/history, id %s, result : %s", id, JSONObject.toJSONString(result)));
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
    public Object candidates(@RequestBody CandidatesRequest request, @RequestParam(value = "currentPage", required = false)Integer currentPage,
                            @RequestParam(value = "count", required = false)Integer count) {
        LogHelper.USER_LOG.info(String.format("willow/candidates, request %s", JSONObject.toJSONString(request)));

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
        Object result = Result.success(userInfo);
        LogHelper.USER_LOG.info(String.format("willow/candidates,  result : %s",  JSONObject.toJSONString(result)));
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
    public Object relation(@RequestBody RelationRequest request) {
        LogHelper.USER_LOG.info(String.format("willow/relation, id %s", request.getAccessToken()));

        Relation relation = associateService.relation(request.getAccessToken(), request.getTargetUserId());
        Object result = Result.success(relation);
        LogHelper.USER_LOG.info(String.format("willow/relation, id %s, result : %s", request.getAccessToken(), JSONObject.toJSONString(result)));
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
