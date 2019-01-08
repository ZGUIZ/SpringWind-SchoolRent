package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;


/**
 * <p>
 * 闲置信息相关图片
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@TableName("idel_pic")
public class IdelPic extends Model<IdelPic> {

    private static final long serialVersionUID = 1L;

    /**
     * 图片ID
     */
	@TableId("pic_id")
	private String picId;
    /**
     * 图片地址
     */
	@TableField("pic_url")
	private String picUrl;
    /**
     * 闲置物品ID
     */
	@TableField("idel_id")
	private String idelId;


	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getIdelId() {
		return idelId;
	}

	public void setIdelId(String idelId) {
		this.idelId = idelId;
	}

	@Override
	protected Serializable pkVal() {
		return this.picId;
	}

}
