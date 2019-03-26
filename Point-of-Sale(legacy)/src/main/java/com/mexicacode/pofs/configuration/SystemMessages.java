package com.mexicacode.pofs.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.mexicacode.pofs.exceptions.SystemConfigException;

/**
 * Esta clase permite acceder al archivo properties que contiene 
 * los mensajes del sistema.
 * 
 * @author Ricardo Mangoré <ricardomangore@gmail.com>
 * @version 0.0.1
 */
public class SystemMessages {

	private static InputStream is;
	private static Properties properties;
		
	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		is = classLoader.getResourceAsStream("messages.properties");
		properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		
	}
	
	public static String getMessage(String key) throws Exception {
		String message = null;
		message = properties.getProperty(key);
		if(message == null)
			throw new SystemConfigException("The property <"+ key +"> was not foud");
		return properties.getProperty(key);
	}
	
}
