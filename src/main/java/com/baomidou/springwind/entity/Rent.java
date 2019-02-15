package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;


/**
 * <p>
 * 租赁信息关联表
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public class Rent extends Model<Rent> {

    private static final long serialVersionUID = 1L;

    /**
     * 租赁ID
     */
	@TableId("rent_id")
	private String rentId;
    /**
     * 租赁用户ID
     */
	@TableField("user_id")
	private String userId;
    /**
     * 闲置信息ID
     */
	@TableField("idel_id")
	private String idelId;
    /**
     * 状态：0.待确认 1.确认 2.拒绝
     */
	private Integer status;
    /**
     * 申请租赁开始日期
     */
	@TableField("start_date")
	private Date startDate;
    /**
     * 申请结束日期
     */
	@TableField("end_date")
	private Date endDate;

	@TableField(exist = false)
	private Student student;

	@TableField("last_rental")
	private Float lastRental;

	@TableField(exist = false)
	private String payPassword;

	public String getRentId() {
		return rentId;
	}

	public void setRentId(String rentId) {
		this.rentId = rentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIdelId() {
		return idelId;
	}

	public void setIdelId(String idelId) {
		this.idelId = idelId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	protected Serializable pkVal() {
		return this.rentId;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Float getLastRental() {
		return lastRental;
	}

	public void setLastRental(Float lastRental) {
		this.lastRental = lastRental;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
}
