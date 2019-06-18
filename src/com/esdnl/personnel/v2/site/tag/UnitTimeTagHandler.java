package com.esdnl.personnel.v2.site.tag;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class UnitTimeTagHandler extends TagSupport {

	private static final long serialVersionUID = 689730080284524805L;
	private String id;
	private String cls;
	private String style;
	private double value;
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

	public void setValue(double value) {

		this.value = value;
	}

	public void setOnChange(String onChange) {

		this.onChange = onChange;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		DecimalFormat df = new DecimalFormat("0.00");
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

			for (double i = 1.0; i > 0.00; i = i - 0.01)
				out.println("<OPTION VALUE='" + df.format(i) + "'"
						+ ((this.value == Double.valueOf(df.format(i))) ? " SELECTED" : "") + ">" + df.format(i) + "</OPTION>");

			out.println("</SELECT>");

		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}