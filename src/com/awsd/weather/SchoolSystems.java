package com.awsd.weather;

import java.util.Iterator;
import java.util.Timer;
import java.util.Vector;

import com.awsd.servlet.ControllerServlet;

public class SchoolSystems {

	private static Vector<SchoolSystem> s = null;
	private static Timer timer = null;
	private static final long WAIT_PERIOD = 150000; // 2.5 mins

	static {
		try {
			s = SchoolSystemDB.getSchoolClosureStatuses();
			System.gc();

			System.err.println("************ STATUS CENTRAL EXPORT STATUS: "
					+ ControllerServlet.WEATHER_CENTRAL_EXPORT_STATUS);
			if (ControllerServlet.WEATHER_CENTRAL_EXPORT_STATUS.equalsIgnoreCase("ON")) {
				timer = new Timer();
				timer.schedule(new SchoolClosureStatusWorker(s), 0, SchoolSystems.WAIT_PERIOD);
			}
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
		}
	}

	public static void stopTimer() {

		timer.cancel();
	}

	public SchoolSystems() {

	}

	public boolean add(SchoolSystem ss) {

		synchronized (s) {
			s.add(ss);
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	public Iterator<SchoolSystem> iterator() {

		synchronized (s) {
			Vector<SchoolSystem> v = (Vector<SchoolSystem>) s.clone();
			return v.iterator();
		}
	}

	public int size() {

		synchronized (s) {
			return s.size();
		}
	}

	public void setVector(Vector<SchoolSystem> s) {

		synchronized (s) {
			SchoolSystems.s = s;
		}
	}

	public static void reload() {

		try {
			synchronized (s) {
				s = SchoolSystemDB.getSchoolClosureStatuses();
			}

			System.gc();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}