package com.esdnl.mrs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.dao.DAOUtils;

public class MaintenanceRequestDB {

	public static MaintenanceRequest getMaintenanceRequest(int request_id) throws RequestException {

		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_maint_request(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, request_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				req = new MaintenanceRequest(rs.getInt("REQUEST_ID"), new RequestType(rs.getString("TYPE_ID")), new StatusCode(rs.getString("CUR_STATUS")), rs.getString("PROBLEM_DESC"), new java.util.Date(rs.getDate(
						"REQUEST_DATE").getTime()), rs.getString("ROOM_NAME_NUMBER"), rs.getInt("REQUESTED_BY_ID"), rs.getInt("SCHOOL_ID"), rs.getInt("SCHOOL_PRIORITY"), ((rs.getString("CATEGORY_ID") != null) ? new RequestCategory(rs.getString("CATEGORY_ID"))
						: null), ((rs.getString("CAPITAL_TYPE_ID") != null) ? new CapitalType(rs.getString("CAPITAL_TYPE_ID"))
						: null), rs.getInt("CAPITAL_PRIORITY"), ((rs.getString("FUNDING_APPROVED") != null) ? ((rs.getString("FUNDING_APPROVED").equalsIgnoreCase("Y")) ? true
						: false)
						: false), ((rs.getDate("DATE_REVIEWED") != null) ? new java.util.Date(rs.getDate("DATE_REVIEWED").getTime())
						: null), rs.getInt("REVIEWED_BY"), ((rs.getDate("DATE_ASSIGNED") != null) ? new java.util.Date(rs.getDate(
						"DATE_ASSIGNED").getTime()) : null), ((rs.getDate("DATE_COMPLETED") != null) ? new java.util.Date(rs.getDate(
						"DATE_COMPLETED").getTime())
						: null), ((rs.getDate("DATE_CANCELLED") != null) ? new java.util.Date(rs.getDate("DATE_CANCELLED").getTime())
						: null), rs.getDouble("ESTIMATE_COST"), rs.getDouble("ACTUAL_COST"));

			}
			else {
				req = null;
			}
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getMaintenanceRequest(int): " + e);
			throw new RequestException("Can not extract maintenance request notes from DB: " + e);
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
		return req;
	}

	public static MaintenanceRequest getMaintenanceRequest(int request_id, Personnel reviewed_by) throws RequestException {

		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_maint_request_admin(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, request_id);
			stat.setInt(3, reviewed_by.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				req = new MaintenanceRequest(rs.getInt("REQUEST_ID"), new RequestType(rs.getString("TYPE_ID")), new StatusCode(rs.getString("CUR_STATUS")), rs.getString("PROBLEM_DESC"), new java.util.Date(rs.getDate(
						"REQUEST_DATE").getTime()), rs.getString("ROOM_NAME_NUMBER"), rs.getInt("REQUESTED_BY_ID"), rs.getInt("SCHOOL_ID"), rs.getInt("SCHOOL_PRIORITY"), ((rs.getString("CATEGORY_ID") != null) ? new RequestCategory(rs.getString("CATEGORY_ID"))
						: null), ((rs.getString("CAPITAL_TYPE_ID") != null) ? new CapitalType(rs.getString("CAPITAL_TYPE_ID"))
						: null), rs.getInt("CAPITAL_PRIORITY"), ((rs.getString("FUNDING_APPROVED") != null) ? ((rs.getString("FUNDING_APPROVED").equalsIgnoreCase("Y")) ? true
						: false)
						: false), ((rs.getDate("DATE_REVIEWED") != null) ? new java.util.Date(rs.getDate("DATE_REVIEWED").getTime())
						: null), rs.getInt("REVIEWED_BY"), ((rs.getDate("DATE_ASSIGNED") != null) ? new java.util.Date(rs.getDate(
						"DATE_ASSIGNED").getTime()) : null), ((rs.getDate("DATE_COMPLETED") != null) ? new java.util.Date(rs.getDate(
						"DATE_COMPLETED").getTime())
						: null), ((rs.getDate("DATE_CANCELLED") != null) ? new java.util.Date(rs.getDate("DATE_CANCELLED").getTime())
						: null), rs.getDouble("ESTIMATE_COST"), rs.getDouble("ACTUAL_COST"));
			}
			else {
				req = null;
			}
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getMaintenanceRequest(int): " + e);
			throw new RequestException("Can not extract maintenance request notes from DB: " + e);
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
		return req;
	}

	public static Vector<MaintenanceRequest> getMaintenanceRequests() throws RequestException {

		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			reqs = new Vector<MaintenanceRequest>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_all_maint_request; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = new MaintenanceRequest(rs.getInt("REQUEST_ID"), new RequestType(rs.getString("TYPE_ID")), new StatusCode(rs.getString("CUR_STATUS")), rs.getString("PROBLEM_DESC"), new java.util.Date(rs.getDate(
						"REQUEST_DATE").getTime()), rs.getString("ROOM_NAME_NUMBER"), rs.getInt("REQUESTED_BY_ID"), rs.getInt("SCHOOL_ID"), rs.getInt("SCHOOL_PRIORITY"), ((rs.getString("CATEGORY_ID") != null) ? new RequestCategory(rs.getString("CATEGORY_ID"))
						: null), ((rs.getString("CAPITAL_TYPE_ID") != null) ? new CapitalType(rs.getString("CAPITAL_TYPE_ID"))
						: null), rs.getInt("CAPITAL_PRIORITY"), ((rs.getString("FUNDING_APPROVED") != null) ? ((rs.getString("FUNDING_APPROVED").equalsIgnoreCase("Y")) ? true
						: false)
						: false), ((rs.getDate("DATE_REVIEWED") != null) ? new java.util.Date(rs.getDate("DATE_REVIEWED").getTime())
						: null), rs.getInt("REVIEWED_BY"), ((rs.getDate("DATE_ASSIGNED") != null) ? new java.util.Date(rs.getDate(
						"DATE_ASSIGNED").getTime()) : null), ((rs.getDate("DATE_COMPLETED") != null) ? new java.util.Date(rs.getDate(
						"DATE_COMPLETED").getTime())
						: null), ((rs.getDate("DATE_CANCELLED") != null) ? new java.util.Date(rs.getDate("DATE_CANCELLED").getTime())
						: null), rs.getDouble("ESTIMATE_COST"), rs.getDouble("ACTUAL_COST"));

				reqs.add(req);
			}
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getMaintenanceRequest(int): " + e);
			throw new RequestException("Can not extract maintenance request notes from DB: " + e);
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
		return reqs;
	}

	public static Vector<MaintenanceRequest> getMaintenanceRequests(School s) throws RequestException {

		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			reqs = new Vector<MaintenanceRequest>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_req_by_school(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s.getSchoolID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = new MaintenanceRequest(rs.getInt("REQUEST_ID"), new RequestType(rs.getString("TYPE_ID")), new StatusCode(rs.getString("CUR_STATUS")), rs.getString("PROBLEM_DESC"), new java.util.Date(rs.getDate(
						"REQUEST_DATE").getTime()), rs.getString("ROOM_NAME_NUMBER"), rs.getInt("REQUESTED_BY_ID"), rs.getInt("SCHOOL_ID"), rs.getInt("SCHOOL_PRIORITY"), ((rs.getString("CATEGORY_ID") != null) ? new RequestCategory(rs.getString("CATEGORY_ID"))
						: null), ((rs.getString("CAPITAL_TYPE_ID") != null) ? new CapitalType(rs.getString("CAPITAL_TYPE_ID"))
						: null), rs.getInt("CAPITAL_PRIORITY"), ((rs.getString("FUNDING_APPROVED") != null) ? ((rs.getString("FUNDING_APPROVED").equalsIgnoreCase("Y")) ? true
						: false)
						: false), ((rs.getDate("DATE_REVIEWED") != null) ? new java.util.Date(rs.getDate("DATE_REVIEWED").getTime())
						: null), rs.getInt("REVIEWED_BY"), ((rs.getDate("DATE_ASSIGNED") != null) ? new java.util.Date(rs.getDate(
						"DATE_ASSIGNED").getTime()) : null), ((rs.getDate("DATE_COMPLETED") != null) ? new java.util.Date(rs.getDate(
						"DATE_COMPLETED").getTime())
						: null), ((rs.getDate("DATE_CANCELLED") != null) ? new java.util.Date(rs.getDate("DATE_CANCELLED").getTime())
						: null), rs.getDouble("ESTIMATE_COST"), rs.getDouble("ACTUAL_COST"));

				reqs.add(req);
			}
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getMaintenanceRequest(int): " + e);
			throw new RequestException("Can not extract maintenance request notes from DB: " + e);
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
		return reqs;
	}

	public static Vector<MaintenanceRequest> getMaintenanceRequests(StatusCode status) throws RequestException {

		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			reqs = new Vector<MaintenanceRequest>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_req_by_status(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, status.getStatusCodeID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = new MaintenanceRequest(rs.getInt("REQUEST_ID"), new RequestType(rs.getString("TYPE_ID")), new StatusCode(rs.getString("CUR_STATUS")), rs.getString("PROBLEM_DESC"), new java.util.Date(rs.getDate(
						"REQUEST_DATE").getTime()), rs.getString("ROOM_NAME_NUMBER"), rs.getInt("REQUESTED_BY_ID"), rs.getInt("SCHOOL_ID"), rs.getInt("SCHOOL_PRIORITY"), ((rs.getString("CATEGORY_ID") != null) ? new RequestCategory(rs.getString("CATEGORY_ID"))
						: null), ((rs.getString("CAPITAL_TYPE_ID") != null) ? new CapitalType(rs.getString("CAPITAL_TYPE_ID"))
						: null), rs.getInt("CAPITAL_PRIORITY"), ((rs.getString("FUNDING_APPROVED") != null) ? ((rs.getString("FUNDING_APPROVED").equalsIgnoreCase("Y")) ? true
						: false)
						: false), ((rs.getDate("DATE_REVIEWED") != null) ? new java.util.Date(rs.getDate("DATE_REVIEWED").getTime())
						: null), rs.getInt("REVIEWED_BY"), ((rs.getDate("DATE_ASSIGNED") != null) ? new java.util.Date(rs.getDate(
						"DATE_ASSIGNED").getTime()) : null), ((rs.getDate("DATE_COMPLETED") != null) ? new java.util.Date(rs.getDate(
						"DATE_COMPLETED").getTime())
						: null), ((rs.getDate("DATE_CANCELLED") != null) ? new java.util.Date(rs.getDate("DATE_CANCELLED").getTime())
						: null), rs.getDouble("ESTIMATE_COST"), rs.getDouble("ACTUAL_COST"));

				reqs.add(req);
			}
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getMaintenanceRequest(int): " + e);
			throw new RequestException("Can not extract maintenance request notes from DB: " + e);
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
		return reqs;
	}

	public static Vector<MaintenanceRequest> getOutstandingMaintenanceRequests() throws RequestException {

		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			reqs = new Vector<MaintenanceRequest>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_all_outstanding_req; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = new MaintenanceRequest(rs.getInt("REQUEST_ID"), new RequestType(rs.getString("TYPE_ID")), new StatusCode(rs.getString("CUR_STATUS")), rs.getString("PROBLEM_DESC"), new java.util.Date(rs.getDate(
						"REQUEST_DATE").getTime()), rs.getString("ROOM_NAME_NUMBER"), rs.getInt("REQUESTED_BY_ID"), rs.getInt("SCHOOL_ID"), rs.getInt("SCHOOL_PRIORITY"), ((rs.getString("CATEGORY_ID") != null) ? new RequestCategory(rs.getString("CATEGORY_ID"))
						: null), ((rs.getString("CAPITAL_TYPE_ID") != null) ? new CapitalType(rs.getString("CAPITAL_TYPE_ID"))
						: null), rs.getInt("CAPITAL_PRIORITY"), ((rs.getString("FUNDING_APPROVED") != null) ? ((rs.getString("FUNDING_APPROVED").equalsIgnoreCase("Y")) ? true
						: false)
						: false), ((rs.getDate("DATE_REVIEWED") != null) ? new java.util.Date(rs.getDate("DATE_REVIEWED").getTime())
						: null), rs.getInt("REVIEWED_BY"), ((rs.getDate("DATE_ASSIGNED") != null) ? new java.util.Date(rs.getDate(
						"DATE_ASSIGNED").getTime()) : null), ((rs.getDate("DATE_COMPLETED") != null) ? new java.util.Date(rs.getDate(
						"DATE_COMPLETED").getTime())
						: null), ((rs.getDate("DATE_CANCELLED") != null) ? new java.util.Date(rs.getDate("DATE_CANCELLED").getTime())
						: null), rs.getDouble("ESTIMATE_COST"), rs.getDouble("ACTUAL_COST"));

				reqs.add(req);
			}
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getOutstandingMaintenanceRequests(): " + e);
			throw new RequestException("Can not extract maintenance request from DB: " + e);
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
		return reqs;
	}

	public static ArrayList<MaintenanceRequest[]> getOutstandingMaintenanceRequestsPages(int max_per_page)
			throws RequestException {

		ArrayList<MaintenanceRequest[]> pages = null;
		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		int req_cnt = 0;

		int cnt = 0;

		try {
			pages = new ArrayList<MaintenanceRequest[]>(15);
			reqs = new Vector<MaintenanceRequest>(max_per_page);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_all_outstanding_req; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = createMaintenanceRequestBean(rs);

				if (++req_cnt > max_per_page) {
					pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));
					reqs = new Vector<MaintenanceRequest>(max_per_page);
					reqs.add(req);
					req_cnt = 1;
				}
				else
					reqs.add(req);

				cnt++;
			}

			if (reqs.size() > 0)
				pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));

			System.out.println("cnt: " + cnt);
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getOutstandingMaintenanceRequests(): " + e);
			throw new RequestException("Can not extract maintenance request from DB: " + e);
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
		return pages;
	}

	public static ArrayList<MaintenanceRequest[]> getOutstandingRegionalMaintenanceRequestsPages(Personnel who,
																																																int max_per_page)
			throws RequestException {

		ArrayList<MaintenanceRequest[]> pages = null;
		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		int req_cnt = 0;

		int cnt = 0;

		try {
			pages = new ArrayList<MaintenanceRequest[]>(15);
			reqs = new Vector<MaintenanceRequest>(max_per_page);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_all_outstanding_region_req(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, who.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = createMaintenanceRequestBean(rs);

				if (++req_cnt > max_per_page) {
					pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));
					reqs = new Vector<MaintenanceRequest>(max_per_page);
					reqs.add(req);
					req_cnt = 1;
				}
				else
					reqs.add(req);

				cnt++;
			}

			if (reqs.size() > 0)
				pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));

			System.out.println("cnt: " + cnt);
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getOutstandingMaintenanceRequests(): " + e);
			throw new RequestException("Can not extract maintenance request from DB: " + e);
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
		return pages;
	}

	public static ArrayList<MaintenanceRequest[]> getOutstandingWorkOrderMaintenanceRequestsPages(Personnel who,
																																																int max_per_page)
			throws RequestException {

		ArrayList<MaintenanceRequest[]> pages = null;
		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		int req_cnt = 0;

		int cnt = 0;

		try {
			pages = new ArrayList<MaintenanceRequest[]>(15);
			reqs = new Vector<MaintenanceRequest>(max_per_page);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_outstanding_workorders(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, who.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = createMaintenanceRequestBean(rs);

				if (++req_cnt > max_per_page) {
					pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));
					reqs = new Vector<MaintenanceRequest>(max_per_page);
					reqs.add(req);
					req_cnt = 1;
				}
				else
					reqs.add(req);

				cnt++;
			}

			if (reqs.size() > 0)
				pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));

			System.out.println("cnt: " + cnt);
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getOutstandingMaintenanceRequests(): " + e);
			throw new RequestException("Can not extract maintenance request from DB: " + e);
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
		return pages;
	}

	public static ArrayList<MaintenanceRequest[]> getOutstandingMaintenanceRequestsPagesByRequestType(Personnel who,
																																																		String type_id,
																																																		int max_per_page)
			throws RequestException {

		ArrayList<MaintenanceRequest[]> pages = null;
		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		int req_cnt = 0;

		int cnt = 0;

		try {
			pages = new ArrayList<MaintenanceRequest[]>(15);
			reqs = new Vector<MaintenanceRequest>(max_per_page);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_osreq_by_req_type(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, who.getPersonnelID());
			stat.setString(3, type_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = createMaintenanceRequestBean(rs);

				if (++req_cnt > max_per_page) {
					pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));
					reqs = new Vector<MaintenanceRequest>(max_per_page);
					reqs.add(req);
					req_cnt = 1;
				}
				else
					reqs.add(req);

				cnt++;
			}

			if (reqs.size() > 0)
				pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));

			System.out.println("cnt: " + cnt);
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getOutstandingMaintenanceRequests(): " + e);
			throw new RequestException("Can not extract maintenance request from DB: " + e);
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
		return pages;
	}

	public static ArrayList<MaintenanceRequest[]> getOutstandingMaintenanceRequestsPagesByRequestCategory(Personnel who,
																																																				String type_id,
																																																				int max_per_page)
			throws RequestException {

		ArrayList<MaintenanceRequest[]> pages = null;
		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		int req_cnt = 0;

		int cnt = 0;

		try {
			pages = new ArrayList<MaintenanceRequest[]>(15);
			reqs = new Vector<MaintenanceRequest>(max_per_page);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_osreq_by_req_cat(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, who.getPersonnelID());
			stat.setString(3, type_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = createMaintenanceRequestBean(rs);

				if (++req_cnt > max_per_page) {
					pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));
					reqs = new Vector<MaintenanceRequest>(max_per_page);
					reqs.add(req);
					req_cnt = 1;
				}
				else
					reqs.add(req);

				cnt++;
			}

			if (reqs.size() > 0)
				pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));

			System.out.println("cnt: " + cnt);
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getOutstandingMaintenanceRequests(): " + e);
			throw new RequestException("Can not extract maintenance request from DB: " + e);
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
		return pages;
	}

	public static ArrayList<MaintenanceRequest[]> getOutstandingMaintenanceRequests(School school)
			throws RequestException {

		ArrayList<MaintenanceRequest[]> pages = null;
		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			pages = new ArrayList<MaintenanceRequest[]>(1);
			reqs = new Vector<MaintenanceRequest>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_outstanding_req_by_school(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, school.getSchoolID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = createMaintenanceRequestBean(rs);
				reqs.add(req);
			}

			pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getMaintenanceRequest(int): " + e);
			throw new RequestException("Can not extract maintenance request notes from DB: " + e);
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
		return pages;
	}

	public static ArrayList<MaintenanceRequest[]> getOutstandingMaintenanceRequestsPagesBySchool(int s_id,
																																																int max_per_page)
			throws RequestException {

		ArrayList<MaintenanceRequest[]> pages = null;
		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		int req_cnt = 0;

		int cnt = 0;

		try {
			pages = new ArrayList<MaintenanceRequest[]>(15);
			reqs = new Vector<MaintenanceRequest>(max_per_page);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_outstanding_req_by_school(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s_id);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = createMaintenanceRequestBean(rs);

				if (++req_cnt > max_per_page) {
					pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));
					reqs = new Vector<MaintenanceRequest>(max_per_page);
					reqs.add(req);
					req_cnt = 1;
				}
				else
					reqs.add(req);

				cnt++;
			}

			if (reqs.size() > 0)
				pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));

			System.out.println("cnt: " + cnt);
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getOutstandingMaintenanceRequests(): " + e);
			throw new RequestException("Can not extract maintenance request from DB: " + e);
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
		return pages;
	}

	public static Vector<MaintenanceRequest> getMaintenanceRequests(School school, StatusCode status)
			throws RequestException {

		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			reqs = new Vector<MaintenanceRequest>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_req_by_status_school(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, school.getSchoolID());
			stat.setString(3, status.getStatusCodeID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = new MaintenanceRequest(rs.getInt("REQUEST_ID"), new RequestType(rs.getString("TYPE_ID")), new StatusCode(rs.getString("CUR_STATUS")), rs.getString("PROBLEM_DESC"), new java.util.Date(rs.getDate(
						"REQUEST_DATE").getTime()), rs.getString("ROOM_NAME_NUMBER"), rs.getInt("REQUESTED_BY_ID"), rs.getInt("SCHOOL_ID"), rs.getInt("SCHOOL_PRIORITY"), ((rs.getString("CATEGORY_ID") != null) ? new RequestCategory(rs.getString("CATEGORY_ID"))
						: null), ((rs.getString("CAPITAL_TYPE_ID") != null) ? new CapitalType(rs.getString("CAPITAL_TYPE_ID"))
						: null), rs.getInt("CAPITAL_PRIORITY"), ((rs.getString("FUNDING_APPROVED") != null) ? ((rs.getString("FUNDING_APPROVED").equalsIgnoreCase("Y")) ? true
						: false)
						: false), ((rs.getDate("DATE_REVIEWED") != null) ? new java.util.Date(rs.getDate("DATE_REVIEWED").getTime())
						: null), rs.getInt("REVIEWED_BY"), ((rs.getDate("DATE_ASSIGNED") != null) ? new java.util.Date(rs.getDate(
						"DATE_ASSIGNED").getTime()) : null), ((rs.getDate("DATE_COMPLETED") != null) ? new java.util.Date(rs.getDate(
						"DATE_COMPLETED").getTime())
						: null), ((rs.getDate("DATE_CANCELLED") != null) ? new java.util.Date(rs.getDate("DATE_CANCELLED").getTime())
						: null), rs.getDouble("ESTIMATE_COST"), rs.getDouble("ACTUAL_COST"));

				reqs.add(req);
			}
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getMaintenanceRequest(int): " + e);
			throw new RequestException("Can not extract maintenance request notes from DB: " + e);
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
		return reqs;
	}

	public static ArrayList<MaintenanceRequest[]> getOutstandingMaintenanceRequestsPagesByStatusSchool(String code,
																																																			School school,
																																																			int max_per_page)
			throws RequestException {

		ArrayList<MaintenanceRequest[]> pages = null;
		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		int req_cnt = 0;

		int cnt = 0;

		try {
			pages = new ArrayList<MaintenanceRequest[]>(15);
			reqs = new Vector<MaintenanceRequest>(max_per_page);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_osreq_by_status_school(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, code);
			stat.setInt(3, school.getSchoolID());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = createMaintenanceRequestBean(rs);

				if (++req_cnt > max_per_page) {
					pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));
					reqs = new Vector<MaintenanceRequest>(max_per_page);
					reqs.add(req);
					req_cnt = 1;
				}
				else
					reqs.add(req);

				cnt++;
			}

			if (reqs.size() > 0)
				pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));

			System.out.println("cnt: " + cnt);
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getOutstandingMaintenanceRequests(): " + e);
			throw new RequestException("Can not extract maintenance request from DB: " + e);
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
		return pages;
	}

	public static ArrayList<MaintenanceRequest[]> getOutstandingMaintenanceRequestsPagesByCategorySchool(	String category_id,
																																																				School school,
																																																				int max_per_page)
			throws RequestException {

		ArrayList<MaintenanceRequest[]> pages = null;
		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		int req_cnt = 0;

		int cnt = 0;

		try {
			pages = new ArrayList<MaintenanceRequest[]>(15);
			reqs = new Vector<MaintenanceRequest>(max_per_page);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_osreq_by_category_school(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, category_id);
			stat.setInt(3, school.getSchoolID());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = createMaintenanceRequestBean(rs);

				if (++req_cnt > max_per_page) {
					pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));
					reqs = new Vector<MaintenanceRequest>(max_per_page);
					reqs.add(req);
					req_cnt = 1;
				}
				else
					reqs.add(req);

				cnt++;
			}

			if (reqs.size() > 0)
				pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));

			System.out.println("cnt: " + cnt);
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getOutstandingMaintenanceRequests(): " + e);
			throw new RequestException("Can not extract maintenance request from DB: " + e);
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
		return pages;
	}

	public static ArrayList<MaintenanceRequest[]> getOutstandingMaintenanceRequestsPagesByStatus(Personnel who,
																																																String code,
																																																int max_per_page)
			throws RequestException {

		ArrayList<MaintenanceRequest[]> pages = null;
		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		int req_cnt = 0;

		int cnt = 0;

		try {
			pages = new ArrayList<MaintenanceRequest[]>(15);
			reqs = new Vector<MaintenanceRequest>(max_per_page);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_osreq_by_status(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, who.getPersonnelID());
			stat.setString(3, code);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = createMaintenanceRequestBean(rs);

				if (++req_cnt > max_per_page) {
					pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));
					reqs = new Vector<MaintenanceRequest>(max_per_page);
					reqs.add(req);
					req_cnt = 1;
				}
				else
					reqs.add(req);

				cnt++;
			}

			if (reqs.size() > 0)
				pages.add((MaintenanceRequest[]) reqs.toArray(new MaintenanceRequest[0]));

			System.out.println("cnt: " + cnt);
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getOutstandingMaintenanceRequests(): " + e);
			throw new RequestException("Can not extract maintenance request from DB: " + e);
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
		return pages;
	}

	public static Vector<MaintenanceRequest> getMaintenanceRequests(Personnel p) throws RequestException {

		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			reqs = new Vector<MaintenanceRequest>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_req_by_requester(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = new MaintenanceRequest(rs.getInt("REQUEST_ID"), new RequestType(rs.getString("TYPE_ID")), new StatusCode(rs.getString("CUR_STATUS")), rs.getString("PROBLEM_DESC"), new java.util.Date(rs.getDate(
						"REQUEST_DATE").getTime()), rs.getString("ROOM_NAME_NUMBER"), rs.getInt("REQUESTED_BY_ID"), rs.getInt("SCHOOL_ID"), rs.getInt("SCHOOL_PRIORITY"), ((rs.getString("CATEGORY_ID") != null) ? new RequestCategory(rs.getString("CATEGORY_ID"))
						: null), ((rs.getString("CAPITAL_TYPE_ID") != null) ? new CapitalType(rs.getString("CAPITAL_TYPE_ID"))
						: null), rs.getInt("CAPITAL_PRIORITY"), ((rs.getString("FUNDING_APPROVED") != null) ? ((rs.getString("FUNDING_APPROVED").equalsIgnoreCase("Y")) ? true
						: false)
						: false), ((rs.getDate("DATE_REVIEWED") != null) ? new java.util.Date(rs.getDate("DATE_REVIEWED").getTime())
						: null), rs.getInt("REVIEWED_BY"), ((rs.getDate("DATE_ASSIGNED") != null) ? new java.util.Date(rs.getDate(
						"DATE_ASSIGNED").getTime()) : null), ((rs.getDate("DATE_COMPLETED") != null) ? new java.util.Date(rs.getDate(
						"DATE_COMPLETED").getTime())
						: null), ((rs.getDate("DATE_CANCELLED") != null) ? new java.util.Date(rs.getDate("DATE_CANCELLED").getTime())
						: null), rs.getDouble("ESTIMATE_COST"), rs.getDouble("ACTUAL_COST"));

				reqs.add(req);
			}
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getMaintenanceRequest(int): " + e);
			throw new RequestException("Can not extract maintenance request notes from DB: " + e);
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
		return reqs;
	}

	public static Vector<MaintenanceRequest> getMaintenanceRequests(Personnel p, StatusCode status)
			throws RequestException {

		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			reqs = new Vector<MaintenanceRequest>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_req_by_requester_status(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.setString(3, status.getStatusCodeID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = new MaintenanceRequest(rs.getInt("REQUEST_ID"), new RequestType(rs.getString("TYPE_ID")), new StatusCode(rs.getString("CUR_STATUS")), rs.getString("PROBLEM_DESC"), new java.util.Date(rs.getDate(
						"REQUEST_DATE").getTime()), rs.getString("ROOM_NAME_NUMBER"), rs.getInt("REQUESTED_BY_ID"), rs.getInt("SCHOOL_ID"), rs.getInt("SCHOOL_PRIORITY"), ((rs.getString("CATEGORY_ID") != null) ? new RequestCategory(rs.getString("CATEGORY_ID"))
						: null), ((rs.getString("CAPITAL_TYPE_ID") != null) ? new CapitalType(rs.getString("CAPITAL_TYPE_ID"))
						: null), rs.getInt("CAPITAL_PRIORITY"), ((rs.getString("FUNDING_APPROVED") != null) ? ((rs.getString("FUNDING_APPROVED").equalsIgnoreCase("Y")) ? true
						: false)
						: false), ((rs.getDate("DATE_REVIEWED") != null) ? new java.util.Date(rs.getDate("DATE_REVIEWED").getTime())
						: null), rs.getInt("REVIEWED_BY"), ((rs.getDate("DATE_ASSIGNED") != null) ? new java.util.Date(rs.getDate(
						"DATE_ASSIGNED").getTime()) : null), ((rs.getDate("DATE_COMPLETED") != null) ? new java.util.Date(rs.getDate(
						"DATE_COMPLETED").getTime())
						: null), ((rs.getDate("DATE_CANCELLED") != null) ? new java.util.Date(rs.getDate("DATE_CANCELLED").getTime())
						: null), rs.getDouble("ESTIMATE_COST"), rs.getDouble("ACTUAL_COST"));

				reqs.add(req);
			}
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getMaintenanceRequest(int): " + e);
			throw new RequestException("Can not extract maintenance request notes from DB: " + e);
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
		return reqs;
	}

	public static Vector<MaintenanceRequest> getOutstandingCaptialMaintenanceRequests() throws RequestException {

		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			reqs = new Vector<MaintenanceRequest>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_outstanding_capital_req; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = new MaintenanceRequest(rs.getInt("REQUEST_ID"), new RequestType(rs.getString("TYPE_ID")), new StatusCode(rs.getString("CUR_STATUS")), rs.getString("PROBLEM_DESC"), new java.util.Date(rs.getDate(
						"REQUEST_DATE").getTime()), rs.getString("ROOM_NAME_NUMBER"), rs.getInt("REQUESTED_BY_ID"), rs.getInt("SCHOOL_ID"), rs.getInt("SCHOOL_PRIORITY"), ((rs.getString("CATEGORY_ID") != null) ? new RequestCategory(rs.getString("CATEGORY_ID"))
						: null), ((rs.getString("CAPITAL_TYPE_ID") != null) ? new CapitalType(rs.getString("CAPITAL_TYPE_ID"))
						: null), rs.getInt("CAPITAL_PRIORITY"), ((rs.getString("FUNDING_APPROVED") != null) ? ((rs.getString("FUNDING_APPROVED").equalsIgnoreCase("Y")) ? true
						: false)
						: false), ((rs.getDate("DATE_REVIEWED") != null) ? new java.util.Date(rs.getDate("DATE_REVIEWED").getTime())
						: null), rs.getInt("REVIEWED_BY"), ((rs.getDate("DATE_ASSIGNED") != null) ? new java.util.Date(rs.getDate(
						"DATE_ASSIGNED").getTime()) : null), ((rs.getDate("DATE_COMPLETED") != null) ? new java.util.Date(rs.getDate(
						"DATE_COMPLETED").getTime())
						: null), ((rs.getDate("DATE_CANCELLED") != null) ? new java.util.Date(rs.getDate("DATE_CANCELLED").getTime())
						: null), rs.getDouble("ESTIMATE_COST"), rs.getDouble("ACTUAL_COST"));

				reqs.add(req);
			}
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getOutstandingCaptialMaintenanceRequests(): " + e);
			throw new RequestException("Can not extract maintenance request notes from DB: " + e);
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
		return reqs;
	}

	public static Vector<MaintenanceRequest> getAllOutstandingMaintenanceRequestsSorted() throws RequestException {

		Vector<MaintenanceRequest> reqs = null;
		MaintenanceRequest req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			reqs = new Vector<MaintenanceRequest>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_all_outstanding_req_sorted; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				req = new MaintenanceRequest(rs.getInt("REQUEST_ID"), new RequestType(rs.getString("TYPE_ID")), new StatusCode(rs.getString("CUR_STATUS")), rs.getString("PROBLEM_DESC"), new java.util.Date(rs.getDate(
						"REQUEST_DATE").getTime()), rs.getString("ROOM_NAME_NUMBER"), rs.getInt("REQUESTED_BY_ID"), rs.getInt("SCHOOL_ID"), rs.getInt("SCHOOL_PRIORITY"), ((rs.getString("CATEGORY_ID") != null) ? new RequestCategory(rs.getString("CATEGORY_ID"))
						: null), ((rs.getString("CAPITAL_TYPE_ID") != null) ? new CapitalType(rs.getString("CAPITAL_TYPE_ID"))
						: null), rs.getInt("CAPITAL_PRIORITY"), ((rs.getString("FUNDING_APPROVED") != null) ? ((rs.getString("FUNDING_APPROVED").equalsIgnoreCase("Y")) ? true
						: false)
						: false), ((rs.getDate("DATE_REVIEWED") != null) ? new java.util.Date(rs.getDate("DATE_REVIEWED").getTime())
						: null), rs.getInt("REVIEWED_BY"), ((rs.getDate("DATE_ASSIGNED") != null) ? new java.util.Date(rs.getDate(
						"DATE_ASSIGNED").getTime()) : null), ((rs.getDate("DATE_COMPLETED") != null) ? new java.util.Date(rs.getDate(
						"DATE_COMPLETED").getTime())
						: null), ((rs.getDate("DATE_CANCELLED") != null) ? new java.util.Date(rs.getDate("DATE_CANCELLED").getTime())
						: null), rs.getDouble("ESTIMATE_COST"), rs.getDouble("ACTUAL_COST"));

				reqs.add(req);
			}
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getOutstandingCaptialMaintenanceRequests(): " + e);
			throw new RequestException("Can not extract maintenance request notes from DB: " + e);
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
		return reqs;
	}

	public static int addMaintenanceRequest(MaintenanceRequest req)
			throws RequestException,
				PersonnelException,
				SchoolException {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ?:= awsd_user.maint_req_sys.insert_maint_req(?, ?, ?, ?, ?, ?, ?, ?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.setString(2, req.getRequestType().getRequestTypeID());
			stat.setString(3, req.getCurentStatus().getStatusCodeID());
			stat.setString(4, req.geProblemDescription());
			stat.setDate(5, new java.sql.Date(req.getRequestedDate().getTime()));
			stat.setString(6, req.getRoomNameNumber());
			stat.setInt(7, req.getSchoolPriority());
			stat.setInt(8, req.getRequestedBy().getPersonnelID());
			stat.setInt(9, req.getSchool().getSchoolID());
			stat.execute();

			id = ((OracleCallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.addMaintenanceRequest(MaintentanceRequest): " + e);
			throw new RequestException("Can not add maintenance request to DB: " + e);
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
		return (id);
	}

	public static boolean addMaintenanceRequestCapital(MaintenanceRequest req)
			throws RequestException,
				PersonnelException,
				SchoolException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.insert_maint_req_cap(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); end;");
			stat.setString(1, req.getRequestType().getRequestTypeID());
			stat.setString(2, req.getCurentStatus().getStatusCodeID());
			stat.setString(3, req.geProblemDescription());
			stat.setDate(4, new java.sql.Date(req.getRequestedDate().getTime()));
			stat.setString(5, req.getRoomNameNumber());
			stat.setInt(6, req.getSchoolPriority());
			stat.setInt(7, req.getRequestedBy().getPersonnelID());
			stat.setInt(8, req.getSchool().getSchoolID());
			stat.setString(9, req.getRequestCategory().getRequestCategoryID());
			stat.setString(10, req.getCapitalType().getCapitalTypeID());
			stat.setInt(11, req.getCapitalPriority());
			stat.setDouble(12, req.getEstimatedCost());
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("MaintenanceRequestDB.addMaintenanceRequest(MaintentanceRequest): " + e);
			throw new RequestException("Can not add maintenance request to DB: " + e);
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

	public static boolean changeSchoolPriority(int req, int priority) throws RequestException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.change_school_priority(?, ?); end;");
			stat.setInt(1, req);
			stat.setInt(2, priority);
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("MaintenanceRequestDB.addMaintenanceRequest(MaintentanceRequest): " + e);
			throw new RequestException("Can not add maintenance request to DB: " + e);
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

	public static boolean changeRequestType(int req, String req_type) throws RequestException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.change_request_type(?, ?); end;");
			stat.setInt(1, req);
			stat.setString(2, req_type);
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("MaintenanceRequestDB.changeRequestType(MaintentanceRequest): " + e);
			throw new RequestException("Can not change request type to DB: " + e);
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

	public static boolean updateMaintenanceRequestStatus(int req, String status) throws RequestException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.update_maint_req_status(?, ?); end;");
			stat.setInt(1, req);
			stat.setString(2, status);
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("MaintenanceRequestDB.updateMaintenanceRequestStatus(int): " + e);
			throw new RequestException("Can not cancel maintenance request to DB: " + e);
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

	public static boolean deleteMaintenanceRequest(int req_id) throws RequestException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.delete_maint_req(?); end;");
			stat.setInt(1, req_id);
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("MaintenanceRequestDB.deleteMaintenanceRequest(int): " + e);
			throw new RequestException("Can not delete maintenance request from DB: " + e);
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

	public static boolean deleteMaintenanceRequest(MaintenanceRequest req) throws RequestException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.delete_maint_req(?); end;");
			stat.setInt(1, req.getRequestID());
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("MaintenanceRequestDB.deleteMaintenanceRequest(int): " + e);
			throw new RequestException("Can not delete maintenance request from DB: " + e);
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

	public static Vector<RequestComment> getMaintenanceRequestComments(int req_id) throws RequestException {

		Vector<RequestComment> comments = null;
		RequestComment comment = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			comments = new Vector<RequestComment>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_req_comments(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, req_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				comment = new RequestComment(rs.getInt("COMMENT_ID"), rs.getInt("REQUEST_ID"), rs.getInt("ENTERED_BY"), new java.util.Date(rs.getDate(
						"ENTERED_DATE").getTime()), rs.getString("REQUEST_COMMENT"));

				comments.add(comment);
			}
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getMaintenanceRequest(int): " + e);
			throw new RequestException("Can not extract maintenance request notes from DB: " + e);
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
		return comments;
	}

	public static boolean addRequestComment(RequestComment comment)
			throws RequestException,
				PersonnelException,
				SchoolException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.insert_req_comment(?, ?, ?); end;");
			stat.setInt(1, comment.getRequestID());
			stat.setInt(2, comment.getMadeBy().getPersonnelID());
			stat.setString(3, comment.getComment());

			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("MaintenanceRequestDB.addRequestComment(RequestComment): " + e);
			throw new RequestException("Can not add maintenance request to DB: " + e);
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

	public static boolean assignMaintentancePersonnel(MaintenanceRequest req, String who[])
			throws RequestException,
				PersonnelException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			if ((who == null) || (who.length < 1))
				return true;

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.assign_maint_req_personnel(?, ?); end;");

			for (int i = 0; i < who.length; i++) {
				stat.clearParameters();
				stat.setInt(1, req.getRequestID());
				stat.setInt(2, Integer.parseInt(who[i]));

				stat.execute();
			}

			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("MaintenanceRequestDB.assignMaintentancePersonnel(MaintenanceRequest req, Personnel who): "
					+ e);
			throw new RequestException("Can not assign maintenance request personnel to DB: " + e);
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

	public static boolean unassignMaintentancePersonnel(int req, int who) throws RequestException, PersonnelException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.unassign_maint_req_personnel(?, ?); end;");

			stat.setInt(1, req);
			stat.setInt(2, who);

			stat.execute();

			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("MaintenanceRequestDB.unassignMaintentancePersonnel(int req, int who): " + e);
			throw new RequestException("Can not unassign maintenance request personnel to DB: " + e);
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

	public static Vector<RequestComment> getRequestAssignedPersonnel(MaintenanceRequest req) throws RequestException {

		Vector<RequestComment> comments = null;
		RequestComment comment = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			comments = new Vector<RequestComment>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_req_maint_personnel(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, req.getRequestID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				comment = new RequestComment(rs.getInt("COMMENT_ID"), rs.getInt("REQUEST_ID"), rs.getInt("ENTERED_BY"), new java.util.Date(rs.getDate(
						"ENTERED_DATE").getTime()), rs.getString("REQUEST_COMMENT"));

				comments.add(comment);
			}
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getMaintenanceRequest(int): " + e);
			throw new RequestException("Can not extract maintenance request notes from DB: " + e);
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
		return comments;
	}

	public static boolean assignVendor(MaintenanceRequest req, String vendor_id[]) throws RequestException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			if ((vendor_id == null) || (vendor_id.length < 0))
				return true;

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.assign_maint_req_vendor(?, ?); end;");

			for (int i = 0; i < vendor_id.length; i++) {
				stat.clearParameters();
				stat.setInt(1, req.getRequestID());
				stat.setInt(2, Integer.parseInt(vendor_id[i]));

				stat.execute();
			}
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("MaintenanceRequestDB.assignVendor(MaintenanceRequest req, Vendor vendor): " + e);
			throw new RequestException("Can not assign maintenance request personnel to DB: " + e);
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

	public static boolean unassignVendor(int req, int vendor_id) throws RequestException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.unassign_maint_req_vendor(?, ?); end;");

			stat.setInt(1, req);
			stat.setInt(2, vendor_id);

			stat.execute();

			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("MaintenanceRequestDB.unassignVendor(int req, int vendor_id): " + e);
			throw new RequestException("Can not unassign vendor to DB: " + e);
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

	public static boolean updateAdminMaintRequest(MaintenanceRequest req) throws RequestException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.maint_req_sys.update_admin_maint_req(?, ?, ?, ?, ?, ?, ?); end;");

			stat.setInt(1, req.getRequestID());
			stat.setString(2, req.getCurentStatus().getStatusCodeID());
			stat.setString(3, req.getRequestCategory().getRequestCategoryID());
			if (req.getCapitalType() != null) {
				stat.setString(4, req.getCapitalType().getCapitalTypeID());
				stat.setInt(5, req.getCapitalPriority());
				stat.setString(6, (req.isCapitalFundingApproved() ? "Y" : "N"));
			}
			else {
				stat.setNull(4, OracleTypes.VARCHAR);
				stat.setInt(5, 0);
				stat.setString(6, "N");
			}
			stat.setDouble(7, req.getEstimatedCost());

			stat.execute();

			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("MaintenanceRequestDB.assignVendor(MaintenanceRequest req, Vendor vendor): " + e);
			throw new RequestException("Can not assign maintenance request personnel to DB: " + e);
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

	public static Vector<School> getRegionalSchools(Personnel p) throws SchoolException {

		Vector<School> reqs = null;
		School s = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			reqs = new Vector<School>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_regional_schools(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				// s = new School(rs.getInt("SCHOOL_ID"), rs.getString("SCHOOL_NAME"),
				// rs.getInt("PRINCIPAL_ID"), rs.getInt("VICEPRINCIPAL_ID"));

				s = SchoolDB.createSchoolBean(rs);
				reqs.add(s);
			}
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getRegionalSchools(Personnel p): " + e);
			throw new SchoolException("Can not extract schools notes from DB: " + e);
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
		return reqs;
	}

	public static Vector<Personnel> getSchoolSupervisor(School s) throws SchoolException {

		Vector<Personnel> reqs = null;

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			reqs = new Vector<Personnel>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.maint_req_sys.get_school_supervisor(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s.getSchoolID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				reqs.add(PersonnelDB.createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("MaintenanceRequestDB.getRegionalSchools(Personnel p): " + e);
			throw new SchoolException("Can not extract schools notes from DB: " + e);
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
		return reqs;
	}

	public static MaintenanceRequest createMaintenanceRequestBean(ResultSet rs) {

		MaintenanceRequest req = null;

		try {
			req = new MaintenanceRequest(rs.getInt("REQUEST_ID"), new RequestType(rs.getString("TYPE_ID")), new StatusCode(rs.getString("CUR_STATUS")), rs.getString("PROBLEM_DESC"), new java.util.Date(rs.getDate(
					"REQUEST_DATE").getTime()), rs.getString("ROOM_NAME_NUMBER"), rs.getInt("REQUESTED_BY_ID"), rs.getInt("SCHOOL_ID"), rs.getInt("SCHOOL_PRIORITY"), ((rs.getString("CATEGORY_ID") != null) ? new RequestCategory(rs.getString("CATEGORY_ID"))
					: null), ((rs.getString("CAPITAL_TYPE_ID") != null) ? new CapitalType(rs.getString("CAPITAL_TYPE_ID")) : null), rs.getInt("CAPITAL_PRIORITY"), ((rs.getString("FUNDING_APPROVED") != null) ? ((rs.getString("FUNDING_APPROVED").equalsIgnoreCase("Y")) ? true
					: false)
					: false), ((rs.getDate("DATE_REVIEWED") != null) ? new java.util.Date(rs.getDate("DATE_REVIEWED").getTime())
					: null), rs.getInt("REVIEWED_BY"), ((rs.getDate("DATE_ASSIGNED") != null) ? new java.util.Date(rs.getDate(
					"DATE_ASSIGNED").getTime()) : null), ((rs.getDate("DATE_COMPLETED") != null) ? new java.util.Date(rs.getDate(
					"DATE_COMPLETED").getTime()) : null), ((rs.getDate("DATE_CANCELLED") != null) ? new java.util.Date(rs.getDate(
					"DATE_CANCELLED").getTime())
					: null), rs.getDouble("ESTIMATE_COST"), rs.getDouble("ACTUAL_COST"));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return req;
	}
}
