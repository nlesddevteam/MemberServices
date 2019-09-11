package com.nlesd.eecd.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.common.Utils;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.eecd.bean.EECDExportItemBean;
import com.nlesd.eecd.dao.EECDShortlistManager;
public class ExportShortlistMultiRequestHandler extends RequestHandlerImpl {
	public ExportShortlistMultiRequestHandler() {
		this.requiredPermissions = new String[] {
				"EECD-VIEW-ADMIN"
		};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		ArrayList<EECDExportItemBean> list = new ArrayList<EECDExportItemBean>();
		String parts[] = Utils.getCurrentSchoolYear().split("-");
		StringBuilder sb = new StringBuilder();
		sb.append(parts[0]);
		sb.append("-");
		sb.append(parts[1].substring(2,4));
		list = EECDShortlistManager.getExportListByAreaMulti(request.getParameter("idlist"), Utils.getCurrentSchoolYear(), sb.toString());
		request.setAttribute("areas", list);
			
		return "export_shortlists.jsp";
	}

}
