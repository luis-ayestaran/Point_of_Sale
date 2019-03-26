package com.mexicacode.pofs.entities;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mexicacode.pofs.hbm.HibernateSessionFactory;

public class ProductTypeTest {
	
	HibernateSessionFactory hsf;

	@Before
	public void setUp() throws Exception {
		hsf = new HibernateSessionFactory();
	}

	@After
	public void tearDown() throws Exception {
		if(hsf.getSessionFactory() != null)
			hsf.getSessionFactory().close();
	}

	@Test
	public void test() {
		Session session = hsf.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(new ProductType("lacteo"));
		session.save(new ProductType("embutido"));
		session.getTransaction().commit();
		session.close();
		
		session = hsf.getSessionFactory().openSession();
		session.beginTransaction();
		List list = session.createQuery("FROM ProductType").list();
		for(ProductType productType : (List<ProductType>) list) {
			System.out.println("Product Type: " + productType.getType());
		}
		session.close();
	}

}
