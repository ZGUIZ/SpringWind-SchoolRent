package com.baomidou.springwind.Exception;

public class IllegalAuthroiyException extends Exception{
    private String msg;
    public IllegalAuthroiyException() {
        super();
        msg="您没有对应的权限";
    }

    public IllegalAuthroiyException(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }
}
