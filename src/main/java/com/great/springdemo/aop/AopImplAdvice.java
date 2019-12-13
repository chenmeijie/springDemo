package com.great.springdemo.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class AopImplAdvice implements AfterReturningAdvice, MethodBeforeAdvice
{

	@Override
	public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable
	{
		System.out.println("吃饭后走走。");
	}

	@Override
	public void before(Method method, Object[] objects, Object o) throws Throwable
	{
		System.out.println("吃饭前洗手。");
	}
}
