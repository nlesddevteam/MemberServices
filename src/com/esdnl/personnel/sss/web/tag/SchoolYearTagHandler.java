package com.esdnl.personnel.sss.web.tag;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.common.Utils;

public class SchoolYearTagHandler extends TagSupport {

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

			Calendar cal = Calendar.getInstance();

			cal.add(Calendar.YEAR, 1);

			out.println("<OPTION value='' " + (this.value == null ? "SELECTED" : "") + ">-Select-</OPTION>");

			String sy = "";
			for (int y = 4; y > 0; y--) {
				sy = Utils.getSchoolYear(cal);

				out.println("<OPTION value='" + sy + "' "
						+ (this.value != null && this.value.equalsIgnoreCase(sy) ? "SELECTED" : "") + ">" + sy + "</OPTION>");

				cal.add(Calendar.YEAR, -1);
			}

			out.println("</SELECT>");

		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}