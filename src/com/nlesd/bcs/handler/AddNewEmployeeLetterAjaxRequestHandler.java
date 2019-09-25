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
public class AddNewEmployeeLetterAjaxRequestHandler extends RequestHandlerImpl{
	public AddNewEmployeeLetterAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("lname"),
				new RequiredFormElement("ltype"),
				new RequiredFormElement("eid")
			});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			String message="ADDED";
			BussingContractorSystemLetterOnFileBean vbean = new BussingContractorSystemLetterOnFileBean();
			if (validate_form()) {
				try {
					//get fields
					vbean.setlName(form.get("lname"));
					String filelocation="";
					if(form.get("ltype")=="E"){
						filelocation="/BCS/documents/employeeletters";
					}else{
						filelocation="/BCS/documents/contractorletters";
					}
					
					String docfilename = save_file("ldocument", filelocation);
					vbean.setlDocument(docfilename);
					vbean.setNotes(form.get("lnotes"));
					vbean.setFkType(form.getInt("eid"));
					vbean.setlType(form.get("ltype"));
					vbean.setAddedBy(usr.getLotusUserFullName());
					BussingContractorEmployeeBean ebean = BussingContractorEmployeeManager.getBussingContractorEmployeeById(vbean.getFkType());
					//save file to db
					BussingContractorSystemLetterOnFileManager.addLetterOnFile(vbean);
					//update audit trail
					AuditTrailBean atbean = new AuditTrailBean();
					atbean.setEntryType(EntryTypeConstant.LETTERONFILEADDED);
					atbean.setEntryId(vbean.getId());
					atbean.setEntryTable(EntryTableConstant.LETTERONFILE);
					DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
					atbean.setEntryNotes("Employee Letter(" + vbean.getlName() + ") added for " + ebean.getFirstName() + " " + ebean.getLastName() + " on  " + dateTimeInstance.format(Calendar.getInstance().getTime()));
					atbean.setContractorId(ebean.getContractorId());
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
			sb.append("<DOCUMENTS>");
			sb.append("<DOCUMENT>");
			sb.append("<MESSAGE>" + message + "</MESSAGE>");
			sb.append("</DOCUMENT>");
			sb.append("</DOCUMENTS>");
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
