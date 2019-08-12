package com.awsd.security.tag;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.security.SecurityException;
import com.awsd.security.User;

public class SecurityCheckTagHandler extends TagSupport {

	private static final long serialVersionUID = -2811993805720405850L;
	private String permissions;
	private boolean allRequired;
	private String roles;

	public void setPermissions(String permissions) {

		this.permissions = permissions;
	}

	public void setAllRequired(boolean allRequired) {

		this.allRequired = allRequired;
	}

	public void setRoles(String roles) {

		this.roles = roles;
	}

	public int doEndTag() throws JspException {

		User usr = null;

		try {
			usr = (User) pageContext.getAttribute("usr", PageContext.SESSION_SCOPE);

			if (usr != null) {
				String[] permissions = ((this.permissions != null) ? this.permissions.split(",") : null);
				String[] roles = ((this.roles != null) ? this.roles.split(",") : null);

				boolean valid;

				//check for valid permissions
				boolean pcheck = false;
				if (permissions != null) {
					if (allRequired) {
						pcheck = true;
						for (String perm : permissions) {
							if (!usr.checkPermission(perm)) {
								pcheck = false;
								break;
							}
						}
					}
					else {
						pcheck = false;
						for (String perm : permissions) {
							if (usr.checkPermission(perm)) {
								pcheck = true;
								break;
							}
						}
					}
				}
				else
					pcheck = true;

				//get for valid roles
				boolean rcheck = false;
				if (roles != null) {
					if (allRequired) {
						rcheck = true;
						for (String role : roles) {
							if (!usr.checkRole(role)) {
								rcheck = false;
								break;
							}
						}
					}
					else {
						rcheck = false;
						for (String role : roles) {
							if (usr.checkRole(role)) {
								rcheck = true;
								break;
							}
						}
					}
				}
				else
					rcheck = true;

				valid = pcheck && rcheck;

				if (!valid) {
					this.pageContext.setAttribute("msg", "Secure Resource! Login Required.", PageContext.REQUEST_SCOPE);
					this.pageContext.forward("/signin.jsp");

					return SKIP_PAGE;
				}
			}
			else {
				this.pageContext.setAttribute("msg", "Secure Resource! Login Required.", PageContext.REQUEST_SCOPE);
				this.pageContext.forward("/signin.jsp");

				return SKIP_PAGE;
			}
		}
		catch (SecurityException e) {
			throw new JspException(e.getMessage());
		}
		catch (ServletException e) {
			throw new JspException(e.getMessage());
		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return EVAL_PAGE;
	}
}