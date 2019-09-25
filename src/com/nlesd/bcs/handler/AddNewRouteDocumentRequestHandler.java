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
public class AddNewRouteDocumentRequestHandler extends RequestHandlerImpl{
	public AddNewRouteDocumentRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("documenttitle")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		String message="";
		if (validate_form()) {
			BussingContractorSystemDocumentBean vbean = new BussingContractorSystemDocumentBean();
			try {
				//get fields
				vbean.setDocumentType(83);
				vbean.setDocumentTitle(form.get("documenttitle"));
				String filelocation="/BCS/documents/system/routes";
				String docfilename = save_file("documentfile", filelocation);
				vbean.setDocumentPath(docfilename);
				vbean.setUploadedBy(usr.getPersonnel().getFullNameReverse());
				vbean.setvInternal("Y");
				vbean.setvExternal("N");
				vbean.setShowMessage("N");
				vbean.setMessageDays(form.getInt("routeid"));
				vbean.setIsActive("Y");
				//save file to db
				BussingContractorSystemDocumentManager.addBussingContractorSystemDocument(vbean);
				//update audit trail
				AuditTrailBean atbean = new AuditTrailBean();
				atbean.setEntryType(EntryTypeConstant.SYSTEMDOCADDED);
				atbean.setEntryId(vbean.getId());
				atbean.setEntryTable(EntryTableConstant.SYSTEMDOC);
				DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
				atbean.setEntryNotes("Route doc (" + vbean.getDocumentTitle() + ") added by: " + usr.getPersonnel().getFullNameReverse() + " on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
				atbean.setContractorId(0);
				AuditTrailManager.addAuditTrail(atbean);
			}
			catch (Exception e) {
				message=e.getMessage();
				sb.append("<CONTRACTS>");
				sb.append("<CONTRACT>");
				sb.append("<MESSAGE>" + message + "</MESSAGE>");
				sb.append("</CONTRACT>");
				sb.append("</CONTRACTS>");
				xml = sb.toString().replaceAll("&", "&amp;");
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
				path = null;

			}
			sb.append("<CONTRACTS>");
			sb.append("<CONTRACT>");
			sb.append("<MESSAGE>" + message + "</MESSAGE>");
			sb.append("</CONTRACT>");
			sb.append("</CONTRACTS>");
		}else {
			sb.append("<CONTRACTS>");
			sb.append("<CONTRACT>");
			sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
			sb.append("</CONTRACT>");
			sb.append("</CONTRACTS>");
			
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

