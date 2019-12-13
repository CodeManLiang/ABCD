package com.cy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.common.config.PageProperties;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.util.Assert;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
/**
  *  日志模块业务层对象.
  *  思考:业务层对象要处理哪些业务呢?
 * 1)核心业务 (数据和业务的基本操作)
 * 2)拓展业务 (权限控制,缓存,异步,...)
 */
@Service
public class SysLogServiceImpl implements SysLogService {

	@Autowired
	private SysLogDao sysLogDao;
	@Autowired
	private PageProperties pageProperties;
	

	@Override
	public void saveObject(SysLog entity) {
		sysLogDao.insertObject(entity);
	}
	@Override
	public int deleteObjects(Integer... ids) {
		//1.参数校验
		//if(ids==null||ids.length==0)
		//	throw new IllegalArgumentException("请先选择");
		Assert.isValid(ids!=null&&ids.length>0,"请先选择");
		//2.执行删除业务
		int rows=sysLogDao.deleteObjects(ids);
		if(rows==0)
			throw new ServiceException("记录可能已经不存在");
		return rows;
	}

	@Override
	public PageObject<SysLog> findPageObjects(
			String username, Integer pageCurrent){
		//1.参数校验
		Assert.isValid(pageCurrent!=null&&pageCurrent>=1, "页码值无效");
		//2.设置查询起始位置
		int pageSize=pageProperties.getPageSize();
		Page<SysLog> page=
		PageHelper.startPage(pageCurrent,pageSize);
		//3.查询当前页日志记录
		List<SysLog> records=sysLogDao.findPageObjects(username);
		return new PageObject<>(records,(int)page.getTotal(), pageCurrent, pageSize);
	} 

}
