package com.esdnl.webupdatesystem.tenders.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.tenders.bean.TenderExceptionBean;
import com.esdnl.webupdatesystem.tenders.bean.TendersBean;
import com.esdnl.webupdatesystem.tenders.constants.TenderRenewal;
import com.esdnl.webupdatesystem.tenders.constants.TenderStatus;
import com.esdnl.webupdatesystem.tenders.dao.TenderExceptionManager;
import com.esdnl.webupdatesystem.tenders.dao.TendersManager;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

import javazoom.upload.UploadFile;

public class AddNewTenderRequestHandler extends RequestHandlerImpl {

	public AddNewTenderRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {				
			
				//new RequiredFormElement("tender_number", "Tender Number is required."),
				//new RequiredFormElement("closing_date", "Closing Date is required."),
				//new RequiredFormElement("opening_location", "Opening Location is required."),				
				//new RequiredFormElement("tender_title", "Tender Title is required."),				
				//new RequiredFormElement("region", "Region is required."),				
				
		});
		this.requiredPermissions = new String[] {
				"TENDER-ADMIN"
		};

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);
		String filelocation = "";
		String newfilename = "";
		String tenderfilename = "";
		UploadFile file = null;
		try {
			if (form.get("op") == null) {
				Collection<SchoolZoneBean> list = SchoolZoneService.getSchoolZoneBeans();
				request.setAttribute("regions", list);
				Map<Integer, String> statuslist = new HashMap<Integer, String>();
				for (TenderStatus t : TenderStatus.ALL) {
					statuslist.put(t.getValue(), t.getDescription());
				}
				request.setAttribute("statuslist", statuslist);
				request.setAttribute("renewallist", TenderRenewal.ALL);
				path = "add_new_tender.jsp";
				System.out.println("loading start form");
			}
			else {
				//check file
				if (form.uploadFileExists("tender_doc")) {
					file = (UploadFile) form.getUploadFiles().get("tender_doc");
					filelocation = "/../../nlesdweb/WebContent/includes/files/tenders/doc/";
					newfilename = save_file("tender_doc", filelocation);
					tenderfilename = file.getFileName();

				}

				//check mandatory fields
				if (validate_form()) {
					//parse the fields
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					TendersBean tb = new TendersBean();
					
					if (StringUtils.isEmpty(form.get("tender_number"))) {
						tb.setTenderNumber("00-000");
					} else {	
					
					tb.setTenderNumber(form.get("tender_number").toString());
					}
					
					
					tb.setTenderZone(SchoolZoneService.getSchoolZoneBean(Integer.parseInt(form.get("region").toString())));
					tb.setTenderTitle(form.get("tender_title").toString());
					
					if (StringUtils.isEmpty(form.get("closing_date"))) {
						tb.setClosingDate(sdf.parse("00/00/0000".toString()));
					}
					else {
					
					tb.setClosingDate(sdf.parse(form.get("closing_date").toString()));
					}
					
					if (StringUtils.isEmpty(form.get("opening_location"))) {
						tb.setTenderOpeningLocation(SchoolZoneService.getSchoolZoneBean(Integer.parseInt("1".toString())));
					}
					else {
						tb.setTenderOpeningLocation(SchoolZoneService.getSchoolZoneBean(Integer.parseInt(form.get("opening_location").toString())));
					}
					
					
					tb.setTenderDoc(tenderfilename);
					
					tb.setTenderStatus(TenderStatus.get(Integer.parseInt(form.get("tender_status").toString())));
					tb.setAddedBy(usr.getPersonnel().getFullNameReverse());
					tb.setDocUploadName(newfilename);

					if (StringUtils.isEmpty(form.get("awarded_date"))) {
						tb.setAwardedDate(null);
					}
					else {
						tb.setAwardedDate(sdf.parse(form.get("awarded_date").toString()));
					}
					tb.setAwardedTo(form.get("awarded_to"));
					if (StringUtils.isEmpty(form.get("contract_value"))) {
						tb.setContractValue(0);
					}
					else {
						tb.setContractValue(form.getDouble("contract_value"));
					}

					int id = TendersManager.addNewTender(tb);
					//now we add the exception record
					if (!StringUtils.isEmpty(form.get("vendor_name"))) {
						TenderExceptionBean tebean = new TenderExceptionBean();
						tebean.setTenderId(id);
						tebean.setVendorName(form.get("vendor_name"));
						tebean.seteDescription(form.get("edescription"));
						tebean.seteAddress(form.get("eaddress"));
						tebean.seteLocation(form.get("elocation"));
						tebean.setePrice(form.get("eprice"));
						tebean.setPoNumber(form.get("po_number"));
						tebean.seteTerms(form.get("eterms"));
						tebean.setTenderRenewal(TenderRenewal.get(form.getInt("erenewal")));
						tebean.setRenewalother(form.get("erenewalother"));
						tebean.seteClause(form.get("eclause"));
						tebean.setAddedBy(usr.getLotusUserFullName());
						TenderExceptionManager.addNewTenderException(tebean);
						
					}
					
					
					Collection<SchoolZoneBean> list = SchoolZoneService.getSchoolZoneBeans();
					request.setAttribute("regions", list);
					request.setAttribute("renewallist", TenderRenewal.ALL);
					//path = "add_new_tender.jsp";
					request.setAttribute("msgOK", "SUCCESS: Tender has been successfully added.");
					//path = "viewTenders.html";
					path = "add_new_tender.jsp";
				}
				else {
					Collection<SchoolZoneBean> list = SchoolZoneService.getSchoolZoneBeans();
					request.setAttribute("regions", list);
					Map<Integer, String> statuslist = new HashMap<Integer, String>();
					for (TenderStatus t : TenderStatus.ALL) {
						statuslist.put(t.getValue(), t.getDescription());
					}
					request.setAttribute("statuslist", statuslist);
					request.setAttribute("renewallist", TenderRenewal.ALL);
					if (!validate_form()) {
						request.setAttribute("msgERR", validator.getErrorString());
					}
					path = "add_new_tender.jsp";
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "add_new_tender.jsp";
		}
		return path;
	}
}
