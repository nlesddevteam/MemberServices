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
import com.esdnl.scrs.domain.SexualBehaviourType;

public class SexualBehaviourTypeService {

	private static HashMap<Integer, SexualBehaviourType> preloaded;

	static {
		try {
			preloaded = getSexualBehaviourTypeMap();
		}
		catch (BullyingException e) {
			e.printStackTrace();
		}
	}

	public static SexualBehaviourType getSexualBehaviourType(int typeId) throws BullyingException {

		SexualBehaviourType type = null;

		if (preloaded.containsKey(typeId))
			type = preloaded.get(typeId);
		else {
			Connection con = null;
			CallableStatement stat = null;
			ResultSet rs = null;

			try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);

				stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_sexbehavior_type(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);

				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);

				if (rs.next()) {
					type = SexualBehaviourTypeService.createSexualBehaviourType(rs);
					preloaded.put(type.getTypeId(), type);
				}
			}
			catch (SQLException e) {
				e.printStackTrace();

				System.err.println("SexualBehaviorType getSexualBehaviourType(int typeId): " + e);
				throw new BullyingException("Can not fetch SexualBehaviourType to DB.", e);
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

	public static ArrayList<SexualBehaviourType> getSexualBehaviourTypes() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<SexualBehaviourType> types = null;

		try {
			types = new ArrayList<SexualBehaviourType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_sexbehaviour_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				types.add(SexualBehaviourTypeService.createSexualBehaviourType(rs));

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("ArrayList<SexualBehaviorType> getSexualBehaviourTypes(): " + e);
			throw new BullyingException("Can not fetch SexualBehaviourType to DB.", e);
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

	public static HashMap<Integer, SexualBehaviourType> getSexualBehaviourTypeMap() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		HashMap<Integer, SexualBehaviourType> types = null;
		SexualBehaviourType type = null;

		try {
			types = new HashMap<Integer, SexualBehaviourType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_sexbehaviour_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				type = SexualBehaviourTypeService.createSexualBehaviourType(rs);
				types.put(type.getTypeId(), type);

			}

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("HashMap<Integer, SexualBehaviourType> getSexualBehaviourTypeMap(): " + e);
			throw new BullyingException("Can not fetch SexualBehaviourType to DB.", e);
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

	public static SexualBehaviourType createSexualBehaviourType(ResultSet rs) {

		SexualBehaviourType abean = null;

		try {
			abean = new SexualBehaviourType();

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
