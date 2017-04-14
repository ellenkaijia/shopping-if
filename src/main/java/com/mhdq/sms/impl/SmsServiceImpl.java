package com.mhdq.sms.impl;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mhdq.dao.manager.SmsLogDao;
import com.mhdq.rpc.RpcRespDTO;
import com.mhdq.sms.SmsLogDTO;
import com.mhdq.sms.SmsService;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月13日  新建  
*/
public class SmsServiceImpl implements SmsService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	
	private String account = "C43747024";
	
	private String password = "2570317f1ea8d70ae658b67e7909771c";
	
	@Autowired
	private SmsLogDao smsLogDao;
	
	@Override
	public RpcRespDTO<String> sendSMS(String phone) {
		
		RpcRespDTO<String> rpcResult = new RpcRespDTO<>();
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod(Url);

		client.getParams().setContentCharset("GBK");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");

		int mobile_code = (int)((Math.random()*9+1)*100000);

	    String content = new String("您的验证码是："+ mobile_code +"。请不要把验证码泄露给其他人。");

		NameValuePair[] data = {//
			    new NameValuePair("account", account), //->APIID
			    new NameValuePair("password", password),  //->APIKEY
			    //new NameValuePair("password", util.StringUtil.MD5Encode("����")),
			    new NameValuePair("mobile", phone), 
			    new NameValuePair("content", content),
		};
		method.setRequestBody(data);

		try {
			client.executeMethod(method);
			
			String SubmitResult =method.getResponseBodyAsString();

			logger.info("短信平台返回的http报文SubmitResult={}", SubmitResult);

			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();

			String code = root.elementText("code");
			String msg = root.elementText("msg");
			String smsid = root.elementText("smsid");

			logger.info("短信平台返回的状态码 code={}",code);
			logger.info("短信平台返回的状态信息 msg={}",msg);
			logger.info("短信平台返回的状态短信Id smsid={}",smsid);

			 if("2".equals(code)){
				 logger.info("短信平台发送短信成功");
				 
				 SmsLogDTO smsLogDTO = new SmsLogDTO();
				 smsLogDTO.setMsgCode(mobile_code + "");
				 smsLogDTO.setMsgInfo(content);
				 smsLogDTO.setStatus(1);
				 smsLogDTO.setUserPhone(phone);
				 
				 int i = smsLogDao.insertSmsLog(smsLogDTO);
				 
				 if(i == 1) {
					 return rpcResult.buildSuccessResp("短信发送成功");
				 } else {
					 return rpcResult.buildFailedResp("短信发送成功,数据插入失败");
				 }
				 
			} else {
				return rpcResult.buildFailedResp(msg);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return rpcResult.buildFailedResp("短信发送接口发生异常");
		}
	}

}
