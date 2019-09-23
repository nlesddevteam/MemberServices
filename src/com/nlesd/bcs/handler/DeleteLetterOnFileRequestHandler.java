package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
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
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("letterid")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (validate_form()) {
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
		    	
		    	sb.append("<LETTERS>");
				sb.append("<LETTER>");
				sb.append("<MESSAGE>SUCCESS</MESSAGE>");
				sb.append("</LETTER>");
				sb.append("</LETTERS>");
				
		    }catch(Exception e){
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
		}else {
			sb.append("<LETTERS>");
			sb.append("<LETTER>");
			sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
			sb.append("</LETTER>");
			sb.append("</LETTERS>");
		}
	    
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