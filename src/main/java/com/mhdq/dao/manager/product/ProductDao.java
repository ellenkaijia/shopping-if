package com.mhdq.dao.manager.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.manager.product.dto.ProductDTO;
import com.mhdq.dao.manager.BaseDao;
import com.server.api.easyui.Page;

public interface ProductDao extends BaseDao<ProductDTO> {

	List<ProductDTO> dataGrid(Page page);
	
	int deleteByProductId(@Param("prodId") String productId);
	
	int updateProductStatus(@Param("prodId") String prodId);
	
}
