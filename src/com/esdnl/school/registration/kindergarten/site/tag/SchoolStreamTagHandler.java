package com.esdnl.school.registration.kindergarten.site.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrantBean;

public class SchoolStreamTagHandler extends TagSupport {

	private static final long serialVersionUID = 3623396499498378630L;

	private String id;
	private String cls;
	private String style;
	private int value;

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

	public int doStartTag() throws JspException {

		JspWriter out = null;

		try {
			out = pageContext.getOut();

			out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
			if ((this.cls != null) && !this.cls.trim().equals(""))
				out.print(" class=\"" + this.cls + "\"");
			if ((this.style != null) && !this.style.trim().equals(""))
				out.print(" style=\"" + this.style + "\"");
			out.println(" >");

			out.println("<OPTION VALUE='' style='font-weight:bold;'> --- Select One --- </OPTION>");
			for (KindergartenRegistrantBean.SCHOOLSTREAM s : KindergartenRegistrantBean.SCHOOLSTREAM.values()) {
				if (s.getValue() > 0)
					out.println("<OPTION VALUE=\"" + s.getValue() + "\"" + ((s.getValue() == this.value) ? " SELECTED" : "")
							+ ">" + s.getText() + "</OPTION>");
			}

			out.println("</SELECT>");

		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}