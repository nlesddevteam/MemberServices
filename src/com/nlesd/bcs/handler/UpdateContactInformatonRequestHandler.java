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
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.constants.StatusConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorManager;
public class UpdateContactInformatonRequestHandler  extends BCSApplicationRequestHandlerImpl {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
			//success
			String message="UPDATED";
			try{
			Integer cid = Integer.parseInt(request.getParameter("cid"));
			BussingContractorBean origbean = new BussingContractorBean();
			origbean = BussingContractorManager.getBussingContractorById(cid);
			BussingContractorBean ebean = new BussingContractorBean();
			ebean.setId(cid);
			ebean.setFirstName(request.getParameter("firstname"));
			ebean.setLastName(request.getParameter("lastname"));
			ebean.setMiddleName(request.getParameter("middlename"));
			ebean.setEmail(request.getParameter("email"));
			ebean.setAddress1(request.getParameter("address1"));
			ebean.setAddress2(request.getParameter("address2"));
			ebean.setCity(request.getParameter("city"));
			ebean.setProvince(request.getParameter("province"));
			ebean.setPostalCode(request.getParameter("postalcode"));
			ebean.setHomePhone(request.getParameter("homephone"));
			ebean.setCellPhone(request.getParameter("cellphone"));
			ebean.setWorkPhone(request.getParameter("workphone"));
			ebean.setCompany(request.getParameter("companyname"));
			ebean.setMaddress1(request.getParameter("maddress1"));
			ebean.setMaddress2(request.getParameter("maddress2"));
			ebean.setMcity(request.getParameter("mcity"));
			ebean.setMprovince(request.getParameter("mprovince"));
			ebean.setMpostalCode(request.getParameter("mpostalcode"));
			if(request.getParameter("msameas") == null){
				ebean.setMsameAs("N");
			}else{
				ebean.setMsameAs("Y");
			}
			
			ebean.setStatus(StatusConstant.APPROVED.getValue());
			//update the archive table first
			BussingContractorManager.updateBussingContractorArc(origbean);
			BussingContractorManager.updateBussingContractor(ebean);
			//update audit trail
			AuditTrailBean atbean = new AuditTrailBean();
			atbean.setEntryType(EntryTypeConstant.CONTRACTORCONTACTUPDATED);
			atbean.setEntryId(origbean.getId());
			atbean.setEntryTable(EntryTableConstant.CONTRACTORS);
			DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
			atbean.setEntryNotes("Contractor Contact Info updated on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
			atbean.setContractorId(ebean.getId());
			AuditTrailManager.addAuditTrail(atbean);
			}catch(Exception e){
				message = e.getMessage();
			}
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
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
			return null;
	}
}
