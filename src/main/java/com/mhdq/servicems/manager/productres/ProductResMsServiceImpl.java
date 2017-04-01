package com.mhdq.servicems.manager.productres;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.manager.product.dto.ProductResDTO;
import com.mhdq.manager.api.service.productres.ProductResMsService;
import com.mhdq.rpc.RpcRespDTO;
import com.mhdq.service.manager.product.ProductService;
import com.mhdq.service.manager.productres.ProductResService;

/**  
* 产品资源服务  
*  
* @author zkj  
* @date 2017年3月28日  新建  
*/
public class ProductResMsServiceImpl implements ProductResMsService {

	@Autowired
	private ProductResService productResService;
	
	
	@Override
	public RpcRespDTO<Integer> createProductRes(List<ProductResDTO> list) {
		RpcRespDTO<Integer> rpcRespDTO = new RpcRespDTO<>();
		int cout = productResService.createProductRes(list);
		if(cout <= 0) {
			return rpcRespDTO.buildFailedResp(null);
		}
		return rpcRespDTO.buildSuccessResp(cout);
	}

	@Override
	public List<ProductResDTO> getProductResList(String prodId) {
		return productResService.getProductResList(prodId);
	}
	

}
