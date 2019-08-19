package com.awsd.security.tag;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.awsd.security.SecurityException;

public class SecurityRequiredPageObjectsCheckTagHandler extends TagSupport {

	private static final long serialVersionUID = -2811993805720405850L;
	private String[] names;
	private String redirectTo;
	private int scope;

	public void setNames(String[] names) {

		this.names = names;
	}

	public void setScope(int scope) {

		this.scope = scope;
	}

	public void setRedirectTo(String redirectTo) {

		this.redirectTo = redirectTo;
	}

	public int doEndTag() throws JspException {

		try {
			boolean valid = true;

			for (String name : names) {
				if (pageContext.getAttribute(name, this.scope) == null) {
					valid = false;
					break;
				}
			}

			if (!valid) {
				this.pageContext.setAttribute("msg", "Invalid request.", PageContext.REQUEST_SCOPE);
				if (StringUtils.isBlank(redirectTo)) {
					this.pageContext.forward("/signin.jsp");
				}
				else {
					this.pageContext.forward(redirectTo);
				}

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