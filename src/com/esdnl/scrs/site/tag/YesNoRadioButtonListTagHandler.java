package com.esdnl.scrs.site.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

public class YesNoRadioButtonListTagHandler extends TagSupport {

	private static final long serialVersionUID = -5901352348568950336L;
	private String id;
	private String cls;
	private String style;
	private boolean value;

	public void setId(String id) {

		this.id = id;
	}

	public void setCls(String cls) {

		this.cls = cls;
	}

	public void setStyle(String style) {

		this.style = style;
	}

	public void setValue(boolean value) {

		this.value = value;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;

		try {
			out = pageContext.getOut();

			out.print("<input type='radio' name=\"" + this.id + "\" id=\"" + this.id + "\"");
			if (StringUtils.isNotEmpty(this.cls))
				out.print(" class=\"" + this.cls + "\"");
			if (StringUtils.isNotEmpty(this.style))
				out.print(" style=\"" + this.style + "\"");
			out.print(" value=\"true\" " + (this.value ? "CHECKED=CHECKED" : "") + "/>");
			out.println("<label for=\"" + this.id + "\">Yes</label>");

			out.print("<input type='radio' name=\"" + this.id + "\" id=\"" + this.id + "\"");
			if (StringUtils.isNotEmpty(this.cls))
				out.print(" class=\"" + this.cls + "\"");
			if (StringUtils.isNotEmpty(this.style))
				out.print(" style=\"" + this.style + "\"");
			out.print(" value=\"false\" " + (!this.value ? "CHECKED=CHECKED" : "") + "/>");
			out.println("<label for=\"" + this.id + "\">No</label>");
		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}