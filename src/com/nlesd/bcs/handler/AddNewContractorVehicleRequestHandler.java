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
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.constants.VehicleStatusConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
public class AddNewContractorVehicleRequestHandler extends BCSApplicationRequestHandlerImpl {
	public AddNewContractorVehicleRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		BussingContractorVehicleBean vbean = new BussingContractorVehicleBean();
		String message="UPDATED";
		try {
				
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
				vbean.setvMake(form.getInt("vmake"));
				vbean.setvModel(-1);
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
				//update audit trail
				AuditTrailBean atbean = new AuditTrailBean();
				atbean.setEntryType(EntryTypeConstant.CONTRACTORVEHICLEADDED);
				atbean.setEntryId(vbean.getId());
				atbean.setEntryTable(EntryTableConstant.CONTRACTORVEHICLE);
				DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
				atbean.setEntryNotes("Contractor vehicle (" + vbean.getvPlateNumber() + ") added on  " + dateTimeInstance.format(Calendar.getInstance().getTime()));
				atbean.setContractorId(vbean.getContractorId());
				AuditTrailManager.addAuditTrail(atbean);
				}catch(Exception e){
					message = e.getMessage();
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