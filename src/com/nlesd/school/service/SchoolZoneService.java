package com.nlesd.school.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.dao.DAOUtils;
import com.nlesd.school.bean.SchoolZoneBean;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class SchoolZoneService {

	private static Map<Integer, SchoolZoneBean> preLoadedSchoolZones = new HashMap<Integer, SchoolZoneBean>(4);

	static {
		try {
			preLoadedSchoolZones = getSchoolZoneBeansMap();
		}
		catch (SchoolException e) {
			try {
				new AlertBean(e);
			}
			catch (EmailException e1) {
				e1.printStackTrace();
			}
			preLoadedSchoolZones = new HashMap<Integer, SchoolZoneBean>(4);
		}
	}

	public static Collection<SchoolZoneBean> getSchoolZoneBeans() throws SchoolException {

		Collection<SchoolZoneBean> zones = null;

		if (preLoadedSchoolZones.size() > 0) {
			zones = preLoadedSchoolZones.values();
		}
		else {
			zones = getSchoolZoneBeans(false);
		}

		/*
		Collections.sort((List<SchoolZoneBean>) zones, new Comparator<SchoolZoneBean>() {
		
			@Override
			public int compare(SchoolZoneBean o1, SchoolZoneBean o2) {
		
				if (o1.getZoneId() == 5)
					return 1;
				else if (o2.getZoneId() == 5)
					return -1;
				else {
					return o1.compareTo(o2);
				}
			}
		
		});
		*/

		return zones;
	}

	public static Collection<SchoolZoneBean> getSchoolZoneBeans(boolean loadZoneSchools) throws SchoolException {

		Collection<SchoolZoneBean> zones = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			zones = new ArrayList<SchoolZoneBean>(4);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_zones; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				zones.add(createSchoolZoneBean(rs, loadZoneSchools));
		}
		catch (SQLException e) {
			System.err.println("Collection<SchoolZoneBean> getSchoolZoneBeans(): " + e);
			throw new SchoolException("Can not extract SchoolZoneBean from DB.");
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

		return zones;
	}

	public static Map<Integer, SchoolZoneBean> getSchoolZoneBeansMap() throws SchoolException {

		HashMap<Integer, SchoolZoneBean> map = new HashMap<Integer, SchoolZoneBean>(4);

		for (SchoolZoneBean zone : SchoolZoneService.getSchoolZoneBeans(true)) {
			map.put(zone.getZoneId(), zone);
		}

		return map;
	}

	public static SchoolZoneBean getSchoolZoneBean(int zoneId) throws SchoolException {

		SchoolZoneBean abean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			if (preLoadedSchoolZones != null && preLoadedSchoolZones.containsKey(zoneId)) {
				abean = preLoadedSchoolZones.get(zoneId);
			}
			else {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_zone(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, zoneId);
				stat.execute();

				rs = ((OracleCallableStatement) stat).getCursor(1);

				if (rs.next()) {
					abean = createSchoolZoneBean(rs);

					//cache it.
					preLoadedSchoolZones.put(abean.getZoneId(), abean);
				}
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolZoneBean getSchoolZoneBean(int zoneId): " + e);
			throw new SchoolException("Can not extract SchoolZoneBean from DB.");
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

		return abean;
	}

	public static SchoolZoneBean getSchoolZoneBean(School school) throws SchoolException {

		SchoolZoneBean abean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_zone_by_school(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, school.getSchoolID());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				abean = createSchoolZoneBean(rs);
			}

		}
		catch (SQLException e) {
			System.err.println("SchoolZoneBean getSchoolZoneBean(School school): " + e);
			throw new SchoolException("Can not extract SchoolZoneBean from DB.");
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

		return abean;
	}

	public static SchoolZoneBean createSchoolZoneBean(ResultSet rs) {

		return createSchoolZoneBean(rs, false);
	}

	public static SchoolZoneBean createSchoolZoneBean(ResultSet rs, boolean loadZoneSchools) {

		SchoolZoneBean abean = null;

		try {
			if (rs.getInt("zone_id") > 0) {
				abean = new SchoolZoneBean();

				abean.setZoneId(rs.getInt("zone_id"));
				abean.setZoneName(rs.getString("zone_name"));
				try {
					abean.setAddress1(rs.getString("zone_address1"));
					abean.setAddress2(rs.getString("zone_address2"));
					abean.setTownCity(rs.getString("zone_towncity"));
					abean.setProvince(rs.getString("zone_province"));
					abean.setPostalCode(rs.getString("zone_postalcode"));
					abean.setTelephone(rs.getString("zone_telephone"));
					abean.setFax(rs.getString("zone_fax"));
				}
				catch (SQLException e) {
					// zone metadata may not always be available.
				}

				if (loadZoneSchools) {
					abean.setSchools(SchoolDB.getSchools(abean));
				}
			}
		}
		catch (Exception e) {
			abean = null;
		}

		return abean;
	}

}
