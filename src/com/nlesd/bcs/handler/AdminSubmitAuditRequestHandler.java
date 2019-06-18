package com.nlesd.bcs.handler;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
public class AdminSubmitAuditRequestHandler extends RequestHandlerImpl {
	public AdminSubmitAuditRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		int audittype = Integer.parseInt(request.getParameter("selectaudit").toString());
		int contractor = Integer.parseInt(request.getParameter("selectcon").toString());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
		
		Date sdate = null;
		Date edate= null;
		try {
			sdate = format.parse(request.getParameter("sdate").toString());
			edate = format.parse(request.getParameter("edate").toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<AuditTrailBean> list = new ArrayList<AuditTrailBean>();
		if(audittype == 1){
			list = AuditTrailManager.getAuditEntriesLogins(contractor, sdate, edate, EntryTypeConstant.CONTRACTORLOGIN.getValue());
		}else if(audittype ==2){
			list = AuditTrailManager.getAuditEntriesEmpVeh(contractor, sdate, edate, EntryTypeConstant.CONTRACTORCOMPANYUPDATED.getValue(),EntryTypeConstant.CONTRACTORDOCDELETED.getValue());
		}
		
		request.setAttribute("auditentries", list);
		path = "admin_view_audit_results.jsp";
		return path;
	}

}
