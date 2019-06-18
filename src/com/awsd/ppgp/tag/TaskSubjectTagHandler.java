package com.awsd.ppgp.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.ppgp.PPGPException;
import com.awsd.ppgp.TaskSubjectBean;
import com.awsd.ppgp.TaskSubjectManager;
import com.esdnl.util.StringUtils;

public class TaskSubjectTagHandler extends TagSupport {

	private static final long serialVersionUID = 494567150582946496L;

	private String id;
	private String cls;
	private String style;
	private String multiple;
	private String grade;
	private String value;
	private String onchange;

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

	public void setGrade(String grade) {

		this.grade = grade;
	}

	public void setValue(String value) {

		this.value = value;
	}

	public void setOnchange(String onchange) {

		this.onchange = onchange;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		TaskSubjectBean[] subs = null;

		try {
			out = pageContext.getOut();

			if (!StringUtils.isEmpty(this.grade))
				subs = TaskSubjectManager.getTaskSubjectBeans(Integer.parseInt(this.grade));

			out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
			if ((this.cls != null) && !this.cls.trim().equals(""))
				out.print(" class=\"" + this.cls + "\"");
			if ((this.style != null) && !this.style.trim().equals(""))
				out.print(" style=\"" + this.style + "\"");
			if (Boolean.valueOf(multiple).booleanValue())
				out.print(" MULTIPLE");
			if (!StringUtils.isEmpty(this.onchange))
				out.print(" onchange=\"" + this.onchange + "\"");
			out.println(">");
			;

			out.println("<OPTION VALUE=\"0\">SELECT SUBJECT AREA</OPTION>");
			for (int i = 0; ((subs != null) && (i < subs.length)); i++)
				out.println("<OPTION VALUE=\""
						+ subs[i].getSubjectID()
						+ "\""
						+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == subs[i].getSubjectID())) ? " SELECTED"
								: "") + ">" + subs[i].getSubjectTitle() + "</OPTION>");

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