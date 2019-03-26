package com.mexicacode.pofs.services;

import java.util.List;

import com.mexicacode.pofs.dao.ProductDao;
import com.mexicacode.pofs.dao.ProductTypeDao;
import com.mexicacode.pofs.exceptions.DaoException;
import com.mexicacode.pofs.entities.Product;
import com.mexicacode.pofs.entities.ProductType;

public class WarehouseService {
	private ProductDao productDao;
	private ProductTypeDao productTypeDao;
	
	public WarehouseService() {
		this.productDao = new ProductDao();
		this.productTypeDao = new ProductTypeDao();
	}
	
	public Product searchProduct(Product product) {
		Product productParam = null;
		try {
			productParam = productDao.find(product);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productParam;
	}
	
	public Integer addProduct(Product product) {
		Integer exit = 0;
		Boolean saved = false;
		ProductType pt = null;
		try {
			pt = productTypeDao.find(product.getProductType());
		} catch(DaoException e) {
			e.printStackTrace();
		}
		try {
			System.out.println("product");
			if(pt != null)
			{
				product.setProductType(pt);
				saved = productDao.save(product);
				if(!saved)
					return 1;
			}
			else
				return 2;
		} catch(DaoException e) {
			e.printStackTrace();
		}
		return exit;
	}
	
	public void updateProduct(Product product) {
		try {
			productDao.update(product);
		} catch(DaoException e) {
			e.printStackTrace();
		}
	}
	
	public List<Product> showProducts() {
		List<Product> list = null;
		try {
			list = productDao.getAll();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ProductType searchProductType(ProductType type) {
		ProductType typeParam = null;
		try {
			typeParam = productTypeDao.find(type);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return typeParam;
	}
	
	public Integer addProductType(ProductType productType) {
		Integer exit = 0;
		boolean saved = false;
		ProductType pt = searchProductType(productType);
		try {
			if(pt == null)
			{
				saved = productTypeDao.save(productType);
				if(!saved)
					exit = 1;
			}
			else
				exit = 2;
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		return exit;
	}
	
	public void updateProductType(ProductType productType) {
		//Integer exit = 0;
		ProductType pt = searchProductType(productType);
		try {
			/*if(pt != null)
			{*/
				productTypeDao.update(productType);
			/*}
			else
				exit = 1;*/
		} catch (DaoException e) {
			e.printStackTrace();
		}
		//return exit;
	}
	
	public List<ProductType> showProductTypes(){
		List<ProductType> list = null;
		try {
			list = productTypeDao.getAll();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
