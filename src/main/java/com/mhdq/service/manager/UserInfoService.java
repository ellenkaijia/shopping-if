package com.mhdq.service.manager;

import com.manager.product.dto.UserInfoDTO;
import com.server.api.easyui.DataGrid;
import com.server.api.easyui.Page;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月21日  新建  
*/
public interface UserInfoService {

	DataGrid dataGrid(UserInfoDTO userInfoDTO, Page page);
}
