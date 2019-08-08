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
public class ViewApprovedListRequestHandler extends RequestHandlerImpl {
	public ViewApprovedListRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		ArrayList<EECDTeacherAreaBean> list = new ArrayList<EECDTeacherAreaBean>();
		
		list = EECDTeacherAreaManager.getEECDTeacherApprovedListById(Integer.parseInt(request.getParameter("aid")));
		EECDAreaBean abean = EECDAreaManager.getEECDAreaById(Integer.parseInt(request.getParameter("aid")));
		String adescription = abean.getAreaDescription();
		request.setAttribute("areas", list);
		request.setAttribute("areadescription", adescription);
		request.setAttribute("listid", Integer.parseInt(request.getParameter("aid")));
		
		//pass back the number of shortlisted people
		ArrayList<EECDTeacherAreaBean> slist = new ArrayList<EECDTeacherAreaBean>();
		
		EECDShortlistBean sbean =  EECDShortlistManager.getShortlistByAreaId(Integer.parseInt(request.getParameter("aid")), Utils.getCurrentSchoolYear());
		if(sbean.getId() > 0) {
			slist = EECDTeacherAreaManager.getEECDTAShortListByIdNew(Integer.parseInt(request.getParameter("aid")),sbean.getId());
			if(sbean.getShortlistCompleted()) {
				request.setAttribute("iscompleted", true);
			}else {
				request.setAttribute("iscompleted", false);
			}
			
		}else {
			request.setAttribute("iscompleted", false);
		}
		request.setAttribute("slisted", slist.size());
			
		return "view_approved_list.jsp";
	}

}
