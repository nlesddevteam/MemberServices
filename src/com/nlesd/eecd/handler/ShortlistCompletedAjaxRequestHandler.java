package com.nlesd.eecd.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.common.Utils;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.velocity.VelocityUtils;
import com.nlesd.eecd.bean.EECDShortlistBean;
import com.nlesd.eecd.bean.EECDTeacherAreaBean;
import com.nlesd.eecd.dao.EECDAreaManager;
import com.nlesd.eecd.dao.EECDShortlistManager;
import com.nlesd.eecd.dao.EECDTeacherAreaManager;
public class ShortlistCompletedAjaxRequestHandler extends RequestHandlerImpl
{
	public ShortlistCompletedAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"EECD-VIEW-SHORTLIST","EECD-VIEW-ADMIN"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("aid")
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
		    	String stype = request.getParameter("stype");
		    	if(stype.equals("C")){
		    		EECDShortlistBean slbean = EECDShortlistManager.getShortlistByAreaId(id, Utils.getCurrentSchoolYear());
		    		EECDAreaManager.shortlistCompletedNew(slbean.getId(), usr.getLotusUserFullName());
		    		//send emails to list members
		    		//get the shortlisted staff
		    		ArrayList<EECDTeacherAreaBean> list = EECDTeacherAreaManager.getEECDTAShortListById(id);
		    		for(EECDTeacherAreaBean ebean : list){
		    			if(ebean.getTeacherEmail() != null){
		    				EmailBean email = new EmailBean();
		    				//employees principal
		    				email.setTo(ebean.getTeacherEmail());
		    	    		email.setFrom("ms@nlesd.ca");
		    				email.setSubject("EECD Committee Selection");
		    				HashMap<String, Object> model = new HashMap<String, Object>();
		    				// set values to be used in template
		    				model.put("tname", ebean.getTeacherName());
		    				model.put("sname", ebean.getSchoolName());
		    				model.put("areaname", ebean.getAreaDescription());
		    				email.setBody(VelocityUtils.mergeTemplateIntoString("eecd/confirm_selection.vm", model));
		    				email.send();
		    			}
		    		}
		    		
		    		
		    		
		    	}else{
		    		EECDShortlistBean slbean = EECDShortlistManager.getShortlistByAreaId(id, Utils.getCurrentSchoolYear());
		    		EECDAreaManager.unlockShortlistNew(slbean.getId(), usr.getLotusUserFullName());
		    	}
		    	message="COMPLETED";
		    }catch(Exception e){
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