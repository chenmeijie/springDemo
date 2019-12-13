package com.great.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HelloController
{
	@RequestMapping("/hello")
	@ResponseBody
	public String helloWorld(){
		return "Hello world!!!!!!!";
	}
	@RequestMapping("/index")
	public String index(){
		System.out.println(2222);
		return "back/index";
	}
}
