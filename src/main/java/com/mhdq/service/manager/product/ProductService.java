package com.mhdq.service.manager.product;

import com.manager.product.dto.ProductDTO;
import com.mhdq.exception.ServiceException;

public interface ProductService {

	/**
	 * 录入记录
	 * @param productDTO
	 */
	String createProduct(ProductDTO productDTO) throws ServiceException;
}
