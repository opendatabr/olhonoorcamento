/* Copyright 2013 de Kellyton Brito. Este arquivo é parte 
* do programa MeuCongressoNacional.com . O MeuCongressoNacional.com 
* é um software livre; você pode redistribuí-lo e/ou modificá-lo 
* dentro dos termos da GNU Affero General Public License como 
* publicada pela Fundação do Software Livre (FSF) na versão 3 
* da Licença. Este programa é distribuído na esperança que possa 
* ser útil, mas SEM NENHUMA GARANTIA; sem uma garantia implícita 
* de ADEQUAÇÃO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. Veja 
* a licença para maiores detalhes, disponível em 
* meucongressonacional.com/license. Você deve ter recebido uma cópia 
* da GNU Affero General Public License, sob o título "LICENCA.txt", 
* junto com este programa, se não, acesse http://www.gnu.org/licenses/
**/

package models.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

	public static String getFormattedDate(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}
	
	public static String getFormattedDateTime(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
	}

	/**
	 * Parse from dd/MM/yyyy and from d/m/yyyy, dd/m/yyyy and d/mm/yyyy
	 * @param str
	 * @return
	 */
	public static Date parseToDate(String str) {
		if (str.charAt(1) == '/'){//se o dia só tem 1 caracter
			str = "0" + str;
		}
		if (str.charAt(4) == '/'){//se o mes só tem 1 caracter
			str = str.substring(0, 3) + "0" + str.substring(3); 
		}
		
		TimeZone GMT = TimeZone.getTimeZone("GMT-03:00");
		
		try {
			int day = Integer.parseInt(str.substring(0, 2));
			int month = Integer.parseInt(str.substring(3, 5)) - 1;
			int year = Integer.parseInt(str.substring(6, 10));
			
			Calendar dateTime = new GregorianCalendar(GMT);
			dateTime.set(year, month, day, 0, 0, 0);
			dateTime.set(Calendar.MILLISECOND, 0);
			return dateTime.getTime();

		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Parse from yyyy-mm-dd
	 * @param str
	 * @return
	 */
	public static Date parseToDateSenado(String str) {
		
		TimeZone GMT = TimeZone.getTimeZone("GMT-03:00");
		
		try {
			int day = Integer.parseInt(str.substring(8, 10));
			int month = Integer.parseInt(str.substring(5, 7)) - 1;
			int year = Integer.parseInt(str.substring(0, 4));
			
			Calendar dateTime = new GregorianCalendar(GMT);
			dateTime.set(year, month, day, 0, 0, 0);
			dateTime.set(Calendar.MILLISECOND, 0);
			return dateTime.getTime();

		} catch (StringIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/**
	 * Parse from dd-MMM-yyyy
	 * @param str
	 * @return
	 */
	public static Date parseToDateCandidato(String str) {
		try {
			return new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH).parse(str);

		} catch (Exception e) {
			return null;
		}
	}

	// De 18/07/2015 19:21 para uma data
	public static Date parseToDateCompleta(String str) {
		if (str.charAt(1) == '/'){//se o dia só tem 1 caracter
			str = "0" + str;
		}
		if (str.charAt(4) == '/'){//se o mes só tem 1 caracter
			str = str.substring(0, 3) + "0" + str.substring(3); 
		}
		
		TimeZone GMT = TimeZone.getTimeZone("GMT-03:00");
		
		try {
			int day = Integer.parseInt(str.substring(0, 2));
			int month = Integer.parseInt(str.substring(3, 5)) - 1;
			int year = Integer.parseInt(str.substring(6, 10));
			
			int hour = Integer.parseInt(str.substring(11, 13));
			int minute = Integer.parseInt(str.substring(14, 16)); 
			
			Calendar dateTime = new GregorianCalendar(GMT);
			dateTime.set(year, month, day, hour, minute, 0);
			dateTime.set(Calendar.MILLISECOND, 0);
			return dateTime.getTime();

		} catch (StringIndexOutOfBoundsException e) {
			return null;
		}
	}

}

