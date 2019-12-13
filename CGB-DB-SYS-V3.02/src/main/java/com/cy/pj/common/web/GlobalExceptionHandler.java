package com.cy.pj.common.web;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cy.pj.common.vo.JsonResult;
/**
 * @ControllerAdvice 注解描述的类为全局异常处理类,
  *  此类中可以定义全局的异常处理方法.
 * 
 */
//@ControllerAdvice
//@ResponseBody
@RestControllerAdvice //==@ControllerAdvice+@ResponseBody
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public JsonResult doHandleRuntimeException(
			RuntimeException e) {
		System.out.println("==global==");
		e.printStackTrace();
		return new JsonResult(e);
	}
}




