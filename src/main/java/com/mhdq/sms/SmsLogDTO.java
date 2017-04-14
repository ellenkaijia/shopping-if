package com.mhdq.sms;

import java.io.Serializable;
import java.util.Date;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月13日  新建  
*/
public class SmsLogDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4193062832627413501L;

	private Long id;
	
	private String userPhone;
	
	private String msgInfo;
	
	private String msgCode;
	
	private Date createTime;
	
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getMsgInfo() {
		return msgInfo;
	}

	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
