package com.esdnl.personnel.jobs.handler;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class ViewSubListApplicantsFilterRequestHandler extends RequestHandlerImpl
{
	public ViewSubListApplicantsFilterRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("list_id", "List id required to view applicant")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		SubListBean list = null;
		super.handleRequest(request, response);
		String path;
		if (validate_form()) {
			try
			{
				String list_id = request.getParameter("list_id");
				list = SubListManager.getSubListBean(Integer.parseInt(list_id));
				if(list.getRegion() != null){
					request.setAttribute("rname", list.getRegion().getName());
					if(list.getRegion().getZone() != null){
						request.setAttribute("zname", list.getRegion().getZone().getZoneName());
					}else{
						request.setAttribute("zname", "");
					}
				}else{
					request.setAttribute("zname", "");
					request.setAttribute("rname", "");
				}
				request.setAttribute("list", list);
				path = "admin_filter_sublist_applicants.jsp";
			}
			catch(JobOpportunityException e)
			{
				e.printStackTrace();
				request.setAttribute("msg", "Could not retrieve Job applicants.");
				path = "admin_view_sub_lists.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "admin_index.jsp";
		}
		return path;
	}
}
