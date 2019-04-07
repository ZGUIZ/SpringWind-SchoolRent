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

	@TableField(exist = false)
	private ManagerRole managerRole;


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
}
