package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

import com.awsd.common.Utils;
import com.awsd.mail.bean.AlertBean;
import com.awsd.school.School;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantFilterParameters;
import com.esdnl.personnel.jobs.bean.ApplicantFilterParametersSS;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;
import com.esdnl.personnel.jobs.constants.DocumentType;
import com.esdnl.personnel.jobs.constants.DocumentTypeSS;
import com.esdnl.personnel.jobs.dao.comparator.IntegerReverseComparator;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class ApplicantProfileManager {

	public static ApplicantProfileBean[] getApplicantProfileBeans() throws JobOpportunityException {

		Vector<ApplicantProfileBean> profiles = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			profiles = new Vector<ApplicantProfileBean>(100, 100);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_applicant_profiles; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				profiles.add(createApplicantProfileBean(rs));
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileManager.getApplicantProfileBean(String): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) profiles.toArray(new ApplicantProfileBean[0]);
	}

	public static ApplicantProfileBean getApplicantProfileBean(String sin) throws JobOpportunityException {

		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_applicant_profile(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createApplicantProfileBean(rs);
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileManager.getApplicantProfileBean(String): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

	public static ApplicantProfileBean[] getApplicantProfileBeanBySurname(String surname_part)
			throws JobOpportunityException {

		Vector<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantProfileBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_appl_profile_by_surname(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, surname_part);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileManager.getApplicantProfileBeanByJob(String): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) v_opps.toArray(new ApplicantProfileBean[0]);
	}

	public static ApplicantProfileBean[] getApplicantProfileBeanByNameSearch(String name_parts)
			throws JobOpportunityException {

		Vector<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantProfileBean>(5);

			String parts[] = name_parts.split(" ");

			System.err.println("PARTS: " + parts.length);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_appl_profile_by_namepart(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.setString(2, parts[0].trim());
			if (parts.length > 1)
				stat.setString(3, parts[1].trim());
			else
				stat.setNull(3, OracleTypes.VARCHAR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileManager.getApplicantProfileBeanByJob(String): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) v_opps.toArray(new ApplicantProfileBean[0]);
	}

	public static ApplicantProfileBean[] searchApplicantProfileBeanByName(String term) throws JobOpportunityException {

		Vector<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantProfileBean>(5);

			String[] parts = term.split(" ");

			con = DAOUtils.getConnection();
			if (parts.length > 1) {
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.search_appl_profile_by_name(?, ?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, parts[0]);
				stat.setString(3, parts[1]);
			}
			else {
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.search_appl_profile_by_name(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, parts[0]);
			}

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println(
					"ApplicantProfileBean[] ApplicantProfileManager.searchApplicantProfileBeanByName(String term): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) v_opps.toArray(new ApplicantProfileBean[0]);
	}

	public static ApplicantProfileBean[] filterApplicantProfileBeans(String filter) throws JobOpportunityException {

		Vector<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantProfileBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.filter_appl_profiles(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, filter);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println(
					"ApplicantProfileBean[] ApplicantProfileManager.filterApplicantProfileBeans(String filter): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) v_opps.toArray(new ApplicantProfileBean[0]);
	}

	public static ApplicantProfileBean[] filterApplicantProfileBeansSS(String filter, String filtertype)
			throws JobOpportunityException {

		Vector<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantProfileBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.filter_appl_profiles_ss(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, filter);
			stat.setString(3, filtertype);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println(
					"ApplicantProfileBean[] filterApplicantProfileBeansSS(String filter, String filtertype) : " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) v_opps.toArray(new ApplicantProfileBean[0]);
	}

	public static ApplicantProfileBean getApplicantProfileBeanByEmail(String email) throws JobOpportunityException {

		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_appl_profile_by_email(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, email);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createApplicantProfileBean(rs);
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileManager.getApplicantProfileBean(String): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

	public static ApplicantProfileBean[] getApplicantProfileBeanByJob(String comp_num) throws JobOpportunityException {

		Vector<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantProfileBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_job_appls(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, comp_num);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileManager.getApplicantProfileBeanByJob(String): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) v_opps.toArray(new ApplicantProfileBean[0]);
	}

	public static ApplicantProfileBean[] getApplicantProfileBeanBySubList(int list_id) throws JobOpportunityException {

		Vector<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantProfileBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sublist_appls(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, list_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileManager.getApplicantProfileBeanBySubList(String): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) v_opps.toArray(new ApplicantProfileBean[0]);
	}

	public static ApplicantProfileBean[] getApplicantShortlist(JobOpportunityBean opp) throws JobOpportunityException {

		Vector<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantProfileBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_short_list(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, opp.getCompetitionNumber());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileBean[] getApplicantShortlist(String comp_num): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) v_opps.toArray(new ApplicantProfileBean[0]);
	}

	public static HashMap<String, ApplicantProfileBean> getApplicantShortlistMap(JobOpportunityBean opp)
			throws JobOpportunityException {

		HashMap<String, ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<String, ApplicantProfileBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_short_list(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, opp.getCompetitionNumber());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.put(eBean.getSIN(), eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileBean[] getApplicantShortlist(String comp_num): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

	public static ApplicantProfileBean[] getApplicantShortlistExcludingInterviewDeclines(JobOpportunityBean opp)
			throws JobOpportunityException {

		Vector<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantProfileBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_short_list_no_declines(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, opp.getCompetitionNumber());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println(
					"ApplicantProfileBean[] getApplicantShortlistExcludingInterviewDeclines(JobOpportunityBean opp): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) v_opps.toArray(new ApplicantProfileBean[0]);
	}

	public static ArrayList<ApplicantProfileBean> getApplicantShortlistInterviewDeclines(JobOpportunityBean opp)
			throws JobOpportunityException {

		ArrayList<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new ArrayList<ApplicantProfileBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_short_list_declines(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, opp.getCompetitionNumber());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileBean[] getApplicantShortlistInterviewDeclines(JobOpportunityBean opp): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

	public static HashMap<String, ApplicantProfileBean> getApplicantShortlistInterviewDeclinesMap(JobOpportunityBean opp)
			throws JobOpportunityException {

		HashMap<String, ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<String, ApplicantProfileBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_short_list_declines(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, opp.getCompetitionNumber());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.put(eBean.getSIN(), eBean);
			}
		}
		catch (SQLException e) {
			System.err.println(
					"HashMap<String, ApplicantProfileBean> getApplicantShortlistInterviewDeclinesMap(JobOpportunityBean opp): "
							+ e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

	public static TreeMap<String, TreeMap<Integer, Vector<ApplicantProfileBean>>> getPoolShortlistMap(JobOpportunityBean opp)
			throws JobOpportunityException {

		TreeMap<String, TreeMap<Integer, Vector<ApplicantProfileBean>>> regions = null;
		TreeMap<Integer, Vector<ApplicantProfileBean>> days = null;
		Vector<ApplicantProfileBean> apps = null;

		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			regions = new TreeMap<String, TreeMap<Integer, Vector<ApplicantProfileBean>>>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_pool_short_list(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, opp.getCompetitionNumber());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);

				if (regions.containsKey(rs.getString("REGION_NAME")))
					days = regions.get(rs.getString("REGION_NAME"));
				else {
					days = new TreeMap<Integer, Vector<ApplicantProfileBean>>(new IntegerReverseComparator());
					regions.put(rs.getString("REGION_NAME"), days);
				}

				if (days.containsKey(rs.getInt("days")))
					apps = days.get(rs.getInt("days"));
				else {
					apps = new Vector<ApplicantProfileBean>();
					days.put(rs.getInt("days"), apps);
				}

				apps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("HashMap<String, ApplicantProfileBean> getPoolShortlistMap(JobOpportunityBean opp): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

	public static ApplicantProfileBean[] getApplicantShortlist(SubListBean list) throws JobOpportunityException {

		Vector<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantProfileBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sublist_short_list2(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, list.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileBean[] getApplicantShortlist(String comp_num): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) v_opps.toArray(new ApplicantProfileBean[0]);
	}

	public static HashMap<String, ApplicantProfileBean> getApplicantShortlistMap(SubListBean list)
			throws JobOpportunityException {

		HashMap<String, ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<String, ApplicantProfileBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sublist_short_list(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, list.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.put(eBean.getSIN(), eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileBean[] getApplicantShortlist(String comp_num): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

	public static ApplicantProfileBean[] getApplicantsNotApproved(SubListBean list) throws JobOpportunityException {

		Vector<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantProfileBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sublist_apps_not_approved(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, list.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileBean[] getApplicantShortlist(String comp_num): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) v_opps.toArray(new ApplicantProfileBean[0]);
	}

	public static HashMap<String, ApplicantProfileBean> getApplicantsNotApprovedMap(SubListBean list)
			throws JobOpportunityException {

		HashMap<String, ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<String, ApplicantProfileBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sublist_apps_not_approved(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, list.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.put(eBean.getSIN(), eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileBean[] getApplicantShortlist(String comp_num): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

	public static ApplicantProfileBean[] getApplicantsWorking(SubListBean list) throws JobOpportunityException {

		Vector<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantProfileBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sublist_apps_working(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, list.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileBean[] getApplicantShortlist(String comp_num): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) v_opps.toArray(new ApplicantProfileBean[0]);
	}

	public static HashMap<String, ApplicantProfileBean> getApplicantsWorkingMap(SubListBean list)
			throws JobOpportunityException {

		HashMap<String, ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<String, ApplicantProfileBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sublist_apps_working(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, list.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.put(eBean.getSIN(), eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileBean[] getApplicantShortlist(String comp_num): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

	public static ApplicantProfileBean[] getApplicantShortlist(SubListBean list, School s)
			throws JobOpportunityException {

		Vector<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantProfileBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sublist_short_list2(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, list.getId());
			stat.setInt(3, s.getSchoolID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileBean[] getApplicantShortlist(String comp_num): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) v_opps.toArray(new ApplicantProfileBean[0]);
	}

	public static ApplicantProfileBean[] filterJobApplicantProfileBean(ApplicantFilterParameters params)
			throws JobOpportunityException {

		Vector<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		StringBuffer sql = null;

		try {
			sql = new StringBuffer();

			sql.append("SELECT DISTINCT APPLICANT.*,");
			//issue with sublist filter needed to remove extra sin
			sql.append(
					"APPLICANT_ESD_EXP.PERM_SCHOOL,APPLICANT_ESD_EXP.PERM_POSITION,APPLICANT_ESD_EXP.REPL_TIME,APPLICANT_ESD_EXP.SUB_TIME,"
							+ "APPLICANT_ESD_EXP.PK_ID,APPLICANT_ESD_EXP.CONTRACT_SCHOOL,APPLICANT_ESD_EXP.CONTRACT_ENDDATE,APPLICANT_ESD_EXP.PERM_TIME,");
			//APPLICANT_ESD_EXP.*, 
			sql.append(" NVL(sen.\"SENORITY\", 0) SENORITY FROM AWSD_USER.APPLICANT LEFT JOIN ");
			sql.append(
					"(SELECT REPLACE(REPLACE(TRIM(mas.\"SIN\"),' ', ''), '-', '') \"SENSIN\", SEN.\"Seniority_Numeric\" \"SENORITY\", MAS.\"EMAIL\" \"SENEMAIL\" FROM AWSD_USER.SDS_PREMPMAS mas JOIN AWSD_USER.SDS_PRSENMAS sen ON mas.EMP_ID = sen.\"Employee\") sen ");
			sql.append(
					"ON (REPLACE(REPLACE(TRIM(APPLICANT.SIN2),' ', ''), '-', '') = sen.\"SENSIN\" OR REPLACE(REPLACE(TRIM(APPLICANT.\"SIN\"),' ', ''), '-', '') = sen.\"SENSIN\" OR LOWER(APPLICANT.EMAIL) = LOWER(sen.\"SENEMAIL\")) ");
			sql.append(" LEFT JOIN AWSD_USER.APPLICANT_ESD_EXP ON APPLICANT.SIN =  APPLICANT_ESD_EXP.SIN WHERE ");

			if (params.getJob() != null)
				sql.append("APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.JOB_APPLICANT WHERE COMP_NUM = '"
						+ params.getJob().getCompetitionNumber() + "')");
			else if (params.getSubList() != null)
				sql.append("APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.JOB_SUB_LIST_APPLICANT WHERE LIST_ID = '"
						+ params.getSubList().getId() + "' AND SHORTLISTED='Y')");

			if (params.getPermanentContract().equals("Y")) {
				sql.append(
						" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_ESD_EXP WHERE PERM_SCHOOL != -1) ");
			}
			else if (params.getPermanentContract().equals("N")) {
				sql.append(
						" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_ESD_EXP WHERE PERM_SCHOOL = -1 OR PERM_SCHOOL IS NULL) ");
			}
			if (params.getReplacementExp() > 0) {
				sql.append(" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_ESD_EXP WHERE REPL_TIME >= "
						+ params.getReplacementExp() + ") ");
			}
			if (params.getPermanentExp() > 0) {
				sql.append(" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_ESD_EXP WHERE PERM_TIME >= "
						+ params.getPermanentExp() + ") ");
			}

			if (params.getTotalExp() > 0) {
				sql.append(
						" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_ESD_EXP WHERE (PERM_TIME + REPL_TIME) >= "
								+ params.getTotalExp() + ") ");
			}

			if (params.getSubDays() > 0) {
				sql.append(" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_ESD_EXP WHERE SUB_TIME >= "
						+ params.getSubDays() + ") ");
			}
			if (params.getSpecialEducationCourses() > 0) {
				sql.append(" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_EDU_OTHER WHERE SPED_CRS >= "
						+ params.getSpecialEducationCourses() + ") ");
			}

			if (params.getFrenchCourses() > 0) {
				sql.append(" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_EDU_OTHER WHERE FR_CRS >= "
						+ params.getFrenchCourses() + ") ");
			}

			if (params.getMathCourses() > 0) {
				sql.append(" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_EDU_OTHER WHERE MATH_CRS >= "
						+ params.getMathCourses() + ") ");
			}

			if (params.getEnglishCourses() > 0) {
				sql.append(" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_EDU_OTHER WHERE ENGLISH_CRS >= "
						+ params.getEnglishCourses() + ") ");
			}

			if (params.getMusicCourses() > 0) {
				sql.append(" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_EDU_OTHER WHERE MUSIC_CRS >= "
						+ params.getMusicCourses() + ") ");
			}

			if (params.getTechnologyCourses() > 0) {
				sql.append(" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_EDU_OTHER WHERE TECH_CRS >= "
						+ params.getTechnologyCourses() + ") ");
			}

			if (params.getScienceCourses() > 0) {
				sql.append(" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_EDU_OTHER WHERE SCIENCE_CRS >= "
						+ params.getScienceCourses() + ") ");
			}

			if (params.isTLARequirements()) {
				sql.append(
						" AND (APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_EDU_OTHER WHERE TOTAL_CRS_COMPLETED >= 20) "
								+ "OR APPLICANT.SIN IN (SELECT DISTINCT APPLICANT_ID FROM AWSD_USER.APPLICANT_DOCUMENT WHERE DOCUMENT_TYPE = "
								+ DocumentType.ECE.getValue() + "))");
			}

			if ((params.getRegionalPreferences() != null) && (params.getRegionalPreferences().length > 0)) {
				sql.append(" AND APPLICANT.SIN IN (SELECT DISTINCT APPLICANT_ID FROM AWSD_USER.APPLICANT_REGION_PREFS WHERE ");
				for (int i = 0; i < params.getRegionalPreferences().length; i++) {
					if (i > 0)
						sql.append(" OR ");

					sql.append("REGION_ID = " + params.getRegionalPreferences()[i]);
				}
				sql.append(") ");
			}

			if ((params.getDegrees() != null) && (params.getDegrees().length > 0)) {
				sql.append(" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_EDU WHERE ");
				for (int i = 0; i < params.getDegrees().length; i++) {
					if (i > 0)
						sql.append(" OR ");

					sql.append("DEGREE_ID = '" + params.getDegrees()[i] + "'");
				}
				sql.append(") ");
			}

			if ((params.getMajors() != null) && (params.getMajors().length > 0)) {
				sql.append(" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_EDU WHERE ");
				for (int i = 0; i < params.getMajors().length; i++) {
					if (i > 0)
						sql.append(" OR ");

					sql.append("MAJOR_ID = " + params.getMajors()[i]);
				}
				sql.append(") ");
			}

			if ((params.getMinors() != null) && (params.getMinors().length > 0)) {
				sql.append(" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_EDU WHERE ");
				for (int i = 0; i < params.getMinors().length; i++) {
					if (i > 0)
						sql.append(" OR ");

					sql.append("MINOR_ID = " + params.getMinors()[i]);
				}
				sql.append(") ");
			}

			if ((params.getTrainingMethods() != null) && (params.getTrainingMethods().length > 0)) {
				sql.append(" AND APPLICANT.SIN IN (SELECT DISTINCT SIN FROM AWSD_USER.APPLICANT_EDU_OTHER WHERE ");
				for (int i = 0; i < params.getTrainingMethods().length; i++) {
					if (i > 0)
						sql.append(" OR ");

					sql.append("TRNLVL = " + params.getTrainingMethods()[i]);
				}
				sql.append(") ");
			}

			sql.append(" ORDER BY sen.\"SENORITY\" DESC, SURNAME, FIRSTNAME");

			//in order for new fields on sub filter screen to work we to adjust the query
			if (params.getSubList() != null) {
				sql.insert(0, "select * from (");
				sql.append(")");
				//now we insert the sql for majors
				sql.append(
						"slistdata left outer join (select sin sin99, LISTAGG(subject_name, ',') WITHIN GROUP (ORDER BY subject_name) majors");
				sql.append(
						" from (select * from applicant_edu, subject where applicant_edu.MAJOR_ID=subject.SUBJECT_ID) group by sin) test1 on slistdata.sin=test1.sin99");
				sql.append(
						" left outer join (select sin sin999, LISTAGG(subject_name, ',') WITHIN GROUP (ORDER BY subject_name) minors");
				sql.append(
						" from (select * from applicant_edu, subject where applicant_edu.MINOR_ID=subject.SUBJECT_ID) group by sin) test1 on slistdata.sin=test1.sin999");
			}

			v_opps = new Vector<ApplicantProfileBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareStatement(sql.toString());
			rs = stat.executeQuery();

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println(sql.toString());
			System.err.println("ApplicantProfileManager.getApplicantProfileBeanByJob(String): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) v_opps.toArray(new ApplicantProfileBean[0]);
	}

	public static ApplicantProfileBean addApplicantProfileBean(ApplicantProfileBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall(
					"begin awsd_user.personnel_jobs_pkg.add_applicant_profile(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");

			stat.setString(1, abean.getEmail().toLowerCase());
			stat.setString(2, abean.getPassword());
			stat.setString(3, abean.getSurname());
			stat.setString(4, abean.getFirstname());
			stat.setString(5, abean.getMiddlename());
			stat.setString(6, abean.getMaidenname());
			stat.setString(7, abean.getSIN());
			stat.setString(8, abean.getAddress1());
			stat.setString(9, abean.getAddress2());
			stat.setString(10, abean.getProvince());
			stat.setString(11, abean.getCountry());
			stat.setString(12, abean.getPostalcode());
			stat.setString(13, abean.getHomephone());
			stat.setString(14, abean.getWorkphone());
			stat.setString(15, abean.getCellphone());
			if (abean.getDOB() != null)
				stat.setDate(16, new java.sql.Date(abean.getDOB().getTime()));
			else
				stat.setNull(16, OracleTypes.DATE);
			stat.setString(17, abean.getProfileType());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantProfileBean addJobOpportunityBean(ApplicantProfileBean abean): " + e);
			throw new JobOpportunityException("Can not add ApplicantProfileBean to DB.", e);
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

	public static ApplicantProfileBean updateApplicantProfileBean(ApplicantProfileBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall(
					"begin awsd_user.personnel_jobs_pkg.update_applicant_profile(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");

			stat.setString(1, abean.getEmail());
			stat.setString(2, abean.getPassword());
			stat.setString(3, abean.getSurname());
			stat.setString(4, abean.getFirstname());
			stat.setString(5, abean.getMiddlename());
			stat.setString(6, abean.getMaidenname());
			stat.setString(7, abean.getSIN());
			stat.setString(8, abean.getAddress1());
			stat.setString(9, abean.getAddress2());
			stat.setString(10, abean.getProvince());
			stat.setString(11, abean.getCountry());
			stat.setString(12, abean.getPostalcode());
			stat.setString(13, abean.getHomephone());
			stat.setString(14, abean.getWorkphone());
			stat.setString(15, abean.getCellphone());
			stat.setString(16, abean.getSIN2());
			if (abean.getDOB() != null)
				stat.setDate(17, new java.sql.Date(abean.getDOB().getTime()));
			else
				stat.setNull(17, OracleTypes.DATE);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantProfileBean addJobOpportunityBean(ApplicantProfileBean abean): " + e);
			throw new JobOpportunityException("Can not add ApplicantProfileBean to DB.", e);
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

	public static void applyForPosition(ApplicantProfileBean abean, String comp_num) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.apply_for_position(?,?); end;");

			stat.setString(1, abean.getSIN());
			stat.setString(2, comp_num);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(
					"ApplicantProfileBean.void applyForPosition(ApplicantProfileBean abean, String comp_num): " + e);
			throw new JobOpportunityException("Can not apply for position.", e);
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

	public static void applyForPosition(ApplicantProfileBean abean, SubListBean list) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.apply_for_sub_list(?,?); end;");

			stat.setString(1, abean.getSIN());
			stat.setInt(2, list.getId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(
					"ApplicantProfileBean.void applyForPosition(ApplicantProfileBean abean, String comp_num): " + e);
			throw new JobOpportunityException("Can not apply for position.", e);
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

	public static void withdrawApplicantion(ApplicantProfileBean abean, JobOpportunityBean job)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.unapply_for_job(?,?); end;");

			stat.setString(1, abean.getSIN());
			stat.setString(2, job.getCompetitionNumber());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(
					"ApplicantProfileBean.void withdrawApplicant(ApplicantProfileBean abean, String comp_num): " + e);
			throw new JobOpportunityException("Can not apply for position.", e);
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

	public static void shortListApplicant(String sin, JobOpportunityBean opp) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.short_list_applicant(?,?); end;");

			stat.setString(1, sin);
			stat.setString(2, opp.getCompetitionNumber());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(
					"ApplicantProfileBean.void shortListApplicant(ApplicantProfileBean abean, String comp_num): " + e);
			throw new JobOpportunityException("Can not apply for position.", e);
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

	public static void shortListApplicant(String sin, SubListBean list) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.short_list_sublist_applicant(?,?); end;");

			stat.setString(1, sin);
			stat.setInt(2, list.getId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(
					"ApplicantProfileBean.void shortListApplicant(ApplicantProfileBean abean, String comp_num): " + e);
			throw new JobOpportunityException("Can not apply for position.", e);
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

	public static void removeShortlistApplicant(String sin, JobOpportunityBean opp) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.remove_short_list_applicant(?,?); end;");

			stat.setString(1, sin);
			stat.setString(2, opp.getCompetitionNumber());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(
					"ApplicantProfileBean.void removeShortlistApplicant(ApplicantProfileBean abean, String comp_num): " + e);
			throw new JobOpportunityException("Can not apply for position.", e);
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

	public static void declineInterviewShortlistApplicant(String sin, JobOpportunityBean opp)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.short_list_declined_interview(?,?); end;");

			stat.setString(1, sin);
			stat.setString(2, opp.getCompetitionNumber());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void declineInterviewShortlistApplicant(String sin, JobOpportunityBean opp): " + e);
			throw new JobOpportunityException("Can not decline interview.", e);
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

	public static void applicantNotApproved(String sin, SubListBean list) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.sublist_applicant_not_approved(?,?); end;");

			stat.setString(1, sin);
			stat.setInt(2, list.getId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(
					"ApplicantProfileBean.void shortListApplicant(ApplicantProfileBean abean, String comp_num): " + e);
			throw new JobOpportunityException("Can not apply for position.", e);
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

	public static void applicantWorking(ApplicantProfileBean profile) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.sublist_applicant_working(?,?); end;");

			stat.setString(1, profile.getSIN());
			stat.setString(2, Utils.getCurrentSchoolYear());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(
					"ApplicantProfileBean.void shortListApplicant(ApplicantProfileBean abean, String comp_num): " + e);
			throw new JobOpportunityException("Can not apply for position.", e);
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

	public static void applicantLoggedIn(ApplicantProfileBean profile) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.applicant_logon(?); end;");

			stat.setString(1, profile.getSIN());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(
					"ApplicantProfileBean.void shortListApplicant(ApplicantProfileBean abean, String comp_num): " + e);
			throw new JobOpportunityException("Can not apply for position.", e);
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

	public static void deleteApplicantProfile(String uid) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.delete_applicant(?); end;");

			stat.setString(1, uid);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void ApplicantProfileBean.deleteApplicantProfile(String uid): " + e);
			throw new JobOpportunityException("Can not delete for position.", e);
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

	public static void applicantProfileModified(ApplicantProfileBean profile) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.applicant_modified(?); end;");

			stat.setString(1, profile.getUID());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("applicantProfileModified(ApplicantProfileBean profile): " + e);
			throw new JobOpportunityException("Can not update ApplicantProfileBean.", e);
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

	public static String getSubListShortListBySchool(int schoolid) throws JobOpportunityException {

		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sublist_s_l_by_school(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, schoolid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				sb.append(createApplicantProfileBean(rs).generateXML());

			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileBean[] getSubListShortListBySchool(int schoolid): " + e);
			throw new JobOpportunityException("Can not extract SubListBySchool from DB.", e);

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
		System.out.println("bean" + sb.toString());
		return sb.toString();
	}

	public static String getSubListMajors(String sin) {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_major_list_by_sin(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				sb.append(rs.getString(2));

			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileBean[] getSubListShortListBySchool(int schoolid): " + e);

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
		return sb.toString();
	}

	public static String getSubListMinors(String sin) {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_minor_list_by_sin(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				sb.append(rs.getString(2));

			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileBean[] getSubListShortListBySchool(int schoolid): " + e);

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
		return sb.toString();
	}

	public static ApplicantProfileBean[] filterJobApplicantProfileBeanSS(ApplicantFilterParametersSS params)
			throws JobOpportunityException {

		Vector<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		StringBuffer sqlfrom = null;
		StringBuffer sqlwhere = null;
		String DATE_FORMAT = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

		try {
			sql = new StringBuffer();
			sqlfrom = new StringBuffer();
			sqlwhere = new StringBuffer();
			//set intial values for applicant table
			sql.append("SELECT DISTINCT app.* ");
			sqlfrom.append(" FROM APPLICANT app ");
			sqlwhere.append(" WHERE app.PROFILETYPE='S'");
			boolean usedExp = false;
			boolean usedCurrent = false;
			if (params.getCurrentEmployee() != null) {
				if (!(params.getCurrentEmployee().equals("A"))) {
					sqlfrom.append(" left outer join APPLICANT_NLESD_EXP_SS nexp on app.SIN=nexp.SIN ");
					sqlwhere.append(" and nexp.CURRENTLYEMPLOYED='" + params.getCurrentEmployee() + "' ");
					usedExp = true;
				}
			}
			if (params.getSenorityDate() != null && params.getSenorityDateFilter() != null) {
				if (params.getSenorityDateFilter().equals("before")) {
					sqlwhere.append(
							" and nexp.SENORITYDATE <= TO_DATE('" + sdf.format(params.getSenorityDate()) + "','mm/dd/yyyy') ");
				}
				else {
					sqlwhere.append(
							" and nexp.SENORITYDATE >= TO_DATE('" + sdf.format(params.getSenorityDate()) + "','mm/dd/yyyy') ");
				}
				//check to see if the table was added before
				if (!(usedExp)) {
					sqlfrom.append(" left outer join APPLICANT_NLESD_EXP_SS nexp on app.SIN=nexp.SIN ");
				}
			}
			if (params.getCurrentPosition() != null) {
				sqlfrom.append(" left outer join APPLICANT_CURRENT_POSITIONS cpos on app.SIN=cpos.SIN ");
				sqlwhere.append(" and cpos.POSITION_HELD = " + params.getCurrentPosition());
				usedCurrent = true;
			}
			if (params.getCurrentPositionType() != null) {
				if (!(usedCurrent)) {
					sqlfrom.append(" left outer join APPLICANT_CURRENT_POSITIONS cpos on app.SIN=cpos.SIN ");
				}

				sqlwhere.append(" and cpos.POSITION_TYPE ='" + params.getCurrentPositionType() + "'");
			}

			if(params.getUnionCode() > 0){
				sqlfrom.append(" left outer join APPLICANT_CURRENT_POSITIONS cpos on app.SIN=cpos.SIN ");
				sqlfrom.append(" left outer join JOB_RTH_POSITIONS rth on cpos.POSITION_HELD=rth.ID ");
				sqlwhere.append(" and rth.union_code = " + params.getUnionCode());
				// we check to see if we need to add the position clause
				if(params.getCurrentUnionPosition() > 0){
					sqlwhere.append(" and rth.ID = " + params.getCurrentUnionPosition());
				}
				usedCurrent=true;
			}
			if ((params.getDegrees() != null) && (params.getDegrees().length > 0)) {
				sqlfrom.append(" left outer join APPLICANT_EDU_POST_SEC_SS edu on app.SIN=edu.SIN ");
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < params.getDegrees().length; i++) {
					if (i == 0) {
						sb.append("'" + params.getDegrees()[i] + "'");
					}
					else {
						sb.append(",'" + params.getDegrees()[i] + "'");
					}

				}
				sqlwhere.append(" and (edu.DEGREE_ID in(" + sb.toString() + ") ");
				//append the type since it could be one of three types for support staff
				sqlwhere.append(" and edu.CTYPE=1) ");
			}

			if ((params.getCertificates() != null) && (params.getCertificates().length > 0)) {
				sqlfrom.append(" left outer join APPLICANT_EDU_POST_SEC_SS edu1 on app.SIN=edu1.SIN ");
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < params.getCertificates().length; i++) {
					if (i == 0) {
						sb.append("'" + params.getCertificates()[i] + "'");
					}
					else {
						sb.append(",'" + params.getCertificates()[i] + "'");
					}

				}
				sqlwhere.append(" and (edu1.DEGREE_ID in(" + sb.toString() + ") ");
				//append the type since it could be one of three types for support staff
				sqlwhere.append(" and edu1.CTYPE=3) ");
			}

			if ((params.getDiplomas() != null) && (params.getDiplomas().length > 0)) {
				sqlfrom.append(" left outer join APPLICANT_EDU_POST_SEC_SS edu2 on app.SIN=edu2.SIN ");
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < params.getDiplomas().length; i++) {
					if (i == 0) {
						sb.append("'" + params.getDiplomas()[i] + "'");
					}
					else {
						sb.append(",'" + params.getDiplomas()[i] + "'");
					}

				}
				sqlwhere.append(" and (edu2.DEGREE_ID in(" + sb.toString() + ") ");
				//append the type since it could be one of three types for support staff
				sqlwhere.append(" and edu2.CTYPE=2) ");
			}
			if (params.isDriversAbstract()) {
				sqlfrom.append(" left outer join APPLICANT_DOCUMENT adoc on app.SIN=adoc.APPLICANT_ID ");
				sqlwhere.append(" and adoc.DOCUMENT_TYPE=" + DocumentTypeSS.DRIVERS_ABSTRACT.getValue());
			}
			if (params.isOhsTraining()) {
				sqlfrom.append(" left outer join APPLICANT_DOCUMENT adoc1 on app.SIN=adoc1.APPLICANT_ID ");
				sqlwhere.append(" and adoc1.DOCUMENT_TYPE=" + DocumentTypeSS.OHS_TRAINING.getValue());
			}
			if (params.isCodeOfConduct()) {
				sqlfrom.append(" left outer join APPLICANT_DOCUMENT adoc2 on app.SIN=adoc2.APPLICANT_ID ");
				sqlwhere.append(" and adoc2.DOCUMENT_TYPE=" + DocumentTypeSS.CODE_OF_CONDUCT.getValue());
			}
			if (params.isFirstAid()) {
				sqlfrom.append(" left outer join APPLICANT_DOCUMENT adoc3 on app.SIN=adoc3.APPLICANT_ID ");
				sqlwhere.append(" and adoc3.DOCUMENT_TYPE=" + DocumentTypeSS.FIRST_AID.getValue());
			}
			if (params.isWhmis()) {
				sqlfrom.append(" left outer join APPLICANT_DOCUMENT adoc4 on app.SIN=adoc4.APPLICANT_ID ");
				sqlwhere.append(" and adoc4.DOCUMENT_TYPE=" + DocumentTypeSS.WHMIS.getValue());
			}
			if (params.isDriversLicense()) {
				sqlfrom.append(" left outer join APPLICANT_DOCUMENT adoc5 on app.SIN=adoc5.APPLICANT_ID ");
				sqlwhere.append(" and adoc5.DOCUMENT_TYPE=" + DocumentTypeSS.DRIVERS_LICENSE.getValue());
			}

			//now we filter to applicants for this comp
			sqlwhere.append(" and app.sin in(select distinct sin from job_applicant where comp_num='"
					+ params.getJob().getCompetitionNumber() + "')");
			// add the sorting
			sqlwhere.append(" ORDER BY app.SURNAME, app.FIRSTNAME");
			v_opps = new Vector<ApplicantProfileBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareStatement(sql.toString() + sqlfrom.toString() + sqlwhere.toString());
			rs = stat.executeQuery();

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println(sql.toString());
			System.err.println(
					"ApplicantProfileBean[] filterJobApplicantProfileBeanSS(ApplicantFilterParametersSS params): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) v_opps.toArray(new ApplicantProfileBean[0]);
	}

	public static ApplicantProfileBean[] getApplicantProfileBeanByNameSearchSS(String name_parts)
			throws JobOpportunityException {

		Vector<ApplicantProfileBean> v_opps = null;
		ApplicantProfileBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<ApplicantProfileBean>(5);

			String parts[] = name_parts.split(" ");

			System.err.println("PARTS: " + parts.length);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_appl_profile_by_namepart_s(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.setString(2, parts[0].trim());
			if (parts.length > 1)
				stat.setString(3, parts[1].trim());
			else
				stat.setNull(3, OracleTypes.VARCHAR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantProfileBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantProfileBean[] getApplicantProfileBeanByNameSearchSS(String name_parts): " + e);
			throw new JobOpportunityException("Can not extract ApplicantProfileBean from DB.", e);
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

		return (ApplicantProfileBean[]) v_opps.toArray(new ApplicantProfileBean[0]);
	}

	public static ApplicantProfileBean createApplicantProfileBean(ResultSet rs) {

		ApplicantProfileBean aBean = null;
		try {
			aBean = new ApplicantProfileBean();

			aBean.setAddress1(rs.getString("ADDRESS1"));
			aBean.setAddress2(rs.getString("ADDRESS2"));
			aBean.setCellphone(rs.getString("CELLPHONE"));
			aBean.setCountry(rs.getString("COUNTRY"));
			aBean.setEmail(rs.getString("EMAIL"));
			aBean.setFirstname(rs.getString("FIRSTNAME"));
			aBean.setHomephone(rs.getString("HOMEPHONE"));
			aBean.setMaidenname(rs.getString("MAIDENNAME"));
			aBean.setMiddlename(rs.getString("MIDDLENAME"));
			aBean.setPassword(rs.getString("PASSWORD"));
			aBean.setPostalcode(rs.getString("POSTALCODE"));
			aBean.setProvince(rs.getString("PROVINCE"));
			aBean.setSIN(rs.getString("SIN"));
			aBean.setSIN2(rs.getString("SIN2"));
			aBean.setSurname(rs.getString("SURNAME"));
			aBean.setWorkphone(rs.getString("WORKPHONE"));

			try {
				if (rs.getTimestamp("DOB") != null) {

					aBean.setDOB(new java.util.Date(rs.getDate("DOB").getTime()));
				}

			}
			catch (SQLException e) {}

			try {
				if (rs.getTimestamp("APPLIED_DATE") != null)
					aBean.setAppliedDate(new java.util.Date(rs.getTimestamp("APPLIED_DATE").getTime()));
			}
			catch (SQLException e) {}

			try {
				if (rs.getTimestamp("MODIFIED_DATE") != null)
					aBean.setModifiedDate(new java.util.Date(rs.getTimestamp("MODIFIED_DATE").getTime()));
			}
			catch (SQLException e) {}

			//used for filtering
			try {
				aBean.setSenority(rs.getDouble("SENORITY"));
			}
			catch (SQLException e) {}

			//used for filtering
			//aBean.setEsdExp(ApplicantEsdExperienceManager.createApplicantEsdExperienceBean(rs));
			//populate sublist major and minors
			//aBean.setMajorsList(getSubListMajors(aBean.getSIN()));
			//aBean.setMinorsList(getSubListMinors(aBean.getSIN()));
			try {
				aBean.setMajorsList(rs.getString("majors"));
			}
			catch (java.sql.SQLException e) {
				aBean.setMajorsList("");
			}
			try {
				aBean.setMinorsList(rs.getString("minors"));
			}
			catch (java.sql.SQLException e) {
				aBean.setMajorsList("");
			}
			aBean.setProfileType(rs.getString("PROFILETYPE"));
		}
		catch (SQLException e) {
			aBean = null;

			System.out.println("ERROR CREATING PROFILE BEAN: " + e.getMessage());

			try {
				new AlertBean(e);
			}
			catch (Exception ex) {}
		}
		return aBean;
	}

}