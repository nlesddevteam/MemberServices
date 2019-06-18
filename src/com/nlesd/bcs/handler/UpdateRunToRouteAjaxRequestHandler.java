package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorSystemRouteRunBean;
import com.nlesd.bcs.bean.BussingContractorSystemRouteRunSchoolBean;
import com.nlesd.bcs.dao.BussingContractorSystemRouteRunManager;
import com.nlesd.bcs.dao.BussingContractorSystemRouteRunSchoolManager;
public class UpdateRunToRouteAjaxRequestHandler  extends RequestHandlerImpl{
	public UpdateRunToRouteAjaxRequestHandler() {

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			String message="SUCCESS";
			HttpSession session = null;
		    User usr = null;
		    session = request.getSession(false);
		    if((session != null) && (session.getAttribute("usr") != null))
		    {
		      usr = (User) session.getAttribute("usr");
		      if(!(usr.getUserPermissions().containsKey("BCS-SYSTEM-ACCESS")))
		      {
		        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
		      }
		    }
		    else
		    {
		      throw new SecurityException("User login required.");
		    }
		    
		    
			BussingContractorSystemRouteRunBean vbean = new BussingContractorSystemRouteRunBean();
			
			try {
				//get fields
				vbean.setId(form.getInt("rrid"));
				vbean.setRouteRun(form.get("routerun"));
				vbean.setRouteTime(form.get("routetime"));
				//save file to db
				BussingContractorSystemRouteRunManager.updateBussingContractorSystemRouteRun(vbean);
				//now we delete the original schools
				BussingContractorSystemRouteRunSchoolManager.deleteRouteRunSchools(form.getInt("rrid"));
				//now we loop through the school for the run and add the record
				int x=1;
				String[] ids = form.get("schoolids").split(",");
				for(String s : ids){
					//now we save the record
					BussingContractorSystemRouteRunSchoolBean rbean = new BussingContractorSystemRouteRunSchoolBean();
					rbean.setRouteRunId(form.getInt("rrid"));
					rbean.setSchoolId(Integer.parseInt(s));
					rbean.setSchoolOrder(x);
					BussingContractorSystemRouteRunSchoolManager.addBussingContractorRouteRunSchool(rbean);
					x++;
				}
			
            }
			catch (Exception e) {
				message=e.getMessage();
		
				
			}
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CONTRACTORS>");
			sb.append("<CONTRACTOR>");
			sb.append("<MESSAGE>" + message + "</MESSAGE>");
			sb.append("<ID>" + vbean.getId() + "</ID>");
			sb.append("</CONTRACTOR>");
			sb.append("</CONTRACTORS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			return null;
		}
	
}
