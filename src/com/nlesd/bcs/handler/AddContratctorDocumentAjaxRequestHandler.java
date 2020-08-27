package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorDocumentBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorDocumentManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequiredFormElement;
public class AddContratctorDocumentAjaxRequestHandler extends BCSApplicationRequestHandlerImpl{
	public AddContratctorDocumentAjaxRequestHandler() {
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
			BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
			BussingContractorDocumentBean vbean = new BussingContractorDocumentBean();
			if (validate_form() && !(this.sessionExpired)) {
				try {
					//get fields
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					vbean.setContractorId(bcbean.getId());
					vbean.setDocumentType(form.getInt("documenttype"));
					vbean.setDocumentTitle(form.get("documenttitle"));
					String filelocation="/BCS/documents/contractordocs/";
					String docfilename = save_file("documentfile", filelocation);
					vbean.setDocumentPath(docfilename);
					if(form.get("expirydate").isEmpty())
					{
						vbean.setExpiryDate(null);
					}else{
						vbean.setExpiryDate(sdf.parse(form.get("expirydate").toString()));
					}
					//save file to db
	            	BussingContractorDocumentManager.addBussingContractorDocument(vbean);
					//update audit trail
					AuditTrailBean atbean = new AuditTrailBean();
					atbean.setEntryType(EntryTypeConstant.CONTRACTORDOCADDED);
					atbean.setEntryId(vbean.getId());
					atbean.setEntryTable(EntryTableConstant.CONTRACTORDOC);
					DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
					atbean.setEntryNotes("Contractor doc (" + vbean.getDocumentTitle() + ") added on  " + dateTimeInstance.format(Calendar.getInstance().getTime()));
					atbean.setContractorId(vbean.getContractorId());
					AuditTrailManager.addAuditTrail(atbean);
	            }
				catch (Exception e) {
					message=e.getMessage();
				}
			}else {
				if(this.sessionExpired) {
					path="contractorLogin.html?msg=Session expired, please login again.";
					return path;
				}else {
					message=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
				}
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
