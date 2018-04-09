package com.ssm.blog.mapper;

import org.apache.ibatis.annotations.Param;
import com.ssm.blog.view.UserInfo;

/**
 * 
 * @author 丁杰
 *
 */
public interface  IUserInfo {
	
	/**
	 * 根据用户名和密码得到用户 校验用户登录 	
	 * @param loginName 登录名
	 * @param passWord 登录密码
	 * @return
	 */
	public UserInfo selectUser(@Param("userName") String userName, @Param("passWord") String passWord);


}
