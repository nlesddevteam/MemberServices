package com.esdnl.personnel.jobs.handler.ajax;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
public class GetRTHUnionPositionsAjaxRequestHandler extends RequestHandlerImpl {
	public GetRTHUnionPositionsAjaxRequestHandler() {
		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("unioncode")
			});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

			int unioncode = Integer.parseInt(request.getParameter("unioncode"));
			
			String xml;
			try {
				xml = StringUtils.encodeXML(RequestToHireManager.getUnionPositions(unioncode));
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			} catch (JobOpportunityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		return null;
	}

}