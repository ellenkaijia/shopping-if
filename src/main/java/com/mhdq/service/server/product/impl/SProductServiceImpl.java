package com.mhdq.service.server.product.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mhdq.dao.manager.product.ProductDao;
import com.mhdq.dao.manager.productres.ProductResDao;
import com.mhdq.service.server.product.SProductService;
import com.server.dto.SProductDTO;
import com.server.dto.SProductLevelDTO;
import com.server.dto.SProductResDTO;

/**
 * 类说明
 * 
 * @author zkj
 * @date 2017年4月6日 新建
 */
@Service
public class SProductServiceImpl implements SProductService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductResDao productResDao;

	@Override
	public SProductLevelDTO getProductDetail(String prodId) {
		logger.info("******获取产品详情服务*****");
		SProductLevelDTO sProductLevelDTO = new SProductLevelDTO();
		List<String> imgUrls = new ArrayList<>();
		SProductDTO sProductDTO = productDao.getProductByProdId(prodId);

		if (sProductDTO == null) {
			logger.info("******获取产品详情服务,获取DTO为空*****");
			return sProductLevelDTO;
		}

		// 开始赋值
		this.wrapIntiDB(sProductLevelDTO, sProductDTO);

		List<SProductResDTO> resList = productResDao.getSProdResByProdId(sProductDTO.getProdId());
		if (CollectionUtils.isEmpty(resList)) {
			logger.info("******没有找到对应的图片资源******");
			return sProductLevelDTO;
		}

		// 不从0开始是要去掉第一个null的父目录
		for (int i = 1; i < resList.size(); i++) {
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("picResource" + File.separator + resList.get(i).getResParentId() + File.separator
					+ resList.get(i).getResId() + ".jpg");
			imgUrls.add(stringBuffer.toString());
		}

		sProductLevelDTO.setImgUrls(imgUrls);
		return sProductLevelDTO;
	}

	private void wrapIntiDB(SProductLevelDTO sP, SProductDTO p) {

		sP.setId(p.getId());
		sP.setBandId(p.getBandId());
		sP.setCodeId(p.getCodeId());
		sP.setCreateTime(p.getCreateTime());
		sP.setIsHot(p.getIsHot());
		sP.setIsNew(p.getIsNew());
		sP.setProdDetail(p.getProdDetail());
		sP.setProdFreeTime(p.getProdFreeTime());
		sP.setProdId(p.getProdId());
		sP.setProdName(p.getProdName());
		sP.setProdPrize(p.getProdPrize());
		sP.setProdSellSum(p.getProdSellSum());
		sP.setProdSum(p.getProdSum());
		sP.setProdTypeCode(p.getProdTypeCode());
		sP.setProdTypeName(p.getProdTypeName());
		sP.setStatus(p.getStatus());
	}

	@Override
	public List<SProductLevelDTO> getProductHot() {

		logger.info("******调用ProductLevelServiceImpl的getProductLevelByCode方法*******");

		List<SProductDTO> result = productDao.getProductByProdTypeCode(prodTypeCode);

		List<SProductLevelDTO> returnList = new ArrayList<>();
		SProductLevelDTO sProductLevelDTO = null;
		List<String> imgUrls = null;

		if (!CollectionUtils.isEmpty(result)) {
			logger.info("******开始构造SProductLevelDTO循环*******");
			for (SProductDTO sProductDTO : result) {
				sProductLevelDTO = new SProductLevelDTO();
				imgUrls = new ArrayList<>();
				// 开始赋值
				this.wrapIntiDB(sProductLevelDTO, sProductDTO);

				List<SProductResDTO> resList = productResDao.getSProdResByProdId(sProductDTO.getProdId());
				if (CollectionUtils.isEmpty(resList)) {
					returnList.add(sProductLevelDTO);
					logger.info("******没有找到对应的图片资源******");
					continue;
				}
				for (int i = 1; i < resList.size(); i++) {
					StringBuffer stringBuffer = new StringBuffer();
					stringBuffer.append("picResource" + File.separator + resList.get(i).getResParentId()
							+ File.separator + resList.get(i).getResId() + ".jpg");
					imgUrls.add(stringBuffer.toString());
				}
				sProductLevelDTO.setImgUrls(imgUrls);
				returnList.add(sProductLevelDTO);
			}
		}

		return returnList;

		productDao.getproductByIsHot(1);
		return null;
	}

	@Override
	public List<SProductLevelDTO> getProductNew() {
		// TODO Auto-generated method stub
		return null;
	}

}
