package com.mhdq.dao.manager;

import com.mhdq.sms.SmsLogDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月13日  新建  
*/
public interface SmsLogDao {
	
	int insertSmsLog(SmsLogDTO smsLogDTO);
	
	SmsLogDTO getRecentMsgCode(String phone);
	

}
