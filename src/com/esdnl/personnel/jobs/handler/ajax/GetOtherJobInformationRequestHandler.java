package com.esdnl.personnel.jobs.handler.ajax;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.OtherJobApplicantBean;
import com.esdnl.personnel.jobs.bean.OtherJobOpportunityBean;
import com.esdnl.personnel.jobs.dao.OtherJobApplicantManager;
import com.esdnl.personnel.jobs.dao.OtherJobOpportunityManager;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.util.StringUtils;
public class GetOtherJobInformationRequestHandler extends PublicAccessRequestHandlerImpl {
	public GetOtherJobInformationRequestHandler() {

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
		String msg="";
		try {
				Date datecheck = new Date();
				Integer jobid = Integer.parseInt(request.getParameter("jobid"));
				ApplicantProfileBean profile = null;
				profile = (ApplicantProfileBean) request.getSession().getAttribute("APPLICANT");
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<JOB>");
			    if(profile == null){
			    	msg="Please login to apply for position";
			    }else{
			    	OtherJobOpportunityBean obean =  OtherJobOpportunityManager.getOtherJobOpportunityBeanById(jobid);
			    	
			    	sb.append("<DETAILS>");
			    	sb.append("<ID>" + obean.getId() + "</ID>");
			    	sb.append("<TITLE>" + obean.getTitle() + "</TITLE>");
			    	sb.append("<FILENAME>" + obean.getFilename() + "</FILENAME>");
			    	sb.append("<POSTINGTYPE>" + obean.getPostingType().getDescription() + "</POSTINGTYPE>");
			    	sb.append("<REGION>" + obean.getRegion().getName() + "</REGION>");
			    	//now we find out the status
			    	if(!(obean.getCancelledDate() == null)){
			    		sb.append("<STATUS>Cancelled</STATUS>");
			    		sb.append("<STATUSDATE>" + obean.getFormatedCancelledDate() + "</STATUSDATE>");
			    	}else if(obean.getEndDate().before(datecheck)){
			    		sb.append("<STATUS>Closed</STATUS>");
			    		sb.append("<STATUSDATE>" + obean.getFormatedEndDate() + "</STATUSDATE>");
			    	}else{
			    		sb.append("<STATUS>Open</STATUS>");
			    		sb.append("<STATUSDATE>" + obean.getFormatedEndDate() + "</STATUSDATE>");
			    	}
			    	//now we check to see if they applied for the job
			    	OtherJobApplicantBean oabean= OtherJobApplicantManager.checkApplicantAppliedFor(profile, jobid);
			    	if(oabean == null){
			    		sb.append("<APPLIED>NO</APPLIED>");
			    	}else{
			    		sb.append("<APPLIED>YES</APPLIED>");
			    		sb.append("<APPLIEDDATE>"  + oabean.getAppliedDateFormatted() + "</APPLIEDDATE>");
			    	}
			    	
			    	msg="FOUND";
			    	
			    }
				sb.append("<MESSAGE>" + msg + "</MESSAGE>");
				sb.append("</DETAILS>");
				sb.append("</JOB>");
				xml = StringUtils.encodeXML(sb.toString());

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
			catch (Exception e) {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<JOB>");
				sb.append("<DETAILS>");
				sb.append("<MESSAGE>" + StringUtils.encodeHTML2(e.getMessage()) + "</MESSAGE>");
				sb.append("</DETAILS>");
				sb.append("</JOB>");
				xml = StringUtils.encodeXML(sb.toString());
				System.out.println(xml);
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
		
		return null;
  }

}
