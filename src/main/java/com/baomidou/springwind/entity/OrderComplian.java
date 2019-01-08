package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 订单投诉
 * </p>
 *
 * @author Yanghu
 * @since 2018-12-16
 */
@TableName("order_complian")
public class OrderComplian extends Model<OrderComplian> {

    private static final long serialVersionUID = 1L;

    /**
     * 投诉ID
     */
	@TableId("complain_id")
	private String complainId;
    /**
     * 投诉内容
     */
	private String context;
    /**
     * 投诉用户
     */
	@TableField("user_id")
	private String userId;
    /**
     * 状态
     */
	private Integer status;
    /**
     * 投诉日期
     */
	@TableField("complain_date")
	private Date complainDate;
    /**
     * 管理员回复
     */
	private String result;
    /**
     * 投诉闲置ID
     */
	@TableField("idle_id")
	private String idleId;
	@TableField("response_person")
	private String responsePerson;


	public String getComplainId() {
		return complainId;
	}

	public void setComplainId(String complainId) {
		this.complainId = complainId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getComplainDate() {
		return complainDate;
	}

	public void setComplainDate(Date complainDate) {
		this.complainDate = complainDate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getIdleId() {
		return idleId;
	}

	public void setIdleId(String idleId) {
		this.idleId = idleId;
	}

	public String getResponsePerson() {
		return responsePerson;
	}

	public void setResponsePerson(String responsePerson) {
		this.responsePerson = responsePerson;
	}

	@Override
	protected Serializable pkVal() {
		return this.complainId;
	}

}
