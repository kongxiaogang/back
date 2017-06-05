package com.kongxiaogang.service;

import java.util.List;

import com.kongxiaogang.model.RoleModel;


/**
 * Service层接口示例
 * @author LiJZ
 *
 */
public interface RoleService {

	/**
	 * 根据条件查询角色列表，如果参数为空则查询所有角色信息
	 * @param role
	 * @return
	 */
	public List<RoleModel> getRoleList(RoleModel role);
	/**
	 * 根据roleid删除角色信息
	 * @param roleid
	 * @return
	 */
	public Integer deleteRole(String roleId);
	/**
	 * 添加角色
	 * @param role
	 */
	public Integer addRole(RoleModel role);
	/**
	 * 添加角色(根据角色对象和权限组)
	 * @param role
	 */
	public Integer addRole(RoleModel role,String pers);
	/**
	 * 根据roleid查找角色信息
	 * @param id
	 * @return
	 */
	public RoleModel getRoleById(String id);
	/**
	 * 修改角色信息
	 * @param role
	 */
	public void editRole(RoleModel role);
	/**
	 * <pre>editRoleAndPer(修改角色和权限)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年9月22日 下午8:02:44    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年9月22日 下午8:02:44    
	 * 修改备注： 
	 * @param role
	 * @param pers</pre>
	 */
	public void editRoleAndPer(RoleModel role, String pers);
}
