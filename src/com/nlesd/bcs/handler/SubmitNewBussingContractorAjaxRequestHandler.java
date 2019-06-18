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
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.velocity.VelocityUtils;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.constants.StatusConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorManager;
public class SubmitNewBussingContractorAjaxRequestHandler extends PublicAccessRequestHandlerImpl {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		String errormessage="";
		//check to see if email, business number and hst number is unique
		boolean check = BussingContractorManager.checkContractor(request.getParameter("email"), request.getParameter("businessnumber"), request.getParameter("hstnumber"));
		if(check){
			//success
			BussingContractorBean ebean = new BussingContractorBean();
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
			ebean.setStatus(StatusConstant.SUBMITTED.getValue());
			ebean.setBusinessNumber(request.getParameter("businessnumber"));
			ebean.setHstNumber(request.getParameter("hstnumber"));
			BussingContractorManager.addBussingContractor(ebean);
			errormessage="SUCCESS";
			//update audit trail
			AuditTrailBean atbean = new AuditTrailBean();
			atbean.setEntryType(EntryTypeConstant.CONTRACTORSUBMITTED);
			atbean.setEntryId(ebean.getId());
			atbean.setEntryTable(EntryTableConstant.CONTRACTORS);
			DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
			atbean.setEntryNotes("Contractor Information Submitted On " + dateTimeInstance.format(Calendar.getInstance().getTime()));
			atbean.setContractorId(ebean.getId());
			AuditTrailManager.addAuditTrail(atbean);
			
			//now we send confirmation email
			HashMap<String, Object> model = new HashMap<String, Object>();
			try {
				//applicant
				EmailBean email = new EmailBean();
				email.setTo(ebean.getEmail());
				email.setFrom("bussingcontractorsystem@nlesd.ca");
				email.setSubject("Newfoundland and Labrador English School District - Bussing Contractor System Registration");
				email.setBody(VelocityUtils.mergeTemplateIntoString(
						"bcs/confirm_submission.vm", model));
				//email.send();
				//finance approval
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			//already exists
			errormessage="Email and/or business number and/or hst number already registered in system";
		}
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		sb.append("<CONTRACTORS>");
		sb.append("<CONTRACTOR>");
		sb.append("<MESSAGE>" + errormessage + "</MESSAGE>");
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
