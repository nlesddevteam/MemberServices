package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorCompanyBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorCompanyManager;
import com.nlesd.bcs.dao.BussingContractorManager;
public class UpdateContactorInformatonAdminRequestHandler extends RequestHandlerImpl {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
			//success
			HttpSession session = null;
			User usr = null;
			session = request.getSession(false);
		 	usr = (User) session.getAttribute("usr");
			String message="UPDATED";
			Integer cid = Integer.parseInt(request.getParameter("cid"));
			try{
			
			BussingContractorBean origbean = new BussingContractorBean();
			origbean = BussingContractorManager.getBussingContractorById(cid);
			BussingContractorBean ebean = new BussingContractorBean();
			ebean.setId(cid);
			ebean.setFirstName(request.getParameter("firstname"));
			ebean.setLastName(request.getParameter("lastname"));
			ebean.setMiddleName(request.getParameter("middlename"));
			ebean.setEmail(origbean.getEmail());
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
			ebean.setBusinessNumber(request.getParameter("businessnumber"));
			ebean.setHstNumber(request.getParameter("hstnumber"));
			ebean.setStatus(origbean.getStatus());
			//update the archive table first
			BussingContractorManager.updateBussingContractorArc(origbean);
			BussingContractorManager.updateBussingContractor(ebean);
			//update audit trail
			AuditTrailBean atbean = new AuditTrailBean();
			atbean.setEntryType(EntryTypeConstant.CONTRACTORCONTACTUPDATED);
			atbean.setEntryId(origbean.getId());
			atbean.setEntryTable(EntryTableConstant.CONTRACTORS);
			DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
			atbean.setEntryNotes("Contractor Info updated by " + usr.getPersonnel().getFullNameReverse() + " on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
			atbean.setContractorId(ebean.getId());
			AuditTrailManager.addAuditTrail(atbean);
			}catch(Exception e){
				message = e.getMessage();
			}
			//now we update the company information
			BussingContractorCompanyBean bccbean = new BussingContractorCompanyBean();
			bccbean.setContractorId(cid);
		    if(request.getParameter("tregular") == null){
		    	bccbean.settRegular("N");
		    }else{
		    	bccbean.settRegular("Y");
		    }
		    if(request.getParameter("talternate") == null){
		    	bccbean.settAlternate("N");
		    }else{
		    	bccbean.settAlternate("Y");
		    }
		    if(request.getParameter("tparent") == null){
		    	bccbean.settParent("N");
		    }else{
		    	bccbean.settParent("Y");
		    }
		    if(request.getParameter("crsameas") == null){
		    	bccbean.setCrSameAs("N");
		    }else{
		    	bccbean.setCrSameAs("Y");
		    }
		    bccbean.setCrFirstName(request.getParameter("crfirstname"));
		    bccbean.setCrLastName(request.getParameter("crlastname"));
		    bccbean.setCrEmail(request.getParameter("cremail"));
		    bccbean.setCrPhoneNumber(request.getParameter("crphonenumber"));
		    if(request.getParameter("tosameas") == null){
		    	bccbean.setToSameAs("N");
		    }else{
		    	bccbean.setToSameAs("Y");
		    }
		    bccbean.setToFirstName(request.getParameter("tofirstname"));
		    bccbean.setToLastName(request.getParameter("tolastname"));
		    bccbean.setToEmail(request.getParameter("toemail"));
		    bccbean.setToPhoneNumber(request.getParameter("tophonenumber"));
			if(cid > 0){
				//get a copy of the original
				BussingContractorCompanyBean origbeanc = BussingContractorCompanyManager.getBussingContractorCompanyById(cid);
			    //update record
				BussingContractorCompanyManager.updateBussingContractorCompany(bccbean);
				//insert arc record
				BussingContractorCompanyManager.updateBussingContractorCompanyArc(origbeanc);
			}else{
			    //insert record
				BussingContractorCompanyManager.addBussingContractorCompany(bccbean);

			}
			
			//update audit trail
			AuditTrailBean atbean = new AuditTrailBean();
			atbean.setEntryType(EntryTypeConstant.CONTRACTORCOMPANYUPDATED);
			atbean.setEntryId(bccbean.getId());
			atbean.setEntryTable(EntryTableConstant.CONTRACTORCOMPANY);
			DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
			atbean.setEntryNotes("Contractor Company Info updated by " + usr.getPersonnel().getFullNameReverse() + " on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
			atbean.setContractorId(cid);
			AuditTrailManager.addAuditTrail(atbean);
		
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