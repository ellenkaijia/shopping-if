package com.mhdq.service.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.product.dto.MAddressInfoDTO;
import com.manager.product.dto.UserInfoDTO;
import com.mhdq.dao.manager.product.MUserDao;
import com.mhdq.service.manager.UserInfoService;
import com.server.api.easyui.DataGrid;
import com.server.api.easyui.Page;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月21日  新建  
*/
@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private MUserDao muserDao;
	
	@Override
	public DataGrid dataGrid(UserInfoDTO userInfoDTO, Page page) {
		page.setParams(userInfoDTO);
		List<UserInfoDTO> list = muserDao.dataGrid(page);
		return Page.getDataGrid(page, list, UserInfoDTO.class);
	}

	@Override
	public DataGrid addressList(MAddressInfoDTO maddressInfoDTO, Page page) {
		page.setParams(maddressInfoDTO);
		List<MAddressInfoDTO> list = muserDao.selectAddressList(page);
		return Page.getDataGrid(page, list, MAddressInfoDTO.class);
	}

}
