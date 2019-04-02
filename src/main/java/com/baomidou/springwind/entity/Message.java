package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;


/**
 * <p>
 * 消息
 * </p>
 *
 * @author Yanghu
 * @since 2019-04-01
 */
public class Message extends Model<Message> {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
	@TableId("msg_id")
	private String msgId;
    /**
     * 消息内容
     */
	private String content;
    /**
     * 标题
     */
	private String title;
    /**
     * 状态
     */
	private Integer status;
    /**
     * 日期
     */
	@TableField("create_date")
	private Date createDate;
	@TableField("user_id")
	private String userId;


	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	protected Serializable pkVal() {
		return this.msgId;
	}

}
