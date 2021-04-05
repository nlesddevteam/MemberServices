<%@ page import="com.awsd.security.*, com.esdnl.personnel.jobs.reports.*, org.apache.poi.xssf.usermodel.XSSFWorkbook, java.io.*" %><%

	try{
		User usr = (User) pageContext.getAttribute("usr", PageContext.SESSION_SCOPE);
		
		if(!(usr.checkRole("ADMINISTRATOR") || usr.checkRole("AD HR") || usr.checkRole("SEO - PERSONNEL"))) {
			// no access
			
			return;
		}
		
		String sy = request.getParameter("sy");
	
		ExcelExporter ee = new PositionPlannerStaffingExcelReport(sy); 
		 
		XSSFWorkbook wb = ee.export();
		
		if(wb != null) {
			System.out.println(usr.getPersonnel().getFullNameReverse() + " downloaded the Staffing Update Report!");
			
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); 
			
			response.setHeader("Expires", "0"); // eliminates browser caching
			response.setHeader("Content-Disposition","attachment; filename=\"" + ee.getFilename() + "\"");  
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