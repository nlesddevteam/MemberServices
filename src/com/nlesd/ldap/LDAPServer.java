package com.nlesd.ldap;

public enum LDAPServer {

	TEST("10.5.0.237", 333, "dc=nlesd,dc=ca", "uid=luser", "luser"), PRODUCTION("10.5.0.237", 333, "dc=nlesd,dc=ca",
			"uid=luser", "luser"), ARCHIVE("FCDSArchive", 444, "ou=province", "uid=admin", "Zulu*1106");

	private String host;
	private int port;
	private String rootDN;
	private String adminDN;
	private String adminPwd;

	private LDAPServer(String host, int port, String rootDN, String adminDN, String adminPwd) {

		this.host = host;
		this.port = port;
		this.rootDN = rootDN;
		this.adminDN = adminDN;
		this.adminPwd = adminPwd;
	}

	public String getHost() {

		return host;
	}

	public int getPort() {

		return port;
	}

	public String getURL() {

		return "ldap://" + this.getHost() + ":" + this.getPort() + "/";
	}

	public String getRootDN() {

		return rootDN;
	}

	public String getAdminDN() {

		return adminDN + "," + rootDN;
	}

	public String getAdminPwd() {

		return adminPwd;
	}

}
