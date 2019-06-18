package com.esdnl.servlet;

public class FormElementPattern {

	public static final String EMAIL_PATTERN = "^[\\w.\\-]+@[\\w\\-]+\\.[a-zA-Z0-9]+$";
	public static final String PHONE_PATTERN = "^(\\d{3})[\\s.-]?(\\d{3})[.-]?(\\d{4})$";
	public static final String DATE_PATTERN = "^\\d{2}/\\d{2}/\\d{4}$";
	public static final String SHORT_DATE_PATTERN = "^\\d{2}/\\d{4}$";
	public static final String CURRENCY_PATTERN = "^\\d+[.[0-9]?[0-9]?]?$";
	public static final String INTEGER_PATTERN = "^\\d+$";
	public static final String TWO_DECIMALS_PATTERN = "^-?\\d+.[0-9][0-9]$";
	public static final String DATE_TIME_PATTERN = "^\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}\\s?(?i)(am|pm)$";
}