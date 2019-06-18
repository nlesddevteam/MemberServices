package com.esdnl.school.registration.kindergarten.site.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class YesNoRadioButtonGroupTagHandler extends TagSupport {

	private static final long serialVersionUID = 3623396499498378630L;

	private String id;
	private String cls;
	private String style;
	private Boolean value;

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

		this.value = new Boolean(value);
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;

		try {
			out = pageContext.getOut();

			out.print("Yes<input type=\"radio\" name=\"" + this.id + "\" id=\"" + this.id + "_1\"");
			if ((this.cls != null) && !this.cls.trim().equals(""))
				out.print(" class=\"" + this.cls + "\"");
			if ((this.style != null) && !this.style.trim().equals(""))
				out.print(" style=\"" + this.style + "\"");
			out.print(" value=\"1\"");
			if (this.value != null && this.value.booleanValue())
				out.print(" CHECKED");
			out.print("/>&nbsp;&nbsp;No<input type=\"radio\" name=\"" + this.id + "\" id=\"" + this.id + "_0\"");
			if ((this.cls != null) && !this.cls.trim().equals(""))
				out.print(" class=\"" + this.cls + "\"");
			if ((this.style != null) && !this.style.trim().equals(""))
				out.print(" style=\"" + this.style + "\"");
			out.print(" value=\"0\"");
			if (this.value != null && !this.value.booleanValue())
				out.print(" CHECKED");
			out.print("/>");

		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}