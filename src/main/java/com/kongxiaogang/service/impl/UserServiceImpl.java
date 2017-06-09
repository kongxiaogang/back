package com.kongxiaogang.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kongxiaogang.dao.UserDao;
import com.kongxiaogang.dao.UserRoleDao;
import com.kongxiaogang.model.UserModel;
import com.kongxiaogang.model.UserRoleModel;
import com.kongxiaogang.service.UserService;
import com.kongxiaogang.service.vo.PageResult;
import com.kongxiaogang.service.vo.ServiceVO;
import com.kongxiaogang.tools.CaptchaUtil;
import com.kongxiaogang.tools.MD5Util;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public UserModel getUserByID(String userId) {
		return userDao.selectByPrimaryKey(Integer.parseInt(userId));
	}

	@Override
	public ServiceVO loginOnMaster(UserModel user) {
		return null;
	}

	@Override
	public ServiceVO<UserModel> loginOnSlave(UserModel user) {
		ServiceVO<UserModel> resultVO = new ServiceVO<UserModel>();
		UserModel select_user = userDao.getUserByUserName(user.getUserName());
		if (null != select_user) {
			System.out.println(MD5Util.MD5Encode(user.getUserPwd()+select_user.getUserSalt(), "utf8"));
			if (null != user.getUserPwd()&& select_user.getUserPwd().equals(MD5Util.MD5Encode(user.getUserPwd()+select_user.getUserSalt(), "utf8"))){
				resultVO.setRunResult(true);
				resultVO.setResultObject(select_user);
			} else {
				resultVO.setRunResult(false);
				resultVO.setMessage("用户名或密码错误！");
			}
		} else {
			resultVO.setMessage("用户不存在！");
		}
		return resultVO;
	}

	@Override
	public List<UserModel> getUserList(UserModel user) {
		return userDao.selectUserList(user);
	}

	@Override
	public int deleteUser(String userId) {
		int userId_int = Integer.parseInt(userId);
		//删除用户角色表该用户信息
		userRoleDao.deleteByUserId(userId_int);
		//删除用户表信息
		userDao.deleteByPrimaryKey(userId_int);
		return userId_int;
	}

	@Override
	public ServiceVO addUser(UserModel user) {
		ServiceVO vo = new ServiceVO();
		if(!StringUtils.isEmpty(user.getUserEmail())) {
			UserModel userResult = userDao.getUserByUserEmail(user.getUserEmail());
			if(null!=userResult) {
				vo.setRunResult(false);
				vo.setMessage("用户邮箱已经存在！");
				return vo;
			}
		}
		if(!StringUtils.isEmpty(user.getUserName())) {
			UserModel userResult = userDao.getUserByUserName(user.getUserName());
			if(null!=userResult) {
				vo.setRunResult(false);
				vo.setMessage("用户名称已经存在！");
				return vo;
			}
		}
		if(!StringUtils.isEmpty(user.getUserTelephone())) {
			UserModel userResult = userDao.getUserByUserTel(user.getUserTelephone());
			if(null!=userResult) {
				vo.setRunResult(false);
				vo.setMessage("用户电话已经存在！");
				return vo;
			}
		}
		//用户信息插入用户表
		//生成加盐
		user.setUserSalt(CaptchaUtil.getSixRandomString());
		//密码加密存储 md5(密码+加盐)
		user.setUserPwd(MD5Util.MD5Encode(user.getUserPwd()+user.getUserSalt(),"utf8"));
		int user_Id = userDao.insertSelective(user);
		UserRoleModel urm = new UserRoleModel();
		//用户角色表是否存在此记录查询校验
		urm.setRoleId(user.getRoleId());
		urm.setUserId(user_Id);
		//插入用户角色表记录
		userRoleDao.insert(urm);
		vo.setRunResult(true);
		vo.setMessage("添加成功！");
		return vo;
	}

	@Override
	public void editUser(UserModel user) {
		if(null!=user.getRoleId()) {
			//删除该用户的用户角色信息
			userRoleDao.deleteByUserId(user.getUserId());
			//增加该用户的用户角色信息
			UserRoleModel urm = new UserRoleModel();
			urm.setRoleId(user.getRoleId());
			urm.setUserId(user.getUserId());
			userRoleDao.insert(urm);
		}
		//更新用户信息
		userDao.updateByPrimaryKeySelective(user);
	}

	@Override
	public List<Map<String, Object>> getUserAndRoleList(UserModel user) {
		return userDao.selectUserAndRoleList(user);
	}

	@Override
	public List<Map<String, Object>> getUserAndRoleAndPerList(UserModel user) {
		return userDao.selectUserAndRoleAndPerList(user);
	}

	@Override
	public UserModel getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}

	@Override
	public List<Map<String, Object>> getUserAndRoleList(Map<String, Object> condition) {
		return userDao.selectUserAndRoleListByMap(condition);
	}

}
