package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 管理员角色表
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@TableName("manager_role")
public class ManagerRole extends Model<ManagerRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
            
     */
	@TableId("role_id")
	private String roleId;
    /**
     * 角色名称
     */
	@TableField("role_name")
	private String roleName;


	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	protected Serializable pkVal() {
		return this.roleId;
	}

}
