package com.esdnl.school.registration.kindergarten.site.tag;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.awsd.school.School;
import com.awsd.school.SchoolException;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrationPeriodBean;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class SchoolsTagHandler extends TagSupport {

	private static final long serialVersionUID = -688375610119372801L;
	private String id;
	private String cls;
	private String style;
	private boolean dummy;
	private String onchange;
	private Object value;
	private KindergartenRegistrationPeriodBean period;

	public void setId(String id) {

		this.id = id;
	}

	public void setCls(String cls) {

		this.cls = cls;
	}

	public void setStyle(String style) {

		this.style = style;
	}

	public void setDummy(boolean dummy) {

		this.dummy = dummy;
	}

	public void setValue(Object value) {

		this.value = value;
	}

	public void setOnchange(String onchange) {

		this.onchange = onchange;
	}

	public void setPeriod(KindergartenRegistrationPeriodBean period) {

		this.period = period;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		int value_id = 0;

		try {
			if (this.value != null) {
				if (this.value instanceof String)
					value_id = Integer.parseInt((String) this.value);
				else if (this.value instanceof School)
					value_id = ((School) this.value).getSchoolID();
			}

			out = pageContext.getOut();

			out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
			if ((this.cls != null) && !this.cls.trim().equals(""))
				out.print(" class=\"" + this.cls + "\"");
			if ((this.style != null) && !this.style.trim().equals(""))
				out.print(" style=\"" + this.style + "\"");
			if ((this.onchange != null) && !this.onchange.trim().equals(""))
				out.print(" onchange=\"" + this.onchange + "\"");
			out.println(" >");

			if (dummy)
				out.println("<OPTION VALUE='' style='font-weight:bold;'> --- Select One --- </OPTION>");

			Collection<SchoolZoneBean> zones = (period != null) ? period.getZones() : SchoolZoneService.getSchoolZoneBeans();

			for (SchoolZoneBean z : zones) {
				if (z.getSchools().size() > 0) {
					out.println("<OPTGROUP LABEL='" + StringUtils.capitalize(z.getZoneName()) + " Region'>");
					for (School s : z.getSchools()) {
						if (s.getLowestGrade().getValue() != School.GRADE.KINDERGARDEN.getValue())
							continue;

						if (s.getSchoolDeptID() != 0)
							out.println("<OPTION VALUE=\"" + s.getSchoolID() + "\""
									+ ((s.getSchoolID() == value_id) ? " SELECTED" : "") + ">" + s.getSchoolName() + "</OPTION>");
					}
					out.println("</OPTGROUP>");
				}
			}

			out.println("</SELECT>");

		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		catch (SchoolException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}