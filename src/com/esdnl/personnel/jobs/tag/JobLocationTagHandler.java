package com.esdnl.personnel.jobs.tag;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.awsd.school.School;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class JobLocationTagHandler extends TagSupport {

	private static final long serialVersionUID = -2280136452059946495L;

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
			out.println("<OPTION VALUE=\"0\">------------------</OPTION>");

			out.println("<OPTGROUP LABEL='District &amp; Regional Offices'>");
			out.println("<OPTION VALUE=\"-999\""
					+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == -999)) ? " SELECTED" : "")
					+ ">District Office</OPTION>");

			out.println("<OPTION VALUE=\"-3000\""
					+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == -3000)) ? " SELECTED" : "")
					+ ">Central Regional Office</OPTION>");
			
			out.println("<OPTION VALUE=\"-3001\""
					+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == -3001)) ? " SELECTED" : "")
					+ ">Central Satellite Office</OPTION>");
			
			out.println("<OPTION VALUE=\"-3001\""
					+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == -3001)) ? " SELECTED" : "")
					+ ">Central Satellite Office</OPTION>");

			out.println("<OPTION VALUE=\"-998\""
					+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == -999)) ? " SELECTED" : "")
					+ ">Avalon Regional Office</OPTION>");

			out.println("<OPTION VALUE=\"-1000\""
					+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == -1000)) ? " SELECTED" : "")
					+ ">Labrador Regional Office</OPTION>");

			out.println("<OPTION VALUE=\"-2000\""
					+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == -2000)) ? " SELECTED" : "")
					+ ">Western Regional Office</OPTION>");

			out.println("</OPTGROUP>");
			/*
			out.println("<OPTION VALUE=\"-100\""
					+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == -100)) ? " SELECTED" : "")
					+ ">Avalon East Region</OPTION>");

			out.println("<OPTION VALUE=\"-200\""
					+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == -200)) ? " SELECTED" : "")
					+ ">Avalon West Region</OPTION>");

			out.println("<OPTION VALUE=\"-300\""
					+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == -300)) ? " SELECTED" : "")
					+ ">Burin Region</OPTION>");

			out.println("<OPTION VALUE=\"-400\""
					+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == -400)) ? " SELECTED" : "")
					+ ">Vista Region</OPTION>");
			*/
			for (SchoolZoneBean z : SchoolZoneService.getSchoolZoneBeans()) {
				if (z.getSchools().size() > 0) {
					out.println("<OPTGROUP LABEL='" + StringUtils.capitalize(z.getZoneName())
							+ " Region Schools &amp; Other Locations'>");
					for (School s : z.getSchools()) {
						out.println("<OPTION VALUE=\""
								+ s.getSchoolID()
								+ "\""
								+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == s.getSchoolID())) ? " SELECTED"
										: "") + ">" + s.getSchoolName() + "</OPTION>");
					}
					out.println("</OPTGROUP>");
				}
			}
			out.println("</SELECT>");

		}
		catch (ServletException e) {
			throw new JspException(e.getMessage());
		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}