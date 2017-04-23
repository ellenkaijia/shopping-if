package com.mhdq.service.server.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.server.dto.SAddressDTO;
import com.server.dto.SProductLevelDTO;
import com.server.dto.SRepairDTO;
import com.server.dto.STalkDTO;
import com.server.dto.SUserDTO;
import com.server.dto.SUserOrderShowDTO;
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
	
	Integer getMyShopCartCount(String userId);
	
	Integer isFavorByUIdProdId(String userId, String prodId);
	
	boolean addInterest(String userId, String prodId);
	
	boolean removeInterest(String userId, String prodId);
	
	List<SProductLevelDTO> getMyCollectionList(String userId);
	
	boolean removeCollection(String userId, String prodId);
	
	Map<String, String> tradeIntoOrder(String userId, String prodId, Integer buyCount, BigDecimal moneySum, Long addressId);
	
	Map<String, String> shopCartradeOrder(String userId, String prodIds, Long addressId) throws Exception;
	
	List<SUserOrderShowDTO> getUserOrder(String userId, Integer status);

	List<SProductLevelDTO> getOrderByUidPid(String userId, String prodId, Integer prodCount);
	
	SAddressDTO getAddressStatusOne(String userId);
	
	boolean isHaveAddress(String userId);
	
	Integer addAddress(String userId, SAddressDTO sAddressDTO);
	
	List<SAddressDTO> getAddressList(String userId);
	
	Integer deleteAddress(Long id);
	
	Integer updateAddressStatus(String userId, Integer id);
	
	SAddressDTO geAddressDTOByOrderId(String userId, String orderId);
	
	List<SProductLevelDTO> getProductByOrderId(String orderId);
	
	Integer addRepair(SRepairDTO sRepairDTO);
	
	Integer addTalk(STalkDTO stalkDTO);
	
	List<SRepairDTO> getMyRepair(String userId);
	
	Integer addNoBuyRepair(SRepairDTO sRepairDTO);
	
	Integer getProductTalkCount(String prodId);
	
}
