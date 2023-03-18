package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SubjectDB;
import com.esdnl.personnel.jobs.bean.ApplicantEducationBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantEducationManager;
import com.esdnl.personnel.jobs.dao.DegreeManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class AdminAddEducationAjaxRequestHandler extends RequestHandlerImpl{
	public AdminAddEducationAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-ADD-EDUCATION"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("applicantid"),new RequiredFormElement("majorid"),new RequiredFormElement("minorid")
			});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			String message="";
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			if (validate_form()) {
				try {
					
					ApplicantEducationBean abean = new ApplicantEducationBean();
					abean.setSIN(form.get("applicantid"));
					abean.setInstitutionName(form.get("insname"));
					abean.setMajor(form.getInt("majorid"));
					abean.setMinor(form.getInt("minorid"));
					abean.setFrom(new Date());
					abean.setTo(new Date());
					abean.setProgramFacultyName(form.get("pfname"));
					abean.setNumberMajorCourses(0);
					abean.setNumberMinorCourses(0);

					if(!StringUtils.isEmpty(form.get("degreeid")) && !form.get("degreeid").equals("-1"))
						abean.setDegreeConferred(form.get("degreeid"));

					ApplicantEducationManager.addApplicantEducationBean(abean);	
					message="SUCCESS";
					
				}catch (Exception e) {
					
					sb.append("<EDULIST>");
					sb.append("<EDUENTRY>");
					sb.append("<MESSAGE>" +e.getMessage() + "</MESSAGE>");
					//now we send back education entries to populate
					sb.append("</EDUENTRY>");
					sb.append("</EDULIST>");
					
					xml = sb.toString().replaceAll("&", "&amp;");
					PrintWriter out = response.getWriter();
					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
					return null;
				}
				
			}else {
				message=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
			}

			sb.append("<EDULIST>");
			//sb.append("<EDUENTRY>");
			sb.append("<MESSAGE>" + message + "</MESSAGE>");
			SimpleDateFormat df = new SimpleDateFormat("MM/yy");
			try {
				ApplicantEducationBean[] elist = ApplicantEducationManager.getApplicantEducationBeans(form.get("applicantid"));
				for(ApplicantEducationBean aeb : elist) {
					//create xml to populate table
					sb.append("<EDUENTRY>");
					sb.append("<EID>").append(aeb.getId()).append("</EID>");
					sb.append("<EINS>").append(aeb.getInstitutionName()).append("</EINS>");
					sb.append("<EDATES>").append(df.format(aeb.getFrom()));
					if(aeb.getTo() != null) {
						sb.append(" - ").append(df.format(aeb.getTo())).append("</EDATES>");
					}else {
						sb.append(" - ").append("</EDATES>");
					}
					sb.append("<EPF>").append(aeb.getProgramFacultyName()).append("</EPF>");
					if(aeb.getMajor() > 0) {
						sb.append("<EMAJORS>").append(SubjectDB.getSubject(aeb.getMajor()).getSubjectName());
						if(aeb.getMajor_other() > 0) {
							sb.append(", ").append(SubjectDB.getSubject(aeb.getMajor()).getSubjectName()).append("</EMAJORS>");
						}else {
							sb.append("</EMAJORS>");
						}
					}else {
						sb.append("<EMAJORS>").append("").append("</EMAJORS>");
					}
					
					if(aeb.getMinor() > 0) {
						sb.append("<EMINORS>").append(SubjectDB.getSubject(aeb.getMinor()).getSubjectName()).append("</EMINORS>");
					}else {
						sb.append("<EMINORS>").append("").append("</EMINORS>");
					}
					sb.append("<EMAJORSCRS>").append(aeb.getNumberMajorCourses()).append("</EMAJORSCRS>");
					sb.append("<EMINORSCRS>").append(aeb.getNumberMinorCourses()).append("</EMINORSCRS>");
					if(aeb.getDegreeConferred() == null) {
						sb.append("<EDEGREE>").append("").append("</EDEGREE>");
					}else {
						sb.append("<EDEGREE>").append(DegreeManager.getDegreeBeans(aeb.getDegreeConferred()).getAbbreviation()).append("</EDEGREE>");
					}
					
					
					sb.append("</EDUENTRY>");
				}
			} catch (JobOpportunityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//sb.append("</EDUENTRY>");
			sb.append("</EDULIST>");
			xml = sb.toString().replaceAll("&", "&amp;");
			System.out.println(sb.toString());
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			return null;
		}
	
}