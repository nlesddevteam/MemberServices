package com.esdnl.personnel.jobs.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.awsd.school.SubjectDB;
import com.awsd.school.SubjectException;
import com.esdnl.personnel.jobs.bean.AssignmentEducationBean;
import com.esdnl.personnel.jobs.bean.AssignmentMajorMinorBean;
import com.esdnl.personnel.jobs.bean.AssignmentTrainingMethodBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.AssignmentEducationManager;
import com.esdnl.personnel.jobs.dao.AssignmentMajorMinorManager;
import com.esdnl.personnel.jobs.dao.AssignmentTrainingMethodManager;
import com.esdnl.personnel.jobs.dao.DegreeManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityAssignmentManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;

public class ViewPostTagHandler extends TagSupport {

	private static final long serialVersionUID = 1119575556033650326L;

	private String competitionNumber;

	public void setCompetitionNumber(String competitionNumber) {

		this.competitionNumber = competitionNumber;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		JobOpportunityBean opp = null;
		JobOpportunityAssignmentBean[] abean = null;
		AssignmentEducationBean[] edu = null;
		AssignmentMajorMinorBean[] mjr = null;
		AssignmentTrainingMethodBean[] trnmtd = null;

		try {
			out = pageContext.getOut();

			opp = JobOpportunityManager.getJobOpportunityBean(this.competitionNumber);
			abean = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(opp);

			out.println("<table class='table table-striped table-condensed' style='font-size:12px;'><tbody>");
			out.println("<tr><td class='tableTitle'>POSITION TITLE:</td><td class='tableResult'>" + opp.getPositionTitle() + "</td></tr>");
			out.println("<tr><td class='tableTitle'>COMPETITION #:</td><td class='tableResult'>" + opp.getCompetitionNumber() + "</td></tr>");

			if (abean.length > 0) {
				//build the civic address string
				School sch = null;
				if(abean[0].getLocation() > 0){
					sch = SchoolDB.getSchool(abean[0].getLocation());
				}
				if(!(sch == null)){
					StringBuilder sb = new StringBuilder();
					sb.append(abean[0].getLocationText()+ " &middot; ");
					if(!(sch.getAddress1() == null)){
						sb.append(sch.getAddress1()+ " &middot; ");
					}
					if(!(sch.getAddress2() == null)){
						sb.append(sch.getAddress2()+ " &middot; ");
					}
					if(!(sch.getTownCity() == null)){
						sb.append(sch.getTownCity());
					}
					if(!(sch.getPostalZipCode() == null)){
						sb.append(" &middot; " + sch.getPostalZipCode());
					}
					sb.append("<br /><br />");
					out.println("<tr><td class='tableTitle'>LOCATION(S):</td><td class='tableResult'>" + sb.toString() + "</td></tr>");
				}else{
					out.println("<tr><td class='tableTitle'>LOCATION(S):</td><td class='tableResult'>" + abean[0].getLocationText() + "</td></tr>");
				}

				
				for (int i = 1; i < abean.length; i++){
					//build the civic address string
					sch = SchoolDB.getSchool(abean[i].getLocation());
					if(!(sch == null)){
						StringBuilder sb = new StringBuilder();
						sb.append(abean[i].getLocationText()+ " &middot; ");
						if(!(sch.getAddress1() == null)){
							sb.append(sch.getAddress1()+ " &middot; ");
						}
						if(!(sch.getAddress2() == null)){
							sb.append(sch.getAddress2()+ " &middot; ");
						}
						if(!(sch.getTownCity() == null)){
							sb.append(sch.getTownCity());
						}
						if(!(sch.getPostalZipCode() == null)){
							sb.append(" &middot; " + sch.getPostalZipCode());
						}
						sb.append("<br /><br />");
						out.println("<tr><td class='tableTitle'>LOCATION(S):</td><td class='tableResult'>" + sb.toString() + "</td></tr>");
					}else{
						out.println("<tr><td class='tableTitle'>LOCATION(S):</td><td class='tableResult'>" + abean[i].getLocationText() + "</td></tr>");
					}
				}
					

				for (int j = 0; j < abean.length; j++) {
					System.out.println(abean[j].getAssignmentId());
					edu = AssignmentEducationManager.getAssignmentEducationBeans(abean[j]);
					
					if (edu.length > 0) {
						out.println("<tr><td class='tableTitle'>EDUCATION REQUIRED:</td><td class='tableResult'><ul><li>" + DegreeManager.getDegreeBeans(edu[0].getDegreeId()).getTitle());
						for (int k = 1; k < edu.length; k++) {
							out.print("<li>" + DegreeManager.getDegreeBeans(edu[k].getDegreeId()).getTitle());
						}						
					out.print("</ul></td></tr>");
					} else {
						out.println("<tr><td class='tableTitle'>EDUCATION REQUIRED:</td><td class='tableResult'>Not Applicable</td></tr>");
					}

					mjr = AssignmentMajorMinorManager.getAssignmentMajorMinorBeans(abean[j]);
					
					if (mjr.length > 0) {
						out.println("<tr><td class='tableTitle'>MAJOR(S) REQUIRED:</td><td class='tableResult'><ul>"
								+ ((mjr[0].getMajorId() > 0) ? "<li>"+SubjectDB.getSubject(mjr[0].getMajorId()).getSubjectName() : ""));
						for (int l = 1; l < mjr.length; l++) {
							if (mjr[l].getMajorId() > 0)
								out.print("<li>" + SubjectDB.getSubject(mjr[l].getMajorId()).getSubjectName());
						}
						
						out.print("</ul></td></tr>");
					} else {
						out.println("<tr><td class='tableTitle'>MAJOR(S) REQUIRED:</td><td class='tableResult'>Not Applicable</td></tr>");
						
					}
					
					
					if (mjr.length > 0) {
						out.println("<tr><td class='tableTitle'>MINOR(S) REQUIRED:</td><td class='tableResult'><ul>" + ((mjr[0].getMinorId() > 0) ? "<li>"+SubjectDB.getSubject(mjr[0].getMinorId()).getSubjectName() : ""));
						for (int l = 1; l < mjr.length; l++) {
							if (mjr[l].getMinorId() > 0) {
								out.print("<li>" + SubjectDB.getSubject(mjr[l].getMinorId()).getSubjectName());
						}}
						
						out.print("</ul></td></tr>");
					} else {
						out.println("<tr><td class='tableTitle'>MINOR(S) REQUIRED:</td><td class='tableResult'>Not Applicable</td></tr>");
						
					}
					
		// OLD CODE SNIPPLETS
		//----------------------------------------		
					
				//	if (edu.length > 0) {
				//		out.println("<tr><td class='tableTitle'>EDUCATION REQUIRED:</td><td class='tableResult'>" + DegreeManager.getDegreeBeans(edu[0].getDegreeId()).getTitle() + "</td></tr>");
				//		for (int k = 1; k < edu.length; k++) {
				//			out.println("<tr><td class='tableTitle'>&nbsp;</td><td class='tableResult'>" + DegreeManager.getDegreeBeans(edu[k].getDegreeId()).getTitle() + "</td></tr>");
				//		}} else {
				//		out.println("<tr><td class='tableTitle'>EDUCATION REQUIRED:</td><td class='tableResult'>Not Applicable</td></tr>");
				//	}			
					
					
		//----------------------------------------			
					
				//	if (mjr.length > 0) {
				//		out.println("<tr><td class='tableTitle'>MAJOR(S) REQUIRED:</td><td class='tableResult'>"+ ((mjr[0].getMajorId() > 0) ? SubjectDB.getSubject(mjr[0].getMajorId()).getSubjectName() : "") + "</td></tr>");
				//		for (int l = 1; l < mjr.length; l++) {
				//			if (mjr[l].getMajorId() > 0)
				//				out.println("<tr><td class='tableTitle'>&nbsp;</td><td class='tableResult'>" + SubjectDB.getSubject(mjr[l].getMajorId()).getSubjectName() + "</td></tr>");
				//		}
				//	} else {
				//		out.println("<tr><td class='tableTitle'>MAJOR(S) REQUIRED:</td><td class='tableResult'>Not Applicable</td></tr>");
				//	}
					
		//--------------------------------			
					
				//	if (mjr.length > 0) {
				//		out.println("<tr><td class='tableTitle'>MINOR(S) REQUIRED:</td><td class='tableResult'>" + ((mjr[0].getMinorId() > 0) ? SubjectDB.getSubject(mjr[0].getMinorId()).getSubjectName() : "")	+ "</td></tr>");
				//		for (int l = 1; l < mjr.length; l++) {
				//			if (mjr[l].getMinorId() > 0) {
				//				out.println("<tr><td class='tableTitle'>&nbsp;</td><td class='tableResult'>" + SubjectDB.getSubject(mjr[l].getMinorId()).getSubjectName() + "</td></tr>");
				//			}}
				//	} else {
				//		out.println("<tr><td class='tableTitle'>MINOR(S) REQUIRED:</td><td class='tableResult'>Not Applicable</td></tr>");
				//	}

		//--------------------------------		
					
					trnmtd = AssignmentTrainingMethodManager.getAssignmentTrainingMethodBeans(abean[j]);
					if (trnmtd.length > 0) {
						try {
							out.println("<tr><td class='tableTitle'>TRAINING METHOD:</td><td class='tableResult'>" + trnmtd[0].getTrainingMethod().getDescription() + "</td></tr>");
						}
						catch (NullPointerException ex) {
							System.err.println(">>>>>>>> NULL POINTER EXCEPTION: ASSIGN_ID="
									+ trnmtd[0].getJobOpportunityAssignmentId());
						}
						for (int s = 1; s < trnmtd.length; s++)
							out.println("<tr><td class='tableTitle'>&nbsp;</td><td class='tableResult'>" + trnmtd[s].getTrainingMethod().getDescription() + "</td></tr>");
					}

				}
			}
			
			out.println("<tr><td class='tableTitle'>JOB TEXT:</td><td class='tableResult'>" + opp.getJobAdText().replaceAll("<BR><BR>", "<br/>") + "</td></tr>");
			//Old code from old system.
			//out.println("<tr><td class='tableTitle'>JOB TEXT:</td><td class='tableResult'>" + opp.getJobAdText().replaceAll("\n", "<BR>") + "</td></tr>");

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
		} catch (SchoolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SKIP_BODY;
	}
}