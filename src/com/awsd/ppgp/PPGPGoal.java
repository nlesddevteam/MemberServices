package com.awsd.ppgp;

import java.io.Serializable;
import java.util.HashMap;

public class PPGPGoal extends HashMap<Integer, PPGPTask> implements Serializable {

	private static final long serialVersionUID = 3692225261256425076L;

	private int gid;
	private String desc;

	public PPGPGoal() {

		this(-1, null);
	}

	public PPGPGoal(String desc) {

		this(-1, desc);
	}

	public PPGPGoal(int gid, String desc) {

		this.setPPGPGoalID(gid);
		this.setPPGPGoalDescription(desc);
	}

	public int getPPGPGoalID() {

		return gid;
	}

	public void setPPGPGoalID(int gid) {

		this.gid = gid;
	}

	public String getPPGPGoalDescription() {

		return desc;
	}

	public void setPPGPGoalDescription(String desc) {

		this.desc = desc;
	}

}