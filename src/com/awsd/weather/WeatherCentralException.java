package com.awsd.weather;

import javax.servlet.ServletException;

public class WeatherCentralException extends ServletException {

	private static final long serialVersionUID = -2246138704629710144L;

	public WeatherCentralException(String msg) {

		super(msg);
	}
}