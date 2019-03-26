package com.mexicacode.pofs.scale.serialcommunication;

import com.mexicacode.pofs.scale.exceptions.PortInUse;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class SerialPortConnection {

	private int bitPerSecond;
	private int dataBits;
	private int parity;
	private int stopBits;
	private CommPortIdentifier portIdentifier;
	private SerialPort serialPort;
	
	public SerialPortConnection() {
		this.bitPerSecond = 9600;
		this.dataBits = SerialPort.DATABITS_8;
		this.parity = SerialPort.PARITY_NONE;
		this.stopBits = SerialPort.STOPBITS_1;
	}
	
	
	public SerialPortConnection(Integer bitPerSecond,Integer dataBits,Integer parity, Integer stopBits) {
		this.bitPerSecond = bitPerSecond;
		this.dataBits = dataBits;
		this.parity = parity;
		this.stopBits = stopBits;
	}
	
	public void connection(String portName) throws NoSuchPortException, PortInUse, PortInUseException, UnsupportedCommOperationException {
		portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if(portIdentifier.isCurrentlyOwned()) {
			throw new PortInUse();
		}else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
			if(commPort instanceof SerialPort) {
				serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(this.bitPerSecond,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
			}
		}
	}
	
	public void configureConnection(Integer bitPerSecond,Integer dataBits,Integer parity, Integer stopBits) {
		this.bitPerSecond = bitPerSecond;
		this.dataBits = dataBits;
		this.parity = parity;
		this.stopBits = stopBits;
	}
	
	public void closeConnection() {
		
	}
	
	public CommPortIdentifier getPortIdentifier() {
		return this.portIdentifier;
	}
	
	public SerialPort getSerialPort() {
		return this.serialPort;
	}
	
	
}
