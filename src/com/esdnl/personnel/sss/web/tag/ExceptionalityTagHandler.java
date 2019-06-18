package com.esdnl.personnel.sss.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ExceptionalityTagHandler extends TagSupport {

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
			out.println("<OPTION value='BI' " + (this.value != null && this.value.equalsIgnoreCase("BI") ? "SELECTED" : "")
					+ ">BI</OPTION>");
			out.println("<OPTION value='CD' " + (this.value != null && this.value.equalsIgnoreCase("CD") ? "SELECTED" : "")
					+ ">CD</OPTION>");
			out.println("<OPTION value='DD' " + (this.value != null && this.value.equalsIgnoreCase("DD") ? "SELECTED" : "")
					+ ">DD</OPTION>");
			out.println("<OPTION value='EMBD' "
					+ (this.value != null && this.value.equalsIgnoreCase("EMBD") ? "SELECTED" : "") + ">EMBD</OPTION>");
			out.println("<OPTION value='GT' " + (this.value != null && this.value.equalsIgnoreCase("GT") ? "SELECTED" : "")
					+ ">GT</OPTION>");
			out.println("<OPTION value='HL' " + (this.value != null && this.value.equalsIgnoreCase("HL") ? "SELECTED" : "")
					+ ">HL</OPTION>");
			out.println("<OPTION value='HD' " + (this.value != null && this.value.equalsIgnoreCase("HD") ? "SELECTED" : "")
					+ ">HD</OPTION>");
			out.println("<OPTION value='PDD' " + (this.value != null && this.value.equalsIgnoreCase("PDD") ? "SELECTED" : "")
					+ ">PDD</OPTION>");
			out.println("<OPTION value='PD' " + (this.value != null && this.value.equalsIgnoreCase("PD") ? "SELECTED" : "")
					+ ">PD</OPTION>");
			out.println("<OPTION value='SLP' " + (this.value != null && this.value.equalsIgnoreCase("SLP") ? "SELECTED" : "")
					+ ">SLP</OPTION>");
			out.println("<OPTION value='VL' " + (this.value != null && this.value.equalsIgnoreCase("VL") ? "SELECTED" : "")
					+ ">VL</OPTION>");
			out.println("<OPTION value='LD' " + (this.value != null && this.value.equalsIgnoreCase("LD") ? "SELECTED" : "")
					+ ">LD</OPTION>");

			out.println("</SELECT>");

		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}