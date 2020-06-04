package com.esdnl.personnel.jobs.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.esdnl.personnel.jobs.constants.DocumentTypeSS;
import com.esdnl.util.StringUtils;

public class DocumentTypeSSListboxTagHandler extends TagSupport {

	private static final long serialVersionUID = 4362564658920999975L;
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

			out.println("<OPTION VALUE='-1'>--- Select Document Type ---</OPTION>");
			for (DocumentTypeSS type : DocumentTypeSS.ALL) {
				if (type.equal(DocumentTypeSS.LETTER)) {
					continue;
				}

				out.println("<OPTION VALUE=\"" + type.getValue() + "\""
						+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == type.getValue())) ? " SELECTED"
								: "")
						+ ">" + type.getDescription() + "</OPTION>");
			}

			out.println("</SELECT>");
		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}
