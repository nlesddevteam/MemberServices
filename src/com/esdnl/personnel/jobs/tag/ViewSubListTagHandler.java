package com.esdnl.personnel.jobs.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.school.Grade;
import com.awsd.school.Subject;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;
import com.esdnl.personnel.jobs.dao.SubListManager;

public class ViewSubListTagHandler extends TagSupport {

	private static final long serialVersionUID = -6232227144906860689L;
	private int listId;

	public void setListId(int listId) {

		this.listId = listId;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		SubListBean list = null;
		Subject[] subjects = null;
		Grade[] grades = null;

		try {
			out = pageContext.getOut();

			list = SubListManager.getSubListBean(this.listId);

			subjects = list.getSubjectAreas();
			grades = list.getGrades();

			
			out.println("<div class=\"panel-group\" style=\"padding-top:5px;\">");
			out.println("<div class=\"panel panel-success\">");
			out.println("<div class=\"panel-heading\"><b>"+list.getTitle()+" Substitute List</b></div>");
			out.println("<div class=\"panel-body\"><div class=\"container-fluid\"> ");	
			out.println("<div class=\"row\"><div class=\"input-group\"><span class=\"input-group-addon\">List Type: </span><div class='form-control' style='text-transform:Capitalize;'>"+ list.getType().getDescription() + "</div></div></div>");
			out.println("<div class=\"row\"><div class=\"input-group\"><span class=\"input-group-addon\">Region: </span><div class='form-control' style='text-transform:Capitalize;'>"+ list.getRegion().getName() + "</div></div></div>");

			if (grades.length > 0) {
				out.println("<div class=\"row\"><div class=\"input-group\"><span class=\"input-group-addon\">Grade Levels: </span><div class='form-control' style='height:auto;text-transform:Capitalize;'><ul>");
				for (int k = 0; k < grades.length; k++) {
					out.println("<li>"+ grades[k].getGradeName() + "</li>");
			}
					out.print("</ul></div></div></div>");
			}
			
			
			
			if (subjects.length > 0) {
				out.println("<div class=\"row\"><div class=\"input-group\"><span class=\"input-group-addon\">Subject Areas: </span><div class='form-control' style='height:auto;text-transform:Capitalize;'><ul>");
				for (int k = 0; k < subjects.length; k++) {
					out.println("<li>"+ subjects[k].getSubjectName() + "</li>");
			}
					out.print("</ul></div></div></div>");	
			}
			
				
			
			out.println(((list.isActive()) ? "<span style='color:white;background-color:Green;'>&nbsp;This List is ACTIVE&nbsp;</style>": "<span style='color:white;background-color:Red;'>&nbsp;NOT ACCEPTING APPLICATIONS AT THIS TIME&nbsp;</span>") + "");
			
			out.println("</div></div></div></div>");
		}

		catch (JobOpportunityException e) {
			e.printStackTrace();
			throw new JspException(e.getMessage());
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}