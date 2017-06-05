package com.kongxiaogang.dao;

import java.util.List;

import com.kongxiaogang.model.MenuModel;

public interface MenuDao {
    public int deleteByPrimaryKey(int menuId);

    public int insert(MenuModel record);

    public int insertSelective(MenuModel record);

    public MenuModel selectByPrimaryKey(int menuId);

    public int updateByPrimaryKeySelective(MenuModel record);

    public int updateByPrimaryKey(MenuModel record);
    
    public List<MenuModel> getLevelOneMenuList();

	public List<MenuModel> getChildMenusByParentId(int parentId);
	/**
	 * <pre>getThisLevelMenusByMenuId(这里用根据此节点id查询同一级的所有节点)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年9月14日 下午1:18:26    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年9月14日 下午1:18:26    
	 * 修改备注： 
	 * @param parseInt
	 * @return</pre>
	 */
	public List<MenuModel> getThisLevelMenusByMenuId(Integer menuId);
	/**
	 * <pre>selectAllMenusList(查询所有菜单信息)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年12月28日 上午11:10:08    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年12月28日 上午11:10:08    
	 * 修改备注： 
	 * @return</pre>
	 */
	public List<MenuModel> selectAllMenusList(MenuModel menu);
    
}