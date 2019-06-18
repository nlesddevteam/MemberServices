package com.esdnl.personnel.jobs.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.school.SchoolDB;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantSubstituteTeachingExpBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantSubExpManager;

public class ApplicantSubExpTagHandler extends TagSupport {

	private static final long serialVersionUID = 8276106397464851588L;

	public int doStartTag() throws JspException {

		SimpleDateFormat sdf = null;
		JspWriter out = null;

		ApplicantProfileBean profile = null;
		ApplicantSubstituteTeachingExpBean[] beans = null;

		try {
			out = pageContext.getOut();

			profile = (ApplicantProfileBean) pageContext.getAttribute("APPLICANT", PageContext.SESSION_SCOPE);

			sdf = new SimpleDateFormat("MMMM yyyy");

			if (profile != null)
				beans = ApplicantSubExpManager.getApplicantSubstituteTeachingExpBeans(profile.getSIN());

			
			out.println("<div style='font-size:16px;color:DimGrey;font-weight:bold;padding-top:5px;'>Substitute Teaching Experience</div>");
			
			
			if ((beans != null) && (beans.length > 0)) {
				
				out.println("<table class='table table-striped table-condensed' style='font-size:11px;'>");
				out.println("<thead><tr>");
				out.println("<th width='30%'>From</th>");
				out.println("<th width='30%'>To</th>");
				//out.println("<th width='20%'>School Board</th>");
				out.println("<th width='30%'># Days Per Year</th>");
				out.println("<th width='10%'>Options</th>");
				out.println("</tr></thead>");				
				out.println("<tbody>");
				
				
				for (int i = 0; i < beans.length; i++) {
					out.println("<TR>");
					out.println("<TD>" + sdf.format(beans[i].getFrom()) + "</TD>");
					out.println("<TD>" + sdf.format(beans[i].getTo()) + "</TD>");
					//out.println("<TD>" + beans[i].getSchoolBoard() + "</TD>");
					out.println("<TD>" + beans[i].getNumDays() + "</TD>");
					out.println("<TD><a class='btn btn-xs btn-danger' href='applicantRegistration.html?step=4&del="+ beans[i].getId() + "'>DEL</a></td>");
					out.println("</TR>");
				}
				out.println("</tbody></table>");	
			
			} else {
				
				out.println("No NLESD Permanent Experience currently on file.");
			
			} 
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