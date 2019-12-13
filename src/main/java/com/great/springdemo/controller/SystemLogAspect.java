package com.great.springdemo.controller;

import com.great.springdemo.entity.Log;
import com.great.springdemo.entity.SystemLog;
import com.great.springdemo.entity.Userinfo;
import com.great.springdemo.service.LogService;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

@Aspect
@Component
public class SystemLogAspect
{
	@Resource
	private LogService logService;
	private static Logger logger = Logger.getLogger("zxtest");
	//Controller层切点
	@Pointcut("execution (* com.great.springdemo.service.*.*(..))  && !execution (* com.great.springdemo.service.LogService.*(..)) && !execution (* com.great.springdemo.service.DpartService.*(..))")
	public  void controllerAspect() {
	}

	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		System.out.println("==========执行controller前置通知===============");

		if(logger.isInfoEnabled()){
			logger.info("before " + joinPoint);
		}
	}

	@Around("controllerAspect()")
	public Object around(ProceedingJoinPoint joinPoint){
		System.out.println("==========开始执行controller环绕通知===============");
		long start = System.currentTimeMillis();
		Object obj = null;
		String methodName = joinPoint.getSignature().getName();
		try {
			obj = ((ProceedingJoinPoint) joinPoint).proceed();
			long end = System.currentTimeMillis();
			if(logger.isInfoEnabled()){
				logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
			}
			System.out.println("==========结束执行controller环绕通知===============");
		} catch (Throwable e) {
			System.out.println("环绕通知中的异常--------------------------------"+methodName+"-------"+e.getMessage());
			long end = System.currentTimeMillis();
			if(logger.isInfoEnabled()){
				logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
			}
		}
		if (null == obj){
			obj = false;
		}
		return obj;
	}

	@After("controllerAspect()")
	public  void after(JoinPoint joinPoint) throws Throwable{
       /* HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();  */
		//读取session中的用户
		// User user = (User) session.getAttribute("user");
		//请求的IP
		//String ip = request.getRemoteAddr();
		Userinfo user = new Userinfo();
		user.setUserid("1");
		user.setLoginname("张三");
		String ip = "127.0.0.1";
		try {

			String targetName = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			Object[] arguments = joinPoint.getArgs();
			Class targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			String operationType = "";
			String operationName = "";
			Class<?>[] paramsType = null;
			for (Method method : methods) {
				boolean flag = true;
				if (method.getName().equals(methodName) &&
						method.getParameterTypes().length==arguments.length) {
					paramsType = method.getParameterTypes();
							for (int i = 0; i < paramsType.length; i++)
							{
								if (null != arguments[i]){
									if (paramsType[i]!=arguments[i].getClass()){
										flag = false;
										break;
									}
								}
							}
						}else {
							flag = false;
						}
						if (flag){
							operationType = method.getAnnotation(Log.class).operationType();
							operationName = method.getAnnotation(Log.class).operationName();
							break;
						}
			}
			//*========控制台输出=========*//
			System.out.println("=====controller后置通知开始=====");
			System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType);
			System.out.println("方法描述:" + operationName);
			System.out.println("请求人:" + user.getLoginname());
			System.out.println("请求IP:" + ip);
			//*========数据库日志=========*//
			SystemLog log = new SystemLog();
			log.setId(UUID.randomUUID().toString());
			log.setDescription(operationName);
			log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType);
			log.setLogType((long)0);
			log.setRequestIp(ip);
			log.setExceptioncode( null);
			log.setExceptionDetail( null);
			log.setParams( null);
			log.setCreateBy(user.getLoginname());
			log.setCreateDate(new Date());
			logService.addLog(log);
			//保存数据库
			//            systemLogService.insert(log);
			System.out.println("=====controller后置通知结束=====");
		}  catch (Exception e) {
			//记录本地异常日志
			logger.error("==后置通知异常==");
			logger.error("异常信息:{}------"+ e.getMessage());
			throw e;
		}
	}

}
