package com.esdnl.webupdatesystem.programs.handler;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.programs.bean.ProgramsFileBean;
import com.esdnl.webupdatesystem.programs.dao.ProgramsFileManager;
import com.esdnl.webupdatesystem.programs.dao.ProgramsManager;

public class DeleteProgramRequestHandler extends RequestHandlerImpl {
	public DeleteProgramRequestHandler() {
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
			Integer programid =Integer.parseInt(form.get("pid"));
			//get list of files to delete from server directory
			String filelocation="/../ROOT/includes/files/programs/doc/";
			//get list of files
			List<ProgramsFileBean> list = ProgramsFileManager.getProgramsFiles(programid);
			
			for(ProgramsFileBean pfb : list)
			{
				delete_file(filelocation, pfb.getPfDoc());
			}

			ProgramsManager.deleteProgram(programid);
			request.setAttribute("programs", ProgramsManager.getPrograms());
			request.setAttribute("msg", "Program has been deleted");
			path = "view_programs.jsp";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "view_programs.jsp";
		}
		return path;
	}
}
