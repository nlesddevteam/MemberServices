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
import com.esdnl.scrs.domain.ThreateningBehaviorType;

public class ThreateningBehaviorTypeService {

	private static HashMap<Integer, ThreateningBehaviorType> preloaded;

	static {
		try {
			preloaded = getThreateningBehaviorTypeMap();
		}
		catch (BullyingException e) {
			e.printStackTrace();
		}
	}

	public static ThreateningBehaviorType getThreateningBehaviorType(int typeId) throws BullyingException {

		ThreateningBehaviorType type = null;

		if (preloaded.containsKey(typeId))
			type = preloaded.get(typeId);
		else {
			Connection con = null;
			CallableStatement stat = null;
			ResultSet rs = null;

			try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);

				stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_threateningbehavior_type(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);

				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);

				if (rs.next()) {
					type = ThreateningBehaviorTypeService.createThreateningBehaviorType(rs);
					preloaded.put(type.getTypeId(), type);
				}
			}
			catch (SQLException e) {
				e.printStackTrace();

				System.err.println("ThreateningBehaviorType getThreateningBehaviorType(int typeId): " + e);
				throw new BullyingException("Can not fetch ThreateningBehaviorType to DB.", e);
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

	public static ArrayList<ThreateningBehaviorType> getThreateningBehaviorTypes() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<ThreateningBehaviorType> types = null;

		try {
			types = new ArrayList<ThreateningBehaviorType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_threateningbehavior_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				types.add(ThreateningBehaviorTypeService.createThreateningBehaviorType(rs));

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("ArrayList<ThreateningBehaviorType> getThreateningBehaviorTypes(): " + e);
			throw new BullyingException("Can not fetch ThreateningBehaviorType to DB.", e);
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

	public static HashMap<Integer, ThreateningBehaviorType> getThreateningBehaviorTypeMap() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		HashMap<Integer, ThreateningBehaviorType> types = null;
		ThreateningBehaviorType type = null;

		try {
			types = new HashMap<Integer, ThreateningBehaviorType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_threateningbehavior_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				type = ThreateningBehaviorTypeService.createThreateningBehaviorType(rs);
				types.put(type.getTypeId(), type);

			}

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("HashMap<Integer, ThreateningBehaviorType> getThreateningBehaviorTypeMap(): " + e);
			throw new BullyingException("Can not fetch ThreateningBehaviorType to DB.", e);
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

	public static ThreateningBehaviorType createThreateningBehaviorType(ResultSet rs) {

		ThreateningBehaviorType abean = null;

		try {
			abean = new ThreateningBehaviorType();

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
