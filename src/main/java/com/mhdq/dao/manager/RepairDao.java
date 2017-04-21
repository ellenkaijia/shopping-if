package com.mhdq.dao.manager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.server.dto.SRepairDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月20日  新建  
*/
public interface RepairDao {

	int insert(SRepairDTO sRepairDTO);
	
	List<SRepairDTO> getRepairList(@Param("userId") String userId);
	
	
}
