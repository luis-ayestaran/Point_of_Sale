package com.mexicacode.pofs.printer;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class Config {

	public Config() {}
	
	public boolean printcard(final String bill) {
		
		PrinterJob job  = PrinterJob.getPrinterJob();
		Printable contentToPrint = new Printable() {
		
		public int print(Graphics graphics, PageFormat pageformat, int pageIndex) throws PrinterException{
			
			Graphics2D g2d = (Graphics2D) graphics; 
			g2d.translate(pageformat.getImageableX() -5, pageformat.getImageableY());
			g2d.setFont(new Font("Monospaced", Font.BOLD, 7));
			

			String[] billz = bill.split(";");
			        int y = 15;  
			        //draw each String in a separate line
			        for (int i = 0; i < billz.length; i++) {
			            graphics.drawString(billz[i], 5, y);
			            y = y + 15;
			        }

			       if (pageIndex >0){return NO_SUCH_PAGE;} //Only one page
			 

			    
			return PAGE_EXISTS;
			
		
		}
		};
	
		PageFormat pageFormat = new PageFormat();
		pageFormat.setOrientation(PageFormat.PORTRAIT);
		Paper pPaper = pageFormat.getPaper();
		pPaper.setImageableArea(0, 0, pPaper.getWidth() , pPaper.getHeight() -2);
		pageFormat.setPaper(pPaper);

		job.setPrintable(contentToPrint, pageFormat);
	
		try {
	      job.print();
		} catch (PrinterException e) {
			System.err.println(e.getMessage());
		}
	    return true;
	}
	
}
