package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorSystemRouteManager;
public class DeleteRouteRequestHandler extends RequestHandlerImpl {
	public DeleteRouteRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
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
	    
	    
        Integer did = form.getInt("rid");
        String routename =form.get("rname");
        boolean result=false;
        result = BussingContractorSystemRouteManager.deleteRoute(did);
        //update audit trail
		AuditTrailBean atbean = new AuditTrailBean();
		atbean.setEntryType(EntryTypeConstant.DELETEROUTE);
		atbean.setEntryId(did);
		atbean.setEntryTable(EntryTableConstant.ROUTE);
		DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
		atbean.setEntryNotes("Route(" + routename + ") deleted by " + usr.getPersonnel().getFullNameReverse() + " on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
		atbean.setContractorId(0);
		AuditTrailManager.addAuditTrail(atbean);
        if(result){
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<ROUTES>");
			sb.append("<ROUTE>");
			sb.append("<MESSAGE>SUCCESS</MESSAGE>");
			sb.append("</ROUTE>");
			sb.append("</ROUTES>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
        }else{
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<ROUTES>");
			sb.append("<ROUTE>");
			sb.append("<MESSAGE>ERROR DELETING VEHICLE</MESSAGE>");
			sb.append("</CONTRACROUTETOR>");
			sb.append("</ROUTES>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
        }   return null;
	}
}
