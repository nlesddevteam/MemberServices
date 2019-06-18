package com.awsd.ppgp.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.ppgp.PPGPDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class DeleteGrowthPlanGoalAjaxRequestHandler extends RequestHandlerImpl {
	public DeleteGrowthPlanGoalAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"PPGP-VIEW"
			};
		
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
		Boolean policyRead = null;
		String message="";
		
		
		policyRead = (Boolean) session.getAttribute("PPGP-POLICY");

		if ((policyRead == null)
				&& !(usr.checkPermission("PPGP-VIEW-SUMMARY") || usr.checkPermission("PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST"))) {
			message="Invalid Permissions";
		}else{
			if (form.exists("gid")) {

				if(PPGPDB.deletePPGPGoal(form.getInt("gid"))){
					
					message="SUCCESS";
				}else{
					message="Could not delete goal from database";
				}
				
			}
			else {
				message="Goal ID required for deletion.";
			}
			
		}
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		sb.append("<PPGP>");
		sb.append("<GOAL>");
		sb.append("<MESSAGE>" + message + "</MESSAGE>");
		sb.append("</GOAL>");
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