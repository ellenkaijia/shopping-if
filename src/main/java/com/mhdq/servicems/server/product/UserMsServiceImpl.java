package com.mhdq.servicems.server.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mhdq.service.server.product.UserService;
import com.server.dto.SProductLevelDTO;
import com.server.dto.SUserDTO;
import com.server.dto.ShopCartDTO;
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

	@Override
	public String queryUserId(SUserDTO sUserDTO) {
		return userService.queryUserId(sUserDTO);
	}

	@Override
	public Integer addShopCart(ShopCartDTO shopCartDTO) {
		return userService.addShopCart(shopCartDTO);
	}

	@Override
	public Integer getMyFavorCount(String userId) {
		return userService.getMyFavorCount(userId);
	}

	@Override
	public SUserDTO getUserAllByUid(String userId) {
		return userService.getUserAllByUid(userId);
	}

	@Override
	public Integer getMyShopCartCount(String userId) {
		return userService.getMyShopCartCount(userId);
	}

	@Override
	public Integer isFavorByUIdProdId(String userId, String prodId) {
		return userService.isFavorByUIdProdId(userId, prodId);
	}

	@Override
	public boolean addInterest(String userId, String prodId) {
		return userService.addInterest(userId, prodId);
	}

	@Override
	public boolean removeInterest(String userId, String prodId) {
		return userService.removeInterest(userId, prodId);
	}

	@Override
	public List<SProductLevelDTO> getMyCollectionList(String userId) {
		return userService.getMyCollectionList(userId);
	}

	@Override
	public boolean removeCollection(String userId, String prodId) {
		return userService.removeCollection(userId,prodId);
	}

}
