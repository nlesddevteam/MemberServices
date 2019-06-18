package com.esdnl.personnel.jobs.tag;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.esdnl.personnel.jobs.bean.OtherJobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.OtherJobOpportunityManager;
public class ViewOtherPostsTagHandler extends TagSupport {
	private static final long serialVersionUID = 1119575556033650326L;
	private int job_id;
	public void setJob_id(int jobId) {

		this.job_id = jobId;
	}
	public int doStartTag() throws JspException {
		JspWriter out = null;
		OtherJobOpportunityBean opp = null;
		try {
			out = pageContext.getOut();
			opp = OtherJobOpportunityManager.getOtherJobOpportunityBeanById(job_id);
			out.println("<table class='table table-striped table-condensed' style='font-size:12px;'><tbody>");			
			out.println("<tr><td class='tableTitle'>TITLE: </td>");
			out.println("<td class='tableResult'>" + opp.getTitle() + "</td></tr>");
			
			out.println("<tr><td class='tableTitle'>CLOSING DATE:</td>");
			out.println("<td class='tableResult'>" +  opp.getFormatedEndDate() + "</td></tr>");
			
			out.println("<tr><td class='tableTitle'>FILE:</td>");
			out.println("<td class='tableResult'><a href='http://www.nlesd.ca/employment/doc/"+ opp.getFilename() +"'>" +   opp.getFilename() + "</a></td></tr>");
			
			out.println("<tr><td class='tableTitle'>TYPE:</td>");
			out.println("<td class='tableResult'>" +  opp.getPostingType() + "</td></tr>");
			
			out.println("<tr><td class='tableTitle'>REGION:</td>");
			out.println("<td class='tableResult'>" +  opp.getRegion().getName() + "</td></tr>");		
			
			out.println("</tbody></TABLE>");
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

