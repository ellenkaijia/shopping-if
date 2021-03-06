package com.mhdq.dao.manager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.server.dto.ShopCartDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月15日  新建  
*/
public interface MarketCarDao {

	int insertMarketCar(ShopCartDTO shopCartDTO);
	
	List<ShopCartDTO> selectByUserId(@Param("userId") String userId);
	
	ShopCartDTO selectByUIdProdId(ShopCartDTO shopCartDTO);
	
	ShopCartDTO selectByUIdProdIdTwo(@Param("userId") String userId, @Param("prodId") String prodId);
	
	int updateProdCountByUIdProdId(@Param("userId") String userId, @Param("prodId") String prodId, @Param("prodCount") Integer prodCount);
	
	int deleteProdByUIdProdId(@Param("userId") String userId, @Param("prodId") String prodId);
	
	int getMyShopCartCountByUId(@Param("userId") String userId);
	
	int deleteProdByUId(@Param("userId") String userId);
	
}
