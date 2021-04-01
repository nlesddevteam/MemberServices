<%@ page import="com.esdnl.personnel.jobs.reports.*, org.apache.poi.xssf.usermodel.XSSFWorkbook, java.io.*" %><%

	try{
		String sy = request.getParameter("sy");
	
		ExcelExporter cdee = new PositionPlannerRedundanciesExcelReport(sy); 
		 
		XSSFWorkbook wb = cdee.export();
		
		if(wb != null) {
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
	}
%>