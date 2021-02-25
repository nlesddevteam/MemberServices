package com.nlesd.bcs.handler;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.DocumentAuditBean;
import com.nlesd.bcs.dao.DocumentAuditManager;

public class ViewDocumentAuditRequestHandler extends RequestHandlerImpl
{
	public ViewDocumentAuditRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("numdays")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		if (validate_form()) {
			//now we get the status type
			int numdays = form.getInt("numdays");
			ArrayList<DocumentAuditBean> docs = new ArrayList<DocumentAuditBean>();
			docs = DocumentAuditManager.getDocumentAudit(numdays);
			request.setAttribute("vehicles", docs);
			request.setAttribute("reporttitle", numdays);
			path = "view_document_audit.jsp";
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";	
		}

		return path;
	}
}