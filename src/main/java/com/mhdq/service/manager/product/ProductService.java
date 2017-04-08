package com.mhdq.service.manager.product;

import java.util.List;

import com.manager.product.dto.ProductBandDTO;
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
	
	String addBand(ProductBandDTO productBandDTO);
	
	DataGrid showBand(ProductBandDTO productBandDTO, Page page);
	
	List<ProductBandDTO> getProductBandList();
	
	Integer gonew(Long id);
	
	Integer cacelgonew(Long id);
	
	Integer gohot(Long id);
	
	Integer cacelgohot(Long id);
	
}
