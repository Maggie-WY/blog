package com.ssm.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.blog.mapper.IUserInfo;
import com.ssm.blog.service.UserInfoService;
import com.ssm.blog.view.UserInfo;

@Service("UserInfoServiceImpl")
public class UserInfoServiceImpl implements UserInfoService{
	
	@Autowired
	private IUserInfo iUserInfo;

	/**
	 * 根据用户名和密码得到用户 校验用户登录 	
	 * @param username 登录名
	 * @param password 登录密码
	 * @return
	 */
	public UserInfo selectUser(String username, String password) {
		return iUserInfo.selectUser(username, password);
	}

}
