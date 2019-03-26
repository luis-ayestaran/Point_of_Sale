package com.mexicacode.pofs.printer;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Vector;

public class printer {
	
	private Config printer = new Config();
	private GregorianCalendar gg = new GregorianCalendar();
	private SimpleDateFormat dd = new SimpleDateFormat("dd/MM/YYYY");
	private SimpleDateFormat ddd = new SimpleDateFormat("HH:mm");
 
	public printer() {}
	
	public void print(Vector<Vector> productos, String subtotal, String descuento, String total, String pago, String cambio) {
		String Header =
	             "       .:: DON JOEL ::.       ;"
	            +"****** Ticket de Compra ******;"
	            + "Fecha:"+dd.format(gg.getTime())+"	 	 	 	 Hora:"+ddd.format(gg.getTime())+"\n;"
	            + "      Lista de Productos      \n;"
	            + "------------------------------\n;"
	            + "Descripción del producto  \n;"
	            + "Artic. C. Unit.	 Pzas.  Costo \n;"
	            + "------------------------------\n;";
		String a = "";
				for(int i=0;i<productos.size();i++)
				{
					for(int j=0;j<productos.get(i).size(); j++)
					{
						a = a+productos.get(i).get(j)+"  ";
					}
					a = a+"\n;";
				}
		String h = Header+a;
		String amt  =
	             "------------------------------\n;"
	             + "Subtotal:              $"+subtotal+"\n;"
	             + "Descuento:             $"+descuento+"\n;"
	             + "Total:                 $"+total+"\n;"
	             + "Efectivo:              $"+pago+"\n;"
	             + "Cambio:                $"+cambio+"\n;"
	             + "******************************\n;"
	             + "          .:: POS ::.         \n;"
	             + "  ¡El mejor Punto De Venta!   \n;"
	             + "    Whatsapp 244-137-0768     \n;"
	             + "******************************\n;"
	             + "   ¡Gracias por su compra!    \n;"
	             + "______________________________\n;";
		String bill = h+amt;
		printer.printcard(bill);
		System.out.println(bill);
		
	}
}
