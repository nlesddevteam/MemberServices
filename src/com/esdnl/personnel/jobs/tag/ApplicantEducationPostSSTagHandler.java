package com.esdnl.personnel.jobs.tag;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import com.esdnl.personnel.jobs.bean.ApplicantEducationPostSSBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantEducationPostSSManager;
import com.esdnl.personnel.jobs.dao.DegreeManager;
import com.esdnl.util.StringUtils;
public class ApplicantEducationPostSSTagHandler extends TagSupport {

	private static final long serialVersionUID = -7224633913777535025L;
	private String showdelete;
	public int doStartTag() throws JspException {

		SimpleDateFormat sdf = null;
		JspWriter out = null;

		ApplicantProfileBean profile = null;
		ApplicantEducationPostSSBean[] beans = null;

		try {
			out = pageContext.getOut();

			profile = (ApplicantProfileBean) pageContext.getAttribute("APPLICANT", PageContext.SESSION_SCOPE);

			sdf = new SimpleDateFormat("MMMM yyyy");

			if (profile != null)
				beans = ApplicantEducationPostSSManager.getApplicantEducationPostSSBeans(profile.getSIN());

			if ((beans != null) && (beans.length > 0)) {
				
				out.println("<table class='table table-striped table-condensed' style='font-size:11px;'>");
				out.println("<thead><tr>");
				out.println("<th width='30%'>INSTITUTION</th>");
				out.println("<th width='20%'>FROM/TO</th>");
				out.println("<th width='20%'>PROGRAM/FACULTY</th>");
				out.println("<th width='*'>DEGREE/OTHER</th>");
				if(this.showdelete.equals("edit")){
					out.println("<th width='10%'>OPTIONS</th>");
				}
				out.println("</tr></thead><tbody>");
				
				for (int i = 0; i < beans.length; i++) {
					out.println("<tr>");
					out.println("<td>" + beans[i].getInstitution() + "</td>");
					out.println("<td>" + sdf.format(beans[i].getFrom()) + " - " + sdf.format(beans[i].getTo()) + "</td>");
					if(beans[i].getProgram() ==  null){
						out.println("<td><span style='color:Silver;'>N/A</span></td>");
					}else{
						out.println("<td>" + beans[i].getProgram() + "</td>");
					}

					if(beans[i].getCtype() == 1){
						if(StringUtils.isEmpty(beans[i].getDegree())){
							out.println("<td><span style='color:Silver;'>N/A</span></td>");
						}else{
							out.println("<td>"+ (!(beans[i].getDegree().equals("0")) ? DegreeManager.getDegreeBeans(beans[i].getDegree()).getTitle() : "<span style='color:Silver;'>N/A</span>") + "</td>");
						}
						
					}else if(beans[i].getCtype() ==2){
						out.println("<td>" + ApplicantEducationPostSSManager.getDiplomaCertTitleById(Integer.parseInt(beans[i].getDegree())) + "</td>");
					}else if(beans[i].getCtype() == 3){
						out.println("<td>" + ApplicantEducationPostSSManager.getDiplomaCertTitleById(Integer.parseInt(beans[i].getDegree())) + "</td>");
					}else{
						out.println("<td><span style='color:Silver;'>N/A</span></td>");
					}
					
					if(this.showdelete.equals("edit")){
						out.println("<td><a class='btn btn-xs btn-danger' href='applicantRegistrationSS.html?step=5b&del="+ beans[i].getId() + "'>DEL</td>");
					}
					
					out.println("</tr>");
				}
				out.println("</tbody></table>");
			}
			else {
				
				out.println("<script>$('#section5').removeClass('panel-success').addClass('panel-danger');</script>");
				out.println("<span style='color:DimGrey;'>No Degree(s), Diploma(s), and/or Certificate(s) currently on file.</span>");
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
}
