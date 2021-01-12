package com.awsd.travel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.common.Utils;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.pd.PersonnelPD;
import com.esdnl.dao.DAOUtils;

public class TravelClaimDB {

	private static final int PAGE_SIZE = 10;

	public static Vector<TravelClaim> getClaims() throws TravelClaimException {

		Vector<TravelClaim> claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new Vector<TravelClaim>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_all_claims; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaims(): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}

	public static Vector<TravelClaim> getClaims(Personnel who) throws TravelClaimException {

		Vector<TravelClaim> claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new Vector<TravelClaim>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_personnel(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, who.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaims(Personnel): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}

	public static TreeMap<String, TreeMap<String, Vector<TravelClaim>>> getClaimsTreeMap(Personnel who)
			throws TravelClaimException {

		TreeMap<String, TreeMap<String, Vector<TravelClaim>>> claims = null;
		TreeMap<String, Vector<TravelClaim>> year_map = null;
		Vector<TravelClaim> year_claims = null;
		Vector<TravelClaim> pd_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new TreeMap<String, TreeMap<String, Vector<TravelClaim>>>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_personnel(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, who.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				year_map = (TreeMap<String, Vector<TravelClaim>>) claims.get(rs.getString("FISCAL_YEAR"));
				if (year_map == null) {
					year_map = new TreeMap<String, Vector<TravelClaim>>();
					claims.put(rs.getString("FISCAL_YEAR"), year_map);
				}

				if (claim instanceof PDTravelClaim) {
					pd_claims = (Vector<TravelClaim>) year_map.get("PD-CLAIMS");

					if (pd_claims == null) {
						pd_claims = new Vector<TravelClaim>();
						year_map.put("PD-CLAIMS", pd_claims);
					}

					pd_claims.add(claim);
				}
				else if (claim instanceof TravelClaim) {
					year_claims = (Vector<TravelClaim>) year_map.get("MONTHLY-CLAIMS");

					if (year_claims == null) {
						year_claims = new Vector<TravelClaim>(12);
						year_map.put("MONTHLY-CLAIMS", year_claims);
					}

					year_claims.add(claim);
				}
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaimsTreeMap(Personnel): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}

	public static TreeMap<Integer, TravelClaim> getClaimsTreeMap(Personnel who, String fy) throws TravelClaimException {

		TreeMap<Integer, TravelClaim> claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new TreeMap<Integer, TravelClaim>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_personnel_year(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, who.getPersonnelID());
			stat.setString(3, fy);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					/*
					 * claim = new PDTravelClaim(rs.getInt("CLAIM_ID"),
					 * rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"),
					 * rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"),
					 * rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"),
					 * rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"),
					 * ((rs.getString("PAID_TCHR_PAYROLL") != null) &&
					 * rs.getString("PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")),
					 * rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"),
					 * rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
					 */
					continue;
				}

				claims.put(new Integer(rs.getInt("FISCAL_MONTH")), claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaimsTreeMap(Personnel, String): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}

	public static Vector<TravelClaim> getClaims(TravelClaimStatus code) throws TravelClaimException {

		Vector<TravelClaim> claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new Vector<TravelClaim>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_status(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, code.getID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaims(TravelClaimStatus): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}

	public static Vector<TravelClaim> getClaims(Personnel who, TravelClaimStatus code) throws TravelClaimException {

		Vector<TravelClaim> claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new Vector<TravelClaim>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_personnel_status(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, who.getPersonnelID());
			stat.setInt(3, code.getID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaims(Personnel, TravelClaimStatus): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}

	public static TravelClaim getClaim(int claim_id) throws TravelClaimException {

		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claim(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, claim_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}
			}
			else {
				claim = null;
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaim(int): " + e);
			throw new TravelClaimException("Can not extract travel claim from DB: " + e);
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
		return claim;
	}

	public static TreeMap<Integer, Vector<TravelClaim>> getClaimsPendingApprovalTreeMap(Personnel supervisor)
			throws TravelClaimException {

		TreeMap<Integer, Vector<TravelClaim>> claims = null;
		Vector<TravelClaim> personnel_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new TreeMap<Integer, Vector<TravelClaim>>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_pending_approval(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, supervisor.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				personnel_claims = (Vector<TravelClaim>) claims.get(new Integer(rs.getInt("PERSONNEL_ID")));
				if (personnel_claims == null) {
					personnel_claims = new Vector<TravelClaim>(5);
					claims.put(new Integer(rs.getInt("PERSONNEL_ID")), personnel_claims);
				}
				personnel_claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaimsTreeMap(Personnel): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}

	public static TreeMap<Integer, Vector<TravelClaim>> getClaimsApprovedTreeMap() throws TravelClaimException {

		TreeMap<Integer, Vector<TravelClaim>> claims = null;
		Vector<TravelClaim> personnel_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new TreeMap<Integer, Vector<TravelClaim>>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_approved; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				personnel_claims = (Vector<TravelClaim>) claims.get(new Integer(rs.getInt("PERSONNEL_ID")));
				if (personnel_claims == null) {
					personnel_claims = new Vector<TravelClaim>(5);
					claims.put(new Integer(rs.getInt("PERSONNEL_ID")), personnel_claims);
				}
				personnel_claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaimsTreeMap(Personnel): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}

	public static ArrayList<ArrayList<TravelClaim>> getClaimsApprovedPagedByDate() throws TravelClaimException {

		ArrayList<ArrayList<TravelClaim>> pages = null;
		ArrayList<TravelClaim> page = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			pages = new ArrayList<ArrayList<TravelClaim>>();
			page = new ArrayList<TravelClaim>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_approved; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				page.add(claim);

				if (page.size() == PAGE_SIZE) {
					pages.add(page);

					page = new ArrayList<TravelClaim>();
				}
			}

			if (page.size() > 0) {
				pages.add(page);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<ArrayList<TravelClaim>> getClaimsApprovedPagedByDate: " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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

	public static LinkedHashMap<Integer, Vector<TravelClaim>> getClaimsPaymentPendingTreeMap() throws TravelClaimException {

		LinkedHashMap<Integer, Vector<TravelClaim>> claims = null;
		Vector<TravelClaim> personnel_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new LinkedHashMap<Integer, Vector<TravelClaim>>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_payment_pending; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				personnel_claims = (Vector<TravelClaim>) claims.get(new Integer(rs.getInt("PERSONNEL_ID")));
				if (personnel_claims == null) {
					personnel_claims = new Vector<TravelClaim>(5);
					claims.put(new Integer(rs.getInt("PERSONNEL_ID")), personnel_claims);
				}
				
				personnel_claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaimsTreeMap(Personnel): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}

	public static TreeMap<Integer, Vector<TravelClaim>> getClaimsPaidTodayTreeMap() throws TravelClaimException {

		TreeMap<Integer, Vector<TravelClaim>> claims = null;
		Vector<TravelClaim> personnel_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new TreeMap<Integer, Vector<TravelClaim>>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_paid_today; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				personnel_claims = (Vector<TravelClaim>) claims.get(new Integer(rs.getInt("PERSONNEL_ID")));
				if (personnel_claims == null) {
					personnel_claims = new Vector<TravelClaim>(5);
					claims.put(new Integer(rs.getInt("PERSONNEL_ID")), personnel_claims);
				}
				personnel_claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaimsTreeMap(Personnel): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}

	public static boolean setCurrentStatus(TravelClaim claim, Personnel who, TravelClaimStatus new_status)
			throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.update_claim_status(?, ?, ?); end;");
			stat.setInt(1, claim.getClaimID());
			stat.setInt(2, who.getPersonnelID());
			stat.setInt(3, new_status.getID());
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.setCurrentStatus(): " + e);
			throw new TravelClaimException("Can not update travel claim status to DB: " + e);
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

	public static int addClaim(Personnel who, Personnel supervisor, String fy, int mon) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		int cid = -1;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.insert_claim(?, ?, ?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, who.getPersonnelID());
			stat.setInt(3, supervisor.getPersonnelID());
			stat.setString(4, fy);
			stat.setInt(5, mon);
			stat.execute();

			cid = ((OracleCallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
			cid = -1;
			System.err.println("TravelClaimDB.addClaim(): " + e);
			throw new TravelClaimException("Can not add travel claim status to DB: " + e);
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
		return (cid);
	}

	public static int addClaim(Personnel who, Personnel supervisor, String fy, int mon, PersonnelPD pd)
			throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		int cid = -1;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.insert_pd_claim(?, ?, ?, ?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, who.getPersonnelID());
			stat.setInt(3, supervisor.getPersonnelID());
			stat.setString(4, fy);
			stat.setInt(5, mon);
			stat.setInt(6, pd.getID());
			stat.execute();

			cid = ((OracleCallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
			cid = -1;
			System.err.println("TravelClaimDB.addClaim(): " + e);
			throw new TravelClaimException("Can not add travel claim status to DB: " + e);
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
		return (cid);
	}

	public static boolean deleteClaim(TravelClaim claim) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.delete_claim(?); end;");
			stat.setInt(1, claim.getClaimID());
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.deleteClaim(): " + e);
			throw new TravelClaimException("Can not delete travel claim status to DB: " + e);
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

	public static Vector<TravelClaimRateSummary> getClaimRateSummaryTotals(TravelClaim claim, double rates[])
			throws TravelClaimException {

		Vector<TravelClaimRateSummary> summaries = null;
		TravelClaimRateSummary rate_summary = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			summaries = new Vector<TravelClaimRateSummary>(2);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claim_rate_summary_totals(?, ?); end;");

			for (int i = 0; i < rates.length; i++) {
				stat.clearParameters();
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, claim.getClaimID());
				stat.setDouble(3, Math.round(rates[i] * 1000) / 1000.0);

				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);

				if (rs.next()) {
					rate_summary = new TravelClaimRateSummary(rates[i], rs.getInt("sum_kms"), rs.getDouble("kms_total"));

					summaries.add(rate_summary);
				}

				try {
					rs.close();
				}
				catch (Exception e) {}
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaimSummaryTotals(Claim): " + e);
			throw new TravelClaimException("Can not extract travel claim sumamry from DB: " + e);
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
		return summaries;
	}

	public static TravelClaimSummary getClaimSummaryTotals(TravelClaim claim) throws TravelClaimException {

		TravelClaimSummary summary = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claim_summary_totals(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, claim.getClaimID());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next()) {
				summary = new TravelClaimSummary(rs.getInt("sum_kms"), rs.getDouble("sum_meals"), rs.getDouble("sum_lodging"), rs.getDouble("sum_other"), rs.getDouble("kms_total"), rs.getDouble("sum_total"));

			}
			else
				summary = null;
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaimSummaryTotals(Claim): " + e);
			throw new TravelClaimException("Can not extract travel claim sumamry from DB: " + e);
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
		return summary;
	}

	public static int getYearToDateKMSTotals(Personnel p) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		int total = 0;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_calendar_year_kms_total(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				total = rs.getInt("sum_kms");
			}
			else {
				total = 0;
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaimSummaryTotals(Claim): " + e);
			throw new TravelClaimException("Can not extract year to date kms usage sumamry from DB: " + e);
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
		return total;
	}

	public static double getYearToDateTotalClaimed(Personnel p, String fy) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		double total = 0;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_fiscal_year_claim_total(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.setString(3, fy);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				total = rs.getDouble("total_claimed");
			}
			else {
				total = 0;
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getYearToDateTotalClaimed(Personnel p): " + e);
			throw new TravelClaimException("Can not extract year to date kms usage sumamry from DB: " + e);
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
		return total;
	}

	public static double getYearToDateTotalClaimed(Personnel p) throws TravelClaimException {

		return getYearToDateTotalClaimed(p, Utils.getCurrentSchoolYear());
	}

	public static boolean setClaimToPaid(TravelClaim claim, Personnel usr, String sds_gl_acc, String sds_tchr_par)
			throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.update_claim_status_to_paid(?, ?, ?, ?); end;");
			stat.setInt(1, claim.getClaimID());
			stat.setInt(2, usr.getPersonnelID());
			stat.setString(3, sds_gl_acc);
			stat.setString(4, sds_tchr_par);
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.payClaim(): " + e);
			throw new TravelClaimException("Can not pay travel claim status to DB: " + e);
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

	public static boolean setSupervisor(TravelClaim claim, Personnel usr) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.update_claim_supervisor(?, ?); end;");
			stat.setInt(1, claim.getClaimID());
			stat.setInt(2, usr.getPersonnelID());
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.setSupervisor(): " + e);
			throw new TravelClaimException("Can not supervisor to DB: " + e);
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

	public static boolean setClaimGLAccount(TravelClaim claim, String sds_gl_acc) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.update_claim_glaccount(?, ?); end;");
			stat.setInt(1, claim.getClaimID());
			stat.setString(2, sds_gl_acc);
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.setClaimGLAccount(): " + e);
			throw new TravelClaimException("Can not set travel claim gl account to DB: " + e);
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

	public static Vector<TravelClaim> getPaidClaimsNotExported(java.util.Date sd) throws TravelClaimException {

		Vector<TravelClaim> claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new Vector<TravelClaim>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_paid_claims_not_exported(?); end;");
			//stat = con.prepareCall("begin ? := awsd_user.testing_pkg.get_paid_claims_not_exported(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setDate(2, new java.sql.Date(sd.getTime()));
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getPaidClaimsNotExported(Date): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}

	public static Vector<TravelClaim> getPaidClaims(java.util.Date sd) throws TravelClaimException {

		Vector<TravelClaim> claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new Vector<TravelClaim>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_paid_claims(?); end;");
			//stat = con.prepareCall("begin ? := awsd_user.testing_pkg.get_paid_claims(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setDate(2, new java.sql.Date(sd.getTime()));
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				if (rs.getDate("EXPORT_DATE") != null)
					claim.setExportDate(new java.util.Date(rs.getDate("EXPORT_DATE").getTime()));

				claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getPaidClaimsNotExported(Date): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}

	public static TreeMap<Integer, Vector<TravelClaim>> searchByName(String fn, String ln) throws TravelClaimException {

		TreeMap<Integer, Vector<TravelClaim>> claims = null;
		Vector<TravelClaim> personnel_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new TreeMap<Integer, Vector<TravelClaim>>();

			con = DAOUtils.getConnection();

			if (fn.equalsIgnoreCase(ln)) {
				stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_search_by_name(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, fn);
			}
			else {
				stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_search_by_name(?, ?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, fn);
				stat.setString(3, ln);
			}

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				personnel_claims = (Vector<TravelClaim>) claims.get(new Integer(rs.getInt("PERSONNEL_ID")));
				if (personnel_claims == null) {
					personnel_claims = new Vector<TravelClaim>(5);
					claims.put(new Integer(rs.getInt("PERSONNEL_ID")), personnel_claims);
				}
				personnel_claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.searchByName(String, String): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}

	public static TreeMap<Integer, Vector<TravelClaim>> searchByVendorNumber(String vn) throws TravelClaimException {

		TreeMap<Integer, Vector<TravelClaim>> claims = null;
		Vector<TravelClaim> personnel_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new TreeMap<Integer, Vector<TravelClaim>>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_search_by_vendor(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, vn);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				personnel_claims = (Vector<TravelClaim>) claims.get(new Integer(rs.getInt("PERSONNEL_ID")));
				if (personnel_claims == null) {
					personnel_claims = new Vector<TravelClaim>(5);
					claims.put(new Integer(rs.getInt("PERSONNEL_ID")), personnel_claims);
				}
				personnel_claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.searchByVendorNumber(String): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}

	public static ArrayList<YearlyKmDetailReportItem> yearlykilometerDetailsReport(String year)
			throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<YearlyKmDetailReportItem> results = new ArrayList<YearlyKmDetailReportItem>();

		try {
			// claims = new TreeMap();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.yearly_kms_details(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, year);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next())
				results.add(YearlyKmDetailReportItem.create(rs));
		}
		catch (SQLException e) {

			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return results;
	}

	public static ArrayList<YearlyClaimsDetailReportItem> yearlyClaimsDetailsReport(String year)
			throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<YearlyClaimsDetailReportItem> results = new ArrayList<YearlyClaimsDetailReportItem>();

		try {
			// claims = new TreeMap();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.yearly_claim_details(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, year);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next())
				results.add(YearlyClaimsDetailReportItem.create(rs));
		}
		catch (SQLException e) {

			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return results;
	}

	public static double[] getKmRates(Date eff_date) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		double rates[] = null;

		try {
			// claims = new TreeMap();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_km_rates(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setDate(2, new java.sql.Date(eff_date.getTime()));
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				rates = new double[] {
						rs.getDouble("BASE"), rs.getDouble("APPROVED")
				};
		}
		catch (SQLException e) {

			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return rates;
	}
	public static TreeMap<Integer, Vector<TravelClaim>> getClaimsPaidTodayLetterTreeMap(String letter) throws TravelClaimException {
		TreeMap<Integer, Vector<TravelClaim>> claims = null;
		Vector<TravelClaim> personnel_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			claims = new TreeMap<Integer, Vector<TravelClaim>>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_paid_today_let(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, letter);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}
				personnel_claims = (Vector<TravelClaim>) claims.get(new Integer(rs.getInt("PERSONNEL_ID")));
				if (personnel_claims == null) {
					personnel_claims = new Vector<TravelClaim>(5);
					claims.put(new Integer(rs.getInt("PERSONNEL_ID")), personnel_claims);
				}
				personnel_claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<Integer, Vector<TravelClaim>> getClaimsPaidTodayLetterTreeMap(String letter): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}
	public static LinkedHashMap<Integer, Vector<TravelClaim>> getClaimsPaymentPendingLetterTreeMap(String letter) throws TravelClaimException {
		LinkedHashMap<Integer, Vector<TravelClaim>> claims = null;
		Vector<TravelClaim> personnel_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			claims = new LinkedHashMap<Integer, Vector<TravelClaim>>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_payment_pending_let(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, letter);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}
				personnel_claims = (Vector<TravelClaim>) claims.get(new Integer(rs.getInt("PERSONNEL_ID")));
				if (personnel_claims == null) {
					personnel_claims = new Vector<TravelClaim>(5);
					claims.put(new Integer(rs.getInt("PERSONNEL_ID")), personnel_claims);
				}
				personnel_claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<Integer, Vector<TravelClaim>> getClaimsPaymentPendingLetterTreeMap(String letter): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}
	public static TreeMap<String,String> getClaimsApprovedDates() throws TravelClaimException {
		TreeMap<String,String> claimsdates = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			claimsdates = new TreeMap<String,String>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_approved_dates; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				claimsdates.put(rs.getString(1), rs.getString(2));
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<String,String> getClaimsApprovedDates():" + e);
			throw new TravelClaimException("Can not extract travel claims dates from DB: " + e);
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
		return claimsdates;
	}
	public static TreeMap<Integer, Vector<TravelClaim>> getClaimsApprovedByDateTreeMap(String approveddate) throws TravelClaimException {
		TreeMap<Integer, Vector<TravelClaim>> claims = null;
		Vector<TravelClaim> personnel_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			claims = new TreeMap<Integer, Vector<TravelClaim>>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_approved_by_date(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, approveddate);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}
				personnel_claims = (Vector<TravelClaim>) claims.get(new Integer(rs.getInt("PERSONNEL_ID")));
				if (personnel_claims == null) {
					personnel_claims = new Vector<TravelClaim>(5);
					claims.put(new Integer(rs.getInt("PERSONNEL_ID")), personnel_claims);
				}
				personnel_claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<Integer, Vector<TravelClaim>> getClaimsApprovedByDateTreeMap(String approveddate): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}
	public static TreeMap<Integer, Vector<TravelClaim>> getClaimsApprovedByZoneTreeMap(Integer zoneid) throws TravelClaimException {
		TreeMap<Integer, Vector<TravelClaim>> claims = null;
		Vector<TravelClaim> personnel_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			claims = new TreeMap<Integer, Vector<TravelClaim>>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_approved_zone(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, zoneid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}
				personnel_claims = (Vector<TravelClaim>) claims.get(new Integer(rs.getInt("PERSONNEL_ID")));
				if (personnel_claims == null) {
					personnel_claims = new Vector<TravelClaim>(5);
					claims.put(new Integer(rs.getInt("PERSONNEL_ID")), personnel_claims);
				}
				personnel_claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<Integer, Vector<TravelClaim>> getClaimsApprovedByZoneTreeMap(Integer zoneid): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}
	public static TreeMap<Integer, Vector<TravelClaim>> getClaimsApprovedByDateAllTreeMap() throws TravelClaimException {
		TreeMap<Integer, Vector<TravelClaim>> claims = null;
		Vector<TravelClaim> personnel_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			claims = new TreeMap<Integer, Vector<TravelClaim>>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_approved_by_dt_all; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}
				personnel_claims = (Vector<TravelClaim>) claims.get(new Integer(rs.getInt("PERSONNEL_ID")));
				if (personnel_claims == null) {
					personnel_claims = new Vector<TravelClaim>(5);
					claims.put(new Integer(rs.getInt("PERSONNEL_ID")), personnel_claims);
				}
				personnel_claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<Integer, Vector<TravelClaim>> getClaimsApprovedByDateAllTreeMap(): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}
	public static ArrayList<YearlyClaimsDetailReportItem> yearlyClaimsDetailsTopReport(String year,Integer rownums)
	throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<YearlyClaimsDetailReportItem> results = new ArrayList<YearlyClaimsDetailReportItem>();
		
		try {
			// claims = new TreeMap();
			int numberofrows= rownums +1;
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_top_users(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, year);
			stat.setInt(3, numberofrows);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next())
				results.add(YearlyClaimsDetailReportItem.createTotal(rs));
		}
		catch (SQLException e) {
		
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return results;
}	
	public static TreeMap<Integer, Vector<TravelClaim>> getClaimsApprovedByZoneAllTreeMap() throws TravelClaimException {
		TreeMap<Integer, Vector<TravelClaim>> claims = null;
		Vector<TravelClaim> personnel_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			claims = new TreeMap<Integer, Vector<TravelClaim>>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_approved_zn_all; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}
				personnel_claims = (Vector<TravelClaim>) claims.get(new Integer(rs.getInt("PERSONNEL_ID")));
				if (personnel_claims == null) {
					personnel_claims = new Vector<TravelClaim>(5);
					claims.put(new Integer(rs.getInt("PERSONNEL_ID")), personnel_claims);
				}
				personnel_claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<Integer, Vector<TravelClaim>> getClaimsApprovedByZoneAllTreeMap(): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}
	public static LinkedHashMap<Integer, Vector<TravelClaim>> getClaimsPreSubmissionTreeMap() throws TravelClaimException {

		LinkedHashMap<Integer, Vector<TravelClaim>> claims = null;
		Vector<TravelClaim> personnel_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new LinkedHashMap<Integer, Vector<TravelClaim>>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_pre_submission; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				personnel_claims = (Vector<TravelClaim>) claims.get(new Integer(rs.getInt("PERSONNEL_ID")));
				if (personnel_claims == null) {
					personnel_claims = new Vector<TravelClaim>(5);
					claims.put(new Integer(rs.getInt("PERSONNEL_ID")), personnel_claims);
				}
				
				personnel_claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("LinkedHashMap<Integer, Vector<TravelClaim>> getClaimsPreSubmissionTreeMap()  " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}
	public static LinkedHashMap<Integer, Vector<TravelClaim>> getClaimsPreSubmissionLetterTreeMap(String letter) throws TravelClaimException {
		LinkedHashMap<Integer, Vector<TravelClaim>> claims = null;
		Vector<TravelClaim> personnel_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			claims = new LinkedHashMap<Integer, Vector<TravelClaim>>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_pre_submission_let(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, letter);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}
				personnel_claims = (Vector<TravelClaim>) claims.get(new Integer(rs.getInt("PERSONNEL_ID")));
				if (personnel_claims == null) {
					personnel_claims = new Vector<TravelClaim>(5);
					claims.put(new Integer(rs.getInt("PERSONNEL_ID")), personnel_claims);
				}
				personnel_claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("LinkedHashMap<Integer, Vector<TravelClaim>> getClaimsPreSubmissionLetterTreeMap(String letter): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}
	public static LinkedHashMap<Integer, Vector<TravelClaim>> getClaimsRejectedTreeMap() throws TravelClaimException {

		LinkedHashMap<Integer, Vector<TravelClaim>> claims = null;
		Vector<TravelClaim> personnel_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new LinkedHashMap<Integer, Vector<TravelClaim>>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_rejected; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				personnel_claims = (Vector<TravelClaim>) claims.get(new Integer(rs.getInt("PERSONNEL_ID")));
				if (personnel_claims == null) {
					personnel_claims = new Vector<TravelClaim>(5);
					claims.put(new Integer(rs.getInt("PERSONNEL_ID")), personnel_claims);
				}
				
				personnel_claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("LinkedHashMap<Integer, Vector<TravelClaim>> getClaimsRejectedTreeMap()  " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}
	public static LinkedHashMap<Integer, Vector<TravelClaim>> getClaimsRejectedLetterTreeMap(String letter) throws TravelClaimException {
		LinkedHashMap<Integer, Vector<TravelClaim>> claims = null;
		Vector<TravelClaim> personnel_claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			claims = new LinkedHashMap<Integer, Vector<TravelClaim>>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_rejected_let(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, letter);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}
				personnel_claims = (Vector<TravelClaim>) claims.get(new Integer(rs.getInt("PERSONNEL_ID")));
				if (personnel_claims == null) {
					personnel_claims = new Vector<TravelClaim>(5);
					claims.put(new Integer(rs.getInt("PERSONNEL_ID")), personnel_claims);
				}
				personnel_claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("LinkedHashMap<Integer, Vector<TravelClaim>> getClaimsRejectedLetterTreeMap(String letter): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}
	public static Vector<TravelClaim> getClaimsSupervisorStatus(Personnel who, int code) throws TravelClaimException {

		Vector<TravelClaim> claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new Vector<TravelClaim>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_super_status(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, who.getPersonnelID());
			stat.setInt(3, code);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaims(Personnel, TravelClaimStatus): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}
	public static Vector<TravelClaim> getClaimsSupervisorPrevious(Personnel who) throws TravelClaimException {

		Vector<TravelClaim> claims = null;
		TravelClaim claim = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			claims = new Vector<TravelClaim>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claims_super_previous(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, who.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (rs.getInt("PD_ID") <= 0) {
					claim = new TravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"));
				}
				else {
					claim = new PDTravelClaim(rs.getInt("CLAIM_ID"), rs.getInt("PERSONNEL_ID"), rs.getInt("CUR_STATUS"), rs.getDate("CREATED_DATE"), rs.getDate("SUBMIT_DATE"), rs.getString("FISCAL_YEAR"), rs.getInt("FISCAL_MONTH"), rs.getInt("SUPERVISOR_ID"), rs.getString("GL_ACCOUNT_CODE"), ((rs.getString("PAID_TCHR_PAYROLL") != null) && rs.getString(
							"PAID_TCHR_PAYROLL").equalsIgnoreCase("Y")), rs.getDate("APPROVED_DATE"), rs.getDate("PAID_DATE"), rs.getDate("EXPORT_DATE"), rs.getInt("PD_ID"));
				}

				claims.add(claim);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaims(Personnel, TravelClaimStatus): " + e);
			throw new TravelClaimException("Can not extract travel claims from DB: " + e);
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
		return claims;
	}
	public static int getYearToDateKMSTotalsFiscalYear(Personnel p) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		int total = 0;
		Date startDate=null;
		Date endDate=null;
		Date today = new Date();
		if(today.getMonth() >6 && today.getMonth() < 9)
		

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_calendar_year_kms_total(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				total = rs.getInt("sum_kms");
			}
			else {
				total = 0;
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.getClaimSummaryTotals(Claim): " + e);
			throw new TravelClaimException("Can not extract year to date kms usage sumamry from DB: " + e);
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
		return total;
	}
	public static double getYearToDateTotalKMSFY(Personnel p, String fy) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		double total = 0;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_calendar_year_kms_total_fy(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.setString(3, fy);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				total = rs.getDouble("sum_kms");
			}
			else {
				total = 0;
			}
		}
		catch (SQLException e) {
			System.err.println("double getYearToDateTotalKMSFY(Personnel p, String fy) throws TravelClaimException : " + e);
			throw new TravelClaimException("Can not extract year to date kms usage sumamry from DB: " + e);
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
		return total;
	}
	public static double getCurrentYearTotalClaimed(Personnel p) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		double total = 0;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_current_year_claim_total(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				total = rs.getDouble("total_claimed");
			}
			else {
				total = 0;
			}
		}
		catch (SQLException e) {
			System.err.println("static double getCurrentYearTotalClaimed(Personnel p) throws TravelClaimException: " + e);
			throw new TravelClaimException("Can not extract year to date kms usage sumamry from DB: " + e);
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
		return total;
	}
}