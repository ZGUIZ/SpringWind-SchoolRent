package com.baomidou.springwind.entity;

public class PassWord {
    private String oldPassword;
    private String newPassword;
    private String confirmPaswword;

    /**
     * 验证码
     */
    private String code;
    private String mail;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPaswword() {
        return confirmPaswword;
    }

    public void setConfirmPaswword(String confirmPaswword) {
        this.confirmPaswword = confirmPaswword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
