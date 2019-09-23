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
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorCompanyBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.constants.StatusConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorCompanyManager;
import com.nlesd.bcs.dao.BussingContractorManager;
public class AdminAddNewContractorAjaxRequestHandler extends RequestHandlerImpl {
	public AdminAddNewContractorAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("busnumber"),
				new RequiredFormElement("hstnumber"),
				new RequiredFormElement("firstname"),
				new RequiredFormElement("lastname"),
				new RequiredFormElement("conemail"),
				new RequiredFormElement("address1"),
				new RequiredFormElement("city"),
				new RequiredFormElement("province"),
				new RequiredFormElement("postalcode"),
				new RequiredFormElement("homephone")
				
			});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
			super.handleRequest(request, response);
			//success
			String message="ADDED";
			BussingContractorBean ebean = new BussingContractorBean();
			ebean.setId(-1);
			if (validate_form()) {
				try{
					if(BussingContractorManager.checkContractor(request.getParameter("conemail"), request.getParameter("busnumber"), request.getParameter("hstnumber"))){
						ebean.setFirstName(request.getParameter("firstname"));
						ebean.setLastName(request.getParameter("lastname"));
						ebean.setMiddleName(request.getParameter("middlename"));
						ebean.setEmail(request.getParameter("conemail"));
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
						ebean.setBusinessNumber(request.getParameter("busnumber"));
						ebean.setHstNumber(request.getParameter("hstnumber"));
						//update the archive table first
						BussingContractorManager.addBussingContractorAdmin(ebean);
						//update audit trail
						AuditTrailBean atbean = new AuditTrailBean();
						atbean.setEntryType(EntryTypeConstant.CONTRACTORCONFIRMED);
						atbean.setEntryId(ebean.getId());
						atbean.setEntryTable(EntryTableConstant.CONTRACTORS);
						DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
						atbean.setEntryNotes("Contractor(" + ebean.getLastName() + "," + ebean.getFirstName() +") Added by " + usr.getPersonnel().getFullNameReverse() + " on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
						atbean.setContractorId(ebean.getId());
						AuditTrailManager.addAuditTrail(atbean);
						
						//now we update the company information
						BussingContractorCompanyBean bccbean = new BussingContractorCompanyBean();
						bccbean.setContractorId(ebean.getId());
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
						BussingContractorCompanyManager.addBussingContractorCompany(bccbean);
						//update audit trail
						atbean.setEntryType(EntryTypeConstant.CONTRACTORCOMPANYUPDATED);
						atbean.setEntryId(bccbean.getId());
						atbean.setEntryTable(EntryTableConstant.CONTRACTORCOMPANY);
						atbean.setEntryNotes("Contractor Company (" + ebean.getLastName() + "," + ebean.getFirstName() +") Info updated by " + usr.getPersonnel().getFullNameReverse() + " on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
						atbean.setContractorId(ebean.getId());
						AuditTrailManager.addAuditTrail(atbean);
						//now we create a temporay password
						String tmppwd = BussingContractorManager.createTempoararyPassword();
						Integer sid = BussingContractorManager.addBussingContractorSecurity(ebean.getId(),ebean.getEmail(),tmppwd);
						//update audit trail
						//AuditTrailBean atbean = new AuditTrailBean();
						atbean.setEntryType(EntryTypeConstant.CONTRACTORSECURITYADDED);
						atbean.setEntryId(sid);
						atbean.setEntryTable(EntryTableConstant.CONTRACTORSECURITY);
						atbean.setEntryNotes("Contractor Security (" + ebean.getLastName() + "," + ebean.getFirstName() +") created by " + usr.getPersonnel().getFullNameReverse() + " on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
						atbean.setContractorId(ebean.getId());
						AuditTrailManager.addAuditTrail(atbean);
					}else{
						message="Email, HST Number or Business Number already used";
					}
				}catch(Exception e){
					message = e.getMessage();
				}
			}else {
				message=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
			}
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CONTRACTORS>");
			sb.append("<CONTRACTOR>");
			sb.append("<MESSAGE>" + message + "</MESSAGE>");
			sb.append("<ID>" + ebean.getId() + "</ID>");
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
