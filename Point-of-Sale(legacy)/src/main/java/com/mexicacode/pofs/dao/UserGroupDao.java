package com.mexicacode.pofs.dao;

import java.util.List;

import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mexicacode.pofs.exceptions.DaoException;
import com.mexicacode.pofs.hbm.HibernateSessionFactory;
import com.mexicacode.pofs.entities.UserGroup;

public class UserGroupDao extends HibernateSessionFactory implements IDao<UserGroup>{

	public UserGroup find(UserGroup t) throws DaoException {
		// TODO Auto-generated method stub
		UserGroup ugReturn = null;
		Session session = getSessionFactory().openSession();
		String queryStr = "FROM UserGroup u WHERE u.group = :group";
		UserGroup userGroup = (UserGroup) session.createQuery(queryStr)
		.setParameter("group", t.getGroup()).uniqueResult();
		if(userGroup == null)
			throw new DaoException(System.getProperty("exception.1002"));
		else 
			ugReturn = userGroup; 
		session.close();
		return ugReturn; 
	} 

	public List<UserGroup> getAll() throws DaoException {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		String queryStr = "FROM UserGroup";
		Query<UserGroup> query = session.createQuery(queryStr, UserGroup.class);
		List<UserGroup> ugList = query.list();
		session.close();
		if(ugList == null)
			return null;
		else
			return ugList;
		//return null;
	}

	public boolean save(UserGroup t) throws DaoException {
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

	public void update(UserGroup t) throws DaoException {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		UserGroup userGroup = (UserGroup) session.get(UserGroup.class, t.getId());
		userGroup = t;
		session.update(userGroup);
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

	public void delete(UserGroup t) throws DaoException {
		// TODO Auto-generated method stub
		
	}

}
