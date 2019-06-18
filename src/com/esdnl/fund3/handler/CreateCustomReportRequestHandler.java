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

public class CreateCustomReportRequestHandler extends RequestHandlerImpl {
	public CreateCustomReportRequestHandler() {

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
				ArrayList<DropdownItemBean> regitems = DropdownManager.getDropdownItems(3);
				request.setAttribute("regions", regitems);
				//funding partners=5
				ArrayList<DropdownItemBean> funditems = DropdownManager.getDropdownItems(5);
				request.setAttribute("funditems", funditems);
				//category = 6
				ArrayList<DropdownItemBean> catitems = DropdownManager.getDropdownItems(6);
				request.setAttribute("catitems", catitems);
				//positions = 2
				ArrayList<DropdownItemBean> positems = DropdownManager.getDropdownItems(2);
				request.setAttribute("positems", positems);

				path = "create_custom_report.jsp";
			
				
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			try {
				//project name=1
				ArrayList<DropdownItemBean> items = DropdownManager.getDropdownItems(1);
				request.setAttribute("projects", items);

				
			} catch (Fund3Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			path = "create_custom_report.jsp";
		}
		return path;
	}


}
