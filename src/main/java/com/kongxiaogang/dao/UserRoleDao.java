package com.kongxiaogang.dao;

import com.kongxiaogang.model.UserRoleModel;

public interface UserRoleDao {
    int deleteByPrimaryKey(int id);

    int insert(UserRoleModel record);

    int insertSelective(UserRoleModel record);

    UserRoleModel selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(UserRoleModel record);

    int updateByPrimaryKey(UserRoleModel record);
    
    int deleteByUserId(int userId);
}