package com.kongxiaogang.service;

import java.util.List;

import com.kongxiaogang.model.MenuModel;

/**
 * 菜单Service层接口示例
 * @author LiJZ
 *
 */
public interface MenuService {

	/**
	 * 查询所有菜单信息
	 */
	public List<MenuModel> getAllMenusList(MenuModel menu);
	/**
	 * 根据父节点获取所有子节点
	 * @param menuModel
	 */
	public List<MenuModel> getChildMenusByParentId(String parentId);
	/**
	 * <pre>getThisLevelMenusByMenuId(根据此节点id查询出同一级的所有节点)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年9月14日 下午1:16:24    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年9月14日 下午1:16:24    
	 * 修改备注： 
	 * @param munuId
	 * @return</pre>
	 */
	public List<MenuModel> getThisLevelMenusByMenuId(String munuId);
	
}
