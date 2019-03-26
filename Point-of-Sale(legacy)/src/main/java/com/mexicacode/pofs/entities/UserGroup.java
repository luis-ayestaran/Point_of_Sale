package com.mexicacode.pofs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="user_group")
public class UserGroup {
	@Id
	@Column(name="usergroup_id")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment",strategy="increment")
	private Long id;
	@Column(name="user_group")
	private String group;
	
	public UserGroup() {}

	public UserGroup(String group) {
		this.setGroup(group);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[ ");
		sb.append("ID: ");
		sb.append(this.getId());
		sb.append(", ");
		sb.append("Group: ");
		sb.append(this.getGroup());
		sb.append(" ]");
		return sb.toString();
	}
}
