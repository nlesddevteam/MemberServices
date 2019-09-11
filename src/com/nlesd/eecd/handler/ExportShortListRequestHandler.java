package com.nlesd.eecd.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.common.Utils;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.eecd.bean.EECDExportItemBean;
import com.nlesd.eecd.dao.EECDShortlistManager;
public class ExportShortListRequestHandler extends RequestHandlerImpl {
	public ExportShortListRequestHandler() {
		this.requiredPermissions = new String[] {
				"EECD-VIEW-ADMIN"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("aid")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		ArrayList<EECDExportItemBean> list = new ArrayList<EECDExportItemBean>();
		//we need to pass a short version of the school year to use with sds data
		//regular value is YYYY - YYYYY we need YYYY-YY
		String parts[] = Utils.getCurrentSchoolYear().split("-");
		StringBuilder sb = new StringBuilder();
		sb.append(parts[0]);
		sb.append("-");
		sb.append(parts[1].substring(2,4));
		list = EECDShortlistManager.getExportListByArea(Integer.parseInt(request.getParameter("aid")),Utils.getCurrentSchoolYear(),sb.toString());
		request.setAttribute("areas", list);
			
		return "export_shortlists.jsp";
	}

}
