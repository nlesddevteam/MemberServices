package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.velocity.VelocityUtils;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorSystemRegionalBean;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.bean.FileHistoryBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.constants.VehicleStatusConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorDateHistoryManager;
import com.nlesd.bcs.dao.BussingContractorSystemRegionalManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
import com.nlesd.bcs.dao.FileHistoryManager;
public class UpdateContractorVehicleRequestHandler extends BCSApplicationRequestHandlerImpl {
	public UpdateContractorVehicleRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("vid"),
				new RequiredFormElement("vmake"),
				new RequiredFormElement("vyear"),
				new RequiredFormElement("vserialnumber"),
				new RequiredFormElement("vplatenumber"),
				new RequiredFormElement("vid")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		super.handleRequest(request, response);
		String xml = null;
		String message="UPDATED";
		String updatedby="";
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (validate_form() && !(this.sessionExpired)) {
			BussingContractorVehicleBean vbean = new BussingContractorVehicleBean();
			BussingContractorVehicleBean origbean = new BussingContractorVehicleBean();
			try {
				Integer vid = form.getInt("vid");
				origbean = BussingContractorVehicleManager.getBussingContractorVehicleById(vid);
				vbean.setId(vid);
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
				updatedby=bcbean.getContractorName();
				vbean.setContractorId(bcbean.getId());
				vbean.setvMake(form.getInt("vmake"));
				//vbean.setvModel(-1);
				//using vmodel for wheelchair accessible
				vbean.setWheelchairAccessible(form.getInt("vwheel"));
				vbean.setvYear(form.get("vyear"));
				vbean.setvSerialNumber(form.get("vserialnumber"));
				vbean.setvPlateNumber(form.get("vplatenumber"));
				vbean.setvType(form.getInt("vtype"));
				vbean.setvSize(form.getInt("vsize"));
				vbean.setvOwner(form.get("vrowner"));
				if(form.get("rdate").isEmpty())
				{
					vbean.setRegExpiryDate(null);
				}else{
					vbean.setRegExpiryDate(sdf.parse(form.get("rdate").toString()));
				}
				if(form.get("idate").isEmpty())
				{
					vbean.setInsExpiryDate(null);
				}else{
					vbean.setInsExpiryDate(sdf.parse(form.get("idate").toString()));
				}
				vbean.setInsuranceProvider(form.get("insuranceprovider"));
				if(form.get("fidate").isEmpty())
				{
					vbean.setFallInsDate(null);
				}else{
					vbean.setFallInsDate(sdf.parse(form.get("fidate").toString()));
				}
				if(form.get("widate").isEmpty())
				{
					vbean.setWinterInsDate(null);
				}else{
					vbean.setWinterInsDate(sdf.parse(form.get("widate").toString()));
				}				
				if(form.get("fheidate").isEmpty())
				{
					vbean.setFallHeInsDate(null);
				}else{
					vbean.setFallHeInsDate(sdf.parse(form.get("fheidate").toString()));
				}
				if(form.get("mheidate1").isEmpty())
				{
					vbean.setMiscHeInsDate1(null);
				}else{
					vbean.setMiscHeInsDate1(sdf.parse(form.get("mheidate1").toString()));
				}
				if(form.get("mheidate2").isEmpty())
				{
					vbean.setMiscHeInsDate2(null);
				}else{
					vbean.setMiscHeInsDate2(sdf.parse(form.get("mheidate2").toString()));
				}
				vbean.setContractorId(bcbean.getId());
				vbean.setvModel2(form.get("vmodel2"));
				vbean.setvMakeOther(form.get("vmakeother"));
				vbean.setFallCMVI(form.get("fallcmvi"));
				vbean.setFallInsStation(form.get("fallinsstation"));
				vbean.setWinterCMVI(form.get("wintercmvi"));
				vbean.setWinterInsStation(form.get("winterinsstation"));
				vbean.setUnitNumber(form.get("unitnumber"));
				vbean.setInsurancePolicyNumber(form.get("insurancepolicynumber"));

				//now we check the docs tab
				String filelocation="/BCS/documents/vehicledocs/";
				String docfilename = "";
				StringBuilder sbfiles = new StringBuilder();
				boolean statusUpdated=false;
				
				if(form.getUploadFile("regFile").getFileSize() > 0){
					docfilename=save_file("regFile", filelocation);
					vbean.setRegFile(docfilename);
					//now we update the history record
					FileHistoryBean fhb = new FileHistoryBean();
					if(origbean.getRegFile() == null) {
						fhb.setFileName("No Previous File");
					}else {
						fhb.setFileName(origbean.getRegFile());
					}
					fhb.setFileAction("DOCUMENT REPLACED");
					if(usr == null) {
						fhb.setActionBy(bcbean.getContractorName());
					}else {
						fhb.setActionBy(usr.getLotusUserFullName());
					}
					fhb.setParentObjectId(vbean.getId());
					fhb.setParentObjectType(8);
					FileHistoryManager.addFileHistory(fhb);
					//now set status to not approve and send email
					sbfiles.append(bcbean.getContractorName() + ": " + vbean.getvPlateNumber() + "(" + vbean.getvSerialNumber() + ")" 
					+ " - " + "Registration File " + " has been changed by " + bcbean.getContractorName()+ "<br />");
					vbean.setvStatus(VehicleStatusConstant.SUBMITTED.getValue());
					BussingContractorVehicleManager.updateContractorVehicleStatus(vbean.getId(), VehicleStatusConstant.SUBMITTED.getValue());
					statusUpdated=true;
				}else{
					vbean.setRegFile(origbean.getRegFile());
				}
				if(form.getUploadFile("insFile").getFileSize() > 0){
					docfilename=save_file("insFile", filelocation);
					vbean.setInsFile(docfilename);
					//now we update the history record
					FileHistoryBean fhb = new FileHistoryBean();
					if(origbean.getInsFile() == null) {
						fhb.setFileName("No Previous File");
					}else {
						fhb.setFileName(origbean.getInsFile());
					}
					fhb.setFileAction("DOCUMENT REPLACED");
					if(usr == null) {
						fhb.setActionBy(bcbean.getContractorName());
					}else {
						fhb.setActionBy(usr.getLotusUserFullName());
					}
					fhb.setParentObjectId(vbean.getId());
					fhb.setParentObjectType(9);
					FileHistoryManager.addFileHistory(fhb);
					//now set status to not approve and send email
					sbfiles.append(bcbean.getContractorName() + ": " + vbean.getvPlateNumber() + "(" + vbean.getvSerialNumber() + ")" 
							+ " - " + "Insurance File " + " has been changed by " + bcbean.getContractorName()+ "<br />");
							vbean.setvStatus(VehicleStatusConstant.SUBMITTED.getValue());
					if(!statusUpdated) {
						BussingContractorVehicleManager.updateContractorVehicleStatus(vbean.getId(), VehicleStatusConstant.SUBMITTED.getValue());
						statusUpdated=true;
					}
				}else{
					vbean.setInsFile(origbean.getInsFile());
				}
				if(form.getUploadFile("fallInsFile").getFileSize() > 0){
					docfilename=save_file("fallInsFile", filelocation);
					vbean.setFallInsFile(docfilename);
					//now we update the history record
					FileHistoryBean fhb = new FileHistoryBean();
					if(origbean.getFallInsFile() == null) {
						fhb.setFileName("No Previous File");
					}else {
						fhb.setFileName(origbean.getFallInsFile());
					}
					fhb.setFileAction("DOCUMENT REPLACED");
					if(usr == null) {
						fhb.setActionBy(bcbean.getContractorName());
					}else {
						fhb.setActionBy(usr.getLotusUserFullName());
					}
					fhb.setParentObjectId(vbean.getId());
					fhb.setParentObjectType(10);
					FileHistoryManager.addFileHistory(fhb);
					FileHistoryManager.addFileHistory(fhb);
					//now set status to not approve and send email
					sbfiles.append(bcbean.getContractorName() + ": " + vbean.getvPlateNumber() + "(" + vbean.getvSerialNumber() + ")" 
							+ " - " + "Primary CMVI " + " has been changed by " + bcbean.getContractorName()+ "<br />");
							vbean.setvStatus(VehicleStatusConstant.SUBMITTED.getValue());
					if(!statusUpdated) {
						BussingContractorVehicleManager.updateContractorVehicleStatus(vbean.getId(), VehicleStatusConstant.SUBMITTED.getValue());
						statusUpdated=true;
					}
				}else{
					vbean.setFallInsFile(origbean.getFallInsFile());
				}
				if(form.getUploadFile("winterInsFile").getFileSize() > 0){
					docfilename=save_file("winterInsFile", filelocation);
					vbean.setWinterInsFile(docfilename);
					//now we update the history record
					FileHistoryBean fhb = new FileHistoryBean();
					if(origbean.getWinterInsFile() == null) {
						fhb.setFileName("No Previous File");
					}else {
						fhb.setFileName(origbean.getWinterInsFile());
					}
					fhb.setFileAction("DOCUMENT REPLACED");
					if(usr == null) {
						fhb.setActionBy(bcbean.getContractorName());
					}else {
						fhb.setActionBy(usr.getLotusUserFullName());
					}
					fhb.setParentObjectId(vbean.getId());
					fhb.setParentObjectType(11);
					FileHistoryManager.addFileHistory(fhb);
					//now set status to not approve and send email
					sbfiles.append(bcbean.getContractorName() + ": " + vbean.getvPlateNumber() + "(" + vbean.getvSerialNumber() + ")" 
							+ " - " + "Secondary CMVI " + " has been changed by " + bcbean.getContractorName()+ "<br />");
							vbean.setvStatus(VehicleStatusConstant.SUBMITTED.getValue());
					if(!statusUpdated) {
						BussingContractorVehicleManager.updateContractorVehicleStatus(vbean.getId(), VehicleStatusConstant.SUBMITTED.getValue());
						statusUpdated=true;
					}
				}else{
					vbean.setWinterInsFile(origbean.getWinterInsFile());
				}
				if(form.getUploadFile("fallHEInsFile").getFileSize() > 0){
					docfilename=save_file("fallHEInsFile", filelocation);
					vbean.setFallHEInsFile(docfilename);
					//now we update the history record
					FileHistoryBean fhb = new FileHistoryBean();
					if(origbean.getFallHEInsFile() == null) {
						fhb.setFileName("No Previous File");
					}else {
						fhb.setFileName(origbean.getFallHEInsFile());
					}
					fhb.setFileAction("DOCUMENT REPLACED");
					if(usr == null) {
						fhb.setActionBy(bcbean.getContractorName());
					}else {
						fhb.setActionBy(usr.getLotusUserFullName());
					}
					fhb.setParentObjectId(vbean.getId());
					fhb.setParentObjectType(12);
					FileHistoryManager.addFileHistory(fhb);
					
				}else{
					vbean.setFallHEInsFile(origbean.getFallHEInsFile());
				}
				if(form.getUploadFile("miscHEInsFile1").getFileSize() > 0){
					docfilename=save_file("miscHEInsFile1", filelocation);
					vbean.setMiscHEInsFile1(docfilename);
					//now we update the history record
					FileHistoryBean fhb = new FileHistoryBean();
					if(origbean.getMiscHEInsFile1() == null) {
						fhb.setFileName("No Previous File");
					}else {
						fhb.setFileName(origbean.getMiscHEInsFile1());
					}
					fhb.setFileAction("DOCUMENT REPLACED");
					if(usr == null) {
						fhb.setActionBy(bcbean.getContractorName());
					}else {
						fhb.setActionBy(usr.getLotusUserFullName());
					}
					fhb.setParentObjectId(vbean.getId());
					fhb.setParentObjectType(13);
					FileHistoryManager.addFileHistory(fhb);
				}else{
					vbean.setMiscHEInsFile1(origbean.getMiscHEInsFile1());
				}
				if(form.getUploadFile("miscHEInsFile2").getFileSize() > 0){
					docfilename=save_file("miscHEInsFile2", filelocation);
					vbean.setMiscHEInsFile2(docfilename);
					//now we update the history record
					FileHistoryBean fhb = new FileHistoryBean();
					if(origbean.getMiscHEInsFile2() == null) {
						fhb.setFileName("No Previous File");
					}else {
						fhb.setFileName(origbean.getMiscHEInsFile2());
					}
					fhb.setFileAction("DOCUMENT REPLACED");
					if(usr == null) {
						fhb.setActionBy(bcbean.getContractorName());
					}else {
						fhb.setActionBy(usr.getLotusUserFullName());
					}
					fhb.setParentObjectId(vbean.getId());
					fhb.setParentObjectType(14);
					FileHistoryManager.addFileHistory(fhb);
				}else{
					vbean.setMiscHEInsFile2(origbean.getMiscHEInsFile2());
				}
				//now we add the record
				BussingContractorVehicleManager.updateBussingContractorVehicle(vbean);
				if(bcbean.getBoardOwned().equals("Y")) {
					BussingContractorSystemRegionalBean regbean = new BussingContractorSystemRegionalBean();
					regbean.setrType("V");
					regbean.setrId(vbean.getId());
					regbean.setRegionCode(form.getInt("regioncode"));
					regbean.setDepotCode(form.getInt("depotcode"));
					if(origbean.getRegionBean() == null || origbean.getRegionBean().getId() <= 0) {
						BussingContractorSystemRegionalManager.addBussingContractorSystemRegionalBean(regbean);
					}else {
						regbean.setId(origbean.getRegionBean().getId());
						BussingContractorSystemRegionalManager.updateBussingContractorSystemRegionalBean(regbean);
					}
			}
				//now we add the archive record
				BussingContractorVehicleManager.addBussingContractorVehicleArc(origbean);
				//now we check to see if dates changed
				BussingContractorDateHistoryManager.CheckChangedVehicleDates(origbean, vbean, updatedby);
				//update audit trail
				AuditTrailBean atbean = new AuditTrailBean();
				atbean.setEntryType(EntryTypeConstant.CONTRACTORVEHICLEUPDATED);
				atbean.setEntryId(vbean.getId());
				atbean.setEntryTable(EntryTableConstant.CONTRACTORVEHICLE);
				DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
				atbean.setEntryNotes("Contractor vehicle (" + vbean.getvPlateNumber() + ") updated on  " + dateTimeInstance.format(Calendar.getInstance().getTime()));
				atbean.setContractorId(vbean.getContractorId());
				AuditTrailManager.addAuditTrail(atbean);
				if(statusUpdated) {
					//send email to bussing
					//now we send the message
					EmailBean email = new EmailBean();
					email.setTo("transportation@nlesd.ca");
					//email.setTo("rodneybatten@nlesd.ca");
					email.setFrom("bussingcontractorsystem@nlesd.ca");
					email.setSubject("NLESD Bussing Contractor System Vehicle Updated");
					HashMap<String, Object> model = new HashMap<String, Object>();
					// set values to be used in template
					model.put("cname", bcbean.getContractorName());
					model.put("ename", vbean.getvPlateNumber() + "(" + vbean.getvSerialNumber() + ")");
					model.put("elist", sbfiles.toString());
					model.put("etypes", "File(s)");
					email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/employeeupdated.vm", model));
					try {
						email.send();
					} catch (EmailException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}catch(Exception e){
				message = e.getMessage();
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
			}
			sb.append("<CONTRACTORS>");
			sb.append("<CONTRACTOR>");
			sb.append("<MESSAGE>" + message + "</MESSAGE>");
			sb.append("<VID>" + vbean.getId() + "</VID>");
			sb.append("</CONTRACTOR>");
			sb.append("</CONTRACTORS>");
		}else {
			if(this.sessionExpired) {
				path="contractorLogin.html?msg=Session expired, please login again.";
				return path;
			}else {
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
			}
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
