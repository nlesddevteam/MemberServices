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
import com.esdnl.personnel.v2.model.recognition.bean.AwardTypeBean;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeException;

public class AwardTypeManager {

	public static void addAwardTypeBean(AwardTypeBean abean) throws EmployeeException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_recognition.add_award_type(?, ?); end;");
			stat.setInt(1, abean.getCategoryId());
			stat.setString(2, abean.getAwardName());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addAwardTypeBean(AwardTypeBean abean): " + e);
			throw new EmployeeException("Can not add AwardTypeBean to DB.", e);
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

	public static AwardTypeBean[] getAwardTypeBean() throws EmployeeException {

		Vector beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_award_types; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createAwardTypeBean(rs));

		}
		catch (SQLException e) {
			System.err.println("AwardTypeBean[] getAwardTypeBeans(String sin): " + e);
			throw new EmployeeException("Can not extract AwardTypeBean from DB.", e);
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

		return ((AwardTypeBean[]) beans.toArray(new AwardTypeBean[0]));
	}

	public static AwardTypeBean[] getAwardTypeBean(AwardCategoryBean cat) throws EmployeeException {

		Vector beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_award_types(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cat.getCategoryId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createAwardTypeBean(rs));

		}
		catch (SQLException e) {
			System.err.println("AwardTypeBean[] getAwardTypeBeans(String sin): " + e);
			throw new EmployeeException("Can not extract AwardTypeBean from DB.", e);
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

		return ((AwardTypeBean[]) beans.toArray(new AwardTypeBean[0]));
	}

	public static AwardTypeBean getAwardTypeBean(int type_id) throws EmployeeException {

		AwardTypeBean bean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_award_type(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, type_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = createAwardTypeBean(rs);

		}
		catch (SQLException e) {
			System.err.println("AwardTypeBean[] getAwardTypeBeans(String sin): " + e);
			throw new EmployeeException("Can not extract AwardTypeBean from DB.", e);
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

	public static AwardTypeBean createAwardTypeBean(ResultSet rs) {

		AwardTypeBean abean = null;

		try {
			abean = new AwardTypeBean();

			abean.setCategoryId(rs.getInt("CAT_ID"));
			abean.setTypeId(rs.getInt("TYPE_ID"));
			abean.setAwardName(rs.getString("AWARD_DESC"));

		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}