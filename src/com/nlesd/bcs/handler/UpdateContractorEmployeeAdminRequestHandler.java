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
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.bean.BussingContractorSystemRegionalBean;
import com.nlesd.bcs.bean.FileHistoryBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorDateHistoryManager;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorManager;
import com.nlesd.bcs.dao.BussingContractorSystemRegionalManager;
import com.nlesd.bcs.dao.FileHistoryManager;
public class UpdateContractorEmployeeAdminRequestHandler extends RequestHandlerImpl {
	public UpdateContractorEmployeeAdminRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid"),
				new RequiredFormElement("employeeposition"),
				new RequiredFormElement("firstname"),
				new RequiredFormElement("lastname"),
				new RequiredFormElement("email"),
				new RequiredFormElement("address1"),
				new RequiredFormElement("city"),
				new RequiredFormElement("province"),
				new RequiredFormElement("postalcode")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			//success
			String message="UPDATED";
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			if (validate_form()) {
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
					if(form.get("coddate").isEmpty())
					{
						vbean.setCodExpiryDate(null);
					}else{
						vbean.setCodExpiryDate(sdf.parse(form.get("coddate").toString()));
					}
					vbean.setFindingsOfGuilt(form.get("findingsofguilt"));
					vbean.setDaSuspensions(form.get("dasuspensions"));
					vbean.setDaAccidents(form.get("daaccidents"));
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
					//now we do the documents
					String filelocation="/BCS/documents/employeedocs/";
					String docfilename = "";
					if(form.getUploadFile("dlfront").getFileSize() > 0){
						docfilename=save_file("dlfront", filelocation);
						vbean.setDlFront(docfilename);
						//now we update the history record
						FileHistoryBean fhb = new FileHistoryBean();
						if(origbean.getDlFront() == null) {
							fhb.setFileName("No Previous File");
						}else {
							fhb.setFileName(origbean.getDlFront());
						}
						fhb.setFileAction("DOCUMENT REPLACED");
						fhb.setActionBy(usr.getLotusUserFullName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(1);
						FileHistoryManager.addFileHistory(fhb);
					}else{
						vbean.setDlFront(origbean.getDlFront());
					}
					if(form.getUploadFile("dlback").getFileSize() > 0){
						docfilename=save_file("dlback", filelocation);
						vbean.setDlBack(docfilename);
						//now we update the history record
						FileHistoryBean fhb = new FileHistoryBean();
						if(origbean.getDlBack() == null) {
							fhb.setFileName("No Previous File");
						}else {
							fhb.setFileName(origbean.getDlBack());
						}
						fhb.setFileAction("DOCUMENT REPLACED");
						fhb.setActionBy(usr.getLotusUserFullName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(2);
						FileHistoryManager.addFileHistory(fhb);
					}else{
						vbean.setDlBack(origbean.getDlBack());
					}
					if(form.getUploadFile("dadocument").getFileSize() > 0){
						docfilename=save_file("dadocument", filelocation);
						vbean.setDaDocument(docfilename);
						//now we update the history record
						FileHistoryBean fhb = new FileHistoryBean();
						if(origbean.getDaDocument() == null) {
							fhb.setFileName("No Previous File");
						}else {
							fhb.setFileName(origbean.getDaDocument());
						}
						fhb.setFileAction("DOCUMENT REPLACED");
						fhb.setActionBy(usr.getLotusUserFullName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(3);
						FileHistoryManager.addFileHistory(fhb);
					}else{
						vbean.setDaDocument(origbean.getDaDocument());
					}
					if(form.getUploadFile("fadocument").getFileSize() > 0){
						docfilename=save_file("fadocument", filelocation);
						vbean.setFaDocument(docfilename);
						//now we update the history record
						FileHistoryBean fhb = new FileHistoryBean();
						if(origbean.getFaDocument() == null) {
							fhb.setFileName("No Previous File");
						}else {
							fhb.setFileName(origbean.getFaDocument());
						}
						fhb.setFileAction("DOCUMENT REPLACED");
						fhb.setActionBy(usr.getLotusUserFullName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(4);
						FileHistoryManager.addFileHistory(fhb);
					}else{
						vbean.setFaDocument(origbean.getFaDocument());
					}
					if(form.getUploadFile("prcvsqdocument").getFileSize() > 0){
						docfilename=save_file("prcvsqdocument", filelocation);
						vbean.setPrcvsqDocument(docfilename);
						//now we update the history record
						FileHistoryBean fhb = new FileHistoryBean();
						if(origbean.getPrcvsqDocument() == null) {
							fhb.setFileName("No Previous File");
						}else {
							fhb.setFileName(origbean.getPrcvsqDocument());
						}
						fhb.setFileAction("DOCUMENT REPLACED");
						fhb.setActionBy(usr.getLotusUserFullName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(5);
						FileHistoryManager.addFileHistory(fhb);
					}else{
						vbean.setPrcvsqDocument(origbean.getPrcvsqDocument());
					}
					if(form.getUploadFile("pccdocument").getFileSize() > 0){
						docfilename=save_file("pccdocument", filelocation);
						vbean.setPccDocument(docfilename);
						//now we update the history record
						FileHistoryBean fhb = new FileHistoryBean();
						if(origbean.getPccDocument() == null) {
							fhb.setFileName("No Previous File");
						}else {
							fhb.setFileName(origbean.getPccDocument());
						}
						fhb.setFileAction("DOCUMENT REPLACED");
						fhb.setActionBy(usr.getLotusUserFullName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(6);
						FileHistoryManager.addFileHistory(fhb);
					}else{
						vbean.setPccDocument(origbean.getPccDocument());
					}
					if(form.getUploadFile("scadocument").getFileSize() > 0){
						docfilename=save_file("scadocument", filelocation);
						vbean.setScaDocument(docfilename);
						//now we update the history record
						FileHistoryBean fhb = new FileHistoryBean();
						if(origbean.getScaDocument() == null) {
							fhb.setFileName("No Previous File");
						}else {
							fhb.setFileName(origbean.getScaDocument());
						}
						fhb.setFileAction("DOCUMENT REPLACED");
						fhb.setActionBy(usr.getLotusUserFullName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(7);
						FileHistoryManager.addFileHistory(fhb);
					}else{
						vbean.setScaDocument(origbean.getScaDocument());
					}
					if(form.getUploadFile("coddocument").getFileSize() > 0){
						docfilename=save_file("coddocument", filelocation);
						vbean.setCodDocument(docfilename);
						//now we update the history record
						FileHistoryBean fhb = new FileHistoryBean();
						if(origbean.getCodDocument() == null) {
							fhb.setFileName("No Previous File");
						}else {
							fhb.setFileName(origbean.getCodDocument());
						}
						fhb.setFileAction("DOCUMENT REPLACED");
						fhb.setActionBy(usr.getLotusUserFullName());
						fhb.setParentObjectId(vbean.getId());
						fhb.setParentObjectType(15);
						FileHistoryManager.addFileHistory(fhb);
					}else{
						vbean.setCodDocument(origbean.getCodDocument());
					}
					vbean.setStatus(origbean.getStatus());
				
				//now we add the record
				BussingContractorEmployeeManager.updateBussingContractorEmployee(vbean);
				//now we add the regional info if
				
				BussingContractorSystemRegionalBean regbean = new BussingContractorSystemRegionalBean();
				regbean.setrType("E");
				regbean.setrId(vbean.getId());
				regbean.setRegionCode(form.getInt("regioncode"));
				regbean.setDepotCode(form.getInt("depotcode"));
				if(origbean.getRegionBean() == null) {
					BussingContractorSystemRegionalManager.addBussingContractorSystemRegionalBean(regbean);
				}else {
					regbean.setId(origbean.getRegionBean().getId());
					BussingContractorSystemRegionalManager.updateBussingContractorSystemRegionalBean(regbean);
				}
				
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
					// generate XML for candidate details.
					sb.append("<CONTRACTOR>");
					sb.append("<CONTRACTORSTATUS>");
					sb.append("<MESSAGE>ERROR SETTING CONTRACTOR STATUS</MESSAGE>");
					sb.append("</CONTRACTORSTATUS>");
					sb.append("</CONTRACTOR>");
					xml = sb.toString().replaceAll("&", "&amp;");
					PrintWriter out = response.getWriter();
					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
					path = null;
				}
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>" + message + "</MESSAGE>");
				sb.append("<VID>" + vbean.getId() + "</VID>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
			}else {
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
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
