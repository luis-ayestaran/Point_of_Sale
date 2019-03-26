package com.mexicacode.pofs.scale.demoapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import com.mexicacode.pofs.scale.exceptions.PortInUse;
import com.mexicacode.pofs.scale.serialcommunication.Bascula;
import com.mexicacode.pofs.scale.serialcommunication.SerialPortConnection;
import com.mexicacode.pofs.scale.serialcommunication.SerialPortReader;
import com.mexicacode.pofs.scale.serialcommunication.SerialPortWriter;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class DemoApp {

	private SerialPort serialPort;
	private SerialPortReader spr;
	private SerialPortWriter spw;
	private Bascula bascula;
	
	public static void main(String[] args) {
		DemoApp demoApp = new DemoApp();
		demoApp.init();
	}
	
	public Bascula getBascula() {
    	return bascula;
    }
	
	public void init() {
		try {
			SerialPortConnection serialPortConnection = new SerialPortConnection();
			serialPortConnection.connection("COM3");
			serialPort = serialPortConnection.getSerialPort();
			OutputStream out = serialPort.getOutputStream();
			InputStream in = serialPort.getInputStream();
			
			spw = new SerialPortWriter(out);
			Thread writerThread = new Thread(spw);
			writerThread.start();
			
			bascula = new Bascula();
			spr = new SerialPortReader(in, bascula);
			serialPort.addEventListener(spr);
			serialPort.notifyOnDataAvailable(true);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortInUse e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
