package com.esdnl.scrs.site.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class AgeListboxTagHandler extends TagSupport {

	private static final long serialVersionUID = -5901352348568950336L;
	private String id;
	private String cls;
	private String style;
	private int value;
	private String multiple;
	private int minAge;
	private int maxAge;
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

	public void setValue(int value) {

		this.value = value;
	}

	public void setMultiple(String multiple) {

		this.multiple = multiple;
	}

	public void setMinAge(int minAge) {

		this.minAge = minAge;
	}

	public void setMaxAge(int maxAge) {

		this.maxAge = maxAge;
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
			out.println("<OPTION VALUE=\"-1\">--- SELECT AGE --- </OPTION>");
			for (int i = this.minAge; i <= this.maxAge; i++)
				out.println("<OPTION VALUE=\"" + i + "\"" + ((this.value == i) ? " SELECTED" : "") + ">" + i + "</OPTION>");

			out.println("</SELECT>");
		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}