package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.BussingContractorDateHistoryBean;
import com.nlesd.bcs.dao.BussingContractorDateHistoryManager;
public class AdminSubmitDateAuditRequestHandler extends RequestHandlerImpl {
	public AdminSubmitDateAuditRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("selecttype"),
				new RequiredFormElement("selectcon")
			});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		if (validate_form()) {
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
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";
		}

		return path;
	}

}
