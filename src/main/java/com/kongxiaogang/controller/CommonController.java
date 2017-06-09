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

import com.kongxiaogang.model.MenuModel;
import com.kongxiaogang.model.UserModel;
import com.kongxiaogang.model.auth.AuthorityMenu;
import com.kongxiaogang.service.MenuService;
import com.kongxiaogang.service.RoleService;
import com.kongxiaogang.service.UserService;
import com.kongxiaogang.tools.CaptchaUtil;

@Controller
@RequestMapping(value = "/")
public class CommonController {
	private static final Logger _log = LoggerFactory.getLogger(CommonController.class);
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
	@RequestMapping(value = "initlogin.do")
	public ModelAndView initlogin(HttpServletRequest request,HttpServletResponse response,ModelAndView mav)
			throws Exception {
		mav.setViewName("/user/login");
		return mav;
	}
	
	@RequestMapping(value = "login.do")
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
			mav.setViewName("redirect:/initlogin.do");
		}catch (LockedAccountException e) {
			token.clear();
			redirectAttributes.addFlashAttribute("errMessage", "您的账户已被锁定,请与管理员联系或10分钟后重试！");
			mav.setViewName("redirect:/initlogin.do");
		} catch (ExcessiveAttemptsException e) {
			token.clear();
			redirectAttributes.addFlashAttribute("errMessage", "您连续输错5次,帐号将被锁定10分钟!");
			mav.setViewName("redirect:/initlogin.do");
		}catch(ExpiredCredentialsException eca)	{
			token.clear();
			redirectAttributes.addFlashAttribute("errMessage", "账户凭证过期！");
			mav.setViewName("redirect:/initlogin.do");
		}catch (AuthenticationException e) {
			token.clear();
			redirectAttributes.addFlashAttribute("errMessage", "账户验证失败！");
			mav.setViewName("redirect:/initlogin.do");
		}catch (Exception e){
			token.clear();
			request.setAttribute("error", "登录异常，请联系管理员！");
			mav.setViewName("redirect:/initlogin.do");
		}
		mav.setViewName("redirect:/home.do");
		return mav;
	}
	
	@RequestMapping(value = "home.do")
	public ModelAndView home(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		// 获取登录的bean
		String userName = (String) SecurityUtils.getSubject().getPrincipal();
		UserModel userModel = userService.getUserByUserName(userName);
		//获取用户菜单的查询条件user
		List<AuthorityMenu> authorityMenus=new ArrayList<AuthorityMenu>();//用户存储登录后左侧菜单树行结构
		if("admin".equals(userName)) { //管理员登录
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
		}
		mav.addObject("menuList", authorityMenus);
		mav.setViewName("/home");
		return mav;
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
	@RequestMapping(value = "logout.do")
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		_log.debug("用户签退！");
		request.getSession().removeAttribute("currentMenu");
		request.getSession().removeAttribute("userAuth");
		return new ModelAndView("redirect:/user/initlogin.do");
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
	@RequestMapping(value = "captcha.do", method = RequestMethod.GET)
    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        CaptchaUtil.outputCaptcha(request, response);
    }
}
