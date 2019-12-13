package com.cy.pj.sys.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysLogDaoTests {
	 @Autowired
	 private SysLogDao sysLogDao;
	 
	 @Test
	 public void testDeleteObjects() {
		int rows=sysLogDao.deleteObjects(100,200,300);
		//int rows=sysLogDao.deleteObjects();
		System.out.println("delete.rows="+rows);
	 }
	 
	 @Test
	 public void testGetRowCount() {
		// int rowCount=
		// sysLogDao.getRowCount(null);
		 //System.out.println("rowCount="+rowCount);
	 }
	 @Test
	 public void testFindPageObjects() {
		// List<SysLog> list=
		// sysLogDao.findPageObjects("admin",0, 3);
//		 for(SysLog log:list) {
//			 System.out.println(log);
//		 }
		 //lambda
		// list.forEach((a)->System.out.println(a));
	 }
	 
}


