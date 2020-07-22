package com.nlesd.bcs.handler;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorDateHistoryBean;
import com.nlesd.bcs.constants.DateFieldConstant;
import com.nlesd.bcs.dao.BussingContractorDateHistoryManager;
import com.nlesd.bcs.dao.BussingContractorManager;
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
			BussingContractorBean bcbean = BussingContractorManager.getBussingContractorById(contractor);
			String sdate = null;
			String edate= null;
			sdate = request.getParameter("sdate").toString();
			edate = request.getParameter("edate").toString();
			
			ArrayList<BussingContractorDateHistoryBean> list = new ArrayList<BussingContractorDateHistoryBean>();
			list = BussingContractorDateHistoryManager.getDateAuditLogEntries(selecttype, contractor, selectfield, selectfieldv, sdate, edate);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date sdate1 = null;
			Date edate1= null;
			try {
				sdate1 = format.parse(request.getParameter("sdate").toString());
				edate1 = format.parse(request.getParameter("edate").toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("auditentries", list);
			request.setAttribute("datetype", selecttype);
			request.setAttribute("bcname", bcbean.getContractorName());
			request.setAttribute("sdate", new SimpleDateFormat("MM/dd/yyyy").format(sdate1));
			request.setAttribute("edate", new SimpleDateFormat("MM/dd/yyyy").format(edate1));
			if(selecttype == 1) {
				request.setAttribute("dstring", DateFieldConstant.get(selectfield));
			}else {
				request.setAttribute("dstring", DateFieldConstant.get(selectfieldv));
			}
			
			path = "admin_view_date_audit_results.jsp";
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";
		}

		return path;
	}

}
