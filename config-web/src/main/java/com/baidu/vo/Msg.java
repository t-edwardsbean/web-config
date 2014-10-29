package com.baidu.vo;

/**
 * Created by edwardsbean on 14-10-29.
 */
public class Msg {
    private String code = "0";
    private String msg = "success";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getReturnData() {
        return returnData;
    }

    public void setReturnData(Object returnData) {
        this.returnData = returnData;
    }

    protected Object returnData;
}
