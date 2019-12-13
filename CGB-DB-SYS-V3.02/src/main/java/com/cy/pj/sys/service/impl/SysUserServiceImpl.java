package com.cy.pj.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.common.config.PageProperties;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.util.Assert;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;
import com.cy.pj.sys.vo.SysUserDeptVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;//依赖,DIP原则
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Autowired
	private PageProperties pageProperties;
	
	@Override
	public int isExists(String columnName, String columnValue) {
		//1.参数校验
		Assert.isEmpty(columnValue, "字段值不正确");
		//2.基于字段以及值进行统计查询
		return sysUserDao.isExist("sys_users",columnName, columnValue);
	}
	
	@Override
	public Map<String, Object> findObjectById(Integer id) {
	    //1.参数校验
		Assert.isValid(id!=null&&id>0, "id值无效");
		//2.查询用户以及用户对应的部门信息
		SysUserDeptVo user=sysUserDao.findObjectById(id);
		if(user==null)
			throw new ServiceException("用户不存在");
		//3.查询用户对应的角色信息
		List<Integer> roleIds=sysUserRoleDao.findRoleIdsByUserId(id);
		//4.封装结果并返回
		Map<String,Object> map=new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
	
	@Override
	public int updateObject(SysUser entity, Integer[] roleIds) {
		//1.参数校验
		Assert.isNull(entity, "保存对象不能为空");
		Assert.isEmpty(entity.getUsername(), "用户名不能为空");
		Assert.isEmpty(roleIds, "必须为用户分配角色");
		//2.保存用户自身信息
		int rows=sysUserDao.updateObject(entity);
		//3.保存用户和角色关系数据
		sysUserRoleDao.deleteById("sys_user_roles","user_id",entity.getId());
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		//4.返回结果
		return rows;
	}
	
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		//1.参数校验
		Assert.isNull(entity, "保存对象不能为空");
		Assert.isEmpty(entity.getUsername(), "用户名不能为空");
		Assert.isEmpty(entity.getPassword(), "密码不能为空");
		Assert.isEmpty(roleIds, "必须为用户分配角色");
		//2.保存用户自身信息
		//2.1对密码进行加密
		String salt=UUID.randomUUID().toString();
		//String newPassword=
		//DigestUtils.md5DigestAsHex((entity.getPassword()+salt).getBytes());
	    SimpleHash sh=new SimpleHash(
			   "MD5",//algorithmName 算法名称
			   entity.getPassword(),//source 未加密的密码
			   salt,//盐值
			   1);//hashIterations 加密次数
	    String newPassword=sh.toHex();
		//2.2重新存储到entity对象
	    entity.setSalt(salt);
	    entity.setPassword(newPassword);
		//2.3持久化用户信息
	    int rows=sysUserDao.insertObject(entity);
		//3.保存用户和角色关系数据
	    sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		//4.返回结果
		return rows;
	}
	//@RequiredLog(operation="valid")
	@Override
	public int validById(Integer id, Integer valid, String modifiedUser) {
		long t1=System.currentTimeMillis();
		//1.参数校验
		Assert.isValid(id!=null&&id>0, "id值无效");
		Assert.isValid(valid!=null&&(valid==1||valid==0), "状态无效");
		//2.修改状态
		int rows=sysUserDao.validById(id, valid, modifiedUser);
		if(rows==0)
			throw new ServiceException("记录可能已经不存在");
		long t2=System.currentTimeMillis();
	    log.info("SysUserServiceImpl.validById execute time {}",(t2-t1));
		//3.返回结果
		return rows;
	}
	
	@Override
	public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
		//1.参数校验
		Assert.isValid(pageCurrent!=null&&pageCurrent>0, "当前页码值不正确");
		//2.设置起始位置
		int pageSize=pageProperties.getPageSize();
		Page<SysUserDeptVo> page=PageHelper.startPage(pageCurrent, pageSize);
		//3.查询当前页记录
		List<SysUserDeptVo> records=
		sysUserDao.findPageObjects(username);
		//4.封装查询结果
		return new PageObject<>(records,(int)page.getTotal(), pageCurrent, pageSize);
	}
}
