package com.esdnl.mrs;

import java.io.Serializable;

public class RequestCategory implements Serializable {

	private static final long serialVersionUID = -5511229822043099451L;

	private String type_id;

	public RequestCategory(String type_id) {

		this.type_id = type_id.toUpperCase();
	}

	public String getRequestCategoryID() {

		return this.type_id;
	}
}