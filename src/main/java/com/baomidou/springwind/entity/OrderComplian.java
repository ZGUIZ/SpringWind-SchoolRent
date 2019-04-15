package com.baomidou.springwind.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;


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
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@TableField("complain_date")
	private Date complainDate;
    /**
     * 管理员回复
     */
	private String result;
    /**
     * 投诉闲置ID
     */
	@TableField("info_id")
	private String infoId;
	@TableField("response_person")
	private String responsePerson;

	@TableField(exist = false)
	private List<String> urls;

	/**
	 * 赔偿金额
	 */
	@TableField("money")
	private Float money;

	@TableField(exist = false)
	private Student student;

	@TableField(exist = false)
	private IdleInfo idleInfo;


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

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
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

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public IdleInfo getIdleInfo() {
		return idleInfo;
	}

	public void setIdleInfo(IdleInfo idleInfo) {
		this.idleInfo = idleInfo;
	}
}
