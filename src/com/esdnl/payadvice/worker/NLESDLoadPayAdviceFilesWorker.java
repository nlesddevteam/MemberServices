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
import com.esdnl.payadvice.bean.NLESDPayAdviceImportJobBean;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayGroupBean;
import com.esdnl.payadvice.dao.NLESDPayAdviceImportJobManager;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayGroupManager;
import com.esdnl.payadvice.dao.NLESDPayAdviceXMLManager;

public class NLESDLoadPayAdviceFilesWorker extends Thread {

	private String payrollfile;
	private String mappingfile;
	private String historyfile;
	private Integer payrollfileid;
	private Integer mappingfileid;
	private Integer historyfileid;
	private String username;
	private String email;
	private String processStarted;

	public void run() {

		Integer paygroupid = 0;
		try {
				EmailBean email = new EmailBean();
				//email.setTo(PersonnelDB.getPersonnelByPermission("PERSONNEL-SUBSTITUTES-RELOAD-TABLES"));
				ArrayList<Personnel> sendTo = null;
				sendTo = new ArrayList<Personnel>();
				sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByPermission("PAY-ADVICE-ADMIN")));
				sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("ADMINISTRATOR")));
				for (Personnel p : sendTo) {
					email.setTo(p.getEmailAddress());
					email.setSubject("Payroll Files loading process started");
					email.setBody("Payroll Files loading process started at "
							+ new SimpleDateFormat("dd/MM/yyyy hh:mm:a").format(Calendar.getInstance().getTime()));
					//email.send();
				}
	
				//save processing job information
				//save pay grooup info (pay period)
				NLESDPayAdvicePayGroupBean bean = null;
				if (this.payrollfile.length() > 0) {
					bean = NLESDPayAdviceXMLManager.getPayGroupBeanByName(payrollfile);
					paygroupid = NLESDPayAdvicePayGroupManager.addNLESDPayAdvicePayGroupBean(bean);
	
				}
				//now add the record to the Pay_Advice_Import_jobs
				NLESDPayAdviceImportJobBean importbean = new NLESDPayAdviceImportJobBean(this.payrollfileid, this.mappingfileid, paygroupid, this.username, this.historyfileid);
				Integer jobid = NLESDPayAdviceImportJobManager.addNLESDPayAdviceImportJobBean(importbean);
				//update mapping status
				NLESDPayAdviceImportJobManager.updateNLESDPayAdviceImportJobMappingStatus("Processing File", jobid);
				//load mapping table
				NLESDPayAdviceXMLManager.loadMappingFile(this.mappingfile, paygroupid);
				//update mapping status
				NLESDPayAdviceImportJobManager.updateNLESDPayAdviceImportJobMappingStatus("File Processed", jobid);
				NLESDPayAdviceImportJobManager.updateNLESDPayAdviceFileProcessed(this.mappingfileid);
				//update history status
				NLESDPayAdviceImportJobManager.updateNLESDPayAdviceImportJobHistoryStatus("Processing File", jobid);
				//load history table
				NLESDPayAdviceXMLManager.loadHistoryFile(this.historyfile, paygroupid);
				NLESDPayAdviceImportJobManager.updateNLESDPayAdviceImportJobHistoryStatus("File Processed", jobid);
				NLESDPayAdviceImportJobManager.updateNLESDPayAdviceFileProcessed(this.historyfileid);
				//update payroll status
				NLESDPayAdviceImportJobManager.updateNLESDPayAdviceImportJobPayrollStatus("Processing File", jobid);
				NLESDPayAdviceXMLManager.loadPayrollFile(this.payrollfile, paygroupid);
				NLESDPayAdviceImportJobManager.updateNLESDPayAdviceImportJobPayrollStatus("File Processed", jobid);
				NLESDPayAdviceImportJobManager.updateNLESDPayAdviceFileProcessed(this.payrollfileid);
				//mark job complete
				NLESDPayAdviceImportJobManager.updateNLESDPayAdviceImportJobComplete(jobid);
				for (Personnel p : sendTo) {
					email.setTo(p.getEmailAddress());
					email.setSubject("Payroll Files loading process completed");
					email.setBody("Payroll Files loading process finished at "
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
					email.setSubject("Payroll Files loading process ERROR Notification");
					email.setBody(sw.toString());
					email.send();
			}
			catch (Exception ex) {}
		}
	}

	public String getPayrollfile() {

		return payrollfile;
	}

	public void setPayrollfile(String payrollfile) {

		this.payrollfile = payrollfile;
	}

	public String getMappingfile() {

		return mappingfile;
	}

	public void setMappingfile(String mappingfile) {

		this.mappingfile = mappingfile;
	}

	public String getHistoryfile() {

		return historyfile;
	}

	public void setHistoryfile(String historyfile) {

		this.historyfile = historyfile;
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

	public String getProcessStarted() {

		return processStarted;
	}

	public void setProcessStarted(String processStarted) {

		this.processStarted = processStarted;
	}

	public Integer getPayrollfileid() {

		return payrollfileid;
	}

	public void setPayrollfileid(Integer payrollfileid) {

		this.payrollfileid = payrollfileid;
	}

	public Integer getMappingfileid() {

		return mappingfileid;
	}

	public void setMappingfileid(Integer mappingfileid) {

		this.mappingfileid = mappingfileid;
	}

	public Integer getHistoryfileid() {

		return historyfileid;
	}

	public void setHistoryfileid(Integer historyfileid) {

		this.historyfileid = historyfileid;
	}
}
