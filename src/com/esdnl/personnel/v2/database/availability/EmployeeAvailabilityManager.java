package com.esdnl.personnel.v2.database.availability;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.v2.model.availability.bean.EmployeeAvailabilityBean;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeException;
import com.esdnl.util.StringUtils;

public class EmployeeAvailabilityManager {

	public static EmployeeAvailabilityBean[] getCurrentEmployeeAvailabilityBean(String sin) throws EmployeeException {

		Vector<EmployeeAvailabilityBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<EmployeeAvailabilityBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_package.get_current_availability(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createEmployeeAvailabilityBean(rs));
		}
		catch (SQLException e) {
			System.err.println("EmployeeAvailabilityBean[] getEmployeeAvailabilityBeans(String sin): " + e);
			throw new EmployeeException("Can not extract EmployeeAvailabilityBean from DB.", e);
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

		return ((EmployeeAvailabilityBean[]) beans.toArray(new EmployeeAvailabilityBean[0]));
	}

	public static EmployeeAvailabilityBean addEmployeeAvailabilityBean(EmployeeAvailabilityBean abean)
			throws EmployeeException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_package.add_availability(?,?,?,?,?,?,?,?); end;");

			stat.setString(1, abean.getSIN());
			stat.setTimestamp(2, new Timestamp(abean.getStartDate().getTime()));
			stat.setTimestamp(3, new Timestamp(abean.getEndDate().getTime()));

			if (abean.getBookedById() > 0)
				stat.setInt(4, abean.getBookedById());
			else
				stat.setNull(4, OracleTypes.NUMBER);

			if (abean.getBookedWhereId() > 0)
				stat.setInt(5, abean.getBookedWhereId());
			else
				stat.setNull(5, OracleTypes.NUMBER);

			if (abean.getNotAvailableById() > 0)
				stat.setInt(6, abean.getNotAvailableById());
			else
				stat.setNull(6, OracleTypes.NUMBER);

			if (!StringUtils.isEmpty(abean.getReason()))
				stat.setString(7, abean.getReason());
			else
				stat.setNull(7, OracleTypes.VARCHAR);

			stat.setInt(8, abean.getReasonCode());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("EmployeeAvailabilityBean addEmployeeAvailabilityBean(EmployeeAvailabilityBean abean): " + e);
			throw new EmployeeException("Can not add EmployeeAvailabilityBean to DB.", e);
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

	public static void deleteEmployeeAvailabilityBean(String sin, java.util.Date sd, java.util.Date ed)
			throws EmployeeException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_package.delete_availability(?,?,?); end;");

			stat.setString(1, sin);
			stat.setTimestamp(2, new Timestamp(sd.getTime()));
			stat.setTimestamp(3, new Timestamp(ed.getTime()));

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("EmployeeAvailabilityBean addEmployeeAvailabilityBean(EmployeeAvailabilityBean abean): " + e);
			throw new EmployeeException("Can not add EmployeeAvailabilityBean to DB.", e);
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

	public static void deleteEmployeeAvailabilityBean(String sin, java.util.Date sd) throws EmployeeException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_package.delete_availability(?,?); end;");

			stat.setString(1, sin);
			stat.setTimestamp(2, new Timestamp(sd.getTime()));

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteEmployeeAvailabilityBean(String sin, java.util.Date sd): " + e);
			throw new EmployeeException("Can not delete EmployeeAvailabilityBean to DB.", e);
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

	public static EmployeeAvailabilityBean createEmployeeAvailabilityBean(ResultSet rs) {

		EmployeeAvailabilityBean abean = null;

		try {
			abean = new EmployeeAvailabilityBean();

			abean.setSIN(rs.getString("SIN"));
			if (rs.getTimestamp("PA_START_DATE") != null)
				abean.setStartDate(new java.util.Date(rs.getTimestamp("PA_START_DATE").getTime()));
			if (rs.getTimestamp("PA_END_DATE") != null)
				abean.setEndDate(new java.util.Date(rs.getTimestamp("PA_END_DATE").getTime()));
			abean.setBookedBy(rs.getInt("BOOKED_BY"));
			abean.setBookedWhere(rs.getInt("BOOKED_WHERE"));
			abean.setNotAvailableBy(rs.getInt("NOT_AVAILABLE_BY"));
			abean.setReason(rs.getString("REASON"));
			abean.setReasonCode(rs.getInt("REASON_CODE"));
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}
}