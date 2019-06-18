package com.esdnl.personnel.sss.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.PersonnelDB;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.sss.domain.bean.SSSException;
import com.esdnl.personnel.sss.domain.bean.SSSProfileSchoolInfoBean;

public class SSSProfileSchoolInfoManager {

	public static SSSProfileSchoolInfoBean addSSSProfileSchoolInfoBean(SSSProfileSchoolInfoBean bean) throws SSSException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.personnel_sss_profile_pkg.add_school_profile(?,?,?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, bean.getSchool().getSchoolID());
			stat.setInt(3, bean.getPeriodLength());
			stat.setInt(4, bean.getPeriodsPerDay());
			stat.setInt(5, bean.getDaysInCycle());
			stat.setString(6, bean.getProjectedSchoolYear());
			stat.setInt(7, bean.getEnteredBy().getPersonnelID());

			stat.execute();

			bean.setProfileId(((OracleCallableStatement) stat).getInt(1));

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(" void addSSSProfileSchoolInfoBean(SSSProfileSchoolInfoBean bean): " + e);
			throw new SSSException("Can not add SSSProfileSchoolInfoBean to DB.", e);
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

		return bean;
	}

	public static void updateSSSProfileSchoolInfoBean(SSSProfileSchoolInfoBean bean) throws SSSException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_sss_profile_pkg.update_school_profile(?,?,?,?,?,?); end;");

			stat.setInt(1, bean.getProfileId());
			stat.setInt(2, bean.getPeriodLength());
			stat.setInt(3, bean.getPeriodsPerDay());
			stat.setInt(4, bean.getDaysInCycle());
			stat.setString(5, bean.getProjectedSchoolYear());
			stat.setInt(6, bean.getEnteredBy().getPersonnelID());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void updateSSSProfileSchoolInfoBean(SSSProfileSchoolInfoBean bean): " + e);
			throw new SSSException("Can not update SSSProfileSchoolInfoBean to DB.", e);
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

	public static SSSProfileSchoolInfoBean getSSSProfileSchoolInfoBean(int profileId) throws SSSException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		SSSProfileSchoolInfoBean bean = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_sss_profile_pkg.get_school_profile(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, profileId);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = createSSSProfileSchoolInfoBean(rs);

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SSSProfileSchoolInfoBean getSSSProfileSchoolInfoBean(int profileId): " + e);
			throw new SSSException("Can not add SSSProfileSchoolInfoBean to DB.", e);
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

		return bean;
	}

	public static SSSProfileSchoolInfoBean getSSSProfileSchoolInfoBean(School school, String projected_school_year)
			throws SSSException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		SSSProfileSchoolInfoBean bean = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_sss_profile_pkg.get_school_profile(?,?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, school.getSchoolID());
			stat.setString(3, projected_school_year);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = createSSSProfileSchoolInfoBean(rs);

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(" void addSSSProfileSchoolInfoBean(SSSProfileSchoolInfoBean bean): " + e);
			throw new SSSException("Can not add SSSProfileSchoolInfoBean to DB.", e);
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

		return bean;
	}

	public static SSSProfileSchoolInfoBean createSSSProfileSchoolInfoBean(ResultSet rs) {

		SSSProfileSchoolInfoBean bean = null;

		try {

			bean = new SSSProfileSchoolInfoBean();

			bean.setDaysInCycle(rs.getInt("DAYS_PER_CYCLE"));
			bean.setEnteredBy(PersonnelDB.createPersonnelBean(rs));
			bean.setEnteredOn(new java.util.Date(rs.getTimestamp("ENTERED_ON").getTime()));
			bean.setPeriodLength(rs.getInt("PERIOD_LENGTH"));
			bean.setPeriodsPerDay(rs.getInt("PERIODS_PER_DAY"));
			bean.setProfileId(rs.getInt("PROFILE_ID"));
			bean.setProjectedSchoolYear(rs.getString("PROJECTED_SY"));
			bean.setSchool(SchoolDB.getSchool(rs.getInt("SCHOOL_ID")));

			bean.setDirty(false);

		}
		catch (SQLException e) {
			e.printStackTrace();
			bean = null;
		}
		catch (SchoolException e) {

			e.printStackTrace();
			bean = null;
		}

		return bean;
	}
}
