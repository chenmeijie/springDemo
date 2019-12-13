package com.great.springdemo.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class AopImplProxy
{
	@Pointcut(value = "execution(public * *.eat(..))")
	public void eat(){

	}

	@Before("eat()")
	public void eatBefore(){
		System.out.println("吃饭前洗手。");
	}
	@AfterReturning("eat()")
	public void eatAfter(){
		System.out.println("吃放后走走。");
	}
}
