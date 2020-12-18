package com.awsd.travel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import com.awsd.travel.bean.PDTravelClaimEventBean;
import com.esdnl.dao.DAOUtils;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class PDTravelClaimEventManager {
	public static ArrayList<PDTravelClaimEventBean> getAllPDEventsByUser(int pid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<PDTravelClaimEventBean> list = new ArrayList<PDTravelClaimEventBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.travel_claim_sys.get_pd_events_by_user(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				PDTravelClaimEventBean abean = new PDTravelClaimEventBean();
				abean = createPDTravelClaimEventBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<PDTravelClaimEventBean> getAllPDEventsByUser(int pid): "
					+ e);
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
		
		return list;
	}
	public static TreeMap<String,Long> getAllPDEventsByUserTM(int pid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<String,Long> list = new TreeMap<String,Long>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.travel_claim_sys.get_pd_events_by_user(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				list.put(rs.getString("EVENT_NAME"),rs.getLong("EVENT_ID"));
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("TreeMap<String,Long> getAllPDEventsByUserTM(int pid): "
					+ e);
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
		
		return list;
	}
	public static PDTravelClaimEventBean getPDEventById(int pid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		PDTravelClaimEventBean abean = new PDTravelClaimEventBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.travel_claim_sys.get_pd_events_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				abean = createPDTravelClaimEventBean(rs);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("PDTravelClaimEventBean getPDEventById(int pid): "
					+ e);
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
		
		return abean;
	}	
	public static PDTravelClaimEventBean createPDTravelClaimEventBean(ResultSet rs) {
		PDTravelClaimEventBean abean = null;
		try {
				abean = new PDTravelClaimEventBean();
				abean.setEventId(rs.getLong("EVENT_ID"));
				abean.setEventName(rs.getString("EVENT_NAME"));
				abean.setAttendedEvent(rs.getInt("ATTENDED"));
				abean.setAttendeeId(rs.getLong("PERSONNEL_ID"));
				abean.setSchedulerId(rs.getLong("SCHEDULER_ID"));
				
				if(!(rs.getTimestamp("EVENT_DATE") ==  null)){
					abean.setEventStartDate(new java.util.Date(rs.getTimestamp("EVENT_DATE").getTime()));
				}
				if(!(rs.getTimestamp("EVENT_ENDDATE") ==  null)){
					abean.setEventEndDate(new java.util.Date(rs.getTimestamp("EVENT_ENDDATE").getTime()));
				}
				abean.setEventDescription(rs.getString("EVENT_DESC"));
			}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
