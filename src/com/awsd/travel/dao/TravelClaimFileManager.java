package com.awsd.travel.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.awsd.travel.TravelClaimException;
import com.awsd.travel.bean.TravelClaimFileBean;
import com.esdnl.dao.DAOUtils;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class TravelClaimFileManager {
	public static ArrayList<TravelClaimFileBean> getTravelClaimFiles(int claimid) {
		TravelClaimFileBean eBean = null;
		ArrayList<TravelClaimFileBean> list = new ArrayList<TravelClaimFileBean>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_km_rates(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, claimid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				eBean = createTravelClaimFileBean(rs);
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
	public static void addTravelClaimFile(TravelClaimFileBean fbean) throws TravelClaimException{
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.add_travel_claim_file(?, ?, ?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, fbean.getFilePath());
			stat.setInt(3, fbean.getClaimId());
			stat.setInt(4, fbean.getItemId());
			stat.setString(5, fbean.getFileNotes());
			stat.execute();
			fbean.setId(((OracleCallableStatement) stat).getInt(1));
			
		}
		catch (SQLException e) {
			throw new TravelClaimException("Can not add travel claim file  to DB: " + e);
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
	public static boolean deleteTravelClaimFile(String filepath, int itemid) throws TravelClaimException{
		Connection con = null;
		CallableStatement stat = null;
		boolean deleted=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.delete_claim_item_att(?, ?); end;");
			stat.setInt(1, itemid);
			stat.setString(2, filepath);
			stat.execute();
			deleted=true;
		}
		catch (SQLException e) {
			deleted=false;
			throw new TravelClaimException("deleteTravelClaimFile(String filepath, int itemid): " + e);
			
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
		return deleted;
	}
	public static TravelClaimFileBean getTravelClaimFileById(int fileid) {
		TravelClaimFileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claim_file_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, fileid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				eBean = createTravelClaimFileBean(rs);
				
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimFileBean getTravelClaimFileById(int fileid): " + e);
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

		return eBean;
	}
	public static void deleteTravelClaimFileById(int fileid) throws TravelClaimException{
		Connection con = null;
		CallableStatement stat = null;
		boolean deleted=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.delete_claim_file_by_id(?); end;");
			stat.setInt(1, fileid);
			stat.execute();
			deleted=true;
		}
		catch (SQLException e) {
			deleted=false;
			throw new TravelClaimException("deleteTravelClaimFileById(int fileid): " + e);
			
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
	public static void updateTravelClaimFilesById(int newid, int oldid) throws TravelClaimException{
		Connection con = null;
		CallableStatement stat = null;
		boolean deleted=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.update_claim_file_by_id(?,?); end;");
			stat.setInt(1, newid);
			stat.setInt(2, oldid);
			stat.execute();
			deleted=true;
		}
		catch (SQLException e) {
			deleted=false;
			throw new TravelClaimException("updateTravelClaimFilesById(int newid, int oldid): " + e);
			
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
	public static TravelClaimFileBean createTravelClaimFileBean(ResultSet rs) {
		TravelClaimFileBean abean = null;
		try {
			abean = new TravelClaimFileBean();
			abean.setId(rs.getInt("CFILEID"));
			abean.setFilePath(rs.getString("FILE_PATH"));
			abean.setDateUploaded(new java.util.Date(rs.getTimestamp("DATE_UPLOADED").getTime()));
			abean.setClaimId(rs.getInt("CLAIM_ID"));
			abean.setItemId(rs.getInt("ITEM_ID"));
			abean.setFileNotes(rs.getString("FILE_NOTES"));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}	
}
