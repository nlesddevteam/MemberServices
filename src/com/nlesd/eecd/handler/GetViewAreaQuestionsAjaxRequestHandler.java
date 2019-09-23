package com.nlesd.eecd.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.eecd.bean.EECDAreaQuestionBean;
import com.nlesd.eecd.dao.EECDAreaQuestionManger;

public class GetViewAreaQuestionsAjaxRequestHandler extends RequestHandlerImpl
{
	public GetViewAreaQuestionsAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"EECD-VIEW-ADMIN","EECD-VIEW-SHORTLIST"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("aid"),
				new RequiredFormElement("psid")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");

		try{
			if(validate_form()) {
				int id = Integer.parseInt(request.getParameter("aid"));
				int psid = Integer.parseInt(request.getParameter("psid"));
				TreeMap<Integer,EECDAreaQuestionBean> list = EECDAreaQuestionManger.getAreaQuestionsAnswers(id,psid);
				if(list.isEmpty()) {
					sb.append("<TAREA>");
					sb.append("<TQUESTION>");
					sb.append("<MESSAGE>NONE</MESSAGE>");
					sb.append("</TQUESTION>");
					sb.append("</TAREA>");
				}else {
					sb.append("<TAREA>");
					for(Map.Entry<Integer,EECDAreaQuestionBean> entry : list.entrySet()) {
						
						sb.append("<TQUESTION>");
						sb.append("<MESSAGE>SUCCESS</MESSAGE>");
						sb.append("<QTEXT>" + entry.getValue().getQuestionText() + "</QTEXT>");
						sb.append("<QSORT>" + entry.getValue().getQuestionSort() + "</QSORT>");
						sb.append("<AREAD>" + entry.getValue().getAreaDescription() + "</AREAD>");
						sb.append("<ETEACHER>" + entry.getValue().getEligibleTeachers() + "</ETEACHER>");
						sb.append("<QID>" + entry.getValue().getId() + "</QID>");
						sb.append("<AREAID>" + entry.getValue().getAreaId()+ "</AREAID>");
						sb.append("<ANSWER>" + entry.getValue().getTeacherAnswer()+ "</ANSWER>");
						sb.append("<ANSWERID>" + entry.getValue().getTeacherAnswerId()+ "</ANSWERID>");
						sb.append("</TQUESTION>");
						
					}
					sb.append("</TAREA>");
				}

			}else {
				sb.append("<TAREA>");
				sb.append("<TQUESTION>");
				sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
				sb.append("</TQUESTION>");
				sb.append("</TAREA>");
			}
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			System.out.println(xml);
			out.write(xml);
			out.flush();
			out.close();
			return null;
		}catch(Exception e){
			sb.append("<TAREA>");
			sb.append("<TQUESTION>");
			sb.append("<MESSAGE>" + e.getMessage() + "</MESSAGE>");
			sb.append("</TQUESTION>");
			sb.append("</TAREA>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			return null;
	    }
		
	}
}
