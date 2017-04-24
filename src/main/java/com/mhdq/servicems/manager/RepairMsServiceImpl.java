package com.mhdq.servicems.manager;

import org.springframework.beans.factory.annotation.Autowired;

import com.manager.product.dto.MRepairInfoDTO;
import com.manager.product.dto.MRepairInfoShowDTO;
import com.mhdq.manager.api.service.RepairMsService;
import com.mhdq.rpc.RpcRespDTO;
import com.mhdq.service.manager.RepairInfoService;
import com.server.api.easyui.DataGrid;
import com.server.api.easyui.Page;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月24日  新建  
*/
public class RepairMsServiceImpl implements RepairMsService{

	@Autowired
	private RepairInfoService repairInfoService;
	
	@Override
	public DataGrid getRepairList(MRepairInfoDTO mrepairInfoDTO, Page page) {
		return repairInfoService.getRepairList(mrepairInfoDTO, page);
	}

	@Override
	public RpcRespDTO<Integer> changeRepairStatusOne(Long id) {
		return repairInfoService.changeRepairStatusOne(id);
	}

}
