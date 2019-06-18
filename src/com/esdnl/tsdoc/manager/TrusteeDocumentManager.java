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
import com.esdnl.tsdoc.bean.TrusteeDocumentBean;

public class TrusteeDocumentManager {

	public static TrusteeDocumentBean addTrusteeDocumentBean(TrusteeDocumentBean abean) throws TSDocException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.add_trustee_doc(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.setString(2, abean.getDocumentTitle());
			stat.setString(3, abean.getFilename());

			stat.execute();

			int doc_id = ((OracleCallableStatement) stat).getInt(1);

			abean.setDocumentId(doc_id);
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

	public static TrusteeDocumentBean getTrusteeDocumentBean(int documentId) throws TSDocException {

		TrusteeDocumentBean bean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_trustee_doc(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, documentId);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = TrusteeDocumentManager.createTrusteeDocumentBean(rs);
		}
		catch (SQLException e) {
			System.err.println("TrusteeDocumentBean getTrusteeDocumentBean(int documentId): " + e);
			throw new TSDocException("Can not extract TrusteeDocumentBean from DB.", e);
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

	public static TrusteeDocumentBean[] getTrusteeDocumentBeans() throws TSDocException {

		Vector<TrusteeDocumentBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<TrusteeDocumentBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_trustee_docs; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(TrusteeDocumentManager.createTrusteeDocumentBean(rs));
		}
		catch (SQLException e) {
			System.err.println("TrusteeDocumentBean[] getTrusteeDocumentBeans(): " + e);
			throw new TSDocException("Can not extract TrusteeDocumentBean from DB.", e);
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

		return (TrusteeDocumentBean[]) beans.toArray(new TrusteeDocumentBean[0]);
	}

	public static TrusteeDocumentBean[] getUnreadTrusteeDocumentBeans(Personnel personnel) throws TSDocException {

		Vector<TrusteeDocumentBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<TrusteeDocumentBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_trustee_docs_unread(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, personnel.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(TrusteeDocumentManager.createTrusteeDocumentBean(rs));
		}
		catch (SQLException e) {
			System.err.println("TrusteeDocumentBean[] getUnreadTrusteeDocumentBeans(Personnel personnel): " + e);
			throw new TSDocException("Can not extract TrusteeDocumentBean from DB.", e);
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

		return (TrusteeDocumentBean[]) beans.toArray(new TrusteeDocumentBean[0]);
	}

	public static TrusteeDocumentBean[] getUnreadGroupPersonnelDocumentBeans(Personnel personnel) throws TSDocException {

		Vector<TrusteeDocumentBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<TrusteeDocumentBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_group_member_docs_unread(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, personnel.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(TrusteeDocumentManager.createTrusteeDocumentBean(rs));
		}
		catch (SQLException e) {
			System.err.println("TrusteeDocumentBean[] getUnreadGroupPersonnelDocumentBeans(Personnel personnel): " + e);
			throw new TSDocException("Can not extract TrusteeDocumentBean from DB.", e);
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

		return (TrusteeDocumentBean[]) beans.toArray(new TrusteeDocumentBean[0]);
	}

	public static TrusteeDocumentBean[] getGroupPersonnelDocumentBeans(Personnel personnel) throws TSDocException {

		Vector<TrusteeDocumentBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<TrusteeDocumentBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_group_member_docs(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, personnel.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(TrusteeDocumentManager.createTrusteeDocumentBean(rs));
		}
		catch (SQLException e) {
			System.err.println("TrusteeDocumentBean[] getGroupPersonnelDocumentBeans(Personnel personnel): " + e);
			throw new TSDocException("Can not extract TrusteeDocumentBean from DB.", e);
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

		return (TrusteeDocumentBean[]) beans.toArray(new TrusteeDocumentBean[0]);
	}

	public static Personnel[] getDocumentPersonnelBeans(TrusteeDocumentBean doc) throws TSDocException {

		Vector<Personnel> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<Personnel>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_doc_personnel(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, doc.getDocumentId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(PersonnelDB.createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Personnel[] getDocumentPersonnelBeans(TrusteeDocumentBean doc): " + e);
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

	public static void deleteTrusteeDocumentBean(int documentId) throws TSDocException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.tsdoc_pkg.del_trustee_doc(?); end;");

			stat.setInt(1, documentId);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteTrusteeDocumentBean(int documentId): " + e);
			throw new TSDocException("Can not delete TrusteeDocumentBean to DB.", e);
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

	public static TreeMap<String, TreeMap<Integer, ArrayList<TrusteeDocumentBean>>> categorizeDocumentsBySchoolYear(TrusteeDocumentBean docs[]) {

		TreeMap<String, TreeMap<Integer, ArrayList<TrusteeDocumentBean>>> years = new TreeMap<String, TreeMap<Integer, ArrayList<TrusteeDocumentBean>>>();
		TreeMap<Integer, ArrayList<TrusteeDocumentBean>> year = null;
		ArrayList<TrusteeDocumentBean> month = null;

		String sy = "";

		Calendar cal = Calendar.getInstance();
		cal.clear();

		for (TrusteeDocumentBean doc : docs) {
			cal.setTime(doc.getDateAdded());

			sy = Utils.getSchoolYear(cal);

			if (years.containsKey(sy))
				year = years.get(sy);
			else {
				year = new TreeMap<Integer, ArrayList<TrusteeDocumentBean>>(new Comparator<Integer>() {

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
				month = new ArrayList<TrusteeDocumentBean>();
				year.put(new Integer(cal.get(Calendar.MONTH)), month);
			}

			month.add(doc);
		}

		return years;
	}

	public static TrusteeDocumentBean createTrusteeDocumentBean(ResultSet rs) {

		TrusteeDocumentBean abean = null;

		try {
			abean = new TrusteeDocumentBean();

			abean.setDocumentId(rs.getInt("DOC_ID"));
			abean.setDocumentTitle(rs.getString("DOC_TITLE"));
			abean.setFilename(rs.getString("FILENAME"));
			abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
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
