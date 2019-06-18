package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.nlesd.bcs.dao.DropdownManager;
public class GetSubDropdownItemsAjaxRequestHandler extends PublicAccessRequestHandlerImpl {
	public GetSubDropdownItemsAjaxRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			int pid = form.getInt("pid");
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			TreeMap<Integer,String> list = DropdownManager.getDropdownValuesTMP(pid);
			sb.append("<FIELDLIST>");
			
			for(Map.Entry<Integer,String> entry : list.entrySet()) {
				  	sb.append("<FIELD>");
					sb.append("<MESSAGE>SUCCESS</MESSAGE>");
					sb.append("<ID>" + entry.getKey() + "</ID>");
					sb.append("<TITLE>" + entry.getValue() + "</TITLE>");
					sb.append("</FIELD>");
				  
			}
			sb.append("</FIELDLIST>");
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
			sb.append("<FIELDLIST>");
			sb.append("<FIELD>");
			sb.append("<MESSAGE>ERROR GETTING REPORT FIELDS</MESSAGE>");
			sb.append("</FIELD>");
			sb.append("</FIELDLIST>");
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
