package com.esdnl.personnel.jobs.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.personnel.jobs.bean.ApplicantEsdExperienceBean;
import com.esdnl.personnel.jobs.bean.ApplicantNLESDPermanentExperienceBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantEsdExperienceManager;
import com.esdnl.personnel.jobs.dao.ApplicantNLESDPermExpManager;

public class ApplicantNLESDPermExpTagHandler extends TagSupport {
	private static final long serialVersionUID = -5425863356686843118L;

	public int doStartTag() throws JspException {
		SimpleDateFormat sdf = null;
		JspWriter out = null;
		ApplicantProfileBean profile = null;
		ApplicantNLESDPermanentExperienceBean[] beans = null;
		ApplicantEsdExperienceBean exp = null;
		try {
			out = pageContext.getOut();
			profile = (ApplicantProfileBean) pageContext.getAttribute("APPLICANT", pageContext.SESSION_SCOPE);
			sdf = new SimpleDateFormat("MMMM yyyy");
			if (profile != null) {
				beans = ApplicantNLESDPermExpManager.getApplicantNLESDPermanentExperienceBeans(profile.getSIN());
				exp = ApplicantEsdExperienceManager.getApplicantEsdExperienceBean(profile.getSIN());
			}
			
			if (exp != null) {
				out.println("<div style='font-size:16px;color:DimGrey;font-weight:bold;padding-top:5px;'>NLESD Permanent Experience (Total Months: "+ exp.getPermanentLTime() +")</div>");
			
			if ((beans != null) && (beans.length > 0)) {
				
				out.println("<table class='table table-striped table-condensed' style='font-size:11px;'>");
				out.println("<thead><tr>");
				out.println("<th width='10%'>From</th>");
				out.println("<th width='10%'>To</th>");
				out.println("<th width='20%'>School</th>");
				out.println("<th width='50%'>Grades and/or Subjects Taught</th>");
				out.println("<th width='10%'>Options</th>");
				out.println("</tr></thead>");				
				out.println("<tbody>");
				
				for (int i = 0; i < beans.length; i++) {
					out.println("<TR>");
					out.println("<TD>" + sdf.format(beans[i].getFrom()) + "</TD>");
					out.println("<TD>" + sdf.format(beans[i].getTo()) + "</TD>");
					out.println("<TD>"+ SchoolDB.getSchool(beans[i].getSchoolId()).getSchoolName() + "</TD>");
					out.println("<TD>" + beans[i].getGradesSubjects() + "</TD>");
					out.println("<TD><a class='btn btn-xs btn-danger' href='applicantRegistration.html?step=2A&del="+ beans[i].getId() + "'>DEL</a></td>");
					out.println("</TR>");
				}
				out.println("</tbody></table>");	
			
			} else {
				
				out.println("No NLESD Permanent Experience currently on file.");
			
			} }
		}
	
		
		catch (SchoolException e) {
			e.printStackTrace();
			throw new JspException(e.getMessage());
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
