package com.mhdq.service.manager;

import com.manager.product.dto.MOrderInfoShowDTO;
import com.mhdq.rpc.RpcRespDTO;
import com.server.api.easyui.DataGrid;
import com.server.api.easyui.Page;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月23日  新建  
*/
public interface OrderInfoService {

	 DataGrid getOrderList(MOrderInfoShowDTO mOrderInfoShowDTO, Page page);
	 
	 RpcRespDTO<Integer> changeOrderStatusOne(Long id);
	 
	 RpcRespDTO<Integer> changeOrderStatusThree(Long id);
}
