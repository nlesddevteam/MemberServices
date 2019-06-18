package com.esdnl.tsdoc.manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.common.Utils;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.esdnl.dao.DAOUtils;
import com.esdnl.tsdoc.bean.TSDocException;
import com.esdnl.tsdoc.bean.TrusteeNoteBean;

public class TrusteeNoteManager {

	public static TrusteeNoteBean getTrusteeNoteBean(int noteId) throws TSDocException {

		TrusteeNoteBean bean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_trustee_note(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, noteId);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = TrusteeNoteManager.createTrusteeNoteBean(rs);
		}
		catch (SQLException e) {
			System.err.println("TrusteeNoteBean getTrusteeNoteBean(int noteId): " + e);
			throw new TSDocException("Can not extract TrusteeNoteBean from DB.", e);
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

		return bean;
	}

	public static TrusteeNoteBean[] getTrusteeNoteBeans() throws TSDocException {

		Vector<TrusteeNoteBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<TrusteeNoteBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_trustee_notes; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(TrusteeNoteManager.createTrusteeNoteBean(rs));
		}
		catch (SQLException e) {
			System.err.println("TrusteeNoteBean[] getTrusteeNoteBeans(): " + e);
			throw new TSDocException("Can not extract TrusteeNoteBean from DB.", e);
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

		return (TrusteeNoteBean[]) beans.toArray(new TrusteeNoteBean[0]);
	}

	public static TrusteeNoteBean[] getUnreadTrusteeNoteBeans(Personnel personnel) throws TSDocException {

		Vector<TrusteeNoteBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<TrusteeNoteBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_trustee_notes_unread(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, personnel.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(TrusteeNoteManager.createTrusteeNoteBean(rs));
		}
		catch (SQLException e) {
			System.err.println("TrusteeNoteBean[] getUnreadTrusteeNoteBeans(Personnel personnel): " + e);
			throw new TSDocException("Can not extract TrusteeNoteBean from DB.", e);
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

		return (TrusteeNoteBean[]) beans.toArray(new TrusteeNoteBean[0]);
	}

	public static TrusteeNoteBean[] getUnreadGroupPersonnelNoteBeans(Personnel personnel) throws TSDocException {

		Vector<TrusteeNoteBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<TrusteeNoteBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_group_member_notes_unread(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, personnel.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(TrusteeNoteManager.createTrusteeNoteBean(rs));
		}
		catch (SQLException e) {
			System.err.println("TrusteeNoteBean[] getUnreadTrusteeNoteBeans(Personnel personnel): " + e);
			throw new TSDocException("Can not extract TrusteeNoteBean from DB.", e);
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

		return (TrusteeNoteBean[]) beans.toArray(new TrusteeNoteBean[0]);
	}

	public static TrusteeNoteBean[] getGroupPersonnelNoteBeans(Personnel personnel) throws TSDocException {

		Vector<TrusteeNoteBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<TrusteeNoteBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_group_member_notes(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, personnel.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(TrusteeNoteManager.createTrusteeNoteBean(rs));
		}
		catch (SQLException e) {
			System.err.println("TrusteeNoteBean[] getGroupPersonnelNoteBeans(Personnel personnel): " + e);
			throw new TSDocException("Can not extract TrusteeNoteBean from DB.", e);
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

		return (TrusteeNoteBean[]) beans.toArray(new TrusteeNoteBean[0]);
	}

	public static TrusteeNoteBean addTrusteeNoteBean(TrusteeNoteBean abean) throws TSDocException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.add_trustee_note(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.setString(2, abean.getNoteTitle());
			stat.setString(3, abean.getNoteText());

			stat.execute();

			int note_id = ((OracleCallableStatement) stat).getInt(1);

			abean.setNoteId(note_id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addTrusteeGroupBean(TrusteeGroupBean abean): " + e);
			throw new TSDocException("Can not add TrusteeGroupBean to DB.", e);
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

	public static Personnel[] getNotePersonnelBeans(TrusteeNoteBean note) throws TSDocException {

		Vector<Personnel> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<Personnel>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_note_personnel(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, note.getNoteId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(PersonnelDB.createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Personnel[] getNotePersonnelBeans(TrusteeNoteBean note): " + e);
			throw new TSDocException("Can not extract Personnel Beans from DB.", e);
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

		return (Personnel[]) beans.toArray(new Personnel[0]);
	}

	public static void deleteTrusteeNoteBean(int noteId) throws TSDocException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.tsdoc_pkg.del_trustee_note(?); end;");

			stat.setInt(1, noteId);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteTrusteeNoteBean(int noteId): " + e);
			throw new TSDocException("Can not delete TrusteeNoteBean to DB.", e);
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

	public static TreeMap<String, TreeMap<Integer, ArrayList<TrusteeNoteBean>>> categorizeNotesBySchoolYear(TrusteeNoteBean notes[]) {

		TreeMap<String, TreeMap<Integer, ArrayList<TrusteeNoteBean>>> years = new TreeMap<String, TreeMap<Integer, ArrayList<TrusteeNoteBean>>>();
		TreeMap<Integer, ArrayList<TrusteeNoteBean>> year = null;
		ArrayList<TrusteeNoteBean> month = null;

		String sy = "";

		Calendar cal = Calendar.getInstance();
		cal.clear();

		for (TrusteeNoteBean note : notes) {
			cal.setTime(note.getDateAdded());

			sy = Utils.getSchoolYear(cal);

			if (years.containsKey(sy))
				year = years.get(sy);
			else {
				year = new TreeMap<Integer, ArrayList<TrusteeNoteBean>>(new Comparator<Integer>() {

					public int compare(Integer o1, Integer o2) {

						if ((o1 >= 8 && o2 >= 8) || (o1 < 8 && o2 < 8))
							return o1.compareTo(o2);
						else
							return o1.compareTo(o2) * -1;
					}
				});

				years.put(sy, year);
			}

			if (year.containsKey(new Integer(cal.get(Calendar.MONTH))))
				month = year.get(new Integer(cal.get(Calendar.MONTH)));
			else {
				month = new ArrayList<TrusteeNoteBean>();
				year.put(new Integer(cal.get(Calendar.MONTH)), month);
			}

			month.add(note);
		}

		return years;
	}

	public static TrusteeNoteBean createTrusteeNoteBean(ResultSet rs) {

		TrusteeNoteBean abean = null;

		try {
			abean = new TrusteeNoteBean();

			abean.setNoteId(rs.getInt("NOTE_ID"));
			abean.setNoteTitle(rs.getString("NOTE_TITLE"));
			abean.setNoteText(rs.getString("NOTE_TEXT"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("date_added").getTime()));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}
