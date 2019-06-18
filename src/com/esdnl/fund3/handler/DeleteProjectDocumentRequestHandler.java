package com.esdnl.fund3.handler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.AuditLogBean;
import com.esdnl.fund3.bean.DropdownItemBean;
import com.esdnl.fund3.bean.ProjectBean;
import com.esdnl.fund3.dao.AuditLogManager;
import com.esdnl.fund3.dao.DropdownManager;
import com.esdnl.fund3.dao.ProjectDocumentManager;
import com.esdnl.fund3.dao.ProjectManager;
import com.esdnl.fund3.dao.ProjectRegionManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class DeleteProjectDocumentRequestHandler extends RequestHandlerImpl {
	public DeleteProjectDocumentRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		try {
			Integer id =Integer.parseInt(form.get("id"));
			Integer pid =Integer.parseInt(form.get("pid"));
			String fid=form.get("fid");
			String fname=form.get("fname");
			//get list of files to delete from server directory
			String filelocation="/../MemberServices/Fund3/documents/";
			//get file name to use with audit log
			delete_file(filelocation, fid);
			ProjectDocumentManager.deleteProjectDocument(id);
			ProjectBean origpb = ProjectManager.getProjectById(pid);
			//now we update the audit log
			AuditLogBean albn = new AuditLogBean();
			albn.setUserName(usr.getPersonnel().getFullNameReverse());
			albn.setLogEntry("Project File Deleted: [" + fname + "]");
			albn.setProjectId(pid);
			AuditLogManager.addNewAuditLog(albn);
			
			//add the project bean to the request
			request.setAttribute("project", origpb);
			//funding partners=5
			ArrayList<DropdownItemBean> items = DropdownManager.getDropdownItems(5);
			request.setAttribute("items", items);
			//category = 6
			ArrayList<DropdownItemBean> catitems = DropdownManager.getDropdownItems(6);
			request.setAttribute("catitems", catitems);
			//positions = 2
			ArrayList<DropdownItemBean> positems = DropdownManager.getDropdownItems(2);
			request.setAttribute("positems", positems);
			//report frequency = 7
			ArrayList<DropdownItemBean> freqitems = DropdownManager.getDropdownItems(7);
			request.setAttribute("freqitems", freqitems);
			//fiscal year = 4
			ArrayList<DropdownItemBean> fiscalitems = DropdownManager.getDropdownItems(4);
			request.setAttribute("fiscalitems", fiscalitems);
			//add the region budget amount
			TreeMap<String,Double> tm = ProjectRegionManager.getProjectRegionByIdEdit(pid);
			request.setAttribute("regionbud", tm);
			ArrayList<DropdownItemBean> regitems = DropdownManager.getDropdownItems(3);
			request.setAttribute("regions", regitems);
			request.setAttribute("msg", "PROJECT DOCUMENT HAS BEEN DELETTED");
			path = "edit_project.jsp";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "edit_project.jsp";
		}
		return path;
	}

}
