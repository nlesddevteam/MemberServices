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
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.bean.FileHistoryBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.constants.VehicleStatusConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
import com.nlesd.bcs.dao.FileHistoryManager;
public class AddNewContractorVehicleRequestHandler extends BCSApplicationRequestHandlerImpl {
	public AddNewContractorVehicleRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("vmake"),
				new RequiredFormElement("vyear"),
				new RequiredFormElement("vserialnumber"),
				new RequiredFormElement("vplatenumber"),
				new RequiredFormElement("vtype"),
				new RequiredFormElement("vsize"),
				new RequiredFormElement("vrowner")
			});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		BussingContractorVehicleBean vbean = new BussingContractorVehicleBean();
		String message="UPDATED";
		if (validate_form() && !(this.sessionExpired)) {
		try {
				if(BussingContractorVehicleManager.checkVehicleSerialNumber(form.get("vserialnumber"))) {
					message="Vehicle record on file with identical Serial Number";
				}else {
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
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
					//now we do the documents
					String filelocation="/BCS/documents/vehicledocs/";
					String docfilename = "";
					if(form.getUploadFile("fallInsFile").getFileSize() > 0){
						docfilename=save_file("fallInsFile", filelocation);
						vbean.setFallInsFile(docfilename);
					}
					if(form.getUploadFile("winterInsFile").getFileSize() > 0){
						docfilename=save_file("winterInsFile", filelocation);
						vbean.setWinterInsFile(docfilename);
					}
					if(form.getUploadFile("fallHEInsFile").getFileSize() > 0){
						docfilename=save_file("fallHEInsFile", filelocation);
						vbean.setFallHEInsFile(docfilename);
					}
					if(form.getUploadFile("miscHEInsFile1").getFileSize() > 0){
						docfilename=save_file("miscHEInsFile1", filelocation);
						vbean.setMiscHEInsFile1(docfilename);
					}
					if(form.getUploadFile("miscHEInsFile2").getFileSize() > 0){
						docfilename=save_file("miscHEInsFile2", filelocation);
						vbean.setMiscHEInsFile2(docfilename);
					}
					if(form.getUploadFile("regFile").getFileSize() > 0){
						docfilename=save_file("regFile", filelocation);
						vbean.setRegFile(docfilename);
					}
					if(form.getUploadFile("insFile").getFileSize() > 0){
						docfilename=save_file("insFile", filelocation);
						vbean.setInsFile(docfilename);
					}
					vbean.setContractorId(bcbean.getId());
					vbean.setvStatus(VehicleStatusConstant.SUBMITTED.getValue());
					vbean.setvModel2(form.get("vmodel2"));
					vbean.setvMakeOther(form.get("vmakeother"));
					vbean.setFallCMVI(form.get("fallcmvi"));
					vbean.setFallInsStation(form.get("fallinsstation"));
					vbean.setWinterCMVI(form.get("wintercmvi"));
					vbean.setWinterInsStation(form.get("winterinsstation"));
					vbean.setUnitNumber(form.get("unitnumber"));
					vbean.setInsurancePolicyNumber(form.get("insurancepolicynumber"));
					//now we add the record
					BussingContractorVehicleManager.addBussingContractorVehicle(vbean);
					//now that we have an id we can update the file history objects
					if(!(vbean.getRegFile() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getRegFile());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(bcbean.getContractorName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(8);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getInsFile() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getInsFile());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(bcbean.getContractorName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(9);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getFallInsFile() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getFallInsFile());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(bcbean.getContractorName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(10);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getWinterInsFile() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getWinterInsFile());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(bcbean.getContractorName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(11);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getFallHEInsFile() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getFallHEInsFile());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(bcbean.getContractorName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(12);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getMiscHEInsFile1() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getMiscHEInsFile1());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(bcbean.getContractorName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(13);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getMiscHEInsFile2() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getMiscHEInsFile2());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(bcbean.getContractorName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(14);
						FileHistoryManager.addFileHistory(fhb);
					}
					
					
					//update audit trail
					AuditTrailBean atbean = new AuditTrailBean();
					atbean.setEntryType(EntryTypeConstant.CONTRACTORVEHICLEADDED);
					atbean.setEntryId(vbean.getId());
					atbean.setEntryTable(EntryTableConstant.CONTRACTORVEHICLE);
					DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
					atbean.setEntryNotes("Contractor vehicle (" + vbean.getvPlateNumber() + ") added on  " + dateTimeInstance.format(Calendar.getInstance().getTime()));
					atbean.setContractorId(vbean.getContractorId());
					AuditTrailManager.addAuditTrail(atbean);
				}
				
			}catch(Exception e){
				message = e.getMessage();
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
		sb.append("<VID>" + vbean.getId() + "</VID>");
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