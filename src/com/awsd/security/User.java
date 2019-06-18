package com.awsd.security;

import java.io.Serializable;
import java.sql.SQLException;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;

public class User implements Serializable {

	private static final long serialVersionUID = 1736354707739886478L;
	private String username;
	private String password;
	private Personnel prec;
	private UserPermissions permissions;
	private UserRoles roles;
	private boolean registered;
	private boolean valid;

	public User() {

		this.username = null;
		this.password = null;
		this.prec = null;
		this.permissions = null;
		this.roles = null;
		this.registered = false;
		this.valid = true;
	}

	public User(String username, String password) {

		this.username = username.toLowerCase();
		this.password = password;

		try {
			this.prec = PersonnelDB.getPersonnel(this.username);
			this.prec.setPassword(this.password);
			this.permissions = null;
			this.roles = null;

			//no errors, user found.
			this.registered = true;
			this.valid = true;
		}
		catch (PersonnelException e) {
			System.err.println(e.getMessage());

			//no errors, unregistered user.
			this.registered = false;
			this.valid = true;

			this.permissions = null;
			this.roles = null;
			this.prec = null;
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());

			//errors found.
			this.registered = false;
			this.valid = false;

			this.permissions = null;
			this.roles = null;
			this.prec = null;
		}
	}

	public User(String email) {

		this.username = email;

		try {
			this.prec = PersonnelDB.getPersonnelByEmail(email);
			this.permissions = null;
			this.roles = null;

			//no errors, user found.
			this.registered = true;
			this.valid = true;
		}
		catch (PersonnelException e) {
			System.err.println(e.getMessage());

			//no errors, unregistered user.
			this.registered = false;
			this.valid = true;

			this.permissions = null;
			this.roles = null;
			this.prec = null;
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());

			//errors found.
			this.registered = false;
			this.valid = false;

			this.permissions = null;
			this.roles = null;
			this.prec = null;
		}
	}

	public String getUsername() {

		return username;
	}

	public String getPassword() {

		return password;
	}

	public Personnel getPersonnel() {

		return prec;
	}

	public void setPersonnel(Personnel prec) throws PersonnelException {

		this.prec = prec;
	}

	public UserPermissions getUserPermissions() throws SecurityException {

		if (permissions == null) {
			try {
				permissions = new UserPermissions(this);
			}
			catch (PermissionException e) {
				System.err.println(e);
				e.printStackTrace(System.err);
				throw new SecurityException(username, "Could not find any permissions.");
			}
		}

		return this.permissions;
	}

	public boolean checkPermission(String permission) {

		boolean check = false;

		try {
			check = getUserPermissions().containsKey(permission);
		}
		catch (Exception e) {
			check = false;
		}

		return check;
	}

	public UserRoles getUserRoles() throws SecurityException {

		if (roles == null) {
			try {
				roles = new UserRoles(prec);
			}
			catch (RoleException e) {
				System.err.println(e);
				e.printStackTrace(System.err);
				throw new SecurityException(username, "Could not find any roles.");
			}
			catch (PermissionException e) {
				System.err.println(e);
				e.printStackTrace(System.err);
				throw new SecurityException(username, "Could not find any roles.");
			}
			catch (PersonnelException e) {
				System.err.println(e);
				e.printStackTrace(System.err);
				throw new SecurityException(username, "Could not find any roles.");
			}
		}

		return this.roles;
	}

	public boolean checkRole(String role) {

		boolean check = false;
		try {
			check = getUserRoles().containsKey(role);
		}
		catch (SecurityException e) {
			check = false;
		}

		return check;
	}

	public String getLotusUserFullName() {

		if (prec != null)
			return this.getPersonnel().getFullName();
		else
			return "UNKNOWN";
	}

	public String getLotusUserFullNameReverse() {

		if (prec != null)
			return this.getPersonnel().getFullNameReverse();
		else
			return "UNKNOWN";
	}

	public boolean isRegistered() {

		return registered;
	}

	public boolean isValid() {

		return valid;
	}

	public void loggedOn() {

		try {
			PersonnelDB.userLoggedOn(this.prec);
		}
		catch (PersonnelException e) {
			e.printStackTrace();
		}
	}

	/*************************/
	/** CONVENIENCE METHODS **/
	/*************************/
	public boolean isAdmin() {

		return checkRole("ADMINISTRATOR");
	}

	public static User authenticate(String username, String password) throws SecurityException {

		User usr = null;
		try {
			Personnel p = PersonnelDB.getPersonnel(username);

			/**
			 * WE HAVE A PERSONNEL RECORD CHECK PASSWORD FOR AUTHENTICATION
			 */

			if (p.getPassword().equals(password)) {
				usr = new User();
				usr.username = username;
				usr.password = password;
				usr.prec = p;

				//no errors, user found.
				usr.registered = true;
				usr.valid = true;
			}
			else
				throw new SecurityException("Invalid Credentials");
		}
		catch (PersonnelException e) {
			usr = new User();
			usr.username = username;
			usr.password = password;
			usr.prec = null;

			//no errors, unregistered user.
			usr.registered = false;
			usr.valid = true;
		}
		catch (SQLException e) {
			// failed to connect to database to check user, still not allowed in!!.

			usr = null;
		}

		return usr;
	}
}
