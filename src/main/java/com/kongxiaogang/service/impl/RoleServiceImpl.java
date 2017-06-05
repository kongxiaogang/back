package com.kongxiaogang.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongxiaogang.dao.RoleDao;
import com.kongxiaogang.dao.RolePerDao;
import com.kongxiaogang.model.RoleModel;
import com.kongxiaogang.model.RolePerModel;
import com.kongxiaogang.service.RoleService;


/**
 * 业务系统接入Trace，必须添加相应类注解<br>
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
	private static final Logger _log = LoggerFactory.getLogger(RoleServiceImpl.class);
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RolePerDao rolePerDao;

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public RolePerDao getRolePerDao() {
		return rolePerDao;
	}

	public void setRolePerDao(RolePerDao rolePerDao) {
		this.rolePerDao = rolePerDao;
	}

	/**
	 * 根据条件查询角色信息列表，如果条件为空则查询所有角色信息
	 */
	@Override
	public List<RoleModel> getRoleList(RoleModel role) {
		return roleDao.selectRoleList(role);
	}

	/**
	 * 根据用户Id删除角色信息
	 */
	@Override
	public Integer deleteRole(String roleId) {
		Integer roleid = Integer.parseInt(roleId);
		return roleDao.deleteByPrimaryKey(roleid);
	}

	/**
	 * 添加角色
	 */
	@Override
	public Integer addRole(RoleModel role) {
		return roleDao.insertSelective(role);

	}
	/**
	 * 根据角色对象和权限组,添加对应数据
	 */
	@Override
	public Integer addRole(RoleModel role, String pers) {
		if(null!=pers&&!"".equals(pers)) {
			//添加角色数据
			int roleid = roleDao.insertSelective(role);
			String[] perarr = pers.split(",");
			//将角色对应权限添加到角色权限关联表
			for (int i = 0; i < perarr.length; i++) {
				RolePerModel rpm = new RolePerModel();
				rpm.setPerId(Integer.parseInt(perarr[i]));
				rpm.setRoleId(roleid);
				rolePerDao.insert(rpm);
			}
		}
		
		return null;
	}
	/**
	 * 根據id找角色信息
	 */
	@Override
	public RoleModel getRoleById(String id) {
		if (null == id || "".equals(id)) {
			return new RoleModel();
		}
		RoleModel role = new RoleModel();
		role.setRoleId(Integer.parseInt(id));
		List<RoleModel> roles = roleDao.selectRoleList(role);
		if (roles.size() > 0) {
			return roles.get(0);
		}
		return new RoleModel();
	}

	/**
	 * 修改角色信息
	 */
	@Override
	public void editRole(RoleModel role) {
		roleDao.updateByPrimaryKeySelective(role);
	}

	@Override
	public void editRoleAndPer(RoleModel role, String pers) {
		if(null!=pers&&!"".equals(pers)) {
			//获取角色id
			int roleid = role.getRoleId();
			//删除角色对应的权限
			rolePerDao.deleteByRoleId(roleid);
			//更新角色信息
			roleDao.updateByPrimaryKeySelective(role);
			//获取角色对应的权限ids
			String[] perarr = pers.split(",");
			//将角色对应权限添加到角色权限关联表
			for (int i = 0; i < perarr.length; i++) {
				RolePerModel rpm = new RolePerModel();
				rpm.setPerId(Integer.parseInt(perarr[i]));
				rpm.setRoleId(roleid);
				rolePerDao.insert(rpm);
			}
		}
	}
}
