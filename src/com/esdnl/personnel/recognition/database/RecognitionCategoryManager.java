package com.esdnl.personnel.recognition.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.recognition.model.bean.RecognitionCategoryBean;
import com.esdnl.personnel.recognition.model.bean.RecognitionException;

public class RecognitionCategoryManager {

	public static void addRecognitionCategoryBean(RecognitionCategoryBean abean) throws RecognitionException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_recognition.add_rec_cat(?,?); end;");

			stat.setString(1, abean.getName());
			stat.setString(2, abean.getDescription());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addRecognitionCategoryBean(RecognitionCategoryBean abean): " + e);
			throw new RecognitionException("Can not add RecognitionCategoryBean to DB.", e);
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

	public static RecognitionCategoryBean[] getRecognitionCategoryBeans() throws RecognitionException {

		Vector beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_rec_cats; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createRecognitionCategoryBean(rs));
		}
		catch (SQLException e) {
			System.err.println("RecognitionCategoryBean[] getRecognitionCategoryBeans(): " + e);
			throw new RecognitionException("Can not extract RecognitionCategoryBean from DB.", e);
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

		return ((RecognitionCategoryBean[]) beans.toArray(new RecognitionCategoryBean[0]));
	}

	public static RecognitionCategoryBean getRecognitionCategoryBean(int id) throws RecognitionException {

		RecognitionCategoryBean bean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_rec_cat(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = createRecognitionCategoryBean(rs);
		}
		catch (SQLException e) {
			System.err.println(" RecognitionCategoryBean getRecognitionCategoryBean(int id): " + e);
			throw new RecognitionException("Can not extract RecognitionCategoryBean from DB.", e);
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

	public static RecognitionCategoryBean createRecognitionCategoryBean(ResultSet rs) {

		RecognitionCategoryBean abean = null;

		try {
			abean = new RecognitionCategoryBean();

			abean.setUID(rs.getInt("ID"));
			abean.setName(rs.getString("NAME"));
			abean.setDescription(rs.getString("DESCRIPTION"));
			abean.setNominationPeriodCount(rs.getInt("PERIOD_COUNT"));
			abean.setSecureOnly(rs.getBoolean("SECURE"));
			abean.setMonitorID(rs.getInt("MONITOR_ID"));
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}
}
