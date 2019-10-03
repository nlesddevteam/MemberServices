package com.esdnl.personnel.jobs.tag;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.personnel.jobs.bean.ApplicantCurrentPositionBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantCurrentPositionManager;
public class ApplicantCurrentPositionsSSTagHandler extends TagSupport{

	private static final long serialVersionUID = -7224633913777535025L;
	private String showdelete;
	public int doStartTag() throws JspException {
		JspWriter out = null;
		ApplicantProfileBean profile = null;
		HashMap<Integer,ApplicantCurrentPositionBean> map = new HashMap<Integer,ApplicantCurrentPositionBean>();
		
		try {
			out = pageContext.getOut();
			profile = (ApplicantProfileBean) pageContext.getAttribute("APPLICANT", PageContext.SESSION_SCOPE);
			
			
			if (profile != null)
				map = ApplicantCurrentPositionManager.getApplicantCurrentPositionBeanMap(profile.getSIN());

			if ((map != null) && (map.size() > 0)) {
			 out.println("<table class='table table-striped table-condensed' style='font-size:12px;'>");
			out.println("<thead><tr>");
			out.println("<th width='12%'>Work Location</th>");
			out.println("<th width='12%'>Position Union</th>");
			out.println("<th width='12%'>Position Held</th>");
			out.println("<th width='12%'>Position Type</th>");
			out.println("<th width='12%'>Position Hours/WK</th>");
			out.println("<th width='12%'>Start Date</th>");
			out.println("<th width='12%'>End Date</th>");
			out.println("<th width='*'>Options</th>");
			out.println("</tr></thead><tbody>");
					for (ApplicantCurrentPositionBean value : map.values()) {
						out.println("<TR>");
						//out.println("<TD>" + SchoolDB.getSchool(value.getSchoolId()).getSchoolName() + "</TD>");
						out.println("<TD>" + getLocationText(value.getSchoolId()) + "</TD>");
						out.println("<TD>" + value.getPositionUnion() + "</TD>");
						out.println("<TD>" + value.getPositionName() + "</TD>");
						out.println("<TD>" + value.getPositionTypeString()+ "</TD>");
						out.println("<TD>" + value.getPositionHours() + "</TD>");
						if(value.getStartDate() ==  null){
							out.println("<TD>&nbsp;</TD>");
						}else{
							out.println("<TD>" + value.getStartDateFormatted() + "</TD>");
						}
						if(value.getEndDate() ==  null){
							out.println("<TD>&nbsp;</TD>");
						}else{
							out.println("<TD>" + value.getEndDateFormatted() + "</TD>");
						}
						if(this.showdelete.equals("edit")){
							out.println("<TD><a class='btn btn-xs btn-danger' href='applicantRegistrationSS.html?step=2&del="+ value.getId() + "'>DEL</a></TD>");
						}else{
							out.println("<TD>&nbsp;</TD>");
						}
						out.println("</TR>");
						
					}
				out.println("</tbody></TABLE>");
			}else {
				out.println("<span style='color:Grey;'>Sorry, no positions currently on file.</span>");
				out.println("<script>$('#section2').removeClass('panel-success').addClass('panel-danger');</script>");	
			}
			
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new JspException(e.getMessage());
		} catch (JobOpportunityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return SKIP_BODY;
	}
	public String getShowdelete() {
		return showdelete;
	}
	public void setShowdelete(String showdelete) {
		this.showdelete = showdelete;
	}
	public String getPositionTypeText(String ptype){
		if(ptype.equals("C")){
			return "Caual";
		}else if(ptype.equals("P")){
			return "Permanent";
		}else if(ptype.equals("S")){
			return "Substitute";
		}else{
			return "Temporary";
		}
	}
	public String getLocationText(int schoolid) {

		String txt = null;

		switch (schoolid) {
		case -3000:
			txt = "Central Regional Office";
			break;
		case -2000:
			txt = "Western Regional Office";
			break;
		case -1000:
			txt = "Labrador Regional Office";
			break;
		case -999:
			txt = "District Office";
			break;
		case -998:
			txt = "Avalon Regional Office";
			break;
		case -100:
			txt = "Avalon East Region";
			break;
		case -200:
			txt = "Avalon West Region";
			break;
		case -300:
			txt = "Burin Region";
			break;
		case -400:
			txt = "Vista Region";
			break;
		default:
			try {
				txt = SchoolDB.getSchool(schoolid).getSchoolName();
			} catch (SchoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		}

		return txt;
	}
}
