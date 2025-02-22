package libreria;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

public class Fecha {

	public static String fecha() {
		int ds;
		String dia;
		Calendar c = new GregorianCalendar();
		ds = c.get(Calendar.DAY_OF_WEEK);
		switch (ds) {
			case 1: dia = "Domingo"; break;
			case 2: dia = "Lunes"; break;
			case 3: dia = "Martes"; break;
			case 4: dia = "Miercoles"; break;
			case 5: dia = "Jueves"; break;
			case 6: dia = "Viernes"; break;
			default: dia = "Sabado"; break;
		}
		return dia + ", " + enTextoFecha(fechaActual());
	}
	public static String fechaActual() {
		int dd, mm, aa;
		Calendar c = new GregorianCalendar();
		dd = c.get(Calendar.DAY_OF_MONTH);
		mm = c.get(Calendar.MONTH) + 1;
		aa = c.get(Calendar.YEAR);
		return ajustar(dd) + "/" + ajustar(mm) + "/" + aa;
	}
	public static String horaActual() {
		int hh, mm, ss;
		Calendar c = new GregorianCalendar();
		hh = c.get(Calendar.HOUR_OF_DAY);
		mm = c.get(Calendar.MINUTE);
		ss = c.get(Calendar.SECOND);
		return ajustar(hh) + ":" + ajustar(mm) + ":" + ajustar(ss);
	}
	public static String enTextoFecha(String fecha) {
		String[] s = fecha.split("/");
		if (s.length == 3)
			return s[0] + " de " + obtenerMes(s[1]) + " de " + s[2];
		else
			return "";
	}
	public static String obtenerMes(String mes) {
		switch (mes) {
			case "01": return "Enero";
			case "02": return "Febrero";
			case "03": return "Marzo";
			case "04": return "Abril";
			case "05": return "Mayo";
			case "06": return "Junio";
			case "07": return "Julio";
			case "08": return "Agosto";
			case "09": return "Setiembre";
			case "10": return "Octubre";
			case "11": return "Noviembre";
			default: return "Diciembre";
		}
	}
	public static int diasTranscurridos(String fechaInicial, String fechaFinal) {
		try {
			SimpleDateFormat adt = new SimpleDateFormat("dd/MM/yyyy");
			Date fi = adt.parse(fechaInicial),
			     ff = adt.parse(fechaFinal);
			return (int) ((ff.getTime() - fi.getTime()) / 86400000);
		}
		catch (Exception e) {
			return 0;
		}
	}
	public static String ajustar(int numero) {
		return String.format("%02d", numero);
	}
	
}