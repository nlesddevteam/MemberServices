package com.esdnl.fund3.handler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.fund3.bean.DropdownItemBean;
import com.esdnl.fund3.bean.ProjectBean;
import com.esdnl.fund3.dao.DropdownManager;
import com.esdnl.fund3.dao.ProjectManager;
import com.esdnl.fund3.dao.ProjectRegionManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class PrintProjectRequestHandler extends RequestHandlerImpl {
	public PrintProjectRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		try {
			int projectid = form.getInt("pid");
			ProjectBean origpb = ProjectManager.getProjectById(projectid);
			//add the project bean to the request
				request.setAttribute("project", origpb);
				
				//add the region budget amount
				TreeMap<String,Double> tm = ProjectRegionManager.getProjectRegionByIdEdit(projectid);
				request.setAttribute("regionbud", tm);
				//fiscal year = 4
				ArrayList<DropdownItemBean> fiscalitems = DropdownManager.getDropdownItems(4);
				request.setAttribute("fiscalitems", fiscalitems);
				//category = 6
				ArrayList<DropdownItemBean> catitems = DropdownManager.getDropdownItems(6);
				request.setAttribute("catitems", catitems);
				//positions = 2
				ArrayList<DropdownItemBean> positems = DropdownManager.getDropdownItems(2);
				request.setAttribute("positems", positems);
				//report frequency = 7
				ArrayList<DropdownItemBean> freqitems = DropdownManager.getDropdownItems(7);
				request.setAttribute("freqitems", freqitems);
				path = "print_project.jsp";
				
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			
			path = "print_project.jsp";
		}
		return path;
	}


}