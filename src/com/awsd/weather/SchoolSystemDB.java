package com.awsd.weather;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.awsd.school.Schools;
import com.awsd.school.bean.RegionBean;
import com.awsd.school.dao.RegionManager;
import com.esdnl.dao.DAOUtils;
import com.esdnl.util.StringUtils;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;
import com.nlesd.schoolstatus.bean.SchoolStatusGlobalConfigBean;
import com.nlesd.schoolstatus.service.SchoolStatusGlobalConfigService;

public class SchoolSystemDB {

	public static Vector<SchoolSystem> getSchoolSystems() throws SchoolSystemException {

		Vector<SchoolSystem> systems = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			systems = new Vector<SchoolSystem>(10);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_school_systems; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				systems.add(createSchoolSystemBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Vector<SchoolSystem> SchoolSystemDB.getSchoolSystems(): " + e);
			throw new SchoolSystemException("Can not extract school systems from DB: " + e);
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
		return systems;
	}

	public static SchoolSystem[] getSchoolSystems(RegionBean region) throws SchoolSystemException {

		Vector<SchoolSystem> v_opps = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<SchoolSystem>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_region_school_systems(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, region.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				v_opps.add(createSchoolSystemBean(rs));
		}
		catch (SQLException e) {
			System.err.println("SchoolSystem[] SchoolSystemDB.getSchoolSystems(RegionBean region): " + e);
			e.printStackTrace(System.err);
			throw new SchoolSystemException("Can not extract SchoolSystems from DB.");
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

		return (SchoolSystem[]) v_opps.toArray(new SchoolSystem[0]);
	}

	public static SchoolSystem getSchoolSystem(int ss_id) throws SchoolSystemException {

		SchoolSystem sys = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_school_system(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, ss_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				sys = createSchoolSystemBean(rs);
			else
				throw new SchoolSystemException("Can not extract School System[" + ss_id + "] from DB ");
		}
		catch (SQLException e) {
			System.err.println("SchoolSystem SchoolSystemDB.getSchoolSystem(int ss_id): " + e);
			throw new SchoolSystemException("Can not extract school system from DB: " + e);
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
		return sys;
	}

	public static SchoolSystem[] getSchoolSystems(Personnel ss_admin) throws SchoolSystemException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		Vector<SchoolSystem> systems = null;
		Schools schools = null;
		SchoolSystem sys = null;
		ClosureStatus status = null;
		School school = null;
		int curr_sys = -1, id;

		boolean summer = false;

		try {
			SchoolStatusGlobalConfigBean config = SchoolStatusGlobalConfigService.getSchoolStatusGlobalConfigBean();

			summer = config.isSummer();

			systems = new Vector<SchoolSystem>();
			schools = new Schools(true);

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_personnel_school_system(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, ss_admin.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				id = rs.getInt("SS_ID");

				if (curr_sys != id) // processing new system
				{
					if (sys != null) {
						sys.setSchoolSystemSchools(schools);
						systems.add(sys);
					}

					sys = SchoolSystemDB.createSchoolSystemBean(rs);

					schools = new Schools(true);

					school = SchoolDB.createSchoolBean(rs);

					// get school closure status information (could be null)
					if (rs.getInt("STATUS_ID") > 0) {
						status = new ClosureStatus(rs.getInt("STATUS_ID"), rs.getString("STATUS_DESC"), rs.getString("COMMENT"), rs.getBoolean("WEATHER_RELATED"), rs.getString("RATIONALE"));

						school.setSchoolClosureStatus(status);
					}
					else {
						if (summer)
							school.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(22));// summer
						else
							// open
							school.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(9));// school

					}
					// add school to current system
					schools.add(school);

					curr_sys = id;
				}
				else // processing current system
				{
					school = SchoolDB.createSchoolBean(rs);

					// get school closure status information (could be null)
					if (rs.getInt("STATUS_ID") > 0) {
						status = new ClosureStatus(rs.getInt("STATUS_ID"), rs.getString("STATUS_DESC"), rs.getString("COMMENT"), rs.getBoolean("WEATHER_RELATED"), rs.getString("RATIONALE"));

						school.setSchoolClosureStatus(status);
					}
					else {
						if (summer)
							school.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(22));// summer
						else
							// open
							school.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(9));// school
					}

					// add school to current system
					schools.add(school);
				}
			}

			if ((sys != null) && (schools != null)) {
				sys.setSchoolSystemSchools(schools);
				systems.add(sys);
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolSystemDB.addSchoolSystem(): " + e);
			throw new SchoolSystemException("Could not add school system to DB: " + e);
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
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
		return ((SchoolSystem[]) systems.toArray(new SchoolSystem[0]));
	}

	public static SchoolSystem getSchoolSystem(School school) throws SchoolSystemException {

		SchoolSystem sys = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_school_school_system(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, school.getSchoolID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				sys = createSchoolSystemBean(rs);
			else
				throw new SchoolSystemException("Can not extract School Syste from DB ");
		}
		catch (SQLException e) {
			System.err.println("SchoolSystem SchoolSystemDB.getSchoolSystem(School school): " + e);
			throw new SchoolSystemException("Can not extract school system from DB: " + e);
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
		return sys;
	}

	// TODO: UPDATE SCHOOL SYSTEM: Implement using stored procedure
	public static boolean updateSchoolSystem(SchoolSystem s) throws SchoolSystemException, PersonnelException {

		Connection con = null;
		PreparedStatement stat = null;
		String sql;
		int check = 0;

		try {
			sql = "UPDATE SCHOOL_SYSTEM SET SS_NAME=?, SS_ADMIN=?, SS_ADMIN_BCKUP=? WHERE SS_ID=?";

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareStatement(sql);

			stat.setString(1, s.getSchoolSystemName());

			if (s.getSchoolSystemAdmin() != null)
				stat.setInt(2, s.getSchoolSystemAdmin().getPersonnelID());
			else
				stat.setNull(2, Types.INTEGER);

			stat.setString(3, concatArray(s.getSchoolSystemAdminBackup(), "|"));

			stat.setInt(4, s.getSchoolSystemID());

			check = stat.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("boolean SchoolSystemDB.updateSchoolSystem(SchoolSystem s): " + e);
			throw new SchoolSystemException("Could not update school system in DB: " + e);
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

		return (check == 1);
	}

	// TODO: ADD SCHOOL SYSTEM: Implement using stored procedure.
	public static int addSchoolSystem(SchoolSystem s, String school_id[])
			throws SchoolSystemException,
				PersonnelException {

		Connection con = null;
		PreparedStatement stat = null;
		Statement stat2 = null;
		ResultSet rs = null;
		String sql;
		int check = 0, id = -1;

		try {
			sql = "INSERT INTO SCHOOL_SYSTEM VALUES(SCHOOL_SYSTEM_SEQ.nextval, ?, ?, ?)";

			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareStatement(sql);
			stat.clearParameters();
			stat.setString(1, s.getSchoolSystemName());
			if (s.getSchoolSystemAdmin() != null) {
				stat.setInt(2, s.getSchoolSystemAdmin().getPersonnelID());
			}
			else {
				stat.setNull(2, Types.NUMERIC);
			}

			stat.setString(3, concatArray(s.getSchoolSystemAdminBackup(), "|"));

			check = stat.executeUpdate();

			if (check == 1) {
				stat2 = con.createStatement();
				rs = stat2.executeQuery("SELECT SCHOOL_SYSTEM_SEQ.CURRVAL ss_id FROM DUAL");

				if (rs.next()) {
					id = rs.getInt("ss_id");

					for (int i = 0; i < school_id.length; i++) {
						sql = "INSERT INTO SCHOOL_SYSTEM_SCHOOLS VALUES(" + id + ", " + school_id[i] + ")";
						check = stat2.executeUpdate(sql);

						if (check != 1) {
							con.rollback();
							throw new SchoolSystemException("Could not add School System School to DB.");
						}
					}
				}
				else {
					id = -1;
					con.rollback();
					throw new SchoolSystemException("Could not retrieve School System ID from DB.");
				}
			}
			else {
				con.rollback();
				throw new SchoolSystemException("Could not add School System to DB.");
			}

			con.commit();
		}
		catch (SQLException e) {
			System.err.println("SchoolSystemDB.addSchoolSystem(): " + e);

			try {
				con.rollback();
			}
			catch (Exception ex) {
				System.err.println("COULD NOT ROLLBACK TRANSACTION.");
			}
			throw new SchoolSystemException("Could not add school system to DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			rs = null;
			try {
				stat.close();
			}
			catch (Exception e) {}
			stat = null;
			try {
				stat2.close();
			}
			catch (Exception e) {}
			stat2 = null;
			try {
				con.close();
			}
			catch (Exception e) {}
			con = null;
		}
		return (id);
	}

	public static boolean deleteSchoolSystem(int ss_id) throws SchoolSystemException {

		Connection con = null;
		CallableStatement stat = null;
		int check = 0;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.schools_pkg.del_school_system(?); end;");
			stat.setInt(1, ss_id);
			stat.execute();

			check = stat.getUpdateCount();

			con.commit();
		}
		catch (SQLException e) {
			System.err.println("SchoolSystemDB.deleteSchoolSystem(): " + e);
			throw new SchoolSystemException("Could not delete school system to DB: " + e);
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

		return (check == 1);
	}

	public static boolean updateSchoolSystemSchoolStatus(int school_id, int status_id, String comment, java.util.Date sd,
																												boolean weatherRelated, String rationale)
			throws SchoolSystemException {

		Connection con = null;
		CallableStatement stat = null;
		int check = 0;

		try {

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.schools_pkg.update_school_closure_status(?,?,?,?,?,?); end;");
			stat.setInt(1, school_id);
			stat.setInt(2, status_id);
			if (!StringUtils.isEmpty(comment))
				stat.setString(3, comment);
			else
				stat.setString(3, null);
			stat.setDate(4, new java.sql.Date(sd.getTime()));
			stat.setBoolean(5, weatherRelated);
			stat.setString(6, rationale);
			stat.execute();

			check = stat.getUpdateCount();

			con.commit();
		}
		catch (SQLException e) {
			System.err.println("boolean updateSchoolSystemSchoolStatus(int school_id, int status_id, String comment, java.util.Date sd, boolean weatherRelated, String rationale): "
					+ e);
			throw new SchoolSystemException("Could not add school system to DB: " + e);
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
		return (check == 1);
	}

	public static Vector<SchoolSystem> getSchoolClosureStatuses()
			throws SchoolSystemException,
				SchoolException,
				ClosureStatusException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		Vector<SchoolSystem> systems = null;
		Schools schools = null;
		SchoolSystem sys = null;
		ClosureStatus status = null;
		School school = null;
		int curr_sys = -1, id;

		boolean summer = false;

		try {
			SchoolStatusGlobalConfigBean config = SchoolStatusGlobalConfigService.getSchoolStatusGlobalConfigBean();

			summer = config.isSummer();

			systems = new Vector<SchoolSystem>();
			schools = new Schools(true);

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_cur_school_closure_status; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = (ResultSet) stat.getObject(1);

			while (rs.next()) {
				id = rs.getInt("SS_ID");

				if (curr_sys != id) // processing new system
				{
					if (sys != null) {
						sys.setSchoolSystemSchools(schools);
						systems.add(sys);
					}

					sys = SchoolSystemDB.createSchoolSystemBean(rs);

					schools = new Schools(true);

					school = SchoolDB.createSchoolBean(rs);

					// get school closure status information (could be null)
					if (rs.getInt("STATUS_ID") > 0) {
						status = new ClosureStatus(rs.getInt("STATUS_ID"), rs.getString("STATUS_DESC"), rs.getString("COMMENT"), rs.getBoolean("WEATHER_RELATED"), rs.getString("RATIONALE"));

						school.setSchoolClosureStatus(status);
					}
					else {
						if (summer)
							school.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(22));// summer
						else
							// open
							school.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(9));// school

					}
					// add school to current system
					schools.add(school);

					curr_sys = id;
				}
				else // processing current system
				{
					school = SchoolDB.createSchoolBean(rs);

					// get school closure status information (could be null)
					if (rs.getInt("STATUS_ID") > 0) {
						status = new ClosureStatus(rs.getInt("STATUS_ID"), rs.getString("STATUS_DESC"), rs.getString("COMMENT"), rs.getBoolean("WEATHER_RELATED"), rs.getString("RATIONALE"));

						school.setSchoolClosureStatus(status);
					}
					else {
						if (summer)
							school.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(22));// summer
						else
							// open
							school.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(9));// school
					}

					// add school to current system
					schools.add(school);
				}
			}

			if ((sys != null) && (schools != null)) {
				sys.setSchoolSystemSchools(schools);
				systems.add(sys);
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolSystemDB.addSchoolSystem(): " + e);
			throw new SchoolSystemException("Could not add school system to DB: " + e);
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
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
		return systems;
	}

	public static HashMap<SchoolZoneBean, Vector<SchoolSystem>> getSchoolClosureStatusesByZone()
			throws SchoolSystemException,
				SchoolException,
				ClosureStatusException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		HashMap<SchoolZoneBean, Vector<SchoolSystem>> zoneMap = null;
		Vector<SchoolSystem> systems = null;

		Schools schools = null;
		SchoolSystem sys = null;
		ClosureStatus status = null;
		School school = null;
		int curr_sys = -1, id;

		boolean summer = false;

		try {
			SchoolStatusGlobalConfigBean config = SchoolStatusGlobalConfigService.getSchoolStatusGlobalConfigBean();

			summer = config.isSummer();

			zoneMap = new HashMap<SchoolZoneBean, Vector<SchoolSystem>>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_cur_school_closure_status; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				id = rs.getInt("SS_ID");

				if (curr_sys != id) // processing new system
				{
					if (sys != null) {
						sys.setSchoolSystemSchools(schools);

						systems = zoneMap.get(sys.getZone());
						if (systems == null) {
							systems = new Vector<SchoolSystem>();

							zoneMap.put(sys.getZone(), systems);
						}

						systems.add(sys);
					}

					sys = SchoolSystemDB.createSchoolSystemBean(rs);

					schools = new Schools(true);

					school = SchoolDB.createSchoolBean(rs);

					// get school closure status information (could be null)
					if (rs.getInt("STATUS_ID") > 0) {
						status = new ClosureStatus(rs.getInt("STATUS_ID"), rs.getString("STATUS_DESC"), rs.getString("COMMENT"), rs.getBoolean("WEATHER_RELATED"), rs.getString("RATIONALE"));

						school.setSchoolClosureStatus(status);
					}
					else {
						if (summer)
							school.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(22));// summer
						else
							// open
							school.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(9));// school

					}
					// add school to current system
					schools.add(school);

					curr_sys = id;
				}
				else // processing current system
				{
					school = SchoolDB.createSchoolBean(rs);

					// get school closure status information (could be null)
					if (rs.getInt("STATUS_ID") > 0) {
						status = new ClosureStatus(rs.getInt("STATUS_ID"), rs.getString("STATUS_DESC"), rs.getString("COMMENT"), rs.getBoolean("WEATHER_RELATED"), rs.getString("RATIONALE"));

						school.setSchoolClosureStatus(status);
					}
					else {
						if (summer)
							school.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(22));// summer
						else
							// open
							school.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(9));// school
					}

					// add school to current system
					schools.add(school);
				}
			}

			if ((sys != null) && (schools != null)) {
				sys.setSchoolSystemSchools(schools);

				systems = zoneMap.get(sys.getZone());
				if (systems == null) {
					systems = new Vector<SchoolSystem>();

					zoneMap.put(sys.getZone(), systems);
				}

				systems.add(sys);
			}
		}
		catch (SQLException e) {
			System.err.println("HashMap<SchoolZoneBean, Vector<SchoolSystem>> getSchoolClosureStatusesByZone: " + e);
			throw new SchoolSystemException("Could not extract school system to DB: " + e);
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
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
		return zoneMap;
	}

	public static TreeMap<RegionBean, Vector<SchoolSystem>> getRegionalizedSchoolClosureStatuses()
			throws SchoolSystemException,
				SchoolException,
				ClosureStatusException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<RegionBean, Vector<SchoolSystem>> regions = null;
		Vector<SchoolSystem> systems = null;
		RegionBean region = null;
		Schools schools = null;
		SchoolSystem sys = null;

		School school = null;
		int curr_sys = -1, ss_id;

		boolean summer = false;

		try {
			SchoolStatusGlobalConfigBean config = SchoolStatusGlobalConfigService.getSchoolStatusGlobalConfigBean();

			summer = config.isSummer();

			regions = new TreeMap<RegionBean, Vector<SchoolSystem>>();
			// systems = new Vector<SchoolSystem>();
			// schools = new Schools(true);

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_cur_school_closure_status; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {

				ss_id = rs.getInt("SS_ID");

				if (curr_sys != ss_id) // processing new system
				{
					if (sys != null) {
						sys.setSchoolSystemSchools(schools);

						if (regions.containsKey(region))
							systems = regions.get(region);
						else {
							systems = new Vector<SchoolSystem>();
							regions.put(region, systems);
						}
						systems.add(sys);
					}

					region = RegionManager.createRegionBean(rs);

					sys = SchoolSystemDB.createSchoolSystemBean(rs);

					schools = new Schools(true);

					school = SchoolDB.createSchoolBean(rs);

					if (summer)
						school.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(22));// summer

					// add school to current system
					schools.add(school);

					curr_sys = ss_id;
				}
				else // processing current system
				{
					school = SchoolDB.createSchoolBean(rs);

					// get school closure status information (could be null)
					if (summer)
						school.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(22));// summer

					// add school to current system
					schools.add(school);
				}
			}

			if ((sys != null) && (schools != null)) {
				sys.setSchoolSystemSchools(schools);
				if (regions.containsKey(region))
					systems = regions.get(region);
				else {
					systems = new Vector<SchoolSystem>();
					regions.put(region, systems);
				}
				systems.add(sys);
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolSystemDB.addSchoolSystem(): " + e);
			throw new SchoolSystemException("Could not add school system to DB: " + e);
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
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
		return regions;
	}

	public static HashMap<SchoolZoneBean, HashMap<RegionBean, Vector<SchoolSystem>>> getRegionalizedSchoolClosureStatuses2()
			throws SchoolSystemException,
				SchoolException,
				ClosureStatusException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		HashMap<SchoolZoneBean, HashMap<RegionBean, Vector<SchoolSystem>>> zones = null;
		HashMap<RegionBean, Vector<SchoolSystem>> regions = null;
		Vector<SchoolSystem> systems = null;
		SchoolZoneBean zone = null;
		RegionBean region = null;
		Schools schools = null;
		SchoolSystem sys = null;

		School school = null;
		int curr_sys = -1, ss_id;

		boolean summer = false;

		try {
			SchoolStatusGlobalConfigBean config = SchoolStatusGlobalConfigService.getSchoolStatusGlobalConfigBean();

			summer = config.isSummer();

			zones = new HashMap<SchoolZoneBean, HashMap<RegionBean, Vector<SchoolSystem>>>();

			// systems = new Vector<SchoolSystem>();
			// schools = new Schools(true);

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_cur_school_closure_status; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {

				ss_id = rs.getInt("SS_ID");

				if (curr_sys != ss_id) // processing new system
				{
					if (sys != null) {
						sys.setSchoolSystemSchools(schools);

						if (zones.containsKey(zone)) {
							regions = zones.get(zone);
						}
						else {
							regions = new HashMap<RegionBean, Vector<SchoolSystem>>();
							zones.put(zone, regions);
						}

						if (zone == null)
							region = null;

						if (regions.containsKey(region))
							systems = regions.get(region);
						else {
							systems = new Vector<SchoolSystem>();
							regions.put(region, systems);
						}

						systems.add(sys);
					}

					zone = SchoolZoneService.createSchoolZoneBean(rs);

					region = RegionManager.createRegionBean(rs);

					sys = SchoolSystemDB.createSchoolSystemBean(rs);

					schools = new Schools(true);

					school = SchoolDB.createSchoolBean(rs);

					if (summer)
						school.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(22));// summer

					// add school to current system
					schools.add(school);

					curr_sys = ss_id;
				}
				else // processing current system
				{
					school = SchoolDB.createSchoolBean(rs);

					// get school closure status information (could be null)
					if (summer)
						school.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(22));// summer

					// add school to current system
					schools.add(school);
				}
			}

			if ((sys != null) && (schools != null)) {
				sys.setSchoolSystemSchools(schools);

				if (zones.containsKey(zone)) {
					regions = zones.get(zone);
				}
				else {
					regions = new HashMap<RegionBean, Vector<SchoolSystem>>();
					zones.put(zone, regions);
				}

				if (zone == null)
					region = null;

				if (regions.containsKey(region))
					systems = regions.get(region);
				else {
					systems = new Vector<SchoolSystem>();
					regions.put(region, systems);
				}

				systems.add(sys);
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<SchoolZoneBean, TreeMap<RegionBean, Vector<SchoolSystem>>> getRegionalizedSchoolClosureStatuses2(): "
					+ e);
			throw new SchoolSystemException("Could retrieve school systems from DB: " + e);
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
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

	public static SchoolSystem createSchoolSystemBean(ResultSet rs) {

		SchoolSystem sys = null;

		try {

			sys = new SchoolSystem(rs.getInt("ss_id"), rs.getString("ss_name"), (rs.getInt("ss_admin") == 0) ? -1
					: rs.getInt("ss_admin"), !StringUtils.isEmpty(rs.getString("ss_admin_bckup")) ? rs.getString("ss_admin_bckup").split(
					"\\|")
					: null);
		}
		catch (SQLException e) {
			e.printStackTrace(System.err);
			sys = null;
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			sys = null;
		}

		return sys;
	}

	private static String concatArray(Personnel[] arr, String sep) {

		String tmp = null;

		if (arr.length > 0) {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != null) {
					if (!StringUtils.isEmpty(tmp))
						tmp = tmp.concat(sep + arr[i].getPersonnelID());
					else
						tmp = Integer.toString(arr[i].getPersonnelID());
				}
			}
		}

		return tmp;
	}
}