package com.esdnl.personnel.jobs.tag;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.esdnl.util.StringUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
public class OtherJobPostsTagHandler extends TagSupport {
	private static final long serialVersionUID = 2688702748884458306L;
	private String status;
	private int type;
	private JobOpportunityBean[] value;
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
	public void setValue(JobOpportunityBean[] value) {

		this.value = value;
	}
	
	public int doStartTag() throws JspException {

		JspWriter out = null;
		JobOpportunityBean[] opps = null;
		JobOpportunityAssignmentBean[] ass = null;
		String bg_color = "";
		String bd_color = "";
			
		int JobCount = 0;
		try {
			// System.err.println("INSIDE TAG");

			out = pageContext.getOut();

			if (this.value == null){
				opps = JobOpportunityManager.getJobOpportunityBeansSupport(this.status, this.type,this.zone);
				
				
			}else{
				opps = this.value;
			}
			
			// System.err.println("Opportunities: " + opps.length);
			out.println("<script>$('document').ready(function(){");
			out.println("$('.otherJobsList"+this.type+"').DataTable({'order': [[ 0, 'desc' ]],'bLengthChange': false,'paging': false, 'lengthMenu': [[-1, 20, 50, 100, 200], ['All', 20, 50, 100, 200]] });");
			out.println("});</script>");	
			out.println("<table width='100%' class='table table-striped table-condensed otherJobsList"+this.type+"' style=\"font-size:11px;padding-top:3px;border-top:1px solid silver;\">");		
			JobCount=0;
			out.println("<thead><tr><th class='tableCB'>SELECT</th><th class='tableCompNum' width='25%'>COMPETITION #</th>"
					+ "<th class='tablePosTitle' width='45%'>POSITION</th>"
					+ "<th class='tableCompEndDate' width='20%'>END DATE</th>"
					+ "<th class='tableOptions' width='10%'>OPTIONS</th></tr></thead><tbody>");

			if (opps.length > 0) {
				for (int i = 0; i < opps.length; i++) {					
					
					JobCount++;
					bg_color = ((!opps[i].isCancelled()) ? "" : "#FFFFCC");
					bd_color = ((!opps[i].isCancelled()) ? "silver" : "#FF0000");
					
					ass = (JobOpportunityAssignmentBean[]) opps[i].toArray(new JobOpportunityAssignmentBean[0]);

					out.println("<tr id='" + opps[i].getCompetitionNumber() + "' style='background-color:" + bg_color + ";'>");
					
					out.println("<td style='vertical-align:middle;text-align:center;border-top:solid 1px " + bd_color + ";"
							+ (opps[i].isCancelled() ? "border-bottom:solid 1px #FF0000;" : "") + "'>"							
							+ "<input type='checkbox' name='comp_num' value ='" + opps[i].getCompetitionNumber() + "'/></td>");
					
					out.println("<td style='border-top:solid 1px " + bd_color + ";"
							+ (opps[i].isCancelled() ? "border-bottom:solid 1px #FF0000;" : "") + "'>"							
							+ opps[i].getCompetitionNumber() + "</td>");
					
					out.println("<td style='border-top:solid 1px " + bd_color + ";"
							+ (opps[i].isCancelled() ? "border-bottom:solid 1px #FF0000;" : "") + "'>");
					
					for (int j = 0; ((ass != null) && (j < ass.length)); j++) {
						if (!StringUtils.isEmpty(ass[j].getLocationText()))
							out.println("<b>" + ass[j].getLocationText() + "</b><br>");
					}
					
					out.println(opps[i].getPositionTitle());
					
					out.println("</td>");
					if (!opps[i].isCancelled()) {
						out.println("<td style='border-top:solid 1px " + bd_color + ";'>"
								+ opps[i].getFormatedCompetitionEndDate() + "</td>");
						out.println("<td style='border-top:solid 1px "
								+ bd_color
								+ ";'><a class='btn btn-xs btn-warning' href='view_job_post.jsp?comp_num="
								+ opps[i].getCompetitionNumber() + "'>View</a></td></tr>");
					}
					else {
						out.println("<td align='center' style='border-top:solid 1px #FF0000;border-bottom:solid 1px #FF0000;color:#FF0000;font-weight:bold;'>POSITION<br/>CANCELLED</td>");
						out.println("<td style='border-top:solid 1px #FF0000;border-bottom:solid 1px #FF0000;color:#FF0000;font-weight:bold;'>&nbsp;</td>");
						out.println("</tr>");
					}
				}
			
			}
			else {
				JobCount=0;
			out.println("<tr><td>&nbsp;</td><td >No positions available at this time. Thank you.</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
			}
			pageContext.setAttribute("JobCount", JobCount);
			out.println("</tbody></table>");           
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
