package com.nlesd.webmaint.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.SchoolException;
import com.esdnl.dao.DAOUtils;
import com.nlesd.school.service.SchoolZoneService;
import com.nlesd.webmaint.bean.StaffDirectoryContactBean;
import com.nlesd.webmaint.bean.StaffDirectoryContactBean.STAFF_DIRECTORY_DIVISION;

public class StaffDirectoryService {

	public static void addStaffDirectoryContactBean(StaffDirectoryContactBean contact) throws SchoolException {

		Connection con = null;
		CallableStatement stat = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.nlesd_web.add_staff_directory_contact(?,?,?,?,?,?,?,?,?,?); end;");

			stat.setInt(1, contact.getDivision().getId());
			stat.setInt(2, contact.getZone().getZoneId());
			stat.setString(3, contact.getPosition());
			stat.setString(4, contact.getFullName());
			stat.setString(5, contact.getTelephone());
			stat.setString(6, contact.getExtension());
			stat.setString(7, contact.getFax());
			stat.setString(8, contact.getEmail());
			stat.setInt(9, contact.getSortorder());
			stat.setString(10, contact.getCellphone());

			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("void addStaffDirectoryContactBean(StaffDirectoryContactBean contact): " + e);
			throw new SchoolException("Can not add StaffDirectoryContactBean from DB.");
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

	public static void updateStaffDirectoryContactBean(StaffDirectoryContactBean contact) throws SchoolException {

		Connection con = null;
		CallableStatement stat = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.nlesd_web.mod_staff_directory_contact(?,?,?,?,?,?,?,?,?,?,?); end;");

			stat.setInt(1, contact.getContactId());
			stat.setInt(2, contact.getDivision().getId());
			stat.setInt(3, contact.getZone().getZoneId());
			stat.setString(4, contact.getPosition());
			stat.setString(5, contact.getFullName());
			stat.setString(6, contact.getTelephone());
			stat.setString(7, contact.getExtension());
			stat.setString(8, contact.getFax());
			stat.setString(9, contact.getEmail());
			stat.setInt(10, contact.getSortorder());
			stat.setString(11, contact.getCellphone());

			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("void updateStaffDirectoryContactBean(StaffDirectoryContactBean contact): " + e);
			throw new SchoolException("Can not update StaffDirectoryContactBean from DB.");
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

	public static void deleteStaffDirectoryContactBean(StaffDirectoryContactBean contact) throws SchoolException {

		Connection con = null;
		CallableStatement stat = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.nlesd_web.del_staff_directory_contact(?); end;");

			stat.setInt(1, contact.getContactId());

			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("deleteStaffDirectoryContactBean(StaffDirectoryContactBean contact): " + e);
			throw new SchoolException("Can not delete StaffDirectoryContactBean from DB.");
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

	public static int getNextSortOrder(int divisionId, int zoneId) throws SchoolException {

		Connection con = null;
		CallableStatement stat = null;

		int sortorder = 0;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nlesd_web.get_next_contact_sortorder(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, divisionId);
			stat.setInt(3, zoneId);

			stat.execute();

			sortorder = ((OracleCallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
			System.err.println("StaffDirectoryContactBean getStaffDirectoryContactBean(int contactId): " + e);
			throw new SchoolException("Can not extract StaffDirectoryContactBean from DB.");
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

		return sortorder;
	}

	public static StaffDirectoryContactBean getStaffDirectoryContactBean(int contactId) throws SchoolException {

		StaffDirectoryContactBean contact = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nlesd_web.get_staff_directory_contact(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, contactId);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				contact = createStaffDirectoryContactBean(rs);
		}
		catch (SQLException e) {
			System.err.println("StaffDirectoryContactBean getStaffDirectoryContactBean(int contactId): " + e);
			throw new SchoolException("Can not extract StaffDirectoryContactBean from DB.");
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

		return contact;
	}

	public static Collection<StaffDirectoryContactBean> getStaffDirectoryContactBeans() throws SchoolException {

		Collection<StaffDirectoryContactBean> contacts = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			contacts = new ArrayList<StaffDirectoryContactBean>(4);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nlesd_web.get_staff_directory_contacts; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				contacts.add(createStaffDirectoryContactBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Collection<StaffDirectoryContactBean> getStaffDirectoryContactBeans(): " + e);
			throw new SchoolException("Can not extract StaffDirectoryContactBean from DB.");
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

		return contacts;
	}

	public static StaffDirectoryContactBean createStaffDirectoryContactBean(ResultSet rs) {

		StaffDirectoryContactBean abean = null;

		try {
			abean = new StaffDirectoryContactBean();

			abean.setContactId(rs.getInt("contact_id"));
			abean.setZone(SchoolZoneService.createSchoolZoneBean(rs));
			abean.setDivision(STAFF_DIRECTORY_DIVISION.get(rs.getInt("division_id")));
			abean.setFullName(rs.getString("fullname"));
			abean.setPosition(rs.getString("position"));
			abean.setEmail(rs.getString("email"));
			abean.setTelephone(rs.getString("telephone"));
			abean.setCellphone(rs.getString("cellphone"));
			abean.setExtension(rs.getString("extension"));
			abean.setFax(rs.getString("fax"));
			abean.setSortorder(rs.getInt("sortorder"));

		}
		catch (Exception e) {
			abean = null;
		}

		return abean;
	}

}
