package com.awsd.ppgp.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.ppgp.TaskDomainStrengthBean;
import com.awsd.ppgp.TaskDomainStrengthManager;
import com.awsd.ppgp.TaskSpecificTopicBean;
import com.awsd.ppgp.TaskSpecificTopicManager;
import com.awsd.ppgp.TaskSubjectBean;
import com.awsd.ppgp.TaskSubjectManager;
import com.awsd.ppgp.TaskTopicAreaBean;
import com.awsd.ppgp.TaskTopicAreaManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class GetGrowthPlanSelectValuesAjaxRequestHandler extends RequestHandlerImpl {
	public GetGrowthPlanSelectValuesAjaxRequestHandler() {
		}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
		String selecttype=form.get("stype");
		String catvalue=form.get("cid");
		String gradevalue=form.get("gid");
		String subvalue=form.get("sid");
		String topvalue=form.get("tid");
		String domainvalue=form.get("did");
		StringBuffer sbo = new StringBuffer();
		
		if(selecttype.equals("SUBJECT")){
			TaskSubjectBean[] subs = TaskSubjectManager.getTaskSubjectBeans(Integer.parseInt(gradevalue));
			if(subs.length > 0){
				sbo.append("<SOPTION>");
				sbo.append("<SID>0</SID>" );
				sbo.append("<STITLE>SELECT SUBJECT</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
				
				for(TaskSubjectBean tsb : subs){
					sbo.append("<SOPTION>");
					sbo.append("<SID>" + tsb.getSubjectID() + "</SID>" );
					sbo.append("<STITLE>" + tsb.getSubjectTitle() + "</STITLE>" );
					sbo.append("<MESSAGE>VALID</MESSAGE>");
					sbo.append("</SOPTION>");
				}
			}else{
				sbo.append("<SOPTION>");
				sbo.append("<MESSAGE>NONE</MESSAGE>");
				sbo.append("</SOPTION>");
			}
		}
		if(selecttype.equals("TOPIC")){
			TaskTopicAreaBean[] subs = TaskTopicAreaManager.getTaskTopicAreaBeans(Integer.parseInt(catvalue), Integer.parseInt(gradevalue), Integer.parseInt(subvalue));
			if(subs.length > 0){
				sbo.append("<SOPTION>");
				sbo.append("<SID>0</SID>" );
				sbo.append("<STITLE>SELECT TOPIC AREA</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
				sbo.append("<SOPTION>");
				sbo.append("<SID>1</SID>" );
				sbo.append("<STITLE>Other</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
				
				for(TaskTopicAreaBean tsb : subs){
					sbo.append("<SOPTION>");
					sbo.append("<SID>" + tsb.getTopicID() + "</SID>" );
					sbo.append("<STITLE>" + tsb.getTopicTitle() + "</STITLE>" );
					sbo.append("<MESSAGE>VALID</MESSAGE>");
					sbo.append("</SOPTION>");
				}
			}else{
				sbo.append("<SOPTION>");
				sbo.append("<SID>0</SID>" );
				sbo.append("<STITLE>SELECT TOPIC AREA</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
				sbo.append("<SOPTION>");
				sbo.append("<SID>1</SID>" );
				sbo.append("<STITLE>Other</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
			}
		}
		if(selecttype.equals("STOPIC")){
			TaskSpecificTopicBean[] subs = TaskSpecificTopicManager.getTaskSpecificTopicBeans(Integer.parseInt(catvalue), Integer.parseInt(gradevalue), Integer.parseInt(subvalue),Integer.parseInt(topvalue));
			if(subs.length > 0){
				sbo.append("<SOPTION>");
				sbo.append("<SID>0</SID>" );
				sbo.append("<STITLE>SELECT SPECIFIC TOPIC AREA</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
				sbo.append("<SOPTION>");
				sbo.append("<SID>1</SID>" );
				sbo.append("<STITLE>Other</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
				
				for(TaskSpecificTopicBean tsb : subs){
					sbo.append("<SOPTION>");
					sbo.append("<SID>" + tsb.getSpecificTopicID() + "</SID>" );
					sbo.append("<STITLE>" + tsb.getSpecificTopicTitle() + "</STITLE>" );
					sbo.append("<MESSAGE>VALID</MESSAGE>");
					sbo.append("</SOPTION>");
				}
			}else{
				sbo.append("<SOPTION>");
				sbo.append("<SID>0</SID>" );
				sbo.append("<STITLE>SELECT SPECIFIC TOPIC AREA</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
				sbo.append("<SOPTION>");
				sbo.append("<SID>1</SID>" );
				sbo.append("<STITLE>Other</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
			}
		}
		if(selecttype.equals("TOPICOTHER")){
			TaskTopicAreaBean[] subs = TaskTopicAreaManager.getTaskTopicAreaBeans(Integer.parseInt(catvalue));
			
			if(subs.length > 0){
				sbo.append("<SOPTION>");
				sbo.append("<SID>0</SID>" );
				sbo.append("<STITLE>SELECT TOPIC AREA</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
				sbo.append("<SOPTION>");
				sbo.append("<SID>1</SID>" );
				sbo.append("<STITLE>Other</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
				
				for(TaskTopicAreaBean tsb : subs){
					sbo.append("<SOPTION>");
					sbo.append("<SID>" + tsb.getTopicID() + "</SID>" );
					sbo.append("<STITLE>" + tsb.getTopicTitle() + "</STITLE>" );
					sbo.append("<MESSAGE>VALID</MESSAGE>");
					sbo.append("</SOPTION>");
				}
			}else{
				sbo.append("<SOPTION>");
				sbo.append("<SID>0</SID>" );
				sbo.append("<STITLE>SELECT TOPIC AREA</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
				sbo.append("<SOPTION>");
				sbo.append("<SID>1</SID>" );
				sbo.append("<STITLE>Other</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
			}
		}
		if(selecttype.equals("STOPICOTHER")){
			TaskSpecificTopicBean[] subs = TaskSpecificTopicManager.getTaskSpecificTopicBeans(Integer.parseInt(catvalue),Integer.parseInt(topvalue));
			if(subs.length > 0){
				sbo.append("<SOPTION>");
				sbo.append("<SID>0</SID>" );
				sbo.append("<STITLE>SELECT SPECIFIC TOPIC AREA</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
				sbo.append("<SOPTION>");
				sbo.append("<SID>1</SID>" );
				sbo.append("<STITLE>Other</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
				
				for(TaskSpecificTopicBean tsb : subs){
					sbo.append("<SOPTION>");
					sbo.append("<SID>" + tsb.getSpecificTopicID() + "</SID>" );
					sbo.append("<STITLE>" + tsb.getSpecificTopicTitle() + "</STITLE>" );
					sbo.append("<MESSAGE>VALID</MESSAGE>");
					sbo.append("</SOPTION>");
				}
			}else{
				sbo.append("<SOPTION>");
				sbo.append("<SID>0</SID>" );
				sbo.append("<STITLE>SELECT SPECIFIC TOPIC AREA</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
				sbo.append("<SOPTION>");
				sbo.append("<SID>1</SID>" );
				sbo.append("<STITLE>Other</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
			}
		}
		if(selecttype.equals("STRENGTH")){
			TaskDomainStrengthBean[] subs=  TaskDomainStrengthManager.getTaskDomainStrengthBeansById(Integer.parseInt(domainvalue));
			if(subs.length > 0){
				sbo.append("<SOPTION>");
				sbo.append("<SID>0</SID>" );
				sbo.append("<STITLE>SELECT STRENGTH</STITLE>" );
				sbo.append("<MESSAGE>VALID</MESSAGE>");
				sbo.append("</SOPTION>");
				
				for(TaskDomainStrengthBean tsb : subs){
					sbo.append("<SOPTION>");
					sbo.append("<SID>" + tsb.getStrengthID()+ "</SID>" );
					sbo.append("<STITLE>" + tsb.getStrengthTitle() + "</STITLE>" );
					sbo.append("<MESSAGE>VALID</MESSAGE>");
					sbo.append("</SOPTION>");
				}
			}else{
				sbo.append("<SOPTION>");
				sbo.append("<MESSAGE>NONE</MESSAGE>");
				sbo.append("</SOPTION>");
			}
		}
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		sb.append("<PPGP>");
		sb.append(sbo.toString());
		sb.append("</PPGP>");
		xml = sb.toString().replaceAll("&", "&amp;");
		System.out.println(xml);
		PrintWriter out = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		out.write(xml);
		out.flush();
		out.close();
			
        return null;
	}
}
