package com.cy.pj.sys.dao;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysMenuDaoTests {

	@Autowired
	private SysMenuDao sysMenuDao;
	
	@Test
	public void testFindObjects01() {
		long t1=System.currentTimeMillis();
		List<Map<String,Object>> list=
		sysMenuDao.findObjects();
		long t2=System.currentTimeMillis();
		System.out.println("time:"+(t2-t1));//time:1207
		for(Map<String,Object> map:list) {
			System.out.println(map);
		}
	}
	@Test
	public void testFindObjects02() {
		Instant start=Instant.now();//JDK8
		List<Map<String,Object>> list=
				sysMenuDao.findObjects();
		Instant end=Instant.now();
		System.out.println("time:"+
		Duration.between(start, end).toMillis());//time:1207
		for(Map<String,Object> map:list) {
			System.out.println(map);
		}
	}
}







