package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.TeacherAllocationBean;
import com.esdnl.personnel.v2.database.sds.LocationManager;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class TeacherAllocationManager {

	public static TeacherAllocationBean addTeacherAllocationBean(TeacherAllocationBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall(
					"begin ? := awsd_user.personnel_jobs_pkg.add_tchr_allocation(?,?,?,?,?,?,?,?,?,?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, abean.getSchoolYear());
			stat.setString(3, abean.getLocation().getLocationId());
			stat.setDouble(4, abean.getRegularUnits());
			stat.setDouble(5, abean.getAdministrativeUnits());
			stat.setDouble(6, abean.getGuidanceUnits());
			stat.setDouble(7, abean.getSpecialistUnits());
			stat.setDouble(8, abean.getLRTUnits());
			stat.setDouble(9, abean.getIRT1Units());
			stat.setDouble(10, abean.getOtherUnits());
			stat.setDouble(11, abean.getIRT2Units());
			stat.setDouble(12, abean.getTLAUnits());
			stat.setDouble(13, abean.getStudentAssistantHours());
			stat.setDouble(14, abean.getReadingSpecialistUnits());

			stat.execute();

			int id = stat.getInt(1);

			abean.setAllocationId(id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("TeacherAllocationBean addTeacherAllocationBean(TeacherAllocationBean abean): " + e);
			throw new JobOpportunityException("Can not add TeacherAllocationBean to DB.", e);
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

	public static TeacherAllocationBean updateTeacherAllocationBean(TeacherAllocationBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.mod_tchr_allocation(?,?,?,?,?,?,?,?,?,?,?,?); end;");

			stat.setInt(1, abean.getAllocationId());
			stat.setDouble(2, abean.getRegularUnits());
			stat.setDouble(3, abean.getAdministrativeUnits());
			stat.setDouble(4, abean.getGuidanceUnits());
			stat.setDouble(5, abean.getSpecialistUnits());
			stat.setDouble(6, abean.getLRTUnits());
			stat.setDouble(7, abean.getIRT1Units());
			stat.setDouble(8, abean.getOtherUnits());
			stat.setDouble(9, abean.getIRT2Units());
			stat.setDouble(10, abean.getTLAUnits());
			stat.setDouble(11, abean.getStudentAssistantHours());
			stat.setDouble(12, abean.getReadingSpecialistUnits());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("TeacherAllocationBean updateTeacherAllocationBean(TeacherAllocationBean abean): " + e);
			throw new JobOpportunityException("Can not update TeacherAllocationBean to DB.", e);
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

	public static TeacherAllocationBean getTeacherAllocationBean(String schoolYear, String locationId)
			throws JobOpportunityException {

		TeacherAllocationBean allocation = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_tchr_allocation(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);
			stat.setString(3, locationId);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				allocation = createTeacherAllocationBean(rs);
		}
		catch (SQLException e) {
			System.err.println("TeacherAllocationBean getTeacherAllocationBean(String schoolYear, String locationId): " + e);
			throw new JobOpportunityException("Can not extract TeacherAllocationBean from DB.", e);
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

		return allocation;
	}

	public static TeacherAllocationBean getTeacherAllocationBean(int allocationId) throws JobOpportunityException {

		return getTeacherAllocationBean(allocationId, true);
	}

	public static TeacherAllocationBean getTeacherAllocationBean(int allocationId, boolean loadAssociatedData)
			throws JobOpportunityException {

		TeacherAllocationBean allocation = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_tchr_allocation(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, allocationId);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				allocation = createTeacherAllocationBean(rs, loadAssociatedData);
		}
		catch (SQLException e) {
			System.err.println("TeacherAllocationBean getTeacherAllocationBean(int allocationId): " + e);
			throw new JobOpportunityException("Can not extract TeacherAllocationBean from DB.", e);
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

		return allocation;
	}

	public static void updateTeacherAllocationBeanStatus(String schoolYear, boolean enabled)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.mod_tchr_allocation_status(?,?); end;");

			stat.setString(1, schoolYear);
			stat.setBoolean(2, enabled);

			stat.execute();

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void updateTeacherAllocationBeanStatus(String schoolYear, boolean enabled): " + e);
			throw new JobOpportunityException("Can not update TeacherAllocationBean to DB.", e);
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

	public static void updateTeacherAllocationBeanStatus(int allocationId, boolean enabled)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.mod_tchr_allocation_status(?,?); end;");

			stat.setInt(1, allocationId);
			stat.setBoolean(2, enabled);

			stat.execute();

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void updateTeacherAllocationBeanStatus(int allocationId, boolean enabled): " + e);
			throw new JobOpportunityException("Can not update TeacherAllocationBean to DB.", e);
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

	public static void updateTeacherAllocationBeanPublished(String schoolYear, boolean published)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.mod_tchr_allocation_published(?,?); end;");

			stat.setString(1, schoolYear);
			stat.setBoolean(2, published);

			stat.execute();

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void updateTeacherAllocationBeanPublished(String schoolYear, boolean enabled): " + e);
			throw new JobOpportunityException("Can not update TeacherAllocationBean to DB.", e);
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

	public static void updateTeacherAllocationBeanPublished(int allocationId, boolean published)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.mod_tchr_allocation_published(?,?); end;");

			stat.setInt(1, allocationId);
			stat.setBoolean(2, published);

			stat.execute();

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void updateTeacherAllocationBeanPublished(int allocationId, boolean published): " + e);
			throw new JobOpportunityException("Can not update TeacherAllocationBean to DB.", e);
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

	public static void forwardTeacherAllocationBeanStatus(String fromSchoolYear, String toSchoolYear)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.forward_tchr_allocation(?,?); end;");

			stat.setString(1, fromSchoolYear);
			stat.setString(2, toSchoolYear);

			stat.execute();

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void forwardTeacherAllocationBeanStatus(String fromSchoolYear, String toSchoolYear): " + e);
			throw new JobOpportunityException("Can not forward TeacherAllocationBean to DB.", e);
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

	public static TeacherAllocationBean createTeacherAllocationBean(ResultSet rs) {

		return createTeacherAllocationBean(rs, true);
	}

	public static TeacherAllocationBean createTeacherAllocationBean(ResultSet rs, boolean loadAssociatedData) {

		TeacherAllocationBean abean = null;
		try {
			abean = new TeacherAllocationBean();

			abean.setAllocationId(rs.getInt("ALLOCATION_ID"));
			abean.setGuidanceUnits(rs.getDouble("GUIDANCE_UNITS"));
			abean.setLocation(LocationManager.createLocationBean(rs));
			abean.setSpecialistUnits(rs.getDouble("SPECIALIST_UNITS"));
			abean.setAdministrativeUnits(rs.getDouble("ADMINISTRATION_UNITS"));
			abean.setRegularUnits(rs.getDouble("REGULAR_UNITS"));
			abean.setLRTUnits(rs.getDouble("LRT_UNITS"));
			abean.setIRT1Units(rs.getDouble("IRT1_UNITS"));
			abean.setIRT2Units(rs.getDouble("IRT2_UNITS"));
			abean.setOtherUnits(rs.getDouble("OTHER_UNITS"));
			abean.setSchoolYear(rs.getString("SCHOOL_YEAR"));
			abean.setTLAUnits(rs.getDouble("TLA_UNITS"));
			abean.setStudentAssistantHours(rs.getDouble("STUD_ASST_HOURS"));
			abean.setReadingSpecialistUnits(rs.getDouble("READING_SPECIALIST_UNITS"));
			abean.setEnabled(rs.getBoolean("ENABLED"));
			abean.setPublished(rs.getBoolean("PUBLISHED"));

			if (loadAssociatedData) {
				abean.setExtraAllocations(TeacherAllocationExtraManager.getTeacherAllocationExtraBeans(abean));
				abean.setPermanentPositions(
						TeacherAllocationPermanentPositionManager.getTeacherAllocationPermanentPositionBeans(abean));
				abean.setVacantPositions(TeacherAllocationVacantPositionManager.getTeacherAllocationVacantPositionBeans(abean));
				abean.setRedundantPositions(
						TeacherAllocationRedundantPositionManager.getTeacherAllocationRedundantPositionBeans(abean));
			}
		}
		catch (Exception e) {
			abean = null;
		}

		return abean;
	}

}
