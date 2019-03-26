package com.mexicacode.pofs.scale.serialcommunication;

import java.io.IOException;
import java.io.InputStream;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class SerialPortReader implements SerialPortEventListener{

	private InputStream in;
    private byte[] buffer = new byte[1024];
    private Bascula bascula;
    
    public Float returnPeso() {
    	return bascula.getPeso();
    }
    
    public SerialPortReader ( InputStream in, Bascula bascula)
    {
        this.in = in;
        this.bascula = bascula;
        System.out.println("Inicia Reader");
    }
    
    public void serialEvent(SerialPortEvent arg0) {
        int data;
        try
        {
            int len = 0;
            while ( ( data = in.read()) > -1 )
            {
                if ( data == '\n' ) {
                    break;
                }
                buffer[len++] = (byte) data;
            }
            String strPeso = new String(buffer,0,len);
            System.out.print("Puerto Serie RETORNA <<<<<<  " + strPeso);
            bascula.clear();
            System.out.println(bascula.getPeso());
            Float peso = getWeight(strPeso);
            bascula.setPeso(peso);
            System.out.println(bascula.getPeso());
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            System.exit(-1);
        }             
    }
    
    public Float getWeight(String string) {
    	Float weight = 0.0F;
    	StringBuilder sb = new StringBuilder();
    	for(int i=0;i<string.length();i++)
    	{
    		switch(string.charAt(i))
    		{
    			case ' ':
    			case 'k':
    			case 'g': break;
    			default: sb.append(string.charAt(i));
    		}
    	}
    	String peso = sb.toString();
    	weight = Float.parseFloat(peso);
    	return weight;
    }
}
