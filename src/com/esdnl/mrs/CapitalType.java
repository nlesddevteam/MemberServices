package com.esdnl.mrs;

import java.io.Serializable;

public class CapitalType implements Serializable {

	private static final long serialVersionUID = 8353462887334343616L;

	private String type_id;

	public CapitalType(String type_id) {

		this.type_id = type_id.toUpperCase();
	}

	public String getCapitalTypeID() {

		return this.type_id;
	}
}