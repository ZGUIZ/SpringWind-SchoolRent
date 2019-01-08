package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;


/**
 * <p>
 * 学校
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public class School extends Model<School> {

    private static final long serialVersionUID = 1L;

	@TableId("school_id")
	private String schoolId;
	@TableField("school_name")
	private String schoolName;
	@TableField("city")
	private String city;
	@TableField("province")
	private String province;


	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Override
	protected Serializable pkVal() {
		return this.schoolId;
	}

}
