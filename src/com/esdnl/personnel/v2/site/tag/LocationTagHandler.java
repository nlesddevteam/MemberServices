package com.esdnl.personnel.v2.site.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.esdnl.personnel.v2.database.sds.LocationManager;
import com.esdnl.personnel.v2.model.sds.bean.LocationBean;
import com.esdnl.personnel.v2.model.sds.bean.LocationException;
import com.esdnl.util.StringUtils;

public class LocationTagHandler extends TagSupport {

	private static final long serialVersionUID = 6745344054361355646L;

	private String id;
	private String cls;
	private String style;
	private String value;
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

	public void setValue(String value) {

		this.value = value;
	}

	public void setOnChange(String onChange) {

		this.onChange = onChange;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		LocationBean[] locs = null;

		try {
			out = pageContext.getOut();

			out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
			if ((this.cls != null) && !this.cls.trim().equals(""))
				out.print(" class=\"" + this.cls + "\"");
			if ((this.style != null) && !this.style.trim().equals(""))
				out.print(" style=\"" + this.style + "\"");
			if ((this.onChange != null) && !this.onChange.trim().equals(""))
				out.print(" onchange=\"" + this.onChange + "\"");
			out.println(" >");

			locs = LocationManager.getLocationBeans();

			//System.err.println("length: " + locs.length);

			out.println("<OPTION VALUE=\"-1\">SELECT LOCATION</OPTION>");
			for (int i = 0; i < locs.length; i++) {
				if (locs[i].getLocationId().trim().equals("293"))
					continue;
				else
					out.println("<OPTION VALUE=\""
							+ locs[i].getLocationDescription()
							+ "\""
							+ ((!StringUtils.isEmpty(this.value) && (this.value.equals(locs[i].getLocationDescription()))) ? " SELECTED"
									: "") + ">" + locs[i].getLocationDescription() + "</OPTION>");
			}

			out.println("</SELECT>");
		}

		catch (LocationException e) {
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