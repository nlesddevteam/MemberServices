package com.esdnl.webmaint.esdweb.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.common.Utils;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.esdnl.dao.DAOUtils;
import com.esdnl.util.DateUtils;
import com.esdnl.webmaint.esdweb.bean.AnnouncementBean;
import com.esdnl.webmaint.esdweb.bean.EsdWebException;
import com.esdnl.webmaint.esdweb.constants.AnnouncementTypeConstant;

public class AnnouncementManager {

	public static Collection<AnnouncementBean> getCurrentAnnouncementBeans(int msg_type) throws EsdWebException {

		Vector<AnnouncementBean> v_opps = null;
		AnnouncementBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<AnnouncementBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_cur_msgs(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, msg_type);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAnnouncementBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("AnnouncementBeanManager.getAnnouncementBeans(): " + e);
			throw new EsdWebException("Can not extract AnnouncementBean from DB.", e);
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

	public static Collection<AnnouncementBean> getCurrentAnnouncementBeans(int msg_type, int limit)
			throws EsdWebException {

		Vector<AnnouncementBean> v_opps = null;
		AnnouncementBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<AnnouncementBean>(limit);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_cur_msgs(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, msg_type);
			stat.setInt(3, limit);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAnnouncementBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<AnnouncementBean> getCurrentAnnouncementBeans(int msg_type, int limit): " + e);
			throw new EsdWebException("Can not extract AnnouncementBean from DB.", e);
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

	public static Collection<AnnouncementBean> getLatestAnnouncementBeans(int limit) throws EsdWebException {

		Vector<AnnouncementBean> v_opps = null;
		AnnouncementBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<AnnouncementBean>(limit);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_latest_msgs(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, limit);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAnnouncementBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<AnnouncementBean> getLatestAnnouncementBeans(int limit): " + e);
			throw new EsdWebException("Can not extract AnnouncementBean from DB.", e);
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

	public static AnnouncementBean[] getFrontPageAnnouncementBeans(int msg_type) throws EsdWebException {

		Vector<AnnouncementBean> v_opps = null;
		AnnouncementBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<AnnouncementBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_front_page_msgs; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAnnouncementBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("AnnouncementBeanManager.getFrontPageAnnouncementBeans(): " + e);
			throw new EsdWebException("Can not extract AnnouncementBean from DB.", e);
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

		return (AnnouncementBean[]) v_opps.toArray(new AnnouncementBean[0]);
	}

	public static AnnouncementBean[] getAnnouncementBeans(int msg_type, boolean archived) throws EsdWebException {

		Vector<AnnouncementBean> v_opps = null;
		AnnouncementBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<AnnouncementBean>(5);

			con = DAOUtils.getConnection();
			if (archived)
				stat = con.prepareCall("begin ? := awsd_user.esd_web.get_archived_msgs(?); end;");
			else
				stat = con.prepareCall("begin ? := awsd_user.esd_web.get_msgs(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, msg_type);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAnnouncementBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("AnnouncementBeanManager.getAnnouncementBeans(): " + e);
			throw new EsdWebException("Can not extract AnnouncementBean from DB.", e);
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

		return (AnnouncementBean[]) v_opps.toArray(new AnnouncementBean[0]);
	}

	public static AnnouncementBean getAnnouncementBean(int msg_id) throws EsdWebException {

		AnnouncementBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_msg(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, msg_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createAnnouncementBean(rs);

		}
		catch (SQLException e) {
			System.err.println("AnnouncementBeanManager.getAnnouncementBeans(): " + e);
			throw new EsdWebException("Can not extract AnnouncementBean from DB.", e);
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

	public static TreeMap<String, TreeMap<String, Vector<AnnouncementBean>>> getArchivedAnnouncementBeansMap(	AnnouncementTypeConstant type,
																																																						School school)
			throws EsdWebException {

		TreeMap<String, TreeMap<String, Vector<AnnouncementBean>>> years = null;
		TreeMap<String, Vector<AnnouncementBean>> msgs = null;
		Vector<AnnouncementBean> month = null;
		AnnouncementBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		Calendar cal = null;
		String sy = "";

		try {
			years = new TreeMap<String, TreeMap<String, Vector<AnnouncementBean>>>(new Comparator<String>() {

				public int compare(String o1, String o2) {

					return o1.compareTo(o2) * -1;
				}
			});

			cal = Calendar.getInstance();

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_archived_msgs(?, ?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);

			if (type != null)
				stat.setInt(2, type.getTypeID());
			else
				stat.setNull(2, OracleTypes.NUMBER);

			if (school != null)
				stat.setInt(3, school.getSchoolID());
			else
				stat.setNull(3, OracleTypes.NUMBER);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAnnouncementBean(rs);

				cal.setTime(eBean.getDate());
				sy = Utils.getSchoolYear(cal);

				if (years.containsKey(sy))
					msgs = (TreeMap<String, Vector<AnnouncementBean>>) years.get(sy);
				else {
					msgs = new TreeMap<String, Vector<AnnouncementBean>>(new Comparator<String>() {

						public int compare(String o1, String o2) {

							return (new Integer(DateUtils.getMonthNumber(o1))).compareTo(new Integer(DateUtils.getMonthNumber(o2)));
						}
					});
					years.put(sy, msgs);
				}

				if (msgs.containsKey(DateUtils.getMonthString(cal.get(Calendar.MONTH))))
					month = msgs.get(DateUtils.getMonthString(cal.get(Calendar.MONTH)));
				else {
					month = new Vector<AnnouncementBean>();
					msgs.put(DateUtils.getMonthString(cal.get(Calendar.MONTH)), month);
				}

				month.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<String, SortedSet<AnnouncementBean>> getArchivedAnnouncementBeansMap(int msg_type, School school): "
					+ e);
			throw new EsdWebException("Can not extract AnnouncementBean from DB.", e);
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

	public static void addAnnouncementBean(AnnouncementBean eBean) throws EsdWebException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin awsd_user.esd_web.add_msg(?, ?, ?, ?, ?, ?, ?, ?, ?); end;");
			stat.setDate(1, new java.sql.Date(eBean.getDate().getTime()));
			stat.setString(2, eBean.getHeader());
			stat.setString(3, eBean.getBody());
			stat.setInt(4, eBean.getType().getTypeID());
			stat.setString(5, eBean.getImage());
			stat.setString(6, eBean.getImageCaption());
			stat.setString(7, eBean.getFullStoryLink());
			stat.setBoolean(8, eBean.isShowingOnFrontPage());
			if (eBean.getSchool() != null)
				stat.setInt(9, eBean.getSchool().getSchoolID());
			else
				stat.setNull(9, OracleTypes.NUMBER);

			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("AnnouncementBeanManager.getAnnouncementBeans(): " + e);
			throw new EsdWebException("Can not ADD AnnouncementBean to DB.", e);
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

	public static void updateAnnouncementBean(AnnouncementBean eBean) throws EsdWebException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin awsd_user.esd_web.update_msg(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); end;");
			stat.setInt(1, eBean.getID());
			stat.setDate(2, new java.sql.Date(eBean.getDate().getTime()));
			stat.setString(3, eBean.getHeader());
			stat.setString(4, eBean.getBody());
			stat.setInt(5, eBean.getType().getTypeID());
			stat.setString(6, eBean.getImage());
			stat.setString(7, eBean.getImageCaption());
			stat.setString(8, eBean.getFullStoryLink());
			stat.setBoolean(9, eBean.isShowingOnFrontPage());
			stat.setBoolean(10, eBean.isArchived());
			if (eBean.getSchool() != null)
				stat.setInt(11, eBean.getSchool().getSchoolID());
			else
				stat.setNull(11, OracleTypes.NUMBER);

			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("AnnouncementBeanManager.getAnnouncementBeans(): " + e);
			throw new EsdWebException("Can not ADD AnnouncementBean to DB.", e);
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

	public static void deleteAnnouncementBean(int msg_id) throws EsdWebException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.esd_web.del_msg(?); end;");
			stat.setInt(1, msg_id);
			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("AnnouncementBeanManager.deleteAnnouncementBean(int visit_id): " + e);
			throw new EsdWebException("Can not DELETE AnnouncementBean to DB.", e);
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

	public static void archiveAnnouncementBean(int msg_id) throws EsdWebException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.esd_web.archive_msg(?); end;");
			stat.setInt(1, msg_id);
			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("AnnouncementBeanManager.deleteAnnouncementBean(int visit_id): " + e);
			throw new EsdWebException("Can not ARCHIVE AnnouncementBean to DB.", e);
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

	public static void removeFrontPageAnnouncementBean(int msg_id) throws EsdWebException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.esd_web.remove_front_page_msg(?); end;");
			stat.setInt(1, msg_id);
			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("AnnouncementBeanManager.deleteAnnouncementBean(int visit_id): " + e);
			throw new EsdWebException("Can not MODIFY AnnouncementBean to DB.", e);
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

	public static AnnouncementBean createAnnouncementBean(ResultSet rs) {

		AnnouncementBean aBean = null;
		try {
			aBean = new AnnouncementBean();

			aBean.setID(rs.getInt("MSG_ID"));
			aBean.setArchived(rs.getBoolean("ARCHIVED"));
			aBean.setBody(rs.getString("MSG_BODY"));
			aBean.setDate(new java.util.Date(rs.getDate("MSG_DATE").getTime()));
			aBean.setFullStoryLink(rs.getString("MSG_FULL_STORY_LINK"));
			aBean.setHeader(rs.getString("MSG_HEADER"));
			aBean.setImage(rs.getString("MSG_IMAGE"));
			aBean.setImageCaption(rs.getString("MSG_IMAGE_CAPTION"));
			aBean.setShowOnFrontPage(rs.getBoolean("SHOW_ON_FRONT_PAGE"));
			aBean.setType(rs.getInt("MSG_TYPE"));
			if (rs.getInt("SCHOOL_ID") > 0)
				aBean.setSchool(SchoolDB.createSchoolBean(rs));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}
