package com.mhdq.dao.manager;

import java.util.List;

/**  
* 类说明   
*  
* @author zkj  
* @date 2017年3月28日  新建  
*/
public interface BaseDao<T> {
	
	T selectByPrimaryKey(Long id);
	
	int insert(T t);
	
	int insertByBatch(List<T> list);
	
	int update(T t);
	
	int delete(T t);

}
