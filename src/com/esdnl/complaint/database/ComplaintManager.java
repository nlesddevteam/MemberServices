package com.esdnl.complaint.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.common.Utils;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.complaint.model.bean.ComplaintBean;
import com.esdnl.complaint.model.bean.ComplaintException;
import com.esdnl.complaint.model.constant.ComplaintStatus;
import com.esdnl.complaint.model.constant.ComplaintType;
import com.esdnl.complaint.model.constant.PhoneType;
import com.esdnl.dao.DAOUtils;

public class ComplaintManager {

	public static int addComplaintBean(ComplaintBean complaint) throws ComplaintException {

		Connection con = null;
		CallableStatement stat = null;
		int id = -1;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.complaint_sys.add_complaint(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, complaint.getFirstName());
			stat.setString(3, complaint.getMiddleInitial());
			stat.setString(4, complaint.getLastName());

			stat.setString(5, complaint.getAddress1());
			stat.setString(6, complaint.getAddress2());
			stat.setString(7, complaint.getAddress3());
			stat.setString(8, complaint.getCity());
			stat.setString(9, complaint.getProvince());
			stat.setString(10, complaint.getCountry());
			stat.setString(11, complaint.getPostalCode());

			stat.setString(12, complaint.getEmail());

			stat.setString(13, complaint.getAreaCode());
			stat.setString(14, complaint.getPhoneNumber());
			stat.setInt(15, complaint.getPhoneType().getValue());

			stat.setString(16, complaint.getContactTime());
			stat.setString(17, complaint.getContactRestrictions());

			stat.setInt(18, complaint.getComplaintType().getValue());
			if (complaint.getSchool() != null)
				stat.setInt(19, complaint.getSchool().getSchoolID());
			else
				stat.setNull(19, OracleTypes.NUMBER);
			stat.setBoolean(20, complaint.isAdminContacted());
			stat.setString(21, complaint.getAdminContactDates());
			stat.setString(22, complaint.getComplaintSummary());
			stat.setString(23, complaint.getUrgency());

			stat.setString(24, complaint.getSupportingDocumentation());

			stat.execute();

			id = ((OracleCallableStatement) stat).getInt(1);

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addAdRequestDegreeBeans(AdRequestBean req): " + e);
			throw new ComplaintException("Can not add AdRequestBean to DB.", e);
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

		return id;
	}

	public static ComplaintBean getComplaintBean(int id, Personnel who) throws ComplaintException {

		ComplaintBean req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.complaint_sys.get_complaint(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.setInt(3, who.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				req = createComplaintBean(rs, true);

		}
		catch (SQLException e) {
			System.err.println("ComplaintBean getComplaintBean(int id, Personnel who): " + e);
			throw new ComplaintException("Can not extract ComplaintBean from DB.", e);
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

		return req;
	}

	public static ComplaintBean getComplaintBean(int id) throws ComplaintException {

		ComplaintBean req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.complaint_sys.get_complaint(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				req = createComplaintBean(rs, true);

		}
		catch (SQLException e) {
			System.err.println("ComplaintBean getComplaintBean(int id): " + e);
			throw new ComplaintException("Can not extract ComplaintBean from DB.", e);
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

		return req;
	}

	public static boolean isComplaintBeanAssignedTo(ComplaintBean c, Personnel p) throws ComplaintException {

		ComplaintBean req = null;
		Connection con = null;
		CallableStatement stat = null;
		boolean assigned = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.complaint_sys.is_complaint_assigned_to(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, c.getId());
			stat.setInt(3, p.getPersonnelID());

			stat.execute();

			assigned = (((OracleCallableStatement) stat).getInt(1) == 1);
		}
		catch (SQLException e) {
			System.err.println("boolean isComplaintBeanAssignedTo(ComplaintBean c, Personnel p): " + e);
			throw new ComplaintException("Can not extract ComplaintBean from DB.", e);
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

		return assigned;
	}

	public static HashMap getComplaintBeans(boolean children) throws ComplaintException {

		ComplaintBean c = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		HashMap statuses = null;

		try {
			statuses = new HashMap();
			for (int i = 0; i < ComplaintStatus.ALL.length; i++)
				statuses.put(ComplaintStatus.ALL[i], new TreeSet(new Comparator() {

					public int compare(Object o1, Object o2) {

						ComplaintBean b1 = (ComplaintBean) o1;
						ComplaintBean b2 = (ComplaintBean) o2;

						return b1.getDateSubmitted().compareTo(b2.getDateSubmitted());
					}
				}));

			con = DAOUtils.getConnection();
			;
			stat = con.prepareCall("begin ? := awsd_user.complaint_sys.get_complaints; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				c = createComplaintBean(rs, children);
				((TreeSet) statuses.get(c.getCurrentStatus())).add(c);
			}

		}
		catch (SQLException e) {
			System.err.println("ComplaintBean getComplaintBean(int id, Personnel who): " + e);
			throw new ComplaintException("Can not extract ComplaintBean from DB.", e);
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

		return statuses;
	}

	public static TreeMap getComplaintBeans2(boolean children) throws ComplaintException {

		ComplaintBean c = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		HashMap statuses = null;
		TreeMap years = null;
		Calendar cal = null;
		String school_year = null;

		try {
			cal = Calendar.getInstance();

			years = new TreeMap(new Comparator() {

				public int compare(Object o1, Object o2) {

					return (((String) o1).compareTo((String) o2) * -1);
				}
			});

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.complaint_sys.get_complaints; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				c = createComplaintBean(rs, children);
				cal.setTime(c.getDateSubmitted());
				school_year = Utils.getSchoolYear(cal);

				if (years.containsKey(school_year))
					statuses = (HashMap) years.get(school_year);
				else {
					statuses = new HashMap();
					years.put(school_year, statuses);
				}

				if (statuses.containsKey(c.getCurrentStatus()))
					((TreeSet) statuses.get(c.getCurrentStatus())).add(c);
				else {
					TreeSet ts = new TreeSet(new Comparator() {

						public int compare(Object o1, Object o2) {

							ComplaintBean b1 = (ComplaintBean) o1;
							ComplaintBean b2 = (ComplaintBean) o2;

							return b1.getDateSubmitted().compareTo(b2.getDateSubmitted());
						}
					});
					ts.add(c);
					statuses.put(c.getCurrentStatus(), ts);
				}
			}

		}
		catch (SQLException e) {
			System.err.println("ComplaintBean getComplaintBean(int id, Personnel who): " + e);
			throw new ComplaintException("Can not extract ComplaintBean from DB.", e);
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

		return years;
	}

	public static HashMap getComplaintBeans(Personnel p, boolean children) throws ComplaintException {

		ComplaintBean c = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		HashMap statuses = null;

		try {
			statuses = new HashMap();
			for (int i = 0; i < ComplaintStatus.ALL.length; i++)
				statuses.put(ComplaintStatus.ALL[i], new TreeSet(new Comparator() {

					public int compare(Object o1, Object o2) {

						ComplaintBean b1 = (ComplaintBean) o1;
						ComplaintBean b2 = (ComplaintBean) o2;

						return b1.getDateSubmitted().compareTo(b2.getDateSubmitted());
					}
				}));

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.complaint_sys.get_complaints(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				c = createComplaintBean(rs, children);
				((TreeSet) statuses.get(c.getCurrentStatus())).add(c);
			}

		}
		catch (SQLException e) {
			System.err.println("ComplaintBean getComplaintBean(int id, Personnel who): " + e);
			throw new ComplaintException("Can not extract ComplaintBean from DB.", e);
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

		return statuses;
	}

	public static TreeMap getComplaintBeans2(Personnel p, boolean children) throws ComplaintException {

		ComplaintBean c = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		HashMap statuses = null;
		TreeMap years = null;
		Calendar cal = null;
		String school_year = null;

		try {
			cal = Calendar.getInstance();

			years = new TreeMap(new Comparator() {

				public int compare(Object o1, Object o2) {

					return (((String) o1).compareTo((String) o2) * -1);
				}
			});

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.complaint_sys.get_complaints(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				c = createComplaintBean(rs, children);
				cal.setTime(c.getDateSubmitted());
				school_year = Utils.getSchoolYear(cal);

				if (years.containsKey(school_year))
					statuses = (HashMap) years.get(school_year);
				else {
					statuses = new HashMap();
					years.put(school_year, statuses);
				}

				if (statuses.containsKey(c.getCurrentStatus()))
					((TreeSet) statuses.get(c.getCurrentStatus())).add(c);
				else {
					TreeSet ts = new TreeSet(new Comparator() {

						public int compare(Object o1, Object o2) {

							ComplaintBean b1 = (ComplaintBean) o1;
							ComplaintBean b2 = (ComplaintBean) o2;

							return b1.getDateSubmitted().compareTo(b2.getDateSubmitted());
						}
					});
					ts.add(c);
					statuses.put(c.getCurrentStatus(), ts);
				}
			}

		}
		catch (SQLException e) {
			System.err.println("ComplaintBean getComplaintBean(int id, Personnel who): " + e);
			throw new ComplaintException("Can not extract ComplaintBean from DB.", e);
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

		return years;
	}

	public static void rejectComplaintBean(ComplaintBean complaint, Personnel p) throws ComplaintException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.complaint_sys.reject_complaint(?,?); end;");

			stat.setInt(1, complaint.getId());
			stat.setInt(2, p.getPersonnelID());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addAdRequestDegreeBeans(AdRequestBean req): " + e);
			throw new ComplaintException("Can not add AdRequestBean to DB.", e);
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

	public static void changeStatus(ComplaintBean complaint, ComplaintStatus new_status, Personnel p)
			throws ComplaintException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.complaint_sys.change_status(?,?,?); end;");

			stat.setInt(1, complaint.getId());
			stat.setInt(2, new_status.getValue());
			stat.setInt(3, p.getPersonnelID());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void changeStatus(ComplaintBean complaint, ComplaintStatus new_status, Personnel p): " + e);
			throw new ComplaintException("Can not change status.", e);
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

	public static void assignComplaintBean(ComplaintBean complaint, Personnel who, Personnel to_who)
			throws ComplaintException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.complaint_sys.assign_complaint(?,?,?); end;");

			stat.setInt(1, complaint.getId());
			stat.setInt(2, who.getPersonnelID());
			stat.setInt(3, to_who.getPersonnelID());

			stat.execute();

			con.commit();

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void assignComplaintBean(ComplaintBean complaint, Personnel who, Personnel to_who): " + e);
			throw new ComplaintException("Can not assign complaint.", e);
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

	public static void deleteComplaintBean(ComplaintBean complaint) throws ComplaintException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.complaint_sys.delete_complaint(?); end;");

			stat.setInt(1, complaint.getId());

			stat.execute();

			con.commit();

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteComplaintBean(ComplaintBean complaint): " + e);
			throw new ComplaintException("Can not delete complaint.", e);
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

	public static Personnel[] getComplaintHandlers() throws ComplaintException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		Vector v = null;

		try {
			v = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.complaint_sys.get_complaint_handlers; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				v.add(PersonnelDB.createPersonnelBean(rs));
		}
		catch (SQLException e) {
			System.err.println("Personnel[] getComplaintHandlers(): " + e);
			throw new ComplaintException("Can not extract Complaint Handers from DB.", e);
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

		return (Personnel[]) v.toArray(new Personnel[0]);
	}

	public static ComplaintBean createComplaintBean(ResultSet rs, boolean children) {

		ComplaintBean abean = null;

		try {
			abean = new ComplaintBean();

			abean.setId(rs.getInt("COMPLAINT_ID"));

			abean.setFirstName(rs.getString("FIRST_NAME"));
			abean.setMiddleInitial(rs.getString("MIDDLE_INITIAL"));
			abean.setLastName(rs.getString("LAST_NAME"));

			abean.setAddress1(rs.getString("ADDRESS_1"));
			abean.setAddress2(rs.getString("ADDRESS_2"));
			abean.setAddress3(rs.getString("ADDRESS_3"));
			abean.setCity(rs.getString("CITY"));
			abean.setProvince(rs.getString("PROVINCE"));
			abean.setCountry(rs.getString("COUNTRY"));
			abean.setPostalCode(rs.getString("POSTAL_CODE"));

			abean.setEmail(rs.getString("EMAIL"));

			abean.setAreaCode(rs.getString("AREA_CODE"));
			abean.setPhoneNumber(rs.getString("PHONE_NUMBER"));
			abean.setPhoneType(PhoneType.get(rs.getInt("PHONE_TYPE")));

			abean.setContactTime(rs.getString("CONTACT_TIME"));
			abean.setContactRestrictions(rs.getString("CONTACT_RESTRICTIONS"));

			abean.setComplaintType(ComplaintType.get(rs.getInt("COMPLAINT_TYPE")));

			if (children) {
				try {
					abean.setSchool(SchoolDB.getSchool(rs.getInt("SCHOOL_ID")));
				}
				catch (SchoolException e) {
					abean.setSchool(null);
				}
			}
			else
				abean.setSchool(null);

			abean.setAdminContacted(rs.getBoolean("ADMIN_CONTACTED"));
			abean.setAdminContactDates(rs.getString("ADMIN_CONTACT_DATES"));
			abean.setComplaintSummary(rs.getString("SUMMARY"));
			abean.setUrgency(rs.getString("URGENCY"));

			abean.setSupportingDocumentation(rs.getString("SUPPORTING_DOCUMENT"));

			abean.setCurrentStatuc(ComplaintStatus.get(rs.getInt("CUR_STATUS")));

			abean.setDateSubmitted(new java.util.Date(rs.getTimestamp("DATE_SUBMITTED").getTime()));

			if (children)
				abean.setHistory(ComplaintHistoryManager.getComplaintHistoryBean(abean));
			else
				abean.setHistory(null);

			if (children)
				abean.setComments(ComplaintCommentManager.getComplaintCommentBean(abean));
			else
				abean.setComments(null);

			if (children)
				abean.setDocuments(ComplaintDocumentManager.getComplaintDocumentBean(abean));
			else
				abean.setDocuments(null);
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}