package com.mexicacode.pofs.scale.serialcommunication;

import java.util.concurrent.Semaphore;

public class Bascula {
	
	private Float peso;
	private static Semaphore semaphore = new Semaphore(1);
	
	public Bascula() {
		this.peso = 0.0F;
	}
	
	public Bascula(Float peso) {
		this.peso = peso;
	}
	
	public Float getPeso() {
		return this.peso;
	}
	
	public void setPeso(Float peso) {
		this.peso = peso;
		System.out.println(peso);
		//semaphore.release();
		//System.out.println("Termina el pesaje. Básculas disponibles: "+semaphore.availablePermits());
	}
	
	public void clear() {
		this.peso = 0.0F;
		//try {
		//	semaphore.acquire();
		//} catch (InterruptedException e) {
		//	e.printStackTrace();
		//}
		//System.out.println("Realizando pesaje. Básculas disponibles: "+semaphore.availablePermits());
	}
}
