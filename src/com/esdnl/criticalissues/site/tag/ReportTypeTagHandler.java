package com.esdnl.criticalissues.site.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.esdnl.criticalissues.constant.ReportTypeConstant;

public class ReportTypeTagHandler extends TagSupport {

	private static final long serialVersionUID = 19218128389009801L;

	private String id;
	private String cls;
	private String style;
	private ReportTypeConstant value = null;
	private String onChange;

	public void setId(String id) {

		this.id = id;
	}

	public void setCls(String cls) {

		this.cls = cls;
	}

	public void setStyle(String style) {

		this.style = style;
	}

	public void setValue(ReportTypeConstant value) {

		this.value = value;
	}

	public void setOnChange(String onChange) {

		this.onChange = onChange;
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
			if ((this.onChange != null) && !this.onChange.trim().equals(""))
				out.print(" onchange=\"" + this.onChange + "\"");
			out.println(">");

			out.println("<OPTION VALUE='-1'>SELECT REPORT</OPTION>");
			for (int i = 0; i < ReportTypeConstant.ALL.length; i++)
				out.println("<OPTION VALUE=\"" + ReportTypeConstant.ALL[i].getValue() + "\""
						+ (ReportTypeConstant.ALL[i].equals(this.value) ? " SELECTED" : "") + ">"
						+ ReportTypeConstant.ALL[i].getDescription() + "</OPTION>");

			out.println("</SELECT>");

		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}
