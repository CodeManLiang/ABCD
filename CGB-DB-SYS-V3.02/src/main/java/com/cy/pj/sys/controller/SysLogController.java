package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

//@Controller
//@ResponseBody
@RestController //==@Controller+@ResponseBody
@RequestMapping("/log/")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	
	
	@RequestMapping("doDeleteObjects")
	public JsonResult doDeleteObjects(Integer...ids) {
		sysLogService.deleteObjects(ids);
		return new JsonResult("delete ok");
	}
	
	@RequestMapping("doFindPageObjects")
	public JsonResult doFindPageObjects(
			String username,Integer pageCurrent) {
		PageObject<SysLog> pageObject=
		sysLogService.findPageObjects(username,
				pageCurrent);
		System.out.println("pageObject="+pageObject);
		return new JsonResult(pageObject);
	}
	/**
	 * @ExceptionHandler 注解描述的方法为一个异常处理方法
	 * @param e
	 * @return
	 */
//	@ExceptionHandler(RuntimeException.class)
//	@ResponseBody
//	public JsonResult doHandleRuntimeException(
//			RuntimeException e) {
//		System.out.println("==local==");
//		e.printStackTrace();
//		return new JsonResult(e);
//	}
}







