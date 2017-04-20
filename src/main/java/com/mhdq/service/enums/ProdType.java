package com.mhdq.service.enums;

import org.apache.commons.lang3.StringUtils;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月3日  新建  
*/
public enum ProdType {
	

	STATUS_W(1, "电视"), STATUS_I(2, "冰箱"), STATUS_U(3, "空调"), STATUS_S(4, "洗衣机"), STATUS_F(5, "厨房用品"), STATUS_P(6, "浴室用品"), STATUS_MISSING(
			20, "其他");

	public static ProdType getByStatusCode(String code) {
		String trimCode = StringUtils.trim(code);
		for (ProdType status : ProdType.values()) {
			if (StringUtils.equals(trimCode, status.desc)) {
				return status;
			}
		}
		return STATUS_MISSING;
	}

	ProdType(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	// 状态代码
	public int code;
	// 状态描述
	public String desc;
	
	public static void main(String args[]) {
		System.out.println(ProdType.getByStatusCode("冰箱").code);
	}
}
