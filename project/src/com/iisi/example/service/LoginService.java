package com.iisi.example.service;

import com.iisi.example.model.test1.Users;

public interface LoginService {
	/**
	 * 是否允许登录
	 * @param userName
	 * @param password
	 * @return
	 */
	boolean allowLogin(String userName, String password);
	
	/**
	 * 通过用户名取得用户对象
	 * @param userName
	 * @return
	 */
	Users getUserByUserName(String userName);
}
