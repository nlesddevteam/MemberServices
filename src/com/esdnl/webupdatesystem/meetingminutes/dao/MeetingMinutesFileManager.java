package com.esdnl.webupdatesystem.meetingminutes.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.meetingminutes.bean.MeetingMinutesException;
import com.esdnl.webupdatesystem.meetingminutes.bean.MeetingMinutesFileBean;

public class MeetingMinutesFileManager {
	public static int addMMFile(MeetingMinutesFileBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_mm_file(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getMMfTitle());
			stat.setString(3, ebean.getMMfDoc());
			stat.setString(4, ebean.getAddedBy());
			stat.setInt(5, ebean.getMMId());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addMMFile(MeetingMinutesFileBean ebean)" + e);
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
	public static ArrayList<MeetingMinutesFileBean> getMHFiles(Integer blogid) throws MeetingMinutesException {
		ArrayList<MeetingMinutesFileBean> mms = null;
		MeetingMinutesFileBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new ArrayList<MeetingMinutesFileBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_mm_files(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, blogid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createMeetingMinutesFileBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("Vector getMeetingMinutesFiles() throws MeetingMinutesException " + e);
			throw new MeetingMinutesException("Can not extract Meeting Minutes Files from DB: " + e);
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
	public static void deleteMeetingMinutesFile(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.delete_other_mm_file(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("deleteMeetingMinutesFile(Integer tid) " + e);
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
	public static MeetingMinutesFileBean createMeetingMinutesFileBean(ResultSet rs) {
		MeetingMinutesFileBean abean = null;
		try {
			abean = new MeetingMinutesFileBean();
			abean.setId(rs.getInt("ID"));
			abean.setMMfTitle(rs.getString("MMF_TITLE"));
			abean.setMMfDoc(rs.getString("MMF_DOC"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setMMId(rs.getInt("MM_ID"));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
}
