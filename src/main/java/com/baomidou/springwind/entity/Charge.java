package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;


/**
 * <p>
 * 充值请求
 * </p>
 *
 * @author Yanghu
 * @since 2018-12-16
 */
public class Charge extends Model<Charge> {

    private static final long serialVersionUID = 1L;

    /**
     * 充值编号
     */
	@TableId("charge_id")
	private String chargeId;
    /**
     * 用户ID
     */
	@TableField("user_id")
	private String userId;
    /**
     * 来源
     */
	private Integer source;
    /**
     * 单号
     */
	private String code;
    /**
     * 凭证截图
     */
	private String pic;
    /**
     * 状态
     */
	private Integer status;
    /**
     * 请求日期
     */
	@TableField("request_date")
	private Date requestDate;
    /**
     * 响应日期
     */
	@TableField("response_date")
	private Date responseDate;
    /**
     * 处理人
     */
	@TableField("response_person")
	private String responsePerson;


	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public String getResponsePerson() {
		return responsePerson;
	}

	public void setResponsePerson(String responsePerson) {
		this.responsePerson = responsePerson;
	}

	@Override
	protected Serializable pkVal() {
		return this.chargeId;
	}

}
