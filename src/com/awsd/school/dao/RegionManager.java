package com.awsd.school.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.School;
import com.awsd.school.bean.RegionBean;
import com.awsd.school.bean.RegionException;
import com.esdnl.dao.DAOUtils;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class RegionManager {

	private static HashMap<Integer, RegionBean> preLoadedRegions = null;

	static {
		try {
			preLoadedRegions = getRegionBeansMap();
		}
		catch (RegionException e) {
			preLoadedRegions = null;
		}
	}

	public static HashMap<Integer, RegionBean> getRegionBeansMap() throws RegionException {

		HashMap<Integer, RegionBean> v_opps = null;
		RegionBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<Integer, RegionBean>(4);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_regions; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createRegionBean(rs);

				v_opps.put(new Integer(eBean.getId()), eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("RegionBean[] getRegionBeans(): " + e);
			throw new RegionException("Can not extract RegionBean from DB.", e);
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

		return v_opps;
	}

	public static RegionBean[] getRegionBeans() throws RegionException {

		Vector<RegionBean> v_opps = null;
		RegionBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<RegionBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_regions; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createRegionBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("RegionBean[] getRegionBeans(): " + e);
			throw new RegionException("Can not extract RegionBean from DB.", e);
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

		return (RegionBean[]) v_opps.toArray(new RegionBean[0]);
	}

	public static Collection<RegionBean> getRegionBeans(SchoolZoneBean zone) throws RegionException {

		Vector<RegionBean> v_opps = null;
		RegionBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<RegionBean>(4);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_zone_regions(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, zone.getZoneId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createRegionBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<RegionBean> getRegionBeans(SchoolZoneBean zone): " + e);
			throw new RegionException("Can not extract RegionBean from DB.", e);
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

		return v_opps;
	}

	public static RegionBean getRegionBean(int id) throws RegionException {

		RegionBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		Integer id_obj = new Integer(id);

		if ((preLoadedRegions != null) && preLoadedRegions.containsKey(id_obj))
			eBean = (RegionBean) preLoadedRegions.get(id_obj);
		else {
			try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_region(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);

				if (rs.next())
					eBean = createRegionBean(rs);

				if (eBean != null && preLoadedRegions != null)
					preLoadedRegions.put(eBean.getId(), eBean);
			}
			catch (SQLException e) {
				System.err.println("RegionBean[] getRegionBeans(): " + e);
				throw new RegionException("Can not extract RegionBean from DB.", e);
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
		}

		return eBean;

	}

	public static RegionBean getRegionBean(School s) throws RegionException {

		RegionBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_school_region(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s.getSchoolID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createRegionBean(rs);
		}
		catch (SQLException e) {
			System.err.println("RegionBean[] getRegionBeans(): " + e);
			throw new RegionException("Can not extract RegionBean from DB.", e);
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

	public static RegionBean createRegionBean(ResultSet rs) {

		RegionBean aBean = null;
		try {
			if (rs.getInt("REGION_ID") > 0) {
				aBean = new RegionBean();

				aBean.setId(rs.getInt("REGION_ID"));
				aBean.setName(rs.getString("REGION_NAME"));

				try {
					aBean.setZone(SchoolZoneService.getSchoolZoneBean(rs.getInt("ZONE_ID")));
				}
				catch (Exception ex) {
					aBean.setZone(null);
				}
			}
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}