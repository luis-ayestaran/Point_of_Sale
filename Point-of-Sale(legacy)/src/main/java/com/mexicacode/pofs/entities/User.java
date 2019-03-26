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
@Table(name="user")
public class User {

	@Id
	@Column(name="user_id")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment",strategy="increment")
	public Long id;
	public String name;
	@Column(name="last_name")
	public String lastName;
	@Column(name="mothers_last_name")
	public String mothersLastName;
	public String username;
	public String password;
	@ManyToOne
	@JoinColumn(name="usergroup_id")
	public UserGroup userGroup;
	
	public User() {}
	
	public User(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}
	
	public User(String name,String lastName,String mothersLastName,String username,String password,UserGroup userGroup) {
		this.setName(name);
		this.setLastName(lastName);
		this.setMothersLastName(mothersLastName);
		this.setUsername(username);
		this.setPassword(password);
		this.setUserGroup(userGroup);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMothersLastName() {
		return mothersLastName;
	}

	public void setMothersLastName(String mothersLastName) {
		this.mothersLastName = mothersLastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}
	
	public String toString() {
		StringBuffer sf = new StringBuffer();
		sf.append("[ ");
		sf.append("ID: ");
		sf.append(this.getId());
		sf.append(", ");
		sf.append("Name: ");
		sf.append(this.getName());
		sf.append(", ");
		sf.append("Last Name: ");
		sf.append(this.getLastName());
		sf.append(", ");
		sf.append("Mother's Last Name: ");
		sf.append(this.getMothersLastName());
		sf.append(", ");
		sf.append("User Name: ");
		sf.append(this.getUsername());
		sf.append(", ");
		sf.append("Password: ");
		sf.append(this.getPassword());
		sf.append(", ");
		sf.append("User Group: ");
		sf.append(this.getUserGroup().getGroup());
		sf.append(" ]");
		return sf.toString();
	}
	
}
