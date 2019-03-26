package com.mexicacode.pofs.services;

public class FlagService {
	private Boolean flag;
	
	public FlagService() {
		flag = false;
	}
	
	public void setEnabled(Boolean flag) {
		this.flag = flag;
	}
	
	public Boolean isEnabled() {
		return flag;
	}
}
