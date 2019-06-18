package com.esdnl.school.registration.kindergarten.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.StringUtils;

import com.awsd.school.SchoolDB;
import com.esdnl.dao.DAOUtils;
import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrantBean;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrantBean.GENDER;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrantBean.REGISTRANT_RELATIONSHIP;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrantBean.REGISTRANT_STATUS;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrantBean.SCHOOLSTREAM;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrationPeriodBean;
import com.esdnl.school.registration.kindergarten.bean.SchoolRegistrantCapBean;
import com.esdnl.school.registration.kindergarten.bean.SchoolRegistrantsSummaryBean;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class KindergartenRegistrationManager {

	public static final String MCP_FORMAT = "000000000000";

	public static KindergartenRegistrationPeriodBean addKindergartenRegistrationPeriodBean(	KindergartenRegistrationPeriodBean abean)
			throws SchoolRegistrationException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.add_registration_period(?,?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, abean.getSchoolYear());

			if (abean.getStartDate() != null)
				stat.setTimestamp(3, new java.sql.Timestamp(abean.getStartDate().getTime()));
			else
				stat.setNull(3, OracleTypes.TIMESTAMP);

			if (abean.getEndDate() != null)
				stat.setTimestamp(4, new java.sql.Timestamp(abean.getEndDate().getTime()));
			else
				stat.setNull(4, OracleTypes.TIMESTAMP);

			if (abean.getAddressConfirmationDeadline() != null)
				stat.setDate(5, new java.sql.Date(abean.getAddressConfirmationDeadline().getTime()));
			else
				stat.setNull(5, OracleTypes.DATE);

			ArrayList<String> zoneIds = new ArrayList<String>();
			for (SchoolZoneBean zone : abean.getZones()) {
				zoneIds.add(String.valueOf(zone.getZoneId()));
			}

			if (zoneIds.size() > 0) {
				stat.setString(6, StringUtils.join(zoneIds, ";"));
			}
			else {
				stat.setNull(6, OracleTypes.VARCHAR);
			}

			stat.execute();

			int id = stat.getInt(1);

			abean.setRegistrationId(id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("KindergartenRegistrationPeriodBean addKindergartenRegistrationPeriodBean(KindergartenRegistrationPeriodBean abean): "
					+ e);
			throw new SchoolRegistrationException("Can not add KindergartenRegistrationPeriodBean to DB.", e);
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

	public static String generateNextMCP() throws SchoolRegistrationException {

		Connection con = null;
		CallableStatement stat = null;

		int mcp = 0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_next_mcp; end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.execute();

			mcp = stat.getInt(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("String generateNextMCP(): " + e);
			throw new SchoolRegistrationException("Can not generate MCP.", e);
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

		return (new DecimalFormat(MCP_FORMAT)).format(mcp);
	}

	public static boolean checkMCPExists(String mcp) throws SchoolRegistrationException {

		Connection con = null;
		CallableStatement stat = null;

		boolean exists = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.check_mcp_existance(?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, mcp);

			stat.execute();

			exists = (stat.getInt(1) > 0);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("boolean checkMCPExistance(String mcp): " + e);
			throw new SchoolRegistrationException("Can not verify MCP existance.", e);
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

		return exists;
	}

	public static Collection<KindergartenRegistrationPeriodBean> getKindergartenRegistrationPeriodBeans()
			throws SchoolRegistrationException {

		Collection<KindergartenRegistrationPeriodBean> periods = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			periods = new ArrayList<KindergartenRegistrationPeriodBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_registration_periods; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				periods.add(createKindergartenRegistrationPeriodBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Collection<KindergartenRegistrationPeriodBean> getKindergartenRegistrationPeriodBeans(): "
					+ e);
			throw new SchoolRegistrationException("Can not extract KindergartenRegistrationPeriodBean from DB.", e);
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

		return periods;
	}

	public static Collection<KindergartenRegistrationPeriodBean> getMostRecentKindergartenRegistrationPeriodBean()
			throws SchoolRegistrationException {

		Collection<KindergartenRegistrationPeriodBean> periods = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			periods = new ArrayList<KindergartenRegistrationPeriodBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_most_recent_reg_period; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				periods.add(createKindergartenRegistrationPeriodBean(rs));
			}
		}
		catch (SQLException e) {
			System.err.println("KindergartenRegistrationPeriodBean getMostRecentKindergartenRegistrationPeriodBean(): " + e);
			throw new SchoolRegistrationException("Can not extract KindergartenRegistrationPeriodBean from DB.", e);
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

		return periods;
	}

	public static KindergartenRegistrationPeriodBean getActiveKindergartenRegistrationPeriodBean()
			throws SchoolRegistrationException {

		KindergartenRegistrationPeriodBean period = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_active_registration_period; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				period = createKindergartenRegistrationPeriodBean(rs);
		}
		catch (SQLException e) {
			System.err.println("KindergartenRegistrationPeriodBean getActiveKindergartenRegistrationPeriodBean(): " + e);
			throw new SchoolRegistrationException("Can not extract KindergartenRegistrationPeriodBean from DB.", e);
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

		return period;
	}

	public static Collection<KindergartenRegistrationPeriodBean> getFutureKindergartenRegistrationPeriodBean()
			throws SchoolRegistrationException {

		Collection<KindergartenRegistrationPeriodBean> periods = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			periods = new ArrayList<KindergartenRegistrationPeriodBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_future_registration_period; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				periods.add(createKindergartenRegistrationPeriodBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Collection<KindergartenRegistrationPeriodBean> getFutureKindergartenRegistrationPeriodBean(): "
					+ e);
			throw new SchoolRegistrationException("Can not extract KindergartenRegistrationPeriodBean from DB.", e);
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

		return periods;
	}

	public static KindergartenRegistrationPeriodBean getKindergartenRegistrationPeriodBean(int period_id)
			throws SchoolRegistrationException {

		KindergartenRegistrationPeriodBean period = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_registration_period(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, period_id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				period = createKindergartenRegistrationPeriodBean(rs);
		}
		catch (SQLException e) {
			System.err.println("KindergartenRegistrationPeriodBean getKindergartenRegistrationPeriodBean(int period_id): "
					+ e);
			throw new SchoolRegistrationException("Can not extract KindergartenRegistrationPeriodBean from DB.", e);
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

		return period;
	}

	public static Collection<String> getDistinctKindergartenRegistrationPeriodSchoolYears()
			throws SchoolRegistrationException {

		Collection<String> sy = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			sy = new ArrayList<String>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_all_schoolyears; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				sy.add(rs.getString("school_year"));
		}
		catch (SQLException e) {
			System.err.println("Collection<String> getDistinctKindergartenRegistrationPeriodSchoolYears(): " + e);
			throw new SchoolRegistrationException("Can not extract KindergartenRegistrationPeriodBean School Years from DB.", e);
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

		return sy;
	}

	public static KindergartenRegistrationPeriodBean createKindergartenRegistrationPeriodBean(ResultSet rs) {

		KindergartenRegistrationPeriodBean abean = null;
		try {
			abean = new KindergartenRegistrationPeriodBean();

			abean.setRegistrationId(rs.getInt("REGISTRATION_ID"));
			abean.setSchoolYear(rs.getString("SCHOOL_YEAR"));

			if (rs.getDate("START_DATE") != null)
				abean.setStartDate(new java.util.Date(rs.getTimestamp("START_DATE").getTime()));

			if (rs.getDate("END_DATE") != null)
				abean.setEndDate(new java.util.Date(rs.getTimestamp("END_DATE").getTime()));

			if (rs.getDate("ADDRESS_CONFIRMATION_DEADLINE") != null)
				abean.setAddressConfirmationDeadline(new java.util.Date(rs.getDate("ADDRESS_CONFIRMATION_DEADLINE").getTime()));

			abean.setPast(rs.getBoolean("IS_PAST"));
			abean.setRegistrantCount(rs.getInt("REGISTRANT_COUNT"));

			try {
				abean.setEnglishCount(rs.getInt("ENGLISH_COUNT"));
			}
			catch (SQLException e) {}
			try {
				abean.setFrenchCount(rs.getInt("FRENCH_COUNT"));
			}
			catch (SQLException e) {}

			if (StringUtils.isNotEmpty(rs.getString("ASSOCIATED_ZONES"))) {
				String[] zones = rs.getString("ASSOCIATED_ZONES").split(";");

				for (String z : zones) {
					abean.addZone(SchoolZoneService.getSchoolZoneBean(Integer.parseInt(z)));
				}
			}
			else {
				//all zones;
				abean.setZones(SchoolZoneService.getSchoolZoneBeans());
			}

		}
		catch (Exception e) {
			abean = null;
		}

		return abean;
	}

	public static KindergartenRegistrantBean addKindergartenRegistrantBean(KindergartenRegistrantBean abean)
			throws SchoolRegistrationException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.add_registrant(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.setInt(2, abean.getRegistration().getRegistrationId());
			stat.setString(3, abean.getStudentFirstName());
			stat.setString(4, abean.getStudentLastName());
			stat.setInt(5, abean.getStudentGender().getValue());
			stat.setString(6, abean.getPhysicalStreetAddress1());
			stat.setString(7, abean.getPhysicalCityTown());
			stat.setString(8, abean.getPhysicalPostalCode());
			if (abean.getDateOfBirth() != null)
				stat.setDate(9, new java.sql.Date(abean.getDateOfBirth().getTime()));
			else
				stat.setNull(9, OracleTypes.DATE);
			stat.setString(10, abean.getMcpNumber());
			stat.setString(11, abean.getMcpExpiry());
			stat.setInt(12, abean.getSchool().getSchoolID());
			stat.setInt(13, abean.getSchoolStream().getValue());
			stat.setString(14, abean.getPrimaryContactName());
			stat.setString(15, abean.getPrimaryContactHomePhone());
			stat.setString(16, abean.getPrimaryContactWorkPhone());
			stat.setString(17, abean.getPrimaryContactCellPhone());
			stat.setString(18, abean.getPrimaryContactEmail());
			stat.setInt(19, abean.getPrimaryContactRelationship().getValue());
			//registration date is current time.
			if (abean.getRegistrationDate() == null)
				abean.setRegistrationDate(Calendar.getInstance().getTime());
			stat.setTimestamp(20, new java.sql.Timestamp(abean.getRegistrationDate().getTime()));
			//physical address approved
			stat.setBoolean(21, false);
			if (abean.getRelatedRegistrant() != null)
				stat.setInt(22, abean.getRelatedRegistrant().getRegistrantId());
			else
				stat.setNull(22, OracleTypes.NUMBER);
			stat.setString(23, abean.getPhysicalStreetAddress2());
			stat.setString(24, abean.getMailingStreetAddress1());
			stat.setString(25, abean.getMailingStreetAddress2());
			stat.setString(26, abean.getMailingCityTown());
			stat.setString(27, abean.getMailingPostalCode());
			stat.setString(28, abean.getSecondaryContactName());
			stat.setString(29, abean.getSecondaryContactHomePhone());
			stat.setString(30, abean.getSecondaryContactWorkPhone());
			stat.setString(31, abean.getSecondaryContactCellPhone());
			stat.setString(32, abean.getSecondaryContactEmail());
			if (abean.getSecondaryContactRelationship() != null)
				stat.setInt(33, abean.getSecondaryContactRelationship().getValue());
			else
				stat.setNull(33, OracleTypes.NUMBER);
			stat.setString(34, abean.getEmergencyContactName());
			stat.setString(35, abean.getEmergencyContactTelephone());
			stat.setBoolean(36, abean.isCustodyIssues());
			stat.setBoolean(37, abean.isHealthConcerns());
			stat.setBoolean(38, abean.isAccessibleFacility());
			stat.setBoolean(39, abean.isEfiSibling());

			stat.execute();

			int id = stat.getInt(1);

			abean.setRegistrantId(id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("KindergartenRegistrantBean addKindergartenRegistrantBean(KindergartenRegistrantBean abean): "
					+ e);
			throw new SchoolRegistrationException("Can not add KindergartenRegistrantBean to DB.", e);
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

	public static KindergartenRegistrantBean updateKindergartenRegistrantBean(KindergartenRegistrantBean abean)
			throws SchoolRegistrationException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.kinder_reg_pkg.mod_registrant(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");

			stat.setInt(1, abean.getRegistrantId());
			stat.setString(2, abean.getStudentFirstName());
			stat.setString(3, abean.getStudentLastName());
			stat.setInt(4, abean.getStudentGender().getValue());
			stat.setString(5, abean.getPhysicalStreetAddress1());
			stat.setString(6, abean.getPhysicalCityTown());
			stat.setString(7, abean.getPhysicalPostalCode());
			if (abean.getDateOfBirth() != null)
				stat.setDate(8, new java.sql.Date(abean.getDateOfBirth().getTime()));
			else
				stat.setNull(8, OracleTypes.DATE);
			stat.setString(9, abean.getMcpNumber());
			stat.setString(10, abean.getMcpExpiry());
			stat.setInt(11, abean.getSchool().getSchoolID());
			stat.setInt(12, abean.getSchoolStream().getValue());
			stat.setString(13, abean.getPrimaryContactName());
			stat.setString(14, abean.getPrimaryContactHomePhone());
			stat.setString(15, abean.getPrimaryContactWorkPhone());
			stat.setString(16, abean.getPrimaryContactCellPhone());
			stat.setString(17, abean.getPrimaryContactEmail());
			stat.setInt(18, abean.getPrimaryContactRelationship().getValue());
			stat.setString(19, abean.getPhysicalStreetAddress2());
			stat.setString(20, abean.getMailingStreetAddress1());
			stat.setString(21, abean.getMailingStreetAddress2());
			stat.setString(22, abean.getMailingCityTown());
			stat.setString(23, abean.getMailingPostalCode());
			stat.setString(24, abean.getSecondaryContactName());
			stat.setString(25, abean.getSecondaryContactHomePhone());
			stat.setString(26, abean.getSecondaryContactWorkPhone());
			stat.setString(27, abean.getSecondaryContactCellPhone());
			stat.setString(28, abean.getSecondaryContactEmail());
			if (abean.getSecondaryContactRelationship() != null)
				stat.setInt(29, abean.getSecondaryContactRelationship().getValue());
			else
				stat.setNull(29, OracleTypes.NUMBER);
			stat.setString(30, abean.getEmergencyContactName());
			stat.setString(31, abean.getEmergencyContactTelephone());
			stat.setBoolean(32, abean.isCustodyIssues());
			stat.setBoolean(33, abean.isHealthConcerns());
			stat.setBoolean(34, abean.isAccessibleFacility());
			stat.setBoolean(35, abean.isEfiSibling());
			stat.setInt(36, abean.getStatus().getValue());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("KindergartenRegistrantBean updateKindergartenRegistrantBean(KindergartenRegistrantBean abean): "
					+ e);
			throw new SchoolRegistrationException("Can not update KindergartenRegistrantBean to DB.", e);
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

	public static void deleteKindergartenRegistrantBean(KindergartenRegistrantBean abean)
			throws SchoolRegistrationException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.kinder_reg_pkg.del_registrant(?); end;");

			stat.setInt(1, abean.getRegistrantId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("KindergartenRegistrantBean deleteKindergartenRegistrantBean(KindergartenRegistrantBean abean): "
					+ e);
			throw new SchoolRegistrationException("Can not delete KindergartenRegistrantBean from DB.", e);
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

	public static KindergartenRegistrantBean approvePhysicalAddress(KindergartenRegistrantBean abean)
			throws SchoolRegistrationException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.kinder_reg_pkg.approve_physical_address(?); end;");

			stat.setInt(1, abean.getRegistrantId());

			stat.execute();

			abean.setPhysicalAddressApproved(true);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void approvePhysicalAddress(KindergartenRegistrantBean abean): " + e);
			throw new SchoolRegistrationException("Can not approve physical address from DB.", e);
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

	public static KindergartenRegistrantBean getKindergartenRegistrantBean(int registrantId)
			throws SchoolRegistrationException {

		KindergartenRegistrantBean registrant = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_registrant(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, registrantId);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				registrant = createKindergartenRegistrantBean(rs);
		}
		catch (SQLException e) {
			System.err.println("KindergartenRegistrantBean getKindergartenRegistrantBean(int registrantId): " + e);
			throw new SchoolRegistrationException("Can not extract KindergartenRegistrantBean from DB.", e);
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

		return registrant;
	}

	public static Collection<KindergartenRegistrantBean> getKindergartenRegistrantBeans(KindergartenRegistrationPeriodBean period)
			throws SchoolRegistrationException {

		Collection<KindergartenRegistrantBean> registrants = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			registrants = new ArrayList<KindergartenRegistrantBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_period_registrants(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, period.getRegistrationId());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				registrants.add(createKindergartenRegistrantBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Collection<KindergartenRegistrantBean> getKindergartenRegistrantBeans(KindergartenRegistrationPeriodBean period): "
					+ e);
			throw new SchoolRegistrationException("Can not extract KindergartenRegistrantBean from DB.", e);
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

		return registrants;
	}

	public static Collection<KindergartenRegistrantBean> getKindergartenRegistrantBeans(String schoolYear)
			throws SchoolRegistrationException {

		Collection<KindergartenRegistrantBean> registrants = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			registrants = new ArrayList<KindergartenRegistrantBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_registrants_by(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				registrants.add(createKindergartenRegistrantBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Collection<KindergartenRegistrantBean> getKindergartenRegistrantBeans(String schoolYear): "
					+ e);
			throw new SchoolRegistrationException("Can not extract KindergartenRegistrantBean from DB.", e);
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

		return registrants;
	}

	public static Collection<KindergartenRegistrantBean> getKindergartenRegistrantBeans(String schoolYear, int schoolId)
			throws SchoolRegistrationException {

		Collection<KindergartenRegistrantBean> registrants = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			registrants = new ArrayList<KindergartenRegistrantBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_registrants_by(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);
			stat.setInt(3, schoolId);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				registrants.add(createKindergartenRegistrantBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Collection<KindergartenRegistrantBean> getKindergartenRegistrantBeans(String schoolYear, int schoolId): "
					+ e);
			throw new SchoolRegistrationException("Can not extract KindergartenRegistrantBean from DB.", e);
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

		return registrants;
	}

	public static Collection<KindergartenRegistrantBean> getKindergartenRegistrantBeans(String schoolYear, int schoolId,
																																											int schoolStreamId)
			throws SchoolRegistrationException {

		Collection<KindergartenRegistrantBean> registrants = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			registrants = new ArrayList<KindergartenRegistrantBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_registrants_by(?, ?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);
			stat.setInt(3, schoolId);
			stat.setInt(4, schoolStreamId);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				registrants.add(createKindergartenRegistrantBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Collection<KindergartenRegistrantBean> getKindergartenRegistrantBeans(String schoolYear, int schoolId, int schoolStreamId): "
					+ e);
			throw new SchoolRegistrationException("Can not extract KindergartenRegistrantBean from DB.", e);
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

		return registrants;
	}

	public static Collection<KindergartenRegistrantBean> getKindergartenRegistrantBeansByStream(String schoolYear,
																																															int schoolStreamId)
			throws SchoolRegistrationException {

		Collection<KindergartenRegistrantBean> registrants = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			registrants = new ArrayList<KindergartenRegistrantBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_registrants_by_stream(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolYear);
			stat.setInt(3, schoolStreamId);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				registrants.add(createKindergartenRegistrantBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Collection<KindergartenRegistrantBean> getKindergartenRegistrantBeansByStream(String schoolYear, int schoolStreamId): "
					+ e);
			throw new SchoolRegistrationException("Can not extract KindergartenRegistrantBean from DB.", e);
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

		return registrants;
	}

	public static Collection<KindergartenRegistrantBean> getKindergartenRegistrantBeansByMCP(String mcp)
			throws SchoolRegistrationException {

		Collection<KindergartenRegistrantBean> registrants = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			registrants = new ArrayList<KindergartenRegistrantBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_registrants_by_mcp(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, mcp);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				registrants.add(createKindergartenRegistrantBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Collection<KindergartenRegistrantBean> getKindergartenRegistrantBeansByStream(String schoolYear, int schoolStreamId): "
					+ e);
			throw new SchoolRegistrationException("Can not extract KindergartenRegistrantBean from DB.", e);
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

		return registrants;
	}

	public static Collection<KindergartenRegistrantBean> getKindergartenRegistrantBeansByStudentName(String[] names)
			throws SchoolRegistrationException {

		Collection<KindergartenRegistrantBean> registrants = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			registrants = new ArrayList<KindergartenRegistrantBean>();
			con = DAOUtils.getConnection();
			if (names.length <= 1) {
				stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_registrants_by_studentname(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, names[0]);
			}
			else {
				stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_registrants_by_studentname(?, ?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, names[0]);
				stat.setString(3, names[1]);
			}
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				registrants.add(createKindergartenRegistrantBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Collection<KindergartenRegistrantBean> getKindergartenRegistrantBeansByStream(String schoolYear, int schoolStreamId): "
					+ e);
			throw new SchoolRegistrationException("Can not extract KindergartenRegistrantBean from DB.", e);
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

		return registrants;
	}

	public static KindergartenRegistrantBean createKindergartenRegistrantBean(ResultSet rs) {

		KindergartenRegistrantBean abean = null;
		try {
			abean = new KindergartenRegistrantBean();

			abean.setAccessibleFacility(rs.getBoolean("accessible_facility"));
			abean.setCustodyIssues(rs.getBoolean("custody_issues"));
			if (rs.getDate("date_of_birth") != null)
				abean.setDateOfBirth(new java.util.Date(rs.getDate("date_of_birth").getTime()));
			abean.setEfiSibling(rs.getBoolean("efi_sibling"));
			abean.setEmergencyContactName(rs.getString("emergency_contact_name"));
			abean.setEmergencyContactTelephone(rs.getString("emergency_contact_telephone"));
			abean.setHealthConcerns(rs.getBoolean("health_concerns"));
			abean.setMailingCityTown(rs.getString("mailing_city_town"));
			abean.setMailingPostalCode(rs.getString("mailing_postal_code"));
			abean.setMailingStreetAddress1(rs.getString("mailing_street_address1"));
			abean.setMailingStreetAddress2(rs.getString("mailing_street_address2"));
			abean.setMcpExpiry(rs.getString("mcp_expiry"));
			abean.setMcpNumber(rs.getString("mcp_number"));
			abean.setPhysicalAddressApproved(rs.getBoolean("physical_address_approved"));
			abean.setPhysicalCityTown(rs.getString("physical_city_town"));
			abean.setPhysicalPostalCode(rs.getString("physical_postal_code"));
			abean.setPhysicalStreetAddress1(rs.getString("physical_street_address1"));
			abean.setPhysicalStreetAddress2(rs.getString("physical_street_address2"));
			abean.setPrimaryContactCellPhone(rs.getString("primary_contact_cell_phone"));
			abean.setPrimaryContactEmail(rs.getString("primary_contact_email"));
			abean.setPrimaryContactHomePhone(rs.getString("primary_contact_home_phone"));
			abean.setPrimaryContactName(rs.getString("primary_contact_name"));
			abean.setPrimaryContactRelationship(REGISTRANT_RELATIONSHIP.get(rs.getInt("primary_contact_relationship")));
			abean.setPrimaryContactWorkPhone(rs.getString("primary_contact_work_phone"));
			abean.setRegistrantId(rs.getInt("registrant_id"));
			abean.setRegistration(createKindergartenRegistrationPeriodBean(rs));
			if (rs.getTimestamp("registration_date") != null)
				abean.setRegistrationDate(new java.util.Date(rs.getTimestamp("registration_date").getTime()));
			if (rs.getInt("related_registrant_id") > 0)
				abean.setRelatedRegistrant(getKindergartenRegistrantBean(rs.getInt("related_registrant_id")));
			abean.setSchool(SchoolDB.createSchoolBean(rs));
			abean.setSchoolStream(SCHOOLSTREAM.get(rs.getInt("school_stream")));
			abean.setSecondaryContactCellPhone(rs.getString("secondary_contact_cell_phone"));
			abean.setSecondaryContactEmail(rs.getString("secondary_contact_email"));
			abean.setSecondaryContactHomePhone(rs.getString("secondary_contact_home_phone"));
			abean.setSecondaryContactName(rs.getString("secondary_contact_name"));
			abean.setSecondaryContactRelationship(REGISTRANT_RELATIONSHIP.get(rs.getInt("secondary_contact_relationship")));
			abean.setSecondaryContactWorkPhone(rs.getString("secondary_contact_work_phone"));
			abean.setStudentFirstName(rs.getString("student_firstname"));
			abean.setStudentGender(GENDER.get(rs.getInt("student_gender")));
			abean.setStudentLastName(rs.getString("student_lastname"));
			abean.setStatus(REGISTRANT_STATUS.get(rs.getInt("status")));
		}
		catch (Exception e) {
			abean = null;
		}

		return abean;
	}

	public static Collection<SchoolRegistrantsSummaryBean> getSchoolRegistrantsSummaryBeans(String school_year)
			throws SchoolRegistrationException {

		Collection<SchoolRegistrantsSummaryBean> summaries = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			summaries = new ArrayList<SchoolRegistrantsSummaryBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_registrants_summary(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, school_year);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				summaries.add(createSchoolRegistrantsSummaryBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Collection<SchoolRegistrantsSummaryBean> getSchoolRegistrantsSummaryBeans(String school_year): "
					+ e);
			throw new SchoolRegistrationException("Can not extract SchoolRegistrantsSummaryBean from DB.", e);
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

		return summaries;
	}

	public static SchoolRegistrantsSummaryBean createSchoolRegistrantsSummaryBean(ResultSet rs) {

		SchoolRegistrantsSummaryBean abean = null;
		try {
			abean = new SchoolRegistrantsSummaryBean();

			abean.setSchoolYear(rs.getString("school_year"));
			abean.setSchool(SchoolDB.createSchoolBean(rs));
			abean.setEnglishRegistrants(rs.getInt("english_registrations"));
			abean.setFrenchRegistrants(rs.getInt("french_registrations"));
			abean.setTotalRegistrants(rs.getInt("total_registrations"));
		}
		catch (Exception e) {
			abean = null;
		}

		return abean;
	}

	public static SchoolRegistrantCapBean addSchoolRegistrantCapBean(SchoolRegistrantCapBean abean)
			throws SchoolRegistrationException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.add_registrant_cap(?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.setString(2, abean.getSchoolYear());
			stat.setInt(3, abean.getSchool().getSchoolID());
			stat.setInt(4, abean.getEnglishCap());
			stat.setInt(5, abean.getFrenchCap());

			stat.execute();

			int id = stat.getInt(1);

			abean.setCapId(id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SchoolRegistrantCapBean addSchoolRegistrantCapBean(SchoolRegistrantCapBean abean): " + e);
			throw new SchoolRegistrationException("Can not add SchoolRegistrantCapBean to DB.", e);
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

	public static SchoolRegistrantCapBean updateSchoolRegistrantCapBean(SchoolRegistrantCapBean abean)
			throws SchoolRegistrationException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.kinder_reg_pkg.mod_registrant_cap(?,?,?); end;");

			stat.setInt(1, abean.getCapId());
			stat.setInt(2, abean.getEnglishCap());
			stat.setInt(3, abean.getFrenchCap());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SchoolRegistrantCapBean updateSchoolRegistrantCapBean(SchoolRegistrantCapBean abean): " + e);
			throw new SchoolRegistrationException("Can not update SchoolRegistrantCapBean to DB.", e);
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

	public static SchoolRegistrantCapBean getSchoolRegistrantCapBean(String school_year, int school_id)
			throws SchoolRegistrationException {

		SchoolRegistrantCapBean cap = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_registrant_cap(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, school_year);
			stat.setInt(3, school_id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				cap = createSchoolRegistrantCapBean(rs);
		}
		catch (SQLException e) {
			System.err.println("SchoolRegistrantCapBean getSchoolRegistrantCapBean(String school_year, int school_id): " + e);
			throw new SchoolRegistrationException("Can not extract SchoolRegistrantCapBean from DB.", e);
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

		return cap;
	}

	public static Collection<SchoolRegistrantCapBean> getSchoolRegistrantCapBeans(String school_year)
			throws SchoolRegistrationException {

		Collection<SchoolRegistrantCapBean> summaries = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			summaries = new ArrayList<SchoolRegistrantCapBean>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.kinder_reg_pkg.get_registrant_caps(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, school_year);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				summaries.add(createSchoolRegistrantCapBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Collection<SchoolRegistrantCapBean> getSchoolRegistrantCapBeans(String school_year): " + e);
			throw new SchoolRegistrationException("Can not extract SchoolRegistrantCapBean from DB.", e);
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

		return summaries;
	}

	public static SchoolRegistrantCapBean createSchoolRegistrantCapBean(ResultSet rs) {

		SchoolRegistrantCapBean abean = null;
		try {
			abean = new SchoolRegistrantCapBean();

			abean.setCapId(rs.getInt("cap_id"));
			abean.setSchoolYear(rs.getString("school_year"));
			abean.setSchool(SchoolDB.createSchoolBean(rs));
			abean.setEnglishCap(rs.getInt("english_cap"));
			abean.setFrenchCap(rs.getInt("french_cap"));
			abean.setSummary(createSchoolRegistrantsSummaryBean(rs));
		}
		catch (Exception e) {
			abean = null;
		}

		return abean;
	}

}
