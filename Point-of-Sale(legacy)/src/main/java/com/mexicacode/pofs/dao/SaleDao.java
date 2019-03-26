package com.mexicacode.pofs.dao;

import java.util.List;

import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mexicacode.pofs.entities.Sale;
import com.mexicacode.pofs.exceptions.DaoException;
import com.mexicacode.pofs.hbm.HibernateSessionFactory;

public class SaleDao extends HibernateSessionFactory implements IDao<Sale>{
	public Sale find(Sale t) throws DaoException {
		// TODO Auto-generated method stub
		Sale saleReturn = null;
		Session session = getSessionFactory().openSession();
		String queryStr = "FROM Sale s WHERE s.id = :id";
		Sale sale = (Sale) session.createQuery(queryStr).setParameter("id", t.getFolio());
		if(sale == null)
			throw new DaoException(System.getProperty("exception.1002"));
		else 
			saleReturn = sale; 
		session.close();
		return saleReturn; 
	} 

	public List<Sale> getAll() throws DaoException {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		String queryStr = "FROM Inventory";
		Query<Sale> query = session.createQuery(queryStr, Sale.class);
		List<Sale> list = query.list();
		session.close();
		if(list == null)
			return null;
		else
			return list;
		//return null;
	}

	public boolean save(Sale t) throws DaoException {
		// TODO Auto-generated method stub
		boolean result = false;
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		Long id = (Long) session.save(t);
		t.setFolio(id);
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

	public void update(Sale t) throws DaoException {
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

	public void delete(Sale t) throws DaoException {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		session.delete(t);
		session.flush();
		session.close();
	}
}
