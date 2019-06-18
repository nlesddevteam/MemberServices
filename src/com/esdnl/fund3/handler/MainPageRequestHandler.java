package com.esdnl.fund3.handler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.DropdownItemBean;
import com.esdnl.fund3.bean.Fund3Exception;
import com.esdnl.fund3.dao.DropdownManager;
import com.esdnl.fund3.dao.ProjectManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class MainPageRequestHandler extends RequestHandlerImpl {
	public MainPageRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		try {

				//project name taken from the projects table
				TreeMap<Integer,String> items = ProjectManager.getAllProjects();
				request.setAttribute("projects", items);
				//positions=2
				ArrayList<DropdownItemBean> positems = DropdownManager.getDropdownItems(2);
				request.setAttribute("positions", positems);
				//regions=3
				ArrayList<DropdownItemBean> regitems = DropdownManager.getDropdownItems(3);
				request.setAttribute("regions", regitems);
				//positions = 4
				ArrayList<DropdownItemBean> fyitems = DropdownManager.getDropdownItems(4);
				request.setAttribute("fiscalyears", fyitems);
				//positions = 5
				ArrayList<DropdownItemBean> catitems = DropdownManager.getDropdownItems(6);
				request.setAttribute("categories", catitems);
				path = "index.jsp";
			
				
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			try {
				//project name=1
				ArrayList<DropdownItemBean> items = DropdownManager.getDropdownItems(1);
				request.setAttribute("projects", items);
				//positions=2
				ArrayList<DropdownItemBean> positems = DropdownManager.getDropdownItems(2);
				request.setAttribute("positions", positems);
				//regoins=3
				ArrayList<DropdownItemBean> regitems = DropdownManager.getDropdownItems(3);
				request.setAttribute("regions", regitems);
				//positions = 4
				ArrayList<DropdownItemBean> fyitems = DropdownManager.getDropdownItems(4);
				request.setAttribute("fiscalyears", fyitems);
				//positions = 5
				ArrayList<DropdownItemBean> catitems = DropdownManager.getDropdownItems(6);
				request.setAttribute("categories", catitems);
				
			} catch (Fund3Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			path = "index.jsp";
		}
		return path;
	}


}
