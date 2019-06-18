package com.esdnl.personnel.jobs.tag;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.esdnl.util.StringUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.OtherJobOpportunityBean;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.OtherJobOpportunityManager;
public class OtherJobPostsOrigTagHandler extends TagSupport {
	private static final long serialVersionUID = 2688702748884458306L;
	private String status;
	private int type;
	private OtherJobOpportunityBean[] value;
	private int zone;
	public void setStatus(String status) {

		this.status = status;
	}

	public void setType(int type) {

		this.type = type;
	}
	public void setZone(int zone) {

		this.zone = zone;
	}
	public void setValue(OtherJobOpportunityBean[] value) {

		this.value = value;
	}
	
	public int doStartTag() throws JspException {

		JspWriter out = null;
		OtherJobOpportunityBean[] opps = null;
		//JobOpportunityAssignmentBean[] ass = null;
		String bg_color = "";
		String bd_color = "";
		int JobCount = 0;
		try {
			// System.err.println("INSIDE TAG");

			out = pageContext.getOut();

			if (this.value == null){
				if(this.status == "ADMIN"){
					if(this.type == 0){
						opps = OtherJobOpportunityManager.getOtherJobOpportunityBeans();
					}else{
						opps = OtherJobOpportunityManager.getOtherJobOpportunityBeanByZone(this.type);
					}
				}else{
					opps = OtherJobOpportunityManager.getOtherJobOpportunityBeanByRegion(this.type);
				}
			
				
			}else{
				opps = this.value;
			}

			// System.err.println("Opportunities: " + opps.length);

			out.println("<table class=\"table table-striped table-condensed\" style=\"font-size:12px;padding-top:3px;border-top:1px solid silver;\">");		
			JobCount=0;
			out.println("<thead><tr><th class='tableCompNum'>Title</th>"
					+ "<th class='tablePosTitle'>Competition End Date</th>"
					+ "<th class='tableCompEndDate'>Region</th>"
					+ "<th class='tableOptions'>Options</th></tr></thead>");

			if (opps.length > 0) {
				for (int i = 0; i < opps.length; i++) {					
					
					JobCount++;
					bg_color = ((!opps[i].isCancelled()) ? "" : "#FFFFCC");
					bd_color = ((!opps[i].isCancelled()) ? "silver" : "#FF0000");
					
					out.println("<tr id='" + opps[i].getTitle() + "' style='background-color:" + bg_color + ";'>");
					
					out.println("<td style='border-top:solid 1px " + bd_color + ";"
							+ (opps[i].isCancelled() ? "border-bottom:solid 1px #FF0000;" : "") + "'>"							
							+ "<input type='checkbox' name='comp_num' value ='" + opps[i].getTitle() + "'/>"
							+ opps[i].getTitle()+ "</td>");
					
					out.println("<td style='border-top:solid 1px " + bd_color + ";"
							+ (opps[i].isCancelled() ? "border-bottom:solid 1px #FF0000;" : "") + "'>");
					out.println(opps[i].getFormatedEndDate());
					
					out.println("</td>");
					if (!opps[i].isCancelled()) {
						out.println("<td style='border-top:solid 1px " + bd_color + ";'>"
								+ opps[i].getRegion().getZone().getZoneName().toUpperCase() + "</td>");
						out.println("<td style='border-top:solid 1px "
								+ bd_color
								+ ";'><a class='btn btn-xs btn-warning' href='view_other_job_post.jsp?job_id="
								+ opps[i].getId() + "'>View</a></td></tr>");
					}
					else
						out.println("<td align='center' colspan='2' style='border-top:solid 1px #FF0000;border-bottom:solid 1px #FF0000;color:#FF0000;font-weight:bold;'>POSITION CANCELLED</td>");
				}
			
			}
			else {
				JobCount=0;
			out.println("<tr><td colspan='4'>No positions available at this time. Thank you.</td></tr>");
			}
			pageContext.setAttribute("JobCount", JobCount);
			out.println("</table>");           
			// System.err.println("TAG FINISHED");
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
