package com.esdnl.fund3.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.dao.ProjectFundingManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class AjaxGetFundingContactsRequestHandler extends RequestHandlerImpl{
	public AjaxGetFundingContactsRequestHandler() {

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
		String regions = form.get("ddid");
		ArrayList<String> list = new ArrayList<String>();
		
			try {
					list= ProjectFundingManager.getProjectFundingContacts(regions);
					// generate XML for candidate details.
					String xml = null;
					StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
					sb.append("<CONTACTS>");
					if(! list.isEmpty()){
						for(String ss : list) {
							//NLESDPayAdviceSearchBean ebean = (NLESDPayAdviceSearchBean) entry.getValue();
							sb.append("<CONTACT>");
							sb.append("<CONTACTNAME>" + ss + "</CONTACTNAME>");
							sb.append("<MESSAGE>LISTFOUND</MESSAGE>");
							sb.append("</CONTACT>");
						}
					}else{
						sb.append("<CONTACT>");
						sb.append("<MESSAGE>NOLIST</MESSAGE>");
						sb.append("</CONTACT>");
					}
					sb.append("</CONTACTS>");
					xml = sb.toString().replaceAll("&", "&amp;");
					System.out.println(xml);
					PrintWriter out = response.getWriter();
					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
					path = null;
			}
			catch (Exception e) {
					e.printStackTrace();
					path = null;
			}
		return path;
		}
	
}