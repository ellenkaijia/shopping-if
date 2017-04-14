package com.mhdq.servicems.server.product;

import org.springframework.beans.factory.annotation.Autowired;

import com.mhdq.service.server.product.UserService;
import com.server.dto.SUserDTO;
import com.server.rpc.UserMsService;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月14日  新建  
*/
public class UserMsServiceImpl implements UserMsService {

	@Autowired
	private UserService userService;
	
	@Override
	public Integer checkPassword(SUserDTO sUserDTO) {
		return userService.checkPassword(sUserDTO);
	}

	@Override
	public boolean checkPhone(String phone) {
		return userService.checkPhone(phone);
	}

	@Override
	public boolean sendSms(String phone) {
		return userService.sendSms(phone);
	}

	@Override
	public Integer registerGo(SUserDTO sUserDTO) {
		return userService.registerGo(sUserDTO);
	}

}
