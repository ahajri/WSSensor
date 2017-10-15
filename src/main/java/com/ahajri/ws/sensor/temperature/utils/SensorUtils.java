package com.ahajri.ws.sensor.temperature.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ahajri.ws.sensor.temperature.bean.NouveauReleve;
import com.ahajri.ws.sensor.temperature.exception.RestError;

/**
 * Classe utlitaires pour Sonde Temperature
 * 
 * @author ahajri
 * @version 1
 */
public final class SensorUtils {


	/**
	 * creation du Row Key Pattern = {AdresseMacduSonde}_{DateduJour en format
	 * YYYYMMDD}
	 * 
	 * @param nouveauReleve
	 * @return Clé ligne
	 * @throws ParseException
	 */
	public static String createCleLigne(NouveauReleve nouveauReleve) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(Long.parseLong(nouveauReleve.getTimestamp())));
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (month.length() == 1) {
			month = "0" + month;
		}
		String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		if (day.length() == 1) {
			day = "0" + day;
		}
		return nouveauReleve.getHwaddr() + WsConstants.CLE_LIGNE_SEPARATOR + year + month + day;
	}

	/**
	 * temperature_9223370548580305807
	 * 
	 * @param model
	 *            Model à convertir
	 * @return {@link NavigableMap}
	 *
	 * @throws ParseException
	 */
//	public static NavigableMap<String, HBaseCelluleEntity> convertToNavigableMap(NouveauReleve model)
//			throws ParseException {
//		NavigableMap<String, HBaseCelluleEntity> map = new TreeMap<>();
//		// Get Timestamp
//
//		long javaLongTime = Long.parseLong(model.getTimestamp());
//
//		Date timestamp = new Date(javaLongTime);
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(timestamp);
//		String hourOfDay = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
//		if (hourOfDay.length() == 1) {
//			hourOfDay = "0" + hourOfDay;
//		}
//		String minute = String.valueOf(cal.get(Calendar.MINUTE));
//		if (minute.length() == 1) {
//			minute = "0" + minute;
//		}
//		String second = String.valueOf(cal.get(Calendar.SECOND));
//		if (second.length() == 1) {
//			second = "0" + second;
//		}
//
//		// get Time
//		String timeKey = hourOfDay + minute + second;
//		// Reverse Timestamps: permet de récupérer la première insertion lorsque
//		// on fait un scan vue que les clés sont triés
//		map.put("temperature_" + timeKey,
//				new HBaseCelluleEntity(model.getTemperature(), Long.parseLong(model.getTimestamp())));
//		return map;
//	}

	/**
	 * 
	 * @param resourceID
	 * @param date
	 *            :long format
	 * @return row Key
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public static String getRowKey(String resourceID, String date) throws NumberFormatException, ParseException {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		c.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6, 8)));
		c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(date.substring(8, 10)));
		c.set(Calendar.MINUTE, Integer.parseInt(date.substring(10, 12)));
		c.set(Calendar.SECOND, Integer.parseInt(date.substring(12, 14)));
		Date timestamp = c.getTime();// sdf.parse(date);
		SimpleDateFormat sdfKey = new SimpleDateFormat(WsConstants.DATE_KEY_PATTERN);
		return resourceID + WsConstants.CLE_LIGNE_SEPARATOR + sdfKey.format(timestamp);
	}

	/**
	 * get cell id from date
	 * 
	 * @param date
	 * @return Temperature_{time as HHmmss}
	 * @throws ParseException
	 */
	public static String getCellKey(String date) throws NumberFormatException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(WsConstants.DATE_CELL_KEY_PATTERN);
		Date timestamp = sdf.parse(date);
		SimpleDateFormat sdfKey = new SimpleDateFormat(WsConstants.TIME_KEY_PATTERN);
		return "temperature_" + sdfKey.format(timestamp);
	}

	/**
	 * 
	 * @param semaine
	 *            semaine format yyyyww
	 * @param dayOfWeek
	 *            jour du semaine (1 à 7)
	 * @return date
	 * @throws ParseException
	 */
//	public static Date getDayFromWeek(final String semaine, final int dayOfWeek) throws ParseException {
//		Calendar cal = DateUtils.toCalendar(semaine, "yyyyww");
//		while (cal.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
//			cal.add(Calendar.DATE, 1);
//		}
//		return cal.getTime();
//	}

	/**
	 * cherche le premier Lundi dans la semaine d'une date donnée
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getFirstDayOfWeek(final Date date) throws ParseException {

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

		return cal.getTime();
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getLastDayOfWeek(final Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 59);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		int index = 1;
		while (index < 7) {
			cal.add(Calendar.DATE, 1);
			index++;
		}
		return cal.getTime();
	}

	/**
	 * 
	 * @param date
	 * @return Long format {yyyyMMddHHmmss}
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public static long getCompareTime(String date) throws NumberFormatException, ParseException {
		SimpleDateFormat sdfKey = new SimpleDateFormat(WsConstants.DATE_CELL_KEY_PATTERN);
		Date d = sdfKey.parse(date);
		SimpleDateFormat sdfDateTime = new SimpleDateFormat(WsConstants.TIME_KEY_PATTERN);
		String formatted = sdfDateTime.format(d);
		if (formatted.length() < 6) {
			for (int i = 0; i < 6 - formatted.length(); i++) {
				formatted = "0" + formatted;
			}
		}
		return Long.valueOf(formatted);
	}

	/**
	 * 
	 * @param date
	 * @return long value of date format yyyyMMddHHmmss
	 */
	public static long getCompareDateTime(String date) {
		return Long.valueOf(date);
	}
	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern){
		return new SimpleDateFormat(pattern).format(date);
	}
	/**
	 * 
	 * @param statusCode
	 * @param code
	 * @param msg
	 * @param devMsg
	 * @param moreInfo
	 * @return {@link RestError}
	 */
	public static  RestError response(int statusCode, String code, String msg, String devMsg, String moreInfo) {
		return new RestError(statusCode, code, msg, devMsg, moreInfo);
	}
}
