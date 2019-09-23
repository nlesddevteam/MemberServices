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
import com.nlesd.bcs.bean.BussingContractorSystemDocumentBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorSystemDocumentManager;
public class AddSystemDocumentAjaxRequestHandler extends RequestHandlerImpl{
	public AddSystemDocumentAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("documenttype"),
				new RequiredFormElement("documenttitle")
			});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			String message="UPDATED";
			BussingContractorSystemDocumentBean vbean = new BussingContractorSystemDocumentBean();
			if (validate_form()) {
				try {
					//get fields
					vbean.setDocumentType(form.getInt("documenttype"));
					vbean.setDocumentTitle(form.get("documenttitle"));
					String filelocation="/BCS/documents/system/";
					String docfilename = save_file("documentfile", filelocation);
					vbean.setDocumentPath(docfilename);
					vbean.setUploadedBy(usr.getPersonnel().getFullNameReverse());
					if(form.getBoolean("vinternal") == true){
						vbean.setvInternal("Y");
					}else{
						vbean.setvInternal("N");
					}
					if(form.getBoolean("vexternal") == true){
						vbean.setvExternal("Y");
					}else{
						vbean.setvExternal("N");
					}
					if(form.getBoolean("showmessage") == true){
						vbean.setShowMessage("Y");
					}else{
						vbean.setShowMessage("N");
					}
					vbean.setMessageDays(form.getInt("messagedays"));
					if(form.getBoolean("isactive") == true){
						vbean.setIsActive("Y");
					}else{
						vbean.setIsActive("N");
					}
					//save file to db
	            	BussingContractorSystemDocumentManager.addBussingContractorSystemDocument(vbean);
					//update audit trail
					AuditTrailBean atbean = new AuditTrailBean();
					atbean.setEntryType(EntryTypeConstant.SYSTEMDOCADDED);
					atbean.setEntryId(vbean.getId());
					atbean.setEntryTable(EntryTableConstant.SYSTEMDOC);
					DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
					atbean.setEntryNotes("System doc (" + vbean.getDocumentTitle() + ") added by: " + usr.getPersonnel().getFullNameReverse() + " on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
					atbean.setContractorId(0);
					AuditTrailManager.addAuditTrail(atbean);
	            }
				catch (Exception e) {
					message=e.getMessage();
				}
			}else {
				message=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
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
