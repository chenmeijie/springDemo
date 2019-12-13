package com.great.springdemo.aop;

public class AopImpl implements AopInterface
{

	@Override
	public void eat()
	{
		System.out.println("某个人在吃饭");
	}
}
