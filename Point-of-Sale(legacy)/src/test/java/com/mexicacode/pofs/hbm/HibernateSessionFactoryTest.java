package com.mexicacode.pofs.hbm;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HibernateSessionFactoryTest {
	
	HibernateSessionFactory hsf;

	@Before
	public void setUp() throws Exception {
		hsf = new HibernateSessionFactory();
	}

	@After
	public void tearDown() throws Exception {
		if(hsf.getSessionFactory()!= null)
			hsf.getSessionFactory().close();
	}

	@Test
	public void test() {
		Session session = hsf.getSessionFactory().openSession();
	}

}
