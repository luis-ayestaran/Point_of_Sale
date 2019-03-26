package com.mexicacode.pofs.scale.serialcommunication;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class SerialPortWriter implements Runnable{

	private Boolean flag = Boolean.TRUE;
	private OutputStream out;
	private Scanner teclado;
	private Bascula bascula;
	private String string;
	
	public SerialPortWriter(OutputStream out) {
		this.string = "";
		this.out = out;
		this.teclado = new Scanner(System.in);
		System.out.println("Inicia Writer...");
	}
	
	public void setString(String string) {
		synchronized(this)
		{
			this.string = string;
			notify();
		}
	}
	
	public void run() {
		synchronized(this)
		{
			try {
				while(flag) {
					wait();
					System.out.println("Envie comando al puerto serie >>>>>> ");
					String command = this.string;//teclado.nextLine();
					System.out.println("comando "+command);
					if("termina".equals(command))
						this.termina();
					else {
						try {
							out.write(Integer.parseInt(command));
							string = "";
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void termina() {
		this.flag = Boolean.FALSE;
	}
	
	public void putMessage(Integer signal) {
		
	}

}
