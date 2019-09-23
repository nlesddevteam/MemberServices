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
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorSystemEmployeeTrainingManager;
public class DeleteEmployeeTrainingRequestHandler extends BCSApplicationRequestHandlerImpl {
	public DeleteEmployeeTrainingRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("vid"),
				new RequiredFormElement("eid"),
				new RequiredFormElement("document")
			});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (validate_form()) {
	        Integer did = form.getInt("vid");
	        Integer eid = form.getInt("eid");
	        String documentname =form.get("document");
	        boolean result=false;
	        result = BussingContractorSystemEmployeeTrainingManager.deleteEmployeeTraining(did);
	        int cid=-1;
			BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
			cid=bcbean.getId();
			BussingContractorEmployeeBean ebean = BussingContractorEmployeeManager.getBussingContractorEmployeeById(eid);
			//update audit trail
			AuditTrailBean atbean = new AuditTrailBean();
			atbean.setEntryType(EntryTypeConstant.EMPLOYEETRAININGDELETED);
			atbean.setEntryId(did);
			atbean.setEntryTable(EntryTableConstant.EMPLOYEETRAINING);
			DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
			atbean.setEntryNotes("Employee training (" + documentname + ") for " + ebean.getFirstName() + " " + ebean.getLastName() +" deleted on  " + dateTimeInstance.format(Calendar.getInstance().getTime()));
			atbean.setContractorId(cid);
			AuditTrailManager.addAuditTrail(atbean);
	        if(result){
				sb.append("<DOCUMENTS>");
				sb.append("<DOCUMENT>");
				sb.append("<MESSAGE>SUCCESS</MESSAGE>");
				sb.append("</DOCUMENT>");
				sb.append("</DOCUMENTS>");
			}else{
				sb.append("<DOCUMENTS>");
				sb.append("<DOCUMENT>");
				sb.append("<MESSAGE>ERROR DELETING VEHICLE</MESSAGE>");
				sb.append("</DOCUMENT>");
				sb.append("</DOCUMENTS>");
			}  
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