package com.ssm.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.blog.exception.CustomException;
import com.ssm.blog.mapper.UserInfoMapper;
import com.ssm.blog.service.UserInfoService;
import com.ssm.blog.view.Result;
import com.ssm.blog.view.UserInfo;

/**
 * 
 * @author 丁杰
 *
 */
@Controller
@RequestMapping("/admin")
public class UserInfoController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	UserInfoService userInfoService;
	
	/**
	 * 转发
	 * @return
	 */
	@RequestMapping("/index.action")
	public String index() {
		return "admin/index";
	}
	
	/**
	 * 跳转至登录页面
	 * @return 
	 */
	@RequestMapping("/login.action")
	public String login() {
		return "admin/login";
	}
	
	/**
	 * 注销登录
	 * @param session
	 * @return
	 */
	@RequestMapping("/loginout.action")
	public String loginout(HttpSession session) {
		if(session!=null) {
			session.invalidate();
		}
		return "admin/login";
	}
	
	/**
	 * 用户登录
	 * @param map
	 * @param request
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("/login.json")
	@ResponseBody
	public Result login2(ModelMap map,HttpServletRequest request) throws CustomException {	
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)) {
			throw new CustomException("用户名或密码不能为空！");
		}
		UserInfo userInfo = userInfoService.selectUser(username, password);
		if(userInfo==null) {
			throw new CustomException("请用户名或密码是否输入正确！");
		}
		request.getSession().setAttribute("userInfo", userInfo);
		
		Result rs = new Result("1","登录成功");
		return rs;
	}
}
