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
import com.esdnl.scrs.domain.LocationType;

public class LocationTypeService {

	private static HashMap<Integer, LocationType> preloaded;

	static {
		try {
			preloaded = getLocationTypesMap();
		}
		catch (BullyingException e) {
			e.printStackTrace();
		}
	}

	public static LocationType getLocationType(int typeId) throws BullyingException {

		LocationType type = null;

		if (preloaded.containsKey(typeId))
			type = preloaded.get(typeId);
		else {
			Connection con = null;
			CallableStatement stat = null;
			ResultSet rs = null;

			try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);

				stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_location_type(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);

				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);

				if (rs.next()) {
					type = LocationTypeService.createLocationType(rs);
					preloaded.put(type.getTypeId(), type);
				}
			}
			catch (SQLException e) {
				e.printStackTrace();

				System.err.println("LocationType getLocationType(int typeId): " + e);
				throw new BullyingException("Can not fetch LocationType to DB.", e);
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

	public static ArrayList<LocationType> getLocationTypes() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<LocationType> types = null;

		try {
			types = new ArrayList<LocationType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_location_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				types.add(LocationTypeService.createLocationType(rs));

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("ArrayList<LocationType> getLocationTypes(): " + e);
			throw new BullyingException("Can not fetch LocationType to DB.", e);
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

	public static HashMap<Integer, LocationType> getLocationTypesMap() throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		HashMap<Integer, LocationType> types = null;
		LocationType loc = null;

		try {
			types = new HashMap<Integer, LocationType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_location_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				loc = LocationTypeService.createLocationType(rs);
				types.put(loc.getTypeId(), loc);
			}

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("HashMap<Integer, LocationType> getLocationTypesMap(): " + e);
			throw new BullyingException("Can not fetch LocationType to DB.", e);
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

	public static LocationType createLocationType(ResultSet rs) {

		LocationType abean = null;

		try {
			abean = new LocationType();

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
