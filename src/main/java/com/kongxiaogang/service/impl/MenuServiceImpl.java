package com.kongxiaogang.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongxiaogang.dao.MenuDao;
import com.kongxiaogang.model.MenuModel;
import com.kongxiaogang.service.MenuService;

/**
 * 
 * 菜单累service层处理
 *
 */
@Service("menuService")
public class MenuServiceImpl  implements MenuService {
	@Autowired
	private MenuDao menuDao;
	
	/**
	 * 获取所有一级菜单节点
	 * @return
	 * @throws Exception
	 */
	public List<MenuModel> getLevelOneMenuList() {
		return menuDao.getLevelOneMenuList();
		
	}
	/**
	 * 根据父节点找到所有子节点，如果父节点为空，则获取一级菜单所有节点
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<MenuModel> getChildMenusByParentId(String parentId) {
		if(parentId==null||"".equals(parentId.trim())) {
			parentId = "1000000";
		}
		return menuDao.getChildMenusByParentId(Integer.parseInt(parentId));
		
	}
	
	/**
	 * 获取所有菜单信息
	 * @param menu
	 * @return
	 */
	@Override
	public List<MenuModel> getAllMenusList(MenuModel menu){
		List<MenuModel> allMenus = menuDao.selectAllMenusList(menu);
		return allMenus;
	}
	@Override
	public List<MenuModel> getThisLevelMenusByMenuId(String munuId) {
		return menuDao.getThisLevelMenusByMenuId(Integer.parseInt(munuId));
	}
	
}
