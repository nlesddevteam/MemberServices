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
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.bean.BussingContractorSystemRegionalBean;
import com.nlesd.bcs.bean.FileHistoryBean;
import com.nlesd.bcs.constants.EmployeeStatusConstant;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorSystemRegionalManager;
import com.nlesd.bcs.dao.FileHistoryManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequiredFormElement;
public class AddNewContractorEmployeeRequestHandler extends BCSApplicationRequestHandlerImpl {
	public AddNewContractorEmployeeRequestHandler() {
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
		if (validate_form() && !(this.sessionExpired)) {
		try {
				//check to see if it is a driver position and if the dl number not in use
				if(form.getInt("employeeposition") == 20 && BussingContractorEmployeeManager.checkEmployeeDLNumber(form.get("dlnumber"))) {
					message="Employee record on file with identical Driver Licence Number";
				}else {
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
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
					if(form.get("coddate").isEmpty())
					{
						vbean.setCodExpiryDate(null);
					}else{
						vbean.setCodExpiryDate(sdf.parse(form.get("coddate").toString()));
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
					if(form.getUploadFile("coddocument").getFileSize() > 0){
						docfilename=save_file("coddocument", filelocation);
						vbean.setCodDocument(docfilename);
					}
					vbean.setStatus(EmployeeStatusConstant.NOTREVIEWED.getValue());
					vbean.setFaLevelC(form.get("falevelc"));
					if(form.exists("vulsector")) {
						int testingint = form.getInt("vulsector");
						vbean.setVulnerableSector(testingint);
					}else {
						vbean.setVulnerableSector(0);
					}
					if(form.exists("contypes")) {
						String[] test = form.getArray("contypes");
						//System.out.println(Arrays.toString(test));
						StringBuilder sb1 = new StringBuilder();
						for( String s: test) {
							if(sb1.length() == 0) {
								sb1.append(s);
							}else {
								sb1.append(",");
								sb1.append(s);
							}
						}
						vbean.setConvictionTypes(sb1.toString());
					}else {
						vbean.setConvictionTypes(null);
					}
					vbean.setDemeritPoints(form.getInt("demeritpoints"));
					if(form.exists("dangerousdriving")) {
						vbean.setDangerousDriving(form.get("dangerousdriving"));
					}
					if(form.exists("suspensions")) {
						vbean.setSuspensions(form.get("suspensions"));
					}					
					//now we add the record
					BussingContractorEmployeeManager.addBussingContractorEmployee(vbean);
					
					//now we add the regional info if
					if(bcbean.getBoardOwned().equals("Y")) {
						BussingContractorSystemRegionalBean regbean = new BussingContractorSystemRegionalBean();
						regbean.setrType("E");
						regbean.setrId(vbean.getId());
						regbean.setRegionCode(form.getInt("regioncode"));
						regbean.setDepotCode(form.getInt("depotcode"));
						BussingContractorSystemRegionalManager.addBussingContractorSystemRegionalBean(regbean);
					}
					
					//now that we have an id we can update the file history objects
					if(!(vbean.getDlFront() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getDlFront());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(bcbean.getContractorName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(1);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getDlBack() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getDlBack());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(bcbean.getContractorName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(2);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getDaDocument() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getDaDocument());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(bcbean.getContractorName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(3);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getFaDocument() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getFaDocument());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(bcbean.getContractorName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(4);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getPrcvsqDocument() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getPrcvsqDocument());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(bcbean.getContractorName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(5);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getPccDocument() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getPccDocument());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(bcbean.getContractorName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(6);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getScaDocument() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getScaDocument());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(bcbean.getContractorName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(7);
						FileHistoryManager.addFileHistory(fhb);
					}
					if(!(vbean.getCodDocument() ==  null)) {
						//now we save document history record
						FileHistoryBean fhb = new FileHistoryBean();
						fhb.setFileName(vbean.getCodDocument());
						fhb.setFileAction("UPLOADED");
						fhb.setActionBy(bcbean.getContractorName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(15);
						FileHistoryManager.addFileHistory(fhb);
					}
					//update audit trail
					AuditTrailBean atbean = new AuditTrailBean();
					atbean.setEntryType(EntryTypeConstant.CONTRACTOREMPLOYEEADDED);
					atbean.setEntryId(vbean.getId());
					atbean.setEntryTable(EntryTableConstant.CONTRACTOREMPLOYEE);
					DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
					atbean.setEntryNotes("Contractor employee (" + vbean.getLastName() + "," + vbean.getFirstName() + ") added on  " + dateTimeInstance.format(Calendar.getInstance().getTime()));
					atbean.setContractorId(vbean.getContractorId());
					AuditTrailManager.addAuditTrail(atbean);
				}

				
				}catch(Exception e){
					if(this.sessionExpired) {
						path="contractorLogin.html?msg=Session expired, please login again.";
						return path;
					}else {
						message=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
					}
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
