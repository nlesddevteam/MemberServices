package com.esdnl.personnel.jobs.test;

import java.util.ArrayList;

import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SEOStaffingAssignmentBean;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.SEOStaffingAssignmentManager;

public class SEOStaffingAssignmentManagerTest {

	public static void main(String args[]) {

		try {
			ArrayList<SEOStaffingAssignmentBean> staffing = SEOStaffingAssignmentManager.getSEOStaffingAssignmentBeans(JobOpportunityManager.getJobOpportunityBean("11-0077"));

			System.out.println("Found? " + staffing.size());

			for (SEOStaffingAssignmentBean b : staffing)
				System.out.println(b.getPersonnel().getFullName());
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
		}
	}
}
