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
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorManager;
import com.nlesd.bcs.dao.BussingContractorVehicleDocumentManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
public class DeleteContractorVehicleDocumentRequestHandler extends BCSApplicationRequestHandlerImpl {
	public DeleteContractorVehicleDocumentRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("vid"),
				new RequiredFormElement("did"),
				new RequiredFormElement("document")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		String xml = null;
		if (validate_form() && !(this.sessionExpired)) {
	        Integer vid = form.getInt("vid");
	        Integer did = form.getInt("did");
	        String documentname =form.get("document");
	        boolean result=false;
	        result = BussingContractorVehicleDocumentManager.deleteContractorVehicleDocument(did);
	        //BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
	        BussingContractorVehicleBean vehbean = BussingContractorVehicleManager.getBussingContractorVehicleById(vid);
	        BussingContractorBean bcbean = BussingContractorManager.getBussingContractorById(vehbean.getContractorId());   
			//update audit trail
			AuditTrailBean atbean = new AuditTrailBean();
			atbean.setEntryType(EntryTypeConstant.CONTRACTORVEHICLEDOCDELETED);
			atbean.setEntryId(vid);
			atbean.setEntryTable(EntryTableConstant.CONTRACTORVEHICLEDOC);
			DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
			atbean.setEntryNotes("Contractor vehile doc (" + documentname + ") deleted on  " + dateTimeInstance.format(Calendar.getInstance().getTime()));
			atbean.setContractorId(bcbean.getId());
			AuditTrailManager.addAuditTrail(atbean);
	        if(result){
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>SUCCESS</MESSAGE>");
				sb.append("<VID>" + vid + "</VID>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
			}else{
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>ERROR DELETING VEHICLE</MESSAGE>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
			} 
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
