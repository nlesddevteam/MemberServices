package com.nlesd.bcs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.velocity.VelocityUtils;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.bean.FileHistoryBean;
import com.nlesd.bcs.bean.FileTypeBean;
import com.nlesd.bcs.constants.EmployeeStatusConstant;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
import com.nlesd.bcs.dao.FileHistoryManager;
import com.nlesd.bcs.dao.FileTypeManager;

public class ContractorDeleteFileAjaxRequestHandler extends BCSApplicationRequestHandlerImpl {
	public ContractorDeleteFileAjaxRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("did"),
				new RequiredFormElement("dtype")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		String smessage="SUCCESS";
		System.out.println("Starting");
		if (validate_form() && !(this.sessionExpired)) {
			System.out.println("Get contractor from session");
			BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
		    String deletetype="";
		    try {
		    Integer did = form.getInt("did");
	        Integer dtype = form.getInt("dtype");
	        String fileName = form.get("filename");
	        //get the file type object to use
	        System.out.println("Starting Get File Type");
	        FileTypeBean ftb = FileTypeManager.getFIleTypeById(dtype);
	        if(ftb.getFileCategory().equals("BCS_CONTRACTOR_EMPLOYEE")) {
	        	deletetype="E";
	        }else {
	        	deletetype="V";
	        }
	        System.out.println("Starting File History Bean");
	        //now we save the current object to history table
	        FileHistoryBean fhb = new FileHistoryBean();
	        fhb.setFileName(fileName);
	        fhb.setFileAction("DELETED");
	        fhb.setActionBy(bcbean.getContractorName());
	        fhb.setParentObjectId(did);
	        fhb.setParentObjectType(ftb.getId());
	        System.out.println("Starting Add History");
	        FileHistoryManager.addFileHistory(fhb);
	        //now we update the record to show the file delete
	        System.out.println("Starting Delete File History");
	        FileHistoryManager.deleteFile(ftb, did);
	        sb.append("<FILES>");
			sb.append("<FILE>");
			sb.append("<MESSAGE>" + smessage + "</MESSAGE>");
			sb.append("<DTYPE>" + deletetype + "</DTYPE>");
			sb.append("</FILE>");
			sb.append("</FILES>");
			System.out.println("Starting Send Message Update Status");
			if(ftb.getFileCategory().equals("BCS_CONTRACTOR_EMPLOYEE")) {
				BussingContractorEmployeeBean vbean = BussingContractorEmployeeManager.getBussingContractorEmployeeById(did);
				BussingContractorEmployeeManager.updateContractorEmployeeStatus(vbean.getId(), EmployeeStatusConstant.NOTAPPROVED.getValue());
				//now we send the message
				EmailBean email = new EmailBean();
				email.setTo("transportation@nlesd.ca");
				//email.setTo("rodneybatten@nlesd.ca");
				email.setFrom("bussingcontractorsystem@nlesd.ca");
				email.setSubject("NLESD Bussing Contractor System Employee Updated");
				HashMap<String, Object> model = new HashMap<String, Object>();
				// set values to be used in template
				model.put("cname", bcbean.getContractorName());
				model.put("ename", vbean.getFullName());
				model.put("elist", ftb.getFileName() + " deleted by " + bcbean.getContractorName());
				model.put("etypes", "File(s)");
				email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/employeeupdated.vm", model));
				email.send();
			}else {
				BussingContractorVehicleBean vbean = BussingContractorVehicleManager.getBussingContractorVehicleById(did);
				BussingContractorEmployeeManager.updateContractorEmployeeStatus(vbean.getId(), EmployeeStatusConstant.NOTAPPROVED.getValue());
				//now we send the message
				EmailBean email = new EmailBean();
				email.setTo("transportation@nlesd.ca");
				//email.setTo("rodneybatten@nlesd.ca");
				email.setFrom("bussingcontractorsystem@nlesd.ca");
				email.setSubject("NLESD Bussing Contractor System Employee Updated");
				HashMap<String, Object> model = new HashMap<String, Object>();
				// set values to be used in template
				model.put("cname", bcbean.getContractorName());
				model.put("ename", vbean.getvPlateNumber() + "(" + vbean.getvSerialNumber() + ")");
				model.put("elist", ftb.getFileName() + " deleted by " + bcbean.getContractorName());
				model.put("etypes", "File(s)");
				email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/employeeupdated.vm", model));
				email.send();
			}
	        
		    }catch(Exception e) {
		    	sb.append("<FILES>");
				sb.append("<FILE>");
				sb.append("<MESSAGE>" + e.getMessage() + "</MESSAGE>");
				sb.append("</FILE>");
				sb.append("</FILES>");
		    	xml = sb.toString().replaceAll("&", "&amp;");
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
				System.out.println("Done");
		    }
	    }else {
			
			if(this.sessionExpired) {
				path="contractorLogin.html?msg=Session expired, please login again.";
				return path;
			}else {
				sb.append("<FILES>");
				sb.append("<FILE>");
				sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
				sb.append("</FILE>");
				sb.append("</FILES>");
			}
			System.out.println("Error");
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