package com.kongxiaogang.service;

import java.util.List;
import java.util.Map;

import com.kongxiaogang.model.PermissionModel;
import com.kongxiaogang.model.auth.TreeNode;


/**
 * Service层接口示例
 * @author LiJZ
 *
 */
public interface PermissionService {

	/**
	 * 根据条件查询权限列表，如果参数为空则查询所有权限信息
	 * @param permission
	 * @return
	 */
	public List<PermissionModel> getPermissionList(PermissionModel permission);
	
	public List<Map<String,Object>> getPermissionList(Map<String,Object> condition);
	/**
	 * 根据permissionid删除权限信息
	 * @param permissionid
	 * @return
	 */
	public Integer deletePermission(String permissionId);
	/**
	 * 添加权限
	 * @param permission
	 */
	public Integer addPermission(PermissionModel permission);
	/**
	 * 根据permissionid查找权限信息
	 * @param id
	 * @return
	 */
	public PermissionModel getPermissionById(String id);
	/**
	 * 修改权限信息
	 * @param permission
	 */
	public void editPermission(PermissionModel permission);
	
	/**
	 * 根据菜单查询所有权限信息树
	 */
	public List<TreeNode> getAllPermissionByMenu(PermissionModel permission);
	/**
	 * <pre>getPermissionsByRoleId(根据角色id查询出对应的权限树)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年9月14日 上午10:11:47    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年9月14日 上午10:11:47    
	 * 修改备注： 
	 * @param id
	 * @return</pre>
	 */
	public List<TreeNode> getPermissionsByRoleId(String id);
	/**
	 * <pre>addOtherLevelPerForOne(为一级菜单对应权限增加下级权限)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2017年5月18日 下午5:07:41    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2017年5月18日 下午5:07:41    
	 * 修改备注： 
	 * @param list
	 * @return</pre>
	 */
	public List<Map<String, Object>> addOtherLevelPerForOne(List<Map<String, Object>> list);
}
