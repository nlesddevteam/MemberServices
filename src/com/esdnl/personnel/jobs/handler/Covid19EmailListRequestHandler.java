package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.esdnl.personnel.jobs.bean.Covid19EmailListBean;
import com.esdnl.personnel.jobs.dao.Covid19EmailListManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.velocity.VelocityUtils;

public class Covid19EmailListRequestHandler extends RequestHandlerImpl {
	public Covid19EmailListRequestHandler() {
		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW-COVID19-EMAIL"
		};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		ArrayList<Covid19EmailListBean> list = Covid19EmailListManager.getCovid19EmailList();
		Date startdate= new Date();
		StringBuilder sb= new StringBuilder();
		ArrayList<String> verified =  new ArrayList<>();
		TreeMap<String,Covid19EmailListBean> emaillist =  new TreeMap<>();
		for(Covid19EmailListBean cb: list) {
			//first we check the verified, if there then do nothing
			if(!(verified.contains(cb.getEmailAddress()))) {
				//not in list so now we check
				if(cb.getDateVerified() != null) {
					//verfied
					verified.add(cb.getEmailAddress());
				}else {
					//check to see if doc is there and not verfied
					if(cb.getDocumentId() > 0) {
						//not verified, add it to the verified list
						verified.add(cb.getEmailAddress());
					}else {
						//no doc, check to see if it exists so no dup emails
						if(!(emaillist.containsKey(cb.getEmailAddress()))) {
							//not in list then add it
							emaillist.put(cb.getEmailAddress(),cb);
						}
						
					}
					
				}
			}
			
		}
		
		//send email to app
		EmailBean ebean = new EmailBean();
		ebean.setTo("rodneybatten@nlesd.ca");
		ebean.setFrom("ms@nlesd.ca");
		ebean.setSubject("COVID19 Vaccination Document Reminder");
		HashMap<String, Object> model = new HashMap<String, Object>();
		ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/covid19_warning_email.vm", model));
		try {
			ebean.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long test =0l;
		//now we create the data string
		for (Map.Entry<String,Covid19EmailListBean> entry : emaillist.entrySet()) {
	        sb.append(entry.getValue().toString());
	        Covid19EmailListManager.addCovid19WarningEmailLog(entry.getValue().getEmailAddress(), -999);
	        test++;
		}
		Date enddate = new Date();
		//now we save the warning email table
		int id = Covid19EmailListManager.addCovid19WarningEmail(startdate, enddate, usr.getLotusUserFullNameReverse(), test);
		
		//response.setContentType("application/csv");
		//response.setHeader("content-disposition","filename=emaillist.csv");
		//PrintWriter out = response.getWriter();
		//out.print(sb.toString());
		//out.flush();
		//out.close();

		//path = null;
		request.setAttribute("cid", id);
		request.setAttribute("emailcount", test);
		return "admin_send_covid19_warning.jsp";
		
	}

}
