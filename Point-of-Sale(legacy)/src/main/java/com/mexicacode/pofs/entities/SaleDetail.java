package com.mexicacode.pofs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="sale_detail")
public class SaleDetail {
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	@Column(name="saledetail_id")
	public Long id;
	public Float quantity;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	public Product product;
	
	@ManyToOne
	@JoinColumn(name="folio")
	public Sale sale;
	
	
	public SaleDetail() {}
	
	public SaleDetail(Float quantity, Product product,Sale sale) {
		this.setQuantity(quantity);
		this.setProduct(product);
		this.setSale(sale);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Float getQuantity() {
		return quantity;
	}
	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Float getSubTotal() {
		return this.getProduct().getPrice() * this.getQuantity();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		sb.append("ID: ");
		sb.append(this.getId());
		sb.append(", ");
		sb.append("Quantity: ");
		sb.append(this.getQuantity());
		sb.append(", ");
		sb.append("Product: ");
		sb.append(this.getProduct());
		sb.append(" ]");
		return sb.toString();
	}
	
}
