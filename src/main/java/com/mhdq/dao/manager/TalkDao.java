package com.mhdq.dao.manager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.server.dto.STalkDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月20日  新建  
*/
public interface TalkDao {

	int insert(STalkDTO sTalkDTO);
	
	int getCountByProdId(@Param("prodId") String prodId); 
	
	List<STalkDTO> getTalkListByprodId(@Param("prodId") String prodId);
}
