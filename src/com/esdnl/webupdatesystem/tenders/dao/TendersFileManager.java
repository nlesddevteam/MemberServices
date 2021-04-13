package com.esdnl.webupdatesystem.tenders.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.tenders.bean.TenderException;
import com.esdnl.webupdatesystem.tenders.bean.TendersFileBean;

public class TendersFileManager {
	public static int addTendersFile(TendersFileBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_tenders_file(?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getTfTitle());
			stat.setString(3, ebean.getTfDoc());
			stat.setString(4, ebean.getAddedBy());
			stat.setInt(5, ebean.getTenderId());
			stat.setTimestamp(6, new Timestamp(ebean.getAddendumDate().getTime()));
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addTendersFile(TendersFileBean ebean)" + e);
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
	public static ArrayList<TendersFileBean> getTendersFiles(Integer tid) throws TenderException {
		ArrayList<TendersFileBean> mms = null;
		TendersFileBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new ArrayList<TendersFileBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_tenders_files(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, tid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createTendersFileBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<TendersFileBean> getTendersFiles(Integer tid) " + e);
			throw new TenderException("Can not extract Tenders Files from DB: " + e);
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
		return mms;
	}
	public static void deleteTendersFile(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.delete_other_tenders_file(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void deleteTendersFile(Integer tid) " + e);
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
	public static TendersFileBean createTendersFileBean(ResultSet rs) {
		TendersFileBean abean = null;
		try {
			abean = new TendersFileBean();
			abean.setId(rs.getInt("ID"));
			abean.setTfTitle(rs.getString("TF_TITLE"));
			abean.setTfDoc(rs.getString("TF_DOC"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setTenderId(rs.getInt("TENDER_ID"));
			abean.setAddendumDate(new java.util.Date(rs.getTimestamp("ADDENDUM_DATE").getTime()));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
	public static TendersFileBean createTendersFileBeanFull(ResultSet rs) {
		TendersFileBean abean = null;
		try {
			abean = new TendersFileBean();
			abean.setId(rs.getInt("FILEID"));
			abean.setTfTitle(rs.getString("TF_TITLE"));
			abean.setTfDoc(rs.getString("TF_DOC"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setTenderId(rs.getInt("TENDER_ID"));
			abean.setAddendumDate(new java.util.Date(rs.getTimestamp("ADDENDUM_DATE").getTime()));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
}
