package com.nlesd.eecd.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.eecd.bean.EECDAreaBean;
import com.nlesd.eecd.dao.EECDAreaManager;
public class GetTeacherSelectedAreasRequestHandler extends RequestHandlerImpl {
	public GetTeacherSelectedAreasRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		//now we get their selections if any
		ArrayList<EECDAreaBean> list = new ArrayList<EECDAreaBean>();
		list = EECDAreaManager.getTeacherSelectedAreasByPID(usr.getPersonnel().getPersonnelID());

		request.setAttribute("areas",list);
		
			
		return "view_teacher_selected.jsp";
	}

}
