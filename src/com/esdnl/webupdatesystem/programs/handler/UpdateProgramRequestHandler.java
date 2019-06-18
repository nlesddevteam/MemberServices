package com.esdnl.webupdatesystem.programs.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.programs.bean.ProgramsBean;
import com.esdnl.webupdatesystem.programs.constants.ProgramsLevel;
import com.esdnl.webupdatesystem.programs.constants.ProgramsRegion;
import com.esdnl.webupdatesystem.programs.dao.ProgramsManager;

public class UpdateProgramRequestHandler extends RequestHandlerImpl {
	public UpdateProgramRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("descriptor_title", "Descriptor Title is required."),
				new RequiredFormElement("program_region", "Region is required."),
				new RequiredFormElement("program_level", "Level is required.")
		});
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
				if (validate_form()) {
					ProgramsBean pb = new ProgramsBean();
					pb.setDescriptorTitle(form.get("descriptor_title").toString());
					pb.setpRegion(ProgramsRegion.get(Integer.parseInt(form.get("program_region").toString())));
					pb.setpLevel(ProgramsLevel.get(Integer.parseInt(form.get("program_level").toString())));
					pb.setAddedBy(usr.getPersonnel().getFullNameReverse());
					pb.setId(form.getInt("id"));
					pb.setProgramStatus(Integer.parseInt(form.get("program_status").toString()));
					ProgramsManager.updatePrograms(pb);
					
					Map<Integer,String> programsregions = new HashMap<Integer,String>();
					for(ProgramsRegion t : ProgramsRegion.ALL)
					{
						programsregions.put(t.getValue(),t.getDescription());
					}
					request.setAttribute("programsregions", programsregions);
					Map<Integer,String> programslevels = new HashMap<Integer,String>();
					for(ProgramsLevel t : ProgramsLevel.ALL)
					{
						programslevels.put(t.getValue(),t.getDescription());
					}
					request.setAttribute("programslevels", programslevels);
					path = "view_program_details.jsp";
					request.setAttribute("msg", "Program has been updated");
					request.setAttribute("program",pb);
				}
				path = "view_program_details.jsp";
			
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			Map<Integer,String> programsregions = new HashMap<Integer,String>();
			for(ProgramsRegion t : ProgramsRegion.ALL)
			{
				programsregions.put(t.getValue(),t.getDescription());
			}
			request.setAttribute("programsregions", programsregions);
			Map<Integer,String> programslevels = new HashMap<Integer,String>();
			for(ProgramsLevel t : ProgramsLevel.ALL)
			{
				programslevels.put(t.getValue(),t.getDescription());
			}
			request.setAttribute("programslevels", programslevels);
			
			path = "view_program_details.jsp";
			
		}
		return path;
	}
}
