package com.kongxiaogang.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kongxiaogang.model.RoleModel;
import com.kongxiaogang.model.auth.TreeNode;
import com.kongxiaogang.model.contants.PageContants;
import com.kongxiaogang.service.PermissionService;
import com.kongxiaogang.service.RoleService;

import net.sf.json.JSONArray;
/**
 * 
 * <pre>项目名称：xhcf-back-web    
 * 类名称：RoleController    
 * 类描述：    角色控制器
 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
 * 创建时间：2016年9月5日 上午11:09:27    
 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
 * 修改时间：2016年9月5日 上午11:09:27    
 * 修改备注：       
 * @version </pre>
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController {

	private static final Logger _log = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService; 
	
	@RequestMapping(value = "/showRoleList.do")
	public ModelAndView showRoleList(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		if(null==pageNum||"".equals(pageNum.trim())) {
			pageNum = PageContants.PAGE_DEFAULTFIRSTPAGE; //默认开始页码
		}
		if(null==pageSize||"".equals(pageSize.trim())) {
			pageSize = PageContants.PAGE_DEFAULTPAGESIZE; //默认每页显示数据条数
		}
		PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        List<RoleModel> list = roleService.getRoleList(new RoleModel());
        PageInfo<RoleModel> page = new PageInfo<RoleModel>(list);
        _log.debug("页面数："+page.getPageNum()+"是否有下一頁:"+page.isHasNextPage()+"是否有上一頁："+page.isHasPreviousPage()+"ain : "+page.getPageSize()+"是否有下一页"+page.getPrePage());
        request.setAttribute("page", page);
		return new ModelAndView("/role/role_list");
	}
	
	@RequestMapping(value = "/showRoleAdd.do")
	public ModelAndView showRoleAdd(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("/role/role_add");
		List<TreeNode> mList =permissionService.getAllPermissionByMenu(null); 
		JSONArray arr = JSONArray.fromObject(mList);
		_log.debug("权限树转json对象："+arr.toString().replace("\"nodes\":[],", ""));
		request.setAttribute("TreeJson", arr.toString().replace("\"nodes\":[],", ""));
		return mav;
	}
	
	@RequestMapping(value = "/showRoleEdit.do")
	public ModelAndView showRoleEdit(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("/role/role_edit");
		//显示所有树节点,并将权限对应的节点选择上
		String id = request.getParameter("id");
		List<TreeNode> mList =permissionService.getPermissionsByRoleId(id);
		JSONArray arr = JSONArray.fromObject(mList);
		_log.debug("权限树转json对象："+arr.toString());
		request.setAttribute("TreeJson", arr.toString().replace("\"nodes\":[],", "").replace("\"state\":{},", ""));
		_log.debug("权限树转json对象："+arr.toString().replace("\"nodes\":[],", "").replace("\"state\":{},", ""));
		RoleModel role = roleService.getRoleById(id);
		request.setAttribute("role", role);
		return mav;
	}
	
	/**
	 * 删除角色及相应的权限 
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/deleteRole.do")
	public ModelAndView deleteRole(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		_log.debug("id："+id);
        roleService.deleteRole(id);
		return new ModelAndView("redirect:showRoleList.do");
	}
	/**
	 *添加角色及角色对应的权限 
	 */
	@RequestMapping(value = "/addRole.do")
	public ModelAndView addRole(HttpServletRequest request,HttpServletResponse response)	throws Exception {
		ModelAndView mav = new ModelAndView("redirect:showRoleList.do");
		//接受参数
		String pers = request.getParameter("permissions");
		String roleDes = request.getParameter("roleDes");
		String roleName = request.getParameter("roleName");
		_log.debug("添加角色的所有权限："+pers+"roleDes:"+roleDes);
		RoleModel rm = new RoleModel();
		rm.setRoleName(roleName);
		rm.setRoleDes(roleDes);
		//添加角色及权限信息
		roleService.addRole(rm, pers);
		mav.addObject("message","添加角色成功！");
		return mav;
	}
	/**
	 * <pre>editRole(更新角色信息及相应的权限信息)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年9月23日 上午9:23:24    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年9月23日 上午9:23:24    
	 * 修改备注： 
	 * @param request
	 * @param role
	 * @return
	 * @throws Exception</pre>
	 */
	@RequestMapping(value = "/editRole.do")
	public ModelAndView editRole(HttpServletRequest request,RoleModel role)	throws Exception {
		String pers = request.getParameter("permissions");
		roleService.editRoleAndPer(role,pers);
		return new ModelAndView("redirect:showRoleList.do");
	}
}
