package com.mexicacode.pofs.services;

import java.util.List;

import com.mexicacode.pofs.dao.UserDao;
import com.mexicacode.pofs.dao.UserGroupDao;
import com.mexicacode.pofs.exceptions.DaoException;
import com.mexicacode.pofs.entities.User;
import com.mexicacode.pofs.entities.UserGroup;

public class AuthenticationService {
	private UserDao userDao;
	private UserGroupDao userGroupDao;
	
	public AuthenticationService() {
		this.userDao = new UserDao();
		this.userGroupDao = new UserGroupDao();
	}
	
	public User searchUser(User user) {
		User valueReturn = null;
		User userParam;
		try {
			userParam = userDao.find(user);
			if(userParam != null)
				valueReturn = userParam;
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valueReturn;
	}
	
	public void registrateUser(User user, UserGroup userGroup) {
		UserGroup ugParam = searchUserGroup(userGroup);
		try {
			if(ugParam != null)
			{
				user.setUserGroup(ugParam);
				userDao.save(user);
			}
			else
			{
				userGroupDao.save(userGroup);
				userDao.save(user);
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Boolean adminExists() {
		Boolean exists = false;
		List<User> list = showUsers();
		for(User u : list)
		{
			if(u.getUserGroup().getGroup().equals("admin"))
			{
				exists = true;
				break;
			}
		}
		return exists;
	}
	
	public List<User> showUsers() {
		List<User> list = null;
		try {
			list = userDao.getAll();
		} catch(DaoException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public UserGroup searchUserGroup(UserGroup userGroup) {
		UserGroup ugParam = null;
		try {
			ugParam = userGroupDao.find(userGroup);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return ugParam;
	}
}
