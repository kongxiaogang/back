package com.kongxiaogang.dao;

import java.util.List;
import java.util.Map;

import com.kongxiaogang.model.PermissionModel;

public interface PermissionDao {
	public int deleteByPrimaryKey(int perId);

    public int insert(PermissionModel record);

    public int insertSelective(PermissionModel record);

    public PermissionModel selectByPrimaryKey(int perId);

    public int updateByPrimaryKeySelective(PermissionModel record);

    public int updateByPrimaryKey(PermissionModel record);
    
	public PermissionModel getPermissionByPermissionName(String permissionName);
	
	public PermissionModel getPermissionNameByPermissionID(String permissionID);
	/**
	 * 根据权限信息查询权限信息列表
	 * @return
	 */
	public List<PermissionModel> selectPermissionList(PermissionModel permission);
	/**
	 * <pre>selectPermissionList(根据map查询权限信息)   
	 */
	public List<Map<String,Object>> selectPermissionListByMap(Map<String,Object> permission);
	/**
	 * 根据顶级菜单获取对应权限
	 * @return
	 */
	public List<PermissionModel> getLevelOnePermissionList();
	/**
	 * 根据操作id获取所有子菜单对应的权限
	 */
	public List<PermissionModel> getChildsPermissionByParentId(Integer opeId);

}