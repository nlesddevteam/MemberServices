package com.awsd.ppgp;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelException;

public class PPGP extends HashMap<Integer, PPGPGoal> implements Serializable {

	private static final long serialVersionUID = 3661807784889385381L;

	private int ppgpID;
	private Personnel p = null;
	private String school_year;

	public PPGP() throws PPGPException {

		this(-1, null, null);
	}

	public PPGP(Personnel p) throws PPGPException {

		this(-1, p, PPGP.getCurrentGrowthPlanYear());
	}

	public PPGP(Personnel p, String sy) throws PPGPException {

		this(-1, p, sy);
	}

	public PPGP(int ppgpID, Personnel p, String school_year) throws PPGPException {

		this.setPPGPID(ppgpID);
		this.setPersonnel(p);
		this.setSchoolYear(school_year);
	}

	public int getPPGPID() {

		return ppgpID;
	}

	public void setPPGPID(int ppgp_id) {

		ppgpID = ppgp_id;
	}

	public Personnel getPersonnel() throws PersonnelException {

		return p;
	}

	public void setPersonnel(Personnel p) {

		this.p = p;
	}

	public String getSchoolYear() {

		return school_year;
	}

	public void setSchoolYear(String school_year) {

		this.school_year = school_year;
	}

	public boolean isSelfReflectionComplete() {

		boolean complete = true;

		if (this.size() > 0) {
			for_each_goal: for (PPGPGoal goal : this.values()) {
				if (goal.size() > 0) {
					for (PPGPTask task : goal.values()) {
						if (!task.isSelfReflectionComplete()) {
							complete = false;
							break for_each_goal;
						}
					}
				}
				else {
					complete = false;
					break for_each_goal;
				}
			}
		}
		else
			complete = false;

		return complete;
	}

	public static String getGrowthPlanYear(int offset) {

		String school_year;

		Calendar cur = Calendar.getInstance();

		cur.add(Calendar.YEAR, offset);

		if (((cur.get(Calendar.MONTH) == Calendar.MAY) && (cur.get(Calendar.DATE) >= 1))
				|| (cur.get(Calendar.MONTH) > Calendar.MAY)) {
			// beginning of school year
			school_year = cur.get(Calendar.YEAR) + "-" + (cur.get(Calendar.YEAR) + 1);
		}
		else if (cur.get(Calendar.MONTH) <= Calendar.MAY) {
			// end of school year
			school_year = (cur.get(Calendar.YEAR) - 1) + "-" + (cur.get(Calendar.YEAR));
		}
		else {
			school_year = "UNKNOWN";
		}

		return school_year;
	}

	public static String getPreviousGrowthPlanYear() {

		return PPGP.getGrowthPlanYear(-1);
	}

	public static String getCurrentGrowthPlanYear() {

		return PPGP.getGrowthPlanYear(0);
	}

	public static String getNextGrowthPlanYear() {

		return PPGP.getGrowthPlanYear(1);
	}

}