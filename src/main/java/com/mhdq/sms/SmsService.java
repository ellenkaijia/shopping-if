package com.mhdq.sms;

import com.mhdq.rpc.RpcRespDTO;

/**  
* 短信服务类   
*  
* @author zkj  
* @date 2017年4月13日  新建  
*/
public interface SmsService {

	RpcRespDTO<String> sendSMS(String phone);
}
