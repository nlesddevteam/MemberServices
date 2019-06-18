package com.nlesd.eecd.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.eecd.bean.EECDTeacherAreaBean;
import com.nlesd.eecd.dao.EECDTeacherAreaManager;
public class ExportShortlistMultiRequestHandler extends RequestHandlerImpl {
	public ExportShortlistMultiRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		ArrayList<EECDTeacherAreaBean> list = new ArrayList<EECDTeacherAreaBean>();
		
		//list = EECDTeacherAreaManager.getEECDTAShortListById(Integer.parseInt(request.getParameter("idlist")));
		list = EECDTeacherAreaManager.getEECDTAShortListByIds(request.getParameter("idlist"));
		request.setAttribute("areas", list);
			
		return "export_shortlists.jsp";
	}

}
