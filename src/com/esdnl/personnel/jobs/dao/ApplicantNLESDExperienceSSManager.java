package com.esdnl.personnel.jobs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantNLESDExperienceSSBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
public class ApplicantNLESDExperienceSSManager {
	public static ApplicantNLESDExperienceSSBean addApplicantNLESDExperienceSS(	ApplicantNLESDExperienceSSBean abean)
	throws JobOpportunityException {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_app_nlesd_exp_ss(?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, abean.getCurrentlyEmployed());
			stat.setString(3, abean.getSin());
			if(abean.getSenorityDate() == null){
				stat.setDate(4, null);
			}else{
				stat.setDate(4, new java.sql.Date(abean.getSenorityDate().getTime()));
			}
			stat.setString(5, abean.getSenorityStatus());
			stat.setString(6, abean.getPosition1());
			stat.setInt(7, abean.getPosition1School());
			stat.setString(8, abean.getPosition1Hours());
			stat.setString(9, abean.getPosition2());
			stat.setInt(10, abean.getPosition2School());
			stat.setString(11, abean.getPosition2Hours());
			
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ApplicantNLESDExperienceSSBean addApplicantNLESDExperienceSS(ApplicantNLESDExperienceSSBean abean)): "
					+ e);
			throw new JobOpportunityException("Can not add ApplicantNLESDExperienceSSBean to DB.", e);
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
	public static ApplicantNLESDExperienceSSBean getApplicantNLESDExperienceSSBeanBySin(String sin)
	throws JobOpportunityException {
		ApplicantNLESDExperienceSSBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_app_nlesd_exp_ss(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				eBean = createApplicantNLESDExperienceSSBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantNLESDExperienceSSBean getApplicantNLESDExperienceSSBeanBySin(String sin): "
					+ e);
			throw new JobOpportunityException("Can not extract ApplicantNLESDPermanentExperienceBean from DB.", e);
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
	public static void updateApplicantNLESDExperienceSS(ApplicantNLESDExperienceSSBean abean)
	throws JobOpportunityException {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.update_app_nlesd_exp_ss(?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setString(1, abean.getCurrentlyEmployed());
			stat.setString(2, abean.getSin());
			if(abean.getSenorityDate() == null){
				stat.setDate(3, null);
			}else{
				stat.setDate(3, new java.sql.Date(abean.getSenorityDate().getTime()));
			}
			stat.setString(4, abean.getSenorityStatus());
			stat.setString(5, abean.getPosition1());
			stat.setInt(6, abean.getPosition1School());
			stat.setString(7, abean.getPosition1Hours());
			stat.setString(8, abean.getPosition2());
			stat.setInt(9, abean.getPosition2School());
			stat.setString(10, abean.getPosition2Hours());
			stat.setInt(11, abean.getId());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateApplicantNLESDExperienceSS(ApplicantNLESDExperienceSSBean abean): "
					+ e);
			throw new JobOpportunityException("Can not add ApplicantNLESDExperienceSSBean to DB.", e);
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
	public static ApplicantNLESDExperienceSSBean createApplicantNLESDExperienceSSBean(ResultSet rs) {
		ApplicantNLESDExperienceSSBean aBean = null;
		try {
			aBean = new ApplicantNLESDExperienceSSBean();
			aBean.setId(rs.getInt("ID"));
			aBean.setCurrentlyEmployed(rs.getString("CURRENTLYEMPLOYED"));
			aBean.setSin(rs.getString("SIN"));
			if(rs.getDate("SENORITYDATE") == null){
				aBean.setSenorityDate(null);
			}else{
				aBean.setSenorityDate(new java.util.Date(rs.getDate("SENORITYDATE").getTime()));
			}
			
			aBean.setSenorityStatus(rs.getString("SENORITYSTATUS"));
			aBean.setPosition1(rs.getString("POSITION1"));
			aBean.setPosition1School(rs.getInt("POSITION1SCHOOL"));
			aBean.setPosition1Hours(rs.getString("POSITION1HOURS"));
			aBean.setPosition2(rs.getString("POSITION2"));
			aBean.setPosition2School(rs.getInt("POSITION2SCHOOL"));
			aBean.setPosition2Hours(rs.getString("POSITION2HOURS"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}	
}
