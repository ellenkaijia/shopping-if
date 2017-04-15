package com.mhdq.service.server.product;

import com.server.dto.SUserDTO;
import com.server.dto.ShopCartDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月14日  新建  
*/
public interface UserService {

	boolean checkPhone(String phone);
	
	boolean sendSms(String phone);
	
	Integer checkPassword(SUserDTO sUserDTO);
	
	Integer registerGo(SUserDTO sUserDTO);
	
	String queryUserId(SUserDTO sUserDTO);
	
	Integer addShopCart(ShopCartDTO shopCartDTO);
	
	Integer getMyFavorCount(String userId);
	
	SUserDTO getUserAllByUid(String userId);
}
