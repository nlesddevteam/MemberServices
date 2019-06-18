package com.esdnl.payadvice.worker;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import com.awsd.mail.bean.EmailBean;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayGroupBean;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayStubBean;
import com.esdnl.payadvice.dao.NLESDPayAdviceEmployeeSecurityManager;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayGroupManager;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayStubManager;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayrollProcessManager;
import com.esdnl.velocity.VelocityUtils;

public class NLESDLoadPayAdvicePayStubEmailWorker extends Thread {

	private Integer payGroupId;
	private String username;
	private String email;
	private String serverpath;
	public static final String DOCUMENT_BASEPATH = "WEB-INF/uploads/payadvice/output/";

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
					email.setSubject("Pay Stub Email  process started");
					email.setBody("Pay Stub Email process started at "
							+ new SimpleDateFormat("dd/MM/yyyy hh:mm:a").format(Calendar.getInstance().getTime()));
					email.send();
				}
				//get pay period info
				NLESDPayAdvicePayGroupBean pgbean = NLESDPayAdvicePayGroupManager.getNLESDPayAdvicePayGroupBean(this.payGroupId);
				String foldername = pgbean.getPayBgDt().replace("/", "") + pgbean.getPayEndDt().replace("/", "");
				String dirname = this.serverpath + NLESDLoadPayAdvicePayStubEmailWorker.DOCUMENT_BASEPATH + foldername;
				//update process record
				NLESDPayAdvicePayrollProcessManager.updateNLESDPayAdvicePayrollProcessEmailStarted(this.payGroupId, username);
				System.out.println("Inside worker");
				//now we select the employees
				ArrayList<NLESDPayAdvicePayStubBean> list = NLESDPayAdvicePayStubManager.getNLESDPayAdvicePayStubList(this.payGroupId);
				for (NLESDPayAdvicePayStubBean bean : list) {
					if (bean != null) {
						String empemail = NLESDPayAdviceEmployeeSecurityManager.getEmployeeEmail(bean.getPayrollId());
						if (empemail.length() > 0) {
							EmailBean ebean = new EmailBean();
							ebean.setSubject("Pay Advice for Deposit on " + pgbean.getCheckDt());
							ebean.setTo(empemail);
							ebean.setBody(VelocityUtils.mergeTemplateIntoString("payadvice/pay_advice_email.vm", null));
							File[] f = new File[2];
							//add paystub
							if (bean.getFileName() != null) {
								f[0] = new File(dirname + "/" + bean.getFileName());
	
							}
	
							ebean.setAttachments(f);
							ebean.send();
						}
					}
	
				}
				//update process record
				NLESDPayAdvicePayrollProcessManager.updateNLESDPayAdvicePayrollProcessEmailFinished(this.getPayGroupId());
				//update paystubs as emailed
				NLESDPayAdvicePayrollProcessManager.updateNLESDPayAdvicePayrollProcessEmailed(this.getPayGroupId());
				//email.setTo(PersonnelDB.getPersonnelByPermission("PERSONNEL-SUBSTITUTES-RELOAD-TABLES"));
				for (Personnel p : sendTo) {
					email.setTo(p.getEmailAddress());
					email.setSubject("Pay Stub Email process completed");
					email.setBody("Pay Stub Email process finished at "
							+ new SimpleDateFormat("dd/MM/yyyy hh:mm:a").format(Calendar.getInstance().getTime()));
					email.send();
				}
		}
		catch (Exception e) {
			try {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					EmailBean email = new EmailBean();
					email.setTo(PersonnelDB.getPersonnelByRole("ADMINISTRATOR"));
					email.setSubject("Pay Stub Email process ERROR Notification");
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
