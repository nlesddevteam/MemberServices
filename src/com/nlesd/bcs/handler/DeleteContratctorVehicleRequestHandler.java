package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;

public class DeleteContratctorVehicleRequestHandler extends PublicAccessRequestHandlerImpl {
	public DeleteContratctorVehicleRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
        Integer vid = form.getInt("vid");
        String plate =form.get("plate");
        boolean result=false;
        int cid=-1;
		
		if(form.get("ttype").equals("A")){
			cid=-1;
		}else{
			BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
			cid=bcbean.getId();
		}
		 result = BussingContractorVehicleManager.deleteContractorVehicle(vid);
        //update audit trail
		AuditTrailBean atbean = new AuditTrailBean();
		atbean.setEntryType(EntryTypeConstant.CONTRACTORVEHICLEDELETED);
		atbean.setEntryId(vid);
		atbean.setEntryTable(EntryTableConstant.CONTRACTORVEHICLE);
		DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
		atbean.setEntryNotes("Contractor vehicle (" + plate + ") deleted on  " + dateTimeInstance.format(Calendar.getInstance().getTime()));
		atbean.setContractorId(cid);
		AuditTrailManager.addAuditTrail(atbean);
        if(result){
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CONTRACTORS>");
			sb.append("<CONTRACTOR>");
			sb.append("<MESSAGE>SUCCESS</MESSAGE>");
			sb.append("</CONTRACTOR>");
			sb.append("</CONTRACTORS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
        }else{
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CONTRACTORS>");
			sb.append("<CONTRACTOR>");
			sb.append("<MESSAGE>ERROR DELETING VEHICLE</MESSAGE>");
			sb.append("</CONTRACTOR>");
			sb.append("</CONTRACTORS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
        }   return null;
	}
}