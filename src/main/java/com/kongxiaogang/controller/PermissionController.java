package com.kongxiaogang.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kongxiaogang.model.MenuModel;
import com.kongxiaogang.model.PermissionModel;
import com.kongxiaogang.model.contants.MenuContants;
import com.kongxiaogang.model.contants.PageContants;
import com.kongxiaogang.service.MenuService;
import com.kongxiaogang.service.PermissionService;
import com.kongxiaogang.tools.RequestParamersUtil;
/**
 * 
 * <pre>项目名称：xhcf-back-web    
 * 类名称：PermissionController    
 * 类描述：   权限控制器
 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
 * 创建时间：2016年9月5日 上午11:09:07    
 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
 * 修改时间：2016年9月5日 上午11:09:07    
 * 修改备注：       
 * @version </pre>
 */
@Controller
@RequestMapping(value = "/permission")
public class PermissionController {

	private static final Logger _log = LoggerFactory.getLogger(PermissionController.class);
	private PermissionService permissionService;
	private MenuService menuService;
	
	@RequestMapping(value = "/showPermissionList.do")
	public ModelAndView showPermissionList(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		Map<String,Object> queryCondition = RequestParamersUtil.paramerToMap(request);
		queryCondition.put("level", MenuContants.MENU_LEVEL_ONE);//等级一的权限
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		if(null==pageNum||"".equals(pageNum.trim())) {
			pageNum = PageContants.PAGE_DEFAULTFIRSTPAGE; //默认开始页码
		}
		if(null==pageSize||"".equals(pageSize.trim())) {
			pageSize = PageContants.PAGE_DEFAULTPAGESIZE; //默认每页显示数据条数
		}
		PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		//获取等级一的所有对应权限
        List<Map<String,Object>> list = permissionService.getPermissionList(queryCondition);
        //一级菜单对应权限增加下级权限
        List<Map<String,Object>> list_alllevel = permissionService.addOtherLevelPerForOne(list);
        PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(list_alllevel);
        _log.debug("页面数："+page.getPageNum()+"是否有下一頁:"+page.isHasNextPage()+"是否有上一頁："+page.isHasPreviousPage()+"ain : "+page.getPageSize()+"是否有下一页"+page.getPrePage());
        request.setAttribute("page", page);
		return new ModelAndView("/permission/permission_list");
	}
	/**
	 * 展示权限添加页面
	 * @throws Exception
	 */
	@RequestMapping(value = "/showPermissionAdd.do")
	public ModelAndView showPermissionAdd(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("/permission/permission_add");
		String parentId = request.getParameter("parentId");
		//查询添加权限时能看到的权限
		List<MenuModel> childMenuList = menuService.getChildMenusByParentId(parentId);
		request.setAttribute("childMenuList", childMenuList);
		return mav;
	}
	/**
	 * 展示权限编辑页面
	 * @throws Exception
	 */
	@RequestMapping(value = "/showPermissionEdit.do")
	public ModelAndView showPermissionEdit(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		String munuId = request.getParameter("menuId");
		//查询修改权限时能看到的权限
		List<MenuModel> childMenuList = menuService.getThisLevelMenusByMenuId(munuId);		
		PermissionModel permission = permissionService.getPermissionById(id);
		request.setAttribute("childMenuList", childMenuList);
		request.setAttribute("permission", permission);
		return new ModelAndView("/permission/permission_edit");
	}
	
	
	@RequestMapping(value = "/deletePermission.do")
	public ModelAndView deletePermission(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		_log.debug("id："+id);
        permissionService.deletePermission(id);
		return new ModelAndView("redirect:showPermissionList.do");
	}
	/**
	 *	添加权限 
	 */
	@RequestMapping(value = "/addPermission.do")
	public ModelAndView addPermission(PermissionModel permission)	throws Exception {
		permissionService.addPermission(permission);
		return new ModelAndView("redirect:showPermissionList.do");
	}
	
	@RequestMapping(value = "/editPermission.do")
	public ModelAndView editPermission(PermissionModel permission)	throws Exception {
		permissionService.editPermission(permission);
		return new ModelAndView("redirect:showPermissionList.do");
	}

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
	public MenuService getMenuService() {
		return menuService;
	}
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	
}
