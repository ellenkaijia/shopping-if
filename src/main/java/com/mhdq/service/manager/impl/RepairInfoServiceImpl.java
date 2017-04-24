package com.mhdq.service.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.product.dto.MRepairInfoDTO;
import com.manager.product.dto.MRepairInfoShowDTO;
import com.mhdq.dao.manager.RepairDao;
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
@Service
public class RepairInfoServiceImpl implements RepairInfoService {

	@Autowired
	private RepairDao repairDao;
	
	@Override
	public DataGrid getRepairList(MRepairInfoDTO mrepairInfoDTO, Page page) {
		page.setParams(mrepairInfoDTO);
		List<MRepairInfoDTO> list = repairDao.getManagerRepair(page);
		return Page.getDataGrid(page, list, MRepairInfoDTO.class);
	}

	@Override
	public RpcRespDTO<Integer> changeRepairStatusOne(Long id) {
		RpcRespDTO<Integer> respDTO = new RpcRespDTO<>();
		int i = repairDao.updateStatusToOne(id);
		return respDTO.buildSuccessResp(i);
	}

}
