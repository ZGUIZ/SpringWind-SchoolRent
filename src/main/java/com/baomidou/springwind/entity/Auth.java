package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;


/**
 * <p>
 * 绑定第三方账号的关联表
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public class Auth extends Model<Auth> {

    private static final long serialVersionUID = 1L;

    /**
     * 关联ID
     */
	@TableId("auth_id")
	private String authId;
    /**
     * 用户ID
     */
	@TableField("user_id")
	private String userId;
    /**
     * 分类信息
     */
	@TableField("cateory_id")
	private String cateoryId;
    /**
     * 授权的token
     */
	private String token;
    /**
     * 授权状态：未授权：0，已授权：1
     */
	private Integer status;


	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCateoryId() {
		return cateoryId;
	}

	public void setCateoryId(String cateoryId) {
		this.cateoryId = cateoryId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.authId;
	}

}
