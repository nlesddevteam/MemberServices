package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.TeacherAllocationBean;
import com.esdnl.personnel.jobs.bean.TeacherAllocationRedundantPositionBean;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeException;

public class TeacherAllocationRedundantPositionManager {

	public static TeacherAllocationRedundantPositionBean addTeacherAllocationRedundantPositionBean(	TeacherAllocationRedundantPositionBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_tchr_allocation_redundancy(?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, abean.getAllocationId());
			stat.setString(3, abean.getEmployee().getEmpId().trim());
			stat.setString(4, abean.getRationale());
			stat.setDouble(5, abean.getUnit());

			stat.execute();

			int id = stat.getInt(1);

			abean.setPositionId(id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("TeacherAllocationRedundantPositionBean addTeacherAllocationRedundantPositionBean(TeacherAllocationRedundantPositionBean abean): "
					+ e);
			throw new JobOpportunityException("Can not add TeacherAllocationRedundantPositionBean to DB.", e);
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

	public static void updateTeacherAllocationRedundantPositionBean(TeacherAllocationRedundantPositionBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.mod_tchr_allocation_redundancy(?,?,?,?); end;");

			stat.setInt(1, abean.getPositionId());
			stat.setString(2, abean.getEmployee().getEmpId().trim());
			stat.setString(3, abean.getRationale());
			stat.setDouble(4, abean.getUnit());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void updateTeacherAllocationRedundantPositionBean(TeacherAllocationRedundantPositionBean abean): "
					+ e);
			throw new JobOpportunityException("Can not update TeacherAllocationRedundantPositionBean in DB.", e);
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

	public static void deleteTeacherAllocationRedundantPositionBean(TeacherAllocationRedundantPositionBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_tchr_allocation_redundancy(?); end;");

			stat.setInt(1, abean.getPositionId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteTeacherAllocationRedundantPositionBean(TeacherAllocationRedundantPositionBean abean): "
					+ e);
			throw new JobOpportunityException("Can not delete TeacherAllocationRedundantPositionBean to DB.", e);
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

	public static TeacherAllocationRedundantPositionBean getTeacherAllocationRedundantPositionBean(int id)
			throws JobOpportunityException {

		TeacherAllocationRedundantPositionBean position = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_tchr_allocation_redundancy(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				position = createTeacherAllocationRedundantPositionBean(rs);
		}
		catch (SQLException e) {
			System.err.println("TeacherAllocationRedundantPositionBean getTeacherAllocationRedundantPositionBean(int id): "
					+ e);
			throw new JobOpportunityException("Can not extract TeacherAllocationRedundantPositionBean from DB.", e);
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

		return position;
	}

	public static Collection<TeacherAllocationRedundantPositionBean> getTeacherAllocationRedundantPositionBeans(TeacherAllocationBean allocation)
			throws JobOpportunityException {

		Collection<TeacherAllocationRedundantPositionBean> redundancies = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			redundancies = new ArrayList<TeacherAllocationRedundantPositionBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_tchr_allocation_redundants(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, allocation.getAllocationId());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				redundancies.add(createTeacherAllocationRedundantPositionBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Collection<TeacherAllocationRedundantPositionBean> getTeacherAllocationRedundantPositionBeans(TeacherAllocationBean allocation): "
					+ e);
			throw new JobOpportunityException("Can not extract TeacherAllocationRedundantPositionBean from DB.", e);
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

		return redundancies;
	}

	public static TeacherAllocationRedundantPositionBean createTeacherAllocationRedundantPositionBean(ResultSet rs) {

		TeacherAllocationRedundantPositionBean abean = null;

		try {
			abean = new TeacherAllocationRedundantPositionBean();

			abean.setAllocationId(rs.getInt("ALLOCATION_ID"));
			abean.setPositionId(rs.getInt("POSITION_ID"));

			try {
				abean.setEmployee(EmployeeManager.getEmployeeBean(rs.getString("EMP_ID").trim()));
			}
			catch (EmployeeException e) {
				abean.setEmployee(null);
			}

			abean.setRationale(rs.getString("RATIONALE"));
			abean.setUnit(rs.getDouble("UNIT"));
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}

}
