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
import com.esdnl.scrs.domain.BullyingBehaviorType;
import com.esdnl.scrs.domain.BullyingException;

public class BullyingBehaviorTypeService {

	private static HashMap<Integer, BullyingBehaviorType> preloaded;

	static {
		try {
			preloaded = getBullyingBehaviorTypeMap();
		}
		catch (BullyingException e) {
			e.printStackTrace();
		}
	}

	public static BullyingBehaviorType getBullyingBehaviorType(int typeId) throws BullyingException {

		BullyingBehaviorType type = null;

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
					type = BullyingBehaviorTypeService.createBullyingBehaviorType(rs);
					preloaded.put(type.getTypeId(), type);
				}
			}
			catch (SQLException e) {
				e.printStackTrace();

				System.err.println("BullyingBehaviorType getBullyingBehaviorType(int typeId): " + e);
				throw new BullyingException("Can not fetch BullyingBehaviorType to DB.", e);
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

	public static ArrayList<BullyingBehaviorType> getBullyingBehaviorType() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BullyingBehaviorType> types = null;

		try {
			types = new ArrayList<BullyingBehaviorType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_behavior_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				types.add(BullyingBehaviorTypeService.createBullyingBehaviorType(rs));

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("ArrayList<BullyingBehaviorType> getBullyingBehaviorType(): " + e);
			throw new BullyingException("Can not fetch BullyingBehaviorType to DB.", e);
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

	public static HashMap<Integer, BullyingBehaviorType> getBullyingBehaviorTypeMap() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		HashMap<Integer, BullyingBehaviorType> types = null;
		BullyingBehaviorType type = null;

		try {
			types = new HashMap<Integer, BullyingBehaviorType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_behavior_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				type = BullyingBehaviorTypeService.createBullyingBehaviorType(rs);
				types.put(type.getTypeId(), type);

			}

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("ArrayList<BullyingBehaviorType> getBullyingBehaviorType(): " + e);
			throw new BullyingException("Can not fetch BullyingBehaviorType to DB.", e);
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

	public static BullyingBehaviorType createBullyingBehaviorType(ResultSet rs) {

		BullyingBehaviorType abean = null;

		try {
			abean = new BullyingBehaviorType();

			abean.setTypeId(rs.getInt("TYPE_ID"));
			abean.setTypeName(rs.getString("TYPE_NAME"));
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
