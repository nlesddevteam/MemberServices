package com.nlesd.eecd.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.common.Utils;
import com.awsd.mail.bean.EmailBean;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.velocity.VelocityUtils;
import com.nlesd.eecd.bean.EECDShortlistBean;
import com.nlesd.eecd.bean.EECDTeacherAreaBean;
import com.nlesd.eecd.dao.EECDAreaManager;
import com.nlesd.eecd.dao.EECDShortlistManager;
import com.nlesd.eecd.dao.EECDTeacherAreaManager;
public class ShortlistCompletedAjaxRequestHandler implements RequestHandler
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    HttpSession session = null;
	    User usr = null;
	    String message="";
	    session = request.getSession(false);
	    if((session != null) && (session.getAttribute("usr") != null))
	    {
	      usr = (User) session.getAttribute("usr");
	      if(!(usr.getUserPermissions().containsKey("EECD-VIEW-SHORTLIST")))
	      {
	        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
	      }
	    }
	    else
	    {
	      throw new SecurityException("User login required.");
	    }
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
	    	message=e.toString();
	    }
	    
	    
	    String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
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