package com.esdnl.webupdatesystem.tenders.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
import com.esdnl.webupdatesystem.tenders.bean.TenderException;
import com.esdnl.webupdatesystem.tenders.bean.TendersBean;
import com.esdnl.webupdatesystem.tenders.constants.TenderStatus;
import com.esdnl.webupdatesystem.tenders.dao.TendersManager;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

import javazoom.upload.UploadFile;

public class UpdateTenderRequestHandler extends RequestHandlerImpl {

	public UpdateTenderRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("tender_number", "Tender Number is required."),
				new RequiredFormElement("region", "Region is required."),
				new RequiredFormElement("tender_title", "Tender Title is required."),
				new RequiredFormElement("closing_date", "Closing Date is required."),
				new RequiredFormElement("opening_location", "Opening Location is required.")
		});
		this.requiredPermissions = new String[] {
				"TENDER-ADMIN", "TENDER-EDIT"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);
		String filelocation = "/../../nlesdweb/WebContent/includes/files/tenders/doc/";
		String newfilename = "";
		String origfilename = "";
		UploadFile file = null;
		try {
			//get copy of original to use for file update and deletion
			TendersBean tbb = TendersManager.getTenderById(form.getInt("id"));
			//check file
			if (form.uploadFileExists("tender_doc")) {
				file = (UploadFile) form.getUploadFiles().get("tender_doc");
				//save the file
				newfilename = save_file("tender_doc", filelocation);
				origfilename = file.getFileName();

			}
			else {
				//no new file uploaded so we will retrieve the exisiting info
				newfilename = tbb.getDocUploadName();
				origfilename = tbb.getTenderDoc();

			}
			//check mandatory fields
			if (validate_form()) {
				//parse the fields
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				TendersBean tb = new TendersBean();
				tb.setTenderNumber(form.get("tender_number").toString());
				tb.setTenderZone(SchoolZoneService.getSchoolZoneBean(Integer.parseInt(form.get("region").toString())));
				tb.setTenderTitle(form.get("tender_title").toString());
				tb.setClosingDate(sdf.parse(form.get("closing_date").toString()));
				tb.setTenderDoc(origfilename);
				tb.setTenderOpeningLocation(
						SchoolZoneService.getSchoolZoneBean(Integer.parseInt(form.get("opening_location").toString())));
				tb.setTenderStatus(TenderStatus.get(Integer.parseInt(form.get("tender_status").toString())));
				tb.setAddedBy(usr.getPersonnel().getFullNameReverse());
				tb.setDocUploadName(newfilename);
				if (form.get("awarded_date") == null) {
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
				tb.setId(form.getInt("id"));
				//int id = TendersManager.addNweTender(tb);
				TendersManager.updateTender(tb);
				//now remove the origninal file from server
				if (!(origfilename == null) && !(tbb.getTenderDoc() == null)) {
					if (!tbb.getTenderDoc().equals(origfilename)) {
						delete_file(filelocation, tbb.getDocUploadName());
					}
				}
				Collection<SchoolZoneBean> list = SchoolZoneService.getSchoolZoneBeans();
				request.setAttribute("regions", list);
				Map<Integer, String> statuslist = new HashMap<Integer, String>();

				for (TenderStatus t : TenderStatus.ALL) {
					statuslist.put(t.getValue(), t.getDescription());
				}

				request.setAttribute("statuslist", statuslist);
				request.setAttribute("tender", TendersManager.getTenderById(form.getInt("id")));
				path = "view_tender_details.jsp";
				request.setAttribute("msg", "Tender has been updated");
			}
			else {
				Collection<SchoolZoneBean> list = SchoolZoneService.getSchoolZoneBeans();
				request.setAttribute("regions", list);
				Map<Integer, String> statuslist = new HashMap<Integer, String>();
				for (TenderStatus t : TenderStatus.ALL) {
					statuslist.put(t.getValue(), t.getDescription());
				}
				request.setAttribute("statuslist", statuslist);
				if (!validate_form()) {
					request.setAttribute("msg", validator.getErrorString());
				}
				request.setAttribute("tender", TendersManager.getTenderById(form.getInt("id")));
				path = "view_tender_details.jsp";
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			try {
				request.setAttribute("tender", TendersManager.getTenderById(form.getInt("id")));
			}
			catch (NullPointerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (TenderException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			path = "view_tender_details.jsp";

		}
		return path;
	}
}
