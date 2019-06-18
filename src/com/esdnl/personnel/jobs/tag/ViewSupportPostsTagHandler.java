package com.esdnl.personnel.jobs.tag;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.JobOpportunityAssignmentManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
public class ViewSupportPostsTagHandler extends TagSupport {

	private static final long serialVersionUID = 1119575556033650326L;

	private String competitionNumber;

	public void setCompetitionNumber(String competitionNumber) {

		this.competitionNumber = competitionNumber;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		JobOpportunityBean opp = null;
		JobOpportunityAssignmentBean[] abean = null;

		try {
			out = pageContext.getOut();

			opp = JobOpportunityManager.getJobOpportunityBean(this.competitionNumber);
			abean = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(opp);

			out.println("<TABLE width='100%' cellpadding='0' cellspacing='0' border='0'>");
			out.println("<TR><TD colspan='2' class='displayPositionTitle'>" + opp.getPositionTitle() + "</TD></TR>");

			out.println("<TR style='padding-top:5px;'><TD  class='displayCompetitionNumber'>Competition #:</TD><TD width='*' class='displayCompetitionNumber'> "
					+ opp.getCompetitionNumber() + "</TD></TR>");

			if (abean.length > 0) {
				//build the civic address string
				School sch = null;
				if(abean[0].getLocation() > 0){
					sch = SchoolDB.getSchool(abean[0].getLocation());
				}
				if(!(sch == null)){
					StringBuilder sb = new StringBuilder();
					sb.append(abean[0].getLocationText()+ "<br />");
					if(!(sch.getAddress1() == null)){
						sb.append(sch.getAddress1()+ "<br />");
					}
					if(!(sch.getAddress2() == null)){
						sb.append(sch.getAddress2()+ "<br />");
					}
					if(!(sch.getTownCity() == null)){
						sb.append(sch.getTownCity());
					}
					if(!(sch.getPostalZipCode() == null)){
						sb.append(", " + sch.getPostalZipCode());
					}
					sb.append("<br /><br />");
					out.println("<TR  style='padding-top:5px;'><TD  class='displayLocation'>Location(s):</TD><TD width='*' class='displayCompetitionNumber'><B>"
							+ sb.toString() + "</B></TD></TR>");
				}else{
					out.println("<TR  style='padding-top:5px;'><TD  class='displayLocation'>Location(s):</TD><TD width='*' class='displayCompetitionNumber'><B>"
							+ abean[0].getLocationText() + "</B></TD></TR>");
				}

				
				for (int i = 1; i < abean.length; i++){
					//build the civic address string
					sch = SchoolDB.getSchool(abean[i].getLocation());
					if(!(sch == null)){
						StringBuilder sb = new StringBuilder();
						sb.append(abean[i].getLocationText()+ "<br />");
						if(!(sch.getAddress1() == null)){
							sb.append(sch.getAddress1()+ "<br />");
						}
						if(!(sch.getAddress2() == null)){
							sb.append(sch.getAddress2()+ "<br />");
						}
						if(!(sch.getTownCity() == null)){
							sb.append(sch.getTownCity());
						}
						if(!(sch.getPostalZipCode() == null)){
							sb.append("' " + sch.getPostalZipCode());
						}
						sb.append("<br /><br />");
						out.println("<TR  style='padding-top:5px;'><TD  class='displayLocation'>Location(s):</TD><TD width='*' class='displayCompetitionNumber'><B>"
								+ sb.toString() + "</B></TD></TR>");
					}else{
						out.println("<TR  style='padding-top:5px;'><TD  class='displayLocation'>Location(s):</TD><TD width='*' class='displayCompetitionNumber'><B>"
								+ abean[i].getLocationText() + "</B></TD></TR>");
					}
				}
			}
					


			out.println("<TR><TD  colspan='2' class='displayAdText'>" + opp.getJobAdText().replaceAll("\n", "<BR>")
					+ "</TD></TR>");

			out.println("</TABLE>");
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
