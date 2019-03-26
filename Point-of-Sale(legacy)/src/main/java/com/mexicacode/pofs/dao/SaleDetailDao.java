package com.mexicacode.pofs.dao;

import java.util.List;

import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mexicacode.pofs.exceptions.DaoException;
import com.mexicacode.pofs.hbm.HibernateSessionFactory;
import com.mexicacode.pofs.entities.SaleDetail;

public class SaleDetailDao extends HibernateSessionFactory implements IDao<SaleDetail>{
	public SaleDetail find(SaleDetail t) throws DaoException {
		// TODO Auto-generated method stub
		SaleDetail detailReturn = null;
		Session session = getSessionFactory().openSession();
		String queryStr = "FROM Detail d WHERE d.id = :id AND d.id_sale = :id_sale AND d.id_product = :id_product";
		SaleDetail detail = (SaleDetail) session.createQuery(queryStr)
		.setParameter("id", t.getId())
		.setParameter("id_sale", t.getSale().getFolio())
		.setParameter("id_product", t.getProduct().getId());
		if(detail == null)
			throw new DaoException(System.getProperty("exception.1002"));
		else 
			detailReturn = detail; 
		session.close();
		return detailReturn; 
	} 

	public List<SaleDetail> getAll() throws DaoException {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		String queryStr = "FROM Detail";
		Query<SaleDetail> query = session.createQuery(queryStr, SaleDetail.class);
		List<SaleDetail> detailList = query.list();
		session.close();
		if(detailList == null)
			return null;
		else
			return detailList;
		//return null;
	}

	public boolean save(SaleDetail t) throws DaoException {
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

	public void update(SaleDetail t) throws DaoException {
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

	public void delete(SaleDetail t) throws DaoException {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		session.delete(t);
		session.flush();
		session.close();
	}
}
