package com.baomidou.springwind.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.activerecord.Model;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * 闲置信息
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@TableName("idle_info")
public class IdleInfo extends Model<IdleInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 闲置信息编号
     */
	@TableId("info_id")
	private String infoId;
    /**
     * 类别
     */
	@TableField("classify_id")
	private String classifyId;
    /**
     * 闲置信息内容
     */
	@TableField("idel_info")
	private String idelInfo;
    /**
     * 损毁描述
     */
	@TableField("destory_info")
	private String destoryInfo;
    /**
     * 最低押金
     */
	private Float deposit;
    /**
     * 租金
     */
	private Float retal;
    /**
     * 日租：1;固定租金：2
     */
	@TableField("retal_type")
	private Integer retalType;
    /**
     * 状态：0：未租赁，1：正在租赁，2：已完成
     */
	private Integer status;
    /**
     * 发布日期
     */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("create_date")
	private Date createDate;
    /**
     * 租赁最迟归还日期
     */
	@TableField("end_date")
	private Date endDate;

	@TableField("user_id")
	private String userId;

	/**
	 * 学校ID
	 */
	@TableField("school_id")
	private String schoolId;
	/**
	 * 标题
	 */
	private String title;

	@TableField("address")
	private String address;

	@TableField(exist = false)
	private List<IdelPic> picList;

	@TableField(exist = false)
	private Student student;

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}

	public String getIdelInfo() {
		return idelInfo;
	}

	public void setIdelInfo(String idelInfo) {
		this.idelInfo = idelInfo;
	}

	public String getDestoryInfo() {
		return destoryInfo;
	}

	public void setDestoryInfo(String destoryInfo) {
		this.destoryInfo = destoryInfo;
	}

	public Float getDeposit() {
		return deposit;
	}

	public void setDeposit(Float deposit) {
		this.deposit = deposit;
	}

	public Float getRetal() {
		return retal;
	}

	public void setRetal(Float retal) {
		this.retal = retal;
	}

	public Integer getRetalType() {
		return retalType;
	}

	public void setRetalType(Integer retalType) {
		this.retalType = retalType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	protected Serializable pkVal() {
		return this.infoId;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<IdelPic> getPicList() {
		return picList;
	}

	public void setPicList(List<IdelPic> picList) {
		this.picList = picList;
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
	public static IdleInfo getObjectFromJsonObject(JSONObject object){
		IdleInfo idleInfo=new IdleInfo();
		idleInfo.setInfoId(object.getString("infoId"));
		idleInfo.setClassifyId(object.getString("classifyId"));
		idleInfo.setDestoryInfo(object.getString("destoryInfo"));
		idleInfo.setDeposit(object.getFloat("deposit"));
		idleInfo.setRetal(object.getFloat("retal"));
		idleInfo.setIdelInfo(object.getString("idelInfo"));
		idleInfo.setRetalType(object.getInteger("retalType"));
		idleInfo.setCreateDate(object.getDate("createDate"));
		idleInfo.setEndDate(object.getDate("endDate"));
		idleInfo.setSchoolId(object.getString("schoolId"));
		idleInfo.setStatus(object.getInteger("status"));
		idleInfo.setUserId(object.getString("userId"));
		idleInfo.setTitle(object.getString("title"));
		return idleInfo;
	}
}
