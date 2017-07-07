package com.oneday.constant;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 16:38
 */
public enum ErrorCodeEnum {
    FAILURE("-1", "操作失败"),
    SYSTEM_EXCEPTION("-2", "系统异常"),
    SUCCESS("0000", "成功"),
    NULL_PARAM("0001", "参数为空"),
    INVALID_PARAM("0002", "参数不合法"),

    USER_NOT_FOUND("0011", "用户不存在"),

    USER_SEX_INVALID("0012", "用户性别不合法"),
    USER_HUNTER_INVALID("0013", "追求者不合法"),
    USER_REGIST_DUPLICATE_ERROR("0014", "用户已经注册过了"),
    USER_REGIST_CODE_ERROR("0015", "注册验证码错误"),
    USER_LOGIN_PASSWORD_ERROR("0016", "登录账号或者密码错误"),
    USER_LOGIN_CODE_ERROR("0017", "登录账号或者验证码错误"),
    USER_NOT_LOGIN_ERROR("0018", "用户未登录"),
    USER_REGIST_IDCARD_EXISTED("0019", "注册身份证已经存在"),
    USER_REGIST_FAIL("0020", "注册失败"),
    USER_LOGIN_FAIL("0021", "用户名不存在或者密码错误"),
    USER_ACCESS_TOKEN_EXPIRED("0022", "用户accessToken失效"),
    USER_ENCRYPT_FAIL("0023", "加密失败"),
    USER_DECRYPT_FAIL("0024", "解密失败"),

    STATE_ERROR("0101", "状态转换异常"),
    STATE_SEND_DUPLICATE_ERROR("0102", "重复发送请求"),
    STATE_RECEIVER_CANT_REJECT_ERROR("0103", "接受者无法拒绝，因为当前只有一名追求者"),
    STATE_REJECT_ILLEGAL_ERROR("0104", "已经接受，不能直接拒绝"),
    STATE_ALREADY_SUCCESS("0105", "已经是成功状态"),
    STATE_HUNTER_SEND_ON_HOLD_ERROR("0106", "追求者已经被人接受，除非被接受者拒绝，否则无法再发送请求。"),
    STATE_ACCEPT_DUPLICATED_ERROR("0107", "重复接受。"),

    FILE_UPLOAD_FAIL("0201", "文件上传失败"),


    //主题相关
    TOPIC_NOT_FOUND("0301","主题不存在"),
    ;

    private String code;
    private String value;

    ErrorCodeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    /**
     * 返回枚举的code
     */
    @Override
    public String toString() {
        if (code != null) {
            return code;
        }
        return null;
    }
}
