package com.esdnl.scrs.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.bean.RegionBean;
import com.awsd.school.dao.RegionManager;
import com.esdnl.dao.DAOUtils;
import com.esdnl.school.bean.StudentRecordBean;
import com.esdnl.scrs.domain.BullyingBehaviorType;
import com.esdnl.scrs.domain.BullyingException;
import com.esdnl.scrs.domain.LocationType;
import com.esdnl.scrs.domain.MonthlyIncidentFrequencyBean;
import com.esdnl.scrs.domain.TimeType;

public class AdminReportingService {

	public static ArrayList<MonthlyIncidentFrequencyBean> getDistrictMonthlyFrequencyReport() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<MonthlyIncidentFrequencyBean> rpt = null;

		try {
			rpt = new ArrayList<MonthlyIncidentFrequencyBean>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_mthly_freq_stats_by_dist; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				rpt.add(new MonthlyIncidentFrequencyBean(new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()), rs.getInt("FREQUENCY_COUNT")));

			return rpt;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("ArrayList<DistrictFrequencyReportBean> getDistrictFrequencyReport(): " + e);
			throw new BullyingException("Can not fetch DistrictFrequencyReportBean to DB.", e);
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

	public static TreeMap<RegionBean, TreeMap<Date, MonthlyIncidentFrequencyBean>> getRegionMonthlyFrequencyReport()
			throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<RegionBean, TreeMap<Date, MonthlyIncidentFrequencyBean>> rpt = null;
		RegionBean region = null;

		try {
			rpt = new TreeMap<RegionBean, TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_mthly_freq_stats_by_region; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				region = RegionManager.createRegionBean(rs);

				if (!rpt.containsKey(region))
					rpt.put(region, new TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>());

				((TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>) rpt.get(region)).put(
						new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()),
						new MonthlyIncidentFrequencyBean(new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()), rs.getInt("FREQUENCY_COUNT")));
			}

			return rpt;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("HashMap<RegionBean, TreeMap<Date, MonthlyIncidentFrequencyBean>> getRegionFrequencyReport(): "
					+ e);
			throw new BullyingException("Can not fetch DistrictFrequencyReportBean to DB.", e);
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

	public static TreeMap<School, TreeMap<Date, MonthlyIncidentFrequencyBean>> getSchoolMonthlyFrequencyReport()
			throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<School, TreeMap<Date, MonthlyIncidentFrequencyBean>> rpt = null;
		School school = null;

		try {
			rpt = new TreeMap<School, TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_mthly_freq_stats_by_school; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				school = SchoolDB.createSchoolBean(rs);

				if (!rpt.containsKey(school))
					rpt.put(school, new TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>());

				((TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>) rpt.get(school)).put(
						new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()),
						new MonthlyIncidentFrequencyBean(new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()), rs.getInt("FREQUENCY_COUNT")));
			}

			return rpt;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("HashMap<RegionBean, TreeMap<Date, MonthlyIncidentFrequencyBean>> getRegionFrequencyReport(): "
					+ e);
			throw new BullyingException("Can not fetch DistrictFrequencyReportBean to DB.", e);
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

	public static TreeMap<StudentRecordBean.GENDER, TreeMap<Date, MonthlyIncidentFrequencyBean>> getDistrictMonthlyGenderFrequencyReport()
			throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<StudentRecordBean.GENDER, TreeMap<Date, MonthlyIncidentFrequencyBean>> rpt = null;
		StudentRecordBean.GENDER gender = null;

		try {
			rpt = new TreeMap<StudentRecordBean.GENDER, TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_mthly_gender_stats_by_dist; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				gender = StudentRecordBean.GENDER.get(rs.getInt("STUDENT_GENDER"));

				if (!rpt.containsKey(gender))
					rpt.put(gender, new TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>());

				((TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>) rpt.get(gender)).put(
						new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()),
						new MonthlyIncidentFrequencyBean(new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()), rs.getInt("FREQUENCY_COUNT")));
			}

			return rpt;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("TreeMap<BullyingIncidentBean.GENDER, TreeMap<Date, MonthlyIncidentFrequencyBean>> getDistrictGenderFrequencyReport(): "
					+ e);
			throw new BullyingException("Can not fetch DistrictGenderFrequencyReport to DB.", e);
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

	public static TreeMap<School.GRADE, TreeMap<Date, MonthlyIncidentFrequencyBean>> getDistrictMonthlyGradeFrequencyReport()
			throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<School.GRADE, TreeMap<Date, MonthlyIncidentFrequencyBean>> rpt = null;
		School.GRADE grade = null;

		try {
			rpt = new TreeMap<School.GRADE, TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_mthly_grade_stats_by_dist; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				grade = School.GRADE.get(rs.getInt("STUDENT_GRADE_ID"));

				if (!rpt.containsKey(grade))
					rpt.put(grade, new TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>());

				((TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>) rpt.get(grade)).put(
						new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()),
						new MonthlyIncidentFrequencyBean(new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()), rs.getInt("FREQUENCY_COUNT")));
			}

			return rpt;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("TreeMap<School.GRADE, TreeMap<Date, MonthlyIncidentFrequencyBean>> getDistrictMonthlyGradeFrequencyReport(): "
					+ e);
			throw new BullyingException("Can not fetch DistrictGradeFrequencyReport to DB.", e);
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

	public static TreeMap<School.GRADE_CATEGORY, TreeMap<Date, MonthlyIncidentFrequencyBean>> getDistrictMonthlyGradeCategoryFrequencyReport()
			throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<School.GRADE_CATEGORY, TreeMap<Date, MonthlyIncidentFrequencyBean>> rpt = null;
		School.GRADE_CATEGORY grade = null;

		try {
			rpt = new TreeMap<School.GRADE_CATEGORY, TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_mthly_grade_stats_by_dist; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				grade = School.GRADE_CATEGORY.get(School.GRADE.get(rs.getInt("STUDENT_GRADE_ID")));

				if (!rpt.containsKey(grade))
					rpt.put(grade, new TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>());

				((TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>) rpt.get(grade)).put(
						new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()),
						new MonthlyIncidentFrequencyBean(new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()), rs.getInt("FREQUENCY_COUNT")));
			}

			return rpt;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("TreeMap<School.GRADE_CATEGORY, TreeMap<Date, MonthlyIncidentFrequencyBean>> getDistrictMonthlyGradeCategoryFrequencyReport(): "
					+ e);
			throw new BullyingException("Can not fetch DistrictGradeFrequencyReport to DB.", e);
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

	public static TreeMap<LocationType, TreeMap<Date, MonthlyIncidentFrequencyBean>> getDistrictMonthlyLocationFrequencyReport()
			throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<LocationType, TreeMap<Date, MonthlyIncidentFrequencyBean>> rpt = null;
		LocationType loc = null;

		HashMap<Integer, LocationType> locs = null;

		try {
			rpt = new TreeMap<LocationType, TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>>();

			locs = LocationTypeService.getLocationTypesMap();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_mthly_loc_stats_by_dist; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				loc = locs.get(rs.getInt("LOCATION_ID"));

				if (!rpt.containsKey(loc))
					rpt.put(loc, new TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>());

				((TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>) rpt.get(loc)).put(
						new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()),
						new MonthlyIncidentFrequencyBean(new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()), rs.getInt("FREQUENCY_COUNT")));
			}

			return rpt;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("TreeMap<BullyingLocationType, TreeMap<Date, MonthlyIncidentFrequencyBean>> getDistrictMonthlyLocationFrequencyReport(): "
					+ e);
			throw new BullyingException("Can not fetch DistrictLocationFrequencyReport to DB.", e);
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

	public static TreeMap<BullyingBehaviorType, TreeMap<Date, MonthlyIncidentFrequencyBean>> getDistrictMonthlyBehaviorFrequencyReport()
			throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<BullyingBehaviorType, TreeMap<Date, MonthlyIncidentFrequencyBean>> rpt = null;
		BullyingBehaviorType loc = null;

		HashMap<Integer, BullyingBehaviorType> locs = null;

		try {
			rpt = new TreeMap<BullyingBehaviorType, TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>>();

			locs = BullyingBehaviorTypeService.getBullyingBehaviorTypeMap();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_mthly_beh_stats_by_dist; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				loc = locs.get(rs.getInt("TYPE_ID"));

				if (!rpt.containsKey(loc))
					rpt.put(loc, new TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>());

				((TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>) rpt.get(loc)).put(
						new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()),
						new MonthlyIncidentFrequencyBean(new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()), rs.getInt("FREQUENCY_COUNT")));
			}

			return rpt;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("TreeMap<BullyingLocationType, TreeMap<Date, MonthlyIncidentFrequencyBean>> getDistrictMonthlyLocationFrequencyReport(): "
					+ e);
			throw new BullyingException("Can not fetch DistrictLocationFrequencyReport to DB.", e);
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

	public static TreeMap<TimeType, TreeMap<Date, MonthlyIncidentFrequencyBean>> getDistrictMonthlyTimeFrequencyReport()
			throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<TimeType, TreeMap<Date, MonthlyIncidentFrequencyBean>> rpt = null;
		TimeType loc = null;

		HashMap<Integer, TimeType> locs = null;

		try {
			rpt = new TreeMap<TimeType, TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>>();

			locs = TimeTypeService.getTimeTypesMap();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_mthly_when_stats_by_dist; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				loc = locs.get(rs.getInt("TIME_ID"));

				if (!rpt.containsKey(loc))
					rpt.put(loc, new TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>());

				((TreeMap<java.util.Date, MonthlyIncidentFrequencyBean>) rpt.get(loc)).put(
						new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()),
						new MonthlyIncidentFrequencyBean(new java.util.Date(rs.getDate("FREQUENCY_MONTH").getTime()), rs.getInt("FREQUENCY_COUNT")));
			}

			return rpt;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("TreeMap<BullyingLocationType, TreeMap<Date, MonthlyIncidentFrequencyBean>> getDistrictMonthlyLocationFrequencyReport(): "
					+ e);
			throw new BullyingException("Can not fetch DistrictLocationFrequencyReport to DB.", e);
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
}
