package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.TeacherRecommendationBean;
import com.esdnl.personnel.jobs.constants.RecommendationStatus;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.RecommendationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class StopApplicantOfferAjaxRequestHandler extends RequestHandlerImpl {
	public StopApplicantOfferAjaxRequestHandler() {
		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("recid"),new RequiredFormElement("roptions")
			});
		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-STOP-OFFER"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			if (validate_form()) {
				//first get the recid
				int recid = form.getInt("recid");
				//get rec
				TeacherRecommendationBean tbean = RecommendationManager.getTeacherRecommendationBean(recid);
				//now get the candidate email
				ApplicantProfileBean abean = ApplicantProfileManager.getApplicantProfileBean(tbean.getCandidateId());
				String roptions = form.get("roptions");
				//now delete the email
				RecommendationManager.deleteOfferEmail(abean.getEmail(),tbean.getCompetitionNumber());
				//now we check to see if the rec is being deleted or status changed
				if(roptions.equals("delete")) {
					RecommendationManager.deleteTeacherRecommendationBean(recid);
				}else {
					RecommendationManager.resetOfferStatus(recid,RecommendationStatus.RECOMMENDED.getValue() );
				}
				
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<REC>");
				sb.append("<OFFER>");
				sb.append("<STATUS>SUCCESS</STATUS>");
				sb.append("<COMP>" + tbean.getCompetitionNumber() + "</COMP>");
				sb.append("</REC>");
				sb.append("</OFFER>");
				xml = StringUtils.encodeXML(sb.toString());
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}else {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<REC>");
				sb.append("<OFFER>");
				sb.append("<STATUS>" + this.validator.getErrorString() + "</STATUS>");
				sb.append("</REC>");
				sb.append("</OFFER>");
				xml = StringUtils.encodeXML(sb.toString());
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
				
			}
			catch (Exception e) {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<REC>");
				sb.append("<OFFER>");
				sb.append("<STATUS>" + e.getMessage() + "</STATUS>");
				sb.append("</REC>");
				sb.append("</OFFER>");
				xml = StringUtils.encodeXML(sb.toString());
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
