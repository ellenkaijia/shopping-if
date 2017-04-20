package com.mhdq.dao.manager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.server.dto.SAddressDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月19日  新建  
*/
public interface AddressDao {

	int insertSAddress(SAddressDTO sAddressDTO);
	
	List<SAddressDTO> selectByUserId(@Param("userId") String userId);
	
	int deleteAddressById(@Param("id") Integer id);
	
	SAddressDTO selectByUidStatusOne(@Param("userId") String userId);
	
	int updateAddressStatusZeroByUserId(@Param("userId") String userId);
	
	int updateAddressStatusOneById(@Param("id") Integer id);
}
