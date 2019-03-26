package com.mexicacode.pofs.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="sale")
public class Sale {

	@Id
	@Column(name="folio")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	private Long folio;
	private Date date;
	private Float total;
	
	@OneToMany(mappedBy="sale")
	private Set<SaleDetail> detail;
	
	public Sale() {}
	
	public Sale(Date date) {
		this.setDate(date);
	}
	
	public Sale(Date date, Float total) {
		this.setDate(date);
		this.setTotal(total);
	}
	
	public Sale(Date date, Float total, Set<SaleDetail> detail) {
		this(date,total);
		this.setDetail(detail);
	}

	public Long getFolio() {
		return folio;
	}

	public void setFolio(Long folio) {
		this.folio = folio;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Set<SaleDetail> getDetail() {
		return detail;
	}

	public void setDetail(Set<SaleDetail> detail) {
		this.detail = detail;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		sb.append("Folio: ");
		sb.append(this.getFolio());
		sb.append(", ");
		sb.append("Date: ");
		sb.append(this.getDate());
		sb.append(", ");
		sb.append("Detail: ");
		sb.append(this.getDetail().toString());
		sb.append(" ]");
		return sb.toString();
	}
	
}
