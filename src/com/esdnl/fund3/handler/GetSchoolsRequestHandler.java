package com.esdnl.fund3.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.dao.DropdownManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class GetSchoolsRequestHandler extends RequestHandlerImpl{
	public GetSchoolsRequestHandler() {

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
		String regions = form.get("ddid");
		TreeMap<String,Integer> schoollist = new TreeMap<String,Integer>();
		
			try {
					schoollist= DropdownManager.getSchoolsByRegion(regions);
					// generate XML for candidate details.
					String xml = null;
					StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
					sb.append("<SCHOOLS>");
					if(! schoollist.isEmpty()){
						for(Map.Entry<String,Integer> entry : schoollist.entrySet()) {
							//NLESDPayAdviceSearchBean ebean = (NLESDPayAdviceSearchBean) entry.getValue();
							sb.append("<SCHOOL>");
							sb.append("<SCHOOLID>" + entry.getValue()+ "</SCHOOLID>");
							sb.append("<SCHOOLNAME>" + entry.getKey() + "</SCHOOLNAME>");
							sb.append("<MESSAGE>LISTFOUND</MESSAGE>");
							sb.append("</SCHOOL>");
						}
					}else{
						sb.append("<SCHOOL>");
						sb.append("<MESSAGE>NOLIST</MESSAGE>");
						sb.append("</SCHOOL>");
					}
					sb.append("</SCHOOLS>");
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
