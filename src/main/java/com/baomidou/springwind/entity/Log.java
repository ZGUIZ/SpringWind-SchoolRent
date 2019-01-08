package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;


/**
 * <p>
 * 日志表
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public class Log extends Model<Log> {

    private static final long serialVersionUID = 1L;

    /**
     * 日志编号
     */
	@TableId("log_id")
	private Integer logId;
    /**
     * 日志信息
     */
	@TableField("log_info")
	private String logInfo;
    /**
     * 创建时间
     */
	@TableField("create_date")
	private Date createDate;


	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	protected Serializable pkVal() {
		return this.logId;
	}

}
