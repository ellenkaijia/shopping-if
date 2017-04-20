package com.mhdq.service.server.product;

import java.util.List;

import com.server.dto.ShopCarShowDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月15日  新建  
*/
public interface ShopCartService {

	List<ShopCarShowDTO> getShopCartList(String userId);
	
	boolean changeShopCartNum(String prodId, Integer num, String userId);
	
	boolean deleteShopCart(String prodId, String userId);
	
	
}
