package com.mexicacode.pofs.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeService {
	private Date now;
	
	public TimeService() {
		now = new Date();
	}
	
	public Date getCurrentDate() {
		return now;
	}
	
	public String getDate() {
		String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String mysqlDate = formatter.format(now);
		return mysqlDate;
	}
	
	public Date stringToDate(String string) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(string);
			System.out.println(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public String dateToString(Date date) {
		String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String mysqlDate = formatter.format(date);
		return mysqlDate;
	}
	
	public static void main(String args[]) {
		TimeService ts = new TimeService();
		System.out.println("Date "+ts.getDate());
		System.out.println(ts.dateToString(ts.getCurrentDate()));
	}
}
