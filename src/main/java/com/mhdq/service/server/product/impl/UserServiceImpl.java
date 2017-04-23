package com.mhdq.service.server.product.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mhdq.dao.manager.AddressDao;
import com.mhdq.dao.manager.FavorDao;
import com.mhdq.dao.manager.MarketCarDao;
import com.mhdq.dao.manager.OrderDao;
import com.mhdq.dao.manager.RepairDao;
import com.mhdq.dao.manager.SmsLogDao;
import com.mhdq.dao.manager.TalkDao;
import com.mhdq.dao.manager.UserDao;
import com.mhdq.dao.manager.product.ProductDao;
import com.mhdq.dao.manager.productres.ProductResDao;
import com.mhdq.rpc.RpcCommonConstant;
import com.mhdq.rpc.RpcRespDTO;
import com.mhdq.service.server.product.UserService;
import com.mhdq.sms.SmsLogDTO;
import com.mhdq.sms.SmsService;
import com.server.api.util.GenerateCode;
import com.server.dto.SAddressDTO;
import com.server.dto.SFavorDTO;
import com.server.dto.SOrderDTO;
import com.server.dto.SProductDTO;
import com.server.dto.SProductLevelDTO;
import com.server.dto.SProductResDTO;
import com.server.dto.SRepairDTO;
import com.server.dto.STalkDTO;
import com.server.dto.SUserDTO;
import com.server.dto.SUserOrderShowDTO;
import com.server.dto.ShopCartDTO;

/**
 * 类说明
 * 
 * @author zkj
 * @date 2017年4月14日 新建
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

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private RepairDao repairDao;
	
	@Autowired
	private TalkDao talkDao;

	@Override
	public boolean checkPhone(String phone) {
		SUserDTO userDTO = userDao.selectByPhone(phone);
		if (userDTO == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean sendSms(String phone) {
		RpcRespDTO<String> rpcRespDTO = smsService.sendSMS(phone);
		if (RpcCommonConstant.CODE_SUCCESS.equals(rpcRespDTO.getCode())) {
			return true;
		}
		return false;
	}

	@Override
	public Integer checkPassword(SUserDTO sUserDTO) {
		log.info("*****checkPassword userPassword={},checkCode={}******", sUserDTO.getUserPassword(),
				sUserDTO.getCheckCode());
		if (sUserDTO.getUserPassword() == null) {
			SmsLogDTO smsLogDto = smsLogDao.getRecentMsgCode(sUserDTO.getUserPhone());
			if (smsLogDto == null || !smsLogDto.getMsgCode().equals(sUserDTO.getCheckCode())) {
				return -99;
			}
		} else {
			SUserDTO userDTO = userDao.selectByPhonePwd(sUserDTO);
			if (userDTO == null) {
				return -98;
			}
		}
		return 0;
	}

	@Override
	public Integer registerGo(SUserDTO sUserDTO) {
		SmsLogDTO smsLogDto = smsLogDao.getRecentMsgCode(sUserDTO.getUserPhone());
		if (smsLogDto == null || !smsLogDto.getMsgCode().equals(sUserDTO.getCheckCode())) {
			return 1;
		}
		String userId = GenerateCode.generateUserIdCode();
		sUserDTO.setUserId(userId);
		int i = userDao.insertUser(sUserDTO);
		if (i == 1) {
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
		if (shop != null) {
			return -99; // 数据已经存在
		}
		int i = marketCarDao.insertMarketCar(shopCartDTO);
		if (i == 1) {
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
		if (sFavorDTO == null) {
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
		if (i == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean removeInterest(String userId, String prodId) {
		int i = favorDao.deleteFavorByUIdProdId(userId, prodId);
		if (i == 1) {
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
		if (i == 1) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, String> tradeIntoOrder(String userId, String prodId, Integer buyCount, BigDecimal moneySum,Long addressId) {
		Map<String, String> map = new HashMap<>();
		SOrderDTO sOrderDTO = new SOrderDTO();
		sOrderDTO.setBuyCount(buyCount);
		sOrderDTO.setMoneySum(moneySum);
		sOrderDTO.setProdId(prodId);
		sOrderDTO.setStatus(0);
		sOrderDTO.setTalkStatus(2);
		sOrderDTO.setUserId(userId);
		sOrderDTO.setAttachAddress(addressId);

		String orderId = GenerateCode.generateOrderNo(userId);
		sOrderDTO.setOrderId(orderId);

		int i = orderDao.insertOrder(sOrderDTO);
		
		int j = productDao.updateSellNumAndNum(prodId, buyCount, buyCount);
		
		if (i == 1 && j == 1) {
			map.put("code", "0");
			map.put("orderId", orderId);
		} else {
			map.put("code", "-99");
			map.put("orderId", "失败");
		}
		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, String> shopCartradeOrder(String userId, String prodIds,Long addressId) throws Exception {
		Map<String, String> map = new HashMap<>();
		SOrderDTO sOrderDTO = null;

		int Count = 0;

		String orderId = GenerateCode.generateOrderNo(userId);

		String prods[] = prodIds.split("[,，]");

		for (String prodId : prods) {
			ShopCartDTO shopCartDTO = new ShopCartDTO();
			shopCartDTO.setUserId(userId);
			shopCartDTO.setProdId(prodId);

			ShopCartDTO resultShop = marketCarDao.selectByUIdProdId(shopCartDTO);
			SProductDTO ss = productDao.getProductByProdId(prodId);

			sOrderDTO = new SOrderDTO();
			sOrderDTO.setBuyCount(resultShop.getProdCount());
			sOrderDTO.setMoneySum(ss.getProdPrize().multiply(new BigDecimal(resultShop.getProdCount())));
			sOrderDTO.setProdId(prodId);
			sOrderDTO.setStatus(0);
			sOrderDTO.setTalkStatus(2);
			sOrderDTO.setUserId(userId);
			sOrderDTO.setOrderId(orderId);
			sOrderDTO.setAttachAddress(addressId);

			int i = orderDao.insertOrder(sOrderDTO);
			
			int j = productDao.updateSellNumAndNum(prodId, resultShop.getProdCount(), resultShop.getProdCount());
			
			if (i == 1 && j == 1) {
				marketCarDao.deleteProdByUIdProdId(userId, prodId);
				Count++;
			}
		}

		if (Count == prods.length) {
			marketCarDao.deleteProdByUId(userId);
			map.put("code", "0");
			map.put("orderId", orderId);
		} else {
			map.put("code", "-99");
			map.put("orderId", "失败");
		}

		return map;
	}

	@Override
	public List<SUserOrderShowDTO> getUserOrder(String userId, Integer status) {
		List<SOrderDTO> orderList = new ArrayList<>();
		List<SUserOrderShowDTO> resultList = new ArrayList<>();
		if (status == 0) {
			status = null;
			orderList = orderDao.selectByStatusUId(status, userId);
		} else if (status == 1) {
			status = 0;
			orderList = orderDao.selectByStatusUId(status, userId);
		} else if (status == 2) {
			status = 0;
			orderList = orderDao.selectByStatusUId(status, userId);
		} else if (status == 3) {
			status = 1;
			orderList = orderDao.selectByStatusUId(status, userId);
		} else if (status == 4) {
			status = 3;
			orderList = orderDao.selectByStatusUId(status, userId);
		}

		if (CollectionUtils.isEmpty(orderList)) {
			return null;
		}

		for (SOrderDTO sOrderDTO : orderList) {
			SUserOrderShowDTO sUserOrderShowDTO = new SUserOrderShowDTO();

			sUserOrderShowDTO.setCreateTime(sOrderDTO.getCreateTime());
			sUserOrderShowDTO.setOrderId(sOrderDTO.getOrderId());
			sUserOrderShowDTO.setProdId(sOrderDTO.getProdId());
			sUserOrderShowDTO.setProdPrizeSum(sOrderDTO.getMoneySum());
			sUserOrderShowDTO.setStatus(sOrderDTO.getStatus());
			sUserOrderShowDTO.setTalkStatus(sOrderDTO.getTalkStatus());
			
			SProductDTO sProductDTO = productDao.getProductByProdId(sOrderDTO.getProdId());
			sUserOrderShowDTO.setProdName(sProductDTO.getProdName());
			sUserOrderShowDTO.setProdPrize(sProductDTO.getProdPrize());
			
			List<SProductResDTO> resDTOs = productResDao.getSProdResByProdId(sOrderDTO.getProdId());
			
			if(CollectionUtils.isEmpty(resDTOs)) {
				resultList.add(sUserOrderShowDTO);
				continue;
			}
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("picResource" + File.separator + resDTOs.get(1).getResParentId()
					+ File.separator + resDTOs.get(1).getResId() + ".jpg");
			sUserOrderShowDTO.setImgUrl(stringBuffer.toString());

			resultList.add(sUserOrderShowDTO);
		}

		return resultList;
	}

	@Override
	public List<SProductLevelDTO> getOrderByUidPid(String userId, String prodId, Integer prodCount) {
		log.info("******开始getOrderByUidPid方法*******");
		List<SProductLevelDTO> returnList = new ArrayList<>();
		SProductLevelDTO sProductLevelDTO = null;
		List<String> imgUrls = null;
		String prodIds[] = prodId.split("[,，]");
		
		if(prodIds.length == 1  && prodCount != null) {
			
			SProductDTO sProductDTO = productDao.getProductByProdId(prodIds[0]);
			if(sProductDTO != null) {
				log.info("******开始构造SProductLevelDTO循环*******");
				sProductLevelDTO = new SProductLevelDTO();
				imgUrls = new ArrayList<>();
				// 开始赋值
				this.wrapIntiDB(sProductLevelDTO, sProductDTO);
				sProductLevelDTO.setBuyCount(prodCount);
				
				List<SProductResDTO> resList = productResDao.getSProdResByProdId(sProductDTO.getProdId());
				if (CollectionUtils.isEmpty(resList)) {
					returnList.add(sProductLevelDTO);
					log.info("******没有找到对应的图片资源******");
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
			
		} else {
			for (String ppId : prodIds) {
				ShopCartDTO shopCartDTO = marketCarDao.selectByUIdProdIdTwo(userId, ppId);
				
				if (shopCartDTO != null) {
					log.info("******开始构造SProductLevelDTO循环*******");
					sProductLevelDTO = new SProductLevelDTO();
					imgUrls = new ArrayList<>();
					
					SProductDTO sProductDTO = productDao.getProductByProdId(shopCartDTO.getProdId());
					// 开始赋值
					this.wrapIntiDB(sProductLevelDTO, sProductDTO);
					sProductLevelDTO.setBuyCount(shopCartDTO.getProdCount());
					
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
		}


		return returnList;
	}

	@Override
	public SAddressDTO getAddressStatusOne(String userId) {
		return addressDao.selectByUidStatusOne(userId);
	}

	@Override
	public boolean isHaveAddress(String userId) {
		SAddressDTO addressDTO = addressDao.selectByUidStatusOne(userId);
		if(addressDTO == null) {
			return false;
		}
		return true;
	}

	@Override
	public Integer addAddress(String userId, SAddressDTO sAddressDTO) {
		
		SAddressDTO addressDTO = addressDao.selectByUidStatusOne(userId);
		if(addressDTO == null) {
			sAddressDTO.setStatus(1);
		} else {
			sAddressDTO.setStatus(0);
		}
		sAddressDTO.setUserId(userId);
		int i = addressDao.insertSAddress(sAddressDTO);
		if(i == 1) {
			return 0;
		} else {
			return -99;
		}
		
	}

	@Override
	public List<SAddressDTO> getAddressList(String userId) {
		return addressDao.selectByUserId(userId);
	}

	@Override
	public Integer deleteAddress(Long id) {
		int i = addressDao.deleteAddressById(id);
		if(i == 1) {
			return 0;
		}
		return -99;
	}

	@Override
	public Integer updateAddressStatus(String userId, Integer id) {
		
		addressDao.updateAddressStatusZeroByUserId(userId);
		
		int i = addressDao.updateAddressStatusOneById(id);
		if(i == 1) {
			return 0;
		}
		return -99;
	}

	@Override
	public SAddressDTO geAddressDTOByOrderId(String userId, String orderId) {
		List<SOrderDTO> list = orderDao.selectByOrderId(orderId);
		if(! CollectionUtils.isEmpty(list)) {
			SAddressDTO addressDTO = addressDao.selectById(list.get(0).getAttachAddress());
			return addressDTO;
		}
		return null;
	}

	@Override
	public List<SProductLevelDTO> getProductByOrderId(String orderId) {
		
		List<SOrderDTO> sorderDTO = orderDao.selectByOrderId(orderId);
		
		List<SProductLevelDTO> returnList = new ArrayList<>();
		SProductLevelDTO sProductLevelDTO = null;
		List<String> imgUrls = null;
		
		if (!CollectionUtils.isEmpty(sorderDTO)) {
			log.info("******开始构造SProductLevelDTO循环*******");
			for (SOrderDTO ssrderDTO : sorderDTO) {
				sProductLevelDTO = new SProductLevelDTO();
				imgUrls = new ArrayList<>();

				SProductDTO sProductDTO = productDao.getProductByProdId(ssrderDTO.getProdId());
				// 开始赋值
				this.wrapIntiDB(sProductLevelDTO, sProductDTO);
				sProductLevelDTO.setBuyCount(ssrderDTO.getBuyCount());

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

	@Override
	public Integer addRepair(SRepairDTO sRepairDTO) {
		String repairId = GenerateCode.generateRepairNo();
		sRepairDTO.setRepairId(repairId);
		sRepairDTO.setStatus(0);
		int i = repairDao.insert(sRepairDTO);
		if(i == 1) {
			return 0;
		}
		return -99;
	}

	@Override
	public Integer addTalk(STalkDTO stalkDTO) {
		
		int i = talkDao.insert(stalkDTO);
		if(i == 1) {
			int j = orderDao.updateTalkStatusByOrderIdProdId(stalkDTO.getOrderId(), stalkDTO.getProdId());
			if(j == 1) {
				return 0;
			} else {
				return -98;
			}
		}
		return -99;
	}

	@Override
	public List<SRepairDTO> getMyRepair(String userId) {
		return repairDao.getRepairList(userId);
	}

	@Override
	public Integer addNoBuyRepair(SRepairDTO sRepairDTO) {
		String repairId = GenerateCode.generateRepairNo();
		sRepairDTO.setRepairId(repairId);
		sRepairDTO.setStatus(0);
		int i = repairDao.insert(sRepairDTO);
		if(i == 1) {
			return 0;
		}
		return -99;
	}

	@Override
	public Integer getProductTalkCount(String prodId) {
		return talkDao.getCountByProdId(prodId);
	}

}
