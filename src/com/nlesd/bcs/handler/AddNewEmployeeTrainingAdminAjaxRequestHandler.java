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
import com.nlesd.bcs.bean.BussingContractorSystemEmployeeTrainingBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorSystemEmployeeTrainingManager;
import com.nlesd.bcs.dao.DropdownManager;
public class AddNewEmployeeTrainingAdminAjaxRequestHandler extends RequestHandlerImpl{
	public AddNewEmployeeTrainingAdminAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("trainingtype"),
				new RequiredFormElement("eid"),
				new RequiredFormElement("traininglength"),
				new RequiredFormElement("providedby"),
				new RequiredFormElement("location")
			});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			String message="ADDED";
			int cid=-1;
			BussingContractorSystemEmployeeTrainingBean vbean = new BussingContractorSystemEmployeeTrainingBean();
			if (validate_form()) {
				try {
					//get fields
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					vbean.setTrainingType(form.getInt("trainingtype"));
					if(form.get("trainingdate").isEmpty())
					{
						vbean.setTrainingDate(null);
					}else{
						vbean.setTrainingDate(sdf.parse(form.get("trainingdate").toString()));
					}
					if(form.get("expirydate").isEmpty())
					{
						vbean.setExpiryDate(null);
					}else{
						vbean.setExpiryDate(sdf.parse(form.get("expirydate").toString()));
					}
					if(form.uploadFileExists("documentfile")){
						String filelocation="/BCS/documents/employeedocs/";
						String docfilename = save_file("documentfile", filelocation);
						vbean.settDocument(docfilename);
					}else{
						vbean.settDocument("");
					}
					
					vbean.setNotes(form.get("rnotes"));
					vbean.setFkEmployee(form.getInt("eid"));
					BussingContractorEmployeeBean ebean = BussingContractorEmployeeManager.getBussingContractorEmployeeById(vbean.getPk());
					vbean.setTrainingLength(form.getInt("traininglength"));
					vbean.setProvidedBy(form.get("providedby"));
					vbean.setLocation(form.get("location"));
					//save file to db
					BussingContractorSystemEmployeeTrainingManager.addEmployeeTraining(vbean);
					//update audit trail
					AuditTrailBean atbean = new AuditTrailBean();
					atbean.setEntryType(EntryTypeConstant.EMPLOYEETRAININGADDED);
					atbean.setEntryId(vbean.getPk());
					atbean.setEntryTable(EntryTableConstant.EMPLOYEETRAINING);
					DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
					atbean.setEntryNotes("Employee Training (" + DropdownManager.getDropdownItemText(vbean.getTrainingType()) + ") added for " + ebean.getFirstName() + " " + ebean.getLastName() + " on  " + dateTimeInstance.format(Calendar.getInstance().getTime()));
					atbean.setContractorId(cid);
					AuditTrailManager.addAuditTrail(atbean);
	            }
				catch (Exception e) {
					message=e.getMessage();
				}
			}else {
				message="Error updating information";
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
