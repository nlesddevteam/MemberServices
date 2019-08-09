package com.esdnl.webupdatesystem.tenders.handler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.tenders.constants.TenderStatus;
import com.esdnl.webupdatesystem.tenders.dao.TendersFileManager;
import com.esdnl.webupdatesystem.tenders.dao.TendersManager;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class DeleteTendersOtherFileRequestHandler extends RequestHandlerImpl {

	public DeleteTendersOtherFileRequestHandler() {

		this.requiredPermissions = new String[] {
				"TENDER-ADMIN", "TENDER-EDIT"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);
		try {
			String fid = form.get("fid");
			Integer id = Integer.parseInt(form.get("id"));
			Integer tid = Integer.parseInt(form.get("tid"));
			//get list of files to delete from server directory
			String filelocation = "/../../nlesdweb/WebContent/includes/files/tenders/doc/";
			delete_file(filelocation, fid);
			TendersFileManager.deleteTendersFile(id);
			request.setAttribute("tender", TendersManager.getTenderById(tid));
			Collection<SchoolZoneBean> list = SchoolZoneService.getSchoolZoneBeans();
			request.setAttribute("regions", list);
			Map<Integer, String> statuslist = new HashMap<Integer, String>();
			for (TenderStatus t : TenderStatus.ALL) {
				statuslist.put(t.getValue(), t.getDescription());
			}
			request.setAttribute("statuslist", statuslist);
			request.setAttribute("msg", "Other Tenders File has been deleted");
			path = "view_tender_details.jsp";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "view_tender_details.jsp";
		}
		return path;
	}
}
