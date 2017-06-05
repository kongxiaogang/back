package com.kongxiaogang.model;

import com.kongxiaogang.model.common.BaseModel;

public class RolePerModel extends BaseModel {
    private Integer id;

    private Integer roleId;

    private Integer perId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPerId() {
        return perId;
    }

    public void setPerId(Integer perId) {
        this.perId = perId;
    }
}