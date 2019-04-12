package com.baomidou.springwind.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * 学生学号
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public class Student extends Model<Student> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
	@TableId("user_id")
	private String userId;
    /**
     * 学生学号
     */
	@TableField("student_id")
	private String studentId;
    /**
     * 学校编号
     */
	@TableField("school_id")
	private String schoolId;
    /**
     * 显示的用户名
     */
	@TableField("user_name")
	private String userName;
    /**
     * 用户真实姓名
     */
	@TableField("real_name")
	private String realName;
    /**
     * 登录密码
     */
	private String password;

	@TableField(exist = false)
	private String confirmPassword;
    /**
     * 支付密码
     */
	@TableField("pay_password")
	private String payPassword;

	@TableField(exist = false)
	private String confirmPayPassword;
    /**
     * 认证等级
     */
	@TableField("auth_level")
	private Integer authLevel;
    /**
     * 用户信誉
     */
	@TableField("credit")
	private Integer credit;
    /**
     * 用户头像URL
     */
	@TableField("user_icon")
	private String userIcon;
    /**
     * 绑定手机号码
     */
	@TableField("telephone")
	private String telephone;
    /**
     * 绑定邮箱
     */
	@TableField("email")
	private String email;
    /**
     * 用户性别
     */
	@TableField("sex")
	private String sex;
    /**
     * 账号状态：0：未认证，1：正常，2：禁止登陆
     */
	@TableField("status")
	private Integer status;
    /**
     * 注册日期
     */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@TableField("create_date")
	private Date createDate;

	@TableField(exist = false)
	private School school;

	@TableField(exist = false)
	private String beanStatus;

	@TableField(exist = false)
	private List<AuthPicture> authPictureList;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public Integer getAuthLevel() {
		return authLevel;
	}

	public void setAuthLevel(Integer authLevel) {
		this.authLevel = authLevel;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	@Override
	protected Serializable pkVal() {
		return this.userId;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public String getBeanStatus() {
		if(beanStatus == null || "".equals(beanStatus)){
			return "edit";
		}
		return beanStatus;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getConfirmPayPassword() {
		return confirmPayPassword;
	}

	public void setConfirmPayPassword(String confirmPayPassword) {
		this.confirmPayPassword = confirmPayPassword;
	}

	public void setBeanStatus(String beanStatus) {
		this.beanStatus = beanStatus;
	}

	public List<AuthPicture> getAuthPictureList() {
		return authPictureList;
	}

	public void setAuthPictureList(List<AuthPicture> authPictureList) {
		this.authPictureList = authPictureList;
	}

	/**
	 * 从JSON数据获取对象
	 * @param object
	 * @return
	 */
	public static Student getObjectFromJsonObject(JSONObject object){
		Student student=new Student();
		student.setUserId(object.getString("userId"));
		student.setPassword(object.getString("password"));
		student.setStudentId("".equals(object.getString("studentId"))? null: object.getString("studentId"));
		student.setSchoolId("".equals(object.getString("schoolId"))? null: object.getString("schoolId"));
		student.setUserName("".equals(object.getString("userName"))? null: object.getString("userName"));
		student.setRealName("".equals(object.getString("realName"))? null: object.getString("realName"));
		student.setAuthLevel(object.getInteger("authLevel"));
		student.setCredit(object.getInteger("credit"));
		student.setUserIcon(object.getString("userIcon"));
		student.setTelephone(object.getString("telephone"));
		student.setEmail(object.getString("email"));
		student.setSex(object.getString("sex"));
		student.setStatus(object.getInteger("status"));
		student.setBeanStatus(object.getString("beanStatus"));
		return student;
	}

	public static Student getObjectFromJsonObject(String json){
		return getObjectFromJsonObject(JSONObject.parseObject(json));
	}
}
