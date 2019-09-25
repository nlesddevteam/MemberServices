package com.nlesd.bcs.handler;

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
import com.nlesd.bcs.bean.FileHistoryBean;
import com.nlesd.bcs.dao.FileHistoryManager;

public class GetFileHistoryAjaxRequestHandler extends RequestHandlerImpl {
	public GetFileHistoryAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid"),
				new RequiredFormElement("ftype")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (validate_form()) {
			try {
				int objectid = form.getInt("cid");
				int objecttype = form.getInt("ftype");
				TreeMap<Integer, FileHistoryBean> tmap = FileHistoryManager.getFileHistory(objectid, objecttype);
				sb.append("<FILES>");
				if(tmap.size() > 0){
					
					for(Map.Entry<Integer,FileHistoryBean> entry : tmap.entrySet()) {
						  FileHistoryBean fhb = entry.getValue();
						  	sb.append("<FILE>");
						  	sb.append("<MESSAGE>SUCCESS</MESSAGE>");
							sb.append("<ID>" + fhb.getId() + "</ID>");
							sb.append("<FILENAME>" + fhb.getFileName() + "</FILENAME>");
							sb.append("<FILEACTION>" + fhb.getFileAction() + "</FILEACTION>");
							sb.append("<ACTIONBY>" + fhb.getActionBy() + "</ACTIONBY>");
							sb.append("<ACTIONDATE>" + fhb.getActionDateFormatted()+ "</ACTIONDATE>");
							sb.append("<PARENTOBJECTID>" + fhb.getParentObjectId() + "</PARENTOBJECTID>");
							sb.append("<PARENTOBJECTTYPE>" + fhb.getParentObjectType() + "</PARENTOBJECTTYPE>");
							if(fhb.getPathType() == "V") {
								sb.append("<FILEPATH>" + request.getContextPath() + "/BCS/documents/vehicledocs/" + "</FILEPATH>" );
							}else {
								sb.append("<FILEPATH>" + request.getContextPath() + "/BCS/documents/employeedocs/" + "</FILEPATH>" );
							}
							sb.append("</FILE>");
						  
						}
					
				}else{
					sb.append("<FILE>");
					sb.append("<MESSAGE>No file history</MESSAGE>");
					sb.append("</FILE>");
				}
				sb.append("</FILES>");
				

				
			}
			catch (Exception e) {
				// generate XML for candidate details.
				sb.append("<FILES>");
				sb.append("<FILE>");
				sb.append("<MESSAGE>ERROR GETING FILE HISTORY</MESSAGE>");
				sb.append("</FILE>");
				sb.append("</FILES>");
				xml = sb.toString().replaceAll("&", "&amp;");
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
				path = null;

			}
		}else{
			sb.append("<FILE>");
			sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
			sb.append("</FILE>");
		}
		xml = sb.toString().replaceAll("&", "&amp;");
		PrintWriter out = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		out.write(xml);
		out.flush();
		out.close();
		path = null;
		return path;
	}

}
