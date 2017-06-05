package com.kongxiaogang.model;

import com.kongxiaogang.model.common.BaseModel;

public class MenuModel extends BaseModel{
    private Integer menuId;

    private String menuName;

    private String pageUrl;

    private Integer parentId;

    private Integer level;

    private Integer orderNumber;
    
    private Integer leafNode; 

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl == null ? null : pageUrl.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

	public Integer getLeafNode() {
		return leafNode;
	}

	public void setLeafNode(Integer leafNode) {
		this.leafNode = leafNode;
	}
}