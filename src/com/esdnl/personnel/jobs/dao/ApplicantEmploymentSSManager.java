package com.esdnl.personnel.jobs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantEmploymentSSBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
public class ApplicantEmploymentSSManager {
	public static ApplicantEmploymentSSBean addApplicantEmploymentSS(ApplicantEmploymentSSBean abean)
	throws JobOpportunityException {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_app_employment_ss(?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, abean.getSin());
			stat.setString(3, abean.getCompany());
			stat.setString(4, abean.getAddress());
			stat.setString(5, abean.getJobTitle());
			stat.setString(6, abean.getPhoneNumber());
			stat.setString(7, abean.getSupervisor());
			stat.setString(8, abean.getStartingSalary());
			stat.setString(9, abean.getEndingSalary());
			stat.setString(10, abean.getDuties());
			if(abean.getFromDate() == null){
				stat.setDate(11, null);
			}else{
				stat.setDate(11, new java.sql.Date(abean.getFromDate().getTime()));
			}
			if(abean.getToDate() == null){
				stat.setDate(12, null);
			}else{
				stat.setDate(12, new java.sql.Date(abean.getToDate().getTime()));
			}
			stat.setString(13, abean.getReasonForLeaving());
			stat.setString(14, abean.getContact());
			stat.setInt(15,abean.getReason());
			
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ApplicantEmploymentSSBean addApplicantEmploymentSS(ApplicantEmploymentSSBean abean): "
					+ e);
			throw new JobOpportunityException("Can not add ApplicantEmploymentSSBean to DB.", e);
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
	public static ArrayList<ApplicantEmploymentSSBean> getApplicantEmploymentSSBeanBySin(String sin)
	throws JobOpportunityException {
		ArrayList<ApplicantEmploymentSSBean> list = new ArrayList<ApplicantEmploymentSSBean>();
		ApplicantEmploymentSSBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_app_employment_ss(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				eBean = createApplicantEmploymentSSBean(rs);
				list.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<ApplicantEmploymentSSBean> getApplicantEmploymentSSBeanBySin(String sin): "
					+ e);
			throw new JobOpportunityException("Can not extract ApplicantEmploymentSSBean from DB.", e);
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
		return list;
	}
	public static void deleteApplicantEmploymentSSBean(int id) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_app_employment(?); end;");

			stat.setInt(1, id);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(" void deleteApplicantEmploymentSSBean(int id)): " + e);
			throw new JobOpportunityException("Can not delete ApplicantEmplymentSSBean in DB.", e);
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
	public static ApplicantEmploymentSSBean createApplicantEmploymentSSBean(ResultSet rs) {
		ApplicantEmploymentSSBean aBean = null;
		try {
			aBean = new ApplicantEmploymentSSBean();
			aBean.setId(rs.getInt("ID"));
			aBean.setSin(rs.getString("SIN"));
			aBean.setCompany(rs.getString("COMPANY"));
			aBean.setAddress(rs.getString("ADDRESS"));
			aBean.setJobTitle(rs.getString("JOBTITLE"));
			aBean.setPhoneNumber(rs.getString("PHONENUMBER"));
			aBean.setSupervisor(rs.getString("SUPERVISOR"));
			aBean.setStartingSalary(rs.getString("STARTINGSALARY"));
			aBean.setEndingSalary(rs.getString("ENDINGSALARY"));
			aBean.setDuties(rs.getString("DUTIES"));
			if(!(rs.getDate("FROMDATE")==null)){
				aBean.setFromDate(new java.util.Date(rs.getDate("FROMDATE").getTime()));
			}else{
				aBean.setFromDate(null);
			}
			if(!(rs.getDate("TODATE")==null)){
				aBean.setToDate(new java.util.Date(rs.getDate("TODATE").getTime()));
			}else{
				aBean.setToDate(null);
			}
			aBean.setReasonForLeaving(rs.getString("REASONFORLEAVING"));
			aBean.setContact(rs.getString("CONTACT"));
			aBean.setReason(rs.getInt("REASON"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}	
}
