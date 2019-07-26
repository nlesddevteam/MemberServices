package com.esdnl.personnel.jobs.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.awsd.school.School;
import com.awsd.school.SubjectException;
import com.esdnl.personnel.jobs.bean.AssignmentEducationBean;
import com.esdnl.personnel.jobs.bean.AssignmentMajorMinorBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;

public class ViewPostTagHandler extends TagSupport {

	private static final long serialVersionUID = 1119575556033650326L;

	private String competitionNumber;
	private JobOpportunityBean job;

	public void setCompetitionNumber(String competitionNumber) {

		this.competitionNumber = competitionNumber;
	}

	public void setJob(JobOpportunityBean job) {

		this.job = job;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		JobOpportunityBean opp = null;

		try {
			out = pageContext.getOut();

			if (this.job != null) {
				opp = this.job;
			}
			else {
				opp = JobOpportunityManager.getJobOpportunityBean(this.competitionNumber);
			}

			out.println("<table class='table table-striped table-condensed' style='font-size:12px;'><tbody>");
			out.println("<tr><td class='tableTitle'>POSITION TITLE:</td><td class='tableResult'>" + opp.getPositionTitle()
					+ "</td></tr>");
			out.println("<tr><td class='tableTitle'>COMPETITION #:</td><td class='tableResult'>" + opp.getCompetitionNumber()
					+ "</td></tr>");

			if (opp.size() > 0) {

				for (JobOpportunityAssignmentBean abean : opp) {
					School sch = abean.getSchool();

					out.println("<tr><td class='tableTitle'>LOCATION(S):</td><td class='tableResult'>");
					if (sch != null) {
						StringBuilder sb = new StringBuilder();
						sb.append(sch.getSchoolName() + " &middot; ");

						if (StringUtils.isNotBlank(sch.getAddress1())) {
							sb.append(sch.getAddress1() + " &middot; ");
						}

						if (StringUtils.isNotBlank(sch.getAddress2())) {
							sb.append(sch.getAddress2() + " &middot; ");
						}

						if (StringUtils.isNotBlank(sch.getTownCity())) {
							sb.append(sch.getTownCity());
						}

						if (StringUtils.isNotBlank(sch.getPostalZipCode())) {
							sb.append(" &middot; " + sch.getPostalZipCode());
						}

						sb.append("<br /><br />");
						out.println(sb.toString());
					}
					else {
						out.println(abean.getLocationText());
					}
					out.println("</td></tr>");
          
					if (abean.getRequiredEducationSize() > 0) {
						out.println("<tr><td class='tableTitle'>EDUCATION REQUIRED:</td><td class='tableResult'>");
						for (AssignmentEducationBean edu : abean.getRequiredEducation()) {
							out.print("&middot; " + edu.getDegree().getTitle() + "<br/>");
						}
						out.print("</td></tr>");
					}
					else {
						//out.println(
						//		"<tr><td class='tableTitle'>EDUCATION REQUIRED:</td><td class='tableResult'>Not Applicable</td></tr>");
					}

					if (abean.getRequriedMajorsOnlySize() > 0) {
						out.println("<tr><td class='tableTitle'>MAJOR(S) REQUIRED:</td><td class='tableResult'>");
						for (AssignmentMajorMinorBean mjr : abean.getRequiredMajorsOnly()) {
							out.print("&middot; " + mjr.getMajorSubject().getSubjectName() + "<br/>");
						}
						out.print("</td></tr>");
					}
					else {
						//out.println(
						//		"<tr><td class='tableTitle'>MAJOR(S) REQUIRED:</td><td class='tableResult'>Not Applicable</td></tr>");
					}

					if (abean.getRequriedMinorsOnlySize() > 0) {
						out.println("<tr><td class='tableTitle'>MINOR(S) REQUIRED:</td><td class='tableResult'>");
						for (AssignmentMajorMinorBean mir : abean.getRequiredMinorsOnly()) {
							out.print("&middot; " + mir.getMinorSubject().getSubjectName() + "<br/>");
						}
						out.print("</td></tr>");
					}
					else {
						//out.println(
						//		"<tr><td class='tableTitle'>MINOR(S) REQUIRED:</td><td class='tableResult'>Not Applicable</td></tr>");
					}

					if (abean.getRequriedTrainingMethodsSize() > 0) {
						out.println("<tr><td class='tableTitle'>TRAINING METHOD:</td><td class='tableResult'>");
						for (TrainingMethodConstant trnmtd : abean.getRequriedTrainingMethods()) {
							out.println("&middot; " + trnmtd.getDescription() + "<br/>");
						}
						out.print("</td></tr>");
					}

				}
			}

			out.println("<tr><td class='tableTitle'>JOB TEXT:</td><td class='tableResult'>"
					+ opp.getJobAdText().replaceAll("<br><br>", "<br />") + "</td></tr>");

			out.println("</tbody></table>");
		}
		catch (SubjectException e) {
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