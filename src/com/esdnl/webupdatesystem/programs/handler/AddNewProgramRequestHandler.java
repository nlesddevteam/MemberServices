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

public class AddNewProgramRequestHandler extends RequestHandlerImpl {
	public AddNewProgramRequestHandler() {
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
			if(form.get("op") == null)
			{
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
				path = "add_new_program.jsp";
				
			}
			else{
				
				if (validate_form()) {
					ProgramsBean pb = new ProgramsBean();
					pb.setDescriptorTitle(form.get("descriptor_title").toString());
					pb.setpRegion(ProgramsRegion.get(Integer.parseInt(form.get("program_region").toString())));
					pb.setpLevel(ProgramsLevel.get(Integer.parseInt(form.get("program_level").toString())));
					pb.setAddedBy(usr.getPersonnel().getFullNameReverse());
					pb.setProgramStatus(Integer.parseInt(form.get("program_status").toString()));
					int id = ProgramsManager.addProgram(pb);
					
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
					path = "add_new_program.jsp";
					request.setAttribute("msg", "Program has been added");
				}
				path = "add_new_program.jsp";
			}
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
			
			path = "add_new_program.jsp";
			
		}
		return path;
	}
}