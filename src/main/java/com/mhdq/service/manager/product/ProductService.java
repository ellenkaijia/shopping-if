package com.mhdq.service.manager.product;

import com.manager.product.dto.ProductDTO;
import com.mhdq.exception.ServiceException;
import com.server.api.easyui.DataGrid;
import com.server.api.easyui.Page;

public interface ProductService {

	/**
	 * 录入记录
	 * @param productDTO
	 */
	String createProduct(ProductDTO productDTO) throws ServiceException;
	
	DataGrid dataGrid(ProductDTO productDTO, Page page);
	
	Integer deleteProduct(String productId);
	
	Integer updateProduct(ProductDTO productDTO);
	
	Integer releaseProduct(String productId);
	
	ProductDTO showProduct(Long id);
}
