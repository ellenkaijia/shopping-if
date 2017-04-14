package com.mhdq.servicems.manager.product;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.manager.product.dto.ProductBandDTO;
import com.manager.product.dto.ProductDTO;
import com.manager.product.dto.SortDTO;
import com.mhdq.dao.manager.productres.ProductResDao;
import com.mhdq.exception.ServiceException;
import com.mhdq.manager.api.service.product.ProductMsService;
import com.mhdq.rpc.RpcRespDTO;
import com.mhdq.service.manager.product.ProductService;
import com.mhdq.service.manager.productres.ProductResService;
import com.server.api.easyui.DataGrid;
import com.server.api.easyui.Page;

/**  
* 产品服务类  
*  
* @author zkj  
* @date 2017年3月27日  新建  
*/
public class ProductMsServiceImpl implements ProductMsService {

	@Autowired
	private ProductService productService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public RpcRespDTO<String> createPrpduct(ProductDTO productDTO) {
		
		RpcRespDTO<String> result = new RpcRespDTO<>();
		String prodId = "";
		try {
			prodId = productService.createProduct(productDTO);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			return result.buildFailedResp(e.getMessage());
		}
		return result.buildSuccessResp(prodId);
	}

	@Override
	public DataGrid dataGrid(ProductDTO productDTO, Page page) {
		return productService.dataGrid(productDTO, page);
	}

	@Override
	public RpcRespDTO<Integer> deleteProduct(String productId) {
		RpcRespDTO<Integer> result = new RpcRespDTO<>();
		Integer ret = productService.deleteProduct(productId);
		return result.buildSuccessResp(ret);
	}

	@Override
	public RpcRespDTO<Integer> updateProduct(ProductDTO productDTO) {
		RpcRespDTO<Integer> result = new RpcRespDTO<>();
		Integer ret = productService.updateProduct(productDTO);
		return result.buildSuccessResp(ret);
	}

	@Override
	public RpcRespDTO<Integer> releaseProduct(String productId) {
		RpcRespDTO<Integer> result = new RpcRespDTO<>();
		Integer ret = productService.releaseProduct(productId);
		return result.buildSuccessResp(ret);
	}

	@Override
	public ProductDTO showProduct(Long id) {
		return productService.showProduct(id);
	}

	@Override
	public RpcRespDTO<String> addBand(ProductBandDTO productBandDTO) {
		RpcRespDTO<String> result = new RpcRespDTO<>();
		String ret = productService.addBand(productBandDTO);
		return result.buildSuccessResp(ret);
	}

	@Override
	public DataGrid showBand(ProductBandDTO productBandDTO, Page page) {
		return productService.showBand(productBandDTO, page);
	}

	@Override
	public List<ProductBandDTO> getProductBandList() {
		return productService.getProductBandList();
	}

	@Override
	public RpcRespDTO<Integer> gonew(Long id) {
		RpcRespDTO<Integer> result = new RpcRespDTO<>();
		Integer ret = productService.gonew(id);
		return result.buildSuccessResp(ret);
	}

	@Override
	public RpcRespDTO<Integer> cacelgonew(Long id) {
		RpcRespDTO<Integer> result = new RpcRespDTO<>();
		Integer ret = productService.cacelgonew(id);
		return result.buildSuccessResp(ret);
	}

	@Override
	public RpcRespDTO<Integer> gohot(Long id) {
		RpcRespDTO<Integer> result = new RpcRespDTO<>();
		Integer ret = productService.gohot(id);
		return result.buildSuccessResp(ret);
	}

	@Override
	public RpcRespDTO<Integer> cacelgohot(Long id) {
		RpcRespDTO<Integer> result = new RpcRespDTO<>();
		Integer ret = productService.cacelgohot(id);
		return result.buildSuccessResp(ret);
	}

	@Override
	public RpcRespDTO<String> addSort(SortDTO sortDTO) {
		RpcRespDTO<String> result = new RpcRespDTO<>();
		String ret = productService.addSort(sortDTO);
		return result.buildSuccessResp(ret);
	}

	@Override
	public DataGrid showSort(SortDTO sortDTO, Page page) {
		return productService.showSort(sortDTO, page);
	}

	@Override
	public List<SortDTO> getProductSortList() {
		return productService.getProductSortList();
	}

}
