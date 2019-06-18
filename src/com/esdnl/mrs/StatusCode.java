package com.esdnl.mrs;

import java.io.Serializable;

public class StatusCode implements Serializable {

	private static final long serialVersionUID = 6606044176028072487L;

	private String code_id;

	public StatusCode(String code_id) {

		this.code_id = code_id.toUpperCase();
	}

	public String getStatusCodeID() {

		return this.code_id;
	}

	public boolean equals(Object obj) {

		return ((obj != null) && (obj instanceof StatusCode) && ((StatusCode) obj).getStatusCodeID().equals(
				this.getStatusCodeID()));
	}
}