package com.kongxiaogang.dao;

import com.kongxiaogang.model.RolePerModel;

public interface RolePerDao {
    int deleteByPrimaryKey(int id);

    int insert(RolePerModel record);

    int insertSelective(RolePerModel record);

    RolePerModel selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(RolePerModel record);

    int updateByPrimaryKey(RolePerModel record);
    
    int deleteByRoleId(int roleId);
}