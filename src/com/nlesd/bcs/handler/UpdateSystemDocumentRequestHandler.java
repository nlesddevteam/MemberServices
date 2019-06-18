package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorSystemDocumentBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorSystemDocumentManager;
public class UpdateSystemDocumentRequestHandler extends RequestHandlerImpl{
	public UpdateSystemDocumentRequestHandler() {

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
			super.handleRequest(request, response);
			String message="UPDATED";
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
			BussingContractorSystemDocumentBean vbean = new BussingContractorSystemDocumentBean();
			BussingContractorSystemDocumentBean origbean = new BussingContractorSystemDocumentBean();
			origbean = BussingContractorSystemDocumentManager.getBussingContractorSystemDocumentById(form.getInt("did"));
			//get a copy of the original 
			try {
				//get fields
				vbean.setId(origbean.getId());
				vbean.setDocumentType(form.getInt("documenttype"));
				vbean.setDocumentTitle(form.get("documenttitle"));
				//if(form.exists("documentname")){
					if(form.getUploadFile("documentname").getFileSize() > 0){
						String filelocation="/../MemberServices/BCS/documents/system/";
						String docfilename=save_file("documentname", filelocation);
						vbean.setDocumentPath(docfilename);
						vbean.setUploadedBy(usr.getPersonnel().getFullNameReverse());
					}else{
						vbean.setDocumentPath(origbean.getDocumentPath());
						vbean.setUploadedBy(origbean.getUploadedBy());
					}
				//}else{
				//	vbean.setDocumentPath(origbean.getDocumentPath());
				//	vbean.setUploadedBy(origbean.getUploadedBy());
				//}
				
				if(form.exists("vinternal")){
					if(form.getInt("vinternal") == 1){
						vbean.setvInternal("Y");
					}else{
						vbean.setvInternal("N");
					}
				}else{
					vbean.setvInternal("N");
				}
				
				if(form.exists("vexternal")){
					if(form.getInt("vexternal") == 1){
						vbean.setvExternal("Y");
					}else{
						vbean.setvExternal("N");
					}
				}else{
					vbean.setvExternal("N");
				}
				if(form.exists("showmessage")){
					if(form.getInt("showmessage") == 1){
						vbean.setShowMessage("Y");
					}else{
						vbean.setShowMessage("N");
					}
				}else{
					vbean.setShowMessage("N");
				}
				
				vbean.setMessageDays(form.getInt("messagedays"));
				if(form.exists("isactive")){
					if(form.getInt("isactive") == 1){
						vbean.setIsActive("Y");
					}else{
						vbean.setIsActive("N");
					}
				}else{
					vbean.setIsActive("N");
				}
				
				//now we check to see if the file is being made active so we can change the date uploaded
				if(origbean.getIsActive().equals("N")){
					if(vbean.getIsActive().equals("Y")){
						Date now = new Date();
						vbean.setDateUploaded(now);
						vbean.setUploadedBy(usr.getPersonnel().getFullNameReverse());
					}
				}else{
					vbean.setDateUploaded(origbean.getDateUploaded());
				}
				//save file to db
            	BussingContractorSystemDocumentManager.updateBussingContractorSystemDocument(vbean);
				//update audit trail
				AuditTrailBean atbean = new AuditTrailBean();
				atbean.setEntryType(EntryTypeConstant.SYSTEMDOCUPDATED);
				atbean.setEntryId(vbean.getId());
				atbean.setEntryTable(EntryTableConstant.SYSTEMDOC);
				DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
				atbean.setEntryNotes("System doc (" + vbean.getDocumentTitle() + ") updated by: " + usr.getPersonnel().getFullNameReverse() + " on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
				atbean.setContractorId(0);
				AuditTrailManager.addAuditTrail(atbean);
            }
			catch (Exception e) {
				message=e.getMessage();
		
				
			}
			
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CONTRACTORS>");
			sb.append("<CONTRACTOR>");
			sb.append("<MESSAGE>" + message + "</MESSAGE>");
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