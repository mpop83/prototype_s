package com.test.prototype_s.server;

/**
 * Represents the Result of a Server Request
 */

public class Result {

   public enum ResultCode {
        RES_OK, RES_HTTP_ERR, RES_NOK
    }

    private ResultCode resCode;
    private String result;

    public Result(ResultCode resCode, String result) {
        this.resCode = resCode;
        this.result = result;
    }

    public ResultCode getResCode() {
        return resCode;
    }

    public String getResult() {
        return result;
    }
}
