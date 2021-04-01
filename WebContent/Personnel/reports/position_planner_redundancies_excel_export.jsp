<%@ page import="com.awsd.security.*, com.esdnl.personnel.jobs.reports.*, org.apache.poi.xssf.usermodel.XSSFWorkbook, java.io.*" %><%

	try{
		User usr = (User) pageContext.getAttribute("usr", PageContext.SESSION_SCOPE);
		
		if(!(usr.checkRole("ADMINISTRATOR") || usr.checkRole("AD HR") || usr.checkRole("SEO - PERSONNEL"))) {
			// no access
			
			return;
		}
		
		String sy = request.getParameter("sy");
	
		ExcelExporter cdee = new PositionPlannerRedundanciesExcelReport(sy); 
		 
		XSSFWorkbook wb = cdee.export();
		
		if(wb != null) {
			System.out.println("GOT THE REPORT!");
			
			response.setContentType("application/vnd.openxml"); 
			
			response.setHeader("Expires:", "0"); // eliminates browser caching
			response.setHeader("Content-Disposition","attachment; filename=\"" + cdee.getFilename() + "\"");  
			wb.write(response.getOutputStream());
			
			return;
		}
		else {
			// no access
			
			return;
		}
	}
	catch(IOException e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
%>