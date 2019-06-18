package com.esdnl.fund3.handler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.DropdownItemBean;
import com.esdnl.fund3.dao.DropdownManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class GetDropdownItemRequestHandler extends RequestHandlerImpl{
	public GetDropdownItemRequestHandler() {

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
		Integer dropdownid = form.getInt("ddid");
		
		try {
				DropdownItemBean dditem = DropdownManager.getDropdownItemById(dropdownid);
				// generate XML for candidate details.
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<DROPDOWN>");
				if(!(dditem == null))
				{
					sb.append("<ITEM>");
					sb.append("<VALUE>" + dditem.getId() + "</VALUE>");
					sb.append("<TEXT>" + dditem.getDdText() + "</TEXT>");
					sb.append("<ISACTIVE>" + dditem.getIsActive() + "</ISACTIVE>");
					sb.append("</ITEM>");
				}

				sb.append("</DROPDOWN>");
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