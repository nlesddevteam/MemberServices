package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorDateHistoryBean;
import com.nlesd.bcs.dao.BussingContractorDateHistoryManager;
public class AdminSubmitDateAuditRequestHandler extends RequestHandlerImpl {
	public AdminSubmitDateAuditRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		int selecttype = Integer.parseInt(request.getParameter("selecttype").toString());
		int contractor = Integer.parseInt(request.getParameter("selectcon").toString());
		int selectfield = Integer.parseInt(request.getParameter("selectfield").toString());
		int selectfieldv = Integer.parseInt(request.getParameter("selectfieldv").toString());
		String sdate = null;
		String edate= null;
		sdate = request.getParameter("sdate").toString();
		edate = request.getParameter("edate").toString();
		
		ArrayList<BussingContractorDateHistoryBean> list = new ArrayList<BussingContractorDateHistoryBean>();
		list = BussingContractorDateHistoryManager.getDateAuditLogEntries(selecttype, contractor, selectfield, selectfieldv, sdate, edate);
		
		request.setAttribute("auditentries", list);
		request.setAttribute("datetype", selecttype);
		path = "admin_view_date_audit_results.jsp";
		return path;
	}

}
