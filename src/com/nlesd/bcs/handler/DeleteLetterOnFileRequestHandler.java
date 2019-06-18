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
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.bean.BussingContractorSystemLetterOnFileBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorSystemLetterOnFileManager;
public class DeleteLetterOnFileRequestHandler extends RequestHandlerImpl {
	public DeleteLetterOnFileRequestHandler() {

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
	    
	    try{
	    	Integer did = form.getInt("letterid");
	    	BussingContractorSystemLetterOnFileBean vbean= BussingContractorSystemLetterOnFileManager.getLetterOnFileById(did);
	    	BussingContractorSystemLetterOnFileManager.deleteLetterOnFile(did);
	    	BussingContractorEmployeeBean ebean = BussingContractorEmployeeManager.getBussingContractorEmployeeById(vbean.getFkType());
	    	//update audit trail
			AuditTrailBean atbean = new AuditTrailBean();
			atbean.setEntryType(EntryTypeConstant.LETTERONFILEDELETED);
			atbean.setEntryId(vbean.getId());
			atbean.setEntryTable(EntryTableConstant.LETTERONFILE);
			DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
			atbean.setEntryNotes("Employee Letter(" + vbean.getlName() + ") deleted for " + ebean.getFirstName() + " " + ebean.getLastName() + " on  " + dateTimeInstance.format(Calendar.getInstance().getTime()));
			atbean.setContractorId(ebean.getContractorId());
			AuditTrailManager.addAuditTrail(atbean);
	    	
	    	String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<LETTERS>");
			sb.append("<LETTER>");
			sb.append("<MESSAGE>SUCCESS</MESSAGE>");
			sb.append("</LETTER>");
			sb.append("</LETTERS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
	    }catch(Exception e){
	    	String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<LETTERS>");
			sb.append("<LETTER>");
			sb.append("<MESSAGE>" + e.getMessage() + "</MESSAGE>");
			sb.append("</LETTER>");
			sb.append("</LETTERS>");
			xml = sb.toString().replaceAll("&", "&amp;");
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