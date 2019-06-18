package com.esdnl.personnel.jobs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantCurrentPositionBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
public class ApplicantCurrentPositionManager {

	public static void addApplicantCurrentPositionBean(ApplicantCurrentPositionBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? :=  awsd_user.personnel_jobs_pkg.add_current_position_ss(?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, abean.getSin());
			stat.setInt(3, abean.getSchoolId());
			stat.setInt(4, abean.getPositionHeld());
			stat.setString(5, abean.getPositionHours());
			stat.setString(6, abean.getPositionType());
			if(abean.getStartDate() == null){
				stat.setDate(7, null);
			}else{
				stat.setDate(7, new java.sql.Date(abean.getStartDate().getTime()));
			}
			if(abean.getEndDate() == null){
				stat.setDate(8, null);
			}else{
				stat.setDate(8, new java.sql.Date(abean.getEndDate().getTime()));
			}
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("addApplicantCurrentPositionBean(ApplicantCurrentPositionBean abean): "
					+ e);
			throw new JobOpportunityException("Can not add ApplicantCurrentPositionBean to DB.", e);
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

	public static boolean deleteApplicantCurrentPositionBean(Integer id) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = true;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_current_pos_ss(?); end;");

			stat.setInt(1, id);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("boolean deleteApplicantCurrentPositionBean(String sin): "
					+ e);
			throw new JobOpportunityException("Can not delete ApplicantEducationOtherSSBean to DB.", e);
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

		return check;
	}
	public static HashMap<Integer, ApplicantCurrentPositionBean> getApplicantCurrentPositionBeanMap(String sin)
	throws JobOpportunityException {

			HashMap<Integer, ApplicantCurrentPositionBean> v_opps = null;
			ApplicantCurrentPositionBean r = null;
			Connection con = null;
			CallableStatement stat = null;
			ResultSet rs = null;
			
			try {
				v_opps = new HashMap<Integer, ApplicantCurrentPositionBean>();
			
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_current_positions(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, sin);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
			
				while (rs.next()) {
					r = createApplicantCurrentPositionBean(rs);
					v_opps.put(r.getId(), r);
				}
			}
			catch (SQLException e) {
				System.err.println("static HashMap<Integer, ApplicantCurrentPositionBean> getApplicantCurrentPositionBeanMap(String sin): "
						+ e);
				throw new JobOpportunityException("Can not extract ApplicantCurrentPositionBean from DB.", e);
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
	
	public static ApplicantCurrentPositionBean createApplicantCurrentPositionBean(ResultSet rs) {

		ApplicantCurrentPositionBean aBean = null;
		try {
			aBean = new ApplicantCurrentPositionBean();

			aBean.setSin(rs.getString("SIN"));
			aBean.setSchoolId(rs.getInt("SCHOOL_ID"));
			aBean.setPositionHeld(rs.getInt("POSITION_HELD"));
			aBean.setPositionHours(rs.getString("POSITION_HOURS"));
			aBean.setId(rs.getInt("ID"));
			aBean.setPositionType(rs.getString("POSITION_TYPE"));
			aBean.setPositionName(rs.getString("PDESCRIPTION"));
			aBean.setPositionUnion(rs.getString("UNION_NAME"));
			if (rs.getDate("START_DATE") != null)
				aBean.setStartDate(new java.util.Date(rs.getDate("START_DATE").getTime()));
			if (rs.getDate("END_DATE") != null)
				aBean.setEndDate(new java.util.Date(rs.getDate("END_DATE").getTime()));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}
