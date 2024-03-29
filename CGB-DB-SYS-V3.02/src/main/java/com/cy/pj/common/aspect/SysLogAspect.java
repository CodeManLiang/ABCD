package com.cy.pj.common.aspect;

import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;


/**
 * @Aspect 注解描述的类型为一个切面类型
 * @Component 注解描述的类为spring中的一个bean对象类型(一般组件)
 * 说明:对于一个切面对象而言,可以理解为封装特定功能的一个扩展业务对象,此对象中通常会定义:
 * 1)切入点(Pointcut):织入扩展功能的一个点(通常为目标方法集合)
 * 2)通知(Advice):是用于进行功能拓展的业务方法
 */

@Aspect
@Component
public class SysLogAspect {
	 //private Logger log = 
	 //LoggerFactory.getLogger(SysLogAspect.class);
	 //bean(bean的名字)为切入点表达式
     @Pointcut("bean(sysUserServiceImpl)")
	 //@Pointcut("@annoation(com.cy.pj.common.annotation.RequiredLog)")
	 public void doPointCut() {}
     
     //@Around("bean(sysUserServiceImpl)")
	 @Around("doPointCut()")
     public Object around(ProceedingJoinPoint jp)
     throws Throwable{
    	long t1=System.currentTimeMillis();
    	Object result=jp.proceed();//调用下一个切面或目标方法
    	long t2=System.currentTimeMillis();
    	//将用户行为信息存储到数据库中.
    	saveLog(jp, (t2-t1));
    	return result;
     }
	 @Autowired
	 private SysLogService sysLogService;
	 //将用户行为信息存储到数据库中.
	 private void saveLog(ProceedingJoinPoint jp,long time)throws Exception {
	   //1.获取用户行为日志(谁(ip+用户名)在什么时间访问了什么方法,传递什么参数,执行时长是多少等)
	   //2.1获取目标方法
	   MethodSignature ms=(MethodSignature)jp.getSignature();
	   Class<?> targetClass=jp.getTarget().getClass();
	   
	   //Method targetMethod=
	   //targetClass.getDeclaredMethod(ms.getName(),ms.getParameterTypes());
	   //RequiredLog rLog=targetMethod.getAnnotation(RequiredLog.class);
	   //String operation=rLog.operation();
	   
	   String dType=targetClass.getName();
	   String methodName=ms.getName();
	   String targetClassMethod=dType+"."+methodName;
	   //2.2获取方法参数
		String params=Arrays.toString(jp.getArgs());
	   //2.对信息进行封装(SysLog)
		SysLog log=new SysLog();
		log.setIp("192.168.1.111");
		log.setUsername("admin");
		log.setOperation("operation");
		log.setMethod(targetClassMethod);//targetClass.targetMethod
		log.setParams(params);
		log.setTime(time);
		log.setCreatedTime(new Date());
	   //3.将信息写入到数据库.
	    sysLogService.saveObject(log);
	 }
}








