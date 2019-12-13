package com.great.springdemo.test;

import com.great.springdemo.bean.Menu;
import com.great.springdemo.entity.Userinfo;
import com.great.springdemo.entity.dPart;
import com.great.springdemo.mapper.DpartMap;
import com.great.springdemo.mapper.UserMap;
import com.great.springdemo.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test1
{
	public static void main(String[] args)
	{
		String str = "";
//		findDpart();
//		findBean();
//		findU();
//		login();
//		findMenu();
	}

//	public static void findMenu(){
//		SqlSession session = MybatisUtils.getSession();
//		UserMap mapper = session.getMapper(UserMap.class);
//		List<Menu> list = mapper.findMenu();
////		System.out.println(map);
//	}

	public static void login(){
		SqlSession session = MybatisUtils.getSession();
		UserMap mapper = session.getMapper(UserMap.class);
		String[] arr = new String[]{"admin","e10adc3949ba59abbe56e057f20f883e"};
		boolean login = mapper.login(arr);
		System.out.println(login);
	}

	public static void findU(){
		SqlSession session = MybatisUtils.getSession();
		DpartMap mapper = session.getMapper(DpartMap.class);
		Map<Integer,Userinfo> map = mapper.findU(1);
		for (Map.Entry<Integer,Userinfo> entry : map.entrySet())
		{
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
	}

	public static void findBean(){
		SqlSession session = MybatisUtils.getSession();
		DpartMap mapper = session.getMapper(DpartMap.class);
		Userinfo bean = mapper.findBean(1);
		System.out.println(bean);
	}

	public static void findDpart(){
		SqlSession session = MybatisUtils.getSession();
		DpartMap mapper = session.getMapper(DpartMap.class);
		List<dPart> dParts = mapper.findDpart(0);
		for (dPart dPart : dParts)
		{
			System.out.println(dPart);
		}
		session.close();
	}
}
