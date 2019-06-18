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
import com.esdnl.scrs.domain.ActionType;
import com.esdnl.scrs.domain.BullyingException;

public class ActionTypeService {

	private static HashMap<Integer, ActionType> preloaded;

	static {
		try {
			preloaded = getActionTypeMap();
		}
		catch (BullyingException e) {
			e.printStackTrace();
		}
	}

	public static ActionType getActionType(int typeId) throws BullyingException {

		ActionType type = null;

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
					type = ActionTypeService.createActionType(rs);
					preloaded.put(type.getTypeId(), type);
				}
			}
			catch (SQLException e) {
				e.printStackTrace();

				System.err.println("ActionType getActionType(int typeId): " + e);
				throw new BullyingException("Can not fetch ActionType to DB.", e);
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

	public static ArrayList<ActionType> getActionTypes() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<ActionType> types = null;

		try {
			types = new ArrayList<ActionType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_action_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				types.add(ActionTypeService.createActionType(rs));

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("ArrayList<ActionType> getActionTypes(): " + e);
			throw new BullyingException("Can not fetch ActionType to DB.", e);
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

	public static ArrayList<ActionType> getActionTypes(int parentId) throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<ActionType> types = null;

		try {
			types = new ArrayList<ActionType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_action_types(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, parentId);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				types.add(ActionTypeService.createActionType(rs));

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("ArrayList<ActionType> getActionTypes(): " + e);
			throw new BullyingException("Can not fetch ActionType to DB.", e);
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

	public static HashMap<Integer, ActionType> getActionTypeMap() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		HashMap<Integer, ActionType> types = null;
		ActionType type = null;

		try {
			types = new HashMap<Integer, ActionType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_all_action_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				type = ActionTypeService.createActionType(rs);
				types.put(type.getTypeId(), type);

			}

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("HashMap<Integer, ActionType> getActionTypeMap(): " + e);
			throw new BullyingException("Can not fetch ActionType to DB.", e);
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

	public static ActionType createActionType(ResultSet rs) {

		ActionType abean = null;

		try {
			abean = new ActionType();

			abean.setTypeId(rs.getInt("TYPE_ID"));
			abean.setTypeName(rs.getString("TYPE_NAME"));
			abean.setIsSpecified(rs.getBoolean("IS_SPECIFIED"));
			abean.setParent(rs.getBoolean("IS_PARENT"));
			abean.setParentId(rs.getInt("PARENT_ID"));
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
