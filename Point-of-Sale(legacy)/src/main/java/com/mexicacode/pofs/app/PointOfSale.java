package com.mexicacode.pofs.app;

import com.mexicacode.pofs.views.Login;

public class PointOfSale {

	public static void main(String[] args) {
		PointOfSale pofs = new PointOfSale();
		pofs.init();
	}
	
	private void init() {
		new Login(this);
	}

}
