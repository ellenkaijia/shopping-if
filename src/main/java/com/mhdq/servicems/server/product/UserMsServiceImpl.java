package com.mhdq.servicems.server.product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mhdq.service.server.product.UserService;
import com.server.dto.SAddressDTO;
import com.server.dto.SProductLevelDTO;
import com.server.dto.SUserDTO;
import com.server.dto.SUserOrderShowDTO;
import com.server.dto.ShopCartDTO;
import com.server.rpc.UserMsService;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月14日  新建  
*/
public class UserMsServiceImpl implements UserMsService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
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

	@Override
	public Map<String, String> tradeIntoOrder(String userId, String prodId, Integer buyCount, BigDecimal moneySum, Long addressId) {
		return userService.tradeIntoOrder(userId, prodId, buyCount, moneySum, addressId);
	}

	@Override
	public Map<String, String> shopCartradeOrder(String userId, String prodIds, Long addressId) {
		Map<String, String> result = new HashMap<>();
		try {
			return userService.shopCartradeOrder(userId, prodIds,addressId);
		} catch (Exception e) {
			result.put("code", "-99");
			result.put("orderId", "交易失败");
			logger.error(e.getMessage());
			return result;
		}
		
	}

	@Override
	public List<SUserOrderShowDTO> getUserOrder(String userId, Integer status) {
		return userService.getUserOrder(userId, status);
	}

	@Override
	public List<SProductLevelDTO> getOrderByUidPid(String userId, String prodId, Integer prodCount) {
		return userService.getOrderByUidPid(userId, prodId, prodCount);
	}

	@Override
	public SAddressDTO getAddressStatusOne(String userId) {
		return userService.getAddressStatusOne(userId);
	}

	@Override
	public boolean isHaveAddress(String userId) {
		return userService.isHaveAddress(userId);
	}

	@Override
	public Integer addAddress(String userId, SAddressDTO sAddressDTO) {
		return userService.addAddress(userId, sAddressDTO);
	}

	@Override
	public List<SAddressDTO> getAddressList(String userId) {
		return userService.getAddressList(userId);
	}

	@Override
	public Integer deleteAddress(Integer id) {
		return userService.deleteAddress(id);
	}

	@Override
	public Integer updateAddressStatus(String userId, Integer id) {
		return userService.updateAddressStatus(userId, id);
	}

}
