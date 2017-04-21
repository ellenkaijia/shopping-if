package com.mhdq.dao.manager.product;

import java.util.List;

import com.manager.product.dto.UserInfoDTO;
import com.server.api.easyui.Page;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月21日  新建  
*/
public interface MUserDao {

	List<UserInfoDTO> dataGrid(Page page);
}
