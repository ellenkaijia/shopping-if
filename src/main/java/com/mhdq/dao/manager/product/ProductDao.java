package com.mhdq.dao.manager.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.manager.product.dto.ProductBandDTO;
import com.manager.product.dto.ProductDTO;
import com.mhdq.dao.manager.BaseDao;
import com.server.api.easyui.Page;
import com.server.dto.SProductDTO;

public interface ProductDao extends BaseDao<ProductDTO> {

	List<ProductDTO> dataGrid(Page page);
	
	int deleteByProductId(@Param("prodId") String productId);
	
	int updateProductStatus(@Param("prodId") String prodId);
	
	int addBand(ProductBandDTO productBandDTO);
	
	List<ProductBandDTO> showBand(Page page);
	
	List<ProductBandDTO> getProductBandList();
	
	int gonewById(Long id);
	
	int cacelgonewById(Long id);
	
	int gohotById(Long id);
	
	int cacelgohotById(Long id);
	
	//server
	List<SProductDTO> getProductByProdTypeCode(@Param("prodTypeCode") int prodTypeCode);
	
	SProductDTO getProductByProdId(@Param("prodId") String prodId);
	
	List<SProductDTO> getproductByIsNew(@Param("isNew") int isNew);
	
	List<SProductDTO> getproductByIsHot(@Param("isHot") int isHot);
}
