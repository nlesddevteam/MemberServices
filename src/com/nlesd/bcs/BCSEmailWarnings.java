package com.nlesd.bcs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.apache.velocity.app.Velocity;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.velocity.VelocityUtils;
import com.nlesd.bcs.bean.ApplicationSettingsBean;
import com.nlesd.bcs.bean.BussingContractorDocumentBean;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.bean.BussingContractorReportingHistoryBean;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.dao.ApplicationSettingsManager;
import com.nlesd.bcs.dao.BussingContractorReportingHistoryManager;
import com.nlesd.bcs.dao.BussingContractorWarningsManager;

public class BCSEmailWarnings {
	public static void main(String args[]) {
		ApplicationSettingsBean abean = ApplicationSettingsManager.getApplicationSettings();
		if(!(abean.isRunWeeklyReport())) {
			//report disable by admin
			return;
		}
		
		try {
			int emailcount=0;
			Velocity.setProperty("file.resource.loader.path", "");
			Velocity.init();
			String esql = BussingContractorWarningsManager.getWarningsSqlByTable(EntryTableConstant.CONTRACTOREMPLOYEE.getDescription());
			String vsql = BussingContractorWarningsManager.getWarningsSqlByTable(EntryTableConstant.CONTRACTORVEHICLE.getDescription());
			String cdsql = BussingContractorWarningsManager.getWarningsSqlByTable(EntryTableConstant.CONTRACTORDOC.getDescription());
			//used to send email to bussing admin
			ArrayList<String> suspendedliste = new ArrayList<String>();
			TreeMap<String,ArrayList<BussingContractorEmployeeBean>> elist = BussingContractorWarningsManager.getEmployeeWarningsAutomatedTM(esql,suspendedliste);
			//now we check the manual warnings that can't just be done through sql
			BussingContractorWarningsManager.getCODEmployeeWarnings(elist,suspendedliste);
			BussingContractorWarningsManager.getDriverAbstractEmployeeWarnings(elist,suspendedliste);
			ArrayList<String> suspendedlistv = new ArrayList<String>();
			TreeMap<String,ArrayList<BussingContractorVehicleBean>> vlist = BussingContractorWarningsManager.getVehicleWarningsAutomatedTM(vsql,suspendedlistv);
			//now we check the manual vehicle warnings
			BussingContractorWarningsManager.getFallVehicleWarningsTM(vlist, suspendedlistv);
			Calendar cal = Calendar.getInstance();
			Date rundate = new Date();
			cal.setTime(rundate);
			int month = cal.get(Calendar.MONTH);
			//check to see if we need to start validating the winter inspection
			if(month > 9) {
				BussingContractorWarningsManager.getWinterVehicleWarningsTM(vlist, suspendedlistv);
			}
			
			TreeMap<String,ArrayList<BussingContractorDocumentBean>> cdlist = BussingContractorWarningsManager.getDocumentWarningsAutomatedTM(cdsql);
			//first we loop through the employees
			for(Map.Entry<String,ArrayList<BussingContractorEmployeeBean>> entry : elist.entrySet()) {
					String emaila =null;
					StringBuilder sb = new StringBuilder();
					sb.append("<b><u>" + entry.getKey() + "</u></b><br />");
					sb.append("<b><u>" + "Employee Warnings" + "</u></b><br />");
					for(BussingContractorEmployeeBean eb: entry.getValue()) {
						sb.append("<p>");
						sb.append(eb.getFirstName() + " " + eb.getLastName() + ": " + eb.getWarningNotes());
						sb.append("</p>");
						if(emaila == null) {
							emaila=eb.getCompanyEmail();
						}
					}
					// now we check for vehicle warnings the same company
					if(vlist.containsKey(entry.getKey())) {
						//now we add the vehicle warnings
						ArrayList<BussingContractorVehicleBean> vehlist = vlist.get(entry.getKey());
						sb.append("<b><u>" + "Vehicle Warnings" + "</u></b><br />");
						for(BussingContractorVehicleBean vbean : vehlist) {
							if(emaila == null) {
								emaila=vbean.getCompanyEmail();
							}
							sb.append("<p>");
							sb.append(vbean.getvPlateNumber() +"(SN:" + vbean.getvSerialNumber() + " Year:" + vbean.getvYear() +") : "  + vbean.getWarningNotes());
							sb.append("</p>");
						}
					}
					//now we send the message
					EmailBean email = new EmailBean();
					email.setTo(emaila);
					email.setFrom("bussingcontractorsystem@nlesd.ca");
					email.setSubject("NLESD Bussing Contractor System Expirations");
					HashMap<String, Object> model = new HashMap<String, Object>();
					// set values to be used in template
					model.put("pbody", sb.toString());
					email.setBody(VelocityUtils.mergeTemplateIntoString("C:\\BCSEmail\\bcs\\contractor_warnings.vm", model));
					email.send();
					System.out.println("employee email sent to:" + emaila);
					emailcount ++;
			}
			
			//now we check to see if any of the companies only had vehicle warnings
			for(Map.Entry<String,ArrayList<BussingContractorVehicleBean>> entry : vlist.entrySet()) {
				if(elist.containsKey(entry.getKey())) {
					//do nothing already done
				}else {
					String emaila =null;
					StringBuilder sb = new StringBuilder();
					sb.append("<b><u>" + entry.getKey() + "</u></b><br />");
					sb.append("<b><u>" + "Vehicle Warnings" + "</u></b><br />");
					for(BussingContractorVehicleBean eb: entry.getValue()) {
						if(emaila == null) {
							emaila=eb.getCompanyEmail();
						}
						sb.append("<p>");
						sb.append(eb.getvPlateNumber() +"(SN:" + eb.getvSerialNumber() + " Year:" + eb.getvYear() +") : "  + eb.getWarningNotes());
						sb.append("</p>");
					}
					//now we send the message
					EmailBean email = new EmailBean();
					email.setTo(emaila);
					email.setFrom("bussingcontractorsystem@nlesd.ca");
					email.setSubject("NLESD Bussing Contractor System Expirations");
					HashMap<String, Object> model = new HashMap<String, Object>();
					// set values to be used in template
					model.put("pbody", sb.toString());
					email.setBody(VelocityUtils.mergeTemplateIntoString("C:\\BCSEmail\\bcs\\contractor_warnings.vm", model));
					email.send();
					System.out.println("vehicle email sent to:" + emaila);
					emailcount ++;
				}
			}
			//now we check to see if any of the companies only had vehicle warnings
			for(Map.Entry<String,ArrayList<BussingContractorDocumentBean>> entry : cdlist.entrySet()) {
				if(elist.containsKey(entry.getKey())) {
					//do nothing already done
				}else {
					String emaila =null;
					StringBuilder sb = new StringBuilder();
					sb.append("<b><u>" + entry.getKey() + "</u></b><br />");
					sb.append("<b><u>" + "Document Warnings" + "</u></b><br />");
					for(BussingContractorDocumentBean eb: entry.getValue()) {
						if(emaila == null) {
							emaila=eb.getCompanyEmail();
						}
						sb.append("<p>");
						sb.append(eb.getDocumentTitle() +"(" + eb.getTypeString()  +") : "  + eb.getWarningNotes());
						sb.append("</p>");
					}
					//now we send the message
					EmailBean email = new EmailBean();
					email.setTo(emaila);
					email.setFrom("bussingcontractorsystem@nlesd.ca");
					email.setSubject("NLESD Bussing Contractor System Expirations");
					HashMap<String, Object> model = new HashMap<String, Object>();
					// set values to be used in template
					model.put("pbody", sb.toString());
					email.setBody(VelocityUtils.mergeTemplateIntoString("C:\\BCSEmail\\bcs\\contractor_warnings.vm", model));
					email.send();
					System.out.println("doc email sent to:" + emaila);
					emailcount ++;
				}
			}
			//update table to show that it ran
			BussingContractorReportingHistoryBean rbean = new BussingContractorReportingHistoryBean();
			rbean.setReportTitle("Weekly Contractor Expirations");
			rbean.setRanBy("System");
			BussingContractorReportingHistoryManager.addReportingHistoyBean(rbean);
			System.out.println("Total Emails:" + emailcount);

		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
		}
	}
	
}
