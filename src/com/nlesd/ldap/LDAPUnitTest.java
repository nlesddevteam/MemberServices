package com.nlesd.ldap;

import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.NamingException;

public class LDAPUnitTest {

	public static void main(String args[]) {

		try {
			LDAPConnection ldap = new LDAPConnection(LDAPServer.ARCHIVE);

			ldap.authenticate("testuser", "testuser");
		}
		catch (AuthenticationNotSupportedException ex) {
			System.out.println("The authentication is not supported by the server");
		}
		catch (AuthenticationException ex) {
			System.out.println("incorrect password or username");
		}
		catch (NamingException ex) {
			System.out.println("error when trying to create the context");
		}
	}
}
