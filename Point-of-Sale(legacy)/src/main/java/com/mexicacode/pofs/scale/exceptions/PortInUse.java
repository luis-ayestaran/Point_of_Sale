package com.mexicacode.pofs.scale.exceptions;

public class PortInUse extends Exception {
	
	private static final String MESSAGE="El puerto esta en uso";
	
	public PortInUse() {
		super(MESSAGE);
	}

}
