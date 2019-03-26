package com.mexicacode.pofs.entities;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mexicacode.pofs.exceptions.ToolkitException;
import com.mexicacode.pofs.hbm.HibernateSessionFactory;
import com.mexicacode.pofs.utils.Toolkit;

public class UserTest {
	
	private HibernateSessionFactory sf;
	
	@Before
	public void setUp() throws Exception {
		sf = new HibernateSessionFactory();
	}

	@After
	public void tearDown() throws Exception {
		if(sf.getSessionFactory() != null)
			sf.getSessionFactory().close();
	}

	@Test
	public void test() {
		try {
			Session session = sf.getSessionFactory().openSession();
			UserGroup userGroup = new UserGroup("user");
			User user = new User("Luis Fer","Libreros","Ayestarán","luisF",Toolkit.strToMD5("luisF"),userGroup);
			session.beginTransaction();
			session.save(userGroup);
			session.save(user);
			session.getTransaction().commit();
			session.close();
		} catch (ToolkitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
