package com.esdnl.payadvice.worker;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import org.apache.commons.lang.StringUtils;
import com.awsd.mail.bean.EmailBean;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.esdnl.payadvice.bean.NLESDPayAdviceEmployeeInfoBean;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayGroupBean;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayStubBean;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayStubWorkerBean;
import com.esdnl.payadvice.dao.NLESDPayAdviceCreateStubManager;
import com.esdnl.payadvice.dao.NLESDPayAdviceEmployeeInfoManager;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayGroupManager;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayStubManager;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayStubWorkerManager;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayrollProcessManager;
import com.esdnl.velocity.VelocityUtils;

public class NLESDPayAdvicePayStubProcessWorker extends Thread {

	private Integer payGroupId;
	private String username;
	private String email;
	public static final String DOCUMENT_BASEPATH = "WEB-INF/uploads/payadvice/output/";
	private String serverpath;
	private StringBuilder sb = new StringBuilder();
	private boolean isTest = false;

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
					email.setSubject("Pay Stub Creation  process started");
					email.setBody("Pay Stub Creation process started at "
							+ new SimpleDateFormat("dd/MM/yyyy hh:mm:a").format(Calendar.getInstance().getTime()));
					email.send();
				}
				//get pay period info
				NLESDPayAdvicePayGroupBean pgbean = NLESDPayAdvicePayGroupManager.getNLESDPayAdvicePayGroupBean(this.payGroupId);
				//update process record
				NLESDPayAdvicePayrollProcessManager.updateNLESDPayAdvicePayrollProcessStarted(this.payGroupId, username);
				String foldername = pgbean.getPayBgDt().replace("/", "") + pgbean.getPayEndDt().replace("/", "");
				String dirname = this.serverpath + NLESDPayAdvicePayStubProcessWorker.DOCUMENT_BASEPATH + foldername;
				//check to see if folder is there, of not create
				File theDir = new File(dirname);
				if (!theDir.exists()) {
					theDir.mkdirs();
					File theDirPS = new File(dirname + "/stubs");
					theDirPS.mkdirs();
				}
				ArrayList<NLESDPayAdvicePayStubWorkerBean> list = NLESDPayAdvicePayStubWorkerManager.getPayStubInformation(this.payGroupId);
				for (NLESDPayAdvicePayStubWorkerBean psbean : list) {
					String returnstring = NLESDPayAdviceCreateStubManager.createStubForEmployee(psbean.getEmpNumber(),
							this.payGroupId, dirname, psbean.getEmail(), isTest);
	
					System.out.println("*** TEACHER PAYROLL: " + returnstring + " file created.");
	
					//now save the info to the table for email
					NLESDPayAdvicePayStubBean bean = new NLESDPayAdvicePayStubBean();
					bean.setFileName(returnstring);
	
					bean.setPaygroupId(this.payGroupId);
					bean.setPayrollId(psbean.getEmpNumber());
	
					//now we will try and email the stub
					if (StringUtils.isEmpty(psbean.getEmail())) {
						bean.setStubError("NO PAYROLL INFORMATION");
						bean.setEmailed(0);
						//no information in sds table, retrieve name from employee info table
						if (StringUtils.isEmpty(psbean.getFirstName())) {
							NLESDPayAdviceEmployeeInfoBean ei = NLESDPayAdviceEmployeeInfoManager.getNLESDPayAdviceEmployeeInfoBean(
									this.payGroupId, psbean.getEmpNumber());
	
							sb.append(ei.getEmpName() + ": No Payroll Information");
							
						}
						else {
							sb.append(psbean.getFirstName() + " " + psbean.getLastName() + ": No Payroll Information");
						}
						sb.append("\n");
	
						System.out.println(psbean.getFirstName() + " " + psbean.getLastName() + ": No Payroll Information");
	
					}
					else {
						//email exists, send stub
						EmailBean ebean = new EmailBean();
						ebean.setSubject("Pay Advice for " + psbean.getFirstName() + " " + psbean.getLastName() + " Deposited on "
								+ pgbean.getCheckDt());
						ebean.setTo(psbean.getEmail());
						ebean.setFrom("payadvice@nlesd.ca");
						ebean.setBody(VelocityUtils.mergeTemplateIntoString("payadvice/pay_advice_email.vm", null));
	
						//add paystub
						if (StringUtils.isNotEmpty(returnstring)) {
							ebean.setAttachments(new File[] {
								new File(returnstring)
							});
	
							System.out.println("*** TEACHER PAYROLL: File Found: " + returnstring);
						}
	
						ebean.send();
						bean.setStubError("");
						bean.setEmailed(1);
	
					}
					//update paystub table with error or no error
					NLESDPayAdvicePayStubManager.addNLESDPayAdvicePayStub(bean);
	
				}
	
				NLESDPayAdvicePayrollProcessManager.updateNLESDPayAdvicePayrollProcessFinished(this.getPayGroupId());
				//email.setTo(PersonnelDB.getPersonnelByPermission("PERSONNEL-SUBSTITUTES-RELOAD-TABLES"));
				for (Personnel p : sendTo) {
					email.setTo(p.getEmailAddress());
					email.setSubject("Pay Stub Creation process completed");
					sb.insert(
							0,
							"Pay Stub Creation process finished at "
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
					email.setSubject("Pay Stub Creation  process ERROR Notification");
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
