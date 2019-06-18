package com.esdnl.scrs.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.scrs.domain.BullyingException;
import com.esdnl.scrs.domain.SchoolSafetyIssueType;

public class SchoolSafetyIssueTypeService {

	private static HashMap<Integer, SchoolSafetyIssueType> preloaded;

	static {
		try {
			preloaded = getSchoolSafetyIssueTypeMap();
		}
		catch (BullyingException e) {
			e.printStackTrace();
		}
	}

	public static SchoolSafetyIssueType getSchoolSafetyIssueType(int typeId) throws BullyingException {

		SchoolSafetyIssueType type = null;

		if (preloaded.containsKey(typeId))
			type = preloaded.get(typeId);
		else {
			Connection con = null;
			CallableStatement stat = null;
			ResultSet rs = null;

			try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);

				stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_safetyissue_type(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);

				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);

				if (rs.next()) {
					type = SchoolSafetyIssueTypeService.createSchoolSafetyIssueType(rs);
					preloaded.put(type.getTypeId(), type);
				}
			}
			catch (SQLException e) {
				e.printStackTrace();

				System.err.println("SchoolSafetyIssueType getSchoolSafetyIssueType(int typeId): " + e);
				throw new BullyingException("Can not fetch SchoolSafetyIssueType to DB.", e);
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
		return type;
	}

	public static ArrayList<SchoolSafetyIssueType> getSchoolSafetyIssueTypes() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<SchoolSafetyIssueType> types = null;

		try {
			types = new ArrayList<SchoolSafetyIssueType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_safetyissue_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				types.add(SchoolSafetyIssueTypeService.createSchoolSafetyIssueType(rs));

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("ArrayList<SchoolSafetyIssueType> getSchoolSafetyIssueTypes(): " + e);
			throw new BullyingException("Can not fetch SchoolSafetyIssueType to DB.", e);
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

	public static HashMap<Integer, SchoolSafetyIssueType> getSchoolSafetyIssueTypeMap() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		HashMap<Integer, SchoolSafetyIssueType> types = null;
		SchoolSafetyIssueType type = null;

		try {
			types = new HashMap<Integer, SchoolSafetyIssueType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_safetyissue_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				type = SchoolSafetyIssueTypeService.createSchoolSafetyIssueType(rs);
				types.put(type.getTypeId(), type);

			}

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("HashMap<Integer, SchoolSafetyIssueType> getSchoolSafetyIssueTypeMap(): " + e);
			throw new BullyingException("Can not fetch SchoolSafetyIssueType to DB.", e);
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

	public static SchoolSafetyIssueType createSchoolSafetyIssueType(ResultSet rs) {

		SchoolSafetyIssueType abean = null;

		try {
			abean = new SchoolSafetyIssueType();

			abean.setTypeId(rs.getInt("TYPE_ID"));
			abean.setTypeName(rs.getString("TYPE_NAME"));
			abean.setIsSpecified(rs.getBoolean("IS_SPECIFIED"));
			abean.setOther(rs.getBoolean("IS_OTHER"));

			try {
				abean.setSpecified(rs.getString("SPECIFIED"));
			}
			catch (SQLException e) {
				// column does not exits
				abean.setSpecified(null);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}

}
