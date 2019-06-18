package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.AssignmentEducationBean;
import com.esdnl.personnel.jobs.bean.AssignmentMajorMinorBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;
import com.esdnl.util.StringUtils;
import com.nlesd.school.bean.SchoolZoneBean;

public class JobOpportunityManager {

	private static int loadNextCompetitionNumber() throws JobOpportunityException {

		int next = 0;
		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_next_comp_num; end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.execute();

			next = ((OracleCallableStatement) stat).getInt(1);

			if (next <= 0) {
				throw new JobOpportunityException("Can not extract next competition number from DB.");
			}
		}
		catch (SQLException e) {
			System.err.println("int getNextCompetitionNumber(): " + e);
			throw new JobOpportunityException("Can not extract next competition number from DB.", e);
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

		return next;
	}

	public static synchronized String getNextCompetitionNumber(SchoolZoneBean zone) throws JobOpportunityException {

		return com.esdnl.personnel.v2.utils.StringUtils.getCurrentSchoolYear() + "-"
				+ (new DecimalFormat("#00000")).format(JobOpportunityManager.loadNextCompetitionNumber())
				+ ((zone != null) ? "-" + (new DecimalFormat("00")).format(zone.getZoneId()) : "");
	}

	public static synchronized String getNextCompetitionNumber() throws JobOpportunityException {

		return getNextCompetitionNumber(null);
	}

	public static JobOpportunityBean[] getJobOpportunityBeans(String status) throws JobOpportunityException {

		Vector<JobOpportunityBean> v_opps = null;
		JobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<JobOpportunityBean>(5);

			con = DAOUtils.getConnection();
			if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("OPEN"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_open_job_opps; end;");
			else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("CLOSED"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_closed_job_opps; end;");
			else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("AWARDED"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_awarded_job_opps; end;");
			else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("CANCELLED"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_cancelled_job_opps; end;");
			else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("NOSHORTLIST"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_noshortlist_job_opps; end;");
			else
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_all_job_opps; end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createJobOpportunityBean(rs);

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

		return (JobOpportunityBean[]) v_opps.toArray(new JobOpportunityBean[0]);
	}

	public static Vector<JobOpportunityBean> getJobOpportunityBeansVector(String status) throws JobOpportunityException {

		Vector<JobOpportunityBean> v_opps = null;
		JobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<JobOpportunityBean>(5);

			con = DAOUtils.getConnection();
			if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("OPEN"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_open_job_opps; end;");
			else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("CLOSED"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_closed_job_opps; end;");
			else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("AWARDED"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_awarded_job_opps; end;");
			else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("CANCELLED"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_cancelled_job_opps; end;");
			else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("NOSHORTLIST"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_noshortlist_job_opps; end;");
			else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("UNADVERTISED"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_unadvertised_job_opps; end;");
			else
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_all_job_opps; end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createJobOpportunityBean(rs);

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

		return v_opps;
	}
	public static JobOpportunityBean[] getJobOpportunityBeans(String status, int job_type) throws JobOpportunityException {

		Vector<JobOpportunityBean> v_opps = null;
		JobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<JobOpportunityBean>(5);

			con = DAOUtils.getConnection();
			if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("OPEN"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_open_job_opps(?); end;");
			else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("CLOSED"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_closed_job_opps(?); end;");
			else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("AWARDED"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_awarded_job_opps(?); end;");
			else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("CANCELLED"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_cancelled_job_opps(?); end;");
			else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("NOSHORTLIST"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_noshortlist_job_opps(?); end;");
			else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("UNADVERTISED"))
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_unadvertised_job_opps(?); end;");
			else
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_all_job_opps(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, job_type);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createJobOpportunityBean(rs);

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

		return (JobOpportunityBean[]) v_opps.toArray(new JobOpportunityBean[0]);
	}
	public static JobOpportunityBean[] getJobOpportunityBeans(String status, int job_type, int zone) throws JobOpportunityException {

		Vector<JobOpportunityBean> v_opps = null;
		JobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<JobOpportunityBean>(5);

			con = DAOUtils.getConnection();
			if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("OPEN")){
				if(zone ==0){
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_open_job_opps(?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
				}else{
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_open_job_opps_zone(?,?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
					stat.setInt(3, zone);
				}
				
			}else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("CLOSED")){
				if(zone ==0){
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_closed_job_opps(?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
				}else{
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_closed_job_opps_zone(?,?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
					stat.setInt(3, zone);
				}
			}else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("AWARDED")){
				if(zone ==0){
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_awarded_job_opps(?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
				}else{
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_awarded_job_opps_zone(?,?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
					stat.setInt(3, zone);
				}
			}else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("CANCELLED")){
				if(zone ==0){
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_cancelled_job_opps(?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
				}else{
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_cancelled_job_opps_zn(?,?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
					stat.setInt(3, zone);
				}				
			}else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("NOSHORTLIST")){
				if(zone ==0){
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_noshortlist_job_opps(?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
				}else{
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_noshortlist_job_opps_zn(?,?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
					stat.setInt(3, zone);
				}					
			}else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("UNADVERTISED")){
				if(zone ==0){
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_unadvertised_job_opps(?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
				}else{
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_unadvertised_job_opps_zn(?,?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
					stat.setInt(3, zone);
				}					
			}else{
				if(zone ==0){
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_all_job_opps(?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
				}else{
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_all_job_opps_zone(?,?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
					stat.setInt(3, zone);
				}					
			}
				
			
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, job_type);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createJobOpportunityBean(rs);

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

		return (JobOpportunityBean[]) v_opps.toArray(new JobOpportunityBean[0]);
	}
	public static JobOpportunityBean[] getJobOpportunityBeansSupport(String status, int job_type, int zone) throws JobOpportunityException {

		Vector<JobOpportunityBean> v_opps = null;
		JobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<JobOpportunityBean>(5);

			con = DAOUtils.getConnection();
			if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("OPEN")){
				if(zone ==0){
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_open_job_opps_ss(?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
				}else{
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_open_job_opps_zone_ss(?,?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
					stat.setInt(3, zone);
				}
				
			}else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("CLOSED")){
				if(zone ==0){
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_closed_job_opps_ss(?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
				}else{
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_closed_job_opps_zone_ss(?,?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
					stat.setInt(3, zone);
				}
			}else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("AWARDED")){
				if(zone ==0){
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_awarded_job_opps_ss(?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
				}else{
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_awarded_job_opps_zone_ss(?,?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
					stat.setInt(3, zone);
				}
			}else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("CANCELLED")){
				if(zone ==0){
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_cancelled_job_opps_ss(?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
				}else{
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_cancelled_job_opps_zn_ss(?,?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
					stat.setInt(3, zone);
				}				
			}else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("NOSHORTLIST")){
				if(zone ==0){
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_noshortlist_job_opps_ss(?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
				}else{
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_noshortlist_job_opps_zn_ss(?,?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
					stat.setInt(3, zone);
				}					
			}else if (!StringUtils.isEmpty(status) && status.equalsIgnoreCase("UNADVERTISED")){
				if(zone ==0){
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_unadvertised_job_opps_ss(?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
				}else{
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_unadvertised_job_opps_zn_s(?,?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
					stat.setInt(3, zone);
				}					
			}else{
				if(zone ==0){
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_all_job_opps_ss(?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
				}else{
					stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_all_job_opps_zone_ss(?,?); end;");
					stat.registerOutParameter(1, OracleTypes.CURSOR);
					stat.setInt(2, job_type);
					stat.setInt(3, zone);
				}					
			}
				
			
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, job_type);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createJobOpportunityBean(rs);

				v_opps.add(jBean);
			}
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityBean[] getJobOpportunityBeansSupport(String status, int job_type, int zone): " + e);
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

		return (JobOpportunityBean[]) v_opps.toArray(new JobOpportunityBean[0]);
	}
	public static JobOpportunityBean[] getJobOpportunityBeans(int location_id) throws JobOpportunityException {

		Vector<JobOpportunityBean> v_opps = null;
		JobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<JobOpportunityBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_job_opps_loc(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, location_id);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createJobOpportunityBean(rs);

				v_opps.add(jBean);
			}
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityBean[] getJobOpportunityBeans(int location_id): " + e);
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

		return (JobOpportunityBean[]) v_opps.toArray(new JobOpportunityBean[0]);
	}

	public static JobOpportunityBean[] getApplicantOpenJobOpportunityBeans(String sin) throws JobOpportunityException {

		Vector<JobOpportunityBean> v_opps = null;
		JobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<JobOpportunityBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_appl_open_job_opps(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createJobOpportunityBean(rs);

				v_opps.add(jBean);
			}
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityBean[] getJobOpportunityBeans(int location_id): " + e);
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

		return (JobOpportunityBean[]) v_opps.toArray(new JobOpportunityBean[0]);
	}

	public static HashMap<String, JobOpportunityBean> getApplicantOpenJobOpportunityBeansMap(String sin)
			throws JobOpportunityException {

		HashMap<String, JobOpportunityBean> v_opps = null;
		JobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<String, JobOpportunityBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_appl_open_job_opps(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createJobOpportunityBean(rs);

				v_opps.put(jBean.getCompetitionNumber(), jBean);
			}
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityBean[] getJobOpportunityBeans(int location_id): " + e);
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

		return v_opps;
	}

	public static JobOpportunityBean[] searchJobOpportunityBeansByCompetitionNumber(String term)
			throws JobOpportunityException {

		Vector<JobOpportunityBean> v_opps = null;
		JobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<JobOpportunityBean>(5);

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.search_job_opps_by_comp_num(?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, term);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createJobOpportunityBean(rs);

				v_opps.add(jBean);
			}
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityBean[] searchJobOpportunityBeansByCompetitionNumber(String term): " + e);
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

		return (JobOpportunityBean[]) v_opps.toArray(new JobOpportunityBean[0]);
	}

	public static synchronized JobOpportunityBean addJobOpportunityBean(JobOpportunityBean jbean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		Iterator<?> iter = null;
		JobOpportunityAssignmentBean ass = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			JobOpportunityAssignmentBean[] abeans = jbean.toArray(new JobOpportunityAssignmentBean[0]);

			if (abeans.length > 0) {
				jbean.setCompetitionNumber(JobOpportunityManager.getNextCompetitionNumber(abeans[0].getLocationZone()));
			}
			else {
				jbean.setCompetitionNumber(JobOpportunityManager.getNextCompetitionNumber());
			}
			if (jbean.isUnadvertise()) {
				jbean.setCompetitionNumber(jbean.getCompetitionNumber() + "UA");
			}
			
			if(jbean.getIsSupport().equals("Y")){
				jbean.setCompetitionNumber(jbean.getCompetitionNumber() + "SS");
			}

			// add the opportunity info
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_job_opp(?,?,?,?,?,?,?,?); end;");
			stat.setString(1, jbean.getPositionTitle());
			stat.setString(2, jbean.getCompetitionNumber());
			stat.setString(3, jbean.getJobAdText());
			stat.setTimestamp(4, new Timestamp(jbean.getCompetitionEndDate().getTime()));
			stat.setDate(5, new Date(jbean.getListingDate().getTime()));
			stat.setInt(6, jbean.getJobType().getValue());
			stat.setBoolean(7, jbean.isCandidateListPrivate());
			stat.setString(8, jbean.getIsSupport());
			stat.execute();
			stat.close();

			iter = jbean.iterator();
			while (iter.hasNext()) {
				// add opportunty assignment info
				ass = (JobOpportunityAssignmentBean) iter.next();
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_job_opp_assign(?,?); end;");
				stat.registerOutParameter(1, OracleTypes.NUMBER);
				stat.setString(2, jbean.getCompetitionNumber());
				stat.setInt(3, ass.getLocation());
				stat.execute();

				ass.setAssignmentId(((OracleCallableStatement) stat).getInt(1));
				if (ass.getAssignmentId() == -1)
					throw new JobOpportunityException("Could not added JobOpportunityAssignment.");

				stat.close();

				// get education requirements
				AssignmentEducationBean[] edu = ass.getRequiredEducation();
				for (int i = 0; i < edu.length; i++) {
					stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_job_opp_assign_edu(?,?); end;");
					stat.setInt(1, ass.getAssignmentId());
					stat.setString(2, edu[i].getDegreeId());
					stat.execute();
					stat.close();
				}

				// get major/minor info
				AssignmentMajorMinorBean[] mjr = ass.getRequiredMajors();
				for (int i = 0; i < mjr.length; i++) {
					if (mjr[i].getMajorId() != -1) {
						stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_job_opp_assign_mjr(?,?); end;");
						stat.setInt(1, ass.getAssignmentId());
						stat.setInt(2, mjr[i].getMajorId());
						stat.execute();
						stat.close();
					}
					else if (mjr[i].getMinorId() != -1) {
						stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_job_opp_assign_mnrr(?,?); end;");
						stat.setInt(1, ass.getAssignmentId());
						stat.setInt(2, mjr[i].getMinorId());
						stat.execute();
						stat.close();
					}
				}

				// get trnmtd info
				TrainingMethodConstant[] trnmtd = ass.getRequriedTrainingMethods();
				for (int i = 0; i < trnmtd.length; i++) {
					stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_job_opp_assign_trnmtd(?,?); end;");
					stat.setInt(1, ass.getAssignmentId());
					stat.setInt(2, trnmtd[i].getValue());
					stat.execute();
					stat.close();
				}
			}
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

		return jbean;
	}

	/*
	 * 3/21/2014 - Modified to add SHORTLIST_COMPLETE_DATE
	 */
	public static JobOpportunityBean updateJobOpportunityBean(JobOpportunityBean jbean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		Iterator<?> iter = null;
		JobOpportunityAssignmentBean ass = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			// add the opportunity info
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.update_job_opp_2(?,?,?,?,?,?,?,?); end;");
			stat.setString(1, jbean.getPositionTitle());
			stat.setString(2, jbean.getCompetitionNumber());
			stat.setString(3, jbean.getJobAdText());
			stat.setTimestamp(4, new Timestamp(jbean.getCompetitionEndDate().getTime()));
			stat.setDate(5, new Date(jbean.getListingDate().getTime()));
			stat.setInt(6, jbean.getJobType().getValue());
			stat.setBoolean(7, jbean.isCandidateListPrivate());
			if (jbean.getShortlistCompleteDate() != null)
				stat.setTimestamp(8, new Timestamp(jbean.getShortlistCompleteDate().getTime()));
			else
				stat.setNull(8, OracleTypes.DATE);
			stat.execute();
			stat.close();

			iter = jbean.iterator();
			while (iter.hasNext()) {
				// add opportunty assignment info
				ass = (JobOpportunityAssignmentBean) iter.next();
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_job_opp_assign(?,?); end;");
				stat.registerOutParameter(1, OracleTypes.NUMBER);
				stat.setString(2, jbean.getCompetitionNumber());
				stat.setInt(3, ass.getLocation());
				stat.execute();

				ass.setAssignmentId(((OracleCallableStatement) stat).getInt(1));
				if (ass.getAssignmentId() == -1)
					throw new JobOpportunityException("Could not added JobOpportunityAssignment.");

				stat.close();

				// get education requirements
				AssignmentEducationBean[] edu = ass.getRequiredEducation();
				for (int i = 0; i < edu.length; i++) {
					stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_job_opp_assign_edu(?,?); end;");
					stat.setInt(1, ass.getAssignmentId());
					stat.setString(2, edu[i].getDegreeId());
					stat.execute();
					stat.close();
				}

				// get major/minor info
				AssignmentMajorMinorBean[] mjr = ass.getRequiredMajors();
				for (int i = 0; i < mjr.length; i++) {
					if (mjr[i].getMajorId() != -1) {
						stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_job_opp_assign_mjr(?,?); end;");
						stat.setInt(1, ass.getAssignmentId());
						stat.setInt(2, mjr[i].getMajorId());
						stat.execute();
						stat.close();
					}
					else if (mjr[i].getMinorId() != -1) {
						stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_job_opp_assign_mnrr(?,?); end;");
						stat.setInt(1, ass.getAssignmentId());
						stat.setInt(2, mjr[i].getMinorId());
						stat.execute();
						stat.close();
					}
				}

				// get trnmtd info
				TrainingMethodConstant[] trnmtd = ass.getRequriedTrainingMethods();
				for (int i = 0; i < trnmtd.length; i++) {
					stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_job_opp_assign_trnmtd(?,?); end;");
					stat.setInt(1, ass.getAssignmentId());
					stat.setInt(2, trnmtd[i].getValue());
					stat.execute();
					stat.close();
				}
			}

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

		return jbean;
	}

	public static JobOpportunityBean getJobOpportunityBean(String comp_num) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		JobOpportunityBean jbean = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			// get the opportunity info
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_job_opp(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, comp_num);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				jbean = createJobOpportunityBean(rs);

		}
		catch (SQLException e) {
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

		return jbean;
	}

	public static void awardJobOpportunityBean(String comp_num) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			// get the opportunity info
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.award_job_opp(?); end;");
			stat.setString(1, comp_num);
			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityManager.awardJobOpportunityBean(String): " + e);
			throw new JobOpportunityException("Can not award JobOpportunityBean to DB.", e);
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

	public static void deleteJobOpportunityBean(String comp_num) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			// get the opportunity info
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.delete_job(?); end;");
			stat.setString(1, comp_num);
			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityManager.awardJobOpportunityBean(String): " + e);
			throw new JobOpportunityException("Can not award JobOpportunityBean to DB.", e);
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

	public static void cancelJobOpportunityBean(String comp_num) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			// get the opportunity info
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.cancel_job(?); end;");
			stat.setString(1, comp_num);
			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("JobOpportunityManager.awardJobOpportunityBean(String): " + e);
			throw new JobOpportunityException("Can not award JobOpportunityBean to DB.", e);
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
	public static TreeMap getInternalOnlyOtherJobOpportunityBeans2() throws JobOpportunityException {
		TreeMap regions = null;
		JobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		Vector opps = null;
		try {
			regions = new TreeMap();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_open_job_opps_ss(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, JobTypeConstant.INTERNALONLY.getValue());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createJobOpportunityBean(rs);
				String keyname = jBean.getAssignments()[0].getLocationZone().getZoneName() + " - " + jBean.getAssignments()[0].getRegionText();

				if (regions.containsKey(keyname)){
					opps = (Vector) regions.get(keyname);
				}else{
					opps = new Vector();
					regions.put(keyname, opps);
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
	public static TreeMap getExternalOnlyOtherJobOpportunityBeans2() throws JobOpportunityException {
		TreeMap regions = null;
		JobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		Vector opps = null;
		try {
			regions = new TreeMap();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_open_job_opps_ss(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, JobTypeConstant.EXTERNALONLY.getValue());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createJobOpportunityBean(rs);
				String keyname = jBean.getAssignments()[0].getLocationZone().getZoneName() + " - " + jBean.getAssignments()[0].getRegionText();
				
				if (regions.containsKey(keyname)){
					opps = (Vector) regions.get(keyname);
				}else{
					opps = new Vector();
					regions.put(keyname, opps);
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
	public static TreeMap getInternalExternalOtherJobOpportunityBeans() throws JobOpportunityException {
		TreeMap regions = null;
		JobOpportunityBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		Vector opps = null;
		try {
			regions = new TreeMap();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_open_job_opps_ss(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, JobTypeConstant.INTERNALEXTERNAL.getValue());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				jBean = createJobOpportunityBean(rs);
				String keyname = jBean.getAssignments()[0].getLocationZone().getZoneName() + " - " + jBean.getAssignments()[0].getRegionText();
				
				if (regions.containsKey(keyname)){
					opps = (Vector) regions.get(keyname);
				}else{
					opps = new Vector();
					regions.put(keyname, opps);
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
	public static java.util.Date checkApplicantAppliedFor(ApplicantProfileBean abean, String jobid) throws JobOpportunityException {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		java.util.Date applieddate=null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.personnel_jobs_pkg.check_job_applicant_ss(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, abean.getSIN());
			stat.setString(3, jobid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				applieddate=new java.util.Date(rs.getTimestamp("APPLIED_DATE").getTime());
}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("static boolean checkApplicantAppliedFor(ApplicantProfileBean abean, String jobid) : "
					+ e);
			throw new JobOpportunityException("Can not check for position.", e);
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
		return applieddate;
	}	
	public static JobOpportunityBean createJobOpportunityBean(ResultSet rs) {

		JobOpportunityBean jBean = null;

		try {
			jBean = new JobOpportunityBean();
			jBean.setCompetitionNumber(rs.getString("COMP_NUM"));
			jBean.setPositionTitle(rs.getString("POS_TITLE"));
			// jBean.setEmploymentClass(EmploymentConstant.get(rs.getInt("EMP_CAT_ID")));
			jBean.setPostedDate(new java.util.Date(rs.getDate("POSTED_DATE").getTime()));
			jBean.setCompetitionEndDate(new java.util.Date(rs.getTimestamp("COMP_END_DATE").getTime()));
			if (rs.getDate("START_DATE") != null)
				jBean.setJobStartDate(new java.util.Date(rs.getDate("START_DATE").getTime()));
			if (rs.getDate("END_DATE") != null)
				jBean.setJobEndDate(new java.util.Date(rs.getDate("END_DATE").getTime()));
			if (rs.getDate("AWARDED_DATE") != null)
				jBean.setJobAwardedDate(new java.util.Date(rs.getDate("AWARDED_DATE").getTime()));
			jBean.setJobAdText(rs.getString("JOB_AD"));
			if (rs.getDate("DISPLAY_DATE") != null)
				jBean.setListingDate(new java.util.Date(rs.getDate("DISPLAY_DATE").getTime()));
			if (rs.getInt("JOB_TYPE") == 0)
				jBean.setJobType(JobTypeConstant.REGULAR);
			else
				jBean.setJobType(JobTypeConstant.get(rs.getInt("JOB_TYPE")));
			if (rs.getDate("MODIFIED_DATE") != null)
				jBean.setModifiedDate(new java.util.Date(rs.getDate("MODIFIED_DATE").getTime()));
			if (rs.getDate("CANCELLED_DATE") != null)
				jBean.setCancelledDate(new java.util.Date(rs.getDate("CANCELLED_DATE").getTime()));

			jBean.setPrivateCandidateList(rs.getBoolean("CANDIDATELIST_PRIVATE"));

			if (rs.getDate("SHORTLIST_COMPLETE_DATE") != null)
				jBean.setShortlistCompleteDate(new java.util.Date(rs.getTimestamp("SHORTLIST_COMPLETE_DATE").getTime()));

			jBean.add(JobOpportunityAssignmentManager.createJobOpportunityAssignmentBean(rs));
			jBean.setIsSupport(rs.getString("IS_SUPPORT"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			jBean = null;
		}

		return jBean;
	}
}