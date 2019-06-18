package com.esdnl.webupdatesystem.meetinghighlights.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.webupdatesystem.meetinghighlights.bean.MeetingHighlightsBean;
import com.esdnl.webupdatesystem.meetinghighlights.bean.MeetingHighlightsException;
public class MeetingHighlightsManager {
	public static int addNewMeetingHighlights(MeetingHighlightsBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.add_new_meeting_highlights(?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getmHTitle());
			stat.setTimestamp(3, new Timestamp(ebean.getmHDate().getTime()));
			stat.setString(4, ebean.getmHDoc());
			stat.setString(5, ebean.getmHRelPreTitle());
			stat.setString(6, ebean.getmHRelPreDoc());
			stat.setString(7, ebean.getmHRelDocTitle());
			stat.setString(8, ebean.getmHRelDoc());
			stat.setString(9, ebean.getAddedBy());
			stat.setString(10, ebean.getmHMeetingVideo());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addNewMeetingHighlights(MeetingHighlightsBean ebean) " + e);
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

	public static void updateMeetingHighlights(MeetingHighlightsBean ebean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.update_meeting_highlights(?,?,?,?,?,?,?,?,?,?); end;");
			stat.setString(1, ebean.getmHTitle());
			stat.setTimestamp(2, new Timestamp(ebean.getmHDate().getTime()));
			stat.setString(3, ebean.getmHDoc());
			stat.setString(4, ebean.getmHRelPreTitle());
			stat.setString(5, ebean.getmHRelPreDoc());
			stat.setString(6, ebean.getmHRelDocTitle());
			stat.setString(7, ebean.getmHRelDoc());
			stat.setString(8, ebean.getAddedBy());
			stat.setInt(9,ebean.getId());
			stat.setString(10, ebean.getmHMeetingVideo());
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateMeetingHighlights(MeetingHighlightsBean ebean) " + e);
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
	public static Vector<MeetingHighlightsBean> getMeetingHighlights() throws MeetingHighlightsException {
		Vector<MeetingHighlightsBean> mms = null;
		MeetingHighlightsBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			mms = new Vector<MeetingHighlightsBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_meeting_highlights; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createMeetingHighlightsBean(rs);
				mms.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("MeetingHighlightsManager.getMeetingHighlights: " + e);
			throw new MeetingHighlightsException("Can not extract Meeting Highlights from DB: " + e);
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
	public static void deleteMeetingHighlights(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.web_update_system_pkg.delete_meeting_highlights(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("deleteMeetingHighlights(Integer tid)" + e);
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
	public static MeetingHighlightsBean getMeetingHighlightsById(int id) throws MeetingHighlightsException {
		MeetingHighlightsBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.web_update_system_pkg.get_meeting_highlights_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createMeetingHighlightsBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("MeetingHighlightsManager.getMeetingHighlightsById: " + e);
			throw new MeetingHighlightsException("Can not extract Meeting Highlights from DB: " + e);
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
	public static MeetingHighlightsBean createMeetingHighlightsBean(ResultSet rs) {
		MeetingHighlightsBean abean = null;
		try {
			abean = new MeetingHighlightsBean();
			abean.setId(rs.getInt("ID"));
			abean.setmHTitle(rs.getString("HIGHLIGHTS_TITLE"));
			abean.setmHDate(new java.util.Date(rs.getTimestamp("HIGHLIGHTS_DATE").getTime()));
			abean.setmHDoc(rs.getString("HIGHLIGHTS_DOC"));
			abean.setmHRelPreTitle(rs.getString("HIGHLIGHTS_PRE_TITLE"));
			abean.setmHRelPreDoc(rs.getString("HIGHLIGHTS_PRE_DOC"));
			abean.setmHRelDocTitle(rs.getString("HIGHLIGHTS_R_DOC_TITLE"));
			abean.setmHRelDoc(rs.getString("HIGHLIGHTS_R_DOC"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			abean.setOtherMHFiles(MeetingHighlightsFileManager.getMHFiles(abean.getId()));
			abean.setmHMeetingVideo(rs.getString("HIGHLIGHTS_MEETING_VIDEO"));
		}
		catch (SQLException e) {
			abean = null;
		} catch (MeetingHighlightsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return abean;
	}	
}
