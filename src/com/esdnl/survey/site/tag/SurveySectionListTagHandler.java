package com.esdnl.survey.site.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.esdnl.survey.bean.SurveyBean;
import com.esdnl.survey.bean.SurveyException;
import com.esdnl.survey.bean.SurveySectionBean;
import com.esdnl.survey.dao.SurveySectionManager;

public class SurveySectionListTagHandler extends TagSupport {

	private static final long serialVersionUID = 4352915542123294729L;
	private String id;
	private String cls;
	private String style;
	private SurveyBean survey = null;
	private SurveySectionBean value = null;
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

	public void setSurvey(SurveyBean survey) {

		this.survey = survey;
	}

	public void setValue(SurveySectionBean value) {

		this.value = value;
	}

	public void setOnChange(String onChange) {

		this.onChange = onChange;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;

		try {
			out = pageContext.getOut();

			if (this.survey == null)
				throw new NullPointerException("SurveySectionListTagHandler: SURVEY TAG ATTRIBUTE IS NULL.");

			SurveySectionBean[] sections = SurveySectionManager.getSuverySectionBeans(this.survey);

			out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
			if ((this.cls != null) && !this.cls.trim().equals(""))
				out.print(" class=\"" + this.cls + "\"");
			if ((this.style != null) && !this.style.trim().equals(""))
				out.print(" style=\"" + this.style + "\"");
			if ((this.onChange != null) && !this.onChange.trim().equals(""))
				out.print(" onchange=\"" + this.onChange + "\"");
			out.println(">");

			out.println("<OPTION VALUE='-1'>SELECT SURVEY SECTION</OPTION>");
			for (int i = 0; i < sections.length; i++)
				out.println("<OPTION VALUE=\"" + sections[i].getSectionId() + "\""
						+ (sections[i].equals(this.value) ? " SELECTED" : "") + ">"
						+ sections[i].getHeading() + "</OPTION>");

			out.println("</SELECT>");

		}
		catch (SurveyException e) {
			throw new JspException(e.getMessage());
		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}
