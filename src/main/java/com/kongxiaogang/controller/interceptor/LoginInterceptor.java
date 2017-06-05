package com.kongxiaogang.controller.interceptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.kongxiaogang.model.auth.PermissionMenu;
import com.kongxiaogang.model.auth.UserAuthModel;
import com.kongxiaogang.service.UserService;


/**
 * <pre>项目名称：xhcf-back-web    
 * 类名称：WebLoginInterceptor    
 * 类描述：    拦截器（拦截后台管理请求）
 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
 * 创建时间：2016年9月28日 上午11:56:51    
 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
 * 修改时间：2016年9月28日 上午11:56:51    
 * 修改备注：       
 * @version </pre>
 */
public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	UserService userService;
	private static final Logger _log = LoggerFactory.getLogger(LoginInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		_log.debug("进入后台管理拦截器！");
		if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
			_log.debug("开始拦截！");
			//可添加注解对声明AuthPassport的方法进行拦截，目前在拦截器的配置文件中配置
			//AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
			//获取session中的user对象
			UserAuthModel userAuth= (UserAuthModel) request.getSession().getAttribute("userAuth");
			if(null!=userAuth) {//已登录
				boolean hasPermission=false;
				String requestServletPath=request.getServletPath();
				_log.debug("请求地址："+requestServletPath);
				for(PermissionMenu permissionMenu : userAuth.getPermissionMenus()){
        			Pattern pattern = Pattern.compile(permissionMenu.getPermission(),Pattern.CASE_INSENSITIVE);
        			Matcher matcher = pattern.matcher(request.getContextPath()+requestServletPath);
        			if(matcher.find()){
        				_log.debug("当前请求地址："+permissionMenu);
        				request.getSession().setAttribute("currentMenu",permissionMenu);
        			} 
        			hasPermission=true;
        		}
        		if(hasPermission) {
        			_log.debug("拦截并转发此请求！");
        			return true;
        		}
        		else
        			throw new Exception("没有权限！");
			} else {//跳转登录页面
				_log.debug("当前请求menu："+request.getServletPath());
				response.sendRedirect(request.getContextPath()+"/");
				return false;
			}
		} else {
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
	
}
