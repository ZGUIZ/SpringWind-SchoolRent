package com.baomidou.springwind.entity;

public class Result {
    /**
     * 是否成功
     */
    private Boolean result;
    /**
     * 返回信息
     */
    private String msg;

    private Integer code;

    private String paramType;
    /**
     * 参数
     */
    private Object data;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object param) {
        this.data = param;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
