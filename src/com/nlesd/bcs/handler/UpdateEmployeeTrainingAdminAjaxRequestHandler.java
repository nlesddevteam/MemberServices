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
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.bean.BussingContractorSystemEmployeeTrainingBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorManager;
import com.nlesd.bcs.dao.BussingContractorSystemEmployeeTrainingManager;
import com.nlesd.bcs.dao.DropdownManager;

public class UpdateEmployeeTrainingAdminAjaxRequestHandler extends BCSApplicationRequestHandlerImpl{
	public UpdateEmployeeTrainingAdminAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("trainingtype"),
				new RequiredFormElement("providedby"),
				new RequiredFormElement("location")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			String message="UPDATED";
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			if (validate_form()) {
				BussingContractorSystemEmployeeTrainingBean vbean = new BussingContractorSystemEmployeeTrainingBean();
				try {
					if(form.exists("contractor")){
						bcbean = BussingContractorManager.getBussingContractorById(form.getInt("contractor"));
					}else{
						if(usr.checkPermission("BCS-VIEW-WESTERN")){
							bcbean = BussingContractorManager.getBussingContractorById(BoardOwnedContractorsConstant.WESTERN.getValue());
						}
						if(usr.checkPermission("BCS-VIEW-CENTRAL")){
							bcbean = BussingContractorManager.getBussingContractorById(BoardOwnedContractorsConstant.CENTRAL.getValue());
						}
						if(usr.checkPermission("BCS-VIEW-LABRADOR")){
							bcbean = BussingContractorManager.getBussingContractorById(BoardOwnedContractorsConstant.LABRADOR.getValue());
						}

					}
					//get fields
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					vbean.setPk(form.getInt("hidid"));
					vbean.setTrainingType(form.getInt("trainingtype"));
					BussingContractorSystemEmployeeTrainingBean origbean = BussingContractorSystemEmployeeTrainingManager.getEmployeeTrainingByTrainingId(vbean.getPk());
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
						//first we delete old file
						if(!(origbean.gettDocument() == null)){
							delete_file(origbean.gettDocument(),filelocation);
						}
						//now we save the new one
						String docfilename = save_file("documentfile", filelocation);
						vbean.settDocument(docfilename);
					}else{
						vbean.settDocument(origbean.gettDocument());
					}
					
					vbean.setNotes(form.get("rnotes"));
					vbean.setFkEmployee(form.getInt("eid"));
					BussingContractorEmployeeBean ebean = BussingContractorEmployeeManager.getBussingContractorEmployeeById(vbean.getPk());
					vbean.setTrainingLength(form.getInt("traininglength"));
					vbean.setProvidedBy(form.get("providedby"));
					vbean.setLocation(form.get("location"));
					//save file to db
					BussingContractorSystemEmployeeTrainingManager.updateEmployeeTraining(vbean);
					//update audit trail
					AuditTrailBean atbean = new AuditTrailBean();
					atbean.setEntryType(EntryTypeConstant.EMPLOYEETRAININGUPDATED);
					atbean.setEntryId(vbean.getPk());
					atbean.setEntryTable(EntryTableConstant.EMPLOYEETRAINING);
					DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
					atbean.setEntryNotes("Employee Training (" + DropdownManager.getDropdownItemText(vbean.getTrainingType()) + ") updated for " + ebean.getFirstName() + " " + ebean.getLastName() + " on  " + dateTimeInstance.format(Calendar.getInstance().getTime()));
					atbean.setContractorId(bcbean.getId());
					AuditTrailManager.addAuditTrail(atbean);
	            }
				catch (Exception e) {
					message=e.getMessage();
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
					path = null;
			
					
				}
				sb.append("<DOCUMENTS>");
				sb.append("<DOCUMENT>");
				sb.append("<MESSAGE>" + message + "</MESSAGE>");
				sb.append("</DOCUMENT>");
				sb.append("</DOCUMENTS>");
			}else {
				sb.append("<DOCUMENTS>");
				sb.append("<DOCUMENT>");
				sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
				sb.append("</DOCUMENT>");
				sb.append("</DOCUMENTS>");
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
