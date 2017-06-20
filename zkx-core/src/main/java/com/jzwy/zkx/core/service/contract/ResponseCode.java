package com.jzwy.zkx.core.service.contract;

public final class ResponseCode {

    private ResponseCode() {
    }

    // 成功
    public static final String CODE_SUCCESS = "0";
    // 失败
    public static final String DEFAULT_CODE_FAILURE = "1";

    public static final String CODE_NO_SESSION = "-1";

    public static final String CODE_NO_AUTH = "-2";

    public static final String MSG_SUCCESS = "Success";

    public static final String DEFAULT_MSG_FAILURE = "Failure";
}
