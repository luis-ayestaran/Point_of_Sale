package com.mexicacode.pofs.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="product")
public class Product {
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment",strategy="increment")
	@Column(name="product_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="producttype_id")
	private ProductType productType;
	private String product;
	private String brand;
	private Float cost;
	private Float price;
	private Float quantity;
	private String unit;
	private Integer minStock;
	private Integer maxStock;
	private Date entryDate;
	private Date dischargeDate;
	private Long barCode;
	private Boolean inBulk;
	
	public Product() {}
	
	public Product(String product,
			String brand,
			Float cost,
			Float price,
			Float quantity,
			String unit,
			Integer minStock,
			Integer maxStock,
			Date entryDate,
			Date dischargeDate,
			Long barCode,
			ProductType productType) {
		this.setProduct(product);
		this.setBrand(brand);
		this.setCost(cost);
		this.setPrice(price);
		this.setQuantity(quantity);
		this.setUnit(unit);
		this.setMinStock(minStock);
		this.setMaxStock(maxStock);
		this.setEntryDate(entryDate);
		this.setDischargeDate(dischargeDate);
		this.setBarCode(barCode);
		this.setProductType(productType);
	}
	
	public Product(String product,
			String brand,
			Float cost,
			Float price,
			Float quantity,
			String unit,
			Integer minStock,
			Integer maxStock,
			Date entryDate,
			Date dischargeDate,
			Long barCode,
			ProductType productType,
			Boolean inBulk) {
		this.setProduct(product);
		this.setBrand(brand);
		this.setCost(cost);
		this.setPrice(price);
		this.setQuantity(quantity);
		this.setUnit(unit);
		this.setMinStock(minStock);
		this.setMaxStock(maxStock);
		this.setEntryDate(entryDate);
		this.setDischargeDate(dischargeDate);
		this.setBarCode(barCode);
		this.setProductType(productType);
		this.setInBulk(inBulk);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Float getCost() {
		return cost;
	}
	public void setCost(Float cost) {
		this.cost = cost;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getQuantity() {
		return quantity;
	}
	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getMinStock() {
		return minStock;
	}
	public void setMinStock(Integer minStock) {
		this.minStock = minStock;
	}
	public Integer getMaxStock() {
		return maxStock;
	}
	public void setMaxStock(Integer maxStock) {
		this.maxStock = maxStock;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public Date getDischargeDate() {
		return dischargeDate;
	}
	public void setDischargeDate(Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}
	
	public Long getBarCode() {
		return barCode;
	}

	public void setBarCode(Long barCode) {
		this.barCode = barCode;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	
	public Boolean isInBulk() {
		return inBulk;
	}
	
	public void setInBulk(Boolean inBulk) {
		this.inBulk = inBulk;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ID: ");
		sb.append(this.getId());
		sb.append(", ");
		sb.append("Product: ");
		sb.append(this.getProduct());
		sb.append(", ");
		sb.append("Brand: ");
		sb.append(this.getBrand());
		sb.append(", ");
		sb.append("Cost: ");
		sb.append(this.getCost());
		sb.append(", ");
		sb.append("Price: ");
		sb.append(this.getPrice());
		sb.append(", ");
		sb.append("Quantity: ");
		sb.append(this.getQuantity());
		sb.append(", ");
		sb.append("Unitt: ");
		sb.append(this.getUnit());
		sb.append(", ");
		sb.append("Min Stock: ");
		sb.append(this.getMinStock());
		sb.append(", ");
		sb.append("Max Stock: ");
		sb.append(this.getMaxStock());
		sb.append(", ");
		sb.append("Entry Date: ");
		sb.append(this.getEntryDate());
		sb.append(", ");
		sb.append("Discharge Date: ");
		sb.append(this.getDischargeDate());
		sb.append(", ");
		sb.append("Bar Code: ");
		sb.append(this.getBarCode());
		sb.append(" ]");
		return sb.toString(); 
	}
}
