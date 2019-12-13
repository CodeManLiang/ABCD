package com.cy.pj.sys.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 基于此Controll呈现页面
 * @author Administrator
 */
@Controller
@RequestMapping("/")
public class PageController {
	 //此类型的对象线程安全?(如何保证的?,底层CAS算法-借助CPU硬件优势)
	 //private AtomicLong al=new AtomicLong(0);
	 public PageController() {
		//System.out.println("PageController()");
	 }
	 @RequestMapping("doIndexUI")
	 public String doIndexUI() {
		 //String tName=Thread.currentThread().getName();
		 //System.out.println("thread.tname="+tName);
		 //记录页面访问次数
		// System.out.println(al.incrementAndGet());
		 return "starter";
	 }
	 @RequestMapping("doPageUI")
	 public String doPageUI() {
		 return "common/page";
	 }
//	 @RequestMapping("log/log_list")
//	 public String doLogUI() {
//		 return "sys/log_list";//view
//	 }
//	 @RequestMapping("menu/menu_list")
//	 public String doMenuUI() {
//		 return "sys/menu_list";
//	 }
	 //rest风格(一个软件架构编码风格)的URL映射
	 //{}表为rest表达式
	 //@PathVariable用于告诉spring mvc 参数值从url中获取
	 @RequestMapping("{module}/{moduleUI}")
	 public String doModuleUI(
			 @PathVariable String moduleUI) {
		 return "sys/"+moduleUI;
	 }
	
}










