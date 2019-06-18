package com.esdnl.webupdatesystem.meetingminutes.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.meetingminutes.bean.MeetingMinutesBean;
import com.esdnl.webupdatesystem.meetingminutes.bean.MeetingMinutesException;
import com.esdnl.webupdatesystem.meetingminutes.constants.MeetingCategory;
public class MeetingMinutesManager {
	public static int addNewMeetingMinutes(MeetingMinutesBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_meeting_minutes(?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getmMTitle());
			stat.setTimestamp(3, new Timestamp(ebean.getmMDate().getTime()));
			stat.setString(4, ebean.getmMDoc());
			stat.setString(5, ebean.getmMRelPreTitle());
			stat.setString(6, ebean.getmMRelPreDoc());
			stat.setString(7, ebean.getmMRelDocTitle());
			stat.setString(8, ebean.getmMRelDoc());
			stat.setString(9, ebean.getAddedBy());
			stat.setInt(10, ebean.getMeetingCategory().getValue());
			stat.setString(11, ebean.getmMMeetingVideo());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addNewMeetingMinutes(MeetingMinutesBean ebean) " + e);
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

	public static void updateMeetingMinutes(MeetingMinutesBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.update_meeting_minutes(?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setString(1, ebean.getmMTitle());
			stat.setTimestamp(2, new Timestamp(ebean.getmMDate().getTime()));
			stat.setString(3, ebean.getmMDoc());
			stat.setString(4, ebean.getmMRelPreTitle());
			stat.setString(5, ebean.getmMRelPreDoc());
			stat.setString(6, ebean.getmMRelDocTitle());
			stat.setString(7, ebean.getmMRelDoc());
			stat.setString(8, ebean.getAddedBy());
			stat.setInt(9,ebean.getId());
			stat.setInt(10, ebean.getMeetingCategory().getValue());
			stat.setString(11, ebean.getmMMeetingVideo());
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateMeetingMinutes(MeetingMinutesBean ebean) " + e);
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
	public static Vector<MeetingMinutesBean> getMeetingMinutes() throws MeetingMinutesException {
		Vector<MeetingMinutesBean> mms = null;
		MeetingMinutesBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new Vector<MeetingMinutesBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_meeting_minutes; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createMeetingMinutesBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("MeetingMinutesManager.getMeetingMinutes: " + e);
			throw new MeetingMinutesException("Can not extract Meeting Minutes from DB: " + e);
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
	public static void deleteMeetingMinutes(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.delete_meeting_minutes(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("deleteMeetingMinutes(Integer tid)" + e);
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
	public static MeetingMinutesBean getMeetingMinutesById(int id) throws MeetingMinutesException {
		MeetingMinutesBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_meeting_minutes_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createMeetingMinutesBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("MeetingMinutesManager.getMeetingMinutesById: " + e);
			throw new MeetingMinutesException("Can not extract Meeting Minutes from DB: " + e);
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
		return mm;
	}
	public static MeetingMinutesBean createMeetingMinutesBean(ResultSet rs) {
		MeetingMinutesBean abean = null;
		try {
			abean = new MeetingMinutesBean();
			abean.setId(rs.getInt("ID"));
			abean.setmMTitle(rs.getString("MM_TITLE"));
			abean.setmMDate(new java.util.Date(rs.getTimestamp("MM_DATE").getTime()));
			abean.setmMDoc(rs.getString("MM_DOC"));
			abean.setmMRelPreTitle(rs.getString("MM_REL_PRE_TITLE"));
			abean.setmMRelPreDoc(rs.getString("MM_REL_PRE_DOC"));
			abean.setmMRelDocTitle(rs.getString("MM_REL_DOC_TITLE"));
			abean.setmMRelDoc(rs.getString("MM_REL_DOC"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setMeetingCategory(MeetingCategory.get(rs.getInt("MM_CATEGORY")));
			abean.setOtherMMFiles(MeetingMinutesFileManager.getMHFiles(abean.getId()));
			abean.setmMMeetingVideo(rs.getString("MM_MEETING_VIDEO"));
		}
		catch (SQLException e) {
			abean = null;
		} catch (MeetingMinutesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return abean;
	}	
}
