package com.awsd.weather;

public class ClosureStatusException extends WeatherCentralException {

	private static final long serialVersionUID = -3860887356896167541L;

	private int status_id;

	public ClosureStatusException(int status_id, String msg) {

		super(msg);
		this.status_id = status_id;
	}

	public ClosureStatusException(String msg) {

		super(msg);
		this.status_id = -1;
	}

	public int getClosureStatus() {

		return this.status_id;
	}
}