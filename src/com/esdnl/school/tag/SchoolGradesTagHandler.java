package com.esdnl.school.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.school.School;

public class SchoolGradesTagHandler extends TagSupport {

	private static final long serialVersionUID = 3623396499498378630L;

	private String id;
	private String cls;
	private String style;
	private School.GRADE value;
	private String multiple;

	public void setId(String id) {

		this.id = id;
	}

	public void setCls(String cls) {

		this.cls = cls;
	}

	public void setStyle(String style) {

		this.style = style;
	}

	public void setValue(School.GRADE value) {

		this.value = value;
	}

	public void setMultiple(String multiple) {

		this.multiple = multiple;
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

			for (School.GRADE g : School.GRADE.values()) {
				out.println("<OPTION VALUE=\"" + g.getValue() + "\""
						+ ((this.value != null && this.value.getValue() == g.getValue()) ? " SELECTED" : "") + ">" + g.getName()
						+ "</OPTION>");
			}

			out.println("</SELECT>");

		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}