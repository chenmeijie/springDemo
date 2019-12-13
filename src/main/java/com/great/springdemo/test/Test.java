package com.great.springdemo.test;

import com.great.springdemo.entity.dPart;
import com.great.springdemo.mapper.DpartMap;
import com.great.springdemo.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Test
{
	public static void main(String[] args)
	{
		System.out.println("aaa");
//		SqlSession sqlSession = MybatisUtils.getSession();
//		List<dPart> list = sqlSession.selectList("dPartMap.findAll");
//		System.out.println(list);
//		dpart.setdName("学生部改");
//		sqlSession.update("dPartMap.updateDpart",dpart);
//		sqlSession.commit();
//		dPart dPart = new dPart();
//		dPart.setdName("学生部");
//		sqlSession.insert("dPartMap.insertDpart",dPart);
//		sqlSession.commit();
//		dPart dpart = sqlSession.selectOne("dPartMap.findByDno", 1);
//		System.out.println(dpart.toString());
//		sqlSession.delete("dPartMap.deleteDpart",dpart);
//		sqlSession.commit();
//		List<dPart> list2 = sqlSession.selectList("dPartMap.queryAll",dPart.class);
//		System.out.println(list2);
//		sqlSession.close();
//		mapTest1();
//		mapTest();
//		interfaceTest();
//		TestClass testClass = new TestClass();
//		testClass.aa();
//		dynamicSqlTest();
//		insertDpartDyn();
		insertDpartDyn2();
//		updateDynDpart();
//		findByDnoDyn();
//		findCount();
	}



	public static void findCount(){
		SqlSession session = MybatisUtils.getSession();
		DpartMap mapper = session.getMapper(DpartMap.class);
		Integer count = mapper.findCount();
		System.out.println(count);
	}

	public static void  findByDnoDyn(){
		SqlSession session = MybatisUtils.getSession();
		DpartMap mapper = session.getMapper(DpartMap.class);
		int [] arrs = new int[]{1,2,3};
		List<dPart> byDnoDyn = mapper.findByDnoDyn(arrs);
		System.out.println(byDnoDyn);
	}

	public static void updateDynDpart(){
		SqlSession session = MybatisUtils.getSession();
		DpartMap mapper = session.getMapper(DpartMap.class);
		dPart dpart = mapper.findByDno(9);
		dpart.setdName("测试修改");
		mapper.updateDynDpart(dpart);
		List<dPart> all = mapper.findAll();
		System.out.println(all);
		session.commit();
		session.close();
	}

	public static void insertDpartDyn2(){
		SqlSession session = MybatisUtils.getSession();
		DpartMap mapper = session.getMapper(DpartMap.class);
		dPart dPart = new dPart("测试100","100");
		dPart dPart2 = new dPart("测试200","200");
		dPart[]dParts = new dPart[2];
		dParts[0]=dPart;
		dParts[1]=dPart2;
		System.out.println(dParts.length);
		mapper.insertDpartDyn2(dParts);
		List<dPart> all = mapper.findAll();
		System.out.println(all);
		session.commit();
		session.close();
	}

	public static void insertDpartDyn(){
		SqlSession session = MybatisUtils.getSession();
		DpartMap mapper = session.getMapper(DpartMap.class);
		dPart dPart = new dPart("测试100","100");
		dPart dPart2 = new dPart("测试200","200");
		dPart dPart3 = new dPart("测试300","300");
		List<dPart> list = new ArrayList<>();
		list.add(dPart);
		list.add(dPart2);
		list.add(dPart3);
		mapper.insertDpartDyn(list);
		System.out.println(list.size());
		List<dPart> all = mapper.findAll();
		System.out.println(all);
		session.commit();
		session.close();
	}

	public static  void dynamicSqlTest(){
		SqlSession session = MybatisUtils.getSession();
		DpartMap mapper = session.getMapper(DpartMap.class);
		dPart dPart = new dPart();
		dPart.setDno(1);
		dPart.setdName("测");
		List<dPart> byDpart = mapper.findByDpart(dPart);
		System.out.println(byDpart);
	}

	public static void interfaceTest(){
		SqlSession sqlSession = MybatisUtils.getSession();
		DpartMap mapper = sqlSession.getMapper(DpartMap.class);
//		Object byDno = mapper.findByDno(1);
		List<dPart> all = mapper.findAll();
		System.out.println(all);
	}

	public static void mapTest1(){
		SqlSession sqlSession = MybatisUtils.getSession();
		Map<Object, Object> map = sqlSession.selectOne("dPartMap.findByDno", 1);
		for (Map.Entry<Object, Object> item : map.entrySet()){
			System.out.println(item.getKey()+"-"+item.getValue());
		}
		sqlSession.close();
	}
	public static void mapTest(){
		SqlSession sqlSession = MybatisUtils.getSession();
		Map<String, dPart> map = sqlSession.selectMap("com.com.great.mapper.DpartMap.findAll", "dName");
		for (Map.Entry<String, dPart> item : map.entrySet()){
			System.out.println(item.getKey()+"-"+item.getValue());
		}
		sqlSession.close();
	}
}
