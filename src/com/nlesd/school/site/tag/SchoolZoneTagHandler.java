package com.nlesd.school.site.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.school.SchoolException;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class SchoolZoneTagHandler extends TagSupport {

	private static final long serialVersionUID = -3799538623337574827L;

	private String id;
	private String cls;
	private String style;
	private SchoolZoneBean value;
	private String multiple;
	private boolean dummy;

	public void setId(String id) {

		this.id = id;
	}

	public void setCls(String cls) {

		this.cls = cls;
	}

	public void setStyle(String style) {

		this.style = style;
	}

	public void setValue(SchoolZoneBean value) {

		this.value = value;
	}

	public void setMultiple(String multiple) {

		this.multiple = multiple;
	}

	public void setDummy(boolean dummy) {

		this.dummy = dummy;
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

			if (this.dummy)
				out.println("<option value=''>--- Select Zone ---</option>");

			for (SchoolZoneBean z : SchoolZoneService.getSchoolZoneBeans()) {
				out.println("<OPTION VALUE=\"" + z.getZoneId() + "\""
						+ ((this.value != null && this.value.getZoneId() == z.getZoneId()) ? " SELECTED" : "") + ">"
						+ z.getZoneName() + "</OPTION>");
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
