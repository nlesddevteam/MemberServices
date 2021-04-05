package com.esdnl.personnel.jobs.reports;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.esdnl.dao.DAOUtils;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class ReportsManager {

	public static Collection<PositionPlannerStaffingExcelReport.RedundanciesRecord> getPositionPlannerRedundanciesBySchoolYear(String schoolYear)
			throws ReportException {

		Collection<PositionPlannerStaffingExcelReport.RedundanciesRecord> records = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_reports_pkg.get_pp_redundancies(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				records.add(new PositionPlannerStaffingExcelReport.RedundanciesRecord(rs));
			}

		}
		catch (SQLException e) {
			System.err.println(
					"Collection<PositionPlannerRedundanciesExcellReport.ReportRecord> getPositionPlannerRedundanciesBySchoolYear(): "
							+ e);
			throw new ReportException("Can not extract report from DB.", e);
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

		return records;
	}

	public static Collection<PositionPlannerStaffingExcelReport.AllocationsRecord> getPositionPlannerAllocationsBySchoolYear(String schoolYear)
			throws ReportException {

		Collection<PositionPlannerStaffingExcelReport.AllocationsRecord> records = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_reports_pkg.get_pp_allocations(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				records.add(new PositionPlannerStaffingExcelReport.AllocationsRecord(rs));
			}

		}
		catch (SQLException e) {
			System.err.println(
					"Collection<PositionPlannerStaffingExcelReport.AllocationsRecord> getPositionPlannerAllocationsBySchoolYear(String schoolYear): "
							+ e);
			throw new ReportException("Can not extract report from DB.", e);
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

		return records;
	}

	public static Collection<PositionPlannerStaffingExcelReport.VacanciesRecord> getPositionPlannerVacanciesBySchoolYear(String schoolYear)
			throws ReportException {

		Collection<PositionPlannerStaffingExcelReport.VacanciesRecord> records = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_reports_pkg.get_pp_vacancies(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				records.add(new PositionPlannerStaffingExcelReport.VacanciesRecord(rs));
			}

		}
		catch (SQLException e) {
			System.err.println(
					"Collection<PositionPlannerStaffingExcelReport.VacanciesRecord> getPositionPlannerVacanciesBySchoolYear(String schoolYear): "
							+ e);
			throw new ReportException("Can not extract report from DB.", e);
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

		return records;
	}

	public static Collection<PositionPlannerStaffingExcelReport.PermanentsRecord> getPositionPlannerPermanentsBySchoolYear(String schoolYear)
			throws ReportException {

		Collection<PositionPlannerStaffingExcelReport.PermanentsRecord> records = new ArrayList<>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_reports_pkg.get_pp_permanents(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				records.add(new PositionPlannerStaffingExcelReport.PermanentsRecord(rs));
			}

		}
		catch (SQLException e) {
			System.err.println(
					"Collection<PositionPlannerStaffingExcelReport.PermanentsRecord> getPositionPlannerPermanentsBySchoolYear(String schoolYear): "
							+ e);
			throw new ReportException("Can not extract report from DB.", e);
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

		return records;
	}

}
