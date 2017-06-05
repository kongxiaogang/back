package com.kongxiaogang.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kongxiaogang.model.MenuModel;
import com.kongxiaogang.model.RoleModel;
import com.kongxiaogang.model.UserModel;
import com.kongxiaogang.model.auth.AuthorityMenu;
import com.kongxiaogang.model.auth.PermissionMenu;
import com.kongxiaogang.model.auth.UserAuthModel;
import com.kongxiaogang.model.contants.PageContants;
import com.kongxiaogang.service.MenuService;
import com.kongxiaogang.service.RoleService;
import com.kongxiaogang.service.UserService;
import com.kongxiaogang.service.vo.PageResult;
import com.kongxiaogang.service.vo.ServiceVO;
import com.kongxiaogang.tools.CaptchaUtil;
import com.kongxiaogang.tools.JsonUtil;
import com.kongxiaogang.tools.MD5Util;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger _log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;
	/**
	 * <pre>initlogin(显示登陆页面)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2017年5月12日 下午5:21:21    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2017年5月12日 下午5:21:21    
	 * 修改备注： 
	 * @param request
	 * @param response
	 * @param mav
	 * @return
	 * @throws Exception</pre>
	 */
	@RequestMapping(value = "/initlogin.do")
	public ModelAndView initlogin(HttpServletRequest request,HttpServletResponse response,ModelAndView mav)
			throws Exception {
		mav.setViewName("/user/login");
		return mav;
	}
	
	@RequestMapping(value = "/login.do")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		String username = request.getParameter("userName");
		String passwd = request.getParameter("userPwd");
		String login_captcha = request.getParameter("captcha");
		String captcha = (String) request.getSession().getAttribute("randomString");
		_log.debug("username:" + username+"captcha"+captcha+"login_captcha"+login_captcha);
		//校验验证码
		/*if(null==captcha||null==login_captcha||!captcha.toLowerCase().equals(login_captcha.toLowerCase())) {
			redirectAttributes.addFlashAttribute("errMessage", "验证码错误！");
			mav.setViewName("redirect:/user/initlogin.do");
			return mav;
		} */
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, passwd);
		try {
			subject.login(token);
			if (subject.isAuthenticated()) {
				mav.setViewName("home");
			} else {
				redirectAttributes.addFlashAttribute("errMessage", "登录失败，请重新登录！");
				mav.setViewName("redirect:/user/initlogin.do");
			}
		}catch (UnknownAccountException uae) {
			token.clear();
			redirectAttributes.addFlashAttribute("errMessage","账户/密码错误！");
			mav.setViewName("redirect:/user/initlogin.do");
		}catch (IncorrectCredentialsException ice){
			token.clear();
			redirectAttributes.addFlashAttribute("errMessage","账户/密码错误！");
			mav.setViewName("redirect:/user/initlogin.do");
		}catch (LockedAccountException e) {
			token.clear();
			redirectAttributes.addFlashAttribute("errMessage", "您的账户已被锁定,请与管理员联系或10分钟后重试！");
			mav.setViewName("redirect:/user/initlogin.do");
		} catch (ExcessiveAttemptsException e) {
			token.clear();
			redirectAttributes.addFlashAttribute("errMessage", "您连续输错5次,帐号将被锁定10分钟!");
			mav.setViewName("redirect:/user/initlogin.do");
		}catch(ExpiredCredentialsException eca)	{
			token.clear();
			redirectAttributes.addFlashAttribute("errMessage", "账户凭证过期！");
			mav.setViewName("redirect:/user/initlogin.do");
		}catch (AuthenticationException e) {
			token.clear();
			redirectAttributes.addFlashAttribute("errMessage", "账户验证失败！");
			mav.setViewName("redirect:/user/initlogin.do");
		}catch (Exception e){
			token.clear();
			request.setAttribute("error", "登录异常，请联系管理员！");
			mav.setViewName("redirect:/user/initlogin.do");
		}
		
		UserModel userModel = userService.getUserByUserName(username);
		//session中userAuth初始化
		UserAuthModel userAuth = new UserAuthModel(userModel.getUserId(),userModel.getUserName(),userModel.getRealName());
		//获取用户菜单的查询条件user
		List<AuthorityMenu> authorityMenus=new ArrayList<AuthorityMenu>();//用户存储登录后左侧菜单树行结构
		List<PermissionMenu> permissionMenus=new ArrayList<PermissionMenu>(); 
		if("admin".equals(username)) { //管理员登录
			List<MenuModel> allmenuList = menuService.getAllMenusList(new MenuModel());
			Map<Integer,AuthorityMenu> allLevelTwoAuthorityMap = new LinkedHashMap<Integer,AuthorityMenu>();
			Map<Integer,AuthorityMenu> allLevelThreeAuthorityMap = new LinkedHashMap<Integer,AuthorityMenu>();
			for(MenuModel menu : allmenuList){
				if("1".equals(menu.getLevel().toString())) {//一级菜单
					AuthorityMenu authorityMenu=new AuthorityMenu(null,menu.getMenuId(), menu.getMenuName(), "", menu.getPageUrl(),menu.getParentId(),new ArrayList<AuthorityMenu>());
					authorityMenus.add(authorityMenu);
				}
				if("2".equals(menu.getLevel().toString())) {//二级菜单
					AuthorityMenu authorityMenu=new AuthorityMenu(null,menu.getMenuId(), menu.getMenuName(), "", menu.getPageUrl(),menu.getParentId(),new ArrayList<AuthorityMenu>());
					allLevelTwoAuthorityMap.put(authorityMenu.getMenuId(),authorityMenu);
				}
				if("3".equals(menu.getLevel().toString())) {//三级菜单
					AuthorityMenu authorityMenu=new AuthorityMenu(null,menu.getMenuId(), menu.getMenuName(), "", menu.getPageUrl(),menu.getParentId());
					allLevelThreeAuthorityMap.put(authorityMenu.getMenuId(),authorityMenu);
				}
			}
			//将所有三级菜单放在二级菜单的children
			for(Map.Entry<Integer,AuthorityMenu> authority : allLevelThreeAuthorityMap.entrySet()) {
				AuthorityMenu levelThreeAuthority = authority.getValue();
				allLevelTwoAuthorityMap.get(levelThreeAuthority.getParentId()).getChildrens().add(levelThreeAuthority);
			}
			System.out.println(allLevelThreeAuthorityMap.size());
			//将所有二级菜单放在一级菜单的children
			for(Map.Entry<Integer,AuthorityMenu> authority : allLevelTwoAuthorityMap.entrySet()) {
				AuthorityMenu levelTwoAuthority = authority.getValue();
				for(AuthorityMenu levelOneAuthority : authorityMenus) {
					if(levelTwoAuthority.getParentId().toString().equals(levelOneAuthority.getMenuId().toString())) {
						levelOneAuthority.getChildrens().add(levelTwoAuthority);
					}
				}
			}
			//权限列表赋值
			for(AuthorityMenu levelOneMenu : authorityMenus) {
				PermissionMenu firstAuthMenu = new PermissionMenu(levelOneMenu.getMenuId(),levelOneMenu.getMenuName(),levelOneMenu.getMenuName(),levelOneMenu.getPageUrl());
				permissionMenus.add(firstAuthMenu);
				for(AuthorityMenu levelTwoMenu : levelOneMenu.getChildrens()) {
					PermissionMenu secondAuthMenu = new PermissionMenu(levelOneMenu.getMenuId(),levelOneMenu.getMenuName(),levelTwoMenu.getMenuId(),levelTwoMenu.getMenuName(),levelTwoMenu.getMenuName(),levelTwoMenu.getPageUrl());
					permissionMenus.add(secondAuthMenu);
					for(AuthorityMenu levelThreeMenu : levelTwoMenu.getChildrens()) {
						PermissionMenu thirdAuthMenu = new PermissionMenu(levelOneMenu.getMenuId(),levelOneMenu.getMenuName(),levelTwoMenu.getMenuId(),levelTwoMenu.getMenuName(),levelThreeMenu.getMenuId(),levelThreeMenu.getMenuName(),levelThreeMenu.getMenuName(),levelThreeMenu.getPageUrl());
						permissionMenus.add(thirdAuthMenu);
					}
				}
			}
		} else {
			List<Map<String, Object>> roleAuthorities = userService.getUserAndRoleAndPerList(userModel);//获取该用户的所有菜单
			Map<Integer,AuthorityMenu> allLevelTwoAuthorityMap = new LinkedHashMap<Integer,AuthorityMenu>();
			Map<Integer,AuthorityMenu> allLevelThreeAuthorityMap = new LinkedHashMap<Integer,AuthorityMenu>();
			for(Map<String,Object> authority : roleAuthorities){
				if("1".equals(authority.get("level").toString())) {//一级菜单
					AuthorityMenu authorityMenu=new AuthorityMenu((Integer)authority.get("perId"),(Integer)authority.get("menuId"), (String)authority.get("menuName"), "", (String)authority.get("pageUrl"),(Integer)authority.get("parentId"),new ArrayList<AuthorityMenu>() );
					authorityMenus.add(authorityMenu);
				}
				if("2".equals(authority.get("level").toString())) {//二级菜单
					AuthorityMenu authorityMenu=new AuthorityMenu((Integer)authority.get("perId"),(Integer)authority.get("menuId"), (String)authority.get("menuName"), "", (String)authority.get("pageUrl"),(Integer)authority.get("parentId"),new ArrayList<AuthorityMenu>() );
					allLevelTwoAuthorityMap.put(authorityMenu.getMenuId(),authorityMenu);
				}
				if("3".equals(authority.get("level").toString())) {//三级菜单
					AuthorityMenu authorityMenu=new AuthorityMenu((Integer)authority.get("perId"),(Integer)authority.get("menuId"), (String)authority.get("menuName"), "", (String)authority.get("pageUrl"),(Integer)authority.get("parentId"));
					allLevelThreeAuthorityMap.put(authorityMenu.getMenuId(),authorityMenu);
				}
			}
			//将所有三级菜单放在二级菜单的children
			for(Map.Entry<Integer,AuthorityMenu> authority : allLevelThreeAuthorityMap.entrySet()) {
				AuthorityMenu levelThreeAuthority = authority.getValue();
				allLevelTwoAuthorityMap.get(levelThreeAuthority.getParentId()).getChildrens().add(levelThreeAuthority);
			}
			System.out.println(allLevelThreeAuthorityMap.size());
			//将所有二级菜单放在一级菜单的children
			for(Map.Entry<Integer,AuthorityMenu> authority : allLevelTwoAuthorityMap.entrySet()) {
				AuthorityMenu levelTwoAuthority = authority.getValue();
				for(AuthorityMenu levelOneAuthority : authorityMenus) {
					if(levelTwoAuthority.getParentId().toString().equals(levelOneAuthority.getMenuId().toString())) {
						levelOneAuthority.getChildrens().add(levelTwoAuthority);
					}
				}
			}
			//权限列表赋值
			for(AuthorityMenu levelOneMenu : authorityMenus) {
				PermissionMenu firstAuthMenu = new PermissionMenu(levelOneMenu.getMenuId(),levelOneMenu.getMenuName(),levelOneMenu.getMenuName(),levelOneMenu.getPageUrl());
				permissionMenus.add(firstAuthMenu);
				for(AuthorityMenu levelTwoMenu : levelOneMenu.getChildrens()) {
					PermissionMenu secondAuthMenu = new PermissionMenu(levelOneMenu.getMenuId(),levelOneMenu.getMenuName(),levelTwoMenu.getMenuId(),levelTwoMenu.getMenuName(),levelTwoMenu.getMenuName(),levelTwoMenu.getPageUrl());
					permissionMenus.add(secondAuthMenu);
					for(AuthorityMenu levelThreeMenu : levelTwoMenu.getChildrens()) {
						PermissionMenu thirdAuthMenu = new PermissionMenu(levelOneMenu.getMenuId(),levelOneMenu.getMenuName(),levelTwoMenu.getMenuId(),levelTwoMenu.getMenuName(),levelThreeMenu.getMenuId(),levelThreeMenu.getMenuName(),levelThreeMenu.getMenuName(),levelThreeMenu.getPageUrl());
						permissionMenus.add(thirdAuthMenu);
					}
				}
			}
		}
		userAuth.setAuthorityMenus(authorityMenus);
		userAuth.setPermissionMenus(permissionMenus);
		request.getSession().setAttribute("userAuth", userAuth); //将userAuth存入session
		mav.setViewName("home");
		return mav;
		/*//校验用户名和密码
		if(null != username &&!"".equals(username) && null != passwd&&!"".equals(passwd)){
			try {
				UserModel login = new UserModel();
				login.setUserName(username);
				login.setUserPwd(passwd);
				ServiceVO<UserModel> result = userService.loginOnSlave(login);
				if(result.isRunResult()) { //登录成功
					//session中userAuth初始化
					UserAuthModel userAuth = new UserAuthModel(result.getResultObject().getUserId(),result.getResultObject().getUserName(),result.getResultObject().getRealName());
					//获取用户菜单的查询条件user
					UserModel user = new UserModel();
					user.setUserId(result.getResultObject().getUserId());
					List<AuthorityMenu> authorityMenus=new ArrayList<AuthorityMenu>();//用户存储登录后左侧菜单树行结构
					List<PermissionMenu> permissionMenus=new ArrayList<PermissionMenu>(); 
					if("admin".equals(username)) { //管理员登录
						List<MenuModel> allmenuList = menuService.getAllMenusList(new MenuModel());
						Map<Integer,AuthorityMenu> allLevelTwoAuthorityMap = new LinkedHashMap<Integer,AuthorityMenu>();
						Map<Integer,AuthorityMenu> allLevelThreeAuthorityMap = new LinkedHashMap<Integer,AuthorityMenu>();
						for(MenuModel menu : allmenuList){
							if("1".equals(menu.getLevel().toString())) {//一级菜单
								AuthorityMenu authorityMenu=new AuthorityMenu(null,menu.getMenuId(), menu.getMenuName(), "", menu.getPageUrl(),menu.getParentId(),new ArrayList<AuthorityMenu>());
								authorityMenus.add(authorityMenu);
							}
							if("2".equals(menu.getLevel().toString())) {//二级菜单
								AuthorityMenu authorityMenu=new AuthorityMenu(null,menu.getMenuId(), menu.getMenuName(), "", menu.getPageUrl(),menu.getParentId(),new ArrayList<AuthorityMenu>());
								allLevelTwoAuthorityMap.put(authorityMenu.getMenuId(),authorityMenu);
							}
							if("3".equals(menu.getLevel().toString())) {//三级菜单
								AuthorityMenu authorityMenu=new AuthorityMenu(null,menu.getMenuId(), menu.getMenuName(), "", menu.getPageUrl(),menu.getParentId());
								allLevelThreeAuthorityMap.put(authorityMenu.getMenuId(),authorityMenu);
							}
						}
						//将所有三级菜单放在二级菜单的children
						for(Map.Entry<Integer,AuthorityMenu> authority : allLevelThreeAuthorityMap.entrySet()) {
							AuthorityMenu levelThreeAuthority = authority.getValue();
							allLevelTwoAuthorityMap.get(levelThreeAuthority.getParentId()).getChildrens().add(levelThreeAuthority);
						}
						System.out.println(allLevelThreeAuthorityMap.size());
						//将所有二级菜单放在一级菜单的children
						for(Map.Entry<Integer,AuthorityMenu> authority : allLevelTwoAuthorityMap.entrySet()) {
							AuthorityMenu levelTwoAuthority = authority.getValue();
							for(AuthorityMenu levelOneAuthority : authorityMenus) {
								if(levelTwoAuthority.getParentId().toString().equals(levelOneAuthority.getMenuId().toString())) {
									levelOneAuthority.getChildrens().add(levelTwoAuthority);
								}
							}
						}
						//权限列表赋值
						for(AuthorityMenu levelOneMenu : authorityMenus) {
							PermissionMenu firstAuthMenu = new PermissionMenu(levelOneMenu.getMenuId(),levelOneMenu.getMenuName(),levelOneMenu.getMenuName(),levelOneMenu.getPageUrl());
							permissionMenus.add(firstAuthMenu);
							for(AuthorityMenu levelTwoMenu : levelOneMenu.getChildrens()) {
								PermissionMenu secondAuthMenu = new PermissionMenu(levelOneMenu.getMenuId(),levelOneMenu.getMenuName(),levelTwoMenu.getMenuId(),levelTwoMenu.getMenuName(),levelTwoMenu.getMenuName(),levelTwoMenu.getPageUrl());
								permissionMenus.add(secondAuthMenu);
								for(AuthorityMenu levelThreeMenu : levelTwoMenu.getChildrens()) {
									PermissionMenu thirdAuthMenu = new PermissionMenu(levelOneMenu.getMenuId(),levelOneMenu.getMenuName(),levelTwoMenu.getMenuId(),levelTwoMenu.getMenuName(),levelThreeMenu.getMenuId(),levelThreeMenu.getMenuName(),levelThreeMenu.getMenuName(),levelThreeMenu.getPageUrl());
									permissionMenus.add(thirdAuthMenu);
								}
							}
						}
					} else {
						List<Map<String, Object>> roleAuthorities = userService.getUserAndRoleAndPerList(user);//获取该用户的所有菜单
						Map<Integer,AuthorityMenu> allLevelTwoAuthorityMap = new LinkedHashMap<Integer,AuthorityMenu>();
						Map<Integer,AuthorityMenu> allLevelThreeAuthorityMap = new LinkedHashMap<Integer,AuthorityMenu>();
						for(Map<String,Object> authority : roleAuthorities){
							if("1".equals(authority.get("level").toString())) {//一级菜单
								AuthorityMenu authorityMenu=new AuthorityMenu((Integer)authority.get("perId"),(Integer)authority.get("menuId"), (String)authority.get("menuName"), "", (String)authority.get("pageUrl"),(Integer)authority.get("parentId"),new ArrayList<AuthorityMenu>() );
								authorityMenus.add(authorityMenu);
							}
							if("2".equals(authority.get("level").toString())) {//二级菜单
								AuthorityMenu authorityMenu=new AuthorityMenu((Integer)authority.get("perId"),(Integer)authority.get("menuId"), (String)authority.get("menuName"), "", (String)authority.get("pageUrl"),(Integer)authority.get("parentId"),new ArrayList<AuthorityMenu>() );
								allLevelTwoAuthorityMap.put(authorityMenu.getMenuId(),authorityMenu);
							}
							if("3".equals(authority.get("level").toString())) {//三级菜单
								AuthorityMenu authorityMenu=new AuthorityMenu((Integer)authority.get("perId"),(Integer)authority.get("menuId"), (String)authority.get("menuName"), "", (String)authority.get("pageUrl"),(Integer)authority.get("parentId"));
								allLevelThreeAuthorityMap.put(authorityMenu.getMenuId(),authorityMenu);
							}
						}
						//将所有三级菜单放在二级菜单的children
						for(Map.Entry<Integer,AuthorityMenu> authority : allLevelThreeAuthorityMap.entrySet()) {
							AuthorityMenu levelThreeAuthority = authority.getValue();
							allLevelTwoAuthorityMap.get(levelThreeAuthority.getParentId()).getChildrens().add(levelThreeAuthority);
						}
						System.out.println(allLevelThreeAuthorityMap.size());
						//将所有二级菜单放在一级菜单的children
						for(Map.Entry<Integer,AuthorityMenu> authority : allLevelTwoAuthorityMap.entrySet()) {
							AuthorityMenu levelTwoAuthority = authority.getValue();
							for(AuthorityMenu levelOneAuthority : authorityMenus) {
								if(levelTwoAuthority.getParentId().toString().equals(levelOneAuthority.getMenuId().toString())) {
									levelOneAuthority.getChildrens().add(levelTwoAuthority);
								}
							}
						}
						//权限列表赋值
						for(AuthorityMenu levelOneMenu : authorityMenus) {
							PermissionMenu firstAuthMenu = new PermissionMenu(levelOneMenu.getMenuId(),levelOneMenu.getMenuName(),levelOneMenu.getMenuName(),levelOneMenu.getPageUrl());
							permissionMenus.add(firstAuthMenu);
							for(AuthorityMenu levelTwoMenu : levelOneMenu.getChildrens()) {
								PermissionMenu secondAuthMenu = new PermissionMenu(levelOneMenu.getMenuId(),levelOneMenu.getMenuName(),levelTwoMenu.getMenuId(),levelTwoMenu.getMenuName(),levelTwoMenu.getMenuName(),levelTwoMenu.getPageUrl());
								permissionMenus.add(secondAuthMenu);
								for(AuthorityMenu levelThreeMenu : levelTwoMenu.getChildrens()) {
									PermissionMenu thirdAuthMenu = new PermissionMenu(levelOneMenu.getMenuId(),levelOneMenu.getMenuName(),levelTwoMenu.getMenuId(),levelTwoMenu.getMenuName(),levelThreeMenu.getMenuId(),levelThreeMenu.getMenuName(),levelThreeMenu.getMenuName(),levelThreeMenu.getPageUrl());
									permissionMenus.add(thirdAuthMenu);
								}
							}
						}
					}
					userAuth.setAuthorityMenus(authorityMenus);
					userAuth.setPermissionMenus(permissionMenus);
					request.getSession().setAttribute("userAuth", userAuth); //将userAuth存入session
					mav.setViewName("home");
				} else {
					redirectAttributes.addFlashAttribute("errMessage", result.getMessage());
					mav.setViewName("redirect:/user/initlogin.do");
				}
			} catch (Exception e) {
				_log.error("loginOnSlave error",e);
				redirectAttributes.addFlashAttribute("errMessage", "登录失败，请重新登录！");
				mav.setViewName("redirect:/user/initlogin.do");
			}
		} else {
			_log.debug("用户名和密码为空"+captcha);
			redirectAttributes.addFlashAttribute("errMessage", "用户名和密码不能为空！");
			mav.setViewName("redirect:/user/initlogin.do");
		}
		return mav;
		*/
	}
	
	/**
	 * <pre>addUser(用户签退)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年9月30日 下午2:43:50    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年9月30日 下午2:43:50    
	 * 修改备注： 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception</pre>
	 */
	@RequestMapping(value = "/logout.do")
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		_log.debug("用户签退！");
		request.getSession().removeAttribute("currentMenu");
		request.getSession().removeAttribute("userAuth");
		return new ModelAndView("redirect:/user/initlogin.do");
	}
	/**
	 * <pre>addUser(显示修改密码页面)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年9月30日 下午3:16:41    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年9月30日 下午3:16:41    
	 * 修改备注： 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception</pre>
	 */
	@RequestMapping(value = "/showModifyPasswd.do")
	public ModelAndView showModifyPasswd(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		return new ModelAndView("/user/modify_passwd");
	}
	/**
	 * <pre>modifyPasswd(修改密码)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年9月30日 下午3:32:54    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年9月30日 下午3:32:54    
	 * 修改备注： 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception</pre>
	 */
	@RequestMapping(value = "/modifyPasswd.do")
	public ModelAndView modifyPasswd(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		UserAuthModel userAuth= (UserAuthModel)request.getSession().getAttribute("userAuth");
		String newPwd = request.getParameter("newPwd");
		String oldPwd = request.getParameter("oldPwd");
		if(null!=userAuth) {
			_log.debug("修改密码！");
			UserModel selectUser = userService.getUserByID(userAuth.getUserId()+"");
			if(MD5Util.MD5Encode(oldPwd+selectUser.getUserSalt(),"utf8").equals(selectUser.getUserPwd())) {
				UserModel updateUser =  new UserModel();
				updateUser.setUserId(userAuth.getUserId());
				updateUser.setUserSalt(CaptchaUtil.getSixRandomString());
				updateUser.setUserPwd(MD5Util.MD5Encode(newPwd+updateUser.getUserSalt(),"utf8"));
				//修改密码
				userService.editUser(updateUser);
				_log.debug("修改成功！");
			} else {
				ModelAndView mav = new ModelAndView("redirect:/user/showModifyPasswd.do");
				mav.addObject("message","原密码错误!");
				return mav;
			}
		}
		request.getSession().removeAttribute("currentMenu");
		request.getSession().removeAttribute("userAuth");
		return new ModelAndView("redirect:/user/initlogin.do");
	}
	/**
	 * <pre>showUserList(显示用户列表)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年9月30日 下午3:16:55    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年9月30日 下午3:16:55    
	 * 修改备注： 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception</pre>
	 */
	@RequestMapping(value = "/showUserList.do")
	public ModelAndView showUserList(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		if(null==pageNum||"".equals(pageNum.trim())) {
			pageNum = PageContants.PAGE_DEFAULTFIRSTPAGE; //默认开始页码
		}
		if(null==pageSize||"".equals(pageSize.trim())) {
			pageSize = PageContants.PAGE_DEFAULTPAGESIZE; //默认每页显示数据条数
		}
		//前端分页模块，调用此方法后的第一个查询会分页
		PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        List<Map<String,Object>> list = userService.getUserAndRoleList(new UserModel());
        PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(list);
        _log.debug("页面数："+page.getPageNum()+"是否有下一頁:"+page.isHasNextPage()+"是否有上一頁："+page.isHasPreviousPage()+"是否有下一页"+page.getPrePage());
        request.setAttribute("page", page);
		return new ModelAndView("/user/user_list");
	}
	
	/**
	 * <pre>showUserAdd(显示用户添加页面)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年9月30日 下午3:17:14    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年9月30日 下午3:17:14    
	 * 修改备注： 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception</pre>
	 */
	@RequestMapping(value = "/showUserAdd.do")
	public ModelAndView showUserAdd(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		ModelAndView mav =  new ModelAndView("/user/user_add");
		//页面传角色列表信息
		List<RoleModel> roleList = roleService.getRoleList(null);
		mav.addObject("rolelist",roleList);
		return mav;
	}
	/**
	 * <pre>showUserEdit(显示修改用户页面)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年9月30日 下午3:17:37    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年9月30日 下午3:17:37    
	 * 修改备注： 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception</pre>
	 */
	@RequestMapping(value = "/showUserEdit.do")
	public ModelAndView showUserEdit(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		String userId = request.getParameter("userId");
		UserModel user = userService.getUserByID(userId);
		//页面传角色列表信息
		List<RoleModel> roleList = roleService.getRoleList(null);
		request.setAttribute("user", user);
		request.setAttribute("rolelist", roleList);
		return new ModelAndView("/user/user_edit");
	}
	
	/**
	 * <pre>deleteUser(删除用户)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年9月30日 下午3:17:26    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年9月30日 下午3:17:26    
	 * 修改备注： 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception</pre>
	 */
	@RequestMapping(value = "/deleteUser.do")
	public ModelAndView deleteUser(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		String userId = request.getParameter("userId");
		_log.debug("userId："+userId);
        userService.deleteUser(userId);
		return new ModelAndView("redirect:showUserList.do");
	}
	/**
	 * <pre>addUser(添加用户)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年9月30日 下午3:18:01    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年9月30日 下午3:18:01    
	 * 修改备注： 
	 * @param user
	 * @return
	 * @throws Exception</pre>
	 */
	@RequestMapping(value = "/addUser.do")
	public ModelAndView addUser(UserModel user)	throws Exception {
		ModelAndView mav = new ModelAndView();
		ServiceVO vo = userService.addUser(user);
		if(vo.isRunResult()) {
			mav.setViewName("redirect:showUserList.do");
		} else {
			mav.setViewName("redirect:/user/showUserAdd.do");
		}
		mav.addObject("message", vo.getMessage());
		return mav;
	}
	/**
	 * <pre>editUser(编辑用户)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年9月30日 下午3:18:15    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年9月30日 下午3:18:15    
	 * 修改备注： 
	 * @param user
	 * @return
	 * @throws Exception</pre>
	 */
	@RequestMapping(value = "/editUser.do")
	public ModelAndView editUser(UserModel user)	throws Exception {
		ModelAndView mav = new ModelAndView("redirect:showUserList.do");
		user.setUserSalt(CaptchaUtil.getSixRandomString());
		user.setUserPwd(MD5Util.MD5Encode(user.getUserPwd()+user.getUserSalt(),"utf8"));
		userService.editUser(user);
		mav.addObject("message","修改用户成功！");
		return new ModelAndView("redirect:showUserList.do");
	}
	
	
	/**
	 * <pre>checkPassword(检查密码是否正确)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年10月31日 下午5:35:05    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年10月31日 下午5:35:05    
	 * 修改备注： 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception</pre>
	 */
	@RequestMapping(value = "/checkPassword.do")
	@ResponseBody
	public String checkPassword(HttpServletRequest request,HttpServletResponse response) throws Exception{
		PageResult pageResult = new PageResult();
		String oldPwd = request.getParameter("oldPwd");
		UserAuthModel userAuth= (UserAuthModel)request.getSession().getAttribute("userAuth");
		if(null!=userAuth) {
			UserModel user = userService.getUserByID(userAuth.getUserId()+"");
			if(MD5Util.MD5Encode(oldPwd+user.getUserSalt(),"utf8").equals(user.getUserPwd())) {
				pageResult.setCode(0);
				pageResult.setMsg("密码正确！");
			} else {
				pageResult.setCode(-1);
				pageResult.setMsg("密码不正确");
			}
		} else {
			pageResult.setCode(-1);
			pageResult.setMsg("请重新登录！");
		}
		return JsonUtil.pageResultToJsonByJackSon(request, pageResult);
	}
	
	/**
	 * <pre>captcha(获取登录时的验证码)   
	 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
	 * 创建时间：2016年9月30日 下午3:18:32    
	 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
	 * 修改时间：2016年9月30日 下午3:18:32    
	 * 修改备注： 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException</pre>
	 */
	@RequestMapping(value = "/captcha.do", method = RequestMethod.GET)
    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        CaptchaUtil.outputCaptcha(request, response);
    }
}
