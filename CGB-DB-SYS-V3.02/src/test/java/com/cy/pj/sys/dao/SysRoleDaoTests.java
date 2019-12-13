package com.cy.pj.sys.dao;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.vo.SysRoleMenuVo;



@SpringBootTest
public class SysRoleDaoTests {
	  @Autowired
	  private SysRoleDao sysRoleDao;
	  
	  @Test
	  public void testFindObjectById() {
		 SysRoleMenuVo rm=sysRoleDao.findObjectById(1);
		 System.out.println(rm);
	  }
	  
	 
}













