package com.nlesd.eecd.handler;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.eecd.bean.EECDTAApprovalBean;
import com.nlesd.eecd.dao.EECDTAApprovalManager;

public class AdminViewApprovalsNoSchoolRequestHandler extends RequestHandlerImpl {
	public AdminViewApprovalsNoSchoolRequestHandler() {
		this.requiredPermissions = new String[] {
				"EECD-VIEW-APPROVALS-NO-SCHOOL"
		};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		ArrayList<EECDTAApprovalBean> list = new ArrayList<EECDTAApprovalBean>();
		list = EECDTAApprovalManager.getApprovalsNoSchool();
		request.setAttribute("areas", list);
		
			
		return "admin_view_approvals_no_school.jsp";
	}

}
