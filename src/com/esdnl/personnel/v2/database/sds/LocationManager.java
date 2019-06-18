package com.esdnl.personnel.v2.database.sds;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.v2.model.sds.bean.LocationBean;
import com.esdnl.personnel.v2.model.sds.bean.LocationException;

public class LocationManager {

	public static LocationBean getLocationBean(int loc_id) throws LocationException {

		LocationBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sds_hr.get_loc(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, loc_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createLocationBean(rs);
		}
		catch (SQLException e) {
			System.err.println("LocationBean[] getLocationBeans(String sin): " + e);
			throw new LocationException("Can not extract LocationBean from DB.", e);
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
	public static LocationBean getLocationBeanByString(String loc_id) throws LocationException {

		LocationBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sds_hr.get_loc(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, loc_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createLocationBean(rs);
		}
		catch (SQLException e) {
			System.err.println("LocationBean[] getLocationBeans(String sin): " + e);
			throw new LocationException("Can not extract LocationBean from DB.", e);
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
	public static LocationBean getLocationBeanByDeptId(int dept_id) throws LocationException {

		LocationBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sds_hr.get_loc_by_deptid(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, dept_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createLocationBean(rs);
		}
		catch (SQLException e) {
			System.err.println("LocationBean getLocationBeanByDeptId(int dept_id): " + e);
			throw new LocationException("Can not extract LocationBean from DB.", e);
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

	public static LocationBean getLocationBeanByDescription(String loc) throws LocationException {

		LocationBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sds_hr.get_loc_by_desc(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, loc);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createLocationBean(rs);
		}
		catch (SQLException e) {
			System.err.println("LocationBean[] getLocationBeans(String sin): " + e);
			throw new LocationException("Can not extract LocationBean from DB.", e);
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

	public static LocationBean[] getLocationBeans() throws LocationException {

		Vector<LocationBean> v_opps = null;
		LocationBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<LocationBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sds_hr.get_locs; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createLocationBean(rs);

				//System.err.println(eBean);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("LocationBean[] getLocationBeans(String sin): " + e);
			throw new LocationException("Can not extract LocationBean from DB.", e);
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

		return (LocationBean[]) v_opps.toArray(new LocationBean[0]);
	}

	public static LocationBean createLocationBean(ResultSet rs) {

		LocationBean abean = null;

		try {
			abean = new LocationBean();

			abean.setLocationId(rs.getString("Location"));
			abean.setLocationDescription(rs.getString("Loc_Description"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}