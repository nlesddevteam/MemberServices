package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantRecListBean;
import com.esdnl.personnel.jobs.bean.PostTransferRoundSettingsBean;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.constants.RTHPositionTypeConstant;
import com.esdnl.personnel.jobs.constants.RecommendationStatus;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class PostTransferRoundSettingsManager {
	public static PostTransferRoundSettingsBean getPostTransferRoundSettings() {

		PostTransferRoundSettingsBean jBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_ptr_settings; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next()) {
				jBean = createPostTransferRoundSettingBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("PostTransferRoundSettingsBean getPostTransferRoundSettings(): " + e);
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
	public static void updatePostTransferRoundSettings(PostTransferRoundSettingsBean jbean) {

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.update_ptr_settings(?,?,?,?); end;");
			stat.setInt(1, jbean.getPtrStatus());
			stat.setDate(2, new java.sql.Date(jbean.getPtrStartDate().getTime()));
			stat.setDate(3, new java.sql.Date(jbean.getPtrEndDate().getTime()));
			stat.setInt(4, jbean.getPtrPositionLimit());
			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("updatePostTransferRoundSettings(PostTransferRoundSettingsBean jbean): " + e);
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
	public static boolean getPostTransferRoundApplicant(String sin, PostTransferRoundSettingsBean pbean) {
		boolean canapply = false;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_ptr_applicant(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.setDate(3, new java.sql.Date(pbean.getPtrStartDate().getTime()));
			stat.setDate(4, new java.sql.Date(pbean.getPtrEndDate().getTime()));
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			int offercount=0;
			boolean fteposition=false;
			while (rs.next()) {
				//now check the offers that are processed, accepted, rejected,expired and count
				if(rs.getInt("REC_STATUS") == RecommendationStatus.PROCESSED.getValue() || 
						rs.getInt("REC_STATUS") == RecommendationStatus.OFFER_ACCEPTED.getValue() || 
						rs.getInt("REC_STATUS") == RecommendationStatus.OFFER_REJECTED.getValue() ||
						rs.getInt("REC_STATUS") == RecommendationStatus.OFFER_EXPIRED.getValue()) {
						offercount ++;
						if(rs.getInt("REC_STATUS") == RecommendationStatus.OFFER_ACCEPTED.getValue()) {
							System.out.println(rs.getDouble("UNIT_PERCENT"));
							if(rs.getDouble("UNIT_PERCENT") == 1d) {
								fteposition =true;
								break;
							}
						}
					
				}
			}
			if(!(fteposition)) {
				//now we check the count
				if(offercount < pbean.getPtrPositionLimit()) {
					canapply=true;
				}else {
					canapply=false;
				}
			}else {
				canapply=false;
			}
			
		}
		catch (SQLException e) {
			System.err.println("getPostTransferRoundApplicant(String sin, Date sdate, Date edate): " + e);
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

		return canapply;
	}
	public static ArrayList<ApplicantRecListBean> getTeacherRecs(String aid) {

		ArrayList<ApplicantRecListBean> recs = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {



			recs = new ArrayList<ApplicantRecListBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_applicant_recs(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, aid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				//create the beans
				ApplicantRecListBean abean = new ApplicantRecListBean();
				abean.setCompNumber(rs.getString("COMP_NUM"));
				abean.setRecStatus(RecommendationStatus.get(rs.getInt("REC_STATUS")).getDescription());
				abean.setJobUnit(rs.getString("FUNIT"));
				abean.setJobType(JobTypeConstant.get(rs.getInt("JOB_TYPE")).getDescription());
				abean.setRecId(rs.getInt("RECOMMENDATION_ID"));
				abean.setRecDate(rs.getString("RDATFOR"));
				recs.add(abean);
			}
				

		}
		catch (SQLException e) {
			System.err.println("ArrayList<ApplicantRecListBean> getTeacherRecs(String aid): " + e);
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

		return recs;
	}
	public static ArrayList<ApplicantRecListBean> getTeacherRecsSS(String aid) {

		ArrayList<ApplicantRecListBean> recs = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {



			recs = new ArrayList<ApplicantRecListBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_applicant_recs_ss(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, aid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				//create the beans
				ApplicantRecListBean abean = new ApplicantRecListBean();
				abean.setCompNumber(rs.getString("COMP_NUM"));
				abean.setRecStatus(RecommendationStatus.get(rs.getInt("REC_STATUS")).getDescription());
				abean.setJobUnit(rs.getString("FUNIT"));
				abean.setJobType(RTHPositionTypeConstant.get(rs.getInt("POSITION_TYPE")).getDescription());
				abean.setRecId(rs.getInt("RECOMMENDATION_ID"));
				abean.setRecDate(rs.getString("RDATFOR"));
				recs.add(abean);
			}
				

		}
		catch (SQLException e) {
			System.err.println("ArrayList<ApplicantRecListBean> getTeacherRecsSS(String aid): " + e);
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

		return recs;
	}
	public static PostTransferRoundSettingsBean createPostTransferRoundSettingBean(ResultSet rs) {
		PostTransferRoundSettingsBean abean = null;
		try {
			abean = new PostTransferRoundSettingsBean();
			abean.setPtrStatus(rs.getInt("PTR_STATUS"));
			if (rs.getDate("PTR_START_DATE") != null)
				abean.setPtrStartDate(new java.util.Date(rs.getDate("PTR_START_DATE").getTime()));
			if (rs.getDate("PTR_END_DATE") != null)
				abean.setPtrEndDate(new java.util.Date(rs.getDate("PTR_END_DATE").getTime()));	
			abean.setPtrPositionLimit(rs.getInt("PTR_POSITION_LIMIT"));
		}catch (SQLException e) {
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
