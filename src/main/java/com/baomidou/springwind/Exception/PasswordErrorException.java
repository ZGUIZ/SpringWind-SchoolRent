package com.baomidou.springwind.Exception;

public class PasswordErrorException extends Exception{
    public PasswordErrorException() {
    }

    public PasswordErrorException(String message) {
        super(message);
    }
}
