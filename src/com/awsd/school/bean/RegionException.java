package com.awsd.school.bean;

public class RegionException extends Exception {

	private static final long serialVersionUID = 6963542685211420524L;

	public RegionException(String msg) {

		super(msg);
	}

	public RegionException(String msg, Throwable cause) {

		super(msg, cause);
	}

	public RegionException(Throwable cause) {

		super(cause);
	}
}