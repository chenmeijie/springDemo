package com.great.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class TestController
{
	@RequestMapping("/test/{url}")
	public String matchUrl(@PathVariable(value = "url") String path){
		System.out.println(path);
		return "back/" + path;
	}
}
