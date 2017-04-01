package com.mhdq.dao.manager.productres;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.manager.product.dto.ProductResDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年3月28日  新建  
*/
public interface ProductResDao {

	ProductResDTO selectByPrimaryKey(Long id);
	
	int insert(ProductResDTO t);
	
	int insertByBatch(@Param("list") List<ProductResDTO> list);
	
	List<ProductResDTO> getProdResByProdId(@Param("prodId") String prodId);
}
