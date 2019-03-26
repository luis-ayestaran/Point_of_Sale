package com.mexicacode.pofs.dao;

import java.util.List;

import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mexicacode.pofs.exceptions.DaoException;
import com.mexicacode.pofs.hbm.HibernateSessionFactory;
import com.mexicacode.pofs.entities.User;

public class UserDao extends HibernateSessionFactory implements IDao<User>{

	public User find(User t) throws DaoException {
		// TODO Auto-generated method stub
		User userReturn = null;
		Session session = getSessionFactory().openSession();
		String queryStr = "FROM User u WHERE u.username = :username AND u.password = :password";
		User user = (User) session.createQuery(queryStr)
		.setParameter("username", t.getUsername())
		.setParameter("password", t.getPassword()).uniqueResult();
		if(user == null)
			throw new DaoException(System.getProperty("exception.1002"));
		else 
			userReturn = user; 
		session.close();
		return userReturn; 
	} 

	public List<User> getAll() throws DaoException {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		String queryStr = "FROM User";
		Query<User> query = session.createQuery(queryStr, User.class);
		List<User> userList = query.list();
		session.close();
		if(userList == null)
			return null;
		else
			return userList;
		//return null;
	}

	public boolean save(User t) throws DaoException {
		// TODO Auto-generated method stub
		boolean result = false;
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		Long id = (Long) session.save(t);
		t.setId(id);
		try {
			session.getTransaction().commit();
			session.close();
			result = true;
		} catch(IllegalStateException e1) {
			e1.getStackTrace();
			throw e1;
		}catch(RollbackException e2) {
			e2.getStackTrace();
			throw e2;
		}finally{
			session.close();
		}
		return result;
	}

	public void update(User t) throws DaoException {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		User user = (User) session.get(User.class, t.getId());
		user = t;
		session.update(user);
		try {
			session.getTransaction().commit();
			session.close();
		} catch(IllegalStateException e1) {
			e1.getStackTrace();
			throw e1;
		}catch(RollbackException e2) {
			e2.getStackTrace();
			throw e2;
		}finally{
			session.close();
		}
	}

	public void delete(User t) throws DaoException {
		// TODO Auto-generated method stub
		
	}

}
