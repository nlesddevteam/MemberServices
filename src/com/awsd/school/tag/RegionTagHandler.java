package com.awsd.school.tag;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.school.SchoolException;
import com.awsd.school.bean.RegionBean;
import com.awsd.school.bean.RegionException;
import com.awsd.school.dao.RegionManager;
import com.esdnl.util.StringUtils;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class RegionTagHandler extends TagSupport {

	private static final long serialVersionUID = -7648032905214544534L;

	private String id;
	private String cls;
	private String style;
	private String multiple;
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

	public void setMultiple(String multiple) {

		this.multiple = multiple;
	}

	public void setValue(String value) {

		this.value = value;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		Collection<RegionBean> regions = null;

		try {
			out = pageContext.getOut();

			out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
			if ((this.cls != null) && !this.cls.trim().equals(""))
				out.print(" class=\"" + this.cls + "\"");
			if ((this.style != null) && !this.style.trim().equals(""))
				out.print(" style=\"" + this.style + "\"");
			if (Boolean.valueOf(multiple).booleanValue())
				out.println(" MULTIPLE");
			out.println(">");
			out.println("<OPTION VALUE=''></OPTION>");
			out.println("<OPTION style='text-transform:capitalize;font-family:arial;font-size:12pt;color:red;' VALUE='16'>NLESD - PROVINCAL</OPTION>");
			for (SchoolZoneBean zone : SchoolZoneService.getSchoolZoneBeans()) {
				regions = RegionManager.getRegionBeans(zone);
				out.println("<optgroup style='text-transform:uppercase;font-family:arial;font-size:12pt;' label='"
						+ zone.getZoneName() + "'>");
				for (RegionBean region : regions) {
					out.println("<OPTION style='text-transform:capitalize;' VALUE=\""
							+ region.getId()
							+ "\""
							+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == region.getId())) ? " SELECTED"
									: "") + ">" + region.getName() + "</OPTION>");
				}
				out.println("</optgroup>");
			}
			out.println("</SELECT>");

		}
		catch (RegionException e) {
			throw new JspException(e.getMessage());
		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		catch (NumberFormatException e) {
			throw new JspException(e.getMessage());
		}
		catch (SchoolException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}