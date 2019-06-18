package com.awsd.ppgp.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.ppgp.PPGPDB;
import com.awsd.ppgp.PPGPException;
import com.esdnl.util.StringUtils;

public class PGPYears extends TagSupport {

	private static final long serialVersionUID = 1108666074920457429L;

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
		String[] cats = null;

		try {
			out = pageContext.getOut();

			cats = PPGPDB.getPGPYears();

			out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
			if ((this.cls != null) && !this.cls.trim().equals(""))
				out.print(" class=\"" + this.cls + "\"");
			if ((this.style != null) && !this.style.trim().equals(""))
				out.print(" style=\"" + this.style + "\"");
			out.println(">");
			;

			for (int i = 0; i < cats.length; i++)
				out.println("<OPTION VALUE=\"" + cats[i] + "\""
						+ ((!StringUtils.isEmpty(this.value) && StringUtils.isEqual(this.value, cats[i])) ? " SELECTED" : "") + ">"
						+ cats[i] + "</OPTION>");

			out.println("</SELECT>");

		}
		catch (PPGPException e) {
			throw new JspException(e.getMessage());
		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}