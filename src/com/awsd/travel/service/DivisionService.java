package com.awsd.travel.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.PersonnelDB;
import com.awsd.travel.TravelClaimException;
import com.awsd.travel.bean.Division;
import com.esdnl.dao.DAOUtils;

public class DivisionService {

	private static HashMap<Integer, Division> divisions;

	static {
		divisions = new HashMap<Integer, Division>();
		try {
			for (Division d : DivisionService.getDivisions())
				divisions.put(new Integer(d.getId()), d);
		}
		catch (TravelClaimException e) {
			e.printStackTrace();
		}
	}

	public static Collection<Division> getDivisions() throws TravelClaimException {

		ArrayList<Division> divisions = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			divisions = new ArrayList<Division>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_divisions; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				divisions.add(createDivision(rs));

		}
		catch (SQLException e) {
			System.err.println("Collection<Division> getDivisions(): " + e);
			throw new TravelClaimException("Can not extract Division from DB.");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

		return divisions;
	}

	public static Division getDivision(int id) throws TravelClaimException {

		Division division = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		if (divisions != null && divisions.containsKey(new Integer(id)))
			division = divisions.get(new Integer(id));
		else {
			try {

				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_division(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();

				rs = ((OracleCallableStatement) stat).getCursor(1);

				if (rs.next())
					division = createDivision(rs);

			}
			catch (SQLException e) {
				System.err.println("Division getDivision(int id): " + e);
				throw new TravelClaimException("Can not extract Division from DB.");
			}
			finally {
				try {
					rs.close();
				}
				catch (Exception e) {}
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

		return division;
	}

	public static Division createDivision(ResultSet rs) {

		Division abean = null;

		try {
			abean = new Division();

			abean.setId(rs.getInt("DIVISION_ID"));
			abean.setName(rs.getString("DIVISION_NAME"));
			abean.setAssistantDirector(PersonnelDB.createPersonnelBean(rs));

		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}

}
