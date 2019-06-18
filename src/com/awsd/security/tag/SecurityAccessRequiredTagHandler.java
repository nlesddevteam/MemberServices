package com.awsd.security.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.security.User;

public class SecurityAccessRequiredTagHandler extends TagSupport {

	private static final long serialVersionUID = 2463651080530836006L;
	private String permissions;
	private String npermissions;
	private String roles;
	private boolean allRequired;

	public void setPermissions(String permissions) {

		this.permissions = permissions;
	}

	public void setNpermissions(String npermissions) {

		this.npermissions = npermissions;
	}

	public void setRoles(String roles) {

		this.roles = roles;
	}

	public void setAllRequired(boolean allRequired) {

		this.allRequired = allRequired;
	}

	public int doStartTag() throws JspException {

		User usr = null;
		int return_code = SKIP_BODY;

		usr = (User) pageContext.getAttribute("usr", PageContext.SESSION_SCOPE);

		if (usr != null) {
			String[] permissions = (this.permissions != null) ? this.permissions.split(",") : null;
			String[] npermissions = (this.npermissions != null) ? this.npermissions.split(",") : null;
			String[] roles = (this.roles != null) ? this.roles.split(",") : null;

			boolean valid = false;

			if (permissions != null || npermissions != null || roles != null) {

				boolean pcheck;
				if (permissions != null) {
					if (allRequired) {
						pcheck = true;
						for (int i = 0; i < permissions.length; i++) {
							if (!usr.checkPermission(permissions[i])) {
								pcheck = false;
							}
						}
					}
					else {
						pcheck = false;
						for (int i = 0; i < permissions.length; i++) {
							if (usr.checkPermission(permissions[i])) {
								pcheck = true;
								break;
							}
						}
					}
				}
				else
					pcheck = true;

				boolean rcheck;
				if (roles != null) {
					if (allRequired) {
						rcheck = true;
						for (int i = 0; i < roles.length; i++) {
							if (!usr.checkRole(roles[i])) {
								rcheck = false;
							}
						}
					}
					else {
						rcheck = false;
						for (int i = 0; i < roles.length; i++) {
							if (usr.checkRole(roles[i])) {
								rcheck = true;
								break;
							}
						}
					}
				}
				else
					rcheck = true;

				boolean npcheck;
				if (npermissions != null) {
					npcheck = true;
					for (String perm : npermissions) {
						if (usr.checkPermission(perm)) {
							npcheck = false;
							break;
						}
					}
				}
				else
					npcheck = true;

				valid = pcheck && rcheck && npcheck;
			}
			else
				valid = true;

			if (valid) {
				return_code = EVAL_BODY_INCLUDE;
			}
		}
		else {
			return_code = SKIP_BODY;
		}

		return return_code;
	}

}
