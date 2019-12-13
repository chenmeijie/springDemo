package com.great.springdemo.controller;

import com.great.springdemo.annotion.MyController;
import com.great.springdemo.annotion.MyRequestMapping;
import com.great.springdemo.bean.Menu;
import com.great.springdemo.service.UserService;
import com.great.springdemo.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MyController
@MyRequestMapping("/LoginController")
public class LoginController
{

	@MyRequestMapping("/login.do")
	public void login(HttpServletRequest request, HttpServletResponse response,
	                  String title,String password)throws ServletException, IOException
	{
		password = MD5Utils.md5(password);
		System.out.println(title+",pwd:"+password);//title：用户名
		ServletContext servletContext = request.getSession().getServletContext();
		WebApplicationContext applicationContext = (WebApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		UserService userService = applicationContext.getBean("userService",UserService.class);
		String[] arr = new String[]{title,password};
		boolean login = userService.login(arr);
		if (login){
			System.out.println("登录成功");
		}else {
			System.out.println("登录失败");
		}

	}
}
