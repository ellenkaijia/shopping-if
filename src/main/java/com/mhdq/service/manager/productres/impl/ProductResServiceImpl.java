package com.mhdq.service.manager.productres.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.manager.product.dto.ProductResDTO;
import com.mhdq.dao.manager.productres.ProductResDao;
import com.mhdq.service.manager.productres.ProductResService;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年3月28日  新建  
*/
@Service
public class ProductResServiceImpl implements ProductResService {

	@Autowired
	private ProductResDao productResDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int createProductRes(List<ProductResDTO> list) {
		return productResDao.insertByBatch(list);
	}

	@Override
	public List<ProductResDTO> getProductResList(String prodId) {
		return productResDao.getProdResByProdId(prodId);
	}

}
