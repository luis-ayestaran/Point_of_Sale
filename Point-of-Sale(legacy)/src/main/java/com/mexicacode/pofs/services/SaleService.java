package com.mexicacode.pofs.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.mexicacode.pofs.entities.Product;
import com.mexicacode.pofs.entities.SaleDetail;
import com.mexicacode.pofs.entities.Sale;
import com.mexicacode.pofs.dao.SaleDetailDao;
import com.mexicacode.pofs.dao.ProductDao;
import com.mexicacode.pofs.dao.SaleDao;
import com.mexicacode.pofs.exceptions.DaoException;

public class SaleService {
	private ProductDao productDao;
	private SaleDetailDao detailDao;
	private SaleDao saleDao;
	private List<SaleDetail> venta;
	private Float total;
	private Sale sale;
	
	public SaleService() {
		this.productDao = new ProductDao();
		this.detailDao = new SaleDetailDao();
		this.saleDao = new SaleDao();
	}
	
	public boolean addSale(Sale sale) {
		//vender
		boolean result = false;
		sale.setTotal(getTotal());
		sale.setDetail(new HashSet<SaleDetail>(venta));
		try {
			/*for(SaleDetail sd : venta)
			{
				detailDao.save(sd);
			}*/
			result = saleDao.save(sale);
		} catch(DaoException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Sale openSale() {
		//vender
		LocalDate localDate = LocalDate.now();
		sale = new Sale(Date.valueOf(localDate));
		venta = new ArrayList<SaleDetail>();
		return sale;
	}
	
	public void cancelSale() {
		//find sale
		//genero otra venta por el monto igual 
		//reincorporo productos a la entidad product
		while(venta.size() > 0)
		{
			SaleDetail sd = venta.get(0);
			Product p = sd.getProduct();
			p.setQuantity(p.getQuantity() + sd.getQuantity());
			try {
				productDao.update(p);
			} catch(DaoException e) {
				e.printStackTrace();
			}
			venta.remove(0);
		}
	}
	
	public void addDetail(SaleDetail saleDetail) {
		venta.add(saleDetail);
		//descartar la cantidad del detalle de la tabla Product
		Product p = saleDetail.getProduct();
		p.setQuantity(p.getQuantity() - saleDetail.getQuantity());
		try {
			productDao.update(p);
		} catch(DaoException e) {
			e.printStackTrace();
		}
		printVenta();
	}
	
	public void printVenta() {
		System.out.println("VENTA");
		for(int i=0;i<venta.size();i++)
			System.out.println(venta.get(i));
	}
	
	public void removeDetail(SaleDetail saleDetail) {
		Product p = saleDetail.getProduct();
		p.setQuantity(p.getQuantity() + saleDetail.getQuantity());
		try {
			productDao.update(p);
		} catch(DaoException e) {
			e.printStackTrace();
		}
		venta.remove(saleDetail);
		printVenta();
	}
	
	public void saveSale() {
		SaleDao saleDao = new SaleDao();
		for(SaleDetail sd : venta) {
			total =+ sd.getSubTotal();
		}
	}
	
	public Float getTotal() {
		Float total=0.0f;
		//guardo venta
		for(SaleDetail sd : venta) {
			total =+ sd.getSubTotal();
		}
		return total;
	}
	
	public List<SaleDetail> getVenta() {
		return venta;
	}
	
	
	
	
	
	public SaleDetail addDetail(Float cantidad, Product product) {
		SaleDetail detailReturn = null;
		try {
			product.setQuantity(product.getQuantity() - cantidad);
			productDao.update(product);
			SaleDetail detail = new SaleDetail(cantidad, product, sale);
			System.out.println(detail);
			detailDao.save(detail);
		} catch(DaoException e) {
			e.printStackTrace();
		}
		return detailReturn;
	}
	
	/*public void removeDetail(Sale sale, ProductDetail detail, Product product, Integer cantidad) {
		
	}*/
}
