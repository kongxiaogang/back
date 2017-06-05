package com.kongxiaogang.dao;

import java.util.List;
import java.util.Map;

import com.kongxiaogang.model.RoleModel;

public interface RoleDao {
    public int deleteByPrimaryKey(int roleId);

    public int insert(RoleModel record);

    public int insertSelective(RoleModel record);

    public RoleModel selectByPrimaryKey(int roleId);

    public int updateByPrimaryKeySelective(RoleModel record);

    public int updateByPrimaryKey(RoleModel record);
    
	public RoleModel getRoleByRoleName(String roleName);
	/**
	 * 根据角色id查询角色信息
	 */
	public RoleModel getRoleNameByRoleID(String roleID);
	/**
	 * 根据角色信息查询角色信息列表
	 */
	public List<RoleModel> selectRoleList(RoleModel role);
	/**
	 * <pre>selectRoleAndPerList(根据角色id查询对应角色和权限信息)   
	 */
	public List<Map<String,Object>>  selectRoleAndPerList(RoleModel role);
}