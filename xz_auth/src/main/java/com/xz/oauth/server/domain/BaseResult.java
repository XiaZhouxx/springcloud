package com.xz.oauth.server.domain;

/**
 * @author xz
 * @ClassName BaseResult
 * @Description
 * @date 2020/6/5 9:51
 **/
public class BaseResult<T> {
    private String msg;

    private int code;

    private T data;

    public static BaseResult ok(Object data){
        BaseResult result = new BaseResult();
        result.code = 200;
        result.msg = "success";
        result.data = data;
        return result;
    }
    public static BaseResult error(String msg, int code){
        BaseResult result = new BaseResult();
        result.code = code;
        result.msg = msg;
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
