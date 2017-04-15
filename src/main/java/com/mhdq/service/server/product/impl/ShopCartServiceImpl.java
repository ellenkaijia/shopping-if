package com.mhdq.service.server.product.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mhdq.dao.manager.MarketCarDao;
import com.mhdq.dao.manager.product.ProductDao;
import com.mhdq.dao.manager.productres.ProductResDao;
import com.mhdq.service.server.product.ShopCartService;
import com.server.dto.SBandDTO;
import com.server.dto.SProductDTO;
import com.server.dto.SProductResDTO;
import com.server.dto.ShopCarShowDTO;
import com.server.dto.ShopCartDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月15日  新建  
*/
@Service
public class ShopCartServiceImpl implements ShopCartService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductResDao productResDao;
	
	@Autowired
	private MarketCarDao marketCarDao;
	
	@Override
	public List<ShopCarShowDTO> getShopCartList(String userId) {
		
		List<ShopCarShowDTO> allListResult = new ArrayList<>();
		ShopCarShowDTO one = null;
		List<ShopCartDTO> markets = marketCarDao.selectByUserId(userId);
		
		if(CollectionUtils.isEmpty(markets)) {
			return allListResult;
		}
		
		for(ShopCartDTO shop : markets) {
			one = new ShopCarShowDTO();
			
			SProductDTO sproductDto = productDao.getProductByProdId(shop.getProdId());
			
			this.wrapShopCartData(one, sproductDto);   //交换数据
			one.setProdCount(shop.getProdCount());  //数量
			
			SBandDTO sband = productDao.selectBandByBandId(sproductDto.getBandId());
			one.setBandName(sband.getBandName());
			
			List<SProductResDTO> resDTOs = productResDao.getSProdResByProdId(shop.getProdId());
			
			if(CollectionUtils.isEmpty(resDTOs)) {
				allListResult.add(one);
				continue;
			}
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("picResource" + File.separator + resDTOs.get(1).getResParentId()
					+ File.separator + resDTOs.get(1).getResId() + ".jpg");
			one.setImgUrl(stringBuffer.toString());
			allListResult.add(one);
		}
		
		return allListResult;
	}
	
	private void wrapShopCartData(ShopCarShowDTO showDTO, SProductDTO productDTO) {
		showDTO.setProdId(productDTO.getProdId());
		showDTO.setProdDetail(productDTO.getProdDetail());
		showDTO.setProdName(productDTO.getProdName());
		showDTO.setProdPrize(productDTO.getProdPrize());
		showDTO.setProdSum(productDTO.getProdSum());
		showDTO.setProdSellSum(productDTO.getProdSellSum());
	}

	@Override
	public boolean changeShopCartNum(String prodId, Integer num, String userId) {
		int i = marketCarDao.updateProdCountByUIdProdId(userId, prodId, num);
		if(i == 1) {
			return true;
		}
		return false;
	}

}
