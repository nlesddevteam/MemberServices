package com.nlesd.bcs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.FileHistoryBean;
import com.nlesd.bcs.bean.FileTypeBean;
import com.nlesd.bcs.dao.FileHistoryManager;
import com.nlesd.bcs.dao.FileTypeManager;

public class DeleteFileAjaxRequestHandler extends RequestHandlerImpl {
	public DeleteFileAjaxRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
		String smessage="SUCCESS";
		String deletetype="";
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
	    
	    try {
	    Integer did = form.getInt("did");
        Integer dtype = form.getInt("dtype");
        String fileName = form.get("filename");
        //get the file type object to use
        FileTypeBean ftb = FileTypeManager.getFIleTypeById(dtype);
        if(ftb.getFileCategory().equals("BCS_CONTRACTOR_EMPLOYEE")) {
        	deletetype="E";
        }else {
        	deletetype="V";
        }
        //now we save the current object to history table
        FileHistoryBean fhb = new FileHistoryBean();
        fhb.setFileName(fileName);
        fhb.setFileAction("DELETED");
        fhb.setActionBy(usr.getLotusUserFullName());
        fhb.setParentObjectId(did);
        fhb.setParentObjectType(ftb.getId());
        FileHistoryManager.addFileHistory(fhb);
        //now we update the record to show the file delete
        FileHistoryManager.deleteFile(ftb, did);
        //update audit trail
		/**
        AuditTrailBean atbean = new AuditTrailBean();
		atbean.setEntryType(EntryTypeConstant.SYSTEMDOCDELETED);
		atbean.setEntryId(did);
		atbean.setEntryTable(EntryTableConstant.SYSTEMDOC);
		DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
		atbean.setEntryNotes("System doc (" + documentname + ") deleted by " + usr.getPersonnel().getFullNameReverse() + " on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
		atbean.setContractorId(0);
		AuditTrailManager.addAuditTrail(atbean);
		**/
	    }catch(Exception e) {
	    	smessage=e.getMessage();
	    }
        
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		sb.append("<FILES>");
		sb.append("<FILE>");
		sb.append("<MESSAGE>" + smessage + "</MESSAGE>");
		sb.append("<DTYPE>" + deletetype + "</DTYPE>");
		sb.append("</FILE>");
		sb.append("</FILES>");
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