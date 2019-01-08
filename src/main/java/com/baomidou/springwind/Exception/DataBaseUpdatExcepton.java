package com.baomidou.springwind.Exception;

public class DataBaseUpdatExcepton extends Exception{
    private String msg;
    public DataBaseUpdatExcepton(){
        super();
        this.msg="数据库更新异常";
    }

    public DataBaseUpdatExcepton(String message, String msg) {
        super(message);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
