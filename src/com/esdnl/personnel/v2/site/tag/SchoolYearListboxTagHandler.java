package com.esdnl.personnel.v2.site.tag;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.esdnl.personnel.v2.utils.StringUtils;

public class SchoolYearListboxTagHandler extends TagSupport {

	private static final long serialVersionUID = 3852782379656882226L;
	private String id;
	private String cls;
	private String style;
	private String value;
	private String onChange;
	private int pastYears;
	private int futureYears;

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

	public void setOnChange(String onChange) {

		this.onChange = onChange;
	}

	public void setPastYears(int pastYears) {

		if (pastYears < 0)
			this.pastYears = 0;
		else
			this.pastYears = pastYears;
	}

	public void setFutureYears(int futureYears) {

		if (futureYears < 0)
			this.futureYears = 0;
		else
			this.futureYears = futureYears;
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

			Calendar cal = Calendar.getInstance();

			cal.add(Calendar.YEAR, this.futureYears);

			if (org.apache.commons.lang.StringUtils.isEmpty(this.value))
				out.println("<OPTION VALUE=''>- Select -</OPTION>");

			for (int i = this.futureYears; i >= (-1 * this.pastYears); i--) {
				out.println("<OPTION VALUE='" + StringUtils.getSchoolYear(cal.getTime()) + "' PSY='"
						+ StringUtils.getPreviousSchoolYear(cal.getTime()) + "'"
						+ ((StringUtils.getSchoolYear(cal.getTime()).equalsIgnoreCase(this.value)) ? " SELECTED" : "") + ">"
						+ StringUtils.getSchoolYear(cal.getTime()) + "</OPTION>");

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