package com.esdnl.personnel.v2.site.tag;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeBean;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeException;
import com.esdnl.util.StringUtils;

public class EmployeesByLocationTagHandler extends TagSupport {

	private static final long serialVersionUID = 7194489325938064440L;

	private String id;
	private String cls;
	private String style;
	private String value;
	private String location;
	private String multiple = "true";
	private String onChange;
	private String includeNA;

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

	public void setLocation(String location) {

		this.location = location;
	}

	public void setMultiple(String multiple) {

		this.multiple = multiple;
	}

	public void setIncludeNA(String includeNA) {

		this.includeNA = includeNA;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		EmployeeBean[] emps = null;

		try {
			out = pageContext.getOut();

			out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
			if ((this.cls != null) && !this.cls.trim().equals(""))
				out.print(" class=\"" + this.cls + "\"");
			if ((this.style != null) && !this.style.trim().equals(""))
				out.print(" style=\"" + this.style + "\"");
			if ((this.onChange != null) && !this.onChange.trim().equals(""))
				out.print(" onchange=\"" + this.onChange + "\"");
			if (Boolean.valueOf(multiple).booleanValue())
				out.println(" MULTIPLE");
			out.println(">");

			if (!StringUtils.isEmpty(this.location)) {
				emps = EmployeeManager.getEmployeeBeans(
						com.esdnl.personnel.v2.utils.StringUtils.getSchoolYear(Calendar.getInstance().getTime()), this.location);

				Arrays.sort(emps, new Comparator<EmployeeBean>() {

					@Override
					public int compare(EmployeeBean o1, EmployeeBean o2) {

						return o1.getFullnameReverse().compareToIgnoreCase(o2.getFullnameReverse());
					}
				});
			}

			//System.err.println("length: " + locs.length);

			if (!Boolean.valueOf(multiple).booleanValue())
				out.println("<OPTION VALUE='-1'>PLEASE SELECT EMPLOYEE</OPTION>");

			if (!StringUtils.isEmpty(includeNA))
				out.println("<OPTION VALUE='0'>" + includeNA + "</OPTION>");

			for (int i = 0; ((emps != null) && (i < emps.length)); i++)
				out.println("<OPTION VALUE=\"" + emps[i].getEmpId().trim() + "\""
						+ ((!StringUtils.isEmpty(this.value) && (this.value.trim().equals(emps[i].getEmpId().trim()))) ? " SELECTED"
								: "")
						+ ">" + emps[i].getFullnameReverse() + "</OPTION>");

			out.println("</SELECT>");
		}

		catch (EmployeeException e) {
			e.printStackTrace();
			throw new JspException(e.getMessage());
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}