package com.mhdq.service.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mhdq.manager.api.service.LoginMsService;

public class LoginMsServiceImpl implements LoginMsService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public void sayHello(String name) {
		logger.info("我是LoginMsServiceImpl" + name);
	}

}
