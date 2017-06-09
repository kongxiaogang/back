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
import com.kongxiaogang.tools.RequestParamersUtil;

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
		Map<String,Object> queryCondition = RequestParamersUtil.paramerToMap(request);
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
        List<Map<String,Object>> list = userService.getUserAndRoleList(queryCondition);
        PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(list);
        _log.debug("页面数："+page.getPageNum()+"是否有下一頁:"+page.isHasNextPage()+"是否有上一頁："+page.isHasPreviousPage()+"是否有下一页"+page.getPrePage());
        request.setAttribute("page", page);
        request.setAttribute("queryCondition", queryCondition);
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
	@ResponseBody
	public String addUser(HttpServletRequest request,UserModel user)	throws Exception {
		PageResult pageResult = new PageResult();
		ServiceVO vo = userService.addUser(user);
		if(vo.isRunResult()) {
			pageResult.setCode(0);
			pageResult.setMsg("成功！");
		} else {
			
		}
		return JsonUtil.pageResultToJson(request, pageResult);
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
