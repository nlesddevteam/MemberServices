package com.esdnl.survey.site.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.esdnl.survey.constant.SurveyQuestionTypeConstant;

public class QuestionTypeListTagHandler extends TagSupport {

	private static final long serialVersionUID = 5695977562056622161L;
	private String id;
	private String cls;
	private String style;
	private SurveyQuestionTypeConstant value = null;
	private String onChange;

	public void setId(String id) {

		this.id = id;
	}

	public void setCls(String cls) {

		this.cls = cls;
	}

	public void setStyle(String style) {

		this.style = style;
	}

	public void setValue(SurveyQuestionTypeConstant value) {

		this.value = value;
	}

	public void setOnChange(String onChange) {

		this.onChange = onChange;
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
			if ((this.onChange != null) && !this.onChange.trim().equals(""))
				out.print(" onchange=\"" + this.onChange + "\"");
			out.println(">");

			out.println("<OPTION VALUE='-1'>SELECT QUESTION TYPE</OPTION>");
			for (int i = 0; i < SurveyQuestionTypeConstant.ALL.length; i++)
				out.println("<OPTION VALUE=\""
						+ SurveyQuestionTypeConstant.ALL[i].getValue()
						+ "\""
						+ (SurveyQuestionTypeConstant.ALL[i].equals(this.value) ? " SELECTED"
								: "") + ">"
						+ SurveyQuestionTypeConstant.ALL[i].getDescription() + "</OPTION>");

			out.println("</SELECT>");

		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}
