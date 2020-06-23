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
import com.nlesd.bcs.bean.BussingContractorSystemContractBean;
import com.nlesd.bcs.bean.BussingContractorSystemContractHistoryBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorSystemContractHistoryManager;
import com.nlesd.bcs.dao.BussingContractorSystemContractManager;
public class AdminAddNewContractAjaxRequestHandler extends RequestHandlerImpl{
	public AdminAddNewContractAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("contractname"),
				new RequiredFormElement("contracttype")
			});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			String message="SUCCESS";
			BussingContractorSystemContractBean vbean = new BussingContractorSystemContractBean();
			int cid = form.getInt("cid");
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			//now we check to see if it is a board owned contract
			if (validate_form()) {
				if(usr.checkPermission("BCS-VIEW-WESTERN")){
					vbean.setBoardOwned(BoardOwnedContractorsConstant.WESTERN.getValue());
					vbean.setContractRegion(71);
				
				}
				if(usr.checkPermission("BCS-VIEW-CENTRAL")){
					vbean.setBoardOwned(BoardOwnedContractorsConstant.CENTRAL.getValue());
					vbean.setContractRegion(68);
				}
				if(usr.checkPermission("BCS-VIEW-LABRADOR")){
					vbean.setBoardOwned(BoardOwnedContractorsConstant.LABRADOR.getValue());
					vbean.setContractRegion(70);
				}
				try {
					//get fields
					vbean.setContractName(form.get("contractname"));
					vbean.setContractType(form.getInt("contracttype"));
					vbean.setContractNotes(form.get("contractnotes"));
					if(form.exists("contractregion")){
						vbean.setContractRegion(form.getInt("contractregion"));
					}
					if(form.get("contractexpirydate").isEmpty())
					{
						vbean.setContractExpiryDate(null);
					}else{
						vbean.setContractExpiryDate(sdf.parse(form.get("contractexpirydate").toString()));
					}
					vbean.setAddedBy(usr.getPersonnel().getFullNameReverse());
					//vbean.setVehicleSize(-1);
					//vbean.setVehicleType(-1);
					//using vehicle size for issubcontracted
					//using vehicle type for subcontractor
					if(form.exists("subcon")) {
						vbean.setSubContracted(true);
					}else {
						vbean.setSubContracted(false);
					}
					vbean.setSubContractorId(form.getInt("subcontractor"));
					if(form.get("contractstartdate").isEmpty())
					{
						vbean.setContractStartDate(null);
					}else{
						vbean.setContractStartDate(sdf.parse(form.get("contractstartdate").toString()));
					}
					//save file to db
					if(cid > 0){
						vbean.setId(cid);
						BussingContractorSystemContractManager.updateBussingContractorSystemContract(vbean);
					}else{
						BussingContractorSystemContractManager.addBussingContractorSystemContract(vbean);
						//now we update the status to not awarded
						BussingContractorSystemContractHistoryBean chbean = new BussingContractorSystemContractHistoryBean();
						chbean.setContractStatus(84);
						chbean.setContractId(vbean.getId());
						chbean.setContractorId(-1);
						chbean.setStatusNotes("Contract created by " + usr.getPersonnel().getFullNameReverse() );
						chbean.setStatusBy(usr.getPersonnel().getFullNameReverse());
						BussingContractorSystemContractHistoryManager.addBussingContractorSystemContractHistory(chbean);
					}
					//update audit trail
					AuditTrailBean atbean = new AuditTrailBean();
					if(cid > 0){
						atbean.setEntryType(EntryTypeConstant.UPDATECONTRACT);
					}else{
						atbean.setEntryType(EntryTypeConstant.ADDNEWCONTRACT);
					}
					
					atbean.setEntryId(vbean.getId());
					atbean.setEntryTable(EntryTableConstant.CONTRACT);
					DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
					if(cid > 0){
						atbean.setEntryNotes("Contract:  (" + vbean.getContractName() + ") updated by: " + usr.getPersonnel().getFullNameReverse() + " on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
					}else{
						atbean.setEntryNotes("Contract:  (" + vbean.getContractName() + ") added by: " + usr.getPersonnel().getFullNameReverse() + " on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
					}
					atbean.setContractorId(0);
					AuditTrailManager.addAuditTrail(atbean);
	            }
				catch (Exception e) {
					message=e.getMessage();
			
					
				}
			}else {
				message=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
			}

			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CONTRACTORS>");
			sb.append("<CONTRACTOR>");
			sb.append("<MESSAGE>" + message + "</MESSAGE>");
			sb.append("<ID>" + vbean.getId()+ "</ID>");
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
