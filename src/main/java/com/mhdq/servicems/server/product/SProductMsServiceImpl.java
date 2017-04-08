package com.mhdq.servicems.server.product;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mhdq.service.server.product.SProductService;
import com.server.dto.SProductLevelDTO;
import com.server.rpc.SProductMsService;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月6日  新建  
*/
public class SProductMsServiceImpl implements SProductMsService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SProductService sProductService;
	
	@Override
	public SProductLevelDTO getProductDetail(String prodId) {
		logger.info("*********进入SProductMsServiceImpl的getProductDetail********");
		return sProductService.getProductDetail(prodId);
	}

	@Override
	public List<SProductLevelDTO> getProductHot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SProductLevelDTO> getProductNew() {
		// TODO Auto-generated method stub
		return null;
	}

}