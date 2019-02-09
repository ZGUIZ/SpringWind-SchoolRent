package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;


/**
 * <p>
 * 商品类别
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public class Classify extends Model<Classify> {

    private static final long serialVersionUID = 1L;

	@TableId("classify_id")
	private String classifyId;
	@TableField("classify_name")
	private String classifyName;
	@TableField("weight")
	private Integer weight;
	@TableField("image_url")
	private String imageUrl;


	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	protected Serializable pkVal() {
		return this.classifyId;
	}

}
