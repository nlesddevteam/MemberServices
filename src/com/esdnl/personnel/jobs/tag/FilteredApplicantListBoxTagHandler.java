package com.esdnl.personnel.jobs.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class FilteredApplicantListBoxTagHandler extends TagSupport {

	private static final long serialVersionUID = -3965797789629169564L;

	private String id;
	private String cls;
	private String style;

	public void setId(String id) {

		this.id = id;
	}

	public void setCls(String cls) {

		this.cls = cls;
	}

	public void setStyle(String style) {

		this.style = style;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;

		try {
			out = pageContext.getOut();

			out.println("<table cellspacing='0' cellpadding='0' border='0'>");

			out.println("<tr>");
			out.println("<td><input type='textbox' id='" + this.id + "_filter'");
			if ((this.cls != null) && !this.cls.trim().equals(""))
				out.print(" class=\"" + this.cls + "\"");
			out.println("/> <input type='button' id='btn_apply_filter' value='filter'/></td>");
			out.println("</tr>");

			out.println("<tr>");
			out.println("<td><SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
			if ((this.cls != null) && !this.cls.trim().equals(""))
				out.print(" class=\"" + this.cls + "\"");
			if ((this.style != null) && !this.style.trim().equals(""))
				out.print(" style=\"" + this.style + "\"");
			out.println("></SELECT> <input type='button' id='btn_add_applicant' value='add'/>");
			out.println("</td>");
			out.println("</tr>");

			out.println("</table>");

		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}
