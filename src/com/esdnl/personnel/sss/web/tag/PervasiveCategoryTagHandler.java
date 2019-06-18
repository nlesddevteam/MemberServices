package com.esdnl.personnel.sss.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class PervasiveCategoryTagHandler extends TagSupport {

	private static final long serialVersionUID = 7217927147571393059L;

	private String id;
	private String cls;
	private String style;
	private String value;

	public void setId(String id) {

		this.id = id;
	}

	public void setCls(String cls) {

		this.cls = cls;
	}

	public void setStyle(String style) {

		this.style = style;
	}

	public void setValue(String value) {

		this.value = value;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;

		try {
			out = pageContext.getOut();

			out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
			if ((this.cls != null) && !this.cls.trim().equals(""))
				out.print(" class=\"" + this.cls + "\"");
			if ((this.style != null) && !this.style.trim().equals(""))
				out.print(" style=\"" + this.style + "\"");
			out.println(">");

			out.println("<OPTION value='' " + (this.value == null ? "SELECTED" : "") + ">-Select-</OPTION>");
			out.println("<OPTION value='0' " + (this.value != null && this.value.equalsIgnoreCase("0") ? "SELECTED" : "")
					+ ">None</OPTION>");
			out.println("<OPTION value='1' " + (this.value != null && this.value.equalsIgnoreCase("1") ? "SELECTED" : "")
					+ ">1</OPTION>");
			out.println("<OPTION value='2' " + (this.value != null && this.value.equalsIgnoreCase("2") ? "SELECTED" : "")
					+ ">2</OPTION>");
			out.println("<OPTION value='3' " + (this.value != null && this.value.equalsIgnoreCase("3") ? "SELECTED" : "")
					+ ">3</OPTION>");
			out.println("<OPTION value='4' " + (this.value != null && this.value.equalsIgnoreCase("4") ? "SELECTED" : "")
					+ ">4</OPTION>");

			out.println("</SELECT>");

		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}