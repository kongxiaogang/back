package com.kongxiaogang.model.auth;

import com.kongxiaogang.model.common.BaseModel;

public class PermissionMenu extends BaseModel {
	
	private Integer rootId; //根节点id
	private String rootName; //根节点名称
	private Integer secondId; //二级节点id
	private String secondName; //二级节点名称
	private Integer thirdId; //三级节点id
	private String thirdName; //三级节点名称
	private String curName; //当前节点名称
	private String permission; //当前权限

	public PermissionMenu(Integer rootId, String rootName,String curName, String permission){
		this.rootId=rootId;
		this.rootName=rootName;
		this.curName=curName;
		this.permission=permission;
	}
	
	public PermissionMenu(Integer rootId, String rootName, Integer secondId, String secondName,String curName, String permission){
		this.rootId=rootId;
		this.rootName=rootName;
		this.secondId=secondId;
		this.secondName=secondName;
		this.curName=curName;
		this.permission=permission;
	}
	
	public PermissionMenu(Integer rootId, String rootName, Integer secondId, String secondName,Integer thirdId,String thirdName,String curName, String permission){
		this.rootId=rootId;
		this.rootName=rootName;
		this.secondId=secondId;
		this.secondName=secondName;
		this.thirdId=thirdId;
		this.thirdName=thirdName;
		this.curName=curName;
		this.permission=permission;
	}

	public Integer getRootId() {
		return rootId;
	}

	public void setRootId(Integer rootId) {
		this.rootId = rootId;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public Integer getSecondId() {
		return secondId;
	}

	public void setSecondId(Integer secondId) {
		this.secondId = secondId;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getCurName() {
		return curName;
	}

	public void setCurName(String curName) {
		this.curName = curName;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Integer getThirdId() {
		return thirdId;
	}

	public void setThirdId(Integer thirdId) {
		this.thirdId = thirdId;
	}

	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

}
