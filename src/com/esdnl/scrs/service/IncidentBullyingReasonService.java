package com.esdnl.scrs.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import com.esdnl.dao.DAOUtils;
import com.esdnl.scrs.domain.BullyingException;
import com.esdnl.scrs.domain.BullyingReasonType;
import com.esdnl.scrs.domain.IncidentBean;

public class IncidentBullyingReasonService {

	public static ArrayList<BullyingReasonType> getBullyingIncidentReasons(IncidentBean abean) throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BullyingReasonType> types = null;

		try {
			types = new ArrayList<BullyingReasonType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_incident_reasons(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, abean.getIncidentId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				types.add(BullyingReasonTypeService.createBullyingReasonType(rs));

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("ArrayList<BullyingReasonType> getBullyingIncidentReasons(BullyingIncidentBean abean): " + e);
			throw new BullyingException("Can not fetch Incident BullyingReasonType to DB.", e);
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

	public static void addBullyingIncidentReasons(IncidentBean abean) throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			ArrayList<Integer> typeIds = new ArrayList<Integer>();
			ArrayList<String> specified = new ArrayList<String>();

			for (BullyingReasonType type : abean.getBullyingReasonTypes()) {
				typeIds.add(type.getTypeId());
				specified.add(type.getSpecified());
			}

			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin  awsd_user.bullying_pkg.add_incident_reasons(?,?,?); end;");
			stat.setInt(1, abean.getIncidentId());
			stat.setArray(
					2,
					new ARRAY(ArrayDescriptor.createDescriptor("NUM_ARRAY", con), con, (Integer[]) typeIds.toArray(new Integer[0])));
			stat.setArray(
					3,
					new ARRAY(ArrayDescriptor.createDescriptor("STRING_ARRAY", con), con, (String[]) specified.toArray(new String[0])));

			stat.execute();
			stat.close();

			con.commit();

			abean.setBullyingReasonTypes(getBullyingIncidentReasons(abean));
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addBullyingIncidentReasons(BullyingIncidentBean abean): " + e);
			throw new BullyingException("Can not add 'Incident Reason Types' to DB.", e);
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

}
