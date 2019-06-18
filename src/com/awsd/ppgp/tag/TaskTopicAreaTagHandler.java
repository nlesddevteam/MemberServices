package com.awsd.ppgp.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.ppgp.PPGPException;
import com.awsd.ppgp.TaskTopicAreaBean;
import com.awsd.ppgp.TaskTopicAreaManager;
import com.esdnl.util.StringUtils;

public class TaskTopicAreaTagHandler extends TagSupport {

	private static final long serialVersionUID = 8183904366125147610L;

	private String id;
	private String cls;
	private String style;
	private String multiple;
	private String grade;
	private String category;
	private String subject;
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

	public void setCategory(String category) {

		this.category = category;
	}

	public void setGrade(String grade) {

		this.grade = grade;
	}

	public void setSubject(String subject) {

		this.subject = subject;
	}

	public void setValue(String value) {

		this.value = value;
	}

	public void setOnchange(String onchange) {

		this.onchange = onchange;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		TaskTopicAreaBean[] topics = null;

		try {
			out = pageContext.getOut();

			if (!StringUtils.isEmpty(this.category) && !StringUtils.isEmpty(this.grade) && !StringUtils.isEmpty(this.subject))
				topics = TaskTopicAreaManager.getTaskTopicAreaBeans(Integer.parseInt(this.category),
						Integer.parseInt(this.grade), Integer.parseInt(this.subject));
			else if (!StringUtils.isEmpty(this.category))
				topics = TaskTopicAreaManager.getTaskTopicAreaBeans(Integer.parseInt(this.category));

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

			out.println("<OPTION VALUE=\"0\">SELECT TOPIC AREA</OPTION>");
			out.println("<OPTION VALUE=\"1\""
					+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == 1)) ? " SELECTED" : "")
					+ ">Other</OPTION>");
			for (int i = 0; ((topics != null) && (i < topics.length)); i++)
				out.println("<OPTION VALUE=\""
						+ topics[i].getTopicID()
						+ "\""
						+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == topics[i].getTopicID())) ? " SELECTED"
								: "") + ">" + topics[i].getTopicTitle() + "</OPTION>");

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