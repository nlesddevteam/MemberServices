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
import com.esdnl.scrs.domain.BullyingReasonType;

public class BullyingReasonTypeService {

	private static HashMap<Integer, BullyingReasonType> preloaded;

	static {
		try {
			preloaded = getBullyingReasonTypeMap();
		}
		catch (BullyingException e) {
			e.printStackTrace();
		}
	}

	public static BullyingReasonType getBullyingReasonType(int typeId) throws BullyingException {

		BullyingReasonType type = null;

		if (preloaded.containsKey(typeId))
			type = preloaded.get(typeId);
		else {
			Connection con = null;
			CallableStatement stat = null;
			ResultSet rs = null;

			try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);

				stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_authority_figure_type(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);

				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);

				if (rs.next()) {
					type = BullyingReasonTypeService.createBullyingReasonType(rs);
					preloaded.put(type.getTypeId(), type);
				}
			}
			catch (SQLException e) {
				e.printStackTrace();

				System.err.println("BullyingReasonType getBullyingReasonType(int typeId): " + e);
				throw new BullyingException("Can not fetch BullyingReasonType to DB.", e);
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

	public static ArrayList<BullyingReasonType> getBullyingReasonType() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BullyingReasonType> types = null;

		try {
			types = new ArrayList<BullyingReasonType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_reason_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				types.add(BullyingReasonTypeService.createBullyingReasonType(rs));

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("ArrayList<BullyingReasonType> getBullyingReasonType(): " + e);
			throw new BullyingException("Can not fetch BullyingReasonType to DB.", e);
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

	public static HashMap<Integer, BullyingReasonType> getBullyingReasonTypeMap() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		HashMap<Integer, BullyingReasonType> types = null;
		BullyingReasonType type = null;

		try {
			types = new HashMap<Integer, BullyingReasonType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_reason_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				type = BullyingReasonTypeService.createBullyingReasonType(rs);
				types.put(type.getTypeId(), type);

			}

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("HashMap<Integer, BullyingReasonType> getBullyingReasonTypeMap(): " + e);
			throw new BullyingException("Can not fetch BullyingReasonType to DB.", e);
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

	public static BullyingReasonType createBullyingReasonType(ResultSet rs) {

		BullyingReasonType abean = null;

		try {
			abean = new BullyingReasonType();

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
