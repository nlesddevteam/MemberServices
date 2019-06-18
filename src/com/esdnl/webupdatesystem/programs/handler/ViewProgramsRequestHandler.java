package com.esdnl.webupdatesystem.programs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.programs.bean.ProgramsException;
import com.esdnl.webupdatesystem.programs.dao.ProgramsManager;

public class ViewProgramsRequestHandler  extends RequestHandlerImpl {
	public ViewProgramsRequestHandler() {
		this.requiredRoles = new String[] {
				"ADMINISTRATOR","WEB DESIGNER","WEBANNOUNCMENTS-POST"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
	    try {
			request.setAttribute("programs", ProgramsManager.getPrograms());
	    	//request.setAttribute("programs", ProgramsManager.getProgramsByRegion(5));
		} catch (ProgramsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_programs.jsp";
	    return path;
	}
}

