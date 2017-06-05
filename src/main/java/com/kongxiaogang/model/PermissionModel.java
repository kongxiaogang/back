package com.kongxiaogang.model;

import com.kongxiaogang.model.common.BaseModel;

public class PermissionModel extends BaseModel{
    private Integer perId;
    
    private String perKey;

    private String perName;

    private Integer perType;

    private Integer opeId;

    private Integer menuId;

    public Integer getPerId() {
        return perId;
    }

    public void setPerId(Integer perId) {
        this.perId = perId;
    }

    public String getPerKey() {
		return perKey;
	}

	public void setPerKey(String perKey) {
		this.perKey = perKey;
	}

	public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName == null ? null : perName.trim();
    }

    public Integer getPerType() {
        return perType;
    }

    public void setPerType(Integer perType) {
        this.perType = perType;
    }

    public Integer getOpeId() {
        return opeId;
    }

    public void setOpeId(Integer opeId) {
        this.opeId = opeId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}