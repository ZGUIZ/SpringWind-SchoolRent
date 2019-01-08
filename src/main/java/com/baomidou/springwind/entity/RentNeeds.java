package com.baomidou.springwind.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 用户发布的租赁需求
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@TableName("rent_needs")
public class RentNeeds extends Model<RentNeeds> {

    private static final long serialVersionUID = 1L;

    /**
     * 需求信息编号
     */
	@TableId("info_id")
	private String infoId;
    /**
     * 发布的信息
     */
	@TableField("idel_info")
	private String idelInfo;
    /**
     * 发布状态
     */
	private Integer status;
    /**
     * 发布日期
     */
	@TableField("create_date")
	private Date createDate;
    /**
     * 截至日期
     */
	@TableField("end_date")
	private Date endDate;

	/**
	 * 标题
	 */
	private String title;
	/**
	 * 用户ID
	 */
	@TableField("user_id")
	private String userId;

	/**
	 * 学校ID
	 */
	@TableField("school_id")
	private String schoolId;

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getIdelInfo() {
		return idelInfo;
	}

	public void setIdelInfo(String idelInfo) {
		this.idelInfo = idelInfo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	protected Serializable pkVal() {
		return this.infoId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public static RentNeeds getObjectFromJsonObject(JSONObject object){
		RentNeeds needs=new RentNeeds();
		needs.setInfoId(object.getString("infoId"));
		needs.setIdelInfo(object.getString("idelInfo"));
		needs.setCreateDate(object.getDate("createDate"));
		needs.setEndDate(object.getDate("endDate"));
		needs.setSchoolId(object.getString("schoolId"));
		needs.setStatus(object.getInteger("status"));
		needs.setUserId(object.getString("userId"));
		needs.setTitle(object.getString("title"));
		return needs;
	}
}
