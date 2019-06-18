package com.esdnl.payadvice.worker;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import com.awsd.mail.bean.EmailBean;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.cete.dynamicpdf.Document;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayGroupBean;
import com.esdnl.payadvice.dao.NLESDPayAdviceCreateManualStubManager;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayGroupManager;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayrollProcessErrorManager;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayrollProcessManager;
public class NLESDPayAdvicePrintMissingPayStubWorker extends Thread {

	private Integer payGroupId;
	private String username;
	private String email;
	public static final String DOCUMENT_BASEPATH = "WEB-INF/uploads/payadvice/output/missing/";
	private String serverpath;
	private StringBuilder sb = new StringBuilder();

	public void run() {

		try {
				EmailBean email = new EmailBean();
				//email.setTo(PersonnelDB.getPersonnelByPermission("PERSONNEL-SUBSTITUTES-RELOAD-TABLES"));
				ArrayList<Personnel> sendTo = null;
				sendTo = new ArrayList<Personnel>();
				sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByPermission("PAY-ADVICE-ADMIN")));
				sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("ADMINISTRATOR")));
				for (Personnel p : sendTo) {
					email.setTo(p.getEmailAddress());
					email.setSubject("Missing Pay Stub Creation  process started");
					email.setBody("Missing Pay Stub Creation process started at "
							+ new SimpleDateFormat("dd/MM/yyyy hh:mm:a").format(Calendar.getInstance().getTime()));
					email.send();
				}
				//get pay period info
				NLESDPayAdvicePayGroupBean pgbean = NLESDPayAdvicePayGroupManager.getNLESDPayAdvicePayGroupBean(this.payGroupId);
	
				String filename = pgbean.getPayBgDt().replace("/", "") + pgbean.getPayEndDt().replace("/", "");
				String dirname = this.serverpath + NLESDPayAdvicePrintMissingPayStubWorker.DOCUMENT_BASEPATH;
				Document doc = new Document();
		        doc.setCreator("viewpaystub");
		        doc.setAuthor("nlesd");
		        doc.setTitle("Paystub");
				ArrayList<String> list = NLESDPayAdvicePayrollProcessErrorManager.getNLESDPayAdvicePayrollProcessManualPrint(this.payGroupId);
				for(String employee : list) {
					NLESDPayAdviceCreateManualStubManager.createStubForEmployee(employee,this.payGroupId, doc);
				}
		        // Outputs the W-9 to the file
				//set file link name
				String filelinkname="missing/" + filename + ".pdf";
				//set file locaton on server
				String filelocation=serverpath + "PayAdvice/missing/";
		    	filename=filelocation + filename + ".pdf";
		        doc.draw(filename);
		        //update process record with filename
		        NLESDPayAdvicePayrollProcessManager.updateNLESDPayAdviceManualFilename(this.payGroupId, filelinkname);
				for (Personnel p : sendTo) {
					email.setTo(p.getEmailAddress());
					email.setSubject("Missing Pay Stub Creation process completed");
					sb.insert(
							0,
							"Missing Pay Stub Creation process finished at "
									+ new SimpleDateFormat("dd/MM/yyyy hh:mm:a").format(Calendar.getInstance().getTime())
									+ System.getProperty("line.separator"));
					email.setBody(sb.toString());
					email.send();
				}
		}
		catch (Exception e) {
			try {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
	
					EmailBean email = new EmailBean();
					email.setTo(PersonnelDB.getPersonnelByRole("ADMINISTRATOR"));
					email.setSubject("Manual Pay Stub Creation  process ERROR Notification");
					email.setBody(sw.toString());
					email.send();

			}
			catch (Exception ex) {}
		}
	}

	public Integer getPayGroupId() {

		return payGroupId;
	}

	public void setPayGroupId(Integer payGroupId) {

		this.payGroupId = payGroupId;
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getServerpath() {

		return serverpath;
	}

	public void setServerpath(String serverpath) {

		this.serverpath = serverpath;
	}

}
