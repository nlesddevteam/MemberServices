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
import com.esdnl.personnel.jobs.bean.TeacherAllocationPermanentPositionBean;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeException;

public class TeacherAllocationPermanentPositionManager {

	public static TeacherAllocationPermanentPositionBean addTeacherAllocationPermanentPositionBean(	TeacherAllocationPermanentPositionBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_tchr_allocation_perm(?,?,?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, abean.getAllocationId());
			stat.setString(3, abean.getEmployee().getEmpId().trim());
			stat.setInt(4, abean.getClassSize());
			stat.setString(5, abean.getAssignment());
			stat.setDouble(6, abean.getUnit());
			stat.setString(7, abean.getTenur());

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

			System.err.println("TeacherAllocationPermanentPositionBean addTeacherAllocationPermanentPositionBean(	TeacherAllocationPermanentPositionBean abean): "
					+ e);
			throw new JobOpportunityException("Can not add TeacherAllocationPermanentPositionBean to DB.", e);
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

	public static void updateTeacherAllocationPermanentPositionBean(TeacherAllocationPermanentPositionBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.mod_tchr_allocation_perm(?,?,?,?,?,?); end;");

			stat.setInt(1, abean.getPositionId());
			stat.setString(2, abean.getEmployee().getEmpId().trim());
			stat.setInt(3, abean.getClassSize());
			stat.setString(4, abean.getAssignment());
			stat.setDouble(5, abean.getUnit());
			stat.setString(6, abean.getTenur());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void updateTeacherAllocationPermanentPositionBean(TeacherAllocationPermanentPositionBean abean): "
					+ e);
			throw new JobOpportunityException("Can not update TeacherAllocationPermanentPositionBean in DB.", e);
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

	public static void deleteTeacherAllocationPermanentPositionBean(TeacherAllocationPermanentPositionBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_tchr_allocation_perm(?); end;");

			stat.setInt(1, abean.getPositionId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteTeacherAllocationPermanentPositionBean(TeacherAllocationPermanentPositionBean abean): "
					+ e);
			throw new JobOpportunityException("Can not delete TeacherAllocationPermanentPositionBean to DB.", e);
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

	public static TeacherAllocationPermanentPositionBean getTeacherAllocationPermanentPositionBean(int id)
			throws JobOpportunityException {

		TeacherAllocationPermanentPositionBean position = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_tchr_allocation_perm(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				position = createTeacherAllocationPermanentPositionBean(rs);
		}
		catch (SQLException e) {
			System.err.println("TeacherAllocationPermanentPositionBean getTeacherAllocationPermanentPositionBean(int id): "
					+ e);
			throw new JobOpportunityException("Can not extract TeacherAllocationPermanentPositionBean from DB.", e);
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

	public static Collection<TeacherAllocationPermanentPositionBean> getTeacherAllocationPermanentPositionBeans(TeacherAllocationBean allocation)
			throws JobOpportunityException {

		Collection<TeacherAllocationPermanentPositionBean> perms = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			perms = new ArrayList<TeacherAllocationPermanentPositionBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_tchr_allocation_perms(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, allocation.getAllocationId());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				perms.add(createTeacherAllocationPermanentPositionBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Collection<TeacherAllocationPermanentPositionBean> getTeacherAllocationPermanentPositionBean(TeacherAllocationPermanentPositionBean allocation): "
					+ e);
			throw new JobOpportunityException("Can not extract TeacherAllocationPermanentPositionBean from DB.", e);
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

		return perms;
	}

	public static TeacherAllocationPermanentPositionBean createTeacherAllocationPermanentPositionBean(ResultSet rs) {

		TeacherAllocationPermanentPositionBean abean = null;
		try {
			abean = new TeacherAllocationPermanentPositionBean();

			abean.setAllocationId(rs.getInt("ALLOCATION_ID"));
			abean.setPositionId(rs.getInt("POSITION_ID"));
			try {
				abean.setEmployee(EmployeeManager.getEmployeeBean(rs.getString("EMP_ID").trim()));
			}
			catch (EmployeeException e) {
				abean.setEmployee(null);
			}
			abean.setClassSize(rs.getInt("CLASS_SIZE"));
			abean.setAssignment(rs.getString("ASSIGNMENT"));
			abean.setUnit(rs.getDouble("UNIT"));
			abean.setTenur(rs.getString("TENUR"));
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}

}
