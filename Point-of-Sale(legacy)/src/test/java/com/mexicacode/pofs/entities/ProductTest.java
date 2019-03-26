package com.mexicacode.pofs.entities;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mexicacode.pofs.hbm.HibernateSessionFactory;

public class ProductTest {

	private HibernateSessionFactory hsf;
	
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
		ProductType productType = new ProductType("lacteo");
		session.save(productType);
		Product product = new Product("Leche",
				"La vaquita",11.90f,15f,1001f,"L",
				50,200,
				Date.valueOf("2019-01-01"),
				Date.valueOf("2019-12-12"),
				12345l,productType);
		session.save(product);
		session.getTransaction().commit();
		session.close();
		
		session = hsf.getSessionFactory().openSession();
		session.beginTransaction();
		List list = session.createQuery("FROM Product").list();
		for(Product p : (List<Product>) list) {
			System.out.println(p.toString());
		}
		session.getTransaction().commit();
		session.close();
	}

}
