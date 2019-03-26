package com.mexicacode.pofs.dao;

import java.util.List;

import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mexicacode.pofs.exceptions.DaoException;
import com.mexicacode.pofs.hbm.HibernateSessionFactory;
import com.mexicacode.pofs.entities.Product;


/**
 * Esta clase permite el acceso a datos de la entidad Producto
 * 
 * @author Ricardo Mangor�
 * @version 0.0.1
 */
public class ProductDao extends HibernateSessionFactory implements IDao<Product>{
	
	/**
	 * Busca un producto por su ID
	 * 
	 * @version 0.0.1
	 * @param product
	 * @throws DaoException 
	 */
	public Product find(Product product) throws DaoException {
		Session session = getSessionFactory().openSession();
		String strQuery = "SELECT p FROM Product p WHERE p.product = :product OR p.barCode = :barCode";
		Product result = null;
		result = (Product) session.createQuery(strQuery)
		.setParameter("product", product.getProduct())
		.setParameter("barCode", product.getBarCode()).uniqueResult();
		if(result == null)
			throw new DaoException(System.getProperty("exception.1002"));
		session.close();
		return result; 
	}

	public List<Product> getAll() throws DaoException {
		Session session = getSessionFactory().openSession();
		String queryStr = "FROM Product";
		Query<Product> query = session.createQuery(queryStr, Product.class);
		List<Product> productList = query.list();
		session.close();
		if(productList == null)
			return null;
		else
			return productList;
	}

	public boolean save(Product t) throws DaoException {
		boolean result = false;
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		Long id = (Long) session.save(t);
		t.setId(id);
		try {
			session.getTransaction().commit();
			session.close();
			result = true;
		}catch(IllegalStateException e1) {
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

	public void update(Product t) throws DaoException {
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

	public void delete(Product t) throws DaoException {
		// TODO Auto-generated method stub
		
	}
	
}