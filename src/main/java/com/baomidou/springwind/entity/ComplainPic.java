package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 申诉图片
 * </p>
 *
 * @author Yanghu
 * @since 2018-12-16
 */
@TableName("complain_pic")
public class ComplainPic extends Model<ComplainPic> {

    private static final long serialVersionUID = 1L;

    /**
     * 图片编号
     */
	@TableId("pic_id")
	private String picId;
    /**
     * 投诉ID
     */
	@TableField("complain_id")
	private String complainId;
    /**
     * 申诉类型
     */
	private Integer type;
    /**
     * 图片地址
     */
	private String url;


	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public String getComplainId() {
		return complainId;
	}

	public void setComplainId(String complainId) {
		this.complainId = complainId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	protected Serializable pkVal() {
		return this.picId;
	}

}
