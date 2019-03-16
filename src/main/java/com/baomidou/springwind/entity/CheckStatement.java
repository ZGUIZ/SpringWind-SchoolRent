package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 账单
 * </p>
 *
 * @author Yanghu
 * @since 2019-03-12
 */
@TableName("check_statement")
public class CheckStatement extends Model<CheckStatement> {

    private static final long serialVersionUID = 1L;

    /**
     * 账单编号
     */
	@TableId("state_id")
	private String stateId;
    /**
     * 用户
     */
	@TableField("user_id")
	private String userId;
    /**
     * 性质:0.转入  1.转出
     */
	private Integer type;
    /**
     * 金额
     */
	private Float amount;
    /**
     * 描述
     */
	private String memo;
    /**
     * 日期
     */
	@TableField("create_date")
	private Date createDate;


	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	protected Serializable pkVal() {
		return this.stateId;
	}

}
