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

public class AddProjectDocumentsRequestHandler extends RequestHandlerImpl {
	public AddProjectDocumentsRequestHandler() {

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
				//regions=3
				ArrayList<DropdownItemBean> regitems = DropdownManager.getDropdownItems(3);
				request.setAttribute("regions", regitems);
				path = "add_project_documents.jsp";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			try {
				//project name taken from the projects table
				TreeMap<Integer,String> items = ProjectManager.getAllProjects();
				request.setAttribute("projects", items);
				//regions=3
				ArrayList<DropdownItemBean> regitems = DropdownManager.getDropdownItems(3);
				request.setAttribute("regions", regitems);
				path = "add_project_documents.jsp";
				
			} catch (Fund3Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			path = "add_project_documents.jsp";
		}
		return path;
	}


}