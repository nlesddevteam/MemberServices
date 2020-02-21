package com.esdnl.personnel.jobs.handler.ajax;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.RequestToHireBean;
import com.esdnl.personnel.jobs.dao.RequestToHireEmailManager;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequiredFormElement;
public class ResendRTHApprovalAjaxRequestHandler extends RequestHandlerImpl {

	public ResendRTHApprovalAjaxRequestHandler() {
		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("rid")
			});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
				int rid = form.getInt("rid");
				RequestToHireBean rth = RequestToHireManager.getRequestToHireById(rid);
				RequestToHireEmailManager.sendRequestToHireEmail(rth,usr);
				
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<REQUESTTOHIRE>");
				sb.append("<RTH>");
				sb.append("<STATUS>SUCCESS</STATUS>");
				sb.append("</RTH>");
				sb.append("</REQUESTTOHIRE>");
				xml = StringUtils.encodeXML(sb.toString());

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
			catch (Exception e) {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");

				sb.append("<REQUESTTOHIRE>");
				sb.append("<RTH>");
				sb.append("<STATUS>" + e.getMessage() + "</STATUS>");
				sb.append("</RTH>");
				sb.append("</REQUESTTOHIRE>");
				xml = StringUtils.encodeXML(sb.toString());

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
		

		return null;
	}

}
