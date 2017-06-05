package com.kongxiaogang.dao;

import java.util.List;
import java.util.Map;

import com.kongxiaogang.model.UserModel;

public interface UserDao {
    int deleteByPrimaryKey(int userId);

    int insert(UserModel record);

    int insertSelective(UserModel record);

    UserModel selectByPrimaryKey(int userId);

    int updateByPrimaryKeySelective(UserModel record);

    int updateByPrimaryKey(UserModel record);

	UserModel getUserByUserName(String userName);
	
	public UserModel getUserByUserEmail(String email);
	
	public UserModel getUserByUserTel(String userTel);
	
	public UserModel getUserNameByUserID(String userID);
	
	public List<UserModel> selectUserList(UserModel user);
	
	public List<Map<String,Object>> selectUserAndRoleList(UserModel user);
	
	public List<Map<String, Object>> selectUserAndRoleAndPerList(UserModel user);

	UserModel selectByUserName(String username);
}