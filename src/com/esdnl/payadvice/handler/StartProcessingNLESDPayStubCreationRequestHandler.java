package com.esdnl.payadvice.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.esdnl.payadvice.worker.NLESDPayAdvicePayStubProcessWorker;
import com.esdnl.servlet.RequestHandlerImpl;
public class StartProcessingNLESDPayStubCreationRequestHandler extends RequestHandlerImpl {
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
		super.handleRequest(request, response);
	    String path=null;
	    HttpSession session = null;
	    User usr = null;
	    session = request.getSession(false);
		if((session != null) && (session.getAttribute("usr") != null)){
			usr = (User) session.getAttribute("usr");
			if(!(usr.getUserPermissions()).containsKey("PAY-ADVICE-ADMIN"))
			{
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else{
			throw new SecurityException("User login required.");
		}
	    try
	    {
	    	//NLESDLoadPayAdvicePayStubWorker worker = new NLESDLoadPayAdvicePayStubWorker();
	    	NLESDPayAdvicePayStubProcessWorker worker = new NLESDPayAdvicePayStubProcessWorker();
	    	
	    	//retrieve files, could be null
	    	Integer paygroupid = form.getInt("id");
	    	worker.setPayGroupId(paygroupid);
	    	worker.setUsername(usr.getUsername());
	    	worker.setEmail(usr.getPersonnel().getEmailAddress());
	    	worker.setServerpath(session.getServletContext().getRealPath("/"));
	    	Date d= new Date();
	    	worker.start();
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<MESSAGES>");
	    	sb.append("<MESSAGE>");
	    	sb.append("<MTEXT>Pay Stub Creation  process started at " + d + ". You will receive an email notification when the process has finished or you can check the progress on the Pay Period Details page</MTEXT>");
	    	sb.append("</MESSAGE>");
	    	sb.append("</MESSAGES>");
			xml = sb.toString().replaceAll("&", "&amp;");
			System.out.println(xml);
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
	    }
	    catch(Exception e)
	    {
		      e.printStackTrace();
		      request.setAttribute("msg", e.getMessage());
	    }
	    
	    return path;
	  }
}
