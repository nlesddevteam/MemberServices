package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import com.awsd.personnel.PersonnelDB;
import com.awsd.school.SubjectDB;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.AssignmentMajorMinorBean;
import com.esdnl.personnel.jobs.bean.AssignmentTrainingMethodBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SEOStaffingAssignmentBean;
import com.esdnl.personnel.jobs.constants.CriteriaType;

public class SEOStaffingAssignmentManager {

	public static void addSEOStaffingAssignmentBean(SEOStaffingAssignmentBean bean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_seo_staffing_assignment(?,?,?); end;");

			stat.setInt(1, bean.getPersonnel().getPersonnelID());
			stat.setInt(2, bean.getCriteriaType().getValue());
			stat.setInt(3, bean.getCriteriaId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addSEOStaffingAssignmentBean(SEOStaffingAssignmentBean bean): " + e);
			throw new JobOpportunityException("Can not add SEOStaffingAssignmentManager to DB.", e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

	}

	public static void deleteSEOStaffingAssignmentBean(int staffingId) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_seo_staffing_assignment(?); end;");

			stat.setInt(1, staffingId);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteSEOStaffingAssignmentBean(int staffingId): " + e);
			throw new JobOpportunityException("Can not delete SEOStaffingAssignmentManager from DB.", e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

	}

	public static ArrayList<SEOStaffingAssignmentBean> getSEOStaffingAssignmentBeans() throws JobOpportunityException {

		ArrayList<SEOStaffingAssignmentBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new ArrayList<SEOStaffingAssignmentBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_seo_staffing_assignments; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createSEOStaffingAssignmentBean(rs));

		}
		catch (SQLException e) {
			System.err.println("ArrayList<SEOStaffingAssignmentBean> getSEOStaffingAssignmentBeans(): " + e);
			throw new JobOpportunityException("Can not extract SEOStaffingAssignmentBean from DB.", e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

		return beans;
	}

	public static ArrayList<SEOStaffingAssignmentBean> getSEOStaffingAssignmentBeans(JobOpportunityBean job)
			throws JobOpportunityException {

		ArrayList<SEOStaffingAssignmentBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new ArrayList<SEOStaffingAssignmentBean>(5);

			JobOpportunityAssignmentBean[] assignments = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(job);

			if ((assignments != null) && (assignments.length > 0)) {
				ArrayList<Integer> trnmthds = new ArrayList<Integer>();
				ArrayList<Integer> subjects = new ArrayList<Integer>();

				for (AssignmentMajorMinorBean mm : AssignmentMajorMinorManager.getAssignmentMajorMinorBeans(assignments[0])) {
					if (mm.getMajorId() > 0)
						subjects.add(mm.getMajorId());

					if (mm.getMinorId() > 0)
						subjects.add(mm.getMinorId());
				}

				for (AssignmentTrainingMethodBean trnmthd : AssignmentTrainingMethodManager.getAssignmentTrainingMethodBeans(assignments[0]))
					trnmthds.add(trnmthd.getTrainingMethod().getValue());

				con = DAOUtils.getConnection();

				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_seo_staffing_assignments(?,?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);

				stat.setArray(
						2,
						new ARRAY(ArrayDescriptor.createDescriptor("NUM_ARRAY", con), con, (Integer[]) trnmthds.toArray(new Integer[0])));
				stat.setArray(
						3,
						new ARRAY(ArrayDescriptor.createDescriptor("NUM_ARRAY", con), con, (Integer[]) subjects.toArray(new Integer[0])));

				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);

				while (rs.next())
					beans.add(createSEOStaffingAssignmentBean(rs));
			}

		}
		catch (SQLException e) {
			System.err.println("ArrayList<SEOStaffingAssignmentBean> getSEOStaffingAssignmentBeans(): " + e);
			throw new JobOpportunityException("Can not extract SEOStaffingAssignmentBean from DB.", e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

		return beans;
	}

	public static SEOStaffingAssignmentBean createSEOStaffingAssignmentBean(ResultSet rs) {

		SEOStaffingAssignmentBean aBean = null;

		try {
			aBean = new SEOStaffingAssignmentBean();

			aBean.setStaffingId(rs.getInt("staffing_id"));
			aBean.setPersonnel(PersonnelDB.createPersonnelBean(rs));
			aBean.setCriteriaId(rs.getInt("criteria_id"));
			aBean.setCriteriaType(CriteriaType.get(rs.getInt("criteria_type_id")));

			if (aBean.getCriteriaType().equal(CriteriaType.CRITERIA_SUBJECT))
				aBean.setSubject(SubjectDB.createSubjectBean(rs));

		}
		catch (SQLException e) {
			aBean = null;
		}

		return aBean;
	}

}
