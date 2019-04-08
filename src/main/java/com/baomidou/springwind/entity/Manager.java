package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;


/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public class Manager extends Model<Manager> {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员ID
     */
	@TableId("user_id")
	private String userId;
    /**
     * 登录账号
     */
	private String account;
    /**
     * 登录密码
     */
	private String password;
    /**
     * 管理员角色ID
     */
	private String role;

	@TableField("user_name")
	private String userName;

	private String mail;

	/**
	 * 邮箱验证码
	 */
	@TableField(exist = false)
	private String code;

	@TableField(exist = false)
	private ManagerRole managerRole;

	@TableField(exist = false)
	private String beanStatus;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	protected Serializable pkVal() {
		return this.userId;
	}

	public ManagerRole getManagerRole() {
		return managerRole;
	}

	public void setManagerRole(ManagerRole managerRole) {
		this.managerRole = managerRole;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBeanStatus() {
		return beanStatus;
	}

	public void setBeanStatus(String beanStatus) {
		this.beanStatus = beanStatus;
	}
}
