package com.mhdq.service.server.product.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mhdq.dao.manager.TalkDao;
import com.mhdq.dao.manager.UserDao;
import com.mhdq.dao.manager.product.ProductDao;
import com.mhdq.dao.manager.productres.ProductResDao;
import com.mhdq.service.server.product.SProductService;
import com.server.dto.SCurentPageDTO;
import com.server.dto.SProductDTO;
import com.server.dto.SProductLevelDTO;
import com.server.dto.SProductResDTO;
import com.server.dto.STalkDTO;
import com.server.dto.STalkShowDTO;
import com.server.dto.SUserDTO;

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
	
	@Autowired
	private TalkDao talkDao;
	
	@Autowired
	private UserDao userDao;

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

	private void wrapIntiDBCu(SProductLevelDTO sP, SProductDTO p, SCurentPageDTO sCurentPageDTO, int listTotal) {

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
		sP.setOrders(sCurentPageDTO.getOrders());
		sP.setCurPageNO(sCurentPageDTO.getCurPageNO());
		sP.setCurPageNum(sCurentPageDTO.getCurPageNum());
		sP.setListTotal(listTotal);
	}

	@Override
	public List<SProductLevelDTO> getProductHot(SCurentPageDTO sCurentPageDTO) {

		logger.info("******调用SProductServiceImpl的getProductHot方法*******");

		String[] orders = sCurentPageDTO.getOrders().split("[,，]");
		switch (orders[0]) {
		case "date":
			orders[0] = "create_time";
			break;
		case "cash":
			orders[0] = "prod_prize";
			break;
		case "buys":
			orders[0] = "prod_sell_sum";
			break;
		case "comments":
			orders[0] = "comment_count";
			break;
		default:
			orders[0] = "create_time";
			orders[1] = "desc";
			break;
		}

		List<SProductDTO> result = productDao.getproductByParamsHot(orders[0], orders[1],
				sCurentPageDTO.getCurPageNO() * sCurentPageDTO.getCurPageNum(), sCurentPageDTO.getCurPageNum());

		for (SProductDTO ss : result) {
			logger.info("SProductDTO :" + ss.getProdName());
		}

		int count = productDao.getCountProduct();

		int listTotal = count / sCurentPageDTO.getCurPageNum();
		if (count % sCurentPageDTO.getCurPageNum() != 0) {
			listTotal++;
		}

		List<SProductLevelDTO> returnList = new ArrayList<>();
		SProductLevelDTO sProductLevelDTO = null;
		List<String> imgUrls = null;

		if (!CollectionUtils.isEmpty(result)) {
			logger.info("******开始构造SProductLevelDTO循环*******");
			for (SProductDTO sProductDTO : result) {
				sProductLevelDTO = new SProductLevelDTO();
				imgUrls = new ArrayList<>();
				// 开始赋值
				this.wrapIntiDBCu(sProductLevelDTO, sProductDTO, sCurentPageDTO, listTotal);

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

	@Override
	public List<SProductLevelDTO> getProductNew(SCurentPageDTO sCurentPageDTO) {
		logger.info("******调用SProductServiceImpl的getProductNew方法*******");

		String[] orders = sCurentPageDTO.getOrders().split("[,，]");
		switch (orders[0]) {
		case "date":
			orders[0] = "create_time";
			break;
		case "cash":
			orders[0] = "prod_prize";
			break;
		case "buys":
			orders[0] = "prod_sell_sum";
			break;
		case "comments":
			orders[0] = "comment_count";
			break;
		default:
			orders[0] = "create_time";
			orders[1] = "desc";
			break;
		}

		List<SProductDTO> result = productDao.getproductByParamsNew(orders[0], orders[1],
				sCurentPageDTO.getCurPageNO() * sCurentPageDTO.getCurPageNum(), sCurentPageDTO.getCurPageNum());

		for (SProductDTO ss : result) {
			logger.info("SProductDTO :" + ss.getProdName());
		}

		int count = productDao.getCountProduct();

		int listTotal = count / sCurentPageDTO.getCurPageNum();
		if (count % sCurentPageDTO.getCurPageNum() != 0) {
			listTotal++;
		}

		List<SProductLevelDTO> returnList = new ArrayList<>();
		SProductLevelDTO sProductLevelDTO = null;
		List<String> imgUrls = null;

		if (!CollectionUtils.isEmpty(result)) {
			logger.info("******开始构造SProductLevelDTO循环*******");
			for (SProductDTO sProductDTO : result) {
				sProductLevelDTO = new SProductLevelDTO();
				imgUrls = new ArrayList<>();
				// 开始赋值
				this.wrapIntiDBCu(sProductLevelDTO, sProductDTO, sCurentPageDTO, listTotal);

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

	public static void main(String args[]) {
		String[] orders = "date,asc".split("[,，]");
		switch (orders[0]) {
		case "date":
			orders[0] = "dadadad";
			break;

		default:
			break;
		}
		System.out.println(orders[0]);
	}

	@Override
	public List<SProductLevelDTO> getBandList(String bandId, SCurentPageDTO sCurentPageDTO) {
		logger.info("******调用SProductServiceImpl的getBandList方法*******");
		String[] orders = sCurentPageDTO.getOrders().split("[,，]");
		switch (orders[0]) {
		case "date":
			orders[0] = "create_time";
			break;
		case "cash":
			orders[0] = "prod_prize";
			break;
		case "buys":
			orders[0] = "prod_sell_sum";
			break;
		case "comments":
			orders[0] = "comment_count";
			break;
		default:
			orders[0] = "create_time";
			orders[1] = "desc";
			break;
		}

		List<SProductDTO> result = productDao.getproductByParamsBand(bandId, orders[0], orders[1],
				sCurentPageDTO.getCurPageNO() * sCurentPageDTO.getCurPageNum(), sCurentPageDTO.getCurPageNum());

		for (SProductDTO ss : result) {
			logger.info("SProductDTO :" + ss.getProdName());
		}

		int count = productDao.getCountProduct();

		int listTotal = count / sCurentPageDTO.getCurPageNum();
		if (count % sCurentPageDTO.getCurPageNum() != 0) {
			listTotal++;
		}

		List<SProductLevelDTO> returnList = new ArrayList<>();
		SProductLevelDTO sProductLevelDTO = null;
		List<String> imgUrls = null;

		if (!CollectionUtils.isEmpty(result)) {
			logger.info("******开始构造SProductLevelDTO循环*******");
			for (SProductDTO sProductDTO : result) {
				sProductLevelDTO = new SProductLevelDTO();
				imgUrls = new ArrayList<>();
				// 开始赋值
				this.wrapIntiDBCu(sProductLevelDTO, sProductDTO, sCurentPageDTO, listTotal);

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

	@Override
	public List<SProductLevelDTO> getSortList(String sortId, SCurentPageDTO sCurentPageDTO) {
		logger.info("******调用SProductServiceImpl的getSortList方法*******");
		String[] orders = sCurentPageDTO.getOrders().split("[,，]");
		switch (orders[0]) {
		case "date":
			orders[0] = "create_time";
			break;
		case "cash":
			orders[0] = "prod_prize";
			break;
		case "buys":
			orders[0] = "prod_sell_sum";
			break;
		case "comments":
			orders[0] = "comment_count";
			break;
		default:
			orders[0] = "create_time";
			orders[1] = "desc";
			break;
		}

		List<SProductDTO> result = productDao.getproductByParamsSort(sortId, orders[0], orders[1],
				sCurentPageDTO.getCurPageNO() * sCurentPageDTO.getCurPageNum(), sCurentPageDTO.getCurPageNum());

		for (SProductDTO ss : result) {
			logger.info("SProductDTO :" + ss.getProdName());
		}

		int count = productDao.getCountProduct();

		int listTotal = count / sCurentPageDTO.getCurPageNum();
		if (count % sCurentPageDTO.getCurPageNum() != 0) {
			listTotal++;
		}

		List<SProductLevelDTO> returnList = new ArrayList<>();
		SProductLevelDTO sProductLevelDTO = null;
		List<String> imgUrls = null;

		if (!CollectionUtils.isEmpty(result)) {
			logger.info("******开始构造SProductLevelDTO循环*******");
			for (SProductDTO sProductDTO : result) {
				sProductLevelDTO = new SProductLevelDTO();
				imgUrls = new ArrayList<>();
				// 开始赋值
				this.wrapIntiDBCu(sProductLevelDTO, sProductDTO, sCurentPageDTO, listTotal);

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

	@Override
	public List<SProductLevelDTO> getMoreList(Integer more, SCurentPageDTO sCurentPageDTO) {
		
		logger.info("******调用SProductServiceImpl的getSortList方法*******");
		String[] orders = sCurentPageDTO.getOrders().split("[,，]");
		switch (orders[0]) {
		case "date":
			orders[0] = "create_time";
			break;
		case "cash":
			orders[0] = "prod_prize";
			break;
		case "buys":
			orders[0] = "prod_sell_sum";
			break;
		case "comments":
			orders[0] = "comment_count";
			break;
		default:
			orders[0] = "create_time";
			orders[1] = "desc";
			break;
		}

		List<SProductDTO> result = productDao.getproductByParamsMore(more, orders[0], orders[1],
				sCurentPageDTO.getCurPageNO() * sCurentPageDTO.getCurPageNum(), sCurentPageDTO.getCurPageNum());

		for (SProductDTO ss : result) {
			logger.info("SProductDTO :" + ss.getProdName());
		}

		int count = productDao.getCountProduct();

		int listTotal = count / sCurentPageDTO.getCurPageNum();
		if (count % sCurentPageDTO.getCurPageNum() != 0) {
			listTotal++;
		}

		List<SProductLevelDTO> returnList = new ArrayList<>();
		SProductLevelDTO sProductLevelDTO = null;
		List<String> imgUrls = null;

		if (!CollectionUtils.isEmpty(result)) {
			logger.info("******开始构造SProductLevelDTO循环*******");
			for (SProductDTO sProductDTO : result) {
				sProductLevelDTO = new SProductLevelDTO();
				imgUrls = new ArrayList<>();
				// 开始赋值
				this.wrapIntiDBCu(sProductLevelDTO, sProductDTO, sCurentPageDTO, listTotal);

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

	@Override
	public List<STalkShowDTO> getProductTalkList(String prodId) {
		
		List<STalkShowDTO> resultList = new ArrayList<>();
		List<STalkDTO> list = talkDao.getTalkListByprodId(prodId);
		STalkShowDTO dto = null;
		if(CollectionUtils.isEmpty(list)) {
			return resultList;
		}
		for(STalkDTO talkDTO : list) {
			dto = new STalkShowDTO();
			SUserDTO sUserDTO = userDao.selectAllByUid(talkDTO.getUserId());
			
			dto.setCreateTime(talkDTO.getCreateTime());
			dto.setId(talkDTO.getId());
			dto.setUserName(sUserDTO.getUserName());
			dto.setTalkMessage(talkDTO.getTalkMessage());
			
			resultList.add(dto);
		}
		
		return resultList;
	}

}
