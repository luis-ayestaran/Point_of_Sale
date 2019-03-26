package com.mexicacode.pofs.dao;

import java.util.List;

import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mexicacode.pofs.exceptions.DaoException;
import com.mexicacode.pofs.hbm.HibernateSessionFactory;
import com.mexicacode.pofs.entities.ProductType;

public class ProductTypeDao extends HibernateSessionFactory implements IDao<ProductType> {
	public ProductType find(ProductType t) throws DaoException {
		// TODO Auto-generated method stub
		ProductType typeReturn = null;
		Session session = getSessionFactory().openSession();
		String queryStr = "FROM ProductType t WHERE t.id = :id OR t.type= :type";
		ProductType type = (ProductType) session.createQuery(queryStr)
		.setParameter("id", t.getId())
		.setParameter("type", t.getType()).uniqueResult();
		if(type == null)
			throw new DaoException(System.getProperty("exception.1002"));
		else 
			typeReturn = type; 
		session.close();
		return typeReturn; 
	} 

	public List<ProductType> getAll() throws DaoException {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		String queryStr = "FROM ProductType";
		Query<ProductType> query = session.createQuery(queryStr, ProductType.class);
		List<ProductType> list = query.list();
		session.close();
		if(list == null)
			return null;
		else
			return list;
		//return null;
	}

	public boolean save(ProductType t) throws DaoException {
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

	public void update(ProductType t) throws DaoException {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.update(t);
		try {
			session.getTransaction().commit();
			session.close();
		}catch(IllegalStateException e1) {
			e1.getStackTrace();
			throw e1;
		}catch(RollbackException e2) {
			e2.getStackTrace();
			throw e2;
		}finally{
			session.close();
		}
	}

	public void delete(ProductType t) throws DaoException {
		// TODO Auto-generated method stub
	}
}
