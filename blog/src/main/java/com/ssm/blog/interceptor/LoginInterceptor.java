package com.ssm.blog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.blog.view.UserInfo;

public class LoginInterceptor implements HandlerInterceptor{
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 获取请求的url
		String szURI = request.getRequestURI();
		// 如果是登录，就放行
		if (szURI.indexOf("login")>=0 || szURI.indexOf("portal")>=0) {
			return true;
		}
		
		// 判断session
		HttpSession session  = request.getSession();
		// 从session中取出用户身份信息
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		// session存在时，放行
		if (userInfo!=null) {
			return true;
		}
		
		// 执行这里表示用户身份需要认证，跳转登陆页面
		request.getRequestDispatcher("/WEB-INF/page/admin/login.jsp").forward(request, response);
		
		return false;

	}


	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

}
