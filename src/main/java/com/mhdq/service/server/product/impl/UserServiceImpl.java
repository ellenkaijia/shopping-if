package com.mhdq.service.server.product.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mhdq.dao.manager.FavorDao;
import com.mhdq.dao.manager.MarketCarDao;
import com.mhdq.dao.manager.SmsLogDao;
import com.mhdq.dao.manager.UserDao;
import com.mhdq.dao.manager.product.ProductDao;
import com.mhdq.dao.manager.productres.ProductResDao;
import com.mhdq.rpc.RpcCommonConstant;
import com.mhdq.rpc.RpcRespDTO;
import com.mhdq.service.server.product.UserService;
import com.mhdq.sms.SmsLogDTO;
import com.mhdq.sms.SmsService;
import com.server.api.util.GenerateCode;
import com.server.dto.SFavorDTO;
import com.server.dto.SProductDTO;
import com.server.dto.SProductLevelDTO;
import com.server.dto.SProductResDTO;
import com.server.dto.SUserDTO;
import com.server.dto.ShopCartDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月14日  新建  
*/
@Service
public class UserServiceImpl implements UserService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private SmsLogDao smsLogDao;
	
	@Autowired
	private MarketCarDao marketCarDao;
	
	@Autowired
	private FavorDao favorDao;
	
	@Autowired
	private ProductResDao productResDao;
	
	@Autowired
	private ProductDao productDao;

	@Override
	public boolean checkPhone(String phone) {
		SUserDTO userDTO = userDao.selectByPhone(phone);
		if(userDTO == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean sendSms(String phone) {
		RpcRespDTO<String> rpcRespDTO = smsService.sendSMS(phone);
		if(RpcCommonConstant.CODE_SUCCESS.equals(rpcRespDTO.getCode())) {
			return true;
		}
		return false;
	}

	@Override
	public Integer checkPassword(SUserDTO sUserDTO) {
		log.info("*****checkPassword userPassword={},checkCode={}******",sUserDTO.getUserPassword(),sUserDTO.getCheckCode());
		if(sUserDTO.getUserPassword() == null) {
			SmsLogDTO smsLogDto = smsLogDao.getRecentMsgCode(sUserDTO.getUserPhone());
			if(smsLogDto == null || !smsLogDto.getMsgCode().equals(sUserDTO.getCheckCode())) {
				return -99; 
			}
		} else {
			SUserDTO userDTO = userDao.selectByPhonePwd(sUserDTO);
			if(userDTO == null) {
				return -98;
			}
		}
		return 0;
	}

	@Override
	public Integer registerGo(SUserDTO sUserDTO) {
		SmsLogDTO smsLogDto = smsLogDao.getRecentMsgCode(sUserDTO.getUserPhone());
		if(smsLogDto == null || !smsLogDto.getMsgCode().equals(sUserDTO.getCheckCode())) {
			return 1; 
		}
		String userId = GenerateCode.generateUserIdCode();
		sUserDTO.setUserId(userId);
		int i = userDao.insertUser(sUserDTO);
		if(i == 1) {
			return 0;
		}
		return 2;
	}

	@Override
	public String queryUserId(SUserDTO sUserDTO) {
		return userDao.selectUserId(sUserDTO).getUserId();
	}

	@Override
	public Integer addShopCart(ShopCartDTO shopCartDTO) {
		
		ShopCartDTO shop = marketCarDao.selectByUIdProdId(shopCartDTO);
		if(shop != null) {
			return -99;   //数据已经存在
		}
		int i = marketCarDao.insertMarketCar(shopCartDTO);
		if(i == 1) {
			return 0;
		}
		return -98;
	}

	@Override
	public Integer getMyFavorCount(String userId) {
		return favorDao.selectCountByUId(userId);
	}

	@Override
	public SUserDTO getUserAllByUid(String userId) {
		return userDao.selectAllByUid(userId);
	}

	@Override
	public Integer getMyShopCartCount(String userId) {
		return marketCarDao.getMyShopCartCountByUId(userId);
	}

	@Override
	public Integer isFavorByUIdProdId(String userId, String prodId) {
		SFavorDTO sFavorDTO = favorDao.selectByUIdProdId(userId, prodId);
		if(sFavorDTO == null) {
			return -99;
		}
		return 0;
	}

	@Override
	public boolean addInterest(String userId, String prodId) {
		SFavorDTO sFavorDTO = new SFavorDTO();
		sFavorDTO.setProdId(prodId);
		sFavorDTO.setUserId(userId);
		int i = favorDao.insertFavor(sFavorDTO);
		if(i == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean removeInterest(String userId, String prodId) {
		int i = favorDao.deleteFavorByUIdProdId(userId, prodId);
		if(i == 1) {
			return true;
		}
		return false;
	}

	@Override
	public List<SProductLevelDTO> getMyCollectionList(String userId) {
		
		log.info("******开始getMyCollectionList方法*******");
		
		List<SFavorDTO> favorDTOs = favorDao.selectByUserId(userId);

		List<SProductLevelDTO> returnList = new ArrayList<>();
		SProductLevelDTO sProductLevelDTO = null;
		List<String> imgUrls = null;

		if (!CollectionUtils.isEmpty(favorDTOs)) {
			log.info("******开始构造SProductLevelDTO循环*******");
			for (SFavorDTO sfavorDTO : favorDTOs) {
				sProductLevelDTO = new SProductLevelDTO();
				imgUrls = new ArrayList<>();
				
				SProductDTO sProductDTO = productDao.getProductByProdId(sfavorDTO.getProdId());
				// 开始赋值
				this.wrapIntiDB(sProductLevelDTO, sProductDTO);

				List<SProductResDTO> resList = productResDao.getSProdResByProdId(sProductDTO.getProdId());
				if (CollectionUtils.isEmpty(resList)) {
					returnList.add(sProductLevelDTO);
					log.info("******没有找到对应的图片资源******");
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
	public boolean removeCollection(String userId, String prodId) {
		int i = favorDao.deleteFavorByUIdProdId(userId, prodId);
		if(i == 1) {
			return true;
		}
		return false;
	}

}
