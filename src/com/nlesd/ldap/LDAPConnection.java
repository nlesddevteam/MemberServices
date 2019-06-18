package com.nlesd.ldap;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LDAPConnection {

	private LDAPServer server;

	public LDAPConnection(LDAPServer server) {

		this.server = server;
	}

	public LDAPSearchResult authenticate(String uid, String password)
			throws AuthenticationNotSupportedException,
				AuthenticationException,
				NamingException {

		LDAPSearchResult lsr = search(uid);

		if (lsr != null) {
			System.out.println("LDAP Search Result: " + lsr);

			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, server.getURL());
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, lsr.getDistinctName());
			env.put(Context.SECURITY_CREDENTIALS, password);

			DirContext ctx = new InitialDirContext(env);

			//authenticated !!
			System.out.println(lsr.getCommonName() + " Authenticated!!");

			ctx.close();
		}
		else {
			throw new AuthenticationException("User ID and/or password is incorrect.");
		}

		return lsr;

	}

	public LDAPSearchResult search(String uid) throws NamingException {

		LDAPSearchResult lsr = null;

		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, server.getURL());
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, server.getAdminDN());
		env.put(Context.SECURITY_CREDENTIALS, server.getAdminPwd());

		DirContext ctx = new InitialDirContext(env);

		SearchControls ctls = new SearchControls();
		ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		ctls.setReturningAttributes(new String[] {
				"dn", "userid", "mail", "cn"
		});

		// Search for objects that have those matching attributes
		NamingEnumeration answer = ctx.search(server.getRootDN(), "(uid=" + uid + ")", ctls);

		if (answer.hasMore()) {
			SearchResult sr = (SearchResult) answer.next();

			System.out.println(sr.toString());

			lsr = createLDAPSearchResult(sr);
		}

		ctx.close();

		return lsr;
	}

	private LDAPSearchResult createLDAPSearchResult(SearchResult sr) throws NamingException {

		LDAPSearchResult lsr = null;
		Attributes attrs = sr.getAttributes();

		if (attrs.size() > 0) {
			lsr = new LDAPSearchResult();

			lsr.setCommonName(attrs.get("cn").get().toString());
			lsr.setDistinctName(attrs.get("dn").get().toString());
			lsr.setMail(attrs.get("mail").get().toString());
			lsr.setUserId(attrs.get("userid").get().toString());
		}

		return lsr;
	}
}
