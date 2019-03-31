package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;


/**
 * <p>
 * 评价信息
 * </p>
 *
 * @author Yanghu
 * @since 2018-12-16
 */
public class Eval extends Model<Eval> {

    private static final long serialVersionUID = 1L;

    /**
     * 评价ID
     */
	@TableId("eval_id")
	private String evalId;
    /**
     * 评价用户
     */
	@TableField("user_id")
	private String userId;
    /**
     * 闲置商品ID
     */
	@TableField("idle_id")
	private String idleId;
    /**
     * 评价内容
     */
	private String content;
    /**
     * 评价时间
     */
	@TableField("eval_date")
	private Date evalDate;
    /**
     * 评价等级
     */
	private float level;
    /**
     * 状态
     */
	private Integer status;

	private IdleInfo idleInfo;

	private Student student;


	public String getEvalId() {
		return evalId;
	}

	public void setEvalId(String evalId) {
		this.evalId = evalId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIdleId() {
		return idleId;
	}

	public void setIdleId(String idleId) {
		this.idleId = idleId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getEvalDate() {
		return evalDate;
	}

	public void setEvalDate(Date evalDate) {
		this.evalDate = evalDate;
	}

	public float getLevel() {
		return level;
	}

	public void setLevel(float level) {
		this.level = level;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.evalId;
	}

	public IdleInfo getIdleInfo() {
		return idleInfo;
	}

	public void setIdleInfo(IdleInfo idleInfo) {
		this.idleInfo = idleInfo;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}
