package com.nlesd.bcs.worker;
import java.util.TimerTask;
public class ScheduleReportsWorker extends TimerTask {

	//private String rootbasepath = ControllerServlet.CONTEXT_BASE_PATH + "/../ROOT/";

	public ScheduleReportsWorker() {

		super();
		//System.err.println("<<<<<< OPEN JOBS TIMER STARTED >>>>>");
	}

	public void run() {

		try {
			//EmailBean emailbean = new EmailBean();
			//Date d = new Date();
			
			// Note: code built into main page to show warnings to contractor
			//schedule reports not being used currently
			//stub left here in case changes requsted in future
			
			
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
		}
	}

}
