package com.mhdq.service.server.product.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mhdq.dao.manager.FavorDao;
import com.mhdq.dao.manager.MarketCarDao;
import com.mhdq.dao.manager.SmsLogDao;
import com.mhdq.dao.manager.UserDao;
import com.mhdq.rpc.RpcCommonConstant;
import com.mhdq.rpc.RpcRespDTO;
import com.mhdq.service.server.product.UserService;
import com.mhdq.sms.SmsLogDTO;
import com.mhdq.sms.SmsService;
import com.server.api.util.GenerateCode;
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

}
