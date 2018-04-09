package com.ssm.blog.service;

import org.springframework.stereotype.Service;

import com.ssm.blog.view.UserInfo;

@Service("UserInfoService")
public interface UserInfoService {
	//根据用户名和密码得到用户
	public UserInfo selectUser(String username, String password);
	
}
