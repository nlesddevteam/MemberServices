package com.esdnl.fund3.worker;
import java.util.TimerTask;
import com.esdnl.fund3.dao.ProjectReportManager;
public class Fund3ReportWorker extends TimerTask {
	public void run() {
		try {
			//first we check the default report from the project screen
			//monthly report
			ProjectReportManager.getMonthlyReports();
			//now we check the other types of reports
			//one time reports
			ProjectReportManager.getOneTimeReports();
			//now we check the annually ones
			ProjectReportManager.getAnnualReports();
			//now we check semi annual
			ProjectReportManager.getSemiAnnualReports();
			//now we check the quarterly
			ProjectReportManager.getQuarterlyReports();
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
		}
	}
}
