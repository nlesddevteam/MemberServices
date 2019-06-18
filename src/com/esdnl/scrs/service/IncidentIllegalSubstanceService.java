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
import com.esdnl.scrs.domain.IllegalSubstanceType;
import com.esdnl.scrs.domain.IncidentBean;

public class IncidentIllegalSubstanceService {

	public static ArrayList<IllegalSubstanceType> getIncidentIllegalSubstances(IncidentBean abean)
			throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<IllegalSubstanceType> types = null;

		try {
			types = new ArrayList<IllegalSubstanceType>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_incident_illegalsubstances(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, abean.getIncidentId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				types.add(IllegalSubstanceTypeService.createIllegalSubstanceType(rs));

			return types;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("ArrayList<IllegalSubstanceType> getIncidentIllegalSubstances(IncidentBean abean): " + e);
			throw new BullyingException("Can not fetch Incident IllegalSubstanceType to DB.", e);
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

	public static void addIncidentIllegalSubstances(IncidentBean abean) throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			ArrayList<Integer> typeIds = new ArrayList<Integer>();
			ArrayList<String> others = new ArrayList<String>();

			for (IllegalSubstanceType type : abean.getIllegalSubstanceTypes()) {
				typeIds.add(type.getTypeId());
				others.add(type.getSpecified());
			}

			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin  awsd_user.bullying_pkg.add_incident_illegalsubstances(?,?,?); end;");
			stat.setInt(1, abean.getIncidentId());
			stat.setArray(
					2,
					new ARRAY(ArrayDescriptor.createDescriptor("NUM_ARRAY", con), con, (Integer[]) typeIds.toArray(new Integer[0])));
			stat.setArray(
					3,
					new ARRAY(ArrayDescriptor.createDescriptor("STRING_ARRAY", con), con, (String[]) others.toArray(new String[0])));

			stat.execute();

			con.commit();

			abean.setIllegalSubstanceTypes(getIncidentIllegalSubstances(abean));
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addIncidentIllegalSubstances(IncidentBean abean): " + e);
			throw new BullyingException("Can not add 'Incident IllegalSubstanceType' to DB.", e);
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
