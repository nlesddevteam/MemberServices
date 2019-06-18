package com.esdnl.webupdatesystem.programs.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.programs.constants.ProgramsLevel;
import com.esdnl.webupdatesystem.programs.constants.ProgramsRegion;
import com.esdnl.webupdatesystem.programs.dao.ProgramsFileManager;
import com.esdnl.webupdatesystem.programs.dao.ProgramsManager;

public class DeleteProgramOtherFileRequestHandler extends RequestHandlerImpl {
	public DeleteProgramOtherFileRequestHandler() {
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
			String fid=form.get("fid");
			Integer id =Integer.parseInt(form.get("id"));
			Integer programid =Integer.parseInt(form.get("pid"));
			//get list of files to delete from server directory
			String filelocation="/../ROOT/includes/files/programs/doc/";
			delete_file(filelocation, fid);
			ProgramsFileManager.deleteProgramFile(id);
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
			request.setAttribute("program",ProgramsManager.getProgramById(programid));
			
			request.setAttribute("otherfiles", ProgramsFileManager.getProgramsFiles(programid));
			request.setAttribute("msg", "File has been deleted");
			path = "view_program_details.jsp";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "view_program_details.jsp";
		}
		return path;
	}
}
