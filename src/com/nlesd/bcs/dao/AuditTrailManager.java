package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;

public class AuditTrailManager {
	public static void addAuditTrail(AuditTrailBean atbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_audit_trail(?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2,atbean.getEntryType().getValue());
			stat.setInt(3, atbean.getEntryId());
			stat.setInt(4, atbean.getEntryTable().getValue());
			stat.setString(5, atbean.getEntryNotes());
			stat.setInt(6, atbean.getContractorId());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void addAuditTrail(AuditTrailBean atbean): "
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
	}
	public static ArrayList<AuditTrailBean> getAuditEntriesLogins(int cid, Date fdate, Date tdate, int etype) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<AuditTrailBean> list = new ArrayList<AuditTrailBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_audit_logins(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,cid);
			stat.setTimestamp(3, new Timestamp(fdate.getTime()));
			stat.setTimestamp(4, new Timestamp(tdate.getTime()));
			stat.setInt(5,etype);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				AuditTrailBean abean = new AuditTrailBean();
				abean = createAuditTrailBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<AuditTrailBean> getAuditEntriesLogins(int cid, Date fdate, Date tdate, int etype): "
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
	public static ArrayList<AuditTrailBean> getAuditEntriesEmpVeh(int cid, Date fdate, Date tdate, int etypel,int etypeh) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<AuditTrailBean> list = new ArrayList<AuditTrailBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_audit_emp_veh(?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,cid);
			stat.setTimestamp(3, new Timestamp(fdate.getTime()));
			stat.setTimestamp(4, new Timestamp(tdate.getTime()));
			stat.setInt(5,etypel);
			stat.setInt(6,etypeh);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				AuditTrailBean abean = new AuditTrailBean();
				abean = createAuditTrailBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<AuditTrailBean> getAuditEntriesLogins(int cid, Date fdate, Date tdate, int etype): "
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
	public static AuditTrailBean createAuditTrailBean(ResultSet rs) {
		AuditTrailBean abean = null;
		try {
				abean = new AuditTrailBean();
				abean.setId(rs.getInt("ID"));
				abean.setEntryType(EntryTypeConstant.get(rs.getInt("ENTRY_TYPE")));
				abean.setEntryId(rs.getInt("ENTRY_ID"));
				abean.setEntryTable(EntryTableConstant.get(rs.getInt("ENTRY_TABLE")));
				abean.setEntryNotes(rs.getString("ENTRY_NOTES"));
				abean.setEntryDate(new java.util.Date(rs.getTimestamp("ENTRY_DATE").getTime()));
				abean.setContractorId(rs.getInt("CONTRACTOR_ID"));
				if(abean.getContractorId() > 0){
					abean.setBcBean(BussingContractorManager.getBussingContractorById(abean.getContractorId()));
				}else{
					abean.setBcBean(null);
				}
				if(abean.getEntryTable() == EntryTableConstant.CONTRACTORCOMPANY){
					abean.setViewUrl("loadMainDivPage('adminViewContractorArchive.html?cid=" + abean.getEntryId() + "')");
				}else if((abean.getEntryTable() == EntryTableConstant.CONTRACTORVEHICLE)){
					abean.setViewUrl("loadMainDivPage('adminViewVehicleArchive.html?cid=" + abean.getEntryId() + "')");
				}else if((abean.getEntryTable() == EntryTableConstant.CONTRACTOREMPLOYEE)){
					abean.setViewUrl("loadMainDivPage('adminViewEmployeeArchive.html?cid=" + abean.getEntryId() + "')");
				}else{
					abean.setViewUrl("");
				}
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
	
}	

