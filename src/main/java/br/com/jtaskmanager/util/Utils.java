package br.com.jtaskmanager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;

public class Utils {

	public static boolean isEmpty(String value) {
		return null == value || 0 == value.length();
	}

	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

	public static boolean isNotNull(Date value) {
		return null != value;
	}

	public static boolean isStrDateValid(String value) {
		try {
			var dtFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
			LocalDate.parse(value, dtFormat);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Date strToDate(String value) throws ParseException {
		var format = new SimpleDateFormat("dd/MM/yyyy");
		return format.parse(value);
	}

	public static String dateToStr(Date value) {
		var format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(value);
	}

	public static Long dateToLong(Date value) {
		return value.getTime();
	}

	public static Date longToDate(long value) {
		return new Date(value);
	}
}
