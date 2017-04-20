package com.mhdq.dao.manager.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.hibernate.annotations.Sort;

import com.manager.product.dto.ProductBandDTO;
import com.manager.product.dto.ProductDTO;
import com.manager.product.dto.SortDTO;
import com.mhdq.dao.manager.BaseDao;
import com.server.api.easyui.Page;
import com.server.dto.SBandDTO;
import com.server.dto.SProductDTO;
import com.server.dto.SSortDTO;

public interface ProductDao extends BaseDao<ProductDTO> {

	List<ProductDTO> dataGrid(Page page);
	
	int deleteByProductId(@Param("prodId") String productId);
	
	int updateProductStatus(@Param("prodId") String prodId);
	
	int addBand(ProductBandDTO productBandDTO);
	
	int addSort(SortDTO sortDTO);
	
	List<ProductBandDTO> showBand(Page page);
	
	List<SortDTO> showSort(Page page);
	
	SortDTO getSortBySortId(@Param("sortId") String sortId);
	
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
	
	List<SProductDTO> getproductByParamsHot(@Param("orderBy") String orderBy, @Param("whatOrder") String whatOrder, @Param("start") int start, @Param("end") int end);
	
	List<SProductDTO> getproductByParamsNew(@Param("orderBy") String orderBy, @Param("whatOrder") String whatOrder, @Param("start") int start, @Param("end") int end);
	
	List<SProductDTO> getproductByParamsBand(@Param("bandId") String bandId, @Param("orderBy") String orderBy, @Param("whatOrder") String whatOrder, @Param("start") int start, @Param("end") int end);
	
	List<SProductDTO> getproductByParamsSort(@Param("sortId") String sortId, @Param("orderBy") String orderBy, @Param("whatOrder") String whatOrder, @Param("start") int start, @Param("end") int end);
	
	List<SProductDTO> getproductByParamsMore(@Param("more") Integer more, @Param("orderBy") String orderBy, @Param("whatOrder") String whatOrder, @Param("start") int start, @Param("end") int end);
	
	int getCountProduct();
	
	List<SBandDTO> selectAllBand();
	
	List<SortDTO> getProductSortList();
	
	List<SSortDTO> selectAllSort();
	
	SBandDTO selectBandByBandId(@Param("bandId") String bandId);
	
	List<SSortDTO> selectSortBySortName(@Param("sortName") String sortName);
	
	List<SBandDTO> selectBandByBandName(@Param("bandName") String bandName);
	
	List<SProductDTO> selectBySearchParam(@Param("search") String search);
	
	List<SProductDTO> selectByBandId(@Param("bandId") String bandId);
	
	List<SProductDTO> selectBySortId(@Param("sortId") String sortId);
}
