package com.mhdq.service.manager.product.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.manager.product.dto.ProductBandDTO;
import com.manager.product.dto.ProductDTO;
import com.manager.product.dto.ProductResDTO;
import com.mhdq.dao.manager.product.ProductDao;
import com.mhdq.dao.manager.productres.ProductResDao;
import com.mhdq.exception.ServiceException;
import com.mhdq.service.enums.ProdType;
import com.mhdq.service.manager.product.ProductService;
import com.server.api.easyui.DataGrid;
import com.server.api.easyui.Page;
import com.server.api.util.GenerateCode;

@Service
public class ProductServiceImpl implements ProductService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductResDao productResDao;
	
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
	
	/**
	 * @param productDTO
	 * @param prodId
	 */
	private void wrapperInitToDB(ProductDTO productDTO, String prodId) {
		productDTO.setProdSellSum(0);
		productDTO.setStatus(0);
		productDTO.setProdId(prodId);
		int typeCode = ProdType.getByStatusCode(productDTO.getProdTypeName()).code;
		productDTO.setProdTypeCode(typeCode);
		productDTO.setIsHot(0);
		productDTO.setIsNew(0);
		
	}

	@Override
	public DataGrid dataGrid(ProductDTO productDTO, Page page) {
		page.setParams(productDTO);
		List<ProductDTO> list = productDao.dataGrid(page);
		return Page.getDataGrid(page, list, ProductDTO.class);
	}

	@Override
	public Integer deleteProduct(String productId) {
		productResDao.deleteResByProdId(productId);
		return productDao.deleteByProductId(productId);
	}

	@Override
	public Integer updateProduct(ProductDTO productDTO) {
		return productDao.update(productDTO);
	}

	@Override
	public Integer releaseProduct(String productId) {
		return productDao.updateProductStatus(productId);
	}

	@Override
	public ProductDTO showProduct(Long id) {
		return productDao.selectByPrimaryKey(id);
	}

	@Override
	public String addBand(ProductBandDTO productBandDTO) {
		String bandId = GenerateCode.generateBandId();
		productBandDTO.setBandId(bandId);
		int ret = productDao.addBand(productBandDTO);
		if(ret == 1) {
			return bandId;
		} else {
			return null;
		}
	}

	@Override
	public DataGrid showBand(ProductBandDTO productBandDTO, Page page) {
		page.setParams(productBandDTO);
		List<ProductBandDTO> list = productDao.showBand(page);
		return Page.getDataGrid(page, list, ProductBandDTO.class);
	}

	@Override
	public List<ProductBandDTO> getProductBandList() {
		List<ProductBandDTO> list = new ArrayList<>();
		list = productDao.getProductBandList();
		return list;
	}

	@Override
	public Integer gonew(Long id) {
		return productDao.gonewById(id);
	}

	@Override
	public Integer cacelgonew(Long id) {
		return productDao.cacelgonewById(id);
	}

	@Override
	public Integer gohot(Long id) {
		return productDao.gohotById(id);
	}

	@Override
	public Integer cacelgohot(Long id) {
		return productDao.cacelgohotById(id);
	}


}
