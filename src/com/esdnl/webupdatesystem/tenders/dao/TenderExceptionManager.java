package com.esdnl.webupdatesystem.tenders.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.tenders.bean.TenderExceptionBean;
import com.esdnl.webupdatesystem.tenders.constants.TenderRenewal;

import oracle.jdbc.OracleTypes;

public class TenderExceptionManager {
	public static int addNewTenderException(TenderExceptionBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_tender_exc(?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setInt(2, ebean.getTenderId());
			stat.setString(3, ebean.getVendorName());
			stat.setString(4, ebean.geteDescription());
			stat.setString(5, ebean.geteAddress());
			stat.setString(6, ebean.geteLocation());
			stat.setString(7,ebean.getePrice());
			stat.setString(8, ebean.getPoNumber());
			stat.setString(9, ebean.geteTerms());
			stat.setInt(10, ebean.getTenderRenewal().getValue());
			stat.setString(11, ebean.getRenewalother());
			stat.setString(12, ebean.geteClause());
			stat.setString(13, ebean.getAddedBy());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addNewTenderException(TenderExceptionBean ebean) " + e);
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
		return id;
	}
	public static void updateTenderException(TenderExceptionBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.update_tender_exc(?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setInt(1, ebean.getTenderId());
			stat.setString(2, ebean.getVendorName());
			stat.setString(3, ebean.geteDescription());
			stat.setString(4, ebean.geteAddress());
			stat.setString(5, ebean.geteLocation());
			stat.setString(6,ebean.getePrice());
			stat.setString(7, ebean.getPoNumber());
			stat.setString(8, ebean.geteTerms());
			stat.setInt(9, ebean.getTenderRenewal().getValue());
			stat.setString(10, ebean.getRenewalother());
			stat.setString(11, ebean.geteClause());
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateTenderException(TenderExceptionBean ebean)" + e);
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
	public static TenderExceptionBean createTenderExceptionBean(ResultSet rs) {
		TenderExceptionBean abean = null;
		try {
			
			abean = new TenderExceptionBean();
			abean.setWteId(rs.getInt("WTE_ID"));
			abean.setTenderId(rs.getInt("TENDER_ID"));
			abean.setVendorName(rs.getString("VENDOR_NAME"));
			abean.seteDescription(rs.getString("EDESCRIPTION"));
			abean.seteAddress(rs.getString("EADDRESS"));
			abean.seteLocation(rs.getString("ELOCATION"));
			abean.setePrice(rs.getString("EPRICE"));
			abean.setPoNumber(rs.getString("PO_NUMBER"));
			abean.seteTerms(rs.getString("ETERMS"));
			abean.setTenderRenewal(TenderRenewal.get(rs.getInt("ERENEWAL")));
			abean.setRenewalother(rs.getString("ERENEWAL_OTHER"));
			abean.seteClause(rs.getString("ECLAUSE"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}	
}
