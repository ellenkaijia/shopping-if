package com.mhdq.dao.manager.band;

import java.util.List;

import com.server.dto.SBandDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月11日  新建  
*/
public interface BandDao {
	
	List<SBandDTO> selectAllBand();
}
