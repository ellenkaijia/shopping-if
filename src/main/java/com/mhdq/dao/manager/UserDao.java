package com.mhdq.dao.manager;

import org.apache.ibatis.annotations.Param;

import com.server.dto.SUserDTO;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年4月14日  新建  
*/
public interface UserDao {

	int insertUser(SUserDTO sUserDTO);
	
	SUserDTO selectByPhone(String phone);
	
	SUserDTO selectByPhonePwd(SUserDTO sUserDTO);
	
	SUserDTO selectUserId(SUserDTO sUserDTO);
	
	SUserDTO selectAllByUid(@Param("userId") String userId);
}
