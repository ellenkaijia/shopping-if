package com.mhdq.servicems.manager.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.manager.product.dto.ProductDTO;
import com.mhdq.exception.ServiceException;
import com.mhdq.manager.api.service.product.ProductMsService;
import com.mhdq.rpc.RpcRespDTO;
import com.mhdq.service.manager.product.ProductService;

/**  
* 产品服务类  
*  
* @author zkj  
* @date 2017年3月27日  新建  
*/
public class ProductMsServiceImpl implements ProductMsService {

	@Autowired
	private ProductService productService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public RpcRespDTO<String> createPrpduct(ProductDTO productDTO) {
		
		RpcRespDTO<String> result = new RpcRespDTO<>();
		String prodId = "";
		try {
			prodId = productService.createProduct(productDTO);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			return result.buildFailedResp(e.getMessage());
		}
		return result.buildSuccessResp(prodId);
	}

}
