package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorSecurityBean;
import com.nlesd.bcs.dao.BussingContractorSecurityManager;
public class GetPasswordResetInformationAjaxRequestHandler extends PublicAccessRequestHandlerImpl { 
	public GetPasswordResetInformationAjaxRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			String errormessage="";
			String email = form.get("email");
			boolean isvalid=false;
			StringBuffer sbsec= new StringBuffer(); 
			BussingContractorSecurityBean ebean = BussingContractorSecurityManager.getBussingContractorSecurityByEmail(email);
			if( ebean == null){
				errormessage="Email address not found in system.";
			}else{
				if(ebean.getSecurityQuestion() == null){
					errormessage="Account has not been confirmed.  Please see confirmation email for link to confirm account";
				}else{
					isvalid=true;
					//now we send back the security info
					errormessage="SUCCESS";
					sbsec.append("<EMAIL>" + ebean.getEmail() + "</EMAIL>");
					sbsec.append("<QUESTION>" + ebean.getSecurityQuestion() + "</QUESTION>");
					sbsec.append("<ANSWER>" + ebean.getSqAnswer() + "</ANSWER>");
					sbsec.append("<ID>" + ebean.getId() + "</ID>");
				}
			}
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CONTRACTORS>");
			sb.append("<CONTRACTOR>");
			sb.append("<MESSAGE>");
			sb.append(errormessage);
			sb.append("</MESSAGE>");
			if(isvalid){
				sb.append(sbsec.toString());
			}
			sb.append("</CONTRACTOR>");
			sb.append("</CONTRACTORS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;

			
		}
		catch (Exception e) {
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CONTRACTOR>");
			sb.append("<CONTRACTORSTATUS>");
			sb.append("<MESSAGE>ERROR SETTING CONTRACTOR STATUS</MESSAGE>");
			sb.append("</CONTRACTORSTATUS>");
			sb.append("</CONTRACTOR>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;

		}
		return path;
	}

}
