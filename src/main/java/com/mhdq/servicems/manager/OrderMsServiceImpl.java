package com.mhdq.servicems.manager;

import org.springframework.beans.factory.annotation.Autowired;

import com.manager.product.dto.MOrderInfoShowDTO;
import com.mhdq.manager.api.service.OrderMsService;
import com.mhdq.rpc.RpcRespDTO;
import com.mhdq.service.manager.OrderInfoService;
import com.server.api.easyui.DataGrid;
import com.server.api.easyui.Page;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月23日  新建  
*/
public class OrderMsServiceImpl implements OrderMsService {

	@Autowired
	private OrderInfoService orderInfoService;
	
	@Override
	public DataGrid getOrderList(MOrderInfoShowDTO mOrderInfoShowDTO, Page page) {
		return orderInfoService.getOrderList(mOrderInfoShowDTO, page);
	}

	@Override
	public RpcRespDTO<Integer> changeOrderStatusOne(Long id) {
		return orderInfoService.changeOrderStatusOne(id);
	}

	@Override
	public RpcRespDTO<Integer> changeOrderStatusThree(Long id) {
		return orderInfoService.changeOrderStatusThree(id);
	}

}
