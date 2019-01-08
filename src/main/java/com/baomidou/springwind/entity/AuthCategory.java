package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 第三方账号信息
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@TableName("auth_category")
public class AuthCategory extends Model<AuthCategory> {

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
	@TableId("category_id")
	private String categoryId;
    /**
     * 分类名称
     */
	@TableField("category_name")
	private String categoryName;


	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	protected Serializable pkVal() {
		return this.categoryId;
	}

}
