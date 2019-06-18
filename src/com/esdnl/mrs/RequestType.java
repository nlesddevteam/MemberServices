package com.esdnl.mrs;

import java.io.Serializable;

public class RequestType implements Serializable {

	private static final long serialVersionUID = 1409574056242348548L;

	private String type_id;

	public RequestType(String type_id) {

		this.type_id = type_id.toUpperCase();
	}

	public String getRequestTypeID() {

		return this.type_id;
	}

	public boolean equals(Object obj) {

		boolean check = false;

		if (obj instanceof RequestType && (obj != null)
				&& (((RequestType) obj).getRequestTypeID().equals(this.getRequestTypeID())))
			check = true;

		return check;
	}

	public String toString() {

		return this.type_id;
	}
}