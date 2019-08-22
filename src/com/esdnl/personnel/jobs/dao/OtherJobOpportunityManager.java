package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

import com.awsd.mail.bean.AlertBean;
import com.awsd.school.bean.RegionBean;
import com.awsd.school.bean.RegionException;
import com.awsd.school.dao.RegionManager;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.OtherJobOpportunityBean;
import com.esdnl.personnel.jobs.constants.PostingType;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class OtherJobOpportunityManager {

	public static OtherJobOpportunityBean[] getOtherJobOpportunityBeans() throws JobOpportunityException {

		Vector<OtherJobOpportunityBean> v_opps = null;
		OtherJobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<OtherJobOpportunityBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_other_job_opps; end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createOtherJobOpportunityBean(rs);

				v_opps.add(jBean);
			}
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityManager.getJobOpportunityBeans(boolean): " + e);
			throw new JobOpportunityException("Can not extract JobOpportunityBean from DB.", e);
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

		return (OtherJobOpportunityBean[]) v_opps.toArray(new OtherJobOpportunityBean[0]);
	}

	public static HashMap<RegionBean, Vector<OtherJobOpportunityBean>> getInternalOnlyOtherJobOpportunityBeans()
			throws JobOpportunityException {

		HashMap<RegionBean, Vector<OtherJobOpportunityBean>> regions = null;
		Vector<OtherJobOpportunityBean> opps = null;
		OtherJobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			regions = new HashMap<RegionBean, Vector<OtherJobOpportunityBean>>();

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_other_job_opps; end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createOtherJobOpportunityBean(rs);

				if (jBean.isExternalOnly())
					continue;

				if (regions.containsKey(jBean.getRegion()))
					opps = (Vector<OtherJobOpportunityBean>) regions.get(jBean.getRegion());
				else {
					opps = new Vector<OtherJobOpportunityBean>();
					regions.put(jBean.getRegion(), opps);
				}
				opps.add(jBean);
			}
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityManager.getJobOpportunityBeans(boolean): " + e);
			throw new JobOpportunityException("Can not extract JobOpportunityBean from DB.", e);
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

	public static OtherJobOpportunityBean[] getClosedOtherJobOpportunityBeans() throws JobOpportunityException {

		Vector<OtherJobOpportunityBean> v_opps = null;
		OtherJobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<OtherJobOpportunityBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_other_job_opps_closed; end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createOtherJobOpportunityBean(rs);

				v_opps.add(jBean);
			}
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityManager.getJobOpportunityBeans(boolean): " + e);
			throw new JobOpportunityException("Can not extract JobOpportunityBean from DB.", e);
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

		return (OtherJobOpportunityBean[]) v_opps.toArray(new OtherJobOpportunityBean[0]);
	}

	public static void addOtherJobOpportunityBean(OtherJobOpportunityBean jbean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			//add the opportunity info
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_other_job_opp(?,?,?,?,?); end;");
			stat.setString(1, jbean.getTitle());
			stat.setTimestamp(2, new Timestamp(jbean.getEndDate().getTime()));
			stat.setString(3, jbean.getFilename());
			stat.setString(4, Integer.toString(jbean.getPostingType().getValue()));
			stat.setInt(5, jbean.getRegion().getId());
			stat.execute();
			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("JobOpportunityManager.addJobOpportunityBeans(boolean): " + e);
			throw new JobOpportunityException("Can not add JobOpportunityBean to DB.", e);
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

	public static OtherJobOpportunityBean getOtherJobOpportunityBeanById(int job_id) throws JobOpportunityException {

		OtherJobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_other_job_opp_by_id(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, job_id);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				jBean = createOtherJobOpportunityBean(rs);
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityManager.getOtherJobOpportunityById(int): " + e);
			throw new JobOpportunityException("Can not extract OtherJobOpportunityBean from DB.", e);
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

		return jBean;

	}

	public static void deleteOtherJobOpportunityBean(Integer job_id) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			// get the opportunity info
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.delete_job_other(?); end;");
			stat.setInt(1, job_id);

			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityManager.deleteOtherJobOpportunityBean(String): " + e);
			throw new JobOpportunityException("Can not delete JobOpportunityBean to DB.", e);
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

	public static void cancelOtherJobOpportunityBean(Integer job_id, String reason, String userid)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			// get the opportunity info
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.cancel_job_other(?,?,?); end;");
			stat.setString(1, userid);
			stat.setString(2, reason);
			stat.setInt(3, job_id);

			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityManager.deleteOtherJobOpportunityBean(String): " + e);
			throw new JobOpportunityException("Can not delete JobOpportunityBean to DB.", e);
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

	public static OtherJobOpportunityBean[] getOtherJobOpportunityBeanByRegion(int region_id)
			throws JobOpportunityException {

		Vector<OtherJobOpportunityBean> v_opps = null;
		OtherJobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			v_opps = new Vector<OtherJobOpportunityBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_other_job_opps_by_region(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, region_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createOtherJobOpportunityBean(rs);

				v_opps.add(jBean);
			}
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityManager.getOtherJobOpportunityBeanByRegion(int): " + e);
			throw new JobOpportunityException("Can not extract OtherJobOpportunityBean from DB.", e);
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

		return (OtherJobOpportunityBean[]) v_opps.toArray(new OtherJobOpportunityBean[0]);
	}

	public static OtherJobOpportunityBean[] getOtherJobOpportunityBeanByZone(int zone_id) throws JobOpportunityException {

		Vector<OtherJobOpportunityBean> v_opps = null;
		OtherJobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			v_opps = new Vector<OtherJobOpportunityBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_other_job_opps_by_zone(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, zone_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createOtherJobOpportunityBean(rs);

				v_opps.add(jBean);
			}
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityManager.getOtherJobOpportunityBeanByZone(int): " + e);
			throw new JobOpportunityException("Can not extract OtherJobOpportunityBean from DB.", e);
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

		return (OtherJobOpportunityBean[]) v_opps.toArray(new OtherJobOpportunityBean[0]);
	}

	public static OtherJobOpportunityBean updateOtherJobOpportunityBean(OtherJobOpportunityBean jbean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			// add the opportunity info
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.update_other_job_opp(?,?,?,?,?,?); end;");
			stat.setInt(1, jbean.getId());
			stat.setString(2, jbean.getTitle());
			stat.setTimestamp(3, new Timestamp(jbean.getEndDate().getTime()));
			stat.setString(4, jbean.getFilename());
			stat.setString(5, Integer.toString(jbean.getPostingType().getValue()));
			stat.setInt(6, jbean.getRegion().getId());

			stat.execute();
			stat.close();

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("OtherJobOpportunityManager.updateOtherJobOpportunityBeans(boolean): " + e);
			throw new JobOpportunityException("Can not update OtherJobOpportunityBean to DB.", e);
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

		return jbean;
	}

	public static TreeMap<String, Vector<OtherJobOpportunityBean>> getInternalOnlyOtherJobOpportunityBeans2()
			throws JobOpportunityException {

		TreeMap<String, Vector<OtherJobOpportunityBean>> regions = null;
		Vector<OtherJobOpportunityBean> opps = null;
		OtherJobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			regions = new TreeMap<String, Vector<OtherJobOpportunityBean>>();

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.PERSONNEL_JOBS_PKG.get_other_job_opps_reg; end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createOtherJobOpportunityBean(rs);

				if (jBean.isExternalOnly()) {
					continue;
				}

				if ((jBean.getRegion() == null)) {

					new AlertBean(new RegionException("OtherJobOpportunityBean[id=" + jBean.getId()
							+ "] has no assigned REGION"));

					continue;
				}
				else if ((jBean.getRegion().getZone() == null)) {

					new AlertBean(new RegionException("OtherJobOpportunityBean[id=" + jBean.getId() + ", RegionID="
							+ jBean.getRegion().getId() + "] has no assigned ZONE"));

					continue;
				}

				if (regions.containsKey(jBean.getRegion().getZone().getZoneName() + " - " + jBean.getRegion().getName()))
					opps = (Vector<OtherJobOpportunityBean>) regions.get(
							jBean.getRegion().getZone().getZoneName() + " - " + jBean.getRegion().getName());
				else {
					opps = new Vector<OtherJobOpportunityBean>();
					regions.put(jBean.getRegion().getZone().getZoneName() + " - " + jBean.getRegion().getName(), opps);
				}
				opps.add(jBean);
			}
		}
		catch (Exception e) {
			System.err.println("JobOpportunityManager.getJobOpportunityBeans(boolean): " + e);
			throw new JobOpportunityException("Can not extract JobOpportunityBean from DB.", e);
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

	public static TreeMap<String, Vector<OtherJobOpportunityBean>> getExternalOnlyOtherJobOpportunityBeans2()
			throws JobOpportunityException {

		TreeMap<String, Vector<OtherJobOpportunityBean>> regions = null;
		Vector<OtherJobOpportunityBean> opps = null;
		OtherJobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			regions = new TreeMap<String, Vector<OtherJobOpportunityBean>>();

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.PERSONNEL_JOBS_PKG.get_other_job_opps_reg; end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createOtherJobOpportunityBean(rs);

				if (jBean.isInternalOnly()) {
					continue;
				}

				if ((jBean.getRegion() == null)) {

					new AlertBean(new RegionException("OtherJobOpportunityBean[id=" + jBean.getId()
							+ "] has no assigned REGION"));

					continue;
				}
				else if ((jBean.getRegion().getZone() == null)) {

					new AlertBean(new RegionException("OtherJobOpportunityBean[id=" + jBean.getId() + ", RegionID="
							+ jBean.getRegion().getId() + "] has no assigned ZONE"));

					continue;
				}

				if (regions.containsKey(jBean.getRegion().getZone().getZoneName() + " - " + jBean.getRegion().getName()))
					opps = (Vector<OtherJobOpportunityBean>) regions.get(
							jBean.getRegion().getZone().getZoneName() + " - " + jBean.getRegion().getName());
				else {
					opps = new Vector<OtherJobOpportunityBean>();
					regions.put(jBean.getRegion().getZone().getZoneName() + " - " + jBean.getRegion().getName(), opps);
				}
				opps.add(jBean);
			}
		}
		catch (Exception e) {
			System.err.println("JobOpportunityManager.getJobOpportunityBeans(boolean): " + e);
			throw new JobOpportunityException("Can not extract JobOpportunityBean from DB.", e);
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

	public static OtherJobOpportunityBean[] getApplicantAppliedOpenJobOpportunityBeans(String sin)
			throws JobOpportunityException {

		Vector<OtherJobOpportunityBean> v_opps = null;
		OtherJobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			v_opps = new Vector<OtherJobOpportunityBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_appl_open_ojob_opps(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createOtherJobOpportunityBean(rs);

				v_opps.add(jBean);
			}
		}
		catch (SQLException e) {
			System.err.println("OtherJobOpportunityBean[] getApplicantAppliedOpenJobOpportunityBeans(String sin): " + e);
			throw new JobOpportunityException("Can not extract OtherJobOpportunityBean from DB.", e);
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

		return (OtherJobOpportunityBean[]) v_opps.toArray(new OtherJobOpportunityBean[0]);
	}

	public static OtherJobOpportunityBean createOtherJobOpportunityBean(ResultSet rs) {

		OtherJobOpportunityBean jBean = null;

		try {
			jBean = new OtherJobOpportunityBean();
			jBean.setId(rs.getInt("JOB_ID"));
			jBean.setEndDate(new java.util.Date(rs.getTimestamp("CLOSING_DATE").getTime()));
			jBean.setFilename(rs.getString("FILENAME"));
			jBean.setTitle(rs.getString("JOB_TITLE"));
			jBean.setPostingType(PostingType.get(Integer.parseInt(rs.getString("INTERNAL_ONLY"))));
			if (rs.getTimestamp("CANCELLED_DATE") != null) {
				jBean.setCancelledDate(new java.util.Date(rs.getTimestamp("CANCELLED_DATE").getTime()));
			}
			jBean.setCancelled_reason(rs.getString("CANCELLED_REASON"));
			jBean.setCancelled_by(rs.getString("CANCELLED_BY"));
			try {
				if (rs.getInt("REGION_ID") == 0)
					jBean.setRegion(RegionManager.getRegionBean(5)); //district wide
				else
					jBean.setRegion(RegionManager.getRegionBean(rs.getInt("REGION_ID")));
			}
			catch (Exception e) {}
		}
		catch (SQLException e) {
			e.printStackTrace();
			jBean = null;
		}

		return jBean;
	}
}