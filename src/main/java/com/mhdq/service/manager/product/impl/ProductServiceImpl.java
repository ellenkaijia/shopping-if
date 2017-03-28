package com.mhdq.service.manager.product.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.manager.product.dto.ProductDTO;
import com.mhdq.dao.manager.product.ProductDao;
import com.mhdq.exception.ServiceException;
import com.mhdq.service.manager.product.ProductService;
import com.server.api.util.GenerateCode;

@Service
public class ProductServiceImpl implements ProductService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProductDao productDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String createProduct(ProductDTO productDTO) throws ServiceException {
		logger.info("******正在调用产品管理模块的第二层服务(增加一个产品)*******");
		String prodId = GenerateCode.generateProductId();  //生成唯一的产品编号
		this.wrapperInitToDB(productDTO,prodId); //包装几个默认参数
		int count = productDao.insert(productDTO);
		if(count >= 1) {
			return prodId;
		} else {
			throw new ServiceException("******产品参数插入数据库有误******");
		}
	}
	
	private void wrapperInitToDB(ProductDTO productDTO, String prodId) {
		productDTO.setProdSellSum(0);
		productDTO.setStatus(0);
		productDTO.setProdId(prodId);
	}


}
