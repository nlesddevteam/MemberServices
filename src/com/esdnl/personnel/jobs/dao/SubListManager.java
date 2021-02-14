package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.Grade;
import com.awsd.school.Subject;
import com.awsd.school.bean.RegionBean;
import com.awsd.school.bean.RegionException;
import com.awsd.school.dao.RegionManager;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;
import com.esdnl.personnel.jobs.constants.SubstituteListConstant;

public class SubListManager {

	public static SubListBean addSubListBean(SubListBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_sub_list(?,?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, abean.getTitle());
			stat.setInt(3, abean.getType().getValue());
			stat.setInt(4, abean.getRegion().getId());
			stat.setString(5, abean.getSchoolYear());
			stat.setDate(6, new java.sql.Date(abean.getExpiryDate().getTime()));

			stat.execute();

			abean.setId(((OracleCallableStatement) stat).getInt(1));
			stat.close();

			if (abean.getId() == -1) {
				con.rollback();
				throw new JobOpportunityException("Could not added SubListBean.");
			}
			else {
				Subject[] subjects = abean.getSubjectAreas();
				if (subjects.length > 0) {
					stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_sub_list_subject(?, ?); end;");

					for (int i = 0; i < subjects.length; i++) {
						stat.clearParameters();

						stat.setInt(1, abean.getId());
						stat.setInt(2, subjects[i].getSubjectID());

						stat.execute();
					}

					stat.close();
				}

				Grade[] grades = abean.getGrades();
				if (grades.length > 0) {
					stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_sub_list_grade(?, ?); end;");

					for (int i = 0; i < grades.length; i++) {
						stat.clearParameters();

						stat.setInt(1, abean.getId());
						stat.setInt(2, grades[i].getGradeID());

						stat.execute();
					}

					stat.close();
				}
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SubListBean addSubListBean(SubListBean abean): " + e);
			throw new JobOpportunityException("Can not add SubListBean to DB.", e);
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

	public static void deleteSubListBean(int id) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_sub_list(?); end;");

			stat.setInt(1, id);

			stat.execute();

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteSubListBean(int): " + e);
			throw new JobOpportunityException("Can not delete SubListBean to DB.", e);
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

	public static void updateSubListBean(SubListBean list) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.update_sub_list(?,?); end;");

			stat.setInt(1, list.getId());
			stat.setBoolean(2, list.isActive());

			stat.execute();

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("updateSubListBean(SubListBean list): " + e);
			throw new JobOpportunityException("Can not update SubListBean to DB.", e);
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

	public static SubListBean[] getSubListBeans(String school_year, int type_id) throws JobOpportunityException {

		Vector<SubListBean> v_opps = null;
		SubListBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<SubListBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sub_lists(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, school_year);
			stat.setInt(3, type_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createSubListBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("SubListBean[] getSubListBeans(String school_year, int type_id): " + e);
			throw new JobOpportunityException("Can not extract SubListBean from DB.", e);
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

		return (SubListBean[]) v_opps.toArray(new SubListBean[0]);
	}

	public static TreeMap<RegionBean, ArrayList<SubListBean>> getSubListBeansByRegion(String school_year,
																																										SubstituteListConstant type)
			throws JobOpportunityException {

		TreeMap<RegionBean, ArrayList<SubListBean>> region_sublists = null;
		SubListBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			region_sublists = new TreeMap<RegionBean, ArrayList<SubListBean>>(new Comparator<RegionBean>() {

				public int compare(RegionBean o1, RegionBean o2) {

					return o1.getName().compareTo(o2.getName());
				}
			});

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sub_lists(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, school_year);
			stat.setInt(3, type.getValue());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createSubListBean(rs);

				if (!region_sublists.containsKey(eBean.getRegion()))
					region_sublists.put(eBean.getRegion(), new ArrayList<SubListBean>());

				((ArrayList<SubListBean>) region_sublists.get(eBean.getRegion())).add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<RegionBean, ArrayList<SubListBean>> getSubListBeansByRegion(String school_year, SubstituteListConstant type): "
					+ e);
			throw new JobOpportunityException("Can not extract SubListBean from DB.", e);
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

		return region_sublists;
	}

	public static SubListBean[] getSubListBeans(RegionBean region, String school_year, SubstituteListConstant type)
			throws JobOpportunityException {

		Vector<SubListBean> v_opps = null;
		SubListBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<SubListBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sub_lists(?, ?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, region.getId());
			stat.setString(3, school_year);
			stat.setInt(4, type.getValue());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createSubListBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("SubListBean[] getSubListBeans(RegionBean region, String school_year, SubstituteListConstant type): "
					+ e);
			throw new JobOpportunityException("Can not extract SubListBean from DB.", e);
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

		return (SubListBean[]) v_opps.toArray(new SubListBean[0]);
	}

	public static HashMap<Integer, SubListBean> getSubListBeans(ApplicantProfileBean pbean)
			throws JobOpportunityException {

		HashMap<Integer, SubListBean> v_opps = null;
		SubListBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<Integer, SubListBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sub_lists_applicant(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, pbean.getSIN());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createSubListBean(rs);

				v_opps.put(new Integer(rs.getInt("LIST_ID")), eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("HashMap<Integer, SubListBean> getSubListBeans(ApplicantProfileBean pbean): " + e);
			throw new JobOpportunityException("Can not extract SubListBean from DB.", e);
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

	public static SubListBean getSubListBean(int list_id) throws JobOpportunityException {

		SubListBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sub_list(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, list_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createSubListBean(rs);
		}
		catch (SQLException e) {
			System.err.println("SubListBean getSubListBean(int list_id): " + e);
			throw new JobOpportunityException("Can not extract SubListBean from DB.", e);
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

	public static TreeMap<String, ArrayList<SubListBean>> getSubListBeans(SubstituteListConstant type)
			throws JobOpportunityException {

		TreeMap<String, ArrayList<SubListBean>> listMap = null;
		SubListBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			listMap = new TreeMap<String, ArrayList<SubListBean>>(new Comparator<String>() {

				public int compare(String o1, String o2) {

					return -1 * o1.compareTo(o2);
				}
			});

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sub_lists_by_type(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, type.getValue());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createSubListBean(rs);

				if (!listMap.containsKey(eBean.getSchoolYear()))
					listMap.put(eBean.getSchoolYear(), new ArrayList<SubListBean>());

				((ArrayList<SubListBean>) listMap.get(eBean.getSchoolYear())).add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<String, ArrayList<SubListBean>> getSubListBeans(SubstituteListConstant type): " + e);
			throw new JobOpportunityException("Can not extract SubListBean from DB.", e);
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

		return listMap;
	}
	public static SubListBean[] getSubListBeansZone(int zoneid, String school_year, SubstituteListConstant type)
			throws JobOpportunityException {

		Vector<SubListBean> v_opps = null;
		SubListBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<SubListBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sub_lists_reg(?, ?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, zoneid);
			stat.setString(3, school_year);
			stat.setInt(4, type.getValue());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createSubListBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("getSubListBeansZone(int zoneid, String school_year, SubstituteListConstant type): "
					+ e);
			throw new JobOpportunityException("Can not extract SubListBean from DB.", e);
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

		return (SubListBean[]) v_opps.toArray(new SubListBean[0]);
	}
	public static SubListBean createSubListBean(ResultSet rs) {

		SubListBean aBean = null;

		try {
			aBean = new SubListBean();

			aBean.setId(rs.getInt("LIST_ID"));
			aBean.setTitle(rs.getString("LIST_TITLE"));
			aBean.setType(SubstituteListConstant.get(rs.getInt("TYPE_ID")));
			aBean.setRegion(RegionManager.getRegionBean(rs.getInt("REGION_ID")));
			aBean.setSchoolYear(rs.getString("SCHOOL_YEAR"));
			if (rs.getDate("EXPIRE_DATE") != null)
				aBean.setExpiryDate(rs.getDate("EXPIRE_DATE"));
			aBean.setActive(rs.getBoolean("ACTIVE"));

			// aBean.addGradeLevelCollection(SubListGradeManager.getSubListGradeBeansCollection(aBean));
			// aBean.addSubjectAreaCollection(SubListSubjectManager.getSubListSubjectBeansCollection(aBean));

		}
		catch (SQLException e) {
			aBean = null;
		}
		catch (RegionException e) {
			aBean = null;
		}

		return aBean;
	}
}