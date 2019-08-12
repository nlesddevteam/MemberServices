package com.esdnl.personnel.jobs.tag;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;

public class ApplicantLoggedOn extends TagSupport {

	private static final long serialVersionUID = -6876492981595878509L;

	@Override
	public int doEndTag() throws JspException {

		ApplicantProfileBean profile = null;

		try {
			profile = (ApplicantProfileBean) pageContext.getAttribute("APPLICANT", PageContext.SESSION_SCOPE);

			if (profile == null) {
				this.pageContext.setAttribute("msg", "Secure Resource! Login Required.", PageContext.REQUEST_SCOPE);
				this.pageContext.forward("/Personnel/applicant_login.jsp");

				return Tag.SKIP_PAGE;
			}
		}
		catch (SecurityException e) {
			throw new JspException(e.getMessage());
		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		catch (ServletException e) {
			throw new JspException(e.getMessage());
		}

		return Tag.EVAL_PAGE;
	}

}