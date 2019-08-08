package com.nlesd.eecd.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.common.Utils;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.eecd.bean.EECDAreaBean;
import com.nlesd.eecd.dao.EECDAreaManager;
public class ExportSelectionRequestHandler extends RequestHandlerImpl {
	public ExportSelectionRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		ArrayList<EECDAreaBean> list = new ArrayList<EECDAreaBean>();
		list = EECDAreaManager.getAllEECDAreasShortlistedNew(Utils.getCurrentSchoolYear());
		request.setAttribute("areas", list);
		
			
		return "export_selection.jsp";
	}

}
