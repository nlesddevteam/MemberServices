package com.esdnl.webmaint.ceoweb.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.webmaint.ceoweb.bean.CeoWebException;
import com.esdnl.webmaint.ceoweb.bean.DirectorReportBean;

public class DirectorReportManager {

	public static DirectorReportBean[] getDirectorReportBeans() throws CeoWebException {

		Vector<DirectorReportBean> v_opps = null;
		DirectorReportBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<DirectorReportBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.ceo_web.get_director_reports; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createDirectorReportBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("DirectorReportManager.getDirectorReportBeans(): " + e);
			throw new CeoWebException("Can not extract DirectorReportBean from DB.", e);
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

		return (DirectorReportBean[]) v_opps.toArray(new DirectorReportBean[0]);
	}

	public static DirectorReportBean getDirectorReportBean(java.util.Date rpt_date) throws CeoWebException {

		DirectorReportBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.ceo_web.get_director_report(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setDate(2, new java.sql.Date(rpt_date.getTime()));
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createDirectorReportBean(rs);

		}
		catch (SQLException e) {
			System.err.println("DirectorReportManager.getDirectorReportBeans(): " + e);
			throw new CeoWebException("Can not extract DirectorReportBean from DB.", e);
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

		return eBean;
	}

	public static TreeMap<Integer, Vector<DirectorReportBean>> getDirectorReportBeansMap() throws CeoWebException {

		TreeMap<Integer, Vector<DirectorReportBean>> years = null;
		Vector<DirectorReportBean> v_opps = null;
		DirectorReportBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		Calendar cal = null;

		try {
			years = new TreeMap<Integer, Vector<DirectorReportBean>>(new Comparator<Integer>() {

				public int compare(Integer o1, Integer o2) {

					return o1.compareTo(o2) * -1;
				}
			});

			cal = Calendar.getInstance();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.ceo_web.get_director_reports; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createDirectorReportBean(rs);

				cal.setTime(eBean.getReportDate());

				if (years.containsKey(new Integer(cal.get(Calendar.YEAR)))) {
					v_opps = (Vector<DirectorReportBean>) years.get(new Integer(cal.get(Calendar.YEAR)));
				}
				else {
					v_opps = new Vector<DirectorReportBean>();
					years.put(new Integer(cal.get(Calendar.YEAR)), v_opps);
				}

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("DirectorReportManager.getDirectorReportBeans(): " + e);
			throw new CeoWebException("Can not extract DirectorReportBean from DB.", e);
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

		return years;
	}

	public static void addDirectorReportBean(DirectorReportBean eBean) throws CeoWebException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.ceo_web.add_director_report(?, ?); end;");
			stat.setDate(1, new java.sql.Date(eBean.getReportDate().getTime()));
			stat.setString(2, eBean.getReportTitle());
			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("DirectorReportManager.getDirectorReportBeans(): " + e);
			throw new CeoWebException("Can not ADD DirectorReportBean to DB.", e);
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

	public static void updateDirectorReportBean(DirectorReportBean eBean) throws CeoWebException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.ceo_web.update_director_report(?, ?); end;");
			stat.setDate(1, new java.sql.Date(eBean.getReportDate().getTime()));
			stat.setString(2, eBean.getReportTitle());
			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("DirectorReportManager.getDirectorReportBeans(): " + e);
			throw new CeoWebException("Can not UPDATE DirectorReportBean to DB.", e);
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

	public static void deleteDirectorReportBean(java.util.Date rpt_dt) throws CeoWebException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.ceo_web.del_director_report(?); end;");
			stat.setDate(1, new java.sql.Date(rpt_dt.getTime()));
			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("DirectorReportManager.deleteDirectorReportBean(java.util.Date rpt_dt): " + e);
			throw new CeoWebException("Can not DELETE DirectorReportBean to DB.", e);
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

	public static DirectorReportBean createDirectorReportBean(ResultSet rs) {

		DirectorReportBean aBean = null;
		try {
			aBean = new DirectorReportBean();

			aBean.setReportDate(new java.util.Date(rs.getDate("REPORT_DATE").getTime()));
			aBean.setReportTitle(rs.getString("REPORT_TITLE"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}