package com.nlesd.ldap;

public class LDAPSearchResult {

	private String distinctName;
	private String userId;
	private String mail;
	private String commonName;

	protected LDAPSearchResult() {

	}

	public void setDistinctName(String distinctName) {

		this.distinctName = distinctName;
	}

	public String getDistinctName() {

		return distinctName;
	}

	public void setUserId(String userId) {

		this.userId = userId;
	}

	public String getUserId() {

		return userId;
	}

	public void setMail(String mail) {

		this.mail = mail;
	}

	public String getMail() {

		return mail;
	}

	public void setCommonName(String commonName) {

		this.commonName = commonName;
	}

	public String getCommonName() {

		return commonName;
	}

	public String toString() {

		String str = "[UID:" + this.getUserId() + ", CN:" + this.getCommonName() + ", DN:" + this.getDistinctName()
				+ ", MAIL:" + this.getMail() + "]";

		return str;
	}

}
