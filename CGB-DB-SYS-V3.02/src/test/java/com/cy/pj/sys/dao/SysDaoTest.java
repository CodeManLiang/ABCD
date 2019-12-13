package com.cy.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysDaoTest {
	
	@Autowired
	private SysMenuDao sysMenuDao;
	@Test
	public void testFindObjects() {
		long t1 = System.currentTimeMillis();
		List<Map<String, Object>> list = sysMenuDao.findObjects();
		long t2 = System.currentTimeMillis();
		System.out.println("time:"+(t2-t1));
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
}
