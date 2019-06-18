package com.esdnl.roer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelException;
import com.esdnl.dao.DAOUtils;

public class ROERequestDB {

	public static boolean addRequest(ROERequest roer) throws SQLException, PersonnelException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		boolean check = false;
		int req_id = 0;
		Iterator iter = null;
		UnpaidDay unpaid = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.prepareCall("begin ? := awsd_user.roereq_sys.insert_request(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, roer.getPersonnel().getPersonnelID());
			stat.setDate(3, new Date(roer.getFirstDayWorkedDate().getTime()));
			stat.setDate(4, new Date(roer.getLastDayWorkedDate().getTime()));
			stat.setDouble(5, roer.getWeekOneHoursWorked());
			stat.setDouble(6, roer.getWeekTwoHoursWorked());
			stat.setDouble(7, roer.getWeekThreeHoursWorked());
			stat.setDouble(8, roer.getWeekFourHoursWorked());
			stat.setString(9, roer.getReasonForRecordRequest());

			if (roer.getBabyBirthDate() != null)
				stat.setDate(10, new Date(roer.getBabyBirthDate().getTime()));
			else
				stat.setNull(10, OracleTypes.DATE);

			if (roer.getReplacementStartDate() != null)
				stat.setDate(11, new Date(roer.getReplacementStartDate().getTime()));
			else
				stat.setNull(11, OracleTypes.DATE);

			if (roer.getReplacementFinishDate() != null)
				stat.setDate(12, new Date(roer.getReplacementFinishDate().getTime()));
			else
				stat.setNull(12, OracleTypes.DATE);

			stat.setDate(13, new Date(roer.getLastRecordIssuedDate().getTime()));

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				req_id = rs.getInt("REQUEST_ID");
			}
			else {
				req_id = 0;
			}

			rs.close();
			stat.close();

			if (req_id > 0) {
				if (roer.getSickLeaveDates() != null) {
					iter = roer.getSickLeaveDates().iterator();
					if (iter.hasNext()) {
						stat = con.prepareCall("begin awsd_user.roereq_sys.insert_request_sick_date(?, ?); end;");
						while (iter.hasNext()) {
							stat.clearParameters();
							stat.setInt(1, req_id);
							stat.setDate(2, new Date(((java.util.Date) iter.next()).getTime()));
							stat.execute();
						}
						stat.close();
					}
				}

				if (roer.getUnpaidDates() != null) {
					iter = roer.getUnpaidDates().iterator();
					if (iter.hasNext()) {
						stat = con.prepareCall("begin awsd_user.roereq_sys.insert_request_unpaid_date(?, ?, ?); end;");
						while (iter.hasNext()) {
							unpaid = (UnpaidDay) iter.next();
							stat.clearParameters();
							stat.setInt(1, req_id);
							stat.setDate(2, new Date(unpaid.getUnpaidDate().getTime()));
							stat.setInt(3, unpaid.getHoursWorked());
							stat.execute();
						}
						stat.close();
					}
				}

				con.commit();
				check = true;
			}
			else {
				con.rollback();
				check = false;
			}
		}
		catch (SQLException e) {
			con.rollback();
			check = false;
			System.err.println("ROERequestDB.addRequest(ROERequest roer): " + e);
			throw e;
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
		return (check);
	}

	public static boolean completeRequest(int req) throws SQLException, PersonnelException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.prepareCall("begin awsd_user.roereq_sys.complete_request(?); end;");
			stat.setInt(1, req);

			stat.execute();
		}
		catch (SQLException e) {
			con.rollback();
			check = false;
			System.err.println("ROERequestDB.addRequest(ROERequest roer): " + e);
			throw e;
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
		return (check);
	}

	public static boolean deleteROERequest(int req) throws SQLException, PersonnelException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.prepareCall("begin awsd_user.roereq_sys.delete_request(?); end;");
			stat.setInt(1, req);

			stat.execute();
		}
		catch (SQLException e) {
			con.rollback();
			check = false;
			System.err.println("ROERequestDB.addRequest(ROERequest roer): " + e);
			throw e;
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
		return (check);
	}

	public static Vector getROERequests() throws SQLException {

		Vector requests = null;
		ROERequest request = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			requests = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.roereq_sys.get_outstanding_requests; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				request = new ROERequest(rs.getInt("REQUEST_ID"), rs.getInt("PERSONNEL_ID"), new java.util.Date(rs.getDate(
						"REQUEST_DATE").getTime()), (rs.getDate("COMPLETED_DATE") != null) ? new java.util.Date(rs.getDate(
						"COMPLETED_DATE").getTime()) : null, new java.util.Date(rs.getDate("FIRST_DAY_WORKED").getTime()), new java.util.Date(rs.getDate(
						"LAST_DAY_WORKED").getTime()), rs.getDouble("WEEK1_HOURS"), rs.getDouble("WEEK2_HOURS"), rs.getDouble("WEEK3_HOURS"), rs.getDouble("WEEK4_HOURS"), rs.getString("REASON_FOR_RECORD"), (rs.getDate("BABY_BORN_DATE") != null) ? new java.util.Date(rs.getDate(
						"BABY_BORN_DATE").getTime())
						: null, (rs.getDate("REPLACEMENT_START_DATE") != null) ? new java.util.Date(rs.getDate(
						"REPLACEMENT_START_DATE").getTime()) : null, (rs.getDate("REPLACEMENT_FINISH_DATE") != null) ? new java.util.Date(rs.getDate(
						"REPLACEMENT_FINISH_DATE").getTime())
						: null, new java.util.Date(rs.getDate("LAST_RECORD_ISSUED_DATE").getTime()));

				requests.add(request);
			}
		}
		catch (SQLException e) {
			System.err.println("ROERequestDB.getROERequests(): " + e);
			throw e;
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
		return requests;
	}

	public static Vector getROERequests(Personnel p) throws SQLException {

		Vector requests = null;
		ROERequest request = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			requests = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.roereq_sys.get_os_requests_emp(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				request = new ROERequest(rs.getInt("REQUEST_ID"), rs.getInt("PERSONNEL_ID"), new java.util.Date(rs.getDate(
						"REQUEST_DATE").getTime()), (rs.getDate("COMPLETED_DATE") != null) ? new java.util.Date(rs.getDate(
						"COMPLETED_DATE").getTime()) : null, new java.util.Date(rs.getDate("FIRST_DAY_WORKED").getTime()), new java.util.Date(rs.getDate(
						"LAST_DAY_WORKED").getTime()), rs.getDouble("WEEK1_HOURS"), rs.getDouble("WEEK2_HOURS"), rs.getDouble("WEEK3_HOURS"), rs.getDouble("WEEK4_HOURS"), rs.getString("REASON_FOR_RECORD"), (rs.getDate("BABY_BORN_DATE") != null) ? new java.util.Date(rs.getDate(
						"BABY_BORN_DATE").getTime())
						: null, (rs.getDate("REPLACEMENT_START_DATE") != null) ? new java.util.Date(rs.getDate(
						"REPLACEMENT_START_DATE").getTime()) : null, (rs.getDate("REPLACEMENT_FINISH_DATE") != null) ? new java.util.Date(rs.getDate(
						"REPLACEMENT_FINISH_DATE").getTime())
						: null, new java.util.Date(rs.getDate("LAST_RECORD_ISSUED_DATE").getTime()));

				requests.add(request);
			}
		}
		catch (SQLException e) {
			System.err.println("ROERequestDB.getROERequests(Personnel p): " + e);
			e.printStackTrace(System.err);
			throw e;
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
		return requests;
	}

	public static Vector getROERequests(java.util.Date dt) throws SQLException {

		Vector requests = null;
		ROERequest request = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			requests = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.roereq_sys.get_os_requests_dt(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setDate(2, new Date(dt.getTime()));
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				request = new ROERequest(rs.getInt("REQUEST_ID"), rs.getInt("PERSONNEL_ID"), new java.util.Date(rs.getDate(
						"REQUEST_DATE").getTime()), (rs.getDate("COMPLETED_DATE") != null) ? new java.util.Date(rs.getDate(
						"COMPLETED_DATE").getTime()) : null, new java.util.Date(rs.getDate("FIRST_DAY_WORKED").getTime()), new java.util.Date(rs.getDate(
						"LAST_DAY_WORKED").getTime()), rs.getDouble("WEEK1_HOURS"), rs.getDouble("WEEK2_HOURS"), rs.getDouble("WEEK3_HOURS"), rs.getDouble("WEEK4_HOURS"), rs.getString("REASON_FOR_RECORD"), (rs.getDate("BABY_BORN_DATE") != null) ? new java.util.Date(rs.getDate(
						"BABY_BORN_DATE").getTime())
						: null, (rs.getDate("REPLACEMENT_START_DATE") != null) ? new java.util.Date(rs.getDate(
						"REPLACEMENT_START_DATE").getTime()) : null, (rs.getDate("REPLACEMENT_FINISH_DATE") != null) ? new java.util.Date(rs.getDate(
						"REPLACEMENT_FINISH_DATE").getTime())
						: null, new java.util.Date(rs.getDate("LAST_RECORD_ISSUED_DATE").getTime()));

				requests.add(request);
			}
		}
		catch (SQLException e) {
			System.err.println("ROERequestDB.getROERequests(java.util.Date dt): " + e);
			e.printStackTrace(System.err);
			throw e;
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
		return requests;
	}

	public static ROERequest getROERequest(int rid) throws SQLException {

		ROERequest request = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.roereq_sys.get_request(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, rid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				request = new ROERequest(rs.getInt("REQUEST_ID"), rs.getInt("PERSONNEL_ID"), new java.util.Date(rs.getDate(
						"REQUEST_DATE").getTime()), (rs.getDate("COMPLETED_DATE") != null) ? new java.util.Date(rs.getDate(
						"COMPLETED_DATE").getTime()) : null, new java.util.Date(rs.getDate("FIRST_DAY_WORKED").getTime()), new java.util.Date(rs.getDate(
						"LAST_DAY_WORKED").getTime()), rs.getDouble("WEEK1_HOURS"), rs.getDouble("WEEK2_HOURS"), rs.getDouble("WEEK3_HOURS"), rs.getDouble("WEEK4_HOURS"), rs.getString("REASON_FOR_RECORD"), (rs.getDate("BABY_BORN_DATE") != null) ? new java.util.Date(rs.getDate(
						"BABY_BORN_DATE").getTime())
						: null, (rs.getDate("REPLACEMENT_START_DATE") != null) ? new java.util.Date(rs.getDate(
						"REPLACEMENT_START_DATE").getTime()) : null, (rs.getDate("REPLACEMENT_FINISH_DATE") != null) ? new java.util.Date(rs.getDate(
						"REPLACEMENT_FINISH_DATE").getTime())
						: null, new java.util.Date(rs.getDate("LAST_RECORD_ISSUED_DATE").getTime()));
			}
			else
				request = null;
		}
		catch (SQLException e) {
			System.err.println("ROERequestDB.getROERequest(int rid): " + e);
			e.printStackTrace(System.err);
			throw e;
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
		return request;
	}

	public static Vector getROERequestSickDates(ROERequest request) throws SQLException {

		Vector sdates = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			sdates = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.roereq_sys.get_request_sick_dates(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, request.getRequestID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				sdates.add(new java.util.Date(rs.getDate("SICK_LEAVE_DATE").getTime()));
			}
		}
		catch (SQLException e) {
			System.err.println("ROERequestDB.getROERequestSickDates(ROERequest request): " + e);
			e.printStackTrace(System.err);
			throw e;
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
		return sdates;
	}

	public static Vector getROERequestUnpaidDates(ROERequest request) throws SQLException {

		Vector udates = null;
		UnpaidDay ud = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			udates = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.roereq_sys.get_request_unpaid_dates(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, request.getRequestID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				udates.add(new UnpaidDay(new java.util.Date(rs.getDate("UNPAID_DATE").getTime()), rs.getInt("HRS")));
			}
		}
		catch (SQLException e) {
			System.err.println("ROERequestDB.getROERequests(): " + e);
			throw e;
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
		return udates;
	}
}