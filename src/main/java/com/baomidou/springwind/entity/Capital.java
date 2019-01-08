package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;


/**
 * <p>
 * 用户资金信息
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public class Capital extends Model<Capital> {

    private static final long serialVersionUID = 1L;

	@TableId("capital_id")
	private String capitalId;
	@TableField("user_id")
	private String userId;
	private Float capital;
	@TableField("update_time")
	private Date updateTime;


	public String getCapitalId() {
		return capitalId;
	}

	public void setCapitalId(String capitalId) {
		this.capitalId = capitalId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Float getCapital() {
		return capital;
	}

	public void setCapital(Float capital) {
		this.capital = capital;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.capitalId;
	}

}
