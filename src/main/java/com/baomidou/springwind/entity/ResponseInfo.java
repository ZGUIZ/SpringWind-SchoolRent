package com.baomidou.springwind.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * 租赁信息回复
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@TableName("response_info")
public class ResponseInfo extends Model<ResponseInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 回复ID
     */
	@TableId("response_id")
	private String responseId;
    /**
     * 发表回复的用户ID
     */
	@TableField("user_id")
	private String userId;
    /**
     * 发布回复的信息ID
     */
	@TableField("info_id")
	private String infoId;
    /**
     * 回复内容
     */
	@TableField("response_info")
	private String responseInfo;
    /**
     * 回复日期
     */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("response_date")
	private Date responseDate;

	/**
	 * 状态
	 */
	private Integer status;

	@TableField(exist = false)
	private Student student;

	@TableField(exist = false)
	private List<SecondResponseInfo> secondResponseInfos;

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getResponseInfo() {
		return responseInfo;
	}

	public void setResponseInfo(String responseInfo) {
		this.responseInfo = responseInfo;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.responseId;
	}

	public List<SecondResponseInfo> getSecondResponseInfos() {
		return secondResponseInfos;
	}

	public void setSecondResponseInfos(List<SecondResponseInfo> secondResponseInfos) {
		this.secondResponseInfos = secondResponseInfos;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * 从JSON数据获取对象
	 * @param object
	 * @return
	 */
	public static ResponseInfo getObjectFromJsonObject(JSONObject object){
		ResponseInfo info=new ResponseInfo();
		info.setResponseId(object.getString("responseId"));
		info.setUserId(object.getString("userId"));
		info.setResponseInfo(object.getString("responseInfo"));
		info.setResponseDate(object.getDate("responseDate"));
		info.setInfoId(object.getString("infoId"));
		info.setStatus(object.getInteger("status"));
		return info;
	}
}
