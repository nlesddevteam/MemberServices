package com.esdnl.payadvice.handler;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.ControllerServlet;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayrollProcessBean;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayrollProcessManager;
import com.esdnl.payadvice.worker.NLESDPayAdvicePrintMissingPayStubWorker;
import com.esdnl.servlet.RequestHandlerImpl;

public class StartProcessingManualPrintRequestHandler extends RequestHandlerImpl {
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
		super.handleRequest(request, response);
		String path=null;
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
	    	NLESDPayAdvicePrintMissingPayStubWorker worker = new NLESDPayAdvicePrintMissingPayStubWorker();
	    	//retrieve files, could be null
	    	Integer paygroupid = form.getInt("payid");
	    	worker.setPayGroupId(paygroupid);
	    	worker.setUsername(usr.getUsername());
	    	worker.setEmail(usr.getPersonnel().getEmailAddress());
	    	worker.setServerpath(ControllerServlet.CONTEXT_BASE_PATH);
	    	Date d= new Date();
			//update process record
			NLESDPayAdvicePayrollProcessManager.updateNLESDPayAdviceManualFilename(paygroupid, "CREATING");
	    	worker.start();
	    	String message="Pay Stub Creation  process started at " + d + ". You will receive an email notification when the process has finished or you can check the progress on the Pay Period Details page";
	    	request.setAttribute("msg", message);
			NLESDPayAdvicePayrollProcessBean ppbean= NLESDPayAdvicePayrollProcessManager.getNLESDPayAdvicePayrollProcessBean(paygroupid);
			request.setAttribute("ppbean", ppbean);
			request.setAttribute("pgbean", ppbean.getPayGroupBean());
			request.setAttribute("ijbean", ppbean.getImportJobBean());
	    	path="view_pay_period_details.jsp";
			
	    }
	    catch(Exception e)
	    {
		      e.printStackTrace();
		      request.setAttribute("msg", e.getMessage());
		      path="view_pay_period_details.jsp";
	    }
	    
	    return path;
  }
}
