package com.awsd.travel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;

public class TravelClaimKMRateDB {
	public static ArrayList<TravelClaimKMRate> getTravelClaimKMRates() {

		TravelClaimKMRate eBean = null;
		ArrayList<TravelClaimKMRate> list = new ArrayList<TravelClaimKMRate>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_km_rates; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTravelClaimKMRate(rs);
				list.add(eBean);
			}

		}
		catch (SQLException e) {
			System.err.println("static ArrayList<TravelClaimKMRate> getTravelClaimKMRates(): " + e);
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

		return list;
	}
	public static boolean addClaimKmRate(TravelClaimKMRate trate) throws TravelClaimException{
		
		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;
		
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.insert_km_rate(?, ?, ?, ?); end;");
			stat.setDate(1, new Date(trate.getEffectiveStartDate().getTime()));
			stat.setDouble(2, trate.getBaseRate());
			stat.setDouble(3, trate.getApprovedRate());
			stat.setDate(4, new Date(trate.getEffectiveEndDate().getTime()));
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("boolean addClaimItem(TravelClaimKMRate trate): " + e);
			throw new TravelClaimException("Can not add travel claim km rate to DB: " + e);
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
		return (check);
	}	
	public static TravelClaimKMRate createTravelClaimKMRate(ResultSet rs) {

		TravelClaimKMRate abean = null;

		try {
			abean = new TravelClaimKMRate();
			abean.setApprovedRate(rs.getDouble("APPROVED"));
			abean.setBaseRate(rs.getDouble("BASE"));
			abean.setEffectiveStartDate(new java.util.Date(rs.getTimestamp("EFFECTIVE_STARTDATE").getTime()));
			abean.setEffectiveEndDate(new java.util.Date(rs.getTimestamp("EFFECTIVE_ENDDATE").getTime()));

		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
	public static TravelClaimKMRate getTravelClaimKMRate(String sdate,String edate) throws TravelClaimException {

		TravelClaimKMRate item = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_travel_claim_km_rate(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sdate);
			stat.setString(3, edate);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				item = createTravelClaimKMRate(rs);
			}else {
				item = null;
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimKMRate getTravelClaimKMRate(Date sdate,Date edate) " + e);
			throw new TravelClaimException("Can not extract travel claim km rate from DB: " + e);
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
		return item;
	}
	public static boolean deleteTravelClaimKMRate(String sdate,String edate) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.delete_km_rate(?,?); end;");
			stat.setString(1, sdate);
			stat.setString(2, edate);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("boolean deleteTravelClaimKMRate(Date sdate,Date edate) " + e);
			throw new TravelClaimException("Can not delete travel claim km rate from DB: " + e);
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
		return check;
	}	
	public static boolean updateClaimKmRate(TravelClaimKMRate trate) throws TravelClaimException{
		
		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;
		
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.update_km_rate(?, ?, ?, ?); end;");
			stat.setDate(1, new Date(trate.getEffectiveStartDate().getTime()));
			stat.setDouble(2, trate.getBaseRate());
			stat.setDouble(3, trate.getApprovedRate());
			stat.setDate(4, new Date(trate.getEffectiveEndDate().getTime()));
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("boolean updateClaimItem(TravelClaimKMRate trate): " + e);
			throw new TravelClaimException("Can not update travel claim km rate to DB: " + e);
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
		return (check);
	}		
}
