package com.mhdq.service.server.product;

import java.util.List;

import com.server.dto.SCurentPageDTO;
import com.server.dto.SProductLevelDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月6日  新建  
*/
public interface SProductService {

	SProductLevelDTO getProductDetail(String prodId);
	
	List<SProductLevelDTO> getProductHot(SCurentPageDTO sCurentPageDTO);
	
	List<SProductLevelDTO> getProductNew(SCurentPageDTO sCurentPageDTO);
}
