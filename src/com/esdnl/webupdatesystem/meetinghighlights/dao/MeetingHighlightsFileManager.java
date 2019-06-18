package com.esdnl.webupdatesystem.meetinghighlights.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.meetinghighlights.bean.MeetingHighlightsException;
import com.esdnl.webupdatesystem.meetinghighlights.bean.MeetingHighlightsFileBean;

public class MeetingHighlightsFileManager {
	public static int addMHFile(MeetingHighlightsFileBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_mh_file(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getMHfTitle());
			stat.setString(3, ebean.getMHfDoc());
			stat.setString(4, ebean.getAddedBy());
			stat.setInt(5, ebean.getMHId());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addMHFile(MeetingHighlightsFileBean ebean)" + e);
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
	public static ArrayList<MeetingHighlightsFileBean> getMHFiles(Integer blogid) throws MeetingHighlightsException {
		ArrayList<MeetingHighlightsFileBean> mms = null;
		MeetingHighlightsFileBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new ArrayList<MeetingHighlightsFileBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_mh_files(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, blogid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createMeetingHighlightsFileBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("Vector getMeetingHighlightsFiles() throws MeetingHighlightsException " + e);
			throw new MeetingHighlightsException("Can not extract Meeting Highlights Files from DB: " + e);
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
	public static void deleteMeetingHighlightsFile(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.delete_other_mh_file(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("deleteMeetingHighlightsFile(Integer tid) " + e);
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
	public static MeetingHighlightsFileBean createMeetingHighlightsFileBean(ResultSet rs) {
		MeetingHighlightsFileBean abean = null;
		try {
			abean = new MeetingHighlightsFileBean();
			abean.setId(rs.getInt("ID"));
			abean.setMHfTitle(rs.getString("MHF_TITLE"));
			abean.setMHfDoc(rs.getString("MHF_DOC"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setMHId(rs.getInt("MH_ID"));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
}
