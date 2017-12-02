package com.tendercare.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/***
 * 
 * @author Imen Gharsalli
 *
 */
public class DateUtility {
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss+SSSS");
	public static LocalDate dateToday = LocalDate.now(ZoneId.of("Europe/Berlin"));

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(dateTimeNow());
	}

	/***
	 * 
	 * @return
	 */
	public static String dateTimeNow() {
		LocalTime timeNow = LocalTime.now();
		return LocalDateTime.of(dateToday, timeNow).format(formatter);

	}

	/***
	 * 
	 * @return
	 */
	private static LocalDateTime midnightTodayLDT() {
		LocalTime midnight = LocalTime.MIDNIGHT;
		return LocalDateTime.of(dateToday, midnight);
	}

	/***
	 * 
	 * @return
	 */
	public static String midnightToday() {
		return midnightTodayLDT().format(formatter);
	}

	/***
	 * 
	 * @return
	 */
	public static String midnightYesterday() {
		LocalDateTime yesterdayMidnight = midnightTodayLDT().minusDays(1);
		return yesterdayMidnight.format(formatter);

	}
}
