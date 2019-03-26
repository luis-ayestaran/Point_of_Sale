package com.mexicacode.pofs.scale.demoapp;

public class ImportingDLL {
	public static void main(String args[]) {
	    try {
	    	System.load("C:\\Users\\LuisF\\eclipse-workspace\\Point-of-Sale(legacy)\\src\\main\\resources\\rxtxSerial.dll");
	    } catch (UnsatisfiedLinkError e) {
	      System.err.println("Native code library failed to load.\n" + e);
	      System.exit(1);
	    }
	    System.out.println("Biblioteca importada con  éxito");
	 }
}
