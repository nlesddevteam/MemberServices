package com.esdnl.personnel.jobs.tag;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import com.esdnl.personnel.jobs.bean.ApplicantEmploymentSSBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantEmploymentSSManager;
public class ApplicantEmploymentSSTagHandler extends TagSupport {

	private static final long serialVersionUID = -7224633913777535025L;
	private String showdelete;
	public int doStartTag() throws JspException {

		SimpleDateFormat sdf = null;
		JspWriter out = null;

		ApplicantProfileBean profile = null;
		ArrayList<ApplicantEmploymentSSBean> beans = null;

		try {
			out = pageContext.getOut();

			profile = (ApplicantProfileBean) pageContext.getAttribute("APPLICANT", PageContext.SESSION_SCOPE);

			sdf = new SimpleDateFormat("dd-MMM-yyyy");

			if (profile != null)
				beans = ApplicantEmploymentSSManager.getApplicantEmploymentSSBeanBySin(profile.getSIN());

			
			if ((beans != null) && (beans.size() > 0)) {
				
				out.println("<table class='table table-striped table-condensed' style='font-size:11px;'>");
					out.println("<thead><tr style='font-weight:bold;'>");
					out.println("<th width='20%'>COMPANY/ADDRESS</th>");
					out.println("<th width='20%'>SUPERVISOR/CONTACT</th>");
					out.println("<th width='20%'>JOB TITLE/TIME</th>");
					out.println("<th width='*'>DUTIES/REASON FOR LEAVING</th>");
					if(this.showdelete.equals("edit")){
						out.println("<th width='10%'>OPTIONS</th>");
					}
					out.println("</tr></thead><tbody>");
				
				for (int i = 0; i < beans.size(); i++) {
					
					out.println("<tr>");					
					out.println("<td>" + beans.get(i).getCompany()+ "<br/>"+beans.get(i).getAddress()+"</td>");
					out.println("<td>" + beans.get(i).getSupervisor()+ "<br/><b>Tel:</b> "+beans.get(i).getPhoneNumber()+"<br/>");
					
					
					
					if (beans.get(i).getContact().equals("Y")) {
						out.println("<span style='background-color:Green;color:White;'>&nbsp;OK TO CONTACT&nbsp;</span></td>");
					} else {
						out.println("<span style='background-color:Red;color:White;'>&nbsp;DO NOT CONTACT&nbsp;</span></td>");	
						
					}
						
					
					out.println("<td>" + beans.get(i).getJobTitle()+ "<br/><b>From:</b> "+((beans.get(i).getFromDate() !=null)?(sdf.format(beans.get(i).getFromDate())):"N/A") + "<br/><b>To:</b> "+((beans.get(i).getToDate() !=null)?(sdf.format(beans.get(i).getToDate())):"N/A")+"</td>");
					out.println("<td><b>Duties:</b> " + beans.get(i).getDuties()+ "<br/><b>Left/Other:</b> ");
					
					if(beans.get(i).getReason() == 6){
						out.println(beans.get(i).getReasonForLeaving() == null ? " " : beans.get(i).getReasonForLeaving() + "</td>");
					}else{
						out.println(getReasonText(beans.get(i).getReason()) + "</td>");
					}
					
					if(this.showdelete.equals("edit")){
						out.println("<td><a class='btn btn-xs btn-danger' href='applicantRegistrationSS.html?step=3&del="+ beans.get(i).getId() + "'>DEL</a></td>");
						
					}
					
					out.println("</tr>");	
					
				
					
				} 
				out.println("</tbody>");
				out.println("</table>");
				
				
				
			}
			else {
				
				out.println("<span style='color:Grey;'>No employment history currently on file.</span>");
				out.println("<script>$('#section3').removeClass('panel-success').addClass('panel-danger');</script>");	
				
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
	public String getShowdelete() {
		return showdelete;
	}
	public void setShowdelete(String showdelete) {
		this.showdelete = showdelete;
	}
	private String getReasonText(int reason){
		String reasonstring="";
		if( reason == 0){
			reasonstring="Current Position";
		}else if( reason == 1){
			reasonstring="Layoff";
		}else if( reason == 2){
			reasonstring="Resignation";
		}else if( reason == 3){
			reasonstring="Retirement";
		}else if( reason == 4){
			reasonstring="Temporary Postion";
		}else if( reason == 5){
			reasonstring="Contract Ended";
		}else{
			reasonstring="Other";
		}
		
		return reasonstring;
	}
}