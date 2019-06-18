package com.esdnl.personnel.jobs.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import com.awsd.school.SubjectDB;
import com.awsd.school.SubjectException;
import com.esdnl.personnel.jobs.bean.ApplicantEducationBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantEducationManager;
import com.esdnl.personnel.jobs.dao.DegreeManager;
import com.esdnl.util.StringUtils;

public class ApplicantEducationTagHandler extends TagSupport {

	private static final long serialVersionUID = -7224633913777535025L;

	public int doStartTag() throws JspException {

		SimpleDateFormat sdf = null;
		JspWriter out = null;

		ApplicantProfileBean profile = null;
		ApplicantEducationBean[] beans = null;

		try {
			out = pageContext.getOut();

			profile = (ApplicantProfileBean) pageContext.getAttribute("APPLICANT", PageContext.SESSION_SCOPE);

			sdf = new SimpleDateFormat("MMMM yyyy");

			if (profile != null)
				beans = ApplicantEducationManager.getApplicantEducationBeans(profile.getSIN());

			
			out.println("<div style='font-size:16px;color:DimGrey;font-weight:bold;padding-top:5px;'>University/College Education</div>");
			
		

			if ((beans != null) && (beans.length > 0)) {
				
				out.println("<table class='table table-striped table-condensed' style='font-size:11px;'>");
				out.println("<thead><tr>");
				out.println("<th width='25%'>INSTITUTION</th>");
				out.println("<th width='10%'>FROM/TO</th>");
				out.println("<th width='25%'>PROGRAM/FACULTY</th>");
				out.println("<th width='20%'>MAJOR(S)/MINOR(S)</th>");
				out.println("<th width='10%'>DEGREE</th>");
				out.println("<th width='10%'>OPTIONS</th>");
				out.println("</tr></thead>");				
				out.println("<tbody>");
			for (int i = 0; i < beans.length; i++) {
					out.println("<tr>");
					out.println("<td>" + beans[i].getInstitutionName() + "</td>");
					out.println("<td>" + sdf.format(beans[i].getFrom()) + " to<br/>");
					out.println(sdf.format(beans[i].getTo()) + "</td>");
					out.println("<td>" + beans[i].getProgramFacultyName() + "</td>");  
					out.println("<td>");					
                    out.print("<span style='color:Navy;'>Major(#crs):</span>");
							if (beans[i].getMajor() != -1) {						
								out.println(SubjectDB.getSubject(beans[i].getMajor()).getSubjectName() + " (" + beans[i].getNumberMajorCourses()+ ")");
							}
							else {
								out.println("N/A");
							}
					
					out.println("<br/>");					
                    out.print("<span style='color:Green;'>Minor(#crs):</span>");
							if (beans[i].getMinor() != -1) {
								out.println(SubjectDB.getSubject(beans[i].getMinor()).getSubjectName() + " (" + beans[i].getNumberMinorCourses()+ ")");
							} else {
								out.println("N/A");
							}
					out.println("</td>");
					out.println("<td>"+ ((!StringUtils.isEmpty(beans[i].getDegreeConferred())) ? DegreeManager.getDegreeBeans(beans[i].getDegreeConferred()).getAbbreviation() : "&nbsp;") + "</TD>");
					out.println("<td><a class='btn btn-xs btn-danger' href='applicantRegistration.html?step=5&del=" + beans[i].getId() + "'>DEL</td>");
					out.println("</tr>");
				}
				
				out.println("</tbody></table>");
			}
			else {
				out.println("No University/College education currently on file.");
			}
			
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			throw new JspException(e.getMessage());
		}
		catch (SubjectException e) {
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