package com.nlesd.eecd.handler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.common.Utils;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.eecd.bean.EECDShortlistBean;
import com.nlesd.eecd.dao.EECDShortlistManager;
import com.nlesd.eecd.dao.EECDTeacherAreaManager;
public class AddToShortlistAjaxRequestHandler extends RequestHandlerImpl
{
	public AddToShortlistAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"EECD-VIEW-SHORTLIST"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("aid"),
				new RequiredFormElement("areaid")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		String message="";
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (validate_form()) {
			try{
				int id = Integer.parseInt(request.getParameter("aid"));
				int aid = Integer.parseInt(request.getParameter("areaid"));
				int shortlistid=-1;
				//first we check to see if shortlist alreay started
				EECDShortlistBean ebean = EECDShortlistManager.getShortlistByAreaId(aid, Utils.getCurrentSchoolYear());
				if(ebean.getId() == 0) {
					//add the shortlist
					shortlistid = EECDShortlistManager.addAreaShortList(Utils.getCurrentSchoolYear(), aid);
				}else {
					shortlistid=ebean.getId();
				}
				//next we update that they were shortlist with list id
				EECDTeacherAreaManager.addToShortlistNew(id,shortlistid);
				message="ADDED";
			}catch(Exception e){
				message=e.toString();
				sb.append("<TAREAS>");
				sb.append("<TAREA>");
				sb.append("<MESSAGE>" + e.getMessage() + "</MESSAGE>");
				sb.append("</TAREA>");
				sb.append("</TAREAS>");
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
		sb.append("<TAREAS>");
		sb.append("<TAREA>");
		sb.append("<MESSAGE>" + message + "</MESSAGE>");
		sb.append("</TAREA>");
		sb.append("</TAREAS>");
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
