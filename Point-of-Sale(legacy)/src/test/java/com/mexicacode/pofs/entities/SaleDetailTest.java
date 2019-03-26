package com.mexicacode.pofs.entities;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mexicacode.pofs.hbm.HibernateSessionFactory;

public class SaleDetailTest {

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
		LocalDate localDate = LocalDate.now();
		Sale sale = new Sale(Date.valueOf(localDate.toString()));
		ProductType lacteo = new ProductType("lacteo");
		ProductType embutido = new ProductType("embutido");
		ProductType conserva = new ProductType("conserva");
		Product product1 = new Product("Leche",
				"La vaquita",11.90f,15f,1001f,"L",
				50,200,
				Date.valueOf("2019-01-01"),
				Date.valueOf("2019-12-12"),
				12345l,lacteo);
		Product product2 = new Product("Jamón",
				"porky",15.5f,25f, 1001f,"Kg",
				50,200,
				Date.valueOf("2019-01-01"),
				Date.valueOf("2019-12-12"),
				134567l,embutido);
		Product product3 = new Product("Duraznos en almibar",
				"La Latosa",25f,35f,1001f,"unidad",
				50,200,
				Date.valueOf("2019-01-01"),
				Date.valueOf("2022-12-12"),
				12345l,conserva);
		SaleDetail saleDetail1 = new SaleDetail(9f,product1,sale);
		SaleDetail saleDetail2 = new SaleDetail(10f,product2,sale);
		SaleDetail saleDetail3 = new SaleDetail(20f,product3,sale);
		Session session = hsf.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(sale);
		session.save(lacteo);
		session.save(embutido);
		session.save(conserva);
		session.save(product1);
		session.save(product2);
		session.save(product3);
		session.save(saleDetail1);
		session.save(saleDetail2);
		session.save(saleDetail3);
		session.getTransaction().commit();
		session.close();
		
		session = hsf.getSessionFactory().openSession();
		session.beginTransaction();
		List list = session.createQuery("FROM Sale").list();
		for(Sale s : (List<Sale>) list) {
			System.out.println(s.toString());
			for(SaleDetail sd : s.getDetail()){
				System.out.println("Product: " + sd.getProduct().getProduct());
			}
		}
		session.getTransaction().commit();
		session.close();
	}
}
