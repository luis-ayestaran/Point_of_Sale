package com.mexicacode.pofs.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="product_type")
public class ProductType {
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	@Column(name="producttype_id")
	private Long id;
	
	@Column(nullable=false, length=255)
	private String type;
	
	/*@OneToMany(mappedBy="product", cascade = CascadeType.ALL)
	private Set<Product> products = new HashSet();*/
	
	public ProductType() {}
	
	public ProductType(String type) {
		this.setType(type);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	/*public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}	*/
	
}
