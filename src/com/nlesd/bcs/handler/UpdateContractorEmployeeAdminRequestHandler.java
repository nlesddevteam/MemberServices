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
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorDateHistoryManager;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorManager;
public class UpdateContractorEmployeeAdminRequestHandler extends RequestHandlerImpl {
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			//success
			HttpSession session = null;
			User usr = null;
			session = request.getSession(false);
		 	usr = (User) session.getAttribute("usr");
			String message="UPDATED";
			BussingContractorEmployeeBean vbean =  new BussingContractorEmployeeBean();
			BussingContractorEmployeeBean origbean = new BussingContractorEmployeeBean();
			Integer cid = form.getInt("cid");
			origbean = BussingContractorEmployeeManager.getBussingContractorEmployeeById(cid);
			vbean.setId(cid);
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			//BussingContractorBean bcbean = origbean.getBcBean();
			try{
				//vbean.setContractorId(bcbean.getId());
				BussingContractorBean bcbean = null;
				if(form.exists("contractor")){
					bcbean = BussingContractorManager.getBussingContractorById(form.getInt("contractor"));
				}else{
					if(usr.checkPermission("BCS-VIEW-WESTERN")){
						bcbean = BussingContractorManager.getBussingContractorById(BoardOwnedContractorsConstant.WESTERN.getValue());
					}
					if(usr.checkPermission("BCS-VIEW-CENTRAL")){
						bcbean = BussingContractorManager.getBussingContractorById(BoardOwnedContractorsConstant.CENTRAL.getValue());
					}
					if(usr.checkPermission("BCS-VIEW-LABRADOR")){
						bcbean = BussingContractorManager.getBussingContractorById(BoardOwnedContractorsConstant.LABRADOR.getValue());
					}
				}
				vbean.setContractorId(bcbean.getId());
				vbean.setEmployeePosition(form.getInt("employeeposition"));
				vbean.setStartDate(form.get("vmonth") + "-" + form.get("vyear"));
				vbean.setContinuousService(form.get("continuousservice"));
				vbean.setFirstName(form.get("firstname"));
				vbean.setLastName(form.get("lastname"));
				vbean.setMiddleName(form.get("middlename"));
				vbean.setAddress1(form.get("address1"));
				vbean.setAddress2(form.get("address2"));
				vbean.setCity(form.get("city"));
				vbean.setProvince(form.get("province"));
				vbean.setPostalCode(form.get("postalcode"));
				vbean.setHomePhone(form.get("homephone"));
				vbean.setCellPhone(form.get("cellphone"));
				vbean.setEmail(form.get("email"));
				vbean.setDlNumber(form.get("dlnumber"));
				if(form.get("dlexpirydate").isEmpty())
				{
					vbean.setDlExpiryDate(null);
				}else{
					vbean.setDlExpiryDate(sdf.parse(form.get("dlexpirydate").toString()));
				}
				vbean.setDlClass(form.getInt("dlclass"));
				if(form.get("darundate").isEmpty())
				{
					vbean.setDaRunDate(null);
				}else{
					vbean.setDaRunDate(sdf.parse(form.get("darundate").toString()));
				}
				vbean.setDaConvictions(form.get("daconvictions"));
				if(form.get("faexpirydate").isEmpty())
				{
					vbean.setFaExpiryDate(null);
				}else{
					vbean.setFaExpiryDate(sdf.parse(form.get("faexpirydate").toString()));
				}
				if(form.get("pccdate").isEmpty())
				{
					vbean.setPccDate(null);
				}else{
					vbean.setPccDate(sdf.parse(form.get("pccdate").toString()));
				}				
				if(form.get("scadate").isEmpty())
				{
					vbean.setScaDate(null);
				}else{
					vbean.setScaDate(sdf.parse(form.get("scadate").toString()));
				}
				if(form.get("prcvsqdate").isEmpty())
				{
					vbean.setPrcvsqDate(null);
				}else{
					vbean.setPrcvsqDate(sdf.parse(form.get("prcvsqdate").toString()));
				}
				if(form.get("birthdate").isEmpty())
				{
					vbean.setBirthDate(null);
				}else{
					vbean.setBirthDate(sdf.parse(form.get("birthdate").toString()));
				}
				vbean.setFindingsOfGuilt(form.get("findingsofguilt"));
				vbean.setDaSuspensions(form.get("dasuspensions"));
				vbean.setDaAccidents(form.get("daaccidents"));
				//now we do the documents
				String filelocation="/../MemberServices/BCS/documents/employeedocs/";
				String docfilename = "";
				if(form.getUploadFile("dlfront").getFileSize() > 0){
					docfilename=save_file("dlfront", filelocation);
					vbean.setDlFront(docfilename);
				}else{
					vbean.setDlFront(origbean.getDlFront());
				}
				if(form.getUploadFile("dlback").getFileSize() > 0){
					docfilename=save_file("dlback", filelocation);
					vbean.setDlBack(docfilename);
				}else{
					vbean.setDlBack(origbean.getDlBack());
				}
				if(form.getUploadFile("dadocument").getFileSize() > 0){
					docfilename=save_file("dadocument", filelocation);
					vbean.setDaDocument(docfilename);
				}else{
					vbean.setDaDocument(origbean.getDaDocument());
				}
				if(form.getUploadFile("fadocument").getFileSize() > 0){
					docfilename=save_file("fadocument", filelocation);
					vbean.setFaDocument(docfilename);
				}else{
					vbean.setFaDocument(origbean.getFaDocument());
				}
				if(form.getUploadFile("prcvsqdocument").getFileSize() > 0){
					docfilename=save_file("prcvsqdocument", filelocation);
					vbean.setPrcvsqDocument(docfilename);
				}else{
					vbean.setPrcvsqDocument(origbean.getPrcvsqDocument());
				}
				if(form.getUploadFile("pccdocument").getFileSize() > 0){
					docfilename=save_file("pccdocument", filelocation);
					vbean.setPccDocument(docfilename);
				}else{
					vbean.setPccDocument(origbean.getPccDocument());
				}
				if(form.getUploadFile("scadocument").getFileSize() > 0){
					docfilename=save_file("scadocument", filelocation);
					vbean.setScaDocument(docfilename);
				}else{
					vbean.setScaDocument(origbean.getScaDocument());
				}
				vbean.setStatus(origbean.getStatus());
			
			//now we add the record
			BussingContractorEmployeeManager.updateBussingContractorEmployee(vbean);
			//now we add the archive record
			BussingContractorEmployeeManager.addBussingContractorEmployeeArc(origbean);
			//now we check to see if dates changed
			BussingContractorDateHistoryManager.CheckChangedEmployeeDates(origbean, vbean, usr.getLotusUserFullNameReverse());
			//update audit trail
			AuditTrailBean atbean = new AuditTrailBean();
			atbean.setEntryType(EntryTypeConstant.CONTRACTOREMPLOYEEUPDATED);
			atbean.setEntryId(vbean.getId());
			atbean.setEntryTable(EntryTableConstant.CONTRACTOREMPLOYEE);
			DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
			atbean.setEntryNotes(bcbean.getLastName() +"," + bcbean.getFirstName() + "(" + bcbean.getCompany() + ")" + " employee updated (" + vbean.getLastName() + "," + vbean.getFirstName() 
					+ ") added on  " + dateTimeInstance.format(Calendar.getInstance().getTime()) + " by " + usr.getLotusUserFullNameReverse());atbean.setContractorId(vbean.getContractorId());
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
