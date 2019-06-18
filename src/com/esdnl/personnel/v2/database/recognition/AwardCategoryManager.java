package com.esdnl.personnel.v2.database.recognition;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.v2.model.recognition.bean.AwardCategoryBean;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeException;

public class AwardCategoryManager {

	public static void addAwardCategoryBean(AwardCategoryBean abean) throws EmployeeException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_recognition.add_award_cat(?); end;");
			stat.setString(1, abean.getCategoryName());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addAwardCategoryBean(AwardCategoryBean abean): " + e);
			throw new EmployeeException("Can not add AwardCategoryBean to DB.", e);
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

	public static AwardCategoryBean[] getAwardCategoryBean() throws EmployeeException {

		Vector beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_award_cats; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createAwardCategoryBean(rs));
		}
		catch (SQLException e) {
			System.err.println("AwardCategoryBean[] getAwardCategoryBeans(String sin): " + e);
			throw new EmployeeException("Can not extract AwardCategoryBean from DB.", e);
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

		return ((AwardCategoryBean[]) beans.toArray(new AwardCategoryBean[0]));
	}

	public static AwardCategoryBean getAwardCategoryBean(int cat_id) throws EmployeeException {

		AwardCategoryBean bean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_award_cat(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cat_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = createAwardCategoryBean(rs);

		}
		catch (SQLException e) {
			System.err.println("AwardCategoryBean[] getAwardCategoryBeans(String sin): " + e);
			throw new EmployeeException("Can not extract AwardCategoryBean from DB.", e);
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

	public static AwardCategoryBean createAwardCategoryBean(ResultSet rs) {

		AwardCategoryBean abean = null;

		try {
			abean = new AwardCategoryBean();

			abean.setCategoryId(rs.getInt("CAT_ID"));
			abean.setCategoryName(rs.getString("CAT_DESC"));

		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}