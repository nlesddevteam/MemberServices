package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantEducationOtherBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.dao.ApplicantEducationOtherManager;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.util.StringUtils;

public class ApplicantCheckEducationAjaxRequestHandler extends PersonnelApplicationRequestHandlerImpl {

	public ApplicantCheckEducationAjaxRequestHandler() {

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{

		String msg="";
		String msg2="";
		super.handleRequest(request, response);
		try {
					ApplicantProfileBean profile = (ApplicantProfileBean) request.getSession(false).getAttribute("APPLICANT");
					ApplicantEducationOtherBean aoe = ApplicantEducationOtherManager.getApplicantEducationOtherBean(profile.getSIN());
					if( aoe == null){
						msg="Please complete Step 6: Education Continued before applying";
					}else{
						//check for Level of Professional Training
						if(aoe.getProfessionalTrainingLevel() == null){
							msg="Please select Level of Professional Training on Step 6: Education Continued before applying";
						}
						if(aoe.getTeachingCertificateLevel() == null){
							msg2="Please enter Present Newfoundland Teaching Certificate Level on Step 6: Education Continued before applying";
						}
						if(msg== "" && msg2 == ""){
							msg="SUCCESS";
						}
					}
					
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<REFCHECK>");
				sb.append("<RCHECK>");
				sb.append("<MESSAGE>" + msg + "</MESSAGE>");
				sb.append("<MESSAGE2>" + msg2 + "</MESSAGE2>");
				sb.append("</RCHECK>");
				sb.append("</REFCHECK>");
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
				sb.append("<REFCHECK>");
				sb.append("<RCHECK>");
				sb.append("<MESSAGE>" + StringUtils.encodeHTML2(e.getMessage()) + "</MESSAGE>");
				sb.append("<MESSAGE2>" + msg2 + "</MESSAGE2>");
				sb.append("</RCHECK>");
				sb.append("</REFCHECK>");
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
