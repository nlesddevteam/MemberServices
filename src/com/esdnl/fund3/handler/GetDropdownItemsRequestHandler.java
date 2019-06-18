package com.esdnl.fund3.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.DropdownItemBean;
import com.esdnl.fund3.dao.DropdownManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class GetDropdownItemsRequestHandler extends RequestHandlerImpl{
	public GetDropdownItemsRequestHandler() {

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
		Integer dropdownid = form.getInt("ddid");
		
		try {
				ArrayList<DropdownItemBean> dditems = DropdownManager.getDropdownItems(dropdownid);
				// generate XML for candidate details.
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<DROPDOWN>");
				for(DropdownItemBean dd : dditems)
				{
					sb.append("<ITEM>");
					sb.append("<VALUE>" + dd.getId() + "</VALUE>");
					sb.append("<TEXT>" + dd.getDdText() + "</TEXT>");
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
