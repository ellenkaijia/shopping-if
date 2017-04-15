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
import com.mhdq.service.server.product.ProductLevelService;
import com.server.dto.SBandDTO;
import com.server.dto.SBandShowDTO;
import com.server.dto.SProductDTO;
import com.server.dto.SProductLevelDTO;
import com.server.dto.SProductResDTO;
import com.server.dto.SSortDTO;
import com.server.dto.SortShowDTO;

/**
 * 类说明
 * 
 * @author zkj
 * @date 2017年4月5日 新建
 */
@Service
public class ProductLevelServiceImpl implements ProductLevelService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductResDao productResDao;

	@Override
	public List<SProductLevelDTO> getProductLevelByCode(int prodTypeCode) {
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
	public List<SBandShowDTO> getProductBandAll() {
		logger.info("******调用ProductLevelServiceImpl的getProductBandAll方法*******");

		List<SBandDTO> result = productDao.selectAllBand();

		List<SBandShowDTO> returnList = new ArrayList<>();
		SBandShowDTO sBandShowDTO = null;

		if (!CollectionUtils.isEmpty(result)) {
			logger.info("******开始构造SProductLevelDTO循环*******");
			for (SBandDTO sBandDTO : result) {
				sBandShowDTO = new SBandShowDTO();
				// 开始赋值
				this.wrapBandDB(sBandShowDTO, sBandDTO);

				List<SProductResDTO> resList = productResDao.getSProdResByProdId(sBandDTO.getBandId());
				if (CollectionUtils.isEmpty(resList)) {
					returnList.add(sBandShowDTO);
					logger.info("******没有找到对应的图片资源******");
					continue;
				}
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("bandPicResource" + File.separator + resList.get(0).getResId() + ".jpg");
				sBandShowDTO.setImgUrl(stringBuffer.toString());
				returnList.add(sBandShowDTO);
			}
		}

		return returnList;
	}
	
	private void wrapBandDB(SBandShowDTO sBandShowDTO, SBandDTO sBandDTO) {
		sBandShowDTO.setBandId(sBandDTO.getBandId());
		sBandShowDTO.setBandName(sBandDTO.getBandName());
		sBandShowDTO.setId(sBandDTO.getId());
	}
	
	private void wrapSortDB(SortShowDTO sortShowDTO, SSortDTO ssortDTO) {
		sortShowDTO.setId(ssortDTO.getId());
		sortShowDTO.setSortId(ssortDTO.getSortId());
		sortShowDTO.setSortName(ssortDTO.getSortName());
	}

	@Override
	public List<SortShowDTO> getSortAll() {
		logger.info("******调用ProductLevelServiceImpl的getSortAll方法*******");

		List<SSortDTO> result = productDao.selectAllSort();

		List<SortShowDTO> returnList = new ArrayList<>();
		SortShowDTO sortShowDTO = null;

		if (!CollectionUtils.isEmpty(result)) {
			logger.info("******开始构造SortShowDTO循环*******");
			for (SSortDTO sortDTO : result) {
				sortShowDTO = new SortShowDTO();
				// 开始赋值
				this.wrapSortDB(sortShowDTO, sortDTO);

				List<SProductResDTO> resList = productResDao.getSProdResByProdId(sortDTO.getSortId());
				if (CollectionUtils.isEmpty(resList)) {
					returnList.add(sortShowDTO);
					logger.info("******没有找到对应的图片资源******");
					continue;
				}
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("sortPicResource" + File.separator + resList.get(0).getResId() + ".jpg");
				sortShowDTO.setImgUrl(stringBuffer.toString());
				returnList.add(sortShowDTO);
			}
		}

		return returnList;
	}

}
