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

    STATE_ERROR("0101", "状态转换异常"),
    STATE_ALREADY_SUCCESS("0102", "已经成功啦");

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
