package com.nlesd.eecd.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.eecd.bean.EECDAreaQuestionBean;
import com.nlesd.eecd.dao.EECDAreaQuestionManger;

public class AddUpdateTeacherQuestionsAjaxRequestHandler extends RequestHandlerImpl
{
	public AddUpdateTeacherQuestionsAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"EECD-VIEW"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("txtqanswer"),
				new RequiredFormElement("txtqid"),
				new RequiredFormElement("txtareaid")
			});
	}
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    String message="";
	    super.handleRequest(request, response);
	    try{
	    	String answers[] = form.getArray("txtqanswer");
	    	String qids[] = form.getArray("txtqid");
	    	String areaids[] = form.getArray("txtareaid");
	    	String answerid[] = form.getArray("txtanswerid");
	    	int x=0;
	    	for (String s : answers) {
	    		EECDAreaQuestionBean ebean = new EECDAreaQuestionBean();
	    		ebean.setAreaId(Integer.parseInt(areaids[x]));
	    		ebean.setId(Integer.parseInt(qids[x]));
	    		ebean.setTeacherAnswer(s);
	    		ebean.setPersonnelId(usr.getPersonnel().getPersonnelID());
	    		if(Integer.parseInt(answerid[x]) > 0) {
	    			//update
	    			ebean.setTeacherAnswerId(Integer.parseInt(answerid[x]));
	    			EECDAreaQuestionManger.updateTeacherAreaQuestion(ebean);
	    		}else {
	    			//we add them
	    			EECDAreaQuestionManger.addTeacherAreaQuestion(ebean);
	    		}
	    		
	    		x++;
	    	}
	    	message="SUCCESS";
	    	
	    }catch(Exception e){
	    	message=e.toString();
	    }
	    
	    
	    String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		sb.append("<CAREAS>");
		sb.append("<CAREA>");
		sb.append("<MESSAGE>" + message + "</MESSAGE>");
		sb.append("</CAREA>");
		sb.append("</CAREAS>");
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
