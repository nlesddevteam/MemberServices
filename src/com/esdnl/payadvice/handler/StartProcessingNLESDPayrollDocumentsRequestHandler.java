package com.esdnl.payadvice.handler;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.esdnl.payadvice.dao.NLESDPayAdviceImportJobManager;
import com.esdnl.payadvice.worker.NLESDLoadPayAdviceFilesWorker;
import com.esdnl.servlet.RequestHandlerImpl;
public class StartProcessingNLESDPayrollDocumentsRequestHandler extends RequestHandlerImpl {
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
		super.handleRequest(request, response);
	    String path;
	    HttpSession session = null;
	    User usr = null;
	    session = request.getSession(false);
		if((session != null) && (session.getAttribute("usr") != null)){
			usr = (User) session.getAttribute("usr");
			if(!(usr.getUserPermissions()).containsKey("PAY-ADVICE-ADMIN")){
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else
		{
			throw new SecurityException("User login required.");
		}
	    try
	    {
	    	NLESDLoadPayAdviceFilesWorker worker = new NLESDLoadPayAdviceFilesWorker();
	    	//retrieve files, could be null
	    	String payrollfile = form.get("payroll_file");
	    	String mappingfile = form.get("mapping_file");
	    	String historyfile = form.get("other_file");
	    	String payrollfileid = form.get("payroll_file_id");
	    	String mappingfileid = form.get("mapping_file_id");
	    	String historyfileid = form.get("other_file_id");
	    	//pass them to the worker object
	    	if(payrollfileid == null){
	    		worker.setPayrollfileid(-1);
	    	}else{
	    		worker.setPayrollfileid(Integer.parseInt(payrollfileid));
	    	}
	    	if(mappingfileid == null){
	    		worker.setMappingfileid(-1);
	    	}else{
	    		worker.setMappingfileid(Integer.parseInt(mappingfileid));
	    	}
	    	if(historyfileid == null){
	    		worker.setHistoryfileid(-1);
	    	}else{
	    		worker.setHistoryfileid(Integer.parseInt(historyfileid));
	    	}    	
	    	worker.setPayrollfile(payrollfile);
	    	worker.setMappingfile(mappingfile);
	    	worker.setHistoryfile(historyfile);
	    	worker.setUsername(usr.getUsername());
	    	worker.setEmail(usr.getPersonnel().getEmailAddress());
	    	Date d= new Date();
	    	request.setAttribute("msg", "Payroll Files loading process started at " + d + ". You will receive an email notification when the process has finished or you can check the progress on the Status page");
	    	worker.start();
	    	request.setAttribute("jobs", NLESDPayAdviceImportJobManager.getNLESDPayAdviceImportJobBeans());
	    	path = "view_running_jobs.jsp";
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	      request.setAttribute("msg", e.getMessage());
	      path = "process_payroll_files.jsp";
	    }
	    
	    return path;
	  }
}
