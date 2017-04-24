package com.mhdq.service.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.product.dto.MOrderInfoDTO;
import com.manager.product.dto.MOrderInfoShowDTO;
import com.mhdq.dao.manager.AddressDao;
import com.mhdq.dao.manager.OrderDao;
import com.mhdq.dao.manager.UserDao;
import com.mhdq.dao.manager.product.ProductDao;
import com.mhdq.rpc.RpcRespDTO;
import com.mhdq.service.manager.OrderInfoService;
import com.server.api.easyui.DataGrid;
import com.server.api.easyui.Page;
import com.server.dto.SAddressDTO;
import com.server.dto.SProductDTO;
import com.server.dto.SUserDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月23日  新建  
*/
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private AddressDao addressDao;
	
	@Override
	public DataGrid getOrderList(MOrderInfoShowDTO mOrderInfoShowDTO, Page page) {
		
		page.setParams(mOrderInfoShowDTO);
		List<MOrderInfoDTO> list = orderDao.getOrderList(page);
		List<MOrderInfoShowDTO> resultList = new ArrayList<>();
		MOrderInfoShowDTO mInfoShowDTO = null;
		
		if(! CollectionUtils.isEmpty(list)) {
			for(MOrderInfoDTO mm : list) {
				mInfoShowDTO = new MOrderInfoShowDTO();
				
				mInfoShowDTO.setId(mm.getId());
				mInfoShowDTO.setBuyCount(mm.getBuyCount());
				
				SProductDTO sproductDto = productDao.getProductByProdId(mm.getProdId());
				SUserDTO userDTO = userDao.selectAllByUid(mm.getUserId());
				SAddressDTO sAddressDTO = addressDao.selectById(mm.getAttachAddress());
				
				mInfoShowDTO.setCodeId(sproductDto.getCodeId());
				mInfoShowDTO.setCreateTime(mm.getCreateTime());
				mInfoShowDTO.setMoneySum(mm.getMoneySum());
				mInfoShowDTO.setProdId(mm.getProdId());
				mInfoShowDTO.setProdName(sproductDto.getProdName());
				mInfoShowDTO.setStatus(mm.getStatus());
				mInfoShowDTO.setUserId(mm.getUserId());
				mInfoShowDTO.setUserName(sAddressDTO.getUserName());
				mInfoShowDTO.setUserAddress(sAddressDTO.getUserAddress());
				mInfoShowDTO.setUserPhone(sAddressDTO.getUserPhone());
				
				resultList.add(mInfoShowDTO);
			}
		}
		return Page.getDataGrid(page, resultList, MOrderInfoShowDTO.class);
	}

	@Override
	public RpcRespDTO<Integer> changeOrderStatusOne(Long id) {
		RpcRespDTO<Integer> respDTO = new RpcRespDTO<>();
		int i = orderDao.updateStatusOneById(id);
		return respDTO.buildSuccessResp(i);
	}

	@Override
	public RpcRespDTO<Integer> changeOrderStatusThree(Long id) {
		RpcRespDTO<Integer> respDTO = new RpcRespDTO<>();
		int i = orderDao.updateStatusThreeById(id);
		return respDTO.buildSuccessResp(i);
	}


}
