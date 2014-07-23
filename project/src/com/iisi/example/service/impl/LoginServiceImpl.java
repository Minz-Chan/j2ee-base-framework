package com.iisi.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iisi.example.model.dao.impl.UsersDAO;
import com.iisi.example.model.test1.Users;
import com.iisi.example.service.LoginService;
import com.iisi.util.log.BusinessLog;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UsersDAO  userDAO;
	
	@BusinessLog(moduleName = "登录模块", desc = "用户登录验证", exMsg = "用户登录验证失败")
	public boolean allowLogin(String userName, String password) {
		if(userName != null && userName.length() > 0){
			Users user = userDAO.findById(userName);
			if(user != null){
				if(user.getPassword().equals(password))
					return true;
			}
		}
		
		return false;
	}

	@BusinessLog(moduleName = "登录模块", desc = "获取用户对象", exMsg = "获取用户对象失败")
	public Users getUserByUserName(String userName) {
		return userDAO.findById(userName);
	}

}
