package com.mhdq.servicems.manager;

import org.springframework.beans.factory.annotation.Autowired;

import com.manager.product.dto.MAddressInfoDTO;
import com.manager.product.dto.UserInfoDTO;
import com.mhdq.manager.api.service.UserMsService;
import com.mhdq.service.manager.UserInfoService;
import com.server.api.easyui.DataGrid;
import com.server.api.easyui.Page;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月21日  新建  
*/
public class UserMsServiceImpl implements UserMsService{

	@Autowired
	private UserInfoService userInfoService;
	
	@Override
	public DataGrid dataGrid(UserInfoDTO userInfoDTO, Page page) {
		return userInfoService.dataGrid(userInfoDTO, page);
	}

	@Override
	public DataGrid addressList(MAddressInfoDTO maddressInfoDTO, Page page) {
		return userInfoService.addressList(maddressInfoDTO, page);
	}

}
