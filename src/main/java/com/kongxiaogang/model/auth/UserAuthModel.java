package com.kongxiaogang.model.auth;
import java.util.List;

import com.kongxiaogang.model.common.BaseModel;
/**
 * 
 * <pre>项目名称：xhcf-back-model    
 * 类名称：UserModel    
 * 类描述：   此模型为后台管理session中用户模型 
 * 创建人：孔小刚 xiaogangkong@galaxyinternet.com    
 * 创建时间：2016年9月28日 下午6:32:48    
 * 修改人：孔小刚 xiaogangkong@galaxyinternet.com     
 * 修改时间：2016年9月28日 下午6:32:48    
 * 修改备注：       
 * @version </pre>
 */
public class UserAuthModel extends BaseModel{
	private static final long serialVersionUID = 5303838827046354951L;
	private int userId; //用户id
	private String userName; //用户名称
	private String realName; //昵称
	private List<AuthorityMenu> authorityMenus; //用户拥有的权限组
	private List<PermissionMenu> permissionMenus; //菜单名称列表
	public UserAuthModel(){	}
	public UserAuthModel(int userId, String userName,String realName){
		this.userId=userId;
		this.userName=userName;
		this.realName=realName;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public List<AuthorityMenu> getAuthorityMenus() {
		return authorityMenus;
	}
	public void setAuthorityMenus(List<AuthorityMenu> authorityMenus) {
		this.authorityMenus = authorityMenus;
	}
	public List<PermissionMenu> getPermissionMenus() {
		return permissionMenus;
	}
	public void setPermissionMenus(List<PermissionMenu> permissionMenus) {
		this.permissionMenus = permissionMenus;
	}
}
