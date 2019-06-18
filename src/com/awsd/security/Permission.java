package com.awsd.security;

import java.io.Serializable;

public class Permission implements Serializable

{

	private static final long serialVersionUID = 7901693691464999298L;
	private String uid;
	private String desc;

	public Permission(String uid, String desc) {

		this.uid = uid;
		this.desc = desc;
	}

	public String getPermissionUID() {

		return this.uid;
	}

	public String getPermissionDescription() {

		return this.desc;
	}
}