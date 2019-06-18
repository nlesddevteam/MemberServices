package com.esdnl.school.registration.kindergarten.site.tag;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.esdnl.personnel.v2.utils.StringUtils;
import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.service.KindergartenRegistrationManager;

public class SchoolYearTagHandler extends TagSupport {

	private static final long serialVersionUID = 3623396499498378630L;

	private String id;
	private String cls;
	private String style;
	private String value;
	private String multiple;
	private int offset;
	private boolean listAll;

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

	public void setMultiple(String multiple) {

		this.multiple = multiple;
	}

	public void setOffset(int offset) {

		this.offset = offset;
	}

	public void setListAll(boolean listAll) {

		this.listAll = listAll;
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
			if (Boolean.valueOf(multiple).booleanValue())
				out.println(" MULTIPLE");
			out.println(" >");

			out.println("<OPTION VALUE='' style='font-weight:bold;'> --- Select One --- </OPTION>");

			if (!listAll) {
				String sy = "";
				Calendar cal = Calendar.getInstance();
				for (int i = 0; i <= offset; i++) {
					cal.add(Calendar.YEAR, 1);
					sy = StringUtils.getSchoolYear(cal.getTime());
					out.println("<OPTION VALUE=\""
							+ sy
							+ "\""
							+ ((org.apache.commons.lang.StringUtils.isNotEmpty(this.value) && this.value.equals(sy)) ? " SELECTED"
									: "") + ">" + sy + "</OPTION>");
				}
			}
			else {
				try {
					for (String sy : KindergartenRegistrationManager.getDistinctKindergartenRegistrationPeriodSchoolYears()) {
						out.println("<OPTION VALUE=\""
								+ sy
								+ "\""
								+ ((org.apache.commons.lang.StringUtils.isNotEmpty(this.value) && this.value.equals(sy)) ? " SELECTED"
										: "") + ">" + sy + "</OPTION>");
					}
				}
				catch (SchoolRegistrationException e) {
					out.println("<OPTION VALUE=\"\">ERROR LOADING SCHOOL YEARS</OPTION>");
				}
			}

			out.println("</SELECT>");

		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}