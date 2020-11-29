package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.awsd.personnel.Personnel;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.constants.RequestStatus;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.personnel.v2.database.sds.LocationManager;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class AdRequestManager {

	public static AdRequestBean[] getAdRequestBean(RequestStatus status) throws JobOpportunityException {

		Vector<AdRequestBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<AdRequestBean>();

			con = DAOUtils.getConnection();
			//call new function that will returned declined ad requests over the last 60 days
			if(status == RequestStatus.REJECTED) {
				stat = con.prepareCall("begin ? := awsd_user.personnel_ad_request.get_declined_ad_requests; end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
			}else {
				stat = con.prepareCall("begin ? := awsd_user.personnel_ad_request.get_ad_requests_by_status2(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, status.getId());
			}
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createAdRequestBean(rs));

		}
		catch (SQLException e) {
			System.err.println("AdRequestBean[] getAdRequestBeans(String sin): " + e);
			throw new JobOpportunityException("Can not extract AdRequestBean from DB.", e);
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

		return ((AdRequestBean[]) beans.toArray(new AdRequestBean[0]));
	}

	public static int getAdRequestBeanCount(RequestStatus status) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		int cnt = 0;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_ad_request.get_ad_request_count(?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, status.getId());
			stat.execute();
			cnt = ((OracleCallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
			System.err.println("int getAdRequestBeanCount(RequestStatus status): " + e);
			throw new JobOpportunityException("Can not extract AdRequestBean from DB.", e);
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

		return cnt;
	}

	public static AdRequestBean getAdRequestBean(int id) throws JobOpportunityException {

		AdRequestBean req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_ad_request.get_ad_request(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				req = createAdRequestBean(rs);

		}
		catch (SQLException e) {
			System.err.println("AdRequestBean getAdRequestBean(int id): " + e);
			throw new JobOpportunityException("Can not extract AdRequestBean from DB.", e);
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

	public static AdRequestBean getAdRequestBean(String comp_num) throws JobOpportunityException {

		AdRequestBean req = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_ad_request.get_ad_request(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, comp_num);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				req = createAdRequestBean(rs);

		}
		catch (SQLException e) {
			System.err.println("AdRequestBean getAdRequestBean(int id): " + e);
			throw new JobOpportunityException("Can not extract AdRequestBean from DB.", e);
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

	public static void addAdRequestBean(AdRequestBean abean, Personnel p) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;

		try {
			System.out.print("Adding AD basics...");

			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.personnel_ad_request.add_request(?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.setInt(2, p.getPersonnelID());
			stat.setString(3, abean.getLocation().getLocationDescription());
			stat.setDate(4, new java.sql.Date(abean.getStartDate().getTime()));
			if (abean.getEndDate() != null)
				stat.setDate(5, new java.sql.Date(abean.getEndDate().getTime()));
			else
				stat.setDate(5, null);
			stat.setDouble(6, abean.getUnits());
			stat.setInt(7, abean.getTrainingMethod().getValue());
			if (abean.getOwner() != null)
				stat.setString(8, abean.getOwner().getEmpId());
			else
				stat.setString(8, null);
			stat.setString(9, abean.getVacancyReason());
			stat.setString(10, abean.getTitle());
			stat.setInt(11, abean.getJobType().getValue());
			//add new clob field, not used any longer
			stat.setString(12, "");
			stat.setBoolean(13, abean.isUnadvertised());
			stat.setString(14, "N");
			if (abean.getAdText() != null) {
				Clob clobdesc = con.createClob();
				clobdesc.setString(1, abean.getAdText());
				((OracleCallableStatement) stat).setClob(15, clobdesc);
			}else{
				stat.setNull(15, OracleTypes.CLOB);
			}

			stat.execute();

			id = ((OracleCallableStatement) stat).getInt(1);

			if (id > 0) {
				System.out.println("DONE");

				abean.setId(id);

				// add degrees
				System.out.print("Adding AD degrees...");
				if (abean.getDegrees() != null && abean.getDegrees().length > 0)
					AdRequestDegreeManager.addAdRequestDegreeBeans(abean);
				System.out.println("DONE");

				// add majors
				System.out.print("Adding AD majors...");
				if (abean.getMajors() != null && abean.getMajors().length > 0)
					AdRequestMajorManager.addAdRequestMajors(abean);
				System.out.println("DONE");

				// add minors
				System.out.print("Adding AD minors...");
				if (abean.getMinors() != null && abean.getMinors().length > 0)
					AdRequestMinorManager.addAdRequestMinors(abean);
				System.out.println("DONE");

				con.commit();
			}
			else
				con.rollback();
		}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addAdRequestBean(AdRequestBean abean): " + e);
			throw new JobOpportunityException("Can not add AdRequestBean to DB.", e);
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
	public static void updateAdRequestBeanDetails(AdRequestBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.personnel_ad_request.update_ad_request_clob(?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setString(1, abean.getLocation().getLocationDescription());
			stat.setDate(2, new java.sql.Date(abean.getStartDate().getTime()));
			if (abean.getEndDate() != null) {
				stat.setDate(3, new java.sql.Date(abean.getEndDate().getTime()));
			}else {
				stat.setDate(3, null);
			}
			
			stat.setDate(2, new java.sql.Date(abean.getStartDate().getTime()));
			if (abean.getEndDate() != null) {
				stat.setDate(3, new java.sql.Date(abean.getEndDate().getTime()));
			}else {
				stat.setDate(3, null);
			}
			
			stat.setDouble(4, abean.getUnits());
			stat.setInt(5, abean.getTrainingMethod().getValue());
			if (abean.getOwner() != null) {
				stat.setString(6, abean.getOwner().getEmpId());
			}else {
				stat.setString(6, null);
			}
			stat.setString(7, abean.getVacancyReason());
			stat.setString(8, abean.getTitle());
			
			stat.setInt(9, abean.getJobType().getValue());
			//stat.setString(10, abean.getAdText());
			//switched to clob
			if (abean.getAdText() != null) {
				Clob clobdesc = con.createClob();
				clobdesc.setString(1, abean.getAdText());
				((OracleCallableStatement) stat).setClob(10, clobdesc);
			}else{
				stat.setNull(10, OracleTypes.CLOB);
			}
			stat.setBoolean(11, abean.isUnadvertised());
			stat.setInt(12, abean.getId());
			stat.execute();
			//now we add/remove the child objects
			// add degrees
			AdRequestDegreeManager.deleteAdRequestDegreeBeans(abean.getId());
			if (abean.getDegrees() != null && abean.getDegrees().length > 0) {
				AdRequestDegreeManager.addAdRequestDegreeBeans(abean);
			}
			AdRequestMajorManager.deleteAdRequestMajorBeans(abean.getId());
			// add majors
			if (abean.getMajors() != null && abean.getMajors().length > 0) {
				AdRequestMajorManager.addAdRequestMajors(abean);
			}
			AdRequestMinorManager.deleteAdRequestMinorBeans(abean.getId());
			// add minors
			if (abean.getMinors() != null && abean.getMinors().length > 0) {
				AdRequestMinorManager.addAdRequestMinors(abean);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void updateAdRequestBean(AdRequestBean abean): " + e);
			throw new JobOpportunityException("Can not update AdRequestBean to DB.", e);
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
	public static void approveAdRequestBean(AdRequestBean abean, Personnel p) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_ad_request.approve_ad_request_clob(?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(1, abean.getId());
			stat.setString(2, abean.getTitle());
			stat.setDate(3, new java.sql.Date(abean.getStartDate().getTime()));
			if (abean.getEndDate() != null)
				stat.setDate(4, new java.sql.Date(abean.getEndDate().getTime()));
			else
				stat.setDate(4, null);
			//stat.setString(5, abean.getAdText());
			//switched to clob
			if (abean.getAdText() != null) {
				Clob clobdesc = con.createClob();
				clobdesc.setString(1, abean.getAdText());
				((OracleCallableStatement) stat).setClob(5, clobdesc);
			}else{
				stat.setNull(5, OracleTypes.CLOB);
			}
			stat.setInt(6, p.getPersonnelID());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void approveAdRequestBean(AdRequestBean abean, Personnel p): " + e);
			throw new JobOpportunityException("Can not approve AdRequestBean to DB.", e);
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

	public static void declineAdRequestBean(AdRequestBean abean, Personnel p) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_ad_request.decline_ad_request(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(1, abean.getId());
			stat.setInt(2, p.getPersonnelID());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void declineAdRequestBean(AdRequestBean abean, Personnel p): " + e);
			throw new JobOpportunityException("Can not decline AdRequestBean to DB.", e);
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

	public static void postAdRequestBean(AdRequestBean abean, Personnel p, String comp_num)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_ad_request.post_ad_request(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(1, abean.getId());
			stat.setInt(2, p.getPersonnelID());
			stat.setString(3, comp_num);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void declineAdRequestBean(AdRequestBean abean, Personnel p): " + e);
			throw new JobOpportunityException("Can not decline AdRequestBean to DB.", e);
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
	public static void postAdRequestSSBean(int rid, Personnel p, String comp_num)
	throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
		
			stat = con.prepareCall("begin awsd_user.personnel_ad_request.post_ad_request_ss(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(1, rid);
			stat.setInt(2, p.getPersonnelID());
			stat.setString(3, comp_num);
		
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
		
			System.err.println("postAdRequestSSBean(int rid, Personnel p, String comp_num): " + e);
			throw new JobOpportunityException("Can not add AdRequestBean to DB.", e);
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
	public static void updateAdRequestBean(AdRequestBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_ad_request.mod_ad_request(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(1, abean.getId());

			if (abean.getStartDate() != null)
				stat.setDate(2, new java.sql.Date(abean.getStartDate().getTime()));
			else
				stat.setNull(2, OracleTypes.DATE);

			if (abean.getEndDate() != null)
				stat.setDate(3, new java.sql.Date(abean.getEndDate().getTime()));
			else
				stat.setNull(3, OracleTypes.DATE);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void updateAdRequestBean(AdRequestBean abean): " + e);
			throw new JobOpportunityException("Can not update AdRequestBean to DB.", e);
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

	public static void deleteAdRequestBean(int adid) {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.PERSONNEL_JOBS_PKG.delete_vacant_job_ad(?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(1, adid);
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteAdRequestBean(int adid)" + e);
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
	public static void cancelAdRequestBean(int adid, int pid) {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.PERSONNEL_AD_REQUEST.cancel_ad_request(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(1, adid);
			stat.setInt(2, pid);
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void cancelAdRequestBean(int adid, int pid)" + e);
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
	public static AdRequestBean createAdRequestBean(ResultSet rs) {

		AdRequestBean abean = null;

		try {
			abean = new AdRequestBean();

			abean.setId(rs.getInt("REQUEST_ID"));
			abean.setLocation(LocationManager.createLocationBean(rs));
			abean.setStartDate(new java.util.Date(rs.getDate("START_DATE").getTime()));
			if (rs.getDate("END_DATE") != null)
				abean.setEndDate(new java.util.Date(rs.getDate("END_DATE").getTime()));
			abean.setUnits(rs.getDouble("UNIT_TIME"));
			abean.setTrainingMethod(TrainingMethodConstant.get(rs.getInt("TRNLVL")));
			if (rs.getString("EMP_ID") != null)
				abean.setOwner(EmployeeManager.getEmployeeBean(rs.getString("EMP_ID")));
			abean.setVacancyReason(rs.getString("VACANCY_REASON"));
			abean.setCurrentStatus(RequestStatus.get(rs.getInt("CUR_STATUS")));
			abean.setTitle(rs.getString("TITLE"));
			abean.setJobType(JobTypeConstant.get(rs.getInt("JOB_TYPE")));
			//abean.setAdText(rs.getString("AD_TEXT"));
			// check to see if opp was created with new clob field or old varchar field
			if(rs.getString("JOB_REQS") !=  null){
				//check the clob field
				Clob clob = rs.getClob("JOB_REQS");
				abean.setAdText(clob.getSubString(1, (int) clob.length()));
			}else {
				abean.setAdText(rs.getString("AD_TEXT"));
			}
			abean.setCompetitionNumber(rs.getString("COMP_NUM"));

			abean.setUnadvertised(rs.getBoolean("UNADVERTISED"));
			
			// get majors
			// abean.setMajors(AdRequestMajorManager.getAdRequestMajors(abean));

			// get minors
			// abean.setMinors(AdRequestMinorManager.getAdRequestMinors(abean));

			// get degrees
			// abean.setDegrees(AdRequestDegreeManager.getAdRequestDegrees(abean));
			// get history
			// abean.setHistory(AdRequestHistoryManager.getAdRequestHistory(abean));
		}
		catch (com.esdnl.personnel.v2.model.bean.PersonnelException e) {
			e.printStackTrace();
			abean = null;
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