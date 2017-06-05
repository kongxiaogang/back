package com.kongxiaogang.service;

import java.util.List;
import java.util.Map;

import com.kongxiaogang.model.UserModel;
import com.kongxiaogang.service.vo.ServiceVO;

public interface UserService {
	/**
	 * 在Master库实现登录操作
	 */
	public ServiceVO<UserModel> loginOnMaster(UserModel user);
	/**
	 * 在Slave库实现登录操作
	 */
	public ServiceVO<UserModel> loginOnSlave(UserModel user);
	/**
	 * 根据条件查询用户列表，如果参数为空则查询所有用户信息
	 */
	public List<UserModel> getUserList(UserModel user);
	/**
	 * 根据userid删除用户信息
	 */
	public int deleteUser(String userID);
	/**
	 * 添加用户
	 */
	public ServiceVO<UserModel> addUser(UserModel user);
	/**
	 * 根据userid查找用戶信息
	 */
	public UserModel getUserByID(String id);
	public UserModel getUserByUserName(String userName);
	/**
	 * 修改用户信息
	 */
	public void editUser(UserModel user);
	/**
	 * <pre>selectUserAndRoleList(根据用户model查询用户和角色的关联信息)   
	 */
	public List<Map<String,Object>> getUserAndRoleList(UserModel user);
	/**
	 * <pre>getUserAndRoleAndPerList(根据用户查询用户，角色和对应的权限，菜单信息)   
	 */
	public List<Map<String,Object>> getUserAndRoleAndPerList(UserModel user);
	
}
