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
import com.esdnl.scrs.domain.IllegalSubstanceType;

public class IllegalSubstanceTypeService {

	private static HashMap<Integer, IllegalSubstanceType> preloaded;

	static {
		try {
			preloaded = getIllegalSubstanceTypeMap();
		}
		catch (BullyingException e) {
			e.printStackTrace();
		}
	}

	public static IllegalSubstanceType getIllegalSubstanceType(int typeId) throws BullyingException {

		IllegalSubstanceType type = null;

		if (preloaded.containsKey(typeId))
			type = preloaded.get(typeId);
		else {
			Connection con = null;
			CallableStatement stat = null;
			ResultSet rs = null;

			try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);

				stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_illegalsubstance_type(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);

				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);

				if (rs.next()) {
					type = IllegalSubstanceTypeService.createIllegalSubstanceType(rs);
					preloaded.put(type.getTypeId(), type);
				}
			}
			catch (SQLException e) {
				e.printStackTrace();

				System.err.println("IllegalSubstanceType getIllegalSubstanceType(int typeId): " + e);
				throw new BullyingException("Can not fetch IllegalSubstanceType to DB.", e);
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

	public static ArrayList<IllegalSubstanceType> getIllegalSubstanceTypes() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<IllegalSubstanceType> types = null;

		try {
			types = new ArrayList<IllegalSubstanceType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_illegalsubstance_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				types.add(IllegalSubstanceTypeService.createIllegalSubstanceType(rs));

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("ArrayList<IllegalSubstanceType> getIllegalSubstanceTypes(): " + e);
			throw new BullyingException("Can not fetch IllegalSubstanceType to DB.", e);
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

	public static HashMap<Integer, IllegalSubstanceType> getIllegalSubstanceTypeMap() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		HashMap<Integer, IllegalSubstanceType> types = null;
		IllegalSubstanceType type = null;

		try {
			types = new HashMap<Integer, IllegalSubstanceType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_illegalsubstance_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				type = IllegalSubstanceTypeService.createIllegalSubstanceType(rs);
				types.put(type.getTypeId(), type);

			}

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("HashMap<Integer, IllegalSubstanceType> getIllegalSubstanceTypeMap(): " + e);
			throw new BullyingException("Can not fetch IllegalSubstanceType to DB.", e);
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

	public static IllegalSubstanceType createIllegalSubstanceType(ResultSet rs) {

		IllegalSubstanceType abean = null;

		try {
			abean = new IllegalSubstanceType();

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
