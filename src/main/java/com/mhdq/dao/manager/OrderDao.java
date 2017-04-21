package com.mhdq.dao.manager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.server.dto.SOrderDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月17日  新建  
*/
public interface OrderDao {

	int insertOrder(SOrderDTO sOrderDTO);
	
	List<SOrderDTO> selectByUserId(@Param("userId") String userId);
	
	List<SOrderDTO> selectByOrderId(@Param("orderId") String orderId);
	
	int deleteByOrderId(@Param("orderId") String orderId);
	
	List<SOrderDTO> selectByStatusUId(@Param("status") Integer status, @Param("userId") String userId);
	
	int updateStatusByOrderId(@Param("status") Integer status, @Param("orderId") String orderId);
	
	int updateTalkStatusByOrderIdProdId(@Param("orderId") String orderId, @Param("prodId") String prodId);
}
