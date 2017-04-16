package com.mhdq.dao.manager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.server.dto.SFavorDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月15日  新建  
*/
public interface FavorDao {

	int insertFavor(SFavorDTO sFavorDTO);
	
	List<SFavorDTO> selectByUserId(@Param("userId") String userId);
	
	SFavorDTO selectByUIdProdId(@Param("userId") String userId, @Param("prodId") String prodId);
	
	int deleteFavorByUIdProdId(@Param("userId") String userId, @Param("prodId") String prodId);
	
	int selectCountByUId(@Param("userId") String userId);
	
}
