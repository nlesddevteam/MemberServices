package com.awsd.ppgp.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.ppgp.PPGPException;
import com.awsd.ppgp.TaskSpecificTopicBean;
import com.awsd.ppgp.TaskSpecificTopicManager;
import com.esdnl.util.StringUtils;

public class TaskSpecificTopicTagHandler extends TagSupport {

	private static final long serialVersionUID = 2472337666280186906L;

	private String id;
	private String cls;
	private String style;
	private String multiple;
	private String grade;
	private String category;
	private String subject;
	private String topic;
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

	public void setTopic(String topic) {

		this.topic = topic;
	}

	public void setValue(String value) {

		this.value = value;
	}

	public void setOnchange(String onchange) {

		this.onchange = onchange;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		TaskSpecificTopicBean[] stopics = null;

		try {
			out = pageContext.getOut();

			if (!StringUtils.isEmpty(this.category) && !StringUtils.isEmpty(this.grade) && !StringUtils.isEmpty(this.subject)
					&& !StringUtils.isEmpty(this.topic))
				stopics = TaskSpecificTopicManager.getTaskSpecificTopicBeans(Integer.parseInt(this.category),
						Integer.parseInt(this.grade), Integer.parseInt(this.subject), Integer.parseInt(this.topic));
			else if (!StringUtils.isEmpty(this.category) && !StringUtils.isEmpty(this.topic))
				stopics = TaskSpecificTopicManager.getTaskSpecificTopicBeans(Integer.parseInt(this.category),
						Integer.parseInt(this.topic));

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

			out.println("<OPTION VALUE=\"0\">SELECT SPECIFIC TOPIC AREA</OPTION>");
			out.println("<OPTION VALUE=\"1\""
					+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == 1)) ? " SELECTED" : "")
					+ ">Other</OPTION>");
			for (int i = 0; ((stopics != null) && (i < stopics.length)); i++)
				out.println("<OPTION VALUE=\""
						+ stopics[i].getSpecificTopicID()
						+ "\""
						+ ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == stopics[i].getSpecificTopicID())) ? " SELECTED"
								: "") + ">" + stopics[i].getSpecificTopicTitle() + "</OPTION>");

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