package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 用于审核身份的图片
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@TableName("auth_picture")
public class AuthPicture extends Model<AuthPicture> {

    private static final long serialVersionUID = 1L;

    /**
     * 图片ID
     */
	@TableId("pic_id")
	private String picId;
    /**
     * 用户ID
     */
	@TableField("user_id")
	private String userId;
    /**
     * 图片URL
     */
	@TableField("pic_url")
	private String picUrl;
    /**
     * 信息创建日期
     */
	@TableField("create_date")
	private Date createDate;

	/**
	 * 类型
	 */
	private Integer type;
	private Integer status;

	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.picId;
	}

}
