package com.esdnl.mrs;

import java.io.Serializable;

public class Vendor implements Serializable {

	private static final long serialVersionUID = 3816355650760557227L;

	private int vendor_id;
	private String vendor_name;

	public Vendor(int vendor_id, String vendor_name) {

		this.vendor_id = vendor_id;
		this.vendor_name = vendor_name;
	}

	public Vendor(String vendor_name) {

		this(-1, vendor_name);
	}

	public int getVendorID() {

		return this.vendor_id;
	}

	public String getVendorName() {

		return this.vendor_name;
	}

	public String toString() {

		return this.getVendorName();
	}
}