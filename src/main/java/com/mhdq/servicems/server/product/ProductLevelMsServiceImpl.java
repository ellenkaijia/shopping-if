package com.mhdq.servicems.server.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mhdq.service.server.product.ProductLevelService;
import com.server.dto.SBandShowDTO;
import com.server.dto.SProductLevelDTO;
import com.server.dto.SortShowDTO;
import com.server.rpc.ProductLevelMsService;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月5日  新建  
*/
public class ProductLevelMsServiceImpl implements ProductLevelMsService{

	@Autowired
	private ProductLevelService productLevelService;
	
	@Override
	public List<SProductLevelDTO> getProductLevelByCode(int prodTypeCode) {
		return productLevelService.getProductLevelByCode(prodTypeCode);
	}

	@Override
	public List<SBandShowDTO> getProductBandAll() {
		return productLevelService.getProductBandAll();
	}

	@Override
	public List<SortShowDTO> getSortAll() {
		return productLevelService.getSortAll();
	}

}
