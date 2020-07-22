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
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorManager;
public class AdminSubmitAuditRequestHandler extends RequestHandlerImpl {
	public AdminSubmitAuditRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("selectaudit"),
				new RequiredFormElement("selectcon")
			});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		if (validate_form()) {
			int audittype = Integer.parseInt(request.getParameter("selectaudit").toString());
			int contractor = Integer.parseInt(request.getParameter("selectcon").toString());
			BussingContractorBean bcbean = BussingContractorManager.getBussingContractorById(contractor);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String rtype="";
			
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
				rtype="Contractor Login";
			}else if(audittype ==2){
				list = AuditTrailManager.getAuditEntriesEmpVeh(contractor, sdate, edate, EntryTypeConstant.CONTRACTORCOMPANYUPDATED.getValue(),EntryTypeConstant.CONTRACTORDOCDELETED.getValue());
				rtype="Contractor Information Changes(Employees/Vehicles)";
			}
			
			request.setAttribute("auditentries", list);
			request.setAttribute("rtype", rtype);
			request.setAttribute("bcname", bcbean.getContractorName());
			request.setAttribute("sdate", new SimpleDateFormat("MM/dd/yyyy").format(sdate));
			request.setAttribute("edate", new SimpleDateFormat("MM/dd/yyyy").format(edate));
			path = "admin_view_audit_results.jsp";
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";
		}
		

		return path;
	}

}
