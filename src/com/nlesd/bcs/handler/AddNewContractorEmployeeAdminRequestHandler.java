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
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.bean.FileHistoryBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.constants.EmployeeStatusConstant;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorManager;
import com.nlesd.bcs.dao.FileHistoryManager;
public class AddNewContractorEmployeeAdminRequestHandler extends RequestHandlerImpl {
	public AddNewContractorEmployeeAdminRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("employeeposition"),
				new RequiredFormElement("firstname"),
				new RequiredFormElement("lastname"),
				new RequiredFormElement("address1"),
				new RequiredFormElement("city"),
				new RequiredFormElement("province"),
				new RequiredFormElement("postalcode")
			});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		BussingContractorEmployeeBean vbean =  new BussingContractorEmployeeBean();
		String message="UPDATED";
		HttpSession session = null;
		User usr = null;
		session = request.getSession(false);
	 	usr = (User) session.getAttribute("usr");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		if (validate_form()) {
			try {
				//check to see if it is a driver position and if the dl number not in use
				if(form.getInt("employeeposition") == 20 && BussingContractorEmployeeManager.checkEmployeeDLNumber(form.get("dlnumber"))) {
					message="Employee record on file with identical Driver License Number";
				}else {
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
					vbean.setFindingsOfGuilt(form.get("findingsofguilt"));
					if(form.get("birthdate").isEmpty())
					{
						vbean.setBirthDate(null);
					}else{
						vbean.setBirthDate(sdf.parse(form.get("birthdate").toString()));
					}
					vbean.setDaSuspensions(form.get("dasuspensions"));
					vbean.setDaAccidents(form.get("daaccidents"));
					//now we do the documents
					String filelocation="/BCS/documents/employeedocs/";
					String docfilename = "";
					if(form.getUploadFile("dlfront").getFileSize() > 0){
						docfilename=save_file("dlfront", filelocation);
						vbean.setDlFront(docfilename);
					}
					if(form.getUploadFile("dlback").getFileSize() > 0){
						docfilename=save_file("dlback", filelocation);
						vbean.setDlBack(docfilename);
					}
					if(form.getUploadFile("dadocument").getFileSize() > 0){
						docfilename=save_file("dadocument", filelocation);
						vbean.setDaDocument(docfilename);
					}
					if(form.getUploadFile("fadocument").getFileSize() > 0){
						docfilename=save_file("fadocument", filelocation);
						vbean.setFaDocument(docfilename);
					}
					if(form.getUploadFile("prcvsqdocument").getFileSize() > 0){
						docfilename=save_file("prcvsqdocument", filelocation);
						vbean.setPrcvsqDocument(docfilename);
					}
					if(form.getUploadFile("pccdocument").getFileSize() > 0){
						docfilename=save_file("pccdocument", filelocation);
						vbean.setPccDocument(docfilename);
					}
					if(form.getUploadFile("scadocument").getFileSize() > 0){
						docfilename=save_file("scadocument", filelocation);
						vbean.setScaDocument(docfilename);
					}
					vbean.setStatus(EmployeeStatusConstant.NOTREVIEWED.getValue());
					
					//now we add the record
					BussingContractorEmployeeManager.addBussingContractorEmployee(vbean);
					
					//now that we have an id we can update the file history objects
					if(!(vbean.getDlFront() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getDlFront());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(usr.getLotusUserFullName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(1);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getDlBack() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getDlBack());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(usr.getLotusUserFullName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(2);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getDaDocument() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getDaDocument());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(usr.getLotusUserFullName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(3);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getFaDocument() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getFaDocument());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(usr.getLotusUserFullName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(4);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getPrcvsqDocument() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getPrcvsqDocument());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(usr.getLotusUserFullName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(5);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getPccDocument() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getPccDocument());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(usr.getLotusUserFullName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(6);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getScaDocument() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getScaDocument());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(usr.getLotusUserFullName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(7);
						FileHistoryManager.addFileHistory(fhb);
					}
					//update audit trail
					AuditTrailBean atbean = new AuditTrailBean();
					atbean.setEntryType(EntryTypeConstant.CONTRACTOREMPLOYEEADDED);
					atbean.setEntryId(vbean.getId());
					atbean.setEntryTable(EntryTableConstant.CONTRACTOREMPLOYEE);
					DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
					atbean.setEntryNotes(bcbean.getLastName() +"," + bcbean.getFirstName() + "(" + bcbean.getCompany() + ")" + " new employee added (" + vbean.getLastName() + "," + vbean.getFirstName() 
							+ ") added on  " + dateTimeInstance.format(Calendar.getInstance().getTime()) + " by " + usr.getLotusUserFullNameReverse());
					atbean.setContractorId(vbean.getContractorId());
					AuditTrailManager.addAuditTrail(atbean);
				}

				
				}catch(Exception e){
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
		}else {
			message=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
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
