package com.awsd.security.tag;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.security.SecurityException;
import com.awsd.security.User;

public class PermissionRedirectTagHandler extends TagSupport {

	private static final long serialVersionUID = -2811993805720405850L;
	private String permissions;
	private String page;

	public void setPermissions(String permissions) {

		this.permissions = permissions;
	}

	public void setPage(String page) {

		this.page = page;
	}

	public int doStartTag() throws JspException {

		User usr = null;

		try {
			usr = (User) pageContext.getAttribute("usr", PageContext.SESSION_SCOPE);

			if (usr != null) {
				String[] permissions = ((this.permissions != null) ? this.permissions.split(",") : null);
				boolean valid;

				if (permissions != null) {
					valid = false;
					for (int i = 0; i < permissions.length; i++) {
						if (usr.checkPermission(permissions[i])) {
							valid = true;
							break;
						}
					}
				}
				else
					valid = true;

				if (valid)
					this.pageContext.forward(page);
			}
			else {
				this.pageContext.setAttribute("msg", "Secure Resource! Login Required.", PageContext.REQUEST_SCOPE);
				this.pageContext.forward("/signin.jsp");
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

		return SKIP_BODY;
	}
}