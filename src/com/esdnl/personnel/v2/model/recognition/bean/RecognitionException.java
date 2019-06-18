package com.esdnl.personnel.v2.model.recognition.bean;

public class RecognitionException extends Exception {

	private static final long serialVersionUID = 3173054268565094136L;

	public RecognitionException(String msg) {

		super(msg);
	}

	public RecognitionException(String msg, Throwable e) {

		super(msg, e);
	}
}