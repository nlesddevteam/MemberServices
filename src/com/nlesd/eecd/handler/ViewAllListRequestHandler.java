package com.nlesd.eecd.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.common.Utils;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.eecd.bean.EECDAreaBean;
import com.nlesd.eecd.bean.EECDShortlistBean;
import com.nlesd.eecd.bean.EECDTeacherAreaBean;
import com.nlesd.eecd.dao.EECDAreaManager;
import com.nlesd.eecd.dao.EECDShortlistManager;
import com.nlesd.eecd.dao.EECDTeacherAreaManager;
public class ViewAllListRequestHandler extends RequestHandlerImpl {
	public ViewAllListRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		ArrayList<EECDTeacherAreaBean> list = new ArrayList<EECDTeacherAreaBean>();
		
		list = EECDTeacherAreaManager.getEECDTAListStatusByIdSchoolYear(Integer.parseInt(request.getParameter("aid")),Utils.getCurrentSchoolYear());
		EECDAreaBean abean = EECDAreaManager.getEECDAreaById(Integer.parseInt(request.getParameter("aid")));
		request.setAttribute("areas", list);
		request.setAttribute("areadescription", abean.getAreaDescription());
		request.setAttribute("listid", abean.getId());
		request.setAttribute("iscompleted", abean.getShortlistCompleted());
		//pass back the number of shortlisted people
		ArrayList<EECDTeacherAreaBean> slist = new ArrayList<EECDTeacherAreaBean>();
		
		EECDShortlistBean sbean =  EECDShortlistManager.getShortlistByAreaId(Integer.parseInt(request.getParameter("aid")), Utils.getCurrentSchoolYear());
		if(sbean.getId() > 0) {
			slist = EECDTeacherAreaManager.getEECDTAShortListByIdNew(Integer.parseInt(request.getParameter("aid")),sbean.getId());
		}
		request.setAttribute("slisted", slist.size());
			
		return "view_all_list.jsp";
	}

}
