package com.mhdq.service.server.product;

import java.util.List;

import com.server.dto.SBandShowDTO;
import com.server.dto.SProductLevelDTO;
import com.server.dto.SortShowDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月5日  新建  
*/
public interface ProductLevelService {

	List<SProductLevelDTO> getProductLevelByCode(int prodTypeCode);
	
	List<SBandShowDTO> getProductBandAll();
	
	List<SortShowDTO> getSortAll();
}
