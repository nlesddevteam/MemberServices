package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.TeacherAllocationBean;
import com.esdnl.personnel.jobs.bean.TeacherAllocationExtraBean;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class TeacherAllocationExtraManager {

	public static TeacherAllocationExtraBean addTeacherAllocationExtraBean(TeacherAllocationExtraBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_tchr_allocation_extra(?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, abean.getAllocationId());
			stat.setDouble(3, abean.getExtraAllocationUnits());
			stat.setString(4, abean.getRationale());
			stat.setInt(5, abean.getAllocationType().getValue());

			stat.execute();

			int id = stat.getInt(1);

			abean.setExtraId(id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(
					"TeacherAllocationExtraBean addTeacherAllocationExtraBean(TeacherAllocationExtraBean abean): " + e);
			throw new JobOpportunityException("Can not add TeacherAllocationExtraBean to DB.", e);
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

		return abean;
	}

	public static void updateTeacherAllocationExtraBean(TeacherAllocationExtraBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.mod_tchr_allocation_extra(?,?,?,?); end;");

			stat.setInt(1, abean.getExtraId());
			stat.setDouble(2, abean.getExtraAllocationUnits());
			stat.setString(3, abean.getRationale());
			stat.setInt(4, abean.getAllocationType().getValue());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void updateTeacherAllocationExtraBean(TeacherAllocationExtraBean abean): " + e);
			throw new JobOpportunityException("Can not update TeacherAllocationExtraBean in DB.", e);
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

	public static void deleteTeacherAllocationExtraBean(TeacherAllocationExtraBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_tchr_allocation_extra(?); end;");

			stat.setInt(1, abean.getExtraId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteTeacherAllocationExtraBean(TeacherAllocationExtraBean abean): " + e);
			throw new JobOpportunityException("Can not delete TeacherAllocationExtraBean to DB.", e);
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

	public static TeacherAllocationExtraBean getTeacherAllocationExtraBean(int id) throws JobOpportunityException {

		TeacherAllocationExtraBean extra = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_tchr_allocation_extra(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				extra = createTeacherAllocationExtraBean(rs);
		}
		catch (SQLException e) {
			System.err.println("TeacherAllocationExtraBean getTeacherAllocationExtraBean(int id): " + e);
			throw new JobOpportunityException("Can not extract TeacherAllocationExtraBean from DB.", e);
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

		return extra;
	}

	public static Collection<TeacherAllocationExtraBean> getTeacherAllocationExtraBeans(TeacherAllocationBean allocation)
			throws JobOpportunityException {

		Collection<TeacherAllocationExtraBean> extras = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			extras = new ArrayList<TeacherAllocationExtraBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_tchr_allocation_extras(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, allocation.getAllocationId());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				extras.add(createTeacherAllocationExtraBean(rs));
		}
		catch (SQLException e) {
			System.err.println(
					"Collection<TeacherAllocationExtraBean> getTeacherAllocationExtraBeans(TeacherAllocationBean allocation): "
							+ e);
			throw new JobOpportunityException("Can not extract TeacherAllocationExtraBean from DB.", e);
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

		return extras;
	}

	public static TeacherAllocationExtraBean createTeacherAllocationExtraBean(ResultSet rs) {

		TeacherAllocationExtraBean abean = null;
		try {
			abean = new TeacherAllocationExtraBean();

			abean.setAllocationId(rs.getInt("ALLOCATION_ID"));
			abean.setExtraId(rs.getInt("EXTRA_ID"));
			abean.setExtraAllocationUnits(rs.getDouble("EXTRA_UNITS"));
			abean.setRationale(rs.getString("RATIONALE"));
			abean.setAllocationType(TeacherAllocationExtraBean.AllocationType.get(rs.getInt("ALLOCATION_TYPE_ID")));
			if (rs.getTimestamp("CREATED_DATE") != null) {
				abean.setCreatedDate(new java.util.Date(rs.getTimestamp("CREATED_DATE").getTime()));
			}
			else {
				abean.setCreatedDate(null);
			}
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}

}
