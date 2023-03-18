package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.RequestToHireBean;
import com.esdnl.personnel.jobs.bean.TeacherRecommendationBean;
import com.esdnl.personnel.jobs.constants.EmploymentConstant;
import com.esdnl.personnel.jobs.constants.RTHPositionTypeConstant;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.RecommendationManager;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
import com.nlesd.msauditlog.bean.MsAuditLogBean;

public class UpdateRecommendationAjaxRequestHandler extends RequestHandlerImpl {

	public UpdateRecommendationAjaxRequestHandler() {
		requiredPermissions = new String[] { "PERSONNEL-ADMIN-EDIT-RECOMMENDATION" };
		this.validator = new FormValidator(
				new FormElement[] { new RequiredFormElement("rid", "Recommendation Id is required") });
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (validate_form()) {
			try {
				//set initial properties for audit log
				
				MsAuditLogBean adbean = new MsAuditLogBean();
				adbean.setMalAppName("MyHRP");
				adbean.setMalAction("Update Recommendation");
				adbean.setMalBy(usr.getPersonnel().getPersonnelID());
				adbean.setMalObjectKey(form.getInt("rid"));
				
				String utype = form.get("ut");
				TeacherRecommendationBean rbean = RecommendationManager.getTeacherRecommendationBean(form.getInt("rid"));
				RequestToHireBean rthbean = RequestToHireManager.getRequestToHireByCompNum(rbean.getJob().getCompetitionNumber());
				AdRequestBean adrbean = AdRequestManager.getAdRequestBean(rbean.getCompetitionNumber());
				StringBuilder sbal = new StringBuilder();
				if(utype.equals("CS")) {
					if(rbean.getJob().isSupport()) {
						
						RecommendationManager.updateTeacherRecommendationEmpStatus(rthbean.getId(), form.getInt("cstatus"), "S");
						//audit log entry
						sbal.append("Updated employment status value from: ");
						sbal.append(RTHPositionTypeConstant.get(rthbean.getPositionType()).getDescription());
						sbal.append(" to " + RTHPositionTypeConstant.get(form.getInt("cstatus")).getDescription());
						adbean.setMalNotes(sbal.toString());
					}else {
						RecommendationManager.updateTeacherRecommendationEmpStatus(form.getInt("rid"), form.getInt("cstatus"), "T");
						//audit log entry
						sbal.append("Updated employment status value from: ");
						sbal.append(rbean.getEmploymentStatus().getDescription());
						sbal.append(" to " + EmploymentConstant.get(form.getInt("cstatus")).getDescription());
						adbean.setMalNotes(sbal.toString());
					}
				}else if(utype.equals("SD")) {
					String ndate = form.get("dvalue");
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date newdate = format.parse(ndate);
					if(rbean.getJob().isSupport()) {
						RecommendationManager.updateTeacherRecommendationStartDate(rthbean.getId(), newdate, "S");
						sbal.append("Updated Start Date value from: ");
						sbal.append(rthbean.getStartDateFormattedRec());
						sbal.append(" to " + ndate);
						adbean.setMalNotes(sbal.toString());
					}else {
						RecommendationManager.updateTeacherRecommendationStartDate(rbean.getJob().getAdRequest().getId(), newdate, "T");
						sbal.append("Updated Start Date value from: ");
						sbal.append(adrbean.getFormatedStartDate());
						SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
						sbal.append(" to " + formata.format(newdate));
						adbean.setMalNotes(sbal.toString());
					}
				}else if(utype.equals("ED")) {
					if(form.get("dvalue") == null || form.get("dvalue") == "") {
						//removing end date
						if(rbean.getJob().isSupport()) {
							RecommendationManager.updateTeacherRecommendationEndDate(rthbean.getId(), null, "S");
							if(rthbean.getStartDate() != null) {
								sbal.append("Updated End Date value from: ");
								sbal.append(rthbean.getEndDateFormattedRec());
								sbal.append(" to ");
								adbean.setMalNotes(sbal.toString());
							}
							
						}else {
							RecommendationManager.updateTeacherRecommendationEndDate(rbean.getJob().getAdRequest().getId(), null, "T");
							if(adrbean.getFormatedEndDate() != null) {
								sbal.append("Updated End Date value from: ");
								sbal.append(adrbean.getFormatedEndDate());
								sbal.append(" to ");
								adbean.setMalNotes(sbal.toString());
							}
						}
						
					}else {
						String ndate = form.get("dvalue");
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date newdate = format.parse(ndate);
						if(rbean.getJob().isSupport()) {
							RecommendationManager.updateTeacherRecommendationEndDate(rthbean.getId(), newdate, "S");
							sbal.append("Updated End Date value from: ");
							if(rthbean.getEndDate() == null) {
								sbal.append("");
							}else {
								sbal.append(rthbean.getEndDateFormattedRec());
							}
							sbal.append(" to " + ndate);
							adbean.setMalNotes(sbal.toString());
						}else {
							RecommendationManager.updateTeacherRecommendationEndDate(rbean.getJob().getAdRequest().getId(), newdate, "T");
							sbal.append("Updated End Date value from: ");
							if(adrbean.getFormatedEndDate() ==  null) {
								sbal.append("");
							}else{
								sbal.append(adrbean.getFormatedEndDate());
							}
							SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
							sbal.append(" to " + formata.format(newdate));
							adbean.setMalNotes(sbal.toString());
						}
					}
				}else if(utype.equals("SC")) {
					//
					RecommendationManager.updateTeacherRecommendationSpecialConditions(form.getInt("rid"), form.get("sc"), form.get("scc"));
					
					sbal.append("Updated Special Conditions value from: ");
					sbal.append(rbean.getSpecialConditions());
					sbal.append(" to " + form.get("sc"));
					if(rbean.getSpecialConditionsComment() ==  null) {
						sbal.append("Updated Special Conditions Comments value from: ");
						sbal.append("");
					}else {
						sbal.append("Updated Special Conditions Comments value from: ");
						sbal.append(rbean.getSpecialConditionsComment());
					}
					if(form.get("scc") ==  null) {
						sbal.append(" to ");
					}else {
						sbal.append(" to ");
						sbal.append(form.get("scc"));
					}
						
					adbean.setMalNotes(sbal.toString());
				}
				//add audit bean
				add_audit_log(adbean);

			} catch (Exception e) {
				sb.append("<RLIST>");
				sb.append("<UPDATEREC>");
				sb.append("<RSTATUS>" + e.getMessage() +"</RSTATUS>");
				sb.append("</UPDATEREC>");
				sb.append("</RLIST>");
				xml = StringUtils.encodeXML(sb.toString());
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}

			
			sb.append("<RLIST>");
			sb.append("<UPDATEREC>");
			sb.append("<RSTATUS>SUCCESS</RSTATUS>");
			sb.append("</UPDATEREC>");
			sb.append("</RLIST>");
			xml = StringUtils.encodeXML(sb.toString());
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		} else {
			sb.append("<RLIST>");
			sb.append("<UPDATEREC>");
			sb.append("<RSTATUS>" + this.validator.getErrorString() +"</RSTATUS>");
			sb.append("</UPDATEREC>");
			sb.append("</RLIST>");
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